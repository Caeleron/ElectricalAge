package mods.eln.item.electricalitem

import mods.eln.Eln
import mods.eln.item.electricalitem.PortableOreScannerItem.RenderStorage.OreScannerConfigElement
import mods.eln.misc.Utils
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraftforge.oredict.OreDictionary

class OreScannerTasks {
    companion object {
        fun regenOreScannerFactors() {
            PortableOreScannerItem.RenderStorage.blockKeyFactor = null
            Eln.oreScannerConfig.clear()
            if (Eln.addOtherModOreToXRay) {
                for (name in OreDictionary.getOreNames()) {
                    if (name == null) continue
                    // Utils.println(name + " " +
                    // OreDictionary.getOreID(name));
                    if (name.startsWith("ore")) {
                        for (stack in OreDictionary.getOres(name)) {
                            val id = Utils.getItemId(stack) + 4096 * stack.item.getMetadata(stack.itemDamage)
                            // Utils.println(OreDictionary.getOreID(name));
                            var find = false
                            for (c in Eln.oreScannerConfig) {
                                if (c.blockKey == id) {
                                    find = true
                                    break
                                }
                            }
                            if (!find) {
                                Utils.println("$id added to xRay (other mod)")
                                Eln.oreScannerConfig.add(OreScannerConfigElement(id, 0.15f))
                            }
                        }
                    }
                }
            }
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.coal_ore), 5 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.iron_ore), 15 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.gold_ore), 40 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.lapis_ore), 40 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.redstone_ore), 40 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.diamond_ore), 100 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Blocks.emerald_ore), 40 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Eln.oreBlock) + (1 shl 12), 10 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Eln.oreBlock) + (4 shl 12), 20 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Eln.oreBlock) + (5 shl 12), 20 / 100f))
            Eln.oreScannerConfig.add(OreScannerConfigElement(Block.getIdFromBlock(Eln.oreBlock) + (6 shl 12), 20 / 100f))
        }
    }
}
