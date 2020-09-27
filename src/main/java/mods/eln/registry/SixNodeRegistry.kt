package mods.eln.registry

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.ghost.GhostGroup
import mods.eln.i18n.I18N
import mods.eln.misc.Direction
import mods.eln.misc.FunctionTableYProtect
import mods.eln.misc.IFunction
import mods.eln.misc.series.SerieEE
import mods.eln.node.six.SixNodeDescriptor
import mods.eln.signalinductor.SignalInductorDescriptor
import mods.eln.sixnode.Amplifier
import mods.eln.sixnode.AmplifierElement
import mods.eln.sixnode.AmplifierRender
import mods.eln.sixnode.AnalogChipDescriptor
import mods.eln.sixnode.ElectricalFuseHolderDescriptor
import mods.eln.sixnode.EmergencyLampDescriptor
import mods.eln.sixnode.Filter
import mods.eln.sixnode.FilterElement
import mods.eln.sixnode.FilterRender
import mods.eln.sixnode.OpAmp
import mods.eln.sixnode.PIDRegulator
import mods.eln.sixnode.PIDRegulatorElement
import mods.eln.sixnode.PIDRegulatorRender
import mods.eln.sixnode.PortableNaNDescriptor
import mods.eln.sixnode.SampleAndHold
import mods.eln.sixnode.ScannerDescriptor
import mods.eln.sixnode.SummingUnit
import mods.eln.sixnode.SummingUnitElement
import mods.eln.sixnode.SummingUnitRender
import mods.eln.sixnode.TreeResinCollector.TreeResinCollectorDescriptor
import mods.eln.sixnode.VoltageControlledAmplifier
import mods.eln.sixnode.VoltageControlledSawtoothOscillator
import mods.eln.sixnode.VoltageControlledSineOscillator
import mods.eln.sixnode.batterycharger.BatteryChargerDescriptor
import mods.eln.sixnode.diode.DiodeDescriptor
import mods.eln.sixnode.electricalalarm.ElectricalAlarmDescriptor
import mods.eln.sixnode.electricalbreaker.ElectricalBreakerDescriptor
import mods.eln.sixnode.electricalcable.ElectricalCableDescriptor
import mods.eln.sixnode.electricalcable.ElectricalSignalBusCableElement
import mods.eln.sixnode.electricaldatalogger.ElectricalDataLoggerDescriptor
import mods.eln.sixnode.electricaldigitaldisplay.ElectricalDigitalDisplayDescriptor
import mods.eln.sixnode.electricalentitysensor.ElectricalEntitySensorDescriptor
import mods.eln.sixnode.electricalfiredetector.ElectricalFireDetectorDescriptor
import mods.eln.sixnode.electricalgatesource.ElectricalGateSourceDescriptor
import mods.eln.sixnode.electricalgatesource.ElectricalGateSourceRenderObj
import mods.eln.sixnode.electricallightsensor.ElectricalLightSensorDescriptor
import mods.eln.sixnode.electricalmath.ElectricalMathDescriptor
import mods.eln.sixnode.electricalredstoneinput.ElectricalRedstoneInputDescriptor
import mods.eln.sixnode.electricalredstoneoutput.ElectricalRedstoneOutputDescriptor
import mods.eln.sixnode.electricalrelay.ElectricalRelayDescriptor
import mods.eln.sixnode.electricalsensor.ElectricalSensorDescriptor
import mods.eln.sixnode.electricalsource.ElectricalSourceDescriptor
import mods.eln.sixnode.electricalswitch.ElectricalSwitchDescriptor
import mods.eln.sixnode.electricaltimeout.ElectricalTimeoutDescriptor
import mods.eln.sixnode.electricalvumeter.ElectricalVuMeterDescriptor
import mods.eln.sixnode.electricalwatch.ElectricalWatchDescriptor
import mods.eln.sixnode.electricalweathersensor.ElectricalWeatherSensorDescriptor
import mods.eln.sixnode.electricalwindsensor.ElectricalWindSensorDescriptor
import mods.eln.sixnode.energymeter.EnergyMeterDescriptor
import mods.eln.sixnode.groundcable.GroundCableDescriptor
import mods.eln.sixnode.hub.HubDescriptor
import mods.eln.sixnode.lampsocket.LampSocketDescriptor
import mods.eln.sixnode.lampsocket.LampSocketStandardObjRender
import mods.eln.sixnode.lampsocket.LampSocketSuspendedObjRender
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.lampsupply.LampSupplyDescriptor
import mods.eln.sixnode.logicgate.And
import mods.eln.sixnode.logicgate.DFlipFlop
import mods.eln.sixnode.logicgate.JKFlipFlop
import mods.eln.sixnode.logicgate.LogicGateDescriptor
import mods.eln.sixnode.logicgate.Nand
import mods.eln.sixnode.logicgate.Nor
import mods.eln.sixnode.logicgate.Not
import mods.eln.sixnode.logicgate.Or
import mods.eln.sixnode.logicgate.Oscillator
import mods.eln.sixnode.logicgate.PalDescriptor
import mods.eln.sixnode.logicgate.SchmittTrigger
import mods.eln.sixnode.logicgate.XNor
import mods.eln.sixnode.logicgate.Xor
import mods.eln.sixnode.modbusrtu.ModbusRtuDescriptor
import mods.eln.sixnode.powercapacitorsix.PowerCapacitorSixDescriptor
import mods.eln.sixnode.powerinductorsix.PowerInductorSixDescriptor
import mods.eln.sixnode.powersocket.PowerSocketDescriptor
import mods.eln.sixnode.resistor.ResistorDescriptor
import mods.eln.sixnode.thermalcable.ThermalCableDescriptor
import mods.eln.sixnode.thermalsensor.ThermalSensorDescriptor
import mods.eln.sixnode.tutorialsign.TutorialSignDescriptor
import mods.eln.sixnode.wirelesssignal.repeater.WirelessSignalRepeaterDescriptor
import mods.eln.sixnode.wirelesssignal.rx.WirelessSignalRxDescriptor
import mods.eln.sixnode.wirelesssignal.source.WirelessSignalSourceDescriptor
import mods.eln.sixnode.wirelesssignal.tx.WirelessSignalTxDescriptor

