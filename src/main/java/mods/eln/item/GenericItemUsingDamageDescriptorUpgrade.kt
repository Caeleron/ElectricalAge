package mods.eln.item

import mods.eln.generic.GenericItemUsingDamageDescriptor
import net.minecraft.item.Item

open class GenericItemUsingDamageDescriptorUpgrade : GenericItemUsingDamageDescriptor {
    constructor(name: String?) : super(name!!) {}
    constructor(name: String?, iconName: String?) : super(name!!, iconName!!) {}
}
