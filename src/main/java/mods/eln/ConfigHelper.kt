package mods.eln

import cpw.mods.fml.common.event.FMLPreInitializationEvent
import mods.eln.entity.ReplicatorPopProcess
import mods.eln.misc.Utils
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.config.Property
import java.util.*

class ConfigHelper {
    companion object {
        fun loadConfiguration(event: FMLPreInitializationEvent) {
            val config = Configuration(event.getSuggestedConfigurationFile())
            config.load()
            Eln.modbusEnable = config.get("modbus", "enable", false).getBoolean(false)
            Eln.modbusPort = config.get("modbus", "port", 1502).getInt(1502)
            Eln.debugEnabled = config.get("debug", "enable", false).getBoolean(false)

            Eln.explosionEnable = config.get("gameplay", "explosion", true).getBoolean(true)

            //explosionEnable = false;


            //explosionEnable = false;
            Eln.versionCheckEnabled = config.get("general", "versionCheckEnable", true, "Enable version checker").getBoolean(true)
            Eln.analyticsEnabled = config.get("general", "analyticsEnable", true).getBoolean(true)
            Eln.analyticsEnabled = config.get("general", "analyticsEnable", true, "Enable Analytics for Electrical Age").getBoolean(true)
            Eln.analyticsURL = config.get("general", "analyticsURL", "http://eln.ja13.org/stat", "Set update checker URL").getString()
            Eln.analyticsPlayerUUIDOptIn = config.get("general", "analyticsPlayerOptIn", false, "Opt into sending player UUID when sending analytics (default DISABLED)").getBoolean(false)
            Eln.enableFestivities = config.get("general", "enableFestiveItems", true, "Set this to false to disable holiday themed items").getBoolean()

            if (Eln.analyticsEnabled) {
                val p: Property = config.get("general", "playerUUID", "")
                if (p.string.length == 0) {
                    Eln.playerUUID = UUID.randomUUID().toString()
                    p.set(Eln.playerUUID)
                } else Eln.playerUUID = p.string
            }

            Eln.heatTurbinePowerFactor = config.get("balancing", "heatTurbinePowerFactor", 1).getDouble(1.0)
            Eln.solarPanelPowerFactor = config.get("balancing", "solarPanelPowerFactor", 1).getDouble(1.0)
            Eln.windTurbinePowerFactor = config.get("balancing", "windTurbinePowerFactor", 1).getDouble(1.0)
            Eln.waterTurbinePowerFactor = config.get("balancing", "waterTurbinePowerFactor", 1).getDouble(1.0)
            Eln.fuelGeneratorPowerFactor = config.get("balancing", "fuelGeneratorPowerFactor", 1).getDouble(1.0)
            Eln.fuelHeatFurnacePowerFactor = config.get("balancing", "fuelHeatFurnacePowerFactor", 1.0).getDouble()
            Eln.autominerRange = config.get("balancing", "autominerRange", 10, "Maximum horizontal distance from autominer that will be mined").getInt(10)

            Other.elnToIc2ConversionRatio = config.get("balancing", "ElnToIndustrialCraftConversionRatio", 1.0 / 3.0).getDouble(1.0 / 3.0)
            Other.elnToOcConversionRatio = config.get("balancing", "ElnToOpenComputerConversionRatio", 1.0 / 3.0 / 2.5).getDouble(1.0 / 3.0 / 2.5)
            Other.elnToTeConversionRatio = config.get("balancing", "ElnToThermalExpansionConversionRatio", 1.0 / 3.0 * 4).getDouble(1.0 / 3.0 * 4)
            //	Other.ElnToBuildcraftConversionRatio = config.get("balancing", "ElnToBuildcraftConversionRatio", 1.0 / 3.0 / 5 * 2).getDouble(1.0 / 3.0 / 5 * 2);
            //	Other.ElnToBuildcraftConversionRatio = config.get("balancing", "ElnToBuildcraftConversionRatio", 1.0 / 3.0 / 5 * 2).getDouble(1.0 / 3.0 / 5 * 2);
            Eln.plateConversionRatio = config.get("balancing", "platesPerIngot", 1).getInt(1)
            Eln.shaftEnergyFactor = config.get("balancing", "shaftEnergyFactor", 0.05).getDouble(0.05)

            Eln.stdBatteryHalfLife = config.get("battery", "batteryHalfLife", 2, "How many days it takes for a battery to decay half way").getDouble(2.0) * Utils.minecraftDay
            Eln.batteryCapacityFactor = config.get("balancing", "batteryCapacityFactor", 1.0).getDouble(1.0)

            Eln.ComputerProbeEnable = config.get("compatibility", "ComputerProbeEnable", true).getBoolean(true)
            Eln.ElnToOtherEnergyConverterEnable = config.get("compatibility", "ElnToOtherEnergyConverterEnable", true).getBoolean(true)

            Eln.replicatorPop = config.get("entity", "replicatorPop", true).getBoolean(true)
            ReplicatorPopProcess.popPerSecondPerPlayer = config.get("entity", "replicatorPopWhenThunderPerSecond", 1.0 / 120).getDouble(1.0 / 120)
            Eln.replicatorRegistrationId = config.get("entity", "replicatorId", -1).getInt(-1)
            Eln.killMonstersAroundLamps = config.get("entity", "killMonstersAroundLamps", true).getBoolean(true)
            Eln.killMonstersAroundLampsRange = config.get("entity", "killMonstersAroundLampsRange", 9).getInt(9)
            Eln.maxReplicators = config.get("entity", "maxReplicators", 100).getInt(100)

            Eln.forceOreRegen = config.get("mapGenerate", "forceOreRegen", false).getBoolean(false)
            Eln.genCopper = config.get("mapGenerate", "copper", true).getBoolean(true)
            Eln.genLead = config.get("mapGenerate", "lead", true).getBoolean(true)
            Eln.genTungsten = config.get("mapGenerate", "tungsten", true).getBoolean(true)
            Eln.genCinnabar = config.get("mapGenerate", "cinnabar", true).getBoolean(true)
            Eln.genCinnabar = false

            Eln.oredictTungsten = config.get("dictionary", "tungsten", false).getBoolean(false)
            if (Eln.oredictTungsten) {
                Eln.dictTungstenOre = "oreTungsten"
                Eln.dictTungstenDust = "dustTungsten"
                Eln.dictTungstenIngot = "ingotTungsten"
            } else {
                Eln.dictTungstenOre = "oreElnTungsten"
                Eln.dictTungstenDust = "dustElnTungsten"
                Eln.dictTungstenIngot = "ingotElnTungsten"
            }
            Eln.oredictChips = config.get("dictionary", "chips", true).getBoolean(true)
            if (Eln.oredictChips) {
                Eln.dictCheapChip = "circuitBasic"
                Eln.dictAdvancedChip = "circuitAdvanced"
            } else {
                Eln.dictCheapChip = "circuitElnBasic"
                Eln.dictAdvancedChip = "circuitElnAdvanced"
            }

            Eln.incandescentLampLife = config.get("lamp", "incandescentLifeInHours", 16.0).getDouble(16.0)
            Eln.economicLampLife = config.get("lamp", "economicLifeInHours", 64.0).getDouble(64.0)
            Eln.carbonLampLife = config.get("lamp", "carbonLifeInHours", 6.0).getDouble(6.0)
            Eln.ledLampLife = config.get("lamp", "ledLifeInHours", 512.0).getDouble(512.0)
            Eln.ledLampInfiniteLife = config.get("lamp", "infiniteLedLife", false).getBoolean()
            Eln.allowSwingingLamps = config.get("lamp", "swingingLamps", true).getBoolean()

            Eln.fuelGeneratorTankCapacity = config.get("fuelGenerator",
                "tankCapacityInSecondsAtNominalPower", 20 * 60).getDouble(20 * 60.toDouble())

            Eln.addOtherModOreToXRay = config.get("xrayscannerconfig", "addOtherModOreToXRay", true).getBoolean(true)
            Eln.xRayScannerRange = config.get("xrayscannerconfig", "rangeInBloc", 5.0).getDouble(5.0)
            Eln.xRayScannerRange = Math.max(Math.min(Eln.xRayScannerRange, 10.0), 4.0)
            Eln.xRayScannerCanBeCrafted = config.get("xrayscannerconfig", "canBeCrafted", true).getBoolean(true)

            Eln.electricalFrequency = config.get("simulation", "electricalFrequency", 20).getDouble(20.0)
            Eln.electricalInterSystemOverSampling = config.get("simulation", "electricalInterSystemOverSampling", 50).getInt(50)
            Eln.thermalFrequency = config.get("simulation", "thermalFrequency", 400).getDouble(400.0)

            Eln.wirelessTxRange = config.get("wireless", "txRange", 32).getInt()

            Eln.wailaEasyMode = config.get("balancing", "wailaEasyMode", false, "Display more detailed WAILA info on some machines").getBoolean(false)
            Eln.cablePowerFactor = config.get("balancing", "cablePowerFactor", 1.0, "Multiplication factor for cable power capacity. We recommend 2.0 to 4.0 for larger modpacks, but 1.0 for Eln standalone, or if you like a challenge.", 0.5, 4.0).getDouble(1.0)

            Eln.fuelHeatValueFactor = config.get("balancing", "fuelHeatValueFactor", 0.0000675,
                "Factor to apply when converting real word heat values to Minecraft heat values (1mB = 1l).").getDouble()

            Eln.noSymbols = config.get("general", "noSymbols", false).getBoolean()
            Eln.noVoltageBackground = config.get("general", "noVoltageBackground", false).getBoolean()

            Eln.maxSoundDistance = config.get("debug", "maxSoundDistance", 16.0).getDouble()
            config.save()
        }
    }
}
