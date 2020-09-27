package mods.eln.registry

import cpw.mods.fml.common.registry.EntityRegistry
import mods.eln.Eln
import mods.eln.crafting.CraftingRegistry.Companion.findItemStack
import mods.eln.entity.ReplicatorEntity
import mods.eln.i18n.I18N
import mods.eln.misc.Utils
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

class EntityRegistry {
    companion object {
        fun registerEntities() {
            registerReplicator()
        }

        private fun registerReplicator() {
            val redColor = 255 shl 16
            val orangeColor = (255 shl 16) + (200 shl 8)
            if (Eln.replicatorRegistrationId == -1) Eln.replicatorRegistrationId = EntityRegistry.findGlobalUniqueEntityId()
            Utils.println("Replicator registred at" + Eln.replicatorRegistrationId)
            // Register mob
            EntityRegistry.registerGlobalEntityID(ReplicatorEntity::class.java, I18N.TR_NAME(I18N.Type.ENTITY, "EAReplicator"), Eln.replicatorRegistrationId, redColor, orangeColor)
            ReplicatorEntity.dropList.add(findItemStack("Iron Dust", 1))
            ReplicatorEntity.dropList.add(findItemStack("Copper Dust", 1))
            ReplicatorEntity.dropList.add(findItemStack("Gold Dust", 1))
            ReplicatorEntity.dropList.add(ItemStack(Items.redstone))
            ReplicatorEntity.dropList.add(ItemStack(Items.glowstone_dust))
            // Add mob spawn
            // EntityRegistry.addSpawn(ReplicatorEntity.class, 1, 1, 2, EnumCreatureType.monster, BiomeGenBase.plains);
        }
    }
}
