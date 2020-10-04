package mods.eln.sixnode.electriccable

import mods.eln.Eln
import mods.eln.cable.CableRender
import mods.eln.cable.CableRenderDescriptor
import mods.eln.i18n.I18N
import mods.eln.misc.Coordonate
import mods.eln.misc.Direction
import mods.eln.misc.LRDU
import mods.eln.misc.Utils
import mods.eln.misc.UtilsClient
import mods.eln.misc.VoltageLevelColor
import mods.eln.misc.VoltageTier
import mods.eln.node.NodeBase
import mods.eln.node.six.SixNode
import mods.eln.node.six.SixNodeDescriptor
import mods.eln.node.six.SixNodeElement
import mods.eln.node.six.SixNodeElementRender
import mods.eln.node.six.SixNodeEntity
import mods.eln.sim.ElectricalLoad
import mods.eln.sim.IProcess
import mods.eln.sim.ThermalLoad
import mods.eln.sim.mna.component.Resistor
import mods.eln.sim.mna.misc.MnaConst
import mods.eln.sim.nbt.NbtElectricalLoad
import mods.eln.sim.nbt.NbtThermalLoad
import mods.eln.sim.process.destruct.ThermalLoadWatchDog
import mods.eln.sim.process.destruct.VoltageStateWatchDog
import mods.eln.sim.process.destruct.WorldExplosion
import mods.eln.sim.process.heater.ElectricalLoadHeatThermalLoad
import mods.eln.sixnode.genericcable.GenericCableDescriptor
import net.minecraft.entity.Entity
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import org.lwjgl.opengl.GL11
import java.util.*

class ElectricCableDescriptor(name: String, render: CableRenderDescriptor, val material: String = "Copper"): GenericCableDescriptor(name, ElectricCableElement::class.java, ElectricCableRender::class.java) {

    var insulationVoltage = 0.0
        set(x) {
            field = x
            voltageTier = when {
                insulationVoltage <= 0.0 -> {
                    // No insulation means no voltage limits!
                    VoltageTier.LOW
                }
                insulationVoltage <= 300.0 -> {
                    VoltageTier.INDUSTRIAL
                }
                insulationVoltage <= 1_000.0 -> {
                    VoltageTier.INDUSTRIAL
                }
                else -> {
                    VoltageTier.SUBURBAN_GRID
                }
            }
        }

    init {
        this.render = render
        this.electricalRs = 0.01
        this.voltageTier = VoltageTier.NEUTRAL
    }

    override fun applyTo(electricalLoad: ElectricalLoad, rsFactor: Double) {
        electricalLoad.rs = electricalRs * rsFactor
    }

    override fun applyTo(electricalLoad: ElectricalLoad) {
        electricalLoad.rs = electricalRs
    }

    override fun applyTo(resistor: Resistor) {
        resistor.r = electricalRs
    }

    override fun applyTo(resistor: Resistor, factor: Double) {
        resistor.r = electricalRs * factor
    }

    override fun applyTo(thermalLoad: ThermalLoad) {
        thermalLoad.Rs = 1.0
        thermalLoad.C = 1.0
        thermalLoad.Rp = 1.0
    }

    override fun getNodeMask(): Int {
        return NodeBase.MASK_ELECTRIC
    }

    override fun addInformation(itemStack: ItemStack, entityPlayer: EntityPlayer, list: MutableList<String>, par4: Boolean) {
        super.addInformation(itemStack, entityPlayer, list, par4)
        list.add(I18N.tr("Nominal Ratings:"))
        list.add("  " + I18N.tr("Serial resistance: %1$\u2126", Utils.plotValue(electricalRs * 2)))
    }
}

class ElectricCableElement(sixNode: SixNode, side: Direction, descriptor: SixNodeDescriptor): SixNodeElement(sixNode, side, descriptor) {

    val descriptor = descriptor as ElectricCableDescriptor

    val electricalLoad = NbtElectricalLoad("electricalLoad")
    val thermalLoad = NbtThermalLoad("thermalLoad")
    val heater = ElectricalLoadHeatThermalLoad(electricalLoad, thermalLoad)
    val thermalWatchdog = ThermalLoadWatchDog()
    val voltageWatchdog = VoltageStateWatchDog()

