package mods.eln.registry

import cpw.mods.fml.common.registry.GameRegistry
import mods.eln.Eln
import mods.eln.Other
import mods.eln.crafting.CraftingRegistry
import mods.eln.i18n.I18N
import mods.eln.misc.VoltageTier
import mods.eln.node.NodeManager
import mods.eln.node.simple.SimpleNodeItem
import mods.eln.ore.DictTypes
import mods.eln.ore.MetalsData
import mods.eln.ore.OreDescriptor
import mods.eln.ore.OreInfo
import mods.eln.simplenode.computerprobe.ComputerProbeBlock
import mods.eln.simplenode.computerprobe.ComputerProbeEntity
import mods.eln.simplenode.computerprobe.ComputerProbeNode
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherBlock
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherDescriptor
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherDescriptor.ElnDescriptor
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherDescriptor.Ic2Descriptor
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherDescriptor.OcDescriptor
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherEntity
import mods.eln.simplenode.energyconverter.EnergyConverterElnToOtherNode
import net.minecraft.tileentity.TileEntity

class BlockRegistry {
    companion object {
        fun registerBlocks() {
            registerEnergyConverter()
            registerComputer()
            registerOre()
        }

        private fun registerEnergyConverter() {
            if (Eln.ElnToOtherEnergyConverterEnable) {
                val entityName = "eln.EnergyConverterElnToOtherEntity"
                TileEntity.addMapping(EnergyConverterElnToOtherEntity::class.java, entityName)
                NodeManager.registerUuid(EnergyConverterElnToOtherNode.getNodeUuidStatic(), EnergyConverterElnToOtherNode::class.java)
                run {
                    val blockName = I18N.TR_NAME(I18N.Type.TILE, "eln.EnergyConverterElnToOtherLVUBlock")
                    val elnDesc = ElnDescriptor(VoltageTier.LOW.voltage, 2_000.0)
                    val ic2Desc = Ic2Descriptor(32.0, 1)
                    val ocDesc = OcDescriptor(ic2Desc.outMax * Other.elnToOcConversionRatio / Other.elnToIc2ConversionRatio)
                    val desc = EnergyConverterElnToOtherDescriptor("EnergyConverterElnToOtherLVU", elnDesc, ic2Desc, ocDesc)
                    Eln.elnToOtherBlockLvu = EnergyConverterElnToOtherBlock(desc)
                    Eln.elnToOtherBlockLvu.setCreativeTab(Eln.creativeTab).setBlockName(blockName)
                    GameRegistry.registerBlock(Eln.elnToOtherBlockLvu, SimpleNodeItem::class.java, blockName)
                }
                run {
                    val blockName = I18N.TR_NAME(I18N.Type.TILE, "eln.EnergyConverterElnToOtherMVUBlock")
                    val elnDesc = ElnDescriptor(VoltageTier.HIGH_HOUSEHOLD.voltage, 5_000.0)
                    val ic2Desc = Ic2Descriptor(128.0, 2)
                    val ocDesc = OcDescriptor(ic2Desc.outMax * Other.elnToOcConversionRatio / Other.elnToIc2ConversionRatio)
                    val desc = EnergyConverterElnToOtherDescriptor("EnergyConverterElnToOtherMVU", elnDesc, ic2Desc, ocDesc)
                    Eln.elnToOtherBlockMvu = EnergyConverterElnToOtherBlock(desc)
                    Eln.elnToOtherBlockMvu.setCreativeTab(Eln.creativeTab).setBlockName(blockName)
                    GameRegistry.registerBlock(Eln.elnToOtherBlockMvu, SimpleNodeItem::class.java, blockName)
                }
                run {
                    val blockName = I18N.TR_NAME(I18N.Type.TILE, "eln.EnergyConverterElnToOtherHVUBlock")
                    val elnDesc = ElnDescriptor(VoltageTier.INDUSTRIAL.voltage, 15_000.0)
                    val ic2Desc = Ic2Descriptor(512.0, 3)
                    val ocDesc = OcDescriptor(ic2Desc.outMax * Other.elnToOcConversionRatio / Other.elnToIc2ConversionRatio)
                    val desc = EnergyConverterElnToOtherDescriptor("EnergyConverterElnToOtherHVU", elnDesc, ic2Desc, ocDesc)
                    Eln.elnToOtherBlockHvu = EnergyConverterElnToOtherBlock(desc)
                    Eln.elnToOtherBlockHvu.setCreativeTab(Eln.creativeTab).setBlockName(blockName)
                    GameRegistry.registerBlock(Eln.elnToOtherBlockHvu, SimpleNodeItem::class.java, blockName)
                }
            }
        }

        private fun registerComputer() {
            if (Eln.ComputerProbeEnable) {
                val entityName = I18N.TR_NAME(I18N.Type.TILE, "eln.ElnProbe")
                TileEntity.addMapping(ComputerProbeEntity::class.java, entityName)
                NodeManager.registerUuid(ComputerProbeNode.getNodeUuidStatic(), ComputerProbeNode::class.java)
                Eln.computerProbeBlock = ComputerProbeBlock()
                Eln.computerProbeBlock.setCreativeTab(Eln.creativeTab).setBlockName(entityName)
                GameRegistry.registerBlock(Eln.computerProbeBlock, SimpleNodeItem::class.java, entityName)
            }
        }

        private fun registerOre() {
            var i = 1
            for (ore in OreInfo.values()) {
                val cleanName = ore.name.replace("Native ","").replace(" ", "")
                val desc = OreDescriptor(ore.name, i, 30, 6, 10,0,80)
                System.out.println("Adding (${i}) ore$cleanName to registry")
                Eln.oreItem.addDescriptor(i, desc)
                CraftingRegistry.addToOre("${DictTypes.ORE}$cleanName", desc.newItemStack())
                i += 1
            }

            /*run {
                id = 4
                name = I18N.TR_NAME(I18N.Type.NONE, "Lead Ore")
                val desc = OreDescriptor(name, id,
                    8 * if (Eln.genLead) 1 else 0, 3, 9, 0, 24
                )
                Eln.oreItem.addDescriptor(id, desc)
                CraftingRegistry.addToOre("oreLead", desc.newItemStack())
            }*/
        }
    }
}
