package mods.eln.sixnode.groundcable

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.i18n.I18N
import mods.eln.misc.Direction
import mods.eln.misc.LRDU
import mods.eln.misc.Obj3D
import mods.eln.misc.Obj3D.Obj3DPart
import mods.eln.misc.Utils
import mods.eln.misc.VoltageTier
import mods.eln.node.NodeBase
import mods.eln.node.six.SixNode
import mods.eln.node.six.SixNodeDescriptor
import mods.eln.node.six.SixNodeElement
import mods.eln.node.six.SixNodeElementRender
import mods.eln.node.six.SixNodeEntity
import mods.eln.sim.ElectricalLoad
import mods.eln.sim.ThermalLoad
import mods.eln.sim.mna.component.VoltageSource
import mods.eln.sim.nbt.NbtElectricalLoad
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack

class GroundCableDescriptor(name: String, obj3D: Obj3D): SixNodeDescriptor(name, GroundCableElement::class.java, GroundCableRender::class.java) {
    var main: Obj3DPart? = null

    init {
        main = obj3D.getPart("main")
        voltageTier = VoltageTier.NEUTRAL
    }

    fun draw() {
        if (main != null) main!!.draw()
    }

    override fun addInfo(itemStack: ItemStack, entityPlayer: EntityPlayer, list: MutableList<String>) {
        super.addInfo(itemStack, entityPlayer, list)
        list.add(I18N.tr("Provides a zero volt reference."))
        list.add(I18N.tr("Can be used to set point on an"))
        list.add(I18N.tr("electrical network to 0V potential."))
        list.add(I18N.tr("Internal resistance: %1$\u2126", Utils.plotValue(Eln.getSmallRs())))
    }
}

class GroundCableElement(sixNode: SixNode, side: Direction, descriptor: SixNodeDescriptor): SixNodeElement(sixNode, side, descriptor) {
    val electricalLoad = NbtElectricalLoad("electricalLoad")
    val ground = VoltageSource("ground", electricalLoad, null)

    init {
        ground.u = 0.0
        electricalLoadList.add(electricalLoad)
        electricalComponentList.add(ground)
    }

    override fun getElectricalLoad(lrdu: LRDU?, mask: Int): ElectricalLoad? {
        return electricalLoad
    }

    override fun getThermalLoad(lrdu: LRDU?, mask: Int): ThermalLoad? {
        return null
    }

    override fun getConnectionMask(lrdu: LRDU?): Int {
        return NodeBase.MASK_ELECTRIC
    }

    override fun multiMeterString(): String? {
        return Utils.plotAmpere(electricalLoad.current)
    }

    override fun getWaila(): Map<String, String>? {
        val info: MutableMap<String, String> = HashMap()
        info[I18N.tr("Current")] = Utils.plotAmpere(electricalLoad.i)
        return info
    }

    override fun thermoMeterString(): String? {
        return ""
    }

    override fun initialize() {
        Eln.applySmallRs(electricalLoad)
    }
}

class GroundCableRender(tileEntity: SixNodeEntity, side: Direction, descriptor: SixNodeDescriptor): SixNodeElementRender(tileEntity, side, descriptor) {
    var descriptor: GroundCableDescriptor = descriptor as GroundCableDescriptor

    override fun draw() {
        super.draw()
        if (side.isY) {
            front.glRotateOnX()
        }
        descriptor.draw()
    }

    override fun getCableRender(lrdu: LRDU?): CableRenderDescriptor {
        return Eln.uninsulatedHighCurrentRender
    }
}
