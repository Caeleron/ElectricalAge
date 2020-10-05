package mods.eln.misc

import java.util.*

object DescriptorManager {
    val map = HashMap<String, DescriptorBase>()
    fun put(key: String, value: DescriptorBase) {
        map[key] = value
    }

    @JvmStatic
    operator fun get(key: String): DescriptorBase? {
        return map[key]
    }
}
