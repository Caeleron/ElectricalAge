package mods.eln.registry

import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
import mods.eln.ghost.GhostGroup
import mods.eln.i18n.I18N
import mods.eln.item.ElectricalFuseDescriptor
import mods.eln.item.ElectricalFuseDescriptor.Companion.BlownFuse
import mods.eln.misc.Direction
import mods.eln.misc.FunctionTableYProtect
import mods.eln.misc.IFunction
import mods.eln.misc.series.SerieEE
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
import mods.eln.transparentnode.LargeRheostatDescriptor
import mods.eln.transparentnode.NixieTubeDescriptor
import mods.eln.transparentnode.thermaldissipatorpassive.ThermalDissipatorPassiveDescriptor

class SixNodeRegistry {
    companion object {
        fun register() {
            //SIX NODE REGISTRATION
            //Sub-UID must be unique in this section only.
            //============================================
            registerGround(2)
            registerElectricalSource(3)
            registerElectricalCable(32)
            registerThermalCable(48)
            registerLampSocket(64)
            registerLampSupply(65)
            registerBatteryCharger(66)
            registerPowerSocket(67)

            registerWirelessSignal(92)
            registerElectricalDataLogger(93)
            registerElectricalRelay(94)
            registerElectricalGateSource(95)
            registerPassiveComponent(96)
            registerSwitch(97)
            registerElectricalManager(98)
            registerElectricalSensor(100)
            registerThermalSensor(101)
            registerElectricalVuMeter(102)
            registerElectricalAlarm(103)
            registerElectricalEnvironmentalSensor(104)
            registerElectricalRedstone(108)
            registerElectricalGate(109)
            registerTreeResinCollector(116)
            registerSixNodeMisc(117)
            registerLogicalGates(118)
            registerAnalogChips(124)
        }

        private fun registerGround(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Ground Cable")
                val desc = GroundCableDescriptor(name, Eln.obj.getObj("groundcable"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Hub")
                val desc = HubDescriptor(name, Eln.obj.getObj("hub"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalSource(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Source")
                val desc = ElectricalSourceDescriptor(
                    name, Eln.obj.getObj("voltagesource"), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Source")
                val desc = ElectricalSourceDescriptor(
                    name, Eln.obj.getObj("signalsource"), true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalCable(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalCableDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Cable")
                Eln.stdCableRenderSignal = CableRenderDescriptor("eln",
                    "sprites/cable.png", 0.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderSignal,
                    "For signal transmission.", true)
                Eln.signalCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.SVU, Eln.SVP, 0.02 / 50
                    * Eln.gateOutputCurrent / Eln.SVII,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.SVU * 1.3, Eln.SVP * 1.2,  // electricalMaximalVoltage,
                    // electricalMaximalPower,
                    0.5,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, 1.0)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Cable")
                Eln.stdCableRender50V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 1.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender50V,
                    "For low voltage with high current.", false)
                Eln.lowVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.LVU, Eln.LVP(), 0.2 / 20,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.LVU * 1.3, Eln.LVP() * 1.2, 20.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender50V,
                    "For low voltage with high current.", false)
                desc.setPhysicalConstantLikeNormalCable(
                    Eln.LVU, Eln.LVP() / 4, 0.2 / 20,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.LVU * 1.3, Eln.LVP() * 1.2, 20.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.batteryCableDescriptor = desc
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Cable")
                Eln.stdCableRender200V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 2.95f, 0.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender200V,
                    "miaou", false)
                Eln.meduimVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.MVU, Eln.MVP(), 0.10 / 20,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.MVU * 1.3, Eln.MVP() * 1.2, 30.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 12
                // highVoltageCableId = subId;
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Cable")
                Eln.stdCableRender800V = CableRenderDescriptor("eln",
                    "sprites/cable.png", 3.95f, 1.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender800V,
                    "miaou2", false)
                Eln.highVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.HVU, Eln.HVP(), 0.025 * 5 / 4 / 20,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.HVU * 1.3, Eln.HVP() * 1.2, 40.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 16
                // highVoltageCableId = subId;
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Cable")
                Eln.stdCableRender3200V = CableRenderDescriptor("eln",
                    "sprites/cableVHV.png", 3.95f, 1.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRender3200V,
                    "miaou2", false)
                Eln.veryHighVoltageCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.VVU, Eln.VVP(), 0.025 * 5 / 4 / 20 / 8,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.VVU * 1.3, Eln.VVP() * 1.2, 40.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 24
                name = I18N.TR_NAME(I18N.Type.NONE, "Creative Cable")
                Eln.stdCableRenderCreative = CableRenderDescriptor("eln",
                    "sprites/cablecreative.png", 8.0f, 4.0f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderCreative,
                    "Experience the power of Microresistance", false)
                Eln.creativeCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.VVU, Eln.VVU * Eln.VVP(), 1e-9,  //what!?
                    Eln.VVU * 1.3, Eln.VVU * Eln.VVP() * 1.2, 40.0,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, Eln.cableThermalConductionTao // thermalNominalHeatTime,
                    // thermalConductivityTao
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 20
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Bus Cable")
                Eln.stdCableRenderSignalBus = CableRenderDescriptor("eln",
                    "sprites/cable.png", 3.95f, 3.95f)
                desc = ElectricalCableDescriptor(name, Eln.stdCableRenderSignalBus,
                    "For transmitting many signals.", true)
                Eln.signalBusCableDescriptor = desc
                desc.setPhysicalConstantLikeNormalCable(Eln.SVU, Eln.SVP, 0.02 / 50
                    * Eln.gateOutputCurrent / Eln.SVII,  // electricalNominalVoltage,
                    // electricalNominalPower,
                    // electricalNominalPowerDrop,
                    Eln.SVU * 1.3, Eln.SVP * 1.2,  // electricalMaximalVoltage,
                    // electricalMaximalPower,
                    0.5,  // electricalOverVoltageStartPowerLost,
                    Eln.cableWarmLimit, -100.0,  // thermalWarmLimit, thermalCoolLimit,
                    Eln.cableHeatingTime, 1.0)
                desc.ElementClass = ElectricalSignalBusCableElement::class.java
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerThermalCable(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = "Removed from mod Copper Thermal Cable"
                val desc = ThermalCableDescriptor(name,
                    (1000 - 20).toDouble(), (-200).toDouble(),  // thermalWarmLimit, thermalCoolLimit,
                    500.0, 2000.0,  // thermalStdT, thermalStdPower,
                    2.0, 400.0, 0.1,  // thermalStdDrop, thermalStdLost, thermalTao,
                    CableRenderDescriptor("eln",
                        "sprites/tex_thermalcablebase.png", 4f, 4f),
                    "Miaou !") // description
                desc.addToData(false)
                desc.setDefaultIcon("empty-texture")
                Eln.sixNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Thermal Cable")
                val desc = ThermalCableDescriptor(name,
                    (1000 - 20).toDouble(), (-200).toDouble(),  // thermalWarmLimit, thermalCoolLimit,
                    500.0, 2000.0,  // thermalStdT, thermalStdPower,
                    2.0, 10.0, 0.1,  // thermalStdDrop, thermalStdLost, thermalTao,
                    CableRenderDescriptor("eln",
                        "sprites/tex_thermalcablebase.png", 4f, 4f),
                    "Miaou !") // description
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerLampSocket(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Socket A")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("ClassicLampSocket"), false),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    4, 0f, 0f, 0f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Socket B Projector")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("ClassicLampSocket"), false),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    10, (-90).toFloat(), 90f, 0f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Robust Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("RobustLamp"), true),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    3, 0f, 0f, 0f)
                desc.setInitialOrientation(-90f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 5
                name = I18N.TR_NAME(I18N.Type.NONE, "Flat Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("FlatLamp"), true),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    3, 0f, 0f, 0f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Simple Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("SimpleLamp"), true),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    3, 0f, 0f, 0f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 7
                name = I18N.TR_NAME(I18N.Type.NONE, "Fluorescent Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("FluorescentLamp"), true),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    4, 0f, 0f, 0f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cableLeft = false
                desc.cableRight = false
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Street Light")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("StreetLight"), true),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    0, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YN)
                val g = GhostGroup()
                g.addElement(1, 0, 0)
                g.addElement(2, 0, 0)
                desc.setGhostGroup(g)
                desc.renderIconInHand = true
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cameraOpt = false
            }
            run {
                subId = 9
                name = I18N.TR_NAME(I18N.Type.NONE, "Sconce Lamp Socket")
                val desc = LampSocketDescriptor(name, LampSocketStandardObjRender(Eln.obj.getObj("SconceLamp"), true),
                    LampSocketType.Douille,  // LampSocketType
                    true,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                desc.setInitialOrientation(-90f)
                desc.setUserRotationLibertyDegrees(true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 12
                name = I18N.TR_NAME(I18N.Type.NONE, "Suspended Lamp Socket")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 3),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cameraOpt = false
            }
            run {
                subId = 13
                name = I18N.TR_NAME(I18N.Type.NONE, "Long Suspended Lamp Socket")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 7),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    4, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cameraOpt = false
            }

            // TODO: Modern street light.
            Eln.sixNodeItem.addDescriptor(15 + (id shl 6),
                EmergencyLampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Emergency Lamp"),
                    Eln.lowVoltageCableDescriptor, (10 * 60 * 10).toDouble(), 10.0, 5.0, 6, Eln.obj.getObj("EmergencyExitLighting")))
            Eln.sixNodeItem.addDescriptor(16 + (id shl 6),
                EmergencyLampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Emergency Lamp"),
                    Eln.meduimVoltageCableDescriptor, (10 * 60 * 20).toDouble(), 25.0, 10.0, 8, Eln.obj.getObj("EmergencyExitLighting")))
            run {
                subId = 17
                name = I18N.TR_NAME(I18N.Type.NONE, "Suspended Lamp Socket (No Swing)")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 3, false),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    3, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cameraOpt = false
            }
            run {
                subId = 18
                name = I18N.TR_NAME(I18N.Type.NONE, "Long Suspended Lamp Socket (No Swing)")
                val desc = LampSocketDescriptor(name,
                    LampSocketSuspendedObjRender(Eln.obj.getObj("RobustLampSuspended"), true, 7, false),
                    LampSocketType.Douille,  // LampSocketType
                    false,
                    4, 0f, 0f, 0f)
                desc.setPlaceDirection(Direction.YP)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                desc.cameraOpt = false
            }
        }

        private fun registerLampSupply(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Lamp Supply")
                val desc = LampSupplyDescriptor(
                    name, Eln.obj.getObj("DistributionBoard"),
                    32
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerBatteryCharger(id: Int) {
            var subId: Int
            var completId: Int
            var name: String
            var descriptor: BatteryChargerDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Weak 50V Battery Charger")
                descriptor = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable,
                    Eln.LVU, 200.0 // double nominalVoltage,double nominalPower
                )
                Eln.sixNodeItem.addDescriptor(completId, descriptor)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Battery Charger")
                descriptor = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.lowVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable,
                    Eln.LVU, 400.0 // double nominalVoltage,double nominalPower
                )
                Eln.sixNodeItem.addDescriptor(completId, descriptor)
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Battery Charger")
                descriptor = BatteryChargerDescriptor(
                    name, Eln.obj.getObj("batterychargera"),
                    Eln.meduimVoltageCableDescriptor,  // ElectricalCableDescriptor
                    // cable,
                    Eln.MVU, 1000.0 // double nominalVoltage,double nominalPower
                )
                Eln.sixNodeItem.addDescriptor(completId, descriptor)
            }
        }

        private fun registerPowerSocket(id: Int) {
            var subId: Int
            var name: String
            var desc: PowerSocketDescriptor
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "50V Power Socket")
                desc = PowerSocketDescriptor(
                    subId, name, Eln.obj.getObj("PowerSocket"),
                    10 //Range for plugged devices (without obstacles)
                )
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "200V Power Socket")
                desc = PowerSocketDescriptor(
                    subId, name, Eln.obj.getObj("PowerSocket"),
                    10 //Range for plugged devices (without obstacles)
                )
                desc.setPlaceDirection(arrayOf(Direction.XP, Direction.XN, Direction.ZP, Direction.ZN))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerWirelessSignal(id: Int) {
            var subId: Int
            var name: String
            run {
                val desc: WirelessSignalRxDescriptor
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Receiver")
                desc = WirelessSignalRxDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignalrx")
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: WirelessSignalTxDescriptor
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Transmitter")
                desc = WirelessSignalTxDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignaltx"),
                    Eln.wirelessTxRange
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: WirelessSignalRepeaterDescriptor
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Signal Repeater")
                desc = WirelessSignalRepeaterDescriptor(
                    name,
                    Eln.obj.getObj("wirelesssignalrepeater"),
                    Eln.wirelessTxRange
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalDataLogger(id: Int) {
            var subId: Int
            var name: String
            run {
                val desc: ElectricalDataLoggerDescriptor
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Data Logger")
                desc = ElectricalDataLoggerDescriptor(name, true,
                    "DataloggerCRTFloor", 1f, 0.5f, 0f, "\u00a76")
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: ElectricalDataLoggerDescriptor
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Modern Data Logger")
                desc = ElectricalDataLoggerDescriptor(name, true,
                    "FlatScreenMonitor", 0.0f, 1f, 0.0f, "\u00A7a")
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: ElectricalDataLoggerDescriptor
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "Industrial Data Logger")
                desc = ElectricalDataLoggerDescriptor(name, false,
                    "IndustrialPanel", 0.25f, 0.5f, 1f, "\u00A7f")
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalRelay(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalRelayDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Relay")
                desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelayBig"),
                    Eln.lowVoltageCableDescriptor)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Relay")
                desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelayBig"),
                    Eln.meduimVoltageCableDescriptor)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Relay")
                desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("relay800"),
                    Eln.highVoltageCableDescriptor)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 3
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Relay")
                desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("relay800"),
                    Eln.veryHighVoltageCableDescriptor)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Relay")
                desc = ElectricalRelayDescriptor(
                    name, Eln.obj.getObj("RelaySmall"),
                    Eln.signalCableDescriptor)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalGateSource(id: Int) {
            var subId: Int
            var name: String
            val signalsourcepot = ElectricalGateSourceRenderObj(Eln.obj.getObj("signalsourcepot"))
            val ledswitch = ElectricalGateSourceRenderObj(Eln.obj.getObj("ledswitch"))
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Trimmer")
                val desc = ElectricalGateSourceDescriptor(name, signalsourcepot, false,
                    "trimmer")
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch")
                val desc = ElectricalGateSourceDescriptor(name, ledswitch, true,
                    if (Eln.noSymbols) "signalswitch" else "switch")
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Button")
                val desc = ElectricalGateSourceDescriptor(name, ledswitch, true, "button")
                desc.setWithAutoReset()
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 12
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Button")
                val desc = WirelessSignalSourceDescriptor(name, ledswitch, Eln.wirelessTxRange, true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Wireless Switch")
                val desc = WirelessSignalSourceDescriptor(name, ledswitch, Eln.wirelessTxRange, false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerPassiveComponent(id: Int) {
            var subId: Int
            var name: String
            var function: IFunction
            val baseFunction = FunctionTableYProtect(doubleArrayOf(0.0, 0.01, 0.03, 0.1, 0.2, 0.4, 0.8, 1.2), 1.0,
                0.0, 5.0)
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "10A Diode")
                function = FunctionTableYProtect(doubleArrayOf(0.0, 0.1, 0.3,
                    1.0, 2.0, 4.0, 8.0, 12.0), 1.0, 0.0, 100.0)
                val desc = DiodeDescriptor(
                    name,  // int iconId, String name,
                    function,
                    10.0,  // double Imax,
                    1.0, 10.0,
                    Eln.sixNodeThermalLoadInitializer.copy(),
                    Eln.lowVoltageCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "25A Diode")
                function = FunctionTableYProtect(doubleArrayOf(0.0, 0.25,
                    0.75, 2.5, 5.0, 10.0, 20.0, 30.0), 1.0, 0.0, 100.0)
                val desc = DiodeDescriptor(
                    name,  // int iconId, String name,
                    function,
                    25.0,  // double Imax,
                    1.0, 25.0,
                    Eln.sixNodeThermalLoadInitializer.copy(),
                    Eln.lowVoltageCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Diode")
                function = baseFunction.duplicate(1.0, 0.1)
                val desc = DiodeDescriptor(name,  // int iconId,
                    // String name,
                    function, 0.1,  // double Imax,
                    1.0, 0.1,
                    Eln.sixNodeThermalLoadInitializer.copy(), Eln.signalCableDescriptor,
                    Eln.obj.getObj("PowerElectricPrimitives"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal 20H inductor")
                val desc = SignalInductorDescriptor(
                    name, 20.0, Eln.lowVoltageCableDescriptor
                )
                desc.setDefaultIcon("empty-texture")
                Eln.sixNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
            run {
                subId = 32
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Capacitor")
                val desc = PowerCapacitorSixDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE6(-1.0), (60 * 2000).toDouble()
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 34
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Inductor")
                val desc = PowerInductorSixDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE6(-1.0)
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 36
                name = I18N.TR_NAME(I18N.Type.NONE, "Power Resistor")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), 0.0, false
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 37
                name = I18N.TR_NAME(I18N.Type.NONE, "Rheostat")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), 0.0, true
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 38
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermistor")
                val desc = ResistorDescriptor(
                    name, Eln.obj.getObj("PowerElectricPrimitives"), SerieEE.newE12(-2.0), -0.01, false
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 39
                name = I18N.TR_NAME(I18N.Type.NONE, "Large Rheostat")
                val dissipator = ThermalDissipatorPassiveDescriptor(
                    name,
                    Eln.obj.getObj("LargeRheostat"),
                    1000.0, (-100).toDouble(),  // double warmLimit,double coolLimit,
                    4000.0, 800.0,  // double nominalP,double nominalT,
                    10.0, 1.0 // double nominalTao,double nominalConnectionDrop
                )
                val desc = LargeRheostatDescriptor(
                    name, dissipator, Eln.veryHighVoltageCableDescriptor, SerieEE.newE12(0.0)
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerSwitch(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalSwitchDescriptor
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Very High Voltage Switch")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender3200V,
                    Eln.obj.getObj("HighVoltageSwitch"), Eln.VVU, Eln.VVP(), Eln.veryHighVoltageCableDescriptor.electricalRs * 2,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.VVU * 1.5, Eln.VVP() * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "High Voltage Switch")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender800V,
                    Eln.obj.getObj("HighVoltageSwitch"), Eln.HVU, Eln.HVP(), Eln.highVoltageCableDescriptor.electricalRs * 2,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.HVU * 1.5, Eln.HVP() * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Low Voltage Switch")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender50V,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.LVU, Eln.LVP(), Eln.lowVoltageCableDescriptor.electricalRs * 2,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.LVU * 1.5, Eln.LVP() * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "Medium Voltage Switch")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRender200V,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.MVU, Eln.MVP(), Eln.meduimVoltageCableDescriptor.electricalRs * 2,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.MVU * 1.5, Eln.MVP() * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 3
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRenderSignal,
                    Eln.obj.getObj("LowVoltageSwitch"), Eln.SVU, Eln.SVP, 0.02,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.SVU * 1.5, Eln.SVP * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), true)
                Eln.sixNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
            // 4 taken
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Switch with LED")
                desc = ElectricalSwitchDescriptor(name, Eln.stdCableRenderSignal,
                    Eln.obj.getObj("ledswitch"), Eln.SVU, Eln.SVP, 0.02,  // nominalVoltage,
                    // nominalPower,
                    // nominalDropFactor,
                    Eln.SVU * 1.5, Eln.SVP * 1.2,  // maximalVoltage, maximalPower
                    Eln.cableThermalLoadInitializer.copy(), true)
                Eln.sixNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalManager(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Breaker")
                val desc = ElectricalBreakerDescriptor(name, Eln.obj.getObj("ElectricalBreaker"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Energy Meter")
                val desc = EnergyMeterDescriptor(name, Eln.obj.getObj("EnergyMeter"), 8, 0)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 5
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Energy Meter")
                val desc = EnergyMeterDescriptor(name, Eln.obj.getObj("AdvancedEnergyMeter"), 7, 8)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fuse Holder")
                val desc = ElectricalFuseHolderDescriptor(name, Eln.obj.getObj("ElectricalFuse"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 7
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for low voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.lowVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for medium voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.meduimVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 9
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for high voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.highVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 10
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for very high voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.veryHighVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 11
                name = I18N.TR_NAME(I18N.Type.NONE, "Blown Lead Fuse")
                val desc = ElectricalFuseDescriptor(name, null, Eln.obj.getObj("ElectricalFuse"))
                BlownFuse = desc
                Eln.sharedItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalSensor(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalSensorDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Probe")
                desc = ElectricalSensorDescriptor(name, "electricalsensor",
                    false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage Probe")
                desc = ElectricalSensorDescriptor(name, "voltagesensor", true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerThermalSensor(id: Int) {
            var subId: Int
            var name: String
            var desc: ThermalSensorDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermal Probe")
                desc = ThermalSensorDescriptor(name,
                    Eln.obj.getObj("thermalsensor"), false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Temperature Probe")
                desc = ThermalSensorDescriptor(name,
                    Eln.obj.getObj("temperaturesensor"), true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalVuMeter(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalVuMeterDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Analog vuMeter")
                desc = ElectricalVuMeterDescriptor(name, "Vumeter", false)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "LED vuMeter")
                desc = ElectricalVuMeterDescriptor(name, "Led", true)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalAlarm(id: Int) {
            var subId: Int
            var name: String
            var desc: ElectricalAlarmDescriptor
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Nuclear Alarm")
                desc = ElectricalAlarmDescriptor(name,
                    Eln.obj.getObj("alarmmedium"), 7, "eln:alarma", 11.0, 1f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Standard Alarm")
                desc = ElectricalAlarmDescriptor(name,
                    Eln.obj.getObj("alarmmedium"), 7, "eln:smallalarm_critical",
                    1.2, 2f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalEnvironmentalSensor(id: Int) {
            var subId: Int
            var name: String
            run {
                var desc: ElectricalLightSensorDescriptor
                run {
                    subId = 0
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Daylight Sensor")
                    desc = ElectricalLightSensorDescriptor(name, Eln.obj.getObj("daylightsensor"), true)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
                run {
                    subId = 1
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Light Sensor")
                    desc = ElectricalLightSensorDescriptor(name, Eln.obj.getObj("lightsensor"), false)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ElectricalWeatherSensorDescriptor
                {
                    subId = 4
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Weather Sensor")
                    desc = ElectricalWeatherSensorDescriptor(name, Eln.obj.getObj("electricalweathersensor"))
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ElectricalWindSensorDescriptor
                {
                    subId = 8
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Anemometer Sensor")
                    desc = ElectricalWindSensorDescriptor(name, Eln.obj.getObj("Anemometer"), 25.0)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ElectricalEntitySensorDescriptor
                {
                    subId = 12
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Entity Sensor")
                    desc = ElectricalEntitySensorDescriptor(name, Eln.obj.getObj("ProximitySensor"), 10.0)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ElectricalFireDetectorDescriptor
                {
                    subId = 13
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fire Detector")
                    desc = ElectricalFireDetectorDescriptor(name, Eln.obj.getObj("FireDetector"), 15.0, false)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ElectricalFireDetectorDescriptor
                {
                    subId = 14
                    name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Fire Buzzer")
                    desc = ElectricalFireDetectorDescriptor(name, Eln.obj.getObj("FireDetector"), 15.0, true)
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
            run {
                var desc: ScannerDescriptor
                {
                    subId = 15
                    name = I18N.TR_NAME(I18N.Type.NONE, "Scanner")
                    desc = ScannerDescriptor(name, Eln.obj.getObj("scanner"))
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                }
            }
        }

        private fun registerElectricalRedstone(id: Int) {
            var subId: Int
            var name: String
            run {
                val desc: ElectricalRedstoneInputDescriptor
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Redstone-to-Voltage Converter")
                desc = ElectricalRedstoneInputDescriptor(name, Eln.obj.getObj("redtoele"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: ElectricalRedstoneOutputDescriptor
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Voltage-to-Redstone Converter")
                desc = ElectricalRedstoneOutputDescriptor(name,
                    Eln.obj.getObj("eletored"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerElectricalGate(id: Int) {
            var subId: Int
            var name: String
            run {
                val desc: ElectricalTimeoutDescriptor
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Timer")
                desc = ElectricalTimeoutDescriptor(name,
                    Eln.obj.getObj("electricaltimer"))
                desc.setTickSound("eln:timer", 0.01f)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                val desc: ElectricalMathDescriptor
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Processor")
                desc = ElectricalMathDescriptor(name,
                    Eln.obj.getObj("PLC"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerTreeResinCollector(id: Int) {
            var subId: Int
            var completId: Int
            var name: String
            var descriptor: TreeResinCollectorDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Tree Resin Collector")
                descriptor = TreeResinCollectorDescriptor(name, Eln.obj.getObj("treeresincolector"))
                Eln.sixNodeItem.addDescriptor(completId, descriptor)
            }
        }

        private fun registerSixNodeMisc(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Modbus RTU")
                val desc = ModbusRtuDescriptor(
                    name,
                    Eln.obj.getObj("RTU")
                )
                if (Eln.modbusEnable) {
                    Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
                } else {
                    Eln.sixNodeItem.addWithoutRegistry(subId + (id shl 6), desc)
                }
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Analog Watch")
                val desc = ElectricalWatchDescriptor(
                    name,
                    Eln.obj.getObj("WallClock"),
                    20000.0 / (3600 * 40)
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 5
                name = I18N.TR_NAME(I18N.Type.NONE, "Digital Watch")
                val desc = ElectricalWatchDescriptor(
                    name,
                    Eln.obj.getObj("DigitalWallClock"),
                    20000.0 / (3600 * 15)
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Digital Display")
                val desc = ElectricalDigitalDisplayDescriptor(
                    name,
                    Eln.obj.getObj("DigitalDisplay")
                )
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 7
                name = I18N.TR_NAME(I18N.Type.NONE, "Nixie Tube")
                val desc = NixieTubeDescriptor(
                    name,
                    Eln.obj.getObj("NixieTube")
                )
                Eln.transparentNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Tutorial Sign")
                val desc = TutorialSignDescriptor(
                    name, Eln.obj.getObj("TutoPlate"))
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), desc)
            }
        }

        private fun registerLogicalGates(id: Int) {
            val model = Eln.obj.getObj("LogicGates")
            Eln.sixNodeItem.addDescriptor(0 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NOT Chip"), model, "NOT", Not::class.java))
            Eln.sixNodeItem.addDescriptor(1 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "AND Chip"), model, "AND", And::class.java))
            Eln.sixNodeItem.addDescriptor(2 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NAND Chip"), model, "NAND", Nand::class.java))
            Eln.sixNodeItem.addDescriptor(3 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "OR Chip"), model, "OR", Or::class.java))
            Eln.sixNodeItem.addDescriptor(4 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "NOR Chip"), model, "NOR", Nor::class.java))
            Eln.sixNodeItem.addDescriptor(5 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "XOR Chip"), model, "XOR", Xor::class.java))
            Eln.sixNodeItem.addDescriptor(6 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "XNOR Chip"), model, "XNOR", XNor::class.java))
            Eln.sixNodeItem.addDescriptor(7 + (id shl 6),
                PalDescriptor(I18N.TR_NAME(I18N.Type.NONE, "PAL Chip"), model))
            Eln.sixNodeItem.addDescriptor(8 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Schmitt Trigger Chip"), model, "SCHMITT",
                    SchmittTrigger::class.java))
            Eln.sixNodeItem.addDescriptor(9 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "D Flip Flop Chip"), model, "DFF", DFlipFlop::class.java))
            Eln.sixNodeItem.addDescriptor(10 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Oscillator Chip"), model, "OSC", Oscillator::class.java))
            Eln.sixNodeItem.addDescriptor(11 + (id shl 6),
                LogicGateDescriptor(I18N.TR_NAME(I18N.Type.NONE, "JK Flip Flop Chip"), model, "JKFF", JKFlipFlop::class.java))
        }

        private fun registerAnalogChips(id: Int) {
            var id = id
            id = id shl 6
            val model = Eln.obj.getObj("AnalogChips")
            Eln.sixNodeItem.addDescriptor(id + 0,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "OpAmp"), model, "OP", OpAmp::class.java))
            Eln.sixNodeItem.addDescriptor(id + 1, AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "PID Regulator"), model, "PID",
                PIDRegulator::class.java, PIDRegulatorElement::class.java, PIDRegulatorRender::class.java))
            Eln.sixNodeItem.addDescriptor(id + 2,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled sawtooth oscillator"), model, "VCO-SAW",
                    VoltageControlledSawtoothOscillator::class.java))
            Eln.sixNodeItem.addDescriptor(id + 3,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled sine oscillator"), model, "VCO-SIN",
                    VoltageControlledSineOscillator::class.java))
            Eln.sixNodeItem.addDescriptor(id + 4,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Amplifier"), model, "AMP",
                    Amplifier::class.java, AmplifierElement::class.java, AmplifierRender::class.java))
            Eln.sixNodeItem.addDescriptor(id + 5,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Voltage controlled amplifier"), model, "VCA",
                    VoltageControlledAmplifier::class.java))
            Eln.sixNodeItem.addDescriptor(id + 6,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Configurable summing unit"), model, "SUM",
                    SummingUnit::class.java, SummingUnitElement::class.java, SummingUnitRender::class.java))
            Eln.sixNodeItem.addDescriptor(id + 7,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Sample and hold"), model, "SAH",
                    SampleAndHold::class.java))
            Eln.sixNodeItem.addDescriptor(id + 8,
                AnalogChipDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Lowpass filter"), model, "LPF",
                    Filter::class.java, FilterElement::class.java, FilterRender::class.java))
        }
    }
}
