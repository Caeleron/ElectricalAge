package mods.eln.misc

open class DescriptorBase(descriptorKey: String) {

    @JvmField
    public var descriptorKey = descriptorKey

    init {
        DescriptorManager.put(descriptorKey, this)
    }
}