class SixNodeRegistry {
    companion object {

        enum class SNID (val id: Int) {
            GROUND(0),
            ELECTRICAL_SOURCE(1),
            ELECTRICAL_CABLE(2),
            THERMAL_CABLE(3),
            LAMP_SOCKET(4),
            LAMP_SUPPLY(5),
            BATTERY_CHARGER(6),
            POWER_SOCKET(7),
            WIRELESS_SIGNAL(8),
            ELECTRICAL_DATALOGGER(9),
            ELECTRICAL_RELAY(10),
            ELECTRICAL_GATE_SOURCE(11),
            PASSIVE_COMPONENT(12),
            SWITCH(13),
            CIRCUIT_BREAKER(14),
            ELECTRICAL_SENSOR(15),
            THERMAL_SENSOR(16),
            ELECTRICAL_VU_METER(17),
            ELECTRICAL_ALARM(18),
            ENVIRONMENTAL_SENSOR(19),
            ELECTRICAL_REDSTONE(20),
            ELECTRICAL_GATE(21),
            TREE_RESIN_COLLECTOR(22),
            MISC(23),
            LOGIC_GATES(24),
            ANALOG_CHIPS(25),
            DEV_TRASH(26)
        }

        fun register() {
            registerGround(SNID.GROUND.id)
            registerElectricalSource(SNID.ELECTRICAL_SOURCE.id)
            registerElectricalCable(SNID.ELECTRICAL_CABLE.id)
            registerThermalCable(SNID.THERMAL_CABLE.id)
            registerLampSocket(SNID.LAMP_SOCKET.id)
            registerLampSupply(SNID.LAMP_SUPPLY.id)
            registerBatteryCharger(SNID.BATTERY_CHARGER.id)
            registerPowerSocket(SNID.POWER_SOCKET.id)
            registerWirelessSignal(SNID.WIRELESS_SIGNAL.id)
            registerElectricalDataLogger(SNID.ELECTRICAL_DATALOGGER.id)
            registerElectricalRelay(SNID.ELECTRICAL_RELAY.id)
            registerElectricalGateSource(SNID.ELECTRICAL_GATE_SOURCE.id)
            registerPassiveComponent(SNID.PASSIVE_COMPONENT.id)
            registerSwitch(SNID.SWITCH.id)
            registerElectricalManager(SNID.CIRCUIT_BREAKER.id)
            registerElectricalSensor(SNID.ELECTRICAL_SENSOR.id)
            registerThermalSensor(SNID.THERMAL_SENSOR.id)
            registerElectricalVuMeter(SNID.ELECTRICAL_VU_METER.id)
            registerElectricalAlarm(SNID.ELECTRICAL_ALARM.id)
            registerElectricalEnvironmentalSensor(SNID.ENVIRONMENTAL_SENSOR.id)
            registerElectricalRedstone(SNID.ELECTRICAL_REDSTONE.id)
            registerElectricalGate(SNID.ELECTRICAL_GATE.id)
            registerTreeResinCollector(SNID.TREE_RESIN_COLLECTOR.id)
            registerSixNodeMisc(SNID.MISC.id)
            registerLogicalGates(SNID.LOGIC_GATES.id)
            registerAnalogChips(SNID.ANALOG_CHIPS.id)
            registerDevStuff(SNID.DEV_TRASH.id)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerSixNode(group: Int, subId: Int, descriptor: SixNodeDescriptor) {
            Eln.sixNodeItem.addDescriptor(subId + (group shl 6), descriptor)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerHiddenSixNode(group: Int, subId: Int, descriptor: SixNodeDescriptor) {
            Eln.sixNodeItem.addWithoutRegistry(subId + (group shl 6), descriptor)
        }

        private fun registerGround(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Ground Cable")
                val desc = GroundCableDescriptor(name, Eln.obj.getObj("groundcable"))
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Hub")
                val desc = HubDescriptor(name, Eln.obj.getObj("hub"))
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalSource(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Source")
                val desc = ElectricalSourceDescriptor(
                    name, Eln.obj.getObj("voltagesource"), false)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Source")
                val desc = ElectricalSourceDescriptor(
                    name, Eln.obj.getObj("signalsource"), true)
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalCable(id: Int) {
            var name: String
            var desc: ElectricalCableDescriptor
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Cable")
                Eln.stdCableRenderSignal = CableRenderDescriptor("eln",
                    "sprites/cable.png", 0.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderSignal,
                    "For signal transmission.", true)
                Eln.signalCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.SVU, Eln.SVP, 0.02 / 50
                    * Eln.gateOutputCurrent / Eln.SVII,
                    Eln.SVU * 1.3, Eln.SVP * 1.2,
                    0.5,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, 1.0)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Cable")
                Eln.stdCableRender50V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 1.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender50V,
                    "For low voltage with high current.", false)
                Eln.lowVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.LVU, Eln.LVP(), 0.2 / 20,
                    Eln.LVU * 1.3, Eln.LVP() * 1.2, 20.0,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao
                )
                registerSixNode(id, 1, desc)
                Eln.batteryCableDescriptor = desc
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Cable")
                Eln.stdCableRender200V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 2.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender200V,
                    "miaou", false)
                Eln.meduimVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.MVU, Eln.MVP(), 0.10 / 20,
                    Eln.MVU * 1.3, Eln.MVP() * 1.2, 30.0,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao
                )
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Cable")
                Eln.stdCableRender800V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 3.95f, 1.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender800V,
                    "miaou2", false)
                Eln.highVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.HVU, Eln.HVP(), 0.025 * 5 / 4 / 20,
                    Eln.HVU * 1.3, Eln.HVP() * 1.2, 40.0,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao
                )
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Cable")
                Eln.stdCableRender3200V = CableRenderDescriptor("eln",
                    "sprites/cableVHV.png", 3.95f, 1.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender3200V,
                    "miaou2", false)
                Eln.veryHighVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.VVU, Eln.VVP(), 0.025 * 5 / 4 / 20 / 8,
                    Eln.VVU * 1.3, Eln.VVP() * 1.2, 40.0,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao
                )
                registerSixNode(id, 4, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Creative Cable")
                Eln.stdCableRenderCreative = CableRenderDescriptor("eln",
                    "sprites/cablecreative.png", 8.0f, 4.0f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderCreative,
                    "Experience the power of Microresistance", false)
                Eln.creativeCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.VVU, Eln.VVU * Eln.VVP(), 1e-9,
                    Eln.VVU * 1.3, Eln.VVU * Eln.VVP() * 1.2, 40.0,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao
                )
                registerSixNode(id, 5, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Bus Cable")
                Eln.stdCableRenderSignalBus = CableRenderDescriptor("eln",
                    "sprites/cable.png", 3.95f, 3.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderSignalBus,
                    "For transmitting many signals.", true)
                Eln.signalBusCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.SVU, Eln.SVP, 0.02 / 50
                    * Eln.gateOutputCurrent / Eln.SVII,
                    Eln.SVU * 1.3, Eln.SVP * 1.2,
                    0.5,
                    Eln.cableWarmLimit, -100.0,
                    Eln.cableHeatingTime, 1.0)
                desc.ElementClass = ElectricalSignalBusCableElement::class.java
                registerSixNode(id, 6, desc)
            }
        }

        private fun registerThermalCable(id: Int) {
            var name: String
            run {
                name = "Removed from mod Copper Thermal Cable"
                val desc = ThermalCableDescriptor(name,
                    (1000 - 20).toDouble(), (-200).toDouble(),
                    500.0, 2000.0,
                    2.0, 400.0, 0.1,
                    CableRenderDescriptor("eln",
                        "sprites/tex_thermalcablebase.png", 4f, 4f),
                    "Miaou !")
                desc.addToData(false)
                desc.setDefaultIcon("empty-texture")
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Thermal Cable")
                val desc = ThermalCableDescriptor(name,
                    (1000 - 20).toDouble(), (-200).toDouble(),
                    500.0, 2000.0,
                    2.0, 10.0, 0.1,
                    CableRenderDescriptor("eln",
                        "sprites/tex_thermalcablebase.png", 4f, 4f),
                    "Miaou !")
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerLampSocket(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Socket A")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("ClassicLampSocket"), false),
                    LampSocketType.Douille,
                    false,
                    4, 0f, 0f, 0f)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Socket B Projector")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("ClassicLampSocket"), false),
                    LampSocketType.Douille,
                    false,
                    10, (-90).toFloat(), 90f, 0f)
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Robust Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("RobustLamp"), true),
                    LampSocketType.Douille,
                    false,
                    3, 0f, 0f, 0f)
                desc.setInitialOrientation(-90f)
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Flat Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("FlatLamp"), true),
                    LampSocketType.Douille,
                    false,
                    3, 0f, 0f, 0f)
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Simple Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("SimpleLamp"), true),
                    LampSocketType.Douille,
                    false,
                    3, 0f, 0f, 0f)
                registerSixNode(id, 4, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Fluorescent Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("FluorescentLamp"), true),
                    LampSocketType.Douille,
                    false,
                    4, 0f, 0f, 0f)
                desc.cableLeft = false
                desc.cableRight = false
                registerSixNode(id, 5, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Street Light")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("StreetLight"), true),
                    LampSocketType.Douille,
                    false,
                    0, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YN)
                val g = GhostGroup()
                g.addElement(1, 0, 0)
                g.addElement(2, 0, 0)
                desc.setGhostGroup(g)
                desc.renderIconInHand = true
                desc.cameraOpt = false
                registerSixNode(id, 6, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Sconce Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("SconceLamp"), true),
                    LampSocketType.Douille,
                    true,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                desc.setInitialOrientation(-90f)
                desc.setUserRotationLibertyDegrees(true)
                registerSixNode(id, 7, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Suspended Lamp Socket")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 3),
                    LampSocketType.Douille,
                    false,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                desc.cameraOpt = false
                registerSixNode(id, 8, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Long Suspended Lamp Socket")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 7),
                    LampSocketType.Douille,
                    false,
                    4, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                desc.cameraOpt = false
                registerSixNode(id, 9, desc)
            }
            run {
                val desc = EmergencyLampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Emergency Lamp"),
                    Eln.lowVoltageCableDescriptor, (10 * 60 * 10).toDouble(), 10.0, 5.0, 6, Eln.obj.getObj("EmergencyExitLighting"))
                registerSixNode(id, 10, desc)
            }
            run {
                val desc = EmergencyLampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Emergency Lamp"),
                    Eln.meduimVoltageCableDescriptor, (10 * 60 * 20).toDouble(), 25.0, 10.0, 8, Eln.obj.getObj("EmergencyExitLighting"))
                registerSixNode(id, 11, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Suspended Lamp Socket (No Swing)")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 3, false),
                    LampSocketType.Douille,
                    false,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                desc.cameraOpt = false
                registerSixNode(id, 12, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Long Suspended Lamp Socket (No Swing)")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 7, false),
                    LampSocketType.Douille,
                    false,
                    4, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                desc.cameraOpt = false
                registerSixNode(id, 13, desc)
            }
        }

        private fun registerLampSupply(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Supply")
                val desc = LampSupplyDescriptor(
                    name, Eln.obj.getObj("DistributionBoard"),
                    32
                )
                registerSixNode(id, 0, desc)
            }
        }

        private fun registerBatteryCharger(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Weak 50V Battery Charger")
                val desc = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.lowVoltageCableDescriptor,
                    Eln.LVU, 200.0
                )
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Battery Charger")
                val desc = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.lowVoltageCableDescriptor,
                    Eln.LVU, 400.0
                )
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Battery Charger")
                val desc = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.meduimVoltageCableDescriptor,
                    Eln.MVU, 1000.0
                )
                registerSixNode(id, 2, desc)
            }
        }

        private fun registerPowerSocket(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Power Socket")
                val desc = PowerSocketDescriptor(
                    0, name, Eln.obj.getObj("PowerSocket"),
                    10
                )
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Power Socket")
                val desc = PowerSocketDescriptor(
                    1, name, Eln.obj.getObj("PowerSocket"),
                    10
                )
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerWirelessSignal(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Receiver")
                val desc = WirelessSignalRxDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignalrx")
                )
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Transmitter")
                val desc = WirelessSignalTxDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignaltx"),
                    Eln.wirelessTxRange
                )
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Repeater")
                val desc = WirelessSignalRepeaterDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignalrepeater"),
                    Eln.wirelessTxRange
                )
                registerSixNode(id, 2, desc)
            }
        }

        private fun registerElectricalDataLogger(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Data Logger")
                val desc = ElectricalDataLoggerDescriptor(name, true,
                    "DataloggerCRTFloor", 1f, 0.5f, 0f, "\u00a76")
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Modern Data Logger")
                val desc = ElectricalDataLoggerDescriptor(name, true,
                    "FlatScreenMonitor", 0.0f, 1f, 0.0f, "\u00A7a")
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Industrial Data Logger")
                val desc = ElectricalDataLoggerDescriptor(name, false,
                    "IndustrialPanel", 0.25f, 0.5f, 1f, "\u00A7f")
                registerSixNode(id, 2, desc)
            }
        }

        private fun registerElectricalRelay(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Relay")
                val desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelayBig"),
                    Eln.lowVoltageCableDescriptor)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Relay")
                val desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelayBig"),
                    Eln.meduimVoltageCableDescriptor)
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Relay")
                val desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("relay800"),
                    Eln.highVoltageCableDescriptor)
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Relay")
                val desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("relay800"),
                    Eln.veryHighVoltageCableDescriptor)
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Relay")
                val desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelaySmall"),
                    Eln.signalCableDescriptor)
                registerSixNode(id, 4, desc)
            }
        }

        private fun registerElectricalGateSource(id: Int) {
            var name: String
            val signalsourcepot = ElectricalGateSourceRenderObj(Eln.obj.getObj("signalsourcepot"))
            val ledswitch = ElectricalGateSourceRenderObj(Eln.obj.getObj("ledswitch"))
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Trimmer")
                val desc = ElectricalGateSourceDescriptor(name, signalsourcepot, false,
                    "trimmer")
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch")
                val desc = ElectricalGateSourceDescriptor(name, ledswitch, true,
                    if (Eln.noSymbols) "signalswitch" else "switch")
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Button")
                val desc = ElectricalGateSourceDescriptor(name, ledswitch, true, "button")
                desc.setWithAutoReset()
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Button")
                val desc = WirelessSignalSourceDescriptor(name, ledswitch, Eln.wirelessTxRange, true)
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Switch")
                val desc = WirelessSignalSourceDescriptor(name, ledswitch, Eln.wirelessTxRange, false)
                registerSixNode(id, 4, desc)
            }
        }

        private fun registerPassiveComponent(id: Int) {
            var name: String
            var function: IFunction
            val baseFunction = FunctionTableYProtect(doubleArrayOf(0.0, 0.01, 0.03, 0.1, 0.2, 0.4, 0.8, 1.2), 1.0,
                0.0, 5.0)
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "10A Diode")
                function = FunctionTableYProtect(doubleArrayOf(0.0, 0.1, 0.3,
                    1.0, 2.0, 4.0, 8.0, 12.0), 1.0, 0.0, 100.0)
                val desc = DiodeDescriptor(
                    name,
                    function,
                    10.0,
                    1.0, 10.0,
                    Eln.sixNodeThermalLoadInitializer.copy(),
                    Eln.lowVoltageCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "25A Diode")
                function = FunctionTableYProtect(doubleArrayOf(0.0, 0.25,
                    0.75, 2.5, 5.0, 10.0, 20.0, 30.0), 1.0, 0.0, 100.0)
                val desc = DiodeDescriptor(
                    name,
                    function,
                    25.0,
                    1.0, 25.0,
                    Eln.sixNodeThermalLoadInitializer.copy(),
                    Eln.lowVoltageCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Diode")
                function = baseFunction.duplicate(1.0, 0.1)
                val desc = DiodeDescriptor(name,
                    function, 0.1,
                    1.0, 0.1,
                    Eln.sixNodeThermalLoadInitializer.copy(), Eln.signalCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal 20H inductor")
                val desc = SignalInductorDescriptor(
                    name, 20.0, Eln.lowVoltageCableDescriptor
                )
                desc.setDefaultIcon("empty-texture")
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Capacitor")
                val desc = PowerCapacitorSixDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE6(-1.0), (60 * 2000).toDouble()
                )
                registerSixNode(id, 4, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Inductor")
                val desc = PowerInductorSixDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE6(-1.0)
                )
                registerSixNode(id, 5, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Resistor")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), 0.0, false
                )
                registerSixNode(id, 6, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Rheostat")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), 0.0, true
                )
                registerSixNode(id, 7, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermistor")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), -0.01, false
                )
                registerSixNode(id, 8, desc)
            }
        }

        private fun registerSwitch(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Switch")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender3200V,
                    Eln.obj.getObj("HighVoltageSwitch"), Eln.VVU, Eln.VVP(), Eln.veryHighVoltageCableDescriptor.electricalRs * 2,
                    Eln.VVU * 1.5, Eln.VVP() * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), false)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Switch")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender800V,
                    Eln.obj.getObj("HighVoltageSwitch"), Eln.HVU, Eln.HVP(), Eln.highVoltageCableDescriptor.electricalRs * 2,
                    Eln.HVU * 1.5, Eln.HVP() * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), false)
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Switch")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender50V,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.LVU, Eln.LVP(), Eln.lowVoltageCableDescriptor.electricalRs * 2,
                    Eln.LVU * 1.5, Eln.LVP() * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), false)
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Switch")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender200V,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.MVU, Eln.MVP(), Eln.meduimVoltageCableDescriptor.electricalRs * 2,
                    Eln.MVU * 1.5, Eln.MVP() * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), false)
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRenderSignal,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.SVU, Eln.SVP, 0.02,
                    Eln.SVU * 1.5, Eln.SVP * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), true)
                registerSixNode(id, 4, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch with LED")
                val desc = ElectricalSwitchDescriptor(name, Eln.stdCableRenderSignal,
                    Eln.obj.getObj("ledswitch"), Eln.SVU, Eln.SVP, 0.02,
                    Eln.SVU * 1.5, Eln.SVP * 1.2,
                    Eln.cableThermalLoadInitializer.copy(), true)
                registerSixNode(id, 5, desc)
            }
        }

        private fun registerElectricalManager(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Breaker")
                val desc = ElectricalBreakerDescriptor(name, Eln.obj.getObj("ElectricalBreaker"))
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Energy Meter")
                val desc = EnergyMeterDescriptor(name, Eln.obj.getObj("EnergyMeter"), 8, 0)
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Energy Meter")
                val desc = EnergyMeterDescriptor(name, Eln.obj.getObj("AdvancedEnergyMeter"), 7, 8)
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fuse Holder")
                val desc = ElectricalFuseHolderDescriptor(name, Eln.obj.getObj("ElectricalFuse"))
                registerSixNode(id, 3, desc)
            }
        }

        private fun registerElectricalSensor(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Probe")
                val desc = ElectricalSensorDescriptor(name, "electricalsensor",
                    false)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage Probe")
                val desc = ElectricalSensorDescriptor(name, "voltagesensor", true)
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerThermalSensor(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermal Probe")
                val desc = ThermalSensorDescriptor(name,
                    Eln.obj.getObj("thermalsensor"), false)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Temperature Probe")
                val desc = ThermalSensorDescriptor(name,
                    Eln.obj.getObj("temperaturesensor"), true)
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalVuMeter(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Analog vuMeter")
                val desc = ElectricalVuMeterDescriptor(name, "Vumeter", false)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "LED vuMeter")
                val desc = ElectricalVuMeterDescriptor(name, "Led", true)
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalAlarm(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Nuclear Alarm")
                val desc = ElectricalAlarmDescriptor(name,
                    Eln.obj.getObj("alarmmedium"), 7, "eln:alarma", 11.0, 1f)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Standard Alarm")
                val desc = ElectricalAlarmDescriptor(name,
                    Eln.obj.getObj("alarmmedium"), 7, "eln:smallalarm_critical",
                    1.2, 2f)
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalEnvironmentalSensor(id: Int) {
            var name: String
            run {
                var desc: ElectricalLightSensorDescriptor
                run {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Daylight Sensor")
                    desc = ElectricalLightSensorDescriptor(name, Eln.obj.getObj("daylightsensor"), true)
                    registerSixNode(id, 0, desc)
                }
                run {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Light Sensor")
                    desc = ElectricalLightSensorDescriptor(name, Eln.obj.getObj("lightsensor"), false)
                    registerSixNode(id, 1, desc)
                }
            }
            run {
                var desc: ElectricalWeatherSensorDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Weather Sensor")
                    desc = ElectricalWeatherSensorDescriptor(name, Eln.obj.getObj("electricalweathersensor"))
                    registerSixNode(id, 2, desc)
                }
            }
            run {
                var desc: ElectricalWindSensorDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Anemometer Sensor")
                    desc = ElectricalWindSensorDescriptor(name, Eln.obj.getObj("Anemometer"), 25.0)
                    registerSixNode(id, 3, desc)
                }
            }
            run {
                var desc: ElectricalEntitySensorDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Entity Sensor")
                    desc = ElectricalEntitySensorDescriptor(name, Eln.obj.getObj("ProximitySensor"), 10.0)
                    registerSixNode(id, 4, desc)
                }
            }
            run {
                var desc: ElectricalFireDetectorDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fire Detector")
                    desc = ElectricalFireDetectorDescriptor(name, Eln.obj.getObj("FireDetector"), 15.0, false)
                    registerSixNode(id, 5, desc)
                }
            }
            run {
                var desc: ElectricalFireDetectorDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fire Buzzer")
                    desc = ElectricalFireDetectorDescriptor(name, Eln.obj.getObj("FireDetector"), 15.0, true)
                    registerSixNode(id, 6, desc)
                }
            }
            run {
                var desc: ScannerDescriptor
                {
                    name = I18N.TR_NAME(I18N.Type.NONE, "Scanner")
                    desc = ScannerDescriptor(name, Eln.obj.getObj("scanner"))
                    registerSixNode(id, 7, desc)
                }
            }
        }

        private fun registerElectricalRedstone(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Redstone-to-Voltage Converter")
                val desc = ElectricalRedstoneInputDescriptor(name, Eln.obj.getObj("redtoele"))
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage-to-Redstone Converter")
                val desc = ElectricalRedstoneOutputDescriptor(name,
                    Eln.obj.getObj("eletored"))
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerElectricalGate(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Timer")
                val desc = ElectricalTimeoutDescriptor(name,
                    Eln.obj.getObj("electricaltimer"))
                desc.setTickSound("eln:timer", 0.01f)
                registerSixNode(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Processor")
                val desc = ElectricalMathDescriptor(name,
                    Eln.obj.getObj("PLC"))
                registerSixNode(id, 1, desc)
            }
        }

        private fun registerTreeResinCollector(id: Int) {
            run {
                val name = I18N.TR_NAME(I18N.Type.NONE, "Tree Resin Collector")
                val desc = TreeResinCollectorDescriptor(name, Eln.obj.getObj("treeresincolector"))
                registerSixNode(id, 0, desc)
            }
        }

        private fun registerSixNodeMisc(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Modbus RTU")
                val desc = ModbusRtuDescriptor(
                    name,
                    Eln.obj.getObj("RTU")
                )
                if (Eln.modbusEnable) {
                    registerSixNode(id, 0, desc)
                } else {
                    registerHiddenSixNode(id, 0, desc)
                }
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Analog Watch")
                val desc = ElectricalWatchDescriptor(
                    name,
                    Eln.obj.getObj("WallClock"),
                    20000.0 / (3600 * 40)
                )
                registerSixNode(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Digital Watch")
                val desc = ElectricalWatchDescriptor(
                    name,
                    Eln.obj.getObj("DigitalWallClock"),
                    20000.0 / (3600 * 15)
                )
                registerSixNode(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Digital Display")
                val desc = ElectricalDigitalDisplayDescriptor(
                    name,
                    Eln.obj.getObj("DigitalDisplay")
                )
                registerSixNode(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Tutorial Sign")
                val desc = TutorialSignDescriptor(
                    name, Eln.obj.getObj("TutoPlate"))
                registerSixNode(id, 4, desc)
            }
        }

        private fun registerLogicalGates(id: Int) {
            val model = Eln.obj.getObj("LogicGates")
            registerSixNode(id, 0, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NOT Chip"), model, "NOT", Not::class.java))
            registerSixNode(id, 1, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "AND Chip"), model, "AND", And::class.java))
            registerSixNode(id, 2, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NAND Chip"), model, "NAND", Nand::class.java))
            registerSixNode(id, 3, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "OR Chip"), model, "OR", Or::class.java))
            registerSixNode(id, 4, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NOR Chip"), model, "NOR", Nor::class.java))
            registerSixNode(id, 5, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "XOR Chip"), model, "XOR", Xor::class.java))
            registerSixNode(id, 6, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "XNOR Chip"), model, "XNOR", XNor::class.java))
            registerSixNode(id, 7, PalDescriptor(I18N.TR_NAME(I18N.Type.NONE, "PAL Chip"), model))
            registerSixNode(id, 8, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Schmitt Trigger Chip"), model, "SCHMITT",
                SchmittTrigger::class.java))
            registerSixNode(id, 9, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "D Flip Flop Chip"), model, "DFF", DFlipFlop::class.java))
            registerSixNode(id, 10, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Oscillator Chip"), model, "OSC", Oscillator::class.java))
            registerSixNode(id, 11, LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "JK Flip Flop Chip"), model, "JKFF", JKFlipFlop::class.java))
        }

        private fun registerAnalogChips(id: Int) {
            val model = Eln.obj.getObj("AnalogChips")
            registerSixNode(id, 0, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "OpAmp"), model, "OP", OpAmp::class.java))
            registerSixNode(id, 1, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "PID Regulator"), model, "PID",
                PIDRegulator::class.java, PIDRegulatorElement::class.java, PIDRegulatorRender::class.java))
            registerSixNode(id, 2, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled sawtooth oscillator"), model, "VCO-SAW",
                VoltageControlledSawtoothOscillator::class.java))
            registerSixNode(id, 3, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled sine oscillator"), model, "VCO-SIN",
                VoltageControlledSineOscillator::class.java))
            registerSixNode(id, 4, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Amplifier"), model, "AMP",
                Amplifier::class.java, AmplifierElement::class.java, AmplifierRender::class.java))
            registerSixNode(id, 5, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled amplifier"), model, "VCA",
                VoltageControlledAmplifier::class.java))
            registerSixNode(id, 6, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Configurable summing unit"), model, "SUM",
                SummingUnit::class.java, SummingUnitElement::class.java, SummingUnitRender::class.java))
            registerSixNode(id, 7, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Sample and hold"), model, "SAH",
                SampleAndHold::class.java))
            registerSixNode(id, 8, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Lowpass filter"), model, "LPF",
                Filter::class.java, FilterElement::class.java, FilterRender::class.java))
        }

        private fun registerDevStuff(id: Int) {
            run {
                val name = I18N.TR_NAME(I18N.Type.NONE, "Portable NaN")
                Eln.stdPortableNaN = CableRenderDescriptor("eln", "sprites/nan.png", 3.95f, 0.95f)
                Eln.portableNaNDescriptor = PortableNaNDescriptor(name, Eln.stdPortableNaN)
                registerSixNode(id, 0, Eln.portableNaNDescriptor)
            }
        }
    }
}
