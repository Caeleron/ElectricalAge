package mods.eln.registry

import cpw.mods.fml.common.registry.GameRegistry
import mods.eln.Eln
import mods.eln.crafting.CraftingRegistry
import mods.eln.generic.GenericItemUsingDamageDescriptor
import mods.eln.generic.GenericItemUsingDamageDescriptorWithComment
import mods.eln.generic.genericArmorItem
import mods.eln.generic.genericArmorItem.ArmourType
import mods.eln.i18n.I18N
import mods.eln.item.BrushDescriptor
import mods.eln.item.CaseItemDescriptor
import mods.eln.item.CombustionChamber
import mods.eln.item.ConfigCopyToolDescriptor
import mods.eln.item.CopperCableDescriptor
import mods.eln.item.DielectricItem
import mods.eln.item.ElectricalDrillDescriptor
import mods.eln.item.ElectricalFuseDescriptor
import mods.eln.item.EntitySensorFilterDescriptor
import mods.eln.item.FerromagneticCoreDescriptor
import mods.eln.item.FuelBurnerDescriptor
import mods.eln.item.GraphiteDescriptor
import mods.eln.item.HeatingCorpElement
import mods.eln.item.ItemAxeEln
import mods.eln.item.ItemPickaxeEln
import mods.eln.item.LampDescriptor
import mods.eln.item.MachineBoosterDescriptor
import mods.eln.item.MiningPipeDescriptor
import mods.eln.item.OreScanner
import mods.eln.item.OverHeatingProtectionDescriptor
import mods.eln.item.OverVoltageProtectionDescriptor
import mods.eln.item.SolarTrackerDescriptor
import mods.eln.item.TreeResin
import mods.eln.item.electricalitem.BatteryItem
import mods.eln.item.electricalitem.ElectricalArmor
import mods.eln.item.electricalitem.ElectricalAxe
import mods.eln.item.electricalitem.ElectricalLampItem
import mods.eln.item.electricalitem.ElectricalPickaxe
import mods.eln.item.electricalitem.PortableOreScannerItem
import mods.eln.item.regulator.RegulatorAnalogDescriptor
import mods.eln.item.regulator.RegulatorOnOffDescriptor
import mods.eln.mechanical.ClutchPinItem
import mods.eln.mechanical.ClutchPlateItem
import mods.eln.sixnode.electricaldatalogger.DataLogsPrintDescriptor
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.wirelesssignal.WirelessSignalAnalyserItemDescriptor
import mods.eln.wiki.Data
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.Item.ToolMaterial
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemArmor.ArmorMaterial
import net.minecraft.item.ItemHoe
import net.minecraft.item.ItemSpade
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraftforge.common.util.EnumHelper
import net.minecraftforge.oredict.OreDictionary