    init {
        electricalLoad.setCanBeSimplifiedByLine(true)
        electricalLoadList.add(electricalLoad)
        thermalLoad.setAsSlow()
        thermalLoadList.add(thermalLoad)
        thermalSlowProcessList.add(heater)
        thermalWatchdog
            .set(thermalLoad)
            .setLimit(100.0, -100.0)
            .set(WorldExplosion(this).cableExplosion())
        slowProcessList.add(thermalWatchdog)
        slowProcessList.add(PlayerHarmer(electricalLoad, this.descriptor.insulationVoltage, this.coordonate))
    }

    override fun getElectricalLoad(lrdu: LRDU?, mask: Int): ElectricalLoad {
        return electricalLoad
    }

    override fun getThermalLoad(lrdu: LRDU?, mask: Int): ThermalLoad {
        return thermalLoad
    }

    override fun getConnectionMask(lrdu: LRDU?): Int {
        return descriptor.nodeMask
    }

    override fun multiMeterString(): String {
        return Utils.plotUIP(electricalLoad.u, electricalLoad.i)
    }

    override fun thermoMeterString(): String {
        return Utils.plotCelsius("T", thermalLoad.Tc)
    }

    override fun initialize() {
        descriptor.applyTo(electricalLoad)
        descriptor.applyTo(thermalLoad)
    }

    override fun getWaila(): Map<String, String>? {
        val info: MutableMap<String, String> = HashMap()
        info[I18N.tr("Current")] = Utils.plotAmpere("", electricalLoad.i)
        info[I18N.tr("Temperature")] = Utils.plotCelsius("", thermalLoad.t)
        if (Eln.wailaEasyMode) {
            info[I18N.tr("Voltage")] = Utils.plotVolt("", electricalLoad.u)
        }
        val ss = electricalLoad.subSystem
        if (ss != null) {
            val subSystemSize = electricalLoad.subSystem.component.size
            val textColor = when {
                subSystemSize <= 8 -> "§a"
                subSystemSize <= 15 -> "§6"
                else -> "§c"
            }
            info[I18N.tr("Subsystem Matrix Size")] = textColor + subSystemSize
        } else {
            info[I18N.tr("Subsystem Matrix Size")] = "§cnull SubSystem"
        }
        return info
    }
}

class ElectricCableRender(tileEntity: SixNodeEntity, side: Direction, descriptor: SixNodeDescriptor): SixNodeElementRender(tileEntity, side, descriptor) {

    val descriptor = descriptor as ElectricCableDescriptor

    override fun drawCableAuto() = false

    override fun draw() {
        if (descriptor.insulationVoltage < 0.1) {
            when (descriptor.material.toLowerCase()) {
                "copper" -> {
                    GL11.glColor3f(0.722f, 0.451f, 0.20f)
                }
                "aluminum" -> {
                    GL11.glColor3f(0.815f, 0.835f, 0.858f)
                }
                else -> {
                    // Same as copper
                    GL11.glColor3f(0.722f, 0.451f, 0.20f)
                }
            }
        } else {
            Utils.setGlColorFromDye(0, 1.0f)
        }
        UtilsClient.bindTexture(descriptor.render.cableTexture)
        glListCall()
        GL11.glColor3f(1f, 1f, 1f)
    }

    override fun glListDraw() {
        CableRender.drawCable(descriptor.render, connectedSide, CableRender.connectionType(this, side))
        CableRender.drawNode(descriptor.render, connectedSide, CableRender.connectionType(this, side))
    }

    override fun glListEnable() = true

    override fun getCableRender(lrdu: LRDU?): CableRenderDescriptor? {
        return descriptor.render
    }
}

class PlayerHarmer(val electricalLoad: ElectricalLoad, private val insulationVoltage: Double, val location: Coordonate): IProcess {

    private fun harmFunction(distance: Double) = 1.0 - ( distance / 3.0)

    override fun process(time: Double) {
        val harmLevel = Math.max(0.0, (electricalLoad.u - 50 - insulationVoltage) / 500.0)
        val objects = location.world().getEntitiesWithinAABB(Entity::class.java, location.getAxisAlignedBB(4))
        for(obj in objects) {
            val ent = obj as Entity
            val distance = location.distanceTo(ent)
            val pain = (harmFunction(distance) * harmLevel).toFloat()
            if (distance < 3 && pain > 0.05) {
                ent.attackEntityFrom(DamageSource("Cable"), pain)
            }
        }
    }
}
