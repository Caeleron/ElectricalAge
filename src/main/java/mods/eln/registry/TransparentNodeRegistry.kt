package mods.eln.registry

import mods.eln.Eln
import mods.eln.Eln.obj
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
import mods.eln.misc.VoltageTier
import mods.eln.misc.series.SeriesMap
import mods.eln.node.transparent.TransparentNodeDescriptor
import mods.eln.sim.ThermalLoadInitializer
import mods.eln.sim.ThermalLoadInitializerByPowerDrop
import mods.eln.sound.SoundCommand
import mods.eln.transparentnode.FuelGeneratorDescriptor
import mods.eln.transparentnode.FuelHeatFurnaceDescriptor
import mods.eln.transparentnode.LargeRheostatDescriptor
import mods.eln.transparentnode.NixieTubeDescriptor
import mods.eln.transparentnode.autominer.AutoMinerDescriptor
import mods.eln.transparentnode.battery.BatteryDescriptor
import mods.eln.transparentnode.dcdc.DcDcDescriptor
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
import mods.eln.transparentnode.solarpanel.SolarPanelDescriptor
import mods.eln.transparentnode.teleporter.TeleporterDescriptor
import mods.eln.transparentnode.thermaldissipatoractive.ThermalDissipatorActiveDescriptor
import mods.eln.transparentnode.thermaldissipatorpassive.ThermalDissipatorPassiveDescriptor
import mods.eln.transparentnode.turbine.TurbineDescriptor
import mods.eln.transparentnode.turret.TurretDescriptor
import mods.eln.transparentnode.variabledcdc.VariableDcDcDescriptor
import mods.eln.transparentnode.windturbine.WindTurbineDescriptor
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class TransparentNodeRegistry {
    companion object {

        enum class TNID (val id: Int) {
            TRANSFORMER(2),
            HEAT_FURNACE(3),
            TURBINE(4),
            ELECTRICAL_ANTENNA(5),
            BATTERY(6),
            ELECTRICAL_FURNACE(7),
            MACERATOR(8),
            ARC_FURNACE(9),
            COMPRESSOR(10),
            MAGNETIZER(11),
            PLATE_MACHINE(12),
            EGG_INCUBATOR(13),
            AUTO_MINER(14),
            SOLAR_PANEL(15),
            WIND_TURBINE(16),
            THERMAL_DISSIPATORS(17),
            MISC(18),
            TURRET(19),
            FUEL_GENERATOR(20),
            GRID_DEVICES(21),
            FESTIVE(22),
            NIXIE(23)
        }

        fun register() {
            registerTransformer(TNID.TRANSFORMER.id)
            registerHeatFurnace(TNID.HEAT_FURNACE.id)
            registerTurbine(TNID.TURBINE.id)
            registerElectricalAntenna(TNID.ELECTRICAL_ANTENNA.id)
            registerBattery(TNID.BATTERY.id)
            registerElectricalFurnace(TNID.ELECTRICAL_FURNACE.id)
            registerMacerator(TNID.MACERATOR.id)
            registerArcFurnace(TNID.ARC_FURNACE.id)
            registerCompressor(TNID.COMPRESSOR.id)
            registerMagnetizer(TNID.MAGNETIZER.id)
            registerPlateMachine(TNID.PLATE_MACHINE.id)
            registerEggIncubator(TNID.EGG_INCUBATOR.id)
            registerAutoMiner(TNID.AUTO_MINER.id)
            registerSolarPanel(TNID.SOLAR_PANEL.id)
            registerWindTurbine(TNID.WIND_TURBINE.id)
            registerThermalDissipator(TNID.THERMAL_DISSIPATORS.id)
            registerTransparentNodeMisc(TNID.MISC.id)
            registerTurret(TNID.TURRET.id)
            registerFuelGenerator(TNID.FUEL_GENERATOR.id)
            registerGridDevices(TNID.GRID_DEVICES.id)
            //registerFloodlight(68);
            registerFestive(TNID.FESTIVE.id)
            registerNixieTube(TNID.NIXIE.id)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerTransparentNode(group: Int, subId: Int, descriptor: TransparentNodeDescriptor) {
            Eln.transparentNodeItem.addDescriptor(subId + (group shl 6), descriptor)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerHiddenTransparentNode(group: Int, subId: Int, descriptor: TransparentNodeDescriptor) {
            Eln.transparentNodeItem.addWithoutRegistry(subId + (group shl 6), descriptor)
        }

        /*
        private fun registerFloodlight(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Basic Floodlight")
                val desc = BasicFloodlightDescriptor(name, obj.getObj("Floodlight"))
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Motorized Floodlight")
                val desc = MotorizedFloodlightDescriptor(name, obj.getObj("FloodlightMotor"))
                registerTransparentNode(id, 1, desc)
            }
        }
         */

        private fun registerTransformer(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Variable DC-DC Converter")
                val desc = VariableDcDcDescriptor(name, obj.getObj("variabledcdc"),
                    obj.getObj("feromagneticcorea"), obj.getObj("transformatorCase"))
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "DC-DC Converter")
                val desc = DcDcDescriptor(name, obj.getObj("transformator"),
                    obj.getObj("feromagneticcorea"), obj.getObj("transformatorCase"), 0.5f)
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerHeatFurnace(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Stone Heat Furnace")
                val desc = HeatFurnaceDescriptor(name,
                    "stonefurnace", 4000.0,
                    Utils.coalEnergyReference * 2 / 3,
                    8, 500.0,
                    ThermalLoadInitializerByPowerDrop(780.0, (-100).toDouble(), 10.0, 2.0)
                )
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Fuel Heat Furnace")
                val desc = FuelHeatFurnaceDescriptor(name,
                    obj.getObj("FuelHeater"), ThermalLoadInitializerByPowerDrop(780.0, (-100).toDouble(), 10.0, 2.0))
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerTurbine(id: Int) {
            var name: String
            val temperatureToVoltage = FunctionTable(doubleArrayOf(0.0, 0.1, 0.85,
                1.0, 1.1, 1.15, 1.18, 1.19, 1.25), 8.0 / 5.0)
            val powerOutputMap = FunctionTable(doubleArrayOf(0.0, 0.2,
                0.4, 0.6, 0.8, 1.0, 1.3, 1.8, 2.7), 8.0 / 5.0)
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Turbine")
                val resistanceFactor = 0.1
                val nominalU = VoltageTier.LOW.voltage
                val nominalP: Double = 1000 * Eln.heatTurbinePowerFactor // it was 300 before
                val nominalDeltaT = 250.0
                val desc = TurbineDescriptor(name, "turbineb", Eln.smallInsulationLowCurrentRender,
                    temperatureToVoltage.duplicate(nominalDeltaT, nominalU), powerOutputMap.duplicate(nominalP, nominalP), nominalDeltaT,
                    nominalU, nominalP, nominalP / 40, Eln.smallInsulationLowCurrentCopperCable.electricalRs * resistanceFactor, 25.0,
                    nominalDeltaT / 40, nominalP / (nominalU / 25), "eln:heat_turbine_50v")
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Turbine")
                val resistanceFactor = 0.10
                val nominalU = VoltageTier.LOW_HOUSEHOLD.voltage
                val nominalP: Double = 2000 * Eln.heatTurbinePowerFactor
                val nominalDeltaT = 350.0
                val desc = TurbineDescriptor(name, "turbinebblue", Eln.smallInsulationMediumCurrentRender,
                    temperatureToVoltage.duplicate(nominalDeltaT, nominalU), powerOutputMap.duplicate(nominalP, nominalP), nominalDeltaT,
                    nominalU, nominalP, nominalP / 40, Eln.smallInsulationMediumCurrentCopperCable.electricalRs * resistanceFactor, 50.0,
                    nominalDeltaT / 40, nominalP / (nominalU / 25), "eln:heat_turbine_200v")
                registerTransparentNode(id, 1, desc)
            }
            run {
                val desc = SteamTurbineDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Steam Turbine"),
                    obj.getObj("Turbine")
                )
                registerTransparentNode(id, 2, desc)
            }
            run {
                val nominalRads = 200f
                val nominalU = 480f
                val nominalP = 4000f
                val desc = GeneratorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Generator"),
                    obj.getObj("Generator"),
                    nominalRads, nominalU,
                    nominalP / (nominalU / 25),
                    nominalP,
                    Eln.sixNodeThermalLoadInitializer.copy()
                )
                registerTransparentNode(id, 3, desc)
            }
            run {
                val desc = GasTurbineDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Gas Turbine"),
                    obj.getObj("GasTurbine")
                )
                registerTransparentNode(id, 4, desc)
            }
            run {
                val desc = StraightJointDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Joint"),
                    obj.getObj("StraightJoint"))
                registerTransparentNode(id, 5, desc)
            }
            run {
                val desc = VerticalHubDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Joint hub"),
                    obj.getObj("VerticalHub"))
                registerTransparentNode(id, 6, desc)
            }
            run {
                val desc = FlywheelDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Flywheel"),
                    obj.getObj("Flywheel"))
                registerTransparentNode(id, 7, desc)
            }
            run {
                val desc = TachometerDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Tachometer"),
                    obj.getObj("Tachometer"))
                registerTransparentNode(id, 8, desc)
            }
            run {
                val nominalRads = 200f
                val nominalU = 480f
                val nominalP = 1200f
                val desc = MotorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Shaft Motor"),
                    obj.getObj("Motor"),
                    Eln.mediumInsulationMediumCurrentCopperCable,
                    nominalRads,
                    nominalU,
                    nominalP,
                    25.0f * nominalP / nominalU,
                    25.0f * nominalP / nominalU,
                    Eln.sixNodeThermalLoadInitializer.copy()
                )
                registerTransparentNode(id, 9, desc)
            }
            run {
                val desc = ClutchDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Clutch"),
                    obj.getObj("Clutch")
                )
                registerTransparentNode(id, 10, desc)
            }
            run {
                val desc = FixedShaftDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Fixed Shaft"),
                    obj.getObj("FixedShaft")
                )
                registerTransparentNode(id, 11, desc)
            }
            run {
                val desc = RotaryMotorDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Rotary Motor"),
                    obj.getObj("Starter_Motor")
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
                registerTransparentNode(id, 12, desc)
            }
        }

        private fun registerElectricalAntenna(id: Int) {
            var name: String
            run {
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Power Transmitter Antenna")
                val power = 250.0
                desc = ElectricalAntennaTxDescriptor(name,
                    obj.getObj("lowpowertransmitterantenna"), 200,
                    0.9, 0.7,
                    VoltageTier.LOW.voltage, power,
                    VoltageTier.LOW.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationLowCurrentCopperCable)
                registerTransparentNode(id, 0, desc)
            }
            run {
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Power Receiver Antenna")
                val power = 250.0
                desc = ElectricalAntennaRxDescriptor(name,
                    obj.getObj("lowpowerreceiverantenna"), VoltageTier.LOW.voltage, power,
                    VoltageTier.LOW.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationLowCurrentCopperCable)
                registerTransparentNode(id, 1, desc)
            }
            run {
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Power Transmitter Antenna")
                val power = 1000.0
                desc = ElectricalAntennaTxDescriptor(name,
                    obj.getObj("lowpowertransmitterantenna"), 250,
                    0.9, 0.75,
                    VoltageTier.LOW_HOUSEHOLD.voltage, power,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationMediumCurrentCopperCable)
                registerTransparentNode(id, 2, desc)
            }
            run {
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Power Receiver Antenna")
                val power = 1000.0
                desc = ElectricalAntennaRxDescriptor(name,
                    obj.getObj("lowpowerreceiverantenna"), VoltageTier.LOW_HOUSEHOLD.voltage, power,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationMediumCurrentCopperCable)
                registerTransparentNode(id, 3, desc)
            }
            run {
                val desc: ElectricalAntennaTxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "High Power Transmitter Antenna")
                val power = 2000.0
                desc = ElectricalAntennaTxDescriptor(name,
                    obj.getObj("lowpowertransmitterantenna"), 300,
                    0.95, 0.8,
                    VoltageTier.HIGH_HOUSEHOLD.voltage, power,
                    VoltageTier.HIGH_HOUSEHOLD.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationHighCurrentCopperCable)
                registerTransparentNode(id, 4, desc)
            }
            run {
                val desc: ElectricalAntennaRxDescriptor
                name = I18N.TR_NAME(I18N.Type.NONE, "High Power Receiver Antenna")
                val power = 2000.0
                desc = ElectricalAntennaRxDescriptor(name,
                    obj.getObj("lowpowerreceiverantenna"), VoltageTier.HIGH_HOUSEHOLD.voltage, power,
                    VoltageTier.HIGH_HOUSEHOLD.voltage * 1.3, power * 1.3,
                    Eln.smallInsulationHighCurrentCopperCable)
                registerTransparentNode(id, 5, desc)
            }
        }

        private fun registerBattery(id: Int) {
            var name: String
            val heatTIme = 30.0
            val voltageFunctionTable = doubleArrayOf(0.000, 0.9, 1.0, 1.025, 1.04, 1.05,
                2.0)
            val voltageFunction = FunctionTable(voltageFunctionTable,
                6.0 / 5)
            Utils.printFunction(voltageFunction, -0.2, 1.2, 0.1)
            val stdDischargeTime = 60 * 8.toDouble()
            val stdU = VoltageTier.LOW.voltage
            val stdP = 2_000.0
            val stdEfficiency = 1.0 - 2.0 / 50.0
            Eln.batteryVoltageFunctionTable = voltageFunction
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Cost Oriented Battery")
                val desc = BatteryDescriptor(name, "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable,
                    0.5,
                    true, true,
                    voltageFunction,
                    stdU,
                    stdP * 1.2,
                    0.00,
                    stdP,
                    stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),
                    "Cheap battery"
                )
                desc.setRenderSpec("lowcost")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Capacity Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 0.5, true, true, voltageFunction,
                    stdU / 4, stdP / 2 * 1.2, 0.000,
                    stdP / 2, stdDischargeTime * 8 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),
                    "the battery"
                )
                desc.setRenderSpec("capacity")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 0.5, true, true, voltageFunction, stdU * 4,
                    stdP * 1.2, 0.000,
                    stdP, stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),
                    "the battery"
                )
                desc.setRenderSpec("highvoltage")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Current Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 0.5, true, true, voltageFunction, stdU,
                    stdP * 1.2 * 4, 0.000,
                    stdP * 4, stdDischargeTime / 6 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife,
                    heatTIme, 60.0, (-100).toDouble(),
                    "the battery"
                )
                desc.setRenderSpec("current")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Life Oriented Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 0.5, true, false, voltageFunction, stdU,
                    stdP * 1.2, 0.000,
                    stdP, stdDischargeTime * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,
                    heatTIme, 60.0, (-100).toDouble(),
                    "the battery"
                )
                desc.setRenderSpec("life")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 4, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Single-use Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 1.0, false, false, voltageFunction, stdU,
                    stdP * 1.2 * 2, 0.000,
                    stdP * 2, stdDischargeTime / 4 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,
                    heatTIme, 60.0, (-100).toDouble(),
                    "the battery"
                )
                desc.setRenderSpec("coal")
                registerTransparentNode(id, 5, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Experimental Battery")
                val desc = BatteryDescriptor(name,
                    "BatteryBig", Eln.smallInsulationMediumCurrentCopperCable, 0.5, true, false, voltageFunction, stdU * 2,
                    stdP * 1.2 * 8, 0.025,
                    stdP * 8, stdDischargeTime / 4 * Eln.batteryCapacityFactor, stdEfficiency, Eln.stdBatteryHalfLife * 8,
                    heatTIme, 60.0, (-100).toDouble(),
                    "You were unable to fix the power leaking problem, though."
                )
                desc.setRenderSpec("highvoltage")
                desc.setCurrentDrop(desc.electricalU * 1.2, desc.electricalStdP * 1.0)
                registerTransparentNode(id, 6, desc)
            }
        }

        private fun registerElectricalFurnace(id: Int) {
            var name: String
            Eln.furnaceList.add(ItemStack(Blocks.furnace))
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Furnace")
                val powerFromTemperatureTable = doubleArrayOf(0.0, 20.0, 40.0, 80.0, 160.0, 240.0, 360.0, 540.0, 756.0, 1058.4, 1481.76)
                val thermalPlostfTTable = DoubleArray(powerFromTemperatureTable.size)
                for (idx in thermalPlostfTTable.indices) {
                    thermalPlostfTTable[idx] = (powerFromTemperatureTable[idx]
                        * Math.pow((idx + 1.0) / thermalPlostfTTable.size, 2.0)
                        * 2)
                }
                val powerFromTemperature = FunctionTableYProtect(powerFromTemperatureTable,
                    800.0, 0.0, 100000.0)
                val thermalPlostfT = FunctionTableYProtect(
                    thermalPlostfTTable, 800.0, 0.001, 10000000.0)
                val desc = ElectricalFurnaceDescriptor(
                    name, powerFromTemperature, thermalPlostfT,
                    40.0
                )
                Eln.electricalFurnace = desc
                registerTransparentNode(id, 0, desc)
                Eln.furnaceList.add(desc.newItemStack())
            }
        }

        private fun registerMacerator(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Macerator")
                val desc = MaceratorDescriptor(name,
                    "maceratora", VoltageTier.LOW.voltage, 200.0,  // double nominalU,double nominalP,
                    VoltageTier.LOW.voltage * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.smallInsulationLowCurrentCopperCable,
                    Eln.maceratorRecipes)
                desc.setRunningSound("eln:macerator")
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Macerator")
                val desc = MaceratorDescriptor(name,
                    "maceratorb", VoltageTier.LOW_HOUSEHOLD.voltage, 2000.0,  // double nominalU,double nominalP,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.25,  // double maximalU,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),  // thermal,
                    Eln.smallInsulationMediumCurrentCopperCable,
                    // cable
                    Eln.maceratorRecipes)
                desc.setRunningSound("eln:macerator")
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerArcFurnace(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Old 800V Arc Furnace")
                val desc = OldArcFurnaceDescriptor(
                    name,
                    obj.getObj("arcfurnaceold"),
                    VoltageTier.HIGH_HOUSEHOLD.voltage, 10000.0,
                    VoltageTier.HIGH_HOUSEHOLD.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationMediumCurrentCopperCable,
                    Eln.arcFurnaceRecipes)
                desc.setRunningSound("eln:arc_furnace")
                registerTransparentNode(id, 0, desc)
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
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Compressor")
                val desc = CompressorDescriptor(
                    name,
                    obj.getObj("compressora"),
                    VoltageTier.LOW.voltage, 200.0,
                    VoltageTier.LOW.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationLowCurrentCopperCable,
                    Eln.compressorRecipes)
                desc.setRunningSound("eln:compressor_run")
                desc.setEndSound(SoundCommand("eln:compressor_end"))
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Compressor")
                val desc = CompressorDescriptor(
                    name,
                    obj.getObj("compressorb"),
                    VoltageTier.LOW_HOUSEHOLD.voltage, 2000.0,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationMediumCurrentCopperCable,
                    Eln.compressorRecipes)
                desc.setRunningSound("eln:compressor_run")
                desc.setEndSound(SoundCommand("eln:compressor_end"))
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerMagnetizer(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Magnetizer")
                val desc = MagnetizerDescriptor(
                    name,
                    obj.getObj("magnetizera"),
                    VoltageTier.LOW.voltage, 200.0,
                    VoltageTier.LOW.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationLowCurrentCopperCable,
                    Eln.magnetiserRecipes)
                desc.setRunningSound("eln:Motor")
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Magnetizer")
                val desc = MagnetizerDescriptor(
                    name,
                    obj.getObj("magnetizerb"),
                    VoltageTier.LOW_HOUSEHOLD.voltage, 2000.0,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationMediumCurrentCopperCable,
                    Eln.magnetiserRecipes)
                desc.setRunningSound("eln:Motor")
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerPlateMachine(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Plate Machine")
                val desc = PlateMachineDescriptor(
                    name,
                    obj.getObj("platemachinea"),
                    VoltageTier.LOW.voltage, 200.0,
                    VoltageTier.LOW.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationLowCurrentCopperCable,
                    Eln.plateMachineRecipes)
                desc.setRunningSound("eln:plate_machine")
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Plate Machine")
                val desc = PlateMachineDescriptor(
                    name,
                    obj.getObj("platemachineb"),
                    VoltageTier.LOW_HOUSEHOLD.voltage, 2000.0,
                    VoltageTier.LOW_HOUSEHOLD.voltage * 1.25,
                    ThermalLoadInitializer(80.0, (-100).toDouble(), 10.0, 100000.0),
                    Eln.smallInsulationMediumCurrentCopperCable,
                    Eln.plateMachineRecipes)
                desc.setRunningSound("eln:plate_machine")
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerEggIncubator(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Egg Incubator")
                val desc = EggIncubatorDescriptor(
                    name, obj.getObj("eggincubator"),
                    Eln.smallInsulationLowCurrentCopperCable,
                    VoltageTier.LOW.voltage, 50.0)
                registerTransparentNode(id, 0, desc)
            }
        }

        private fun registerAutoMiner(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Auto Miner")
                val powerLoad = arrayOfNulls<Coordonate>(2)
                powerLoad[0] = Coordonate(-2, -1, 1, 0)
                powerLoad[1] = Coordonate(-2, -1, -1, 0)
                val lightCoord = Coordonate(-3, 0, 0, 0)
                val miningCoord = Coordonate(-1, 0, 1, 0)
                val desc = AutoMinerDescriptor(name,
                    obj.getObj("AutoMiner"),
                    powerLoad, lightCoord, miningCoord,
                    2, 1, 0,
                    Eln.smallInsulationMediumCurrentCopperCable,
                    1.0, 50.0
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
                registerTransparentNode(id, 0, desc)
            }
        }

        private fun registerSolarPanel(id: Int) {
            var ghostGroup: GhostGroup
            var name: String
            val solarVoltage = 59.0
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Solar Panel")
                ghostGroup = GhostGroup()
                val desc = SolarPanelDescriptor(name,
                    obj.getObj("smallsolarpannel"), null,
                    ghostGroup, 0, 1, 0,
                    null, solarVoltage / 4, 65.0 * Eln.solarPanelPowerFactor,
                    0.01,
                    Math.PI / 2, Math.PI / 2
                )
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Rotating Solar Panel")
                ghostGroup = GhostGroup()
                val desc = SolarPanelDescriptor(name,
                    obj.getObj("smallsolarpannelrot"), Eln.smallInsulationLowCurrentRender,
                    ghostGroup, 0, 1, 0,
                    null, solarVoltage / 4, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor,
                    0.01,
                    Math.PI / 4, Math.PI / 4 * 3
                )
                registerTransparentNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "2x3 Solar Panel")
                val groundCoordinate = Coordonate(1, 0, 0, 0)
                ghostGroup = GhostGroup()
                ghostGroup.addRectangle(0, 1, 0, 0, -1, 1)
                ghostGroup.removeElement(0, 0, 0)
                val desc = SolarPanelDescriptor(name,
                    obj.getObj("bigSolarPanel"), Eln.smallInsulationMediumCurrentRender,
                    ghostGroup, 1, 1, 0,
                    groundCoordinate,
                    solarVoltage * 2, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor * 8,
                    0.01,
                    Math.PI / 2, Math.PI / 2
                )
                registerTransparentNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "2x3 Rotating Solar Panel")
                val groundCoordinate = Coordonate(1, 0, 0, 0)
                ghostGroup = GhostGroup()
                ghostGroup.addRectangle(0, 1, 0, 0, -1, 1)
                ghostGroup.removeElement(0, 0, 0)
                val desc = SolarPanelDescriptor(name,
                    obj.getObj("bigSolarPanelrot"), Eln.smallInsulationMediumCurrentRender,
                    ghostGroup, 1, 1, 1,
                    groundCoordinate,
                    solarVoltage * 2, Eln.solarPanelBasePower * Eln.solarPanelPowerFactor * 8,
                    0.01,
                    Math.PI / 8 * 3, Math.PI / 8 * 5
                )
                registerTransparentNode(id, 3, desc)
            }
        }

        private fun registerWindTurbine(id: Int) {
            var name: String
            val powerFromWind = FunctionTable(doubleArrayOf(0.0, 0.1, 0.3, 0.5, 0.8, 1.0, 1.1, 1.15, 1.2),
                8.0 / 5.0)
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wind Turbine")
                val desc = WindTurbineDescriptor(
                    name, obj.getObj("WindTurbineMini"),
                    Eln.smallInsulationLowCurrentCopperCable,
                    powerFromWind,
                    160 * Eln.windTurbinePowerFactor, 10.0,
                    VoltageTier.LOW.voltage * 1.18, 22.0,
                    3,
                    7, 2, 2,
                    2, 0.07,
                    "eln:WINDTURBINE_BIG_SF", 1f
                )
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, -1)
                g.addElement(0, 2, 1)
                g.addElement(0, 3, -1)
                g.addElement(0, 3, 1)
                g.addRectangle(0, 0, 1, 3, 0, 0)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 0, desc)
            }
        }

        private fun registerThermalDissipator(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Heatsink")
                val desc = ThermalDissipatorPassiveDescriptor(
                    name,
                    obj.getObj("passivethermaldissipatora"),
                    200.0, (-100).toDouble(),
                    250.0, 30.0,
                    10.0, 1.0
                )
                registerTransparentNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Heatsink with 12V Fan")
                val desc = ThermalDissipatorActiveDescriptor(
                    name,
                    obj.getObj("activethermaldissipatora"),
                    VoltageTier.LOW.voltage, 50.0,
                    800.0,
                    Eln.smallInsulationLowCurrentCopperCable,
                    130.0, (-100).toDouble(),
                    200.0, 30.0,
                    10.0, 1.0
                )
                registerTransparentNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Heatsink with 240V Fan")
                val desc = ThermalDissipatorActiveDescriptor(
                    name,
                    obj.getObj("200vactivethermaldissipatora"),
                    VoltageTier.LOW_HOUSEHOLD.voltage, 60.0,
                    1200.0,
                    Eln.smallInsulationMediumCurrentCopperCable,
                    130.0, (-100).toDouble(),
                    200.0, 30.0,
                    10.0, 1.0
                )
                registerTransparentNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Large Rheostat")
                val dissipator = ThermalDissipatorPassiveDescriptor(
                    name,
                    obj.getObj("LargeRheostat"),
                    1000.0, (-100).toDouble(),
                    4000.0, 800.0,
                    10.0, 1.0
                )
                val desc = LargeRheostatDescriptor(
                    name, dissipator, Eln.mediumInsulationMediumCurrentCopperCable, SeriesMap.newE12(0.0)
                )
                registerTransparentNode(id, 3, desc)
            }
        }

        private fun registerTransparentNodeMisc(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Experimental Transporter")
                val powerLoad = arrayOfNulls<Coordonate>(2)
                powerLoad[0] = Coordonate(-1, 0, 1, 0)
                powerLoad[1] = Coordonate(-1, 0, -1, 0)
                val doorOpen = GhostGroup()
                doorOpen.addRectangle(-4, -3, 2, 2, 0, 0)
                val doorClose = GhostGroup()
                doorClose.addRectangle(-2, -2, 0, 1, 0, 0)
                val desc = TeleporterDescriptor(
                    name, obj.getObj("Transporter"),
                    Eln.smallInsulationMediumCurrentCopperCable,
                    Coordonate(-1, 0, 0, 0), Coordonate(-1, 1, 0, 0),
                    2,
                    powerLoad,
                    doorOpen, doorClose
                )
                desc.setChargeSound("eln:transporter", 0.5f)
                val g = GhostGroup()
                g.addRectangle(-2, 0, 0, 1, -1, -1)
                g.addRectangle(-2, 0, 0, 1, 1, 1)
                g.addRectangle(-4, -1, 2, 2, 0, 0)
                g.addElement(0, 1, 0)
                g.addElement(-1, 0, 0, Eln.ghostBlock, GhostBlock.tFloor)
                g.addRectangle(-3, -3, 0, 1, -1, -1)
                g.addRectangle(-3, -3, 0, 1, 1, 1)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 0, desc)
            }
        }

        private fun registerTurret(id: Int) {
            run {
                val name = I18N.TR_NAME(I18N.Type.NONE, "Defense Turret")
                val desc = TurretDescriptor(name, "Turret")
                registerTransparentNode(id, 0, desc)
            }
        }

        private fun registerFuelGenerator(id: Int) {
            run {
                val desc = FuelGeneratorDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Fuel Generator"), obj.getObj("FuelGenerator50V"),
                    Eln.smallInsulationLowCurrentCopperCable, Eln.fuelGeneratorPowerFactor * 1200, VoltageTier.LOW.voltage * 1.25, Eln.fuelGeneratorTankCapacity)
                registerTransparentNode(id, 0, desc)
            }
            run {
                val desc = FuelGeneratorDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Fuel Generator"), obj.getObj("FuelGenerator200V"),
                    Eln.smallInsulationMediumCurrentCopperCable, Eln.fuelGeneratorPowerFactor * 6000, VoltageTier.LOW_HOUSEHOLD.voltage * 1.25,
                    Eln.fuelGeneratorTankCapacity)
                registerTransparentNode(id, 1, desc)
            }
        }

        private fun registerGridDevices(id: Int) {
            run {
                val desc = GridTransformerDescriptor("Grid DC-DC Converter", obj.getObj("GridConverter"), "textures/wire.png", Eln.smallInsulationMediumCurrentCopperCable)
                val g = GhostGroup()
                g.addElement(1, 0, 0)
                g.addElement(0, 0, -1)
                g.addElement(1, 0, -1)
                g.addElement(1, 1, 0)
                g.addElement(0, 1, 0)
                g.addElement(1, 1, -1)
                g.addElement(0, 1, -1)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 0, desc)
            }
            run {
                val desc = ElectricalPoleDescriptor(
                    "Utility Pole",
                    obj.getObj("UtilityPole"),
                    "textures/wire.png",
                    Eln.uninsulatedMediumCurrentAluminumCable,
                    false,
                    40,
                    51200.0)
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, 0)
                g.addElement(0, 3, 0)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 1, desc)
            }
            run {
                val desc = ElectricalPoleDescriptor(
                    "Utility Pole w/DC-DC Converter",
                    obj.getObj("UtilityPole"),
                    "textures/wire.png",
                    Eln.uninsulatedMediumCurrentAluminumCable,
                    true,
                    40,
                    12800.0)
                val g = GhostGroup()
                g.addElement(0, 1, 0)
                g.addElement(0, 2, 0)
                g.addElement(0, 3, 0)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 2, desc)
            }
            run {
                val desc = ElectricalPoleDescriptor("Transmission Tower",
                    obj.getObj("TransmissionTower"),
                    "textures/wire.png",
                    Eln.uninsulatedMediumCurrentAluminumCable,
                    false,
                    96,
                    51200.0)
                val g = GhostGroup()
                g.addRectangle(-1, 1, 0, 0, -1, 1)
                g.addRectangle(0, 0, 1, 8, 0, 0)
                g.removeElement(0, 0, 0)
                desc.setGhostGroup(g)
                registerTransparentNode(id, 3, desc)
            }
        }

        private fun registerFestive(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Christmas Tree")
                val desc = ChristmasTreeDescriptor(name, obj.getObj("Christmas_Tree"))
                if (Eln.enableFestivities) {
                    registerTransparentNode(id, 0, desc)
                } else {
                    registerHiddenTransparentNode(id, 0, desc)
                }
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Holiday Candle")
                val desc = HolidayCandleDescriptor(name, obj.getObj("Candle_Light"))
                if (Eln.enableFestivities) {
                    registerTransparentNode(id, 1, desc)
                } else {
                    registerHiddenTransparentNode(id, 1, desc)
                }
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "String Lights")
                val desc = StringLightsDescriptor(name, obj.getObj("Christmas_Lights"))
                if (Eln.enableFestivities) {
                    registerTransparentNode(id, 2, desc)
                } else {
                    registerHiddenTransparentNode(id, 2, desc)
                }
            }
        }

        private fun registerNixieTube(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Nixie Tube")
                val desc = NixieTubeDescriptor(
                    name,
                    obj.getObj("NixieTube")
                )
                registerTransparentNode(id, 0, desc)
            }
        }
    }
}