class ItemRegistry {
    companion object {

        enum class IID (val id: Int) {
            HEATING_CORES(0),
            REGULATORS(1),
            LAMPS(2),
            PROTECTION(3),
            COMBUSTION_CHAMBER(4),
            FERROMAGNETIC_CORES(5),
            INGOTS(6),
            DUSTS(7),
            MOTOR_ITEM(8),
            SOLAR_TRACKER(9),
            METERS(10),
            DRILLS(11),
            ORE_SCANNER(12),
            MINING_PIPES(13),
            RESIN_RUBBER(14),
            RAW_CABLE(15),
            ARC_ITEMS(16),
            BRUSHES(17),
            MISC(18),
            ELECTRICAL_TOOLS(19),
            PORTABLE(20),
            FUEL_BURNERS(21),
            FUSES(22)
        }

        fun register() {
            registerHeatingCorp(IID.HEATING_CORES.id)
            registerRegulatorItem(IID.REGULATORS.id)
            registerLampItem(IID.LAMPS.id)
            registerProtection(IID.PROTECTION.id)
            registerCombustionChamber(IID.COMBUSTION_CHAMBER.id)
            registerFerromagneticCore(IID.FERROMAGNETIC_CORES.id)
            registerIngot(IID.INGOTS.id)
            registerDust(IID.DUSTS.id)
            registerElectricalMotor(IID.MOTOR_ITEM.id)
            registerSolarTracker( IID.SOLAR_TRACKER.id)
            registerMeter(IID.METERS.id)
            registerElectricalDrill(IID.DRILLS.id)
            registerOreScanner(IID.ORE_SCANNER.id)
            registerMiningPipe(IID.MINING_PIPES.id)
            registerTreeResinAndRubber(IID.RESIN_RUBBER.id)
            registerRawCable(IID.RAW_CABLE.id)
            registerArc(IID.ARC_ITEMS.id)
            registerBrush(IID.BRUSHES.id)
            registerMiscItem(IID.MISC.id)
            registerElectricalTool(IID.ELECTRICAL_TOOLS.id)
            registerPortableItem(IID.PORTABLE.id)
            registerFuelBurnerItem(IID.FUEL_BURNERS.id)
            registerFuses(IID.FUSES.id)

            registerArmor()
            registerTool()
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerItem(group: Int, subId: Int, element: GenericItemUsingDamageDescriptor) {
            Eln.sharedItem.addElement(subId + (group shl 6), element)
        }

        @Suppress("MemberVisibilityCanBePrivate")
        fun registerHiddenItem(group: Int, subId: Int, element: GenericItemUsingDamageDescriptor) {
            Eln.sharedItem.addWithoutRegistry(subId + (group shl 6), element)
        }

        private fun registerArmor() {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Helmet")
                Eln.helmetCopper = genericArmorItem(ArmorMaterial.IRON, 2, ArmourType.Helmet, "eln:textures/armor/copper_layer_1.png", "eln:textures/armor/copper_layer_2.png").setUnlocalizedName(name).setTextureName("eln:copper_helmet").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.helmetCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.helmetCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Chestplate")
                Eln.plateCopper = genericArmorItem(ArmorMaterial.IRON, 2, ArmourType.Chestplate, "eln:textures/armor/copper_layer_1.png", "eln:textures/armor/copper_layer_2.png").setUnlocalizedName(name).setTextureName("eln:copper_chestplate").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.plateCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.plateCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Leggings")
                Eln.legsCopper = genericArmorItem(ArmorMaterial.IRON, 2, ArmourType.Leggings, "eln:textures/armor/copper_layer_1.png", "eln:textures/armor/copper_layer_2.png").setUnlocalizedName(name).setTextureName("eln:copper_leggings").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.legsCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.legsCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Boots")
                Eln.bootsCopper = genericArmorItem(ArmorMaterial.IRON, 2, ArmourType.Boots, "eln:textures/armor/copper_layer_1.png", "eln:textures/armor/copper_layer_2.png").setUnlocalizedName(name).setTextureName("eln:copper_boots").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.bootsCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.bootsCopper))
            }
            val t1 = "eln:textures/armor/ecoal_layer_1.png"
            val t2 = "eln:textures/armor/ecoal_layer_2.png"
            val energyPerDamage = 500.0
            var armor: Int
            val eCoalMaterial = EnumHelper.addArmorMaterial("ECoal", 10, intArrayOf(3, 8, 6, 3), 9)
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "E-Coal Helmet")
                armor = 3
                Eln.helmetECoal = ElectricalArmor(eCoalMaterial, 2, ArmourType.Helmet, t1, t2,
                    8000.0, 2000.0,
                    armor / 20.0, armor * energyPerDamage,
                    energyPerDamage
                ).setUnlocalizedName(name).setTextureName("eln:ecoal_helmet").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.helmetECoal, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.helmetECoal))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "E-Coal Chestplate")
                armor = 8
                Eln.plateECoal = ElectricalArmor(eCoalMaterial, 2, ArmourType.Chestplate, t1, t2,
                    8000.0, 2000.0,
                    armor / 20.0, armor * energyPerDamage,
                    energyPerDamage
                ).setUnlocalizedName(name).setTextureName("eln:ecoal_chestplate").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.plateECoal, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.plateECoal))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "E-Coal Leggings")
                armor = 6
                Eln.legsECoal = ElectricalArmor(eCoalMaterial, 2, ArmourType.Leggings, t1, t2,
                    8000.0, 2000.0,  // double
                    armor / 20.0, armor * energyPerDamage,
                    energyPerDamage
                ).setUnlocalizedName(name).setTextureName("eln:ecoal_leggings").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.legsECoal, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.legsECoal))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "E-Coal Boots")
                armor = 3
                Eln.bootsECoal = ElectricalArmor(eCoalMaterial, 2, ArmourType.Boots, t1, t2,
                    8000.0, 2000.0,
                    armor / 20.0, armor * energyPerDamage,
                    energyPerDamage
                ).setUnlocalizedName(name).setTextureName("eln:ecoal_boots").setCreativeTab(Eln.creativeTab) as ItemArmor
                GameRegistry.registerItem(Eln.bootsECoal, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.bootsECoal))
            }
        }

        private fun registerTool() {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Sword")
                Eln.swordCopper = ItemSword(ToolMaterial.IRON).setUnlocalizedName(name).setTextureName("eln:copper_sword").setCreativeTab(Eln.creativeTab)
                GameRegistry.registerItem(Eln.swordCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.swordCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Hoe")
                Eln.hoeCopper = ItemHoe(ToolMaterial.IRON).setUnlocalizedName(name).setTextureName("eln:copper_hoe").setCreativeTab(Eln.creativeTab)
                GameRegistry.registerItem(Eln.hoeCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.hoeCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Shovel")
                Eln.shovelCopper = ItemSpade(ToolMaterial.IRON).setUnlocalizedName(name).setTextureName("eln:copper_shovel").setCreativeTab(Eln.creativeTab)
                GameRegistry.registerItem(Eln.shovelCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.shovelCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Pickaxe")
                Eln.pickaxeCopper = ItemPickaxeEln(ToolMaterial.IRON).setUnlocalizedName(name).setTextureName("eln:copper_pickaxe").setCreativeTab(Eln.creativeTab)
                GameRegistry.registerItem(Eln.pickaxeCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.pickaxeCopper))
            }
            run {
                name = I18N.TR_NAME(I18N.Type.ITEM, "Copper Axe")
                Eln.axeCopper = ItemAxeEln(ToolMaterial.IRON).setUnlocalizedName(name).setTextureName("eln:copper_axe").setCreativeTab(Eln.creativeTab)
                GameRegistry.registerItem(Eln.axeCopper, "Eln.$name")
                GameRegistry.registerCustomItemStack(name, ItemStack(Eln.axeCopper))
            }
        }

        private fun registerHeatingCorp(id: Int) {
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Copper Heating Corp"),
                    Eln.LVU, 150.0,
                    190.0,
                    Eln.lowVoltageCableDescriptor
                )
                registerItem(id, 0, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Copper Heating Corp"),
                    Eln.LVU, 250.0,
                    320.0,
                    Eln.lowVoltageCableDescriptor)
                registerItem(id, 1, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 200V Copper Heating Corp"),
                    Eln.MVU, 400.0,
                    500.0,
                    Eln.meduimVoltageCableDescriptor)
                registerItem(id, 2, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Copper Heating Corp"),
                    Eln.MVU, 600.0,
                    750.0,
                    Eln.highVoltageCableDescriptor)
                registerItem(id, 3, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Iron Heating Corp"),
                    Eln.LVU, 180.0,
                    225.0,
                    Eln.lowVoltageCableDescriptor
                )
                registerItem(id, 4, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Iron Heating Corp"),
                    Eln.LVU, 375.0,
                    480.0,
                    Eln.lowVoltageCableDescriptor)
                registerItem(id, 5, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 200V Iron Heating Corp"),
                    Eln.MVU, 600.0,
                    750.0,
                    Eln.meduimVoltageCableDescriptor)
                registerItem(id, 6, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Iron Heating Corp"),
                    Eln.MVU, 900.0,
                    1050.0,
                    Eln.highVoltageCableDescriptor)
                registerItem(id, 7, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Tungsten Heating Corp"),
                    Eln.LVU, 240.0,
                    300.0,
                    Eln.lowVoltageCableDescriptor
                )
                registerItem(id, 8, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Tungsten Heating Corp"),
                    Eln.LVU, 500.0,
                    640.0,
                    Eln.lowVoltageCableDescriptor)
                registerItem(id, 9, element)
            }
            run {
                val element = HeatingCorpElement(
                    I18N.TR_NAME(I18N.Type.NONE, "Small 200V Tungsten Heating Corp"),
                    Eln.MVU, 800.0,
                    1000.0,
                    Eln.meduimVoltageCableDescriptor)
                registerItem(id, 10, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Tungsten Heating Corp"),
                    Eln.MVU, 1200.0,
                    1500.0,
                    Eln.highVoltageCableDescriptor)
                registerItem(id, 11, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 800V Tungsten Heating Corp"),
                    Eln.HVU, 3600.0,
                    4800.0,
                    Eln.veryHighVoltageCableDescriptor)
                registerItem(id, 12, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "800V Tungsten Heating Corp"),
                    Eln.HVU, 4812.0,
                    6015.0,
                    Eln.veryHighVoltageCableDescriptor)
                registerItem(id, 13, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 3.2kV Tungsten Heating Corp"),
                    Eln.VVU, 4000.0,
                    6000.0,
                    Eln.veryHighVoltageCableDescriptor)
                registerItem(id, 14, element)
            }
            run {
                val element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "3.2kV Tungsten Heating Corp"),
                    Eln.VVU, 12000.0,
                    15000.0,
                    Eln.veryHighVoltageCableDescriptor)
                registerItem(id, 15, element)
            }
        }

        private fun registerRegulatorItem(id: Int) {
            run {
                val element = RegulatorOnOffDescriptor(I18N.TR_NAME(I18N.Type.NONE, "On/OFF Regulator 1 Percent"),
                    "onoffregulator", 0.01)
                registerItem(id, 0, element)
            }
            run {
                val element = RegulatorOnOffDescriptor(I18N.TR_NAME(I18N.Type.NONE, "On/OFF Regulator 10 Percent"),
                    "onoffregulator", 0.1)
                registerItem(id, 1, element)
            }
            run {
                val element = RegulatorAnalogDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Analogic Regulator"),
                    "Analogicregulator")
                registerItem(id, 2, element)
            }
        }

        private fun registerLampItem(id: Int) {
            val lightPower = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 15.0, 20.0, 25.0, 30.0, 40.0)
            val lightLevel = DoubleArray(16)
            val economicPowerFactor = 0.5
            val standardGrowRate = 0.0
            for (idx in 0..15) {
                lightLevel[idx] = (idx + 0.49) / 15.0
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[12],
                    lightLevel[12], Eln.incandescentLampLife, standardGrowRate
                )
                registerItem(id, 0, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[14],
                    lightLevel[14], Eln.incandescentLampLife, standardGrowRate
                )
                registerItem(id, 1, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.MVU, lightPower[14],
                    lightLevel[14], Eln.incandescentLampLife, standardGrowRate
                )
                registerItem(id, 2, element)
            }
            run {
                val element = LampDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Small 50V Carbon Incandescent Light Bulb"),
                    "incandescentcarbonlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[11],
                    lightLevel[11], Eln.carbonLampLife, standardGrowRate
                )
                registerItem(id, 3, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Carbon Incandescent Light Bulb"),
                    "incandescentcarbonlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[13],
                    lightLevel[13], Eln.carbonLampLife, standardGrowRate
                )
                registerItem(id, 4, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.LVU, lightPower[12]
                    * economicPowerFactor,
                    lightLevel[12], Eln.economicLampLife, standardGrowRate
                )
                registerItem(id, 5, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.LVU, lightPower[14]
                    * economicPowerFactor,
                    lightLevel[14], Eln.economicLampLife, standardGrowRate
                )
                registerItem(id, 6, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.MVU, lightPower[14]
                    * economicPowerFactor,
                    lightLevel[14], Eln.economicLampLife, standardGrowRate
                )
                registerItem(id, 7, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Farming Lamp"),
                    "farminglamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, 120.0,
                    lightLevel[15], Eln.incandescentLampLife, 0.50
                )
                registerItem(id, 8, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Farming Lamp"),
                    "farminglamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.MVU, 120.0,
                    lightLevel[15], Eln.incandescentLampLife, 0.50
                )
                registerItem(id, 9, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V LED Bulb"),
                    "ledlamp", LampDescriptor.Type.LED,
                    LampSocketType.Douille, Eln.LVU, lightPower[14] / 2,
                    lightLevel[14], Eln.ledLampLife, standardGrowRate
                )
                registerItem(id, 10, element)
            }
            run {
                val element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V LED Bulb"),
                    "ledlamp", LampDescriptor.Type.LED,
                    LampSocketType.Douille, Eln.MVU, lightPower[14] / 2,
                    lightLevel[14], Eln.ledLampLife, standardGrowRate
                )
                registerItem(id, 11, element)
            }
        }

        private fun registerProtection(id: Int) {
            run {
                val element = OverHeatingProtectionDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Overheating Protection"))
                registerItem(id, 0, element)
            }
            run {
                val element = OverVoltageProtectionDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Overvoltage Protection"))
                registerItem(id, 1, element)
            }
        }

        private fun registerCombustionChamber(id: Int) {
            run {
                val element = CombustionChamber(I18N.TR_NAME(I18N.Type.NONE, "Combustion Chamber"))
                registerItem(id, 0, element)
            }
        }

        private fun registerFerromagneticCore(id: Int) {
            run {
                val element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Cheap Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    100.0)
                registerItem(id, 0, element)
            }
            run {
                val element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Average Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    50.0)
                registerItem(id, 1, element)
            }
            run {
                val element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Optimal Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    1.0)
                registerItem(id, 2, element)
            }
        }

        private fun registerIngot(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Ingot")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.copperIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotCopper", element.newItemStack())
                registerItem(id, 0, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Ingot")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.plumbIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotLead", element.newItemStack())
                registerItem(id, 1, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Ingot")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.tungstenIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre(Eln.dictTungstenIngot, element.newItemStack())
                registerItem(id, 2, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Ferrite Ingot")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf("useless", "Really useless"))
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotFerrite", element.newItemStack())
                registerItem(id, 3, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Ingot")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotAlloy", element.newItemStack())
                registerItem(id, 4, element)
            }
        }

        private fun registerDust(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustCopper", element.newItemStack())
                registerItem(id, 0, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustIron", element.newItemStack())
                registerItem(id, 1, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lapis Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustLapis", element.newItemStack())
                registerItem(id, 2, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Diamond Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustDiamond", element.newItemStack())
                registerItem(id, 3, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustLead", element.newItemStack())
                registerItem(id, 4, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre(Eln.dictTungstenDust, element.newItemStack())
                registerItem(id, 5, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Gold Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustGold", element.newItemStack())
                registerItem(id, 6, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Coal Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustCoal", element.newItemStack())
                registerItem(id, 7, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Dust")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustAlloy", element.newItemStack())
                registerItem(id, 8, element)
            }
        }

        private fun registerElectricalMotor(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Motor")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                registerItem(id, 0, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Electrical Motor")
                val element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Data.addResource(element.newItemStack())
                registerItem(id, 1, element)
            }
        }

        private fun registerSolarTracker(id: Int) {
            run {
                val element = SolarTrackerDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Solar Tracker") // iconId, name,
                )
                registerItem(id, 0, element)
            }
        }

        private fun registerMeter(id: Int) {
            run {
                val element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "MultiMeter"))
                Eln.multiMeterElement = element
                registerItem(id, 0, element)
            }
            run {
                val element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Thermometer"))
                Eln.thermometerElement = element
                registerItem(id, 1, element)
            }
            run {
                val element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "AllMeter"))
                Eln.allMeterElement = element
                registerItem(id, 2, element)
            }
            run {
                val element = WirelessSignalAnalyserItemDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Wireless Analyser"))
                registerItem(id, 3, element)
            }
            run {
                val element = ConfigCopyToolDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Config Copy Tool"))
                Eln.configCopyToolElement = element
                registerItem(id, 4, element)
            }
        }

        private fun registerElectricalDrill(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Cheap Electrical Drill")
                val element = ElectricalDrillDescriptor(name,
                    8.0, 4000.0
                )
                registerItem(id, 0, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Average Electrical Drill")
                val element = ElectricalDrillDescriptor(name,
                    5.0, 5000.0
                )
                registerItem(id, 1, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Fast Electrical Drill")
                val element = ElectricalDrillDescriptor(name,
                    3.0, 6000.0
                )
                registerItem(id, 2, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Turbo Electrical Drill")
                val element = ElectricalDrillDescriptor(name,
                    1.0, 10000.0
                )
                registerItem(id, 3, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Irresponsible Electrical Drill")
                val element = ElectricalDrillDescriptor(name,
                    0.1, 20000.0
                )
                registerItem(id, 4, element)
            }
        }

        private fun registerOreScanner(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Ore Scanner")
                val element = OreScanner(name)
                registerItem(id, 0, element)
            }
        }

        private fun registerMiningPipe(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Mining Pipe")
                val element = MiningPipeDescriptor(name)
                Eln.miningPipeDescriptor = element
                registerItem(id, 0, element)
            }
        }

        private fun registerTreeResinAndRubber(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Tree Resin")
                val element = TreeResin(name)
                Eln.treeResin = element
                CraftingRegistry.addToOre("materialResin", element.newItemStack())
                registerItem(id, 0, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Rubber")
                val element = GenericItemUsingDamageDescriptor(name)
                CraftingRegistry.addToOre("itemRubber", element.newItemStack())
                registerItem(id, 1, element)
            }
        }

        private fun registerRawCable(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Cable")
                Eln.copperCableDescriptor = CopperCableDescriptor(name)
                Data.addResource(Eln.copperCableDescriptor.newItemStack())
                registerItem(id, 0, Eln.copperCableDescriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Cable")
                val element = GenericItemUsingDamageDescriptor(name)
                Data.addResource(element.newItemStack())
                registerItem(id, 1, element)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Cable")
                val element = GenericItemUsingDamageDescriptor(name)
                Data.addResource(element.newItemStack())
                registerItem(id, 2, element)
            }
        }

        private fun registerArc(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Graphite Rod")
                Eln.GraphiteDescriptor = GraphiteDescriptor(name)
                Data.addResource(Eln.GraphiteDescriptor.newItemStack())
                registerItem(id, 0, Eln.GraphiteDescriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "2x Graphite Rods")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 1, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "3x Graphite Rods")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 2, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "4x Graphite Rods")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 3, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Synthetic Diamond")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 4, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "unreleasedium")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 5, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Arc Clay Ingot")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 6, descriptor)
                OreDictionary.registerOre("ingotAluminum", descriptor.newItemStack())
                OreDictionary.registerOre("ingotAluminium", descriptor.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Arc Metal Ingot")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 7, descriptor)
                OreDictionary.registerOre("ingotSteel", descriptor.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Inert Canister")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 8, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Canister of Water")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 9, descriptor)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Canister of Arc Water")
                val descriptor = GenericItemUsingDamageDescriptor(name)
                Data.addResource(descriptor.newItemStack())
                registerItem(id, 10, descriptor)
            }
        }

        private fun registerBrush(id: Int) {
            var subId: Int
            //var whiteDesc: BrushDescriptor? = null
            var name: String
            val subNames = arrayOf(
                I18N.TR_NAME(I18N.Type.NONE, "Black Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Red Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Green Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Brown Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Blue Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Purple Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Cyan Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Silver Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Gray Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Pink Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Lime Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Yellow Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Light Blue Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Magenta Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "Orange Brush"),
                I18N.TR_NAME(I18N.Type.NONE, "White Brush"))
            for (idx in 0..15) {
                subId = idx
                name = subNames[idx]
                val desc = BrushDescriptor(name)
                registerItem(id, subId, desc)
                //whiteDesc = desc
            }
            /*
            TODO: Re-enable this.

            val emptyStack: ItemStack = CraftingRegistry.findItemStack("White Brush", 1)
            whiteDesc!!.setLife(emptyStack, 0)
            for (idx in 0..15) {
                CraftingRegistry.addShapelessRecipe(emptyStack.copy(),
                    ItemStack(Blocks.wool, 1, idx),
                    CraftingRegistry.findItemStack("Iron Cable", 1))
            }
            for (idx in 0..15) {
                name = subNames[idx]
                CraftingRegistry.addShapelessRecipe(CraftingRegistry.findItemStack(name, 1),
                    ItemStack(Items.dye, 1, idx),
                    emptyStack.copy())
            }
             */
        }

        private fun registerMiscItem(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Cheap Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 0, desc)
                Data.addResource(desc.newItemStack())
                OreDictionary.registerOre(Eln.dictCheapChip, desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 1, desc)
                Data.addResource(desc.newItemStack())
                OreDictionary.registerOre(Eln.dictAdvancedChip, desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Machine Block")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 2, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("casingMachine", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Probe Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 3, desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermal Probe Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 4, desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 5, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateCopper", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 6, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateIron", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Gold Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 7, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateGold", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 8, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateLead", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 9, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateSilicon", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 10, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateAlloy", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Coal Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 11, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateCoal", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Dust")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 12, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("dustSilicon", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Ingot")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 13, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("ingotSilicon", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Machine Booster")
                val desc = MachineBoosterDescriptor(name)
                registerItem(id, 14, desc)
            }
            run {
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    I18N.TR_NAME(I18N.Type.NONE, "Advanced Machine Block"), arrayOf()) // TODO: Description.
                registerItem(id, 15, desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("casingMachineAdvanced", desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Basic Magnet")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 16, desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Magnet")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 17, desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Data Logger Print")
                val desc = DataLogsPrintDescriptor(name)
                Eln.dataLogsPrintDescriptor = desc
                desc.setDefaultIcon("empty-texture")
                registerHiddenItem(id, 18, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Antenna")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                registerItem(id, 19, desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Player Filter")
                val desc = EntitySensorFilterDescriptor(name, EntityPlayer::class.java, 0f, 1f, 0f)
                registerItem(id, 20, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Monster Filter")
                val desc = EntitySensorFilterDescriptor(name, IMob::class.java, 1f, 0f, 0f)
                registerItem(id, 21, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Animal Filter")
                val desc = EntitySensorFilterDescriptor(name, EntityAnimal::class.java, .3f, .3f, 1f)
                registerItem(id,  22, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Wrench")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, I18N.TR("Electrical age wrench,\nCan be used to turn\nsmall wall blocks").split("\n".toRegex()).toTypedArray())
                registerItem(id, 23, desc)
                Eln.wrenchItemStack = desc.newItemStack()
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Dielectric")
                val desc = DielectricItem(name, Eln.LVU)
                registerItem(id, 24, desc)
            }

            registerItem(id, 25, CaseItemDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Casing")))
            registerItem(id, 26, ClutchPinItem("Clutch Pin"))
            registerItem(id, 27, ClutchPlateItem("Iron Clutch Plate", 5120f, 640f, 640f, 160f, 0.0001f, false))
            registerItem(id, 28, ClutchPlateItem("Gold Clutch Plate", 10240f, 2048f, 1024f, 512f, 0.001f, false))
            registerItem(id, 29, ClutchPlateItem("Copper Clutch Plate", 8192f, 4096f, 1024f, 512f, 0.0003f, false))
            registerItem(id, 30, ClutchPlateItem("Lead Clutch Plate", 15360f, 1024f, 1536f, 768f, 0.0015f, false))
            registerItem(id, 31, ClutchPlateItem("Coal Clutch Plate", 1024f, 128f, 128f, 32f, 0.1f, true))
        }

        private fun registerElectricalTool(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Small Flashlight")
                val desc = ElectricalLampItem(
                    name,  //10, 8, 20, 15, 5, 50, old
                    10, 8, 20.0, 15, 5, 50.0,  // int light,int range
                    6000.0, 100.0 // , energyStorage,discharg, charge
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Electrical Mining Drill")
                val desc = ElectricalPickaxe(
                    name,
                    22f, 1f,  // float strengthOn,float strengthOff, - Haxorian note: buffed this from 8,3 putting it around eff 4
                    40000.0, 200.0, 10000.0 // double energyStorage,double
                    // energyPerBlock,double chargePower
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 12
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Electrical Axe")
                val desc = ElectricalAxe(
                    name,
                    22f, 1f,  // float strengthOn,float strengthOff, - Haxorian note: buffed this too
                    40000.0, 200.0, 10000.0 // double energyStorage,double energyPerBlock,double chargePower
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
        }

        private fun registerPortableItem(id: Int) {
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Battery")
                val desc = BatteryItem(
                    name,
                    40000.0, 125.0, 250.0,  // double energyStorage,double - Haxorian note: doubled storage halved throughput.
                    // chargePower,double dischargePower,
                    2 // int priority
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Battery Pack")
                val desc = BatteryItem(
                    name,
                    160000.0, 500.0, 1000.0,  // double energyStorage,double - Haxorian note: Packs are in 4s now
                    // chargePower,double dischargePower,
                    2 // int priority
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Condensator")
                val desc = BatteryItem(
                    name,
                    4000.0, 2000.0, 2000.0,  // double energyStorage,double - H: Slightly less power way more throughput
                    // chargePower,double dischargePower,
                    1 // int priority
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 17
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable Condensator Pack")
                val desc = BatteryItem(
                    name,
                    16000.0, 8000.0, 8000.0,  // double energyStorage,double
                    // chargePower,double dischargePower,
                    1 // int priority
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 32
                name = I18N.TR_NAME(I18N.Type.NONE, "X-Ray Scanner")
                val desc = PortableOreScannerItem(
                    name, Eln.obj.getObj("XRayScanner"),
                    100000.0, 400.0, 300.0,  // double energyStorage,double - That's right, more buffs!
                    // chargePower,double dischargePower,
                    Eln.xRayScannerRange.toFloat(), (Math.PI / 2).toFloat(),  // float
                    // viewRange,float
                    // viewYAlpha,
                    32, 20 // int resWidth,int resHeight
                )
                Eln.sharedItemStackOne.addElement(subId + (id shl 6), desc)
            }
        }

        private fun registerFuelBurnerItem(id: Int) {
            Eln.sharedItemStackOne.addElement(0 + (id shl 6),
                FuelBurnerDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Small Fuel Burner"), 5000 * Eln.fuelHeatFurnacePowerFactor, 2, 1.6f))
            Eln.sharedItemStackOne.addElement(1 + (id shl 6),
                FuelBurnerDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Medium Fuel Burner"), 10000 * Eln.fuelHeatFurnacePowerFactor, 1, 1.4f))
            Eln.sharedItemStackOne.addElement(2 + (id shl 6),
                FuelBurnerDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Big Fuel Burner"), 25000 * Eln.fuelHeatFurnacePowerFactor, 0, 1f))
        }

        private fun registerFuses(id: Int) {
            var name: String
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for low voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.lowVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                registerItem(id, 0, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for medium voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.meduimVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                registerItem(id, 1, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for high voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.highVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                registerItem(id, 2, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Fuse for very high voltage cables")
                val desc = ElectricalFuseDescriptor(name, Eln.veryHighVoltageCableDescriptor, Eln.obj.getObj("ElectricalFuse"))
                registerItem(id, 3, desc)
            }
            run {
                name = I18N.TR_NAME(I18N.Type.NONE, "Blown Lead Fuse")
                val desc = ElectricalFuseDescriptor(name, null, Eln.obj.getObj("ElectricalFuse"))
                ElectricalFuseDescriptor.BlownFuse = desc
                registerItem(id, 4, desc)
            }
        }
    }
}
