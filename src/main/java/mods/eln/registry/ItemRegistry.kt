package mods.eln.registry

import cpw.mods.fml.common.registry.GameRegistry
import mods.eln.Eln
import mods.eln.cable.CableRenderDescriptor
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
import mods.eln.item.regulator.IRegulatorDescriptor
import mods.eln.item.regulator.RegulatorAnalogDescriptor
import mods.eln.item.regulator.RegulatorOnOffDescriptor
import mods.eln.mechanical.ClutchPinItem
import mods.eln.mechanical.ClutchPlateItem
import mods.eln.sixnode.PortableNaNDescriptor
import mods.eln.sixnode.electricaldatalogger.DataLogsPrintDescriptor
import mods.eln.sixnode.lampsocket.LampSocketType
import mods.eln.sixnode.wirelesssignal.WirelessSignalAnalyserItemDescriptor
import mods.eln.wiki.Data
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.EntityAnimal
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Items
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
        fun register() {
            //ITEM REGISTRATION
            //Sub-UID must be unique in this section only.
            //============================================
            registerHeatingCorp(1)
            registerRegulatorItem(3)
            registerLampItem(4)
            registerProtection(5)
            registerCombustionChamber(6)
            registerFerromagneticCore(7)
            registerIngot(8)
            registerDust(9)
            registerElectricalMotor(10)
            registerSolarTracker(11)
            registerMeter(14)
            registerElectricalDrill(15)
            registerOreScanner(16)
            registerMiningPipe(17)
            registerTreeResinAndRubber(64)
            registerRawCable(65)
            registerArc(69)
            registerBrush(119)
            registerMiscItem(120)
            registerElectricalTool(121)
            registerPortableItem(122)
            registerFuelBurnerItem(124)
            registerPortableNaN() // 125

            registerArmor()
            registerTool()
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
            val t1: String
            val t2: String
            t1 = "eln:textures/armor/ecoal_layer_1.png"
            t2 = "eln:textures/armor/ecoal_layer_2.png"
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
            var subId: Int
            var completId: Int
            var element: HeatingCorpElement
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Copper Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 150.0,  // electricalNominalU, electricalNominalP,
                    190.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor // ElectricalCableDescriptor
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Copper Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 250.0,  // electricalNominalU, electricalNominalP,
                    320.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 200V Copper Heating Corp"),  // iconId,
                    // name,
                    Eln.MVU, 400.0,  // electricalNominalU, electricalNominalP,
                    500.0,  // electricalMaximalP)
                    Eln.meduimVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 3
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Copper Heating Corp"),  // iconId,
                    // name,
                    Eln.MVU, 600.0,  // electricalNominalU, electricalNominalP,
                    750.0,  // electricalMaximalP)
                    Eln.highVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Iron Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 180.0,  // electricalNominalU, electricalNominalP,
                    225.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor // ElectricalCableDescriptor
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 5
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Iron Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 375.0,  // electricalNominalU, electricalNominalP,
                    480.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 6
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 200V Iron Heating Corp"),  // iconId,
                    // name,
                    Eln.MVU, 600.0,  // electricalNominalU, electricalNominalP,
                    750.0,  // electricalMaximalP)
                    Eln.meduimVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 7
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Iron Heating Corp"),  // iconId,
                    // name,
                    Eln.MVU, 900.0,  // electricalNominalU, electricalNominalP,
                    1050.0,  // electricalMaximalP)
                    Eln.highVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 8
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 240.0,  // electricalNominalU, electricalNominalP,
                    300.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor // ElectricalCableDescriptor
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 9
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "50V Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.LVU, 500.0,  // electricalNominalU, electricalNominalP,
                    640.0,  // electricalMaximalP)
                    Eln.lowVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 10
                completId = subId + (id shl 6)
                element = HeatingCorpElement(
                    I18N.TR_NAME(I18N.Type.NONE, "Small 200V Tungsten Heating Corp"),  // iconId, name,
                    Eln.MVU, 800.0,  // electricalNominalU, electricalNominalP,
                    1000.0,  // electricalMaximalP)
                    Eln.meduimVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 11
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "200V Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.MVU, 1200.0,  // electricalNominalU, electricalNominalP,
                    1500.0,  // electricalMaximalP)
                    Eln.highVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 12
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 800V Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.HVU, 3600.0,  // electricalNominalU, electricalNominalP,
                    4800.0,  // electricalMaximalP)
                    Eln.veryHighVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 13
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "800V Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.HVU, 4812.0,  // electricalNominalU, electricalNominalP,
                    6015.0,  // electricalMaximalP)
                    Eln.veryHighVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 14
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "Small 3.2kV Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.VVU, 4000.0,  // electricalNominalU, electricalNominalP,
                    6000.0,  // electricalMaximalP)
                    Eln.veryHighVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 15
                completId = subId + (id shl 6)
                element = HeatingCorpElement(I18N.TR_NAME(I18N.Type.NONE, "3.2kV Tungsten Heating Corp"),  // iconId,
                    // name,
                    Eln.VVU, 12000.0,  // electricalNominalU, electricalNominalP,
                    15000.0,  // electricalMaximalP)
                    Eln.veryHighVoltageCableDescriptor)
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerRegulatorItem(id: Int) {
            var subId: Int
            var completId: Int
            var element: IRegulatorDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = RegulatorOnOffDescriptor(I18N.TR_NAME(I18N.Type.NONE, "On/OFF Regulator 1 Percent"),
                    "onoffregulator", 0.01)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                element = RegulatorOnOffDescriptor(I18N.TR_NAME(I18N.Type.NONE, "On/OFF Regulator 10 Percent"),
                    "onoffregulator", 0.1)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 8
                completId = subId + (id shl 6)
                element = RegulatorAnalogDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Analogic Regulator"),
                    "Analogicregulator")
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerLampItem(id: Int) {
            var subId: Int
            var completId: Int
            val lightPower = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 15.0, 20.0, 25.0, 30.0, 40.0)
            val lightLevel = DoubleArray(16)
            val economicPowerFactor = 0.5
            val standardGrowRate = 0.0
            for (idx in 0..15) {
                lightLevel[idx] = (idx + 0.49) / 15.0
            }
            var element: LampDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[12],
                    lightLevel[12], Eln.incandescentLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[14],
                    lightLevel[14], Eln.incandescentLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Incandescent Light Bulb"),
                    "incandescentironlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.MVU, lightPower[14],
                    lightLevel[14], Eln.incandescentLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                element = LampDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Small 50V Carbon Incandescent Light Bulb"),
                    "incandescentcarbonlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[11],
                    lightLevel[11], Eln.carbonLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 5
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Carbon Incandescent Light Bulb"),
                    "incandescentcarbonlamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, lightPower[13],
                    lightLevel[13], Eln.carbonLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 16
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Small 50V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.LVU, lightPower[12]
                    * economicPowerFactor,
                    lightLevel[12], Eln.economicLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 17
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.LVU, lightPower[14]
                    * economicPowerFactor,
                    lightLevel[14], Eln.economicLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 18
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Economic Light Bulb"),
                    "fluorescentlamp", LampDescriptor.Type.ECO,
                    LampSocketType.Douille, Eln.MVU, lightPower[14]
                    * economicPowerFactor,
                    lightLevel[14], Eln.economicLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 32
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V Farming Lamp"),
                    "farminglamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.LVU, 120.0,
                    lightLevel[15], Eln.incandescentLampLife, 0.50
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 36
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V Farming Lamp"),
                    "farminglamp", LampDescriptor.Type.INCANDESCENT,
                    LampSocketType.Douille, Eln.MVU, 120.0,
                    lightLevel[15], Eln.incandescentLampLife, 0.50
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 37
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "50V LED Bulb"),
                    "ledlamp", LampDescriptor.Type.LED,
                    LampSocketType.Douille, Eln.LVU, lightPower[14] / 2,
                    lightLevel[14], Eln.ledLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 38
                completId = subId + (id shl 6)
                element = LampDescriptor(I18N.TR_NAME(I18N.Type.NONE, "200V LED Bulb"),
                    "ledlamp", LampDescriptor.Type.LED,
                    LampSocketType.Douille, Eln.MVU, lightPower[14] / 2,
                    lightLevel[14], Eln.ledLampLife, standardGrowRate
                )
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerProtection(id: Int) {
            var subId: Int
            var completId: Int
            run {
                subId = 0
                completId = subId + (id shl 6)
                val element = OverHeatingProtectionDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Overheating Protection"))
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                val element = OverVoltageProtectionDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Overvoltage Protection"))
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerCombustionChamber(id: Int) {
            var subId: Int
            var completId: Int
            run {
                subId = 0
                completId = subId + (id shl 6)
                val element = CombustionChamber(I18N.TR_NAME(I18N.Type.NONE, "Combustion Chamber"))
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerFerromagneticCore(id: Int) {
            var subId: Int
            var completId: Int
            var element: FerromagneticCoreDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Cheap Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    100.0)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Average Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    50.0)
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                element = FerromagneticCoreDescriptor(
                    I18N.TR_NAME(I18N.Type.NONE, "Optimal Ferromagnetic Core"), Eln.obj.getObj("feromagneticcorea"),  // iconId,
                    // name,
                    1.0)
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerIngot(id: Int) {
            var subId: Int
            var completId: Int
            var name: String?
            var element: GenericItemUsingDamageDescriptorWithComment
            run {
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Ingot")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Eln.copperIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotCopper", element.newItemStack())
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Ingot")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Eln.plumbIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotLead", element.newItemStack())
            }
            run {
                subId = 5
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Ingot")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Eln.tungstenIngot = element
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre(Eln.dictTungstenIngot, element.newItemStack())
            }
            run {
                subId = 6
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Ferrite Ingot")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf("useless", "Really useless"))
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotFerrite", element.newItemStack())
            }
            run {
                subId = 7
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Ingot")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("ingotAlloy", element.newItemStack())
            }
            run {
                subId = 8
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Mercury")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf("useless", "miaou"))
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("quicksilver", element.newItemStack())
            }
        }

        private fun registerDust(id: Int) {
            var id = id
            var subId: Int
            var completId: Int
            var name: String?
            var element: GenericItemUsingDamageDescriptorWithComment
            run {
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Eln.sharedItem.addElement(completId, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustCopper", element.newItemStack())
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Eln.sharedItem.addElement(completId, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustIron", element.newItemStack())
            }
            run {
                subId = 3
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Lapis Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Eln.sharedItem.addElement(completId, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustLapis", element.newItemStack())
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Diamond Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.dustCopper = element
                Eln.sharedItem.addElement(completId, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustDiamond", element.newItemStack())
            }
            run {
                id = 5
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustLead", element.newItemStack())
            }
            run {
                id = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre(Eln.dictTungstenDust, element.newItemStack())
            }
            run {
                id = 7
                name = I18N.TR_NAME(I18N.Type.NONE, "Gold Dust")
                element = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustGold", element.newItemStack())
            }
            run {
                id = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Coal Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustCoal", element.newItemStack())
            }
            run {
                id = 9
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustAlloy", element.newItemStack())
            }
            run {
                id = 10
                name = I18N.TR_NAME(I18N.Type.NONE, "Cinnabar Dust")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(id, element)
                Data.addResource(element.newItemStack())
                CraftingRegistry.addToOre("dustCinnabar", element.newItemStack())
            }
        }

        private fun registerElectricalMotor(id: Int) {
            var subId: Int
            var completId: Int
            var name: String?
            var element: GenericItemUsingDamageDescriptorWithComment
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Motor")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Data.addResource(element.newItemStack())
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Electrical Motor")
                element = GenericItemUsingDamageDescriptorWithComment(name, arrayOf())
                Eln.sharedItem.addElement(completId, element)
                // GameRegistry.registerCustomItemStack(name,
                // element.newItemStack(1));
                Data.addResource(element.newItemStack())
            }
        }

        private fun registerSolarTracker(id: Int) {
            var subId: Int
            var completId: Int
            var element: SolarTrackerDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = SolarTrackerDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Solar Tracker") // iconId, name,
                )
                Eln.sharedItem.addElement(completId, element)
            }
        }

        private fun registerMeter(id: Int) {
            var subId: Int
            var completId: Int
            var element: GenericItemUsingDamageDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "MultiMeter"))
                Eln.sharedItem.addElement(completId, element)
                Eln.multiMeterElement = element
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Thermometer"))
                Eln.sharedItem.addElement(completId, element)
                Eln.thermometerElement = element
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                element = GenericItemUsingDamageDescriptor(I18N.TR_NAME(I18N.Type.NONE, "AllMeter"))
                Eln.sharedItem.addElement(completId, element)
                Eln.allMeterElement = element
            }
            run {
                subId = 8
                completId = subId + (id shl 6)
                element = WirelessSignalAnalyserItemDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Wireless Analyser"))
                Eln.sharedItem.addElement(completId, element)
            }
            run {
                subId = 16
                completId = subId + (id shl 6)
                element = ConfigCopyToolDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Config Copy Tool"))
                Eln.sharedItem.addElement(completId, element)
                Eln.configCopyToolElement = element
            }
        }

        private fun registerElectricalDrill(id: Int) {
            var subId: Int
            var completId: Int
            var name: String?
            var descriptor: ElectricalDrillDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Cheap Electrical Drill")
                descriptor = ElectricalDrillDescriptor(name,  // iconId, name,
                    8.0, 4000.0 // double operationTime,double operationEnergy
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
            run {
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Average Electrical Drill")
                descriptor = ElectricalDrillDescriptor(name,  // iconId, name,
                    5.0, 5000.0 // double operationTime,double operationEnergy
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
            run {
                subId = 2
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Fast Electrical Drill")
                descriptor = ElectricalDrillDescriptor(name,  // iconId, name,
                    3.0, 6000.0 // double operationTime,double operationEnergy
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
            run {
                subId = 3
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Turbo Electrical Drill")
                descriptor = ElectricalDrillDescriptor(name,  // iconId, name,
                    1.0, 10000.0 // double operationTime,double operationEnergy
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
            run {
                subId = 4
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Irresponsible Electrical Drill")
                descriptor = ElectricalDrillDescriptor(name,  // iconId, name,
                    0.1, 20000.0 // double operationTime,double operationEnergy
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
        }

        private fun registerOreScanner(id: Int) {
            var subId: Int
            var completId: Int
            var name: String?
            var descriptor: OreScanner
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Ore Scanner")
                descriptor = OreScanner(name
                )
                Eln.sharedItem.addElement(completId, descriptor)
            }
        }

        private fun registerMiningPipe(id: Int) {
            var subId: Int
            var completId: Int
            var name: String?
            var descriptor: MiningPipeDescriptor
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Mining Pipe")
                descriptor = MiningPipeDescriptor(name // iconId, name
                )
                Eln.sharedItem.addElement(completId, descriptor)
                Eln.miningPipeDescriptor = descriptor
            }
        }

        private fun registerTreeResinAndRubber(id: Int) {
            var subId: Int
            var completId: Int
            var name: String
            run {
                val descriptor: TreeResin
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Tree Resin")
                descriptor = TreeResin(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Eln.treeResin = descriptor
                CraftingRegistry.addToOre("materialResin", descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Rubber")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                CraftingRegistry.addToOre("itemRubber", descriptor.newItemStack())
            }
        }

        private fun registerRawCable(id: Int) {
            var subId: Int
            var completId: Int
            var name: String
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Cable")
                Eln.copperCableDescriptor = CopperCableDescriptor(name)
                Eln.sharedItem.addElement(completId, Eln.copperCableDescriptor)
                Data.addResource(Eln.copperCableDescriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Cable")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 2
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Tungsten Cable")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
        }

        private fun registerArc(id: Int) {
            var subId: Int
            var completId: Int
            var name: String
            run {
                subId = 0
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Graphite Rod")
                Eln.GraphiteDescriptor = GraphiteDescriptor(name)
                Eln.sharedItem.addElement(completId, Eln.GraphiteDescriptor)
                Data.addResource(Eln.GraphiteDescriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 1
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "2x Graphite Rods")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 2
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "3x Graphite Rods")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 3
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "4x Graphite Rods")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 4
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Synthetic Diamond")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 5
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "unreleasedium")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 6
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Arc Clay Ingot")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
                OreDictionary.registerOre("ingotAluminum", descriptor.newItemStack())
                OreDictionary.registerOre("ingotAluminium", descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 7
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Arc Metal Ingot")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
                OreDictionary.registerOre("ingotSteel", descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 8
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Inert Canister")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 11
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Canister of Water")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
            run {
                val descriptor: GenericItemUsingDamageDescriptor
                subId = 12
                completId = subId + (id shl 6)
                name = I18N.TR_NAME(I18N.Type.NONE, "Canister of Arc Water")
                descriptor = GenericItemUsingDamageDescriptor(name)
                Eln.sharedItem.addElement(completId, descriptor)
                Data.addResource(descriptor.newItemStack())
            }
        }

        private fun registerBrush(id: Int) {
            var subId: Int
            var whiteDesc: BrushDescriptor? = null
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
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                whiteDesc = desc
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
            var subId: Int
            var name: String
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Cheap Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                OreDictionary.registerOre(Eln.dictCheapChip, desc.newItemStack())
            }
            run {
                subId = 1
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                OreDictionary.registerOre(Eln.dictAdvancedChip, desc.newItemStack())
            }
            run {
                subId = 2
                name = I18N.TR_NAME(I18N.Type.NONE, "Machine Block")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("casingMachine", desc.newItemStack())
            }
            run {
                subId = 3
                name = I18N.TR_NAME(I18N.Type.NONE, "Electrical Probe Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                subId = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Thermal Probe Chip")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                subId = 6
                name = I18N.TR_NAME(I18N.Type.NONE, "Copper Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateCopper", desc.newItemStack())
            }
            run {
                subId = 7
                name = I18N.TR_NAME(I18N.Type.NONE, "Iron Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateIron", desc.newItemStack())
            }
            run {
                subId = 8
                name = I18N.TR_NAME(I18N.Type.NONE, "Gold Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateGold", desc.newItemStack())
            }
            run {
                subId = 9
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateLead", desc.newItemStack())
            }
            run {
                subId = 10
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateSilicon", desc.newItemStack())
            }
            run {
                subId = 11
                name = I18N.TR_NAME(I18N.Type.NONE, "Alloy Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateAlloy", desc.newItemStack())
            }
            run {
                subId = 12
                name = I18N.TR_NAME(I18N.Type.NONE, "Coal Plate")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("plateCoal", desc.newItemStack())
            }
            run {
                subId = 16
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Dust")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("dustSilicon", desc.newItemStack())
            }
            run {
                subId = 17
                name = I18N.TR_NAME(I18N.Type.NONE, "Silicon Ingot")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("ingotSilicon", desc.newItemStack())
            }
            run {
                subId = 22
                name = I18N.TR_NAME(I18N.Type.NONE, "Machine Booster")
                val desc = MachineBoosterDescriptor(name)
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 23
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    I18N.TR_NAME(I18N.Type.NONE, "Advanced Machine Block"), arrayOf()) // TODO: Description.
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
                CraftingRegistry.addToOre("casingMachineAdvanced", desc.newItemStack())
            }
            run {
                subId = 28
                name = I18N.TR_NAME(I18N.Type.NONE, "Basic Magnet")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                subId = 29
                name = I18N.TR_NAME(I18N.Type.NONE, "Advanced Magnet")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                subId = 32
                name = I18N.TR_NAME(I18N.Type.NONE, "Data Logger Print")
                val desc = DataLogsPrintDescriptor(name)
                Eln.dataLogsPrintDescriptor = desc
                desc.setDefaultIcon("empty-texture")
                Eln.sharedItem.addWithoutRegistry(subId + (id shl 6), desc)
            }
            run {
                subId = 33
                name = I18N.TR_NAME(I18N.Type.NONE, "Signal Antenna")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, arrayOf())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Data.addResource(desc.newItemStack())
            }
            run {
                subId = 40
                name = I18N.TR_NAME(I18N.Type.NONE, "Player Filter")
                val desc = EntitySensorFilterDescriptor(name, EntityPlayer::class.java, 0f, 1f, 0f)
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 41
                name = I18N.TR_NAME(I18N.Type.NONE, "Monster Filter")
                val desc = EntitySensorFilterDescriptor(name, IMob::class.java, 1f, 0f, 0f)
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 42
                name = I18N.TR_NAME(I18N.Type.NONE, "Animal Filter")
                val desc = EntitySensorFilterDescriptor(name, EntityAnimal::class.java, .3f, .3f, 1f)
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            run {
                subId = 48
                name = I18N.TR_NAME(I18N.Type.NONE, "Wrench")
                val desc = GenericItemUsingDamageDescriptorWithComment(
                    name, I18N.TR("Electrical age wrench,\nCan be used to turn\nsmall wall blocks").split("\n".toRegex()).toTypedArray())
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
                Eln.wrenchItemStack = desc.newItemStack()
            }
            run {
                subId = 52
                name = I18N.TR_NAME(I18N.Type.NONE, "Dielectric")
                val desc = DielectricItem(name, Eln.LVU)
                Eln.sharedItem.addElement(subId + (id shl 6), desc)
            }
            Eln.sharedItem.addElement(53 + (id shl 6), CaseItemDescriptor(I18N.TR_NAME(I18N.Type.NONE, "Casing")))
            Eln.sharedItem.addElement(54 + (id shl 6), ClutchPlateItem("Iron Clutch Plate", 5120f, 640f, 640f, 160f, 0.0001f, false))
            Eln.sharedItem.addElement(55 + (id shl 6), ClutchPinItem("Clutch Pin"))
            Eln.sharedItem.addElement(56 + (id shl 6), ClutchPlateItem("Gold Clutch Plate", 10240f, 2048f, 1024f, 512f, 0.001f, false))
            Eln.sharedItem.addElement(57 + (id shl 6), ClutchPlateItem("Copper Clutch Plate", 8192f, 4096f, 1024f, 512f, 0.0003f, false))
            Eln.sharedItem.addElement(58 + (id shl 6), ClutchPlateItem("Lead Clutch Plate", 15360f, 1024f, 1536f, 768f, 0.0015f, false))
            Eln.sharedItem.addElement(59 + (id shl 6), ClutchPlateItem("Coal Clutch Plate", 1024f, 128f, 128f, 32f, 0.1f, true))
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

        private fun registerPortableNaN() {
            val id: Int
            var subId: Int
            var name: String
            id = 125
            run {
                subId = 0
                name = I18N.TR_NAME(I18N.Type.NONE, "Portable NaN")
                Eln.stdPortableNaN = CableRenderDescriptor("eln", "sprites/nan.png", 3.95f, 0.95f)
                Eln.portableNaNDescriptor = PortableNaNDescriptor(name, Eln.stdPortableNaN)
                Eln.sixNodeItem.addDescriptor(subId + (id shl 6), Eln.portableNaNDescriptor)
            }
        }
    }
}
