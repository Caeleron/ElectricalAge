package mods.eln.registry

import mods.eln.Eln
import mods.eln.ghost.GhostBlock
import mods.eln.ghost.GhostGroup
import mods.eln.gridnode.electricalpole.ElectricalPoleDescriptor
import mods.eln.gridnode.transformer.GridTransformerDescriptor
import mods.eln.i18n.I18N
import mods.eln.mechanical.ClutchDescriptor
import mods.eln.mechanical.FixedShaftDescriptor
import mods.eln.mechanical.FlywheelDescriptor
import mods.eln.mechanical.GasTurbineDescriptor
import mods.eln.mechanical.GeneratorDescriptor
import mods.eln.mechanical.MotorDescriptor
import mods.eln.mechanical.RotaryMotorDescriptor
import mods.eln.mechanical.SteamTurbineDescriptor
import mods.eln.mechanical.StraightJointDescriptor
import mods.eln.mechanical.TachometerDescriptor
import mods.eln.mechanical.VerticalHubDescriptor
import mods.eln.misc.Coordonate
import mods.eln.misc.FunctionTable
import mods.eln.misc.FunctionTableYProtect
import mods.eln.misc.Utils
import mods.eln.misc.series.SerieEE
import mods.eln.sim.ThermalLoadInitializer
import mods.eln.sim.ThermalLoadInitializerByPowerDrop
import mods.eln.sound.SoundCommand
import mods.eln.transparentnode.FuelGeneratorDescriptor
import mods.eln.transparentnode.FuelHeatFurnaceDescriptor
import mods.eln.transparentnode.autominer.AutoMinerDescriptor
import mods.eln.transparentnode.battery.BatteryDescriptor
import mods.eln.transparentnode.dcdc.DcDcDescriptor
import mods.eln.transparentnode.dcdc.LegacyDcDcDescriptor
import mods.eln.transparentnode.eggincubator.EggIncubatorDescriptor
import mods.eln.transparentnode.electricalantennarx.ElectricalAntennaRxDescriptor
import mods.eln.transparentnode.electricalantennatx.ElectricalAntennaTxDescriptor
import mods.eln.transparentnode.electricalfurnace.ElectricalFurnaceDescriptor
import mods.eln.transparentnode.electricalmachine.CompressorDescriptor
import mods.eln.transparentnode.electricalmachine.MaceratorDescriptor
import mods.eln.transparentnode.electricalmachine.MagnetizerDescriptor
import mods.eln.transparentnode.electricalmachine.OldArcFurnaceDescriptor
import mods.eln.transparentnode.electricalmachine.PlateMachineDescriptor
import mods.eln.transparentnode.festive.ChristmasTreeDescriptor
import mods.eln.transparentnode.festive.HolidayCandleDescriptor
import mods.eln.transparentnode.festive.StringLightsDescriptor
import mods.eln.transparentnode.heatfurnace.HeatFurnaceDescriptor
import mods.eln.transparentnode.powercapacitor.PowerCapacitorDescriptor
import mods.eln.transparentnode.powerinductor.PowerInductorDescriptor
import mods.eln.transparentnode.solarpanel.SolarPanelDescriptor
import mods.eln.transparentnode.teleporter.TeleporterDescriptor
import mods.eln.transparentnode.thermaldissipatoractive.ThermalDissipatorActiveDescriptor
import mods.eln.transparentnode.thermaldissipatorpassive.ThermalDissipatorPassiveDescriptor
import mods.eln.transparentnode.turbine.TurbineDescriptor
import mods.eln.transparentnode.turret.TurretDescriptor
import mods.eln.transparentnode.variabledcdc.VariableDcDcDescriptor
import mods.eln.transparentnode.waterturbine.WaterTurbineDescriptor
import mods.eln.transparentnode.windturbine.WindTurbineDescriptor
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class TransparentNodeRegistry {
    companion object {
        fun register() {
            //TRANSPARENT NODE REGISTRATION
            //Sub-UID must be unique in this section only.
            //============================================
            registerPowerComponent(1)
            registerTransformer(2)
            registerHeatFurnace(3)
            registerTurbine(4)
            registerElectricalAntenna(7)
            registerBattery(16)
            registerElectricalFurnace(32)
            registerMacerator(33)
            registerArcFurnace(34)
            registerCompressor(35)
            registerMagnetizer(36)
            registerPlateMachine(37)
            registerEggIncubator(41)
            registerAutoMiner(42)
            registerSolarPanel(48)
            registerWindTurbine(49)
            registerThermalDissipatorPassiveAndActive(64)
            registerTransparentNodeMisc(65)
            registerTurret(66)
            registerFuelGenerator(67)
            registerGridDevices(123)
            //registerFloodlight(68);
            registerFestive(69)
        }

        /*
    private void registerFloodlight(int id) {
        int subId;
        String name;
        {
            subId = 0;
            name = TR_NAME(Type.NONE, "Basic Floodlight");
            BasicFloodlightDescriptor desc = new BasicFloodlightDescriptor(name, obj.getObj("Floodlight"));
            transparentNodeItem.addDescriptor(subId + (id << 6), desc);
        }
        {
            subId = 1;
            name = TR_NAME(Type.NONE, "Motorized Floodlight");
            MotorizedFloodlightDescriptor desc = new MotorizedFloodlightDescriptor(name, obj.getObj("FloodlightMotor"));
            transparentNodeItem.addDescriptor(subId + (id << 6), desc);
        }
    }
*/
        private fun registerPowerComponent(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Power inductor")
                val desc = PowerInductorDescriptor(
                    name, null, SerieEE.newE12(-1.0)
                )
                Eln.transparentNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
            run {
                subId = 20
                name = I18N.TR_NAME(I18N.Type.NONE, "Power capacitor")
                val desc = PowerCapacitorDescriptor(
                    name, null, SerieEE.newE6(-2.0), 300.0
                )
                Eln.transparentNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
        }

        private fun registerTransformer(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Legacy DC-DC Converter")
                val desc = LegacyDcDcDescriptor(name, Eln.obj.getObj("transformator"),
                    Eln.obj.getObj("feromagneticcorea"), Eln.obj.getObj("transformatorCase"), 0.5f)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Variable DC-DC Converter")
                val desc = VariableDcDcDescriptor(name, Eln.obj.getObj("variabledcdc"),
                    Eln.obj.getObj("feromagneticcorea"), Eln.obj.getObj("transformatorCase"))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "DC-DC Converter")
                val desc = DcDcDescriptor(name, Eln.obj.getObj("transformator"),
                    Eln.obj.getObj("feromagneticcorea"), Eln.obj.getObj("transformatorCase"), 0.5f)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerHeatFurnace(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Stone Heat Furnace")
                val desc = HeatFurnaceDescriptor(name,
                    "stonefurnace", 4000.0,
                    Utils.getCoalEnergyReference() * 2 / 3,  // double
                    // nominalPower,
                    // double
                    // nominalCombustibleEnergy,
                    8, 500.0,  // int combustionChamberMax,double
                    // combustionChamberPower,
                    ThermalLoadInitializerByPowerDrop(780.0, (-100).toDouble(), 10.0, 2.0) // thermal
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Fuel Heat Furnace")
                val desc = FuelHeatFurnaceDescriptor(name,
                    Eln.obj.getObj("FuelHeater"), ThermalLoadInitializerByPowerDrop(780.0, (-100).toDouble(), 10.0, 2.0))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerTurbine(id: Int) {
            var subId: Int
            var name: String
            val TtoU = FunctionTable(doubleArrayOf(0.0, 0.1, 0.85,
                1.0, 1.1, 1.15, 1.18, 1.19, 1.25), 8.0 / 5.0)
            val PoutToPin = FunctionTable(doubleArrayOf(0.0, 0.2,
                0.4, 0.6, 0.8, 1.0, 1.3, 1.8, 2.7), 8.0 / 5.0)
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Turbine")
                val RsFactor = 0.1
                val nominalU = Eln.LVU
                val nominalP: Double = 1000 * Eln.heatTurbinePowerFactor // it was 300 before
                val nominalDeltaT = 250.0
                val desc = TurbineDescriptor(name, "turbineb", Eln.lowVoltageCableDescriptor.render,
                    TtoU.duplicate(nominalDeltaT, nominalU), PoutToPin.duplicate(nominalP, nominalP), nominalDeltaT,
                    nominalU, nominalP, nominalP / 40, Eln.lowVoltageCableDescriptor.electricalRs * RsFactor, 25.0,
                    nominalDeltaT / 40, nominalP / (nominalU / 25), "eln:heat_turbine_50v")
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Turbine")
                val RsFactor = 0.10
                val nominalU = Eln.MVU
                val nominalP: Double = 2000 * Eln.heatTurbinePowerFactor
                val nominalDeltaT = 350.0
                val desc = TurbineDescriptor(name, "turbinebblue", Eln.meduimVoltageCableDescriptor.render,
                    TtoU.duplicate(nominalDeltaT, nominalU), PoutToPin.duplicate(nominalP, nominalP), nominalDeltaT,
                    nominalU, nominalP, nominalP / 40, Eln.meduimVoltageCableDescriptor.electricalRs * RsFactor, 50.0,
                    nominalDeltaT / 40, nominalP / (nominalU / 25), "eln:heat_turbine_200v")
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 9
                val desc = SteamTurbineDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Steam Turbine"),
                    Eln.obj.getObj("Turbine")
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 10
                val nominalRads = 800f
                val nominalU = 3200f
                val nominalP = 4000f
                val desc = GeneratorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Generator"),
                    Eln.obj.getObj("Generator"),
                    Eln.highVoltageCableDescriptor,
                    nominalRads, nominalU,
                    nominalP / (nominalU / 25),
                    nominalP,
                    Eln.sixNodeThermalLoadInitializer.copy()
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 11
                val desc = GasTurbineDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Gas Turbine"),
                    Eln.obj.getObj("GasTurbine")
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 12
                val desc = StraightJointDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Joint"),
                    Eln.obj.getObj("StraightJoint"))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 13
                val desc = VerticalHubDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Joint hub"),
                    Eln.obj.getObj("VerticalHub"))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 14
                val desc = FlywheelDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Flywheel"),
                    Eln.obj.getObj("Flywheel"))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 15
                val desc = TachometerDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Tachometer"),
                    Eln.obj.getObj("Tachometer"))
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 16
                val nominalRads = 800f
                val nominalU = 3200f
                val nominalP = 1200f
                val desc = MotorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Shaft Motor"),
                    Eln.obj.getObj("Motor"),
                    Eln.veryHighVoltageCableDescriptor,
                    nominalRads,
                    nominalU,
                    nominalP,
                    25.0f * nominalP / nominalU,
                    25.0f * nominalP / nominalU,
                    Eln.sixNodeThermalLoadInitializer.copy()
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 17
                val desc = ClutchDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Clutch"),
                    Eln.obj.getObj("Clutch")
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 18
                val desc = FixedShaftDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Fixed Shaft"),
                    Eln.obj.getObj("FixedShaft")
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 19
                val desc = RotaryMotorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Rotary Motor"),
                    Eln.obj.getObj("Starter_Motor")
                )
                val g = GhostGroup()
                for (x in -1..1) {
                    for (y in -1..1) {
                        for (z in -1 downTo -3 + 1) {
                            g.addElement(x, y, z)
                        }
                    }
                }
                g.removeElement(0, 0, 0)
                desc.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalAntenna(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Power Transmitter Antenna")
                val P = 250.0
                desc = ElectricalAntennaTxDescriptor(name,
                    Eln.obj.getObj("lowpowertransmitterantenna"), 200,  // int
                    // rangeMax,
                    0.9, 0.7,  // double electricalPowerRatioEffStart,double
                    // electricalPowerRatioEffEnd,
                    Eln.LVU, P,  // double electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.LVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.lowVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Power Receiver Antenna")
                val P = 250.0
                desc = ElectricalAntennaRxDescriptor(name,
                    Eln.obj.getObj("lowpowerreceiverantenna"), Eln.LVU, P,  // double
                    // electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.LVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.lowVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Power Transmitter Antenna")
                val P = 1000.0
                desc = ElectricalAntennaTxDescriptor(name,
                    Eln.obj.getObj("lowpowertransmitterantenna"), 250,  // int
                    // rangeMax,
                    0.9, 0.75,  // double electricalPowerRatioEffStart,double
                    // electricalPowerRatioEffEnd,
                    Eln.MVU, P,  // double electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.MVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.meduimVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 3
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Power Receiver Antenna")
                val P = 1000.0
                desc = ElectricalAntennaRxDescriptor(name,
                    Eln.obj.getObj("lowpowerreceiverantenna"), Eln.MVU, P,  // double
                    // electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.MVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.meduimVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "High Power Transmitter Antenna")
                val P = 2000.0
                desc = ElectricalAntennaTxDescriptor(name,
                    Eln.obj.getObj("lowpowertransmitterantenna"), 300,  // int
                    // rangeMax,
                    0.95, 0.8,  // double electricalPowerRatioEffStart,double
                    // electricalPowerRatioEffEnd,
                    Eln.HVU, P,  // double electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.HVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.highVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 5
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "High Power Receiver Antenna")
                val P = 2000.0
                desc = ElectricalAntennaRxDescriptor(name,
                    Eln.obj.getObj("lowpowerreceiverantenna"), Eln.HVU, P,  // double
                    // electricalNominalVoltage,double
                    // electricalNominalPower,
                    Eln.HVU * 1.3, P * 1.3,  // electricalMaximalVoltage,double
                    // electricalMaximalPower,
                    Eln.highVoltageCableDescriptor)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerBattery(id: Int) {
            var subId: Int
            var name: String
            val heatTIme = 30.0
            val voltageFunctionTable = doubleArrayOf(0.000, 0.9, 1.0, 1.025, 1.04, 1.05,
                2.0)
            val voltageFunction = FunctionTable(voltageFunctionTable,
                6.0 / 5)
            Utils.printFunction(voltageFunction, -0.2, 1.2, 0.1)
            val stdDischargeTime = 60 * 8.toDouble()
            val stdU = Eln.LVU
            val stdP: Double = Eln.LVP() / 4
            val stdEfficiency = 1.0 - 2.0 / 50.0
            Eln.batteryVoltageFunctionTable = voltageFunction
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Cost Oriented Battery")
                val desc = BatteryDescriptor(name, "BatteryBig", Eln.batteryCableDescriptor,
                    0.5,  //what % of charge it starts out with
                    true, true,  //is rechargable?, Uses Life Mechanic?
                    voltageFunction,
                    stdU,  //battery nominal voltage
                    stdP * 1.2,  //how much power it can handle at max,
                    0.00,  //precentage of its total output to self-discharge. Should probably be 0
                    stdP,  //no idea
                    stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit, // thermalCoolLimit,
                    "Cheap battery" // name, description)
                )
                desc.setRenderSpec("lowcost")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Capacity Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.batteryCableDescriptor, 0.5, true, true, voltageFunction,
                    stdU / 4, stdP / 2 * 1.2, 0.000,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP / 2, stdDischargeTime * 8 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                )
                desc.setRenderSpec("capacity")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.meduimVoltageCableDescriptor, 0.5, true, true, voltageFunction, stdU * 4,
                    stdP * 1.2, 0.000,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP, stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                )
                desc.setRenderSpec("highvoltage")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 3
                name = I18N.TR_NAME(I18N.Type.NONE, "Current Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.batteryCableDescriptor, 0.5, true, true, voltageFunction, stdU,
                    stdP * 1.2 * 4, 0.000,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP * 4, stdDischargeTime / 6 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                )
                desc.setRenderSpec("current")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Life Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.batteryCableDescriptor, 0.5, true, false, voltageFunction, stdU,
                    stdP * 1.2, 0.000,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP, stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                )
                desc.setRenderSpec("life")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 5
                name = I18N.TR_NAME(I18N.Type.NONE, "Single-use Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.batteryCableDescriptor, 1.0, false, false, voltageFunction, stdU,
                    stdP * 1.2 * 2, 0.000,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP * 2, stdDischargeTime / 4 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                )
                desc.setRenderSpec("coal")
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Experimental Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.batteryCableDescriptor, 0.5, true, false, voltageFunction, stdU * 2,
                    stdP * 1.2 * 8, 0.025,  // electricalU,
                    // electricalPMax,electricalDischargeRate
                    stdP * 8, stdDischargeTime / 4 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,  // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),  // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "You were unable to fix the power leaking problem, though." // name, description)
                )
                desc.setRenderSpec("highvoltage")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            /*{
                subId = 32;
                name = TR_NAME(Type.NONE, "50V Condensator");

                BatteryDescriptor desc = new BatteryDescriptor(name,
                    "condo200", batteryCableDescriptor, 0.0, true, false,
                    condoVoltageFunction,
                    stdU, stdP * 1.2 * 8, 0.005, // electricalU,//
                    // electricalPMax,electricalDischargeRate
                    stdP * 8, 4, condoEfficiency, stdBatteryHalfLife, // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60, -100, // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "Obselete, must be deleted" // name, description)
                );
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 2.0);
                desc.setDefaultIcon("empty-texture");
                transparentNodeItem.addWithoutRegistry(subId + (id << 6), desc);
            }

            {
                subId = 36;
                name = TR_NAME(I18N.Type.NONE, "200V Condensator");

                BatteryDescriptor desc = new BatteryDescriptor(name,
                    "condo200", highVoltageCableDescriptor, 0.0, true, false,
                    condoVoltageFunction,
                    MVU, MVP() * 1.5, 0.005, // electricalU,//
                    // electricalPMax,electricalDischargeRate
                    MVP(), 4, condoEfficiency, stdBatteryHalfLife, // electricalStdP,
                    // electricalStdDischargeTime,
                    // electricalStdEfficiency,
                    // electricalStdHalfLife,
                    heatTIme, 60, -100, // thermalHeatTime, thermalWarmLimit,
                    // thermalCoolLimit,
                    "the battery" // name, description)
                );
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 2.0);
                desc.setDefaultIcon("empty-texture");
                transparentNodeItem.addWithoutRegistry(subId + (id << 6), desc);
            } */
        }

        private fun registerElectricalFurnace(id: Int) {
            var subId: Int
            var name: String
            Eln.furnaceList.add(ItemStack(Blocks.furnace))
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Furnace")
                val PfTTable = doubleArrayOf(0.0, 20.0, 40.0, 80.0, 160.0, 240.0, 360.0, 540.0, 756.0, 1058.4, 1481.76)
                val thermalPlostfTTable = DoubleArray(PfTTable.size)
                for (idx in thermalPlostfTTable.indices) {
                    thermalPlostfTTable[idx] = (PfTTable[idx]
                        * Math.pow((idx + 1.0) / thermalPlostfTTable.size, 2.0)
                        * 2)
                }
                val PfT = FunctionTableYProtect(PfTTable,
                    800.0, 0.0, 100000.0)
                val thermalPlostfT = FunctionTableYProtect(
                    thermalPlostfTTable, 800.0, 0.001, 10000000.0)
                val desc = ElectricalFurnaceDescriptor(
                    name, PfT, thermalPlostfT,  // thermalPlostfT;
                    40.0 // thermalC;
                )
                Eln.electricalFurnace = desc
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                Eln.furnaceList.add(desc.newItemStack())
            }
            // Utils.smeltRecipeList.addMachine(new ItemStack(Blocks.furnace));
        }

        private fun registerMacerator(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Macerator")
                val desc = MaceratorDescriptor(name,
                    "maceratora", Eln.LVU, 200.0,  // double nominalU,double nominalP,
                    Eln.LVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor cable
                    Eln.maceratorRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:macerator")
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Macerator")
                val desc = MaceratorDescriptor(name,
                    "maceratorb", Eln.MVU, 2000.0,  // double nominalU,double nominalP,
                    Eln.MVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable
                    Eln.maceratorRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:macerator")
            }
        }

        private fun registerArcFurnace(id: Int) {
            var subId: Int
            var name: String?
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Old 800V Arc Furnace")
                val desc = OldArcFurnaceDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("arcfurnaceold"),
                    Eln.HVU, 10000.0,  // double nominalU,double nominalP,
                    Eln.HVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.highVoltageCableDescriptor,  // ElectricalCableDescriptor cable
                    Eln.arcFurnaceRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:arc_furnace")
            }
            /*

            To be released at a later date. Needs a bit of code in the backend, and there's a rendering bug and some other
            minor issues to be resolved.

            {
                subId = 1;
                name = TR_NAME(Type.NONE, "800V Arc Furnace");

                ArcFurnaceDescriptor desc = new ArcFurnaceDescriptor(name, obj.getObj("arcfurnace"));

                transparentNodeItem.addDescriptor(subId + (id << 6), desc);
                //desc.setRunningSound("eln:arc_furnace");

            }
            */
        }

        private fun registerCompressor(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Compressor")
                val desc = CompressorDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("compressora"),
                    Eln.LVU, 200.0,  // double nominalU,double nominalP,
                    Eln.LVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor cable
                    Eln.compressorRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:compressor_run")
                desc.setEndSound(SoundCommand("eln:compressor_end"))
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Compressor")
                val desc = CompressorDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("compressorb"),
                    Eln.MVU, 2000.0,  // double nominalU,double nominalP,
                    Eln.MVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable
                    Eln.compressorRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:compressor_run")
                desc.setEndSound(SoundCommand("eln:compressor_end"))
            }
        }

        private fun registerMagnetizer(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Magnetizer")
                val desc = MagnetizerDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("magnetizera"),
                    Eln.LVU, 200.0,  // double nominalU,double nominalP,
                    Eln.LVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor cable
                    Eln.magnetiserRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:Motor")
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Magnetizer")
                val desc = MagnetizerDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("magnetizerb"),
                    Eln.MVU, 2000.0,  // double nominalU,double nominalP,
                    Eln.MVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable
                    Eln.magnetiserRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:Motor")
            }
        }

        private fun registerPlateMachine(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Plate Machine")
                val desc = PlateMachineDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("platemachinea"),
                    Eln.LVU, 200.0,  // double nominalU,double nominalP,
                    Eln.LVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor cable
                    Eln.plateMachineRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:plate_machine")
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Plate Machine")
                val desc = PlateMachineDescriptor(
                    name,  // String name,
                    Eln.obj.getObj("platemachineb"),
                    Eln.MVU, 2000.0,  // double nominalU,double nominalP,
                    Eln.MVU * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable
                    Eln.plateMachineRecipes)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.setRunningSound("eln:plate_machine")
            }
        }

        private fun registerEggIncubator(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Egg Incubator")
                val desc = EggIncubatorDescriptor(
                    name, Eln.obj.getObj("eggincubator"),
                    Eln.lowVoltageCableDescriptor,
                    Eln.LVU, 50.0)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerAutoMiner(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Auto Miner")
                val powerLoad = arrayOfNulls<Coordonate>(2)
                powerLoad[0] = Coordonate(-2, -1, 1, 0)
                powerLoad[1] = Coordonate(-2, -1, -1, 0)
                val lightCoord = Coordonate(-3, 0, 0, 0)
                val miningCoord = Coordonate(-1, 0, 1, 0)
                val desc = AutoMinerDescriptor(name,
                    Eln.obj.getObj("AutoMiner"),
                    powerLoad, lightCoord, miningCoord,
                    2, 1, 0,
                    Eln.highVoltageCableDescriptor,
                    1.0, 50.0 // double pipeRemoveTime,double pipeRemoveEnergy
                )
                val ghostGroup = GhostGroup()
                ghostGroup.addRectangle(-2, -1, -1, 0, -1, 1)
                ghostGroup.addRectangle(1, 1, -1, 0, 1, 1)
                ghostGroup.addRectangle(1, 1, -1, 0, -1, -1)
                ghostGroup.addElement(1, 0, 0)
                ghostGroup.addElement(0, 0, 1)
                ghostGroup.addElement(0, 1, 0)
                ghostGroup.addElement(0, 0, -1)
                ghostGroup.removeElement(-1, -1, 0)
                desc.setGhostGroup(ghostGroup)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerSolarPanel(id: Int) {
            var subId: Int
            var ghostGroup: GhostGroup
            var name: String
            val LVSolarU = 59.0
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Solar Panel")
                ghostGroup = GhostGroup()
                val desc = SolarPanelDescriptor(name,  // String
                    // name,
                    Eln.obj.getObj("smallsolarpannel"), null,
                    ghostGroup, 0, 1, 0,  // GhostGroup ghostGroup, int
                    // solarOffsetX,int solarOffsetY,int
                    // solarOffsetZ,
                    // FunctionTable solarIfSBase,
                    null, LVSolarU / 4, 65.0 * Eln.solarPanelPowerFactor,  // double electricalUmax,double
                    // electricalPmax,
                    0.01,  // ,double electricalDropFactor
                    Math.PI / 2, Math.PI / 2 // alphaMin alphaMax
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Rotating Solar Panel")
                ghostGroup = GhostGroup()
                val desc = SolarPanelDescriptor(name,  // String
                    // name,
                    Eln.obj.getObj("smallsolarpannelrot"), Eln.lowVoltageCableDescriptor.render,
                    ghostGroup, 0, 1, 0,  // GhostGroup ghostGroup, int
                    // solarOffsetX,int solarOffsetY,int
                    // solarOffsetZ,
                    // FunctionTable solarIfSBase,
                    null, LVSolarU / 4, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor,  // double electricalUmax,double
                    // electricalPmax,
                    0.01,  // ,double electricalDropFactor
                    Math.PI / 4, Math.PI / 4 * 3 // alphaMin alphaMax
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 3
                name = I18N.TR_NAME(I18N.Type.NONE, "2x3 Solar Panel")
                val groundCoordinate = Coordonate(1, 0, 0, 0)
                ghostGroup = GhostGroup()
                ghostGroup.addRectangle(0, 1, 0, 0, -1, 1)
                ghostGroup.removeElement(0, 0, 0)
                val desc = SolarPanelDescriptor(name,
                    Eln.obj.getObj("bigSolarPanel"), Eln.meduimVoltageCableDescriptor.render,
                    ghostGroup, 1, 1, 0,
                    groundCoordinate,
                    LVSolarU * 2, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor * 8,
                    0.01,
                    Math.PI / 2, Math.PI / 2
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "2x3 Rotating Solar Panel")
                val groundCoordinate = Coordonate(1, 0, 0, 0)
                ghostGroup = GhostGroup()
                ghostGroup.addRectangle(0, 1, 0, 0, -1, 1)
                ghostGroup.removeElement(0, 0, 0)
                val desc = SolarPanelDescriptor(name,
                    Eln.obj.getObj("bigSolarPanelrot"), Eln.meduimVoltageCableDescriptor.render,
                    ghostGroup, 1, 1, 1,
                    groundCoordinate,
                    LVSolarU * 2, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor * 8,
                    0.01,
                    Math.PI / 8 * 3, Math.PI / 8 * 5
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerWindTurbine(id: Int) {
            var subId: Int
            var name: String
            val PfW = FunctionTable(doubleArrayOf(0.0, 0.1, 0.3, 0.5, 0.8, 1.0, 1.1, 1.15, 1.2),
                8.0 / 5.0)
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Wind Turbine")
                val desc = WindTurbineDescriptor(
                    name, Eln.obj.getObj("WindTurbineMini"),  // name,Obj3D obj,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable,
                    PfW,  // PfW
                    160 * Eln.windTurbinePowerFactor, 10.0,  // double nominalPower,double nominalWind,
                    Eln.LVU * 1.18, 22.0,  // double maxVoltage, double maxWind,
                    3,  // int offY,
                    7, 2, 2,  // int rayX,int rayY,int rayZ,
                    2, 0.07,  // int blockMalusMinCount,double blockMalus
                    "eln:WINDTURBINE_BIG_SF", 1f // Use the wind turbine sound and play at normal volume (1 => 100%)
                )
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, -1)
                g.addElement(0, 2, 1)
                g.addElement(0, 3, -1)
                g.addElement(0, 3, 1)
                g.addRectangle(0, 0, 1, 3, 0, 0)
                desc.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }

            /*{ //TODO Work on the large wind turbine
                subId = 1;
                name = TR_NAME(Type.NONE, "Large Wind Turbine");

                WindTurbineDescriptor desc = new WindTurbineDescriptor(
                    name, obj.getObj("WindTurbineMini"), // name,Obj3D obj,
                    lowVoltageCableDescriptor,// ElectricalCableDescriptor
                    // cable,
                    PfW,// PfW
                    160 * windTurbinePowerFactor, 10,// double nominalPower,double nominalWind,
                    LVU * 1.18, 22,// double maxVoltage, double maxWind,
                    3,// int offY,
                    7, 2, 2,// int rayX,int rayY,int rayZ,
                    2, 0.07,// int blockMalusMinCount,double blockMalus
                    "eln:WINDTURBINE_BIG_SF", 1f // Use the wind turbine sound and play at normal volume (1 => 100%)
                );

                GhostGroup g = new GhostGroup();
                g.addElement(0, 1, 0);
                g.addElement(0, 2, -1);
                g.addElement(0, 2, 1);
                g.addElement(0, 3, -1);
                g.addElement(0, 3, 1);
                g.addRectangle(0, 0, 1, 3, 0, 0);
                desc.setGhostGroup(g);
                transparentNodeItem.addDescriptor(subId + (id << 6), desc);
            } */run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Water Turbine")
                val waterCoord = Coordonate(1, -1, 0, 0)
                val desc = WaterTurbineDescriptor(
                    name, Eln.obj.getObj("SmallWaterWheel"),  // name,Obj3D obj,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor
                    30 * Eln.waterTurbinePowerFactor,
                    Eln.LVU * 1.18,
                    waterCoord,
                    "eln:water_turbine", 1f
                )
                val g = GhostGroup()
                g.addRectangle(1, 1, 0, 1, -1, 1)
                desc.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerThermalDissipatorPassiveAndActive(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Passive Thermal Dissipator")
                val desc = ThermalDissipatorPassiveDescriptor(
                    name,
                    Eln.obj.getObj("passivethermaldissipatora"),
                    200.0, (-100).toDouble(),  // double warmLimit,double coolLimit,
                    250.0, 30.0,  // double nominalP,double nominalT,
                    10.0, 1.0 // double nominalTao,double nominalConnectionDrop
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 32
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Active Thermal Dissipator")
                val desc = ThermalDissipatorActiveDescriptor(
                    name,
                    Eln.obj.getObj("activethermaldissipatora"),
                    Eln.LVU, 50.0,  // double nominalElectricalU,double
                    // electricalNominalP,
                    800.0,  // double nominalElectricalCoolingPower,
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cableDescriptor,
                    130.0, (-100).toDouble(),  // double warmLimit,double coolLimit,
                    200.0, 30.0,  // double nominalP,double nominalT,
                    10.0, 1.0 // double nominalTao,double nominalConnectionDrop
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 34
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Active Thermal Dissipator")
                val desc = ThermalDissipatorActiveDescriptor(
                    name,
                    Eln.obj.getObj("200vactivethermaldissipatora"),
                    Eln.MVU, 60.0,  // double nominalElectricalU,double
                    // electricalNominalP,
                    1200.0,  // double nominalElectricalCoolingPower,
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cableDescriptor,
                    130.0, (-100).toDouble(),  // double warmLimit,double coolLimit,
                    200.0, 30.0,  // double nominalP,double nominalT,
                    10.0, 1.0 // double nominalTao,double nominalConnectionDrop
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerTransparentNodeMisc(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Experimental Transporter")
                val powerLoad = arrayOfNulls<Coordonate>(2)
                powerLoad[0] = Coordonate(-1, 0, 1, 0)
                powerLoad[1] = Coordonate(-1, 0, -1, 0)
                val doorOpen = GhostGroup()
                doorOpen.addRectangle(-4, -3, 2, 2, 0, 0)
                val doorClose = GhostGroup()
                doorClose.addRectangle(-2, -2, 0, 1, 0, 0)
                val desc = TeleporterDescriptor(
                    name, Eln.obj.getObj("Transporter"),
                    Eln.highVoltageCableDescriptor,
                    Coordonate(-1, 0, 0, 0), Coordonate(-1, 1, 0, 0),
                    2,  // int areaH
                    powerLoad,
                    doorOpen, doorClose
                )
                desc.setChargeSound("eln:transporter", 0.5f)
                val g = GhostGroup()
                g.addRectangle(-2, 0, 0, 1, -1, -1)
                g.addRectangle(-2, 0, 0, 1, 1, 1)
                g.addRectangle(-4, -1, 2, 2, 0, 0)
                g.addElement(0, 1, 0)
                //g.addElement(0, 2, 0);
                g.addElement(-1, 0, 0, Eln.ghostBlock, GhostBlock.tFloor)
                /*	g.addElement(1, 0, 0,ghostBlock,ghostBlock.tLadder);
        g.addElement(1, 1, 0,ghostBlock,ghostBlock.tLadder);
        g.addElement(1, 2, 0,ghostBlock,ghostBlock.tLadder);*/g.addRectangle(-3, -3, 0, 1, -1, -1)
                g.addRectangle(-3, -3, 0, 1, 1, 1)
                // g.addElement(-4, 0, -1);
                // g.addElement(-4, 0, 1);
                desc.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }

            /*if (Other.ccLoaded && ComputerProbeEnable) {
                subId = 4;
                name = "ComputerCraft Probe";

                ComputerCraftIoDescriptor desc = new ComputerCraftIoDescriptor(
                        name,
                        obj.getObj("passivethermaldissipatora")

                        );

                transparentNodeItem.addWithoutRegistry(subId + (id << 6), desc);
            }*/
        }

        private fun registerTurret(id: Int) {
            run {
                val subId = 0
                val name = I18N.TR_NAME(I18N.Type.NONE, "800V Defence Turret")
                val desc = TurretDescriptor(name, "Turret")
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerFuelGenerator(id: Int) {
            var subId: Int
            run {
                subId = 1
                val descriptor = FuelGeneratorDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Fuel Generator"), Eln.obj.getObj("FuelGenerator50V"),
                    Eln.lowVoltageCableDescriptor, Eln.fuelGeneratorPowerFactor * 1200, Eln.LVU * 1.25, Eln.fuelGeneratorTankCapacity)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
            run {
                subId = 2
                val descriptor = FuelGeneratorDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Fuel Generator"), Eln.obj.getObj("FuelGenerator200V"),
                    Eln.meduimVoltageCableDescriptor, Eln.fuelGeneratorPowerFactor * 6000, Eln.MVU * 1.25,
                    Eln.fuelGeneratorTankCapacity)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
        }

        private fun registerGridDevices(id: Int) {
            var subId: Int
            run {
                subId = 3
                val descriptor = GridTransformerDescriptor("Grid DC-DC Converter", Eln.obj.getObj("GridConverter"), "textures/wire.png", Eln.highVoltageCableDescriptor)
                val g = GhostGroup()
                g.addElement(1, 0, 0)
                g.addElement(0, 0, -1)
                g.addElement(1, 0, -1)
                g.addElement(1, 1, 0)
                g.addElement(0, 1, 0)
                g.addElement(1, 1, -1)
                g.addElement(0, 1, -1)
                descriptor.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
            run {
                subId = 4
                val descriptor = ElectricalPoleDescriptor(
                    "Utility Pole",
                    Eln.obj.getObj("UtilityPole"),
                    "textures/wire.png",
                    Eln.highVoltageCableDescriptor,
                    false,
                    40,
                    51200.0)
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, 0)
                g.addElement(0, 3, 0)
                //g.addRectangle(-1, 1, 3, 4, -1, 1);
                descriptor.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
            run {
                subId = 5
                val descriptor = ElectricalPoleDescriptor(
                    "Utility Pole w/DC-DC Converter",
                    Eln.obj.getObj("UtilityPole"),
                    "textures/wire.png",
                    Eln.highVoltageCableDescriptor,
                    true,
                    40,
                    12800.0)
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, 0)
                g.addElement(0, 3, 0)
                //g.addRectangle(-1, 1, 3, 4, -1, 1);
                descriptor.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
            run {
                subId = 6
                val descriptor = ElectricalPoleDescriptor("Transmission Tower",
                    Eln.obj.getObj("TransmissionTower"),
                    "textures/wire.png",
                    Eln.highVoltageCableDescriptor,
                    false,
                    96,
                    51200.0)
                val g = GhostGroup()
                g.addRectangle(-1, 1, 0, 0, -1, 1)
                g.addRectangle(0, 0, 1, 8, 0, 0)
                g.removeElement(0, 0, 0)
                descriptor.setGhostGroup(g)
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), descriptor)
            }
            run { subId = 7 }
        }

        private fun registerFestive(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Christmas Tree")
                val desc = ChristmasTreeDescriptor(name, Eln.obj.getObj("Christmas_Tree"))
                if (Eln.enableFestivities) {
                    Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                } else {
                    Eln.transparentNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
                }
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Holiday Candle")
                val desc = HolidayCandleDescriptor(name, Eln.obj.getObj("Candle_Light"))
                if (Eln.enableFestivities) {
                    Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                } else {
                    Eln.transparentNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
                }
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "String Lights")
                val desc = StringLightsDescriptor(name, Eln.obj.getObj("Christmas_Lights"))
                if (Eln.enableFestivities) {
                    Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
                } else {
                    Eln.transparentNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
                }
            }
        }
    }
}
