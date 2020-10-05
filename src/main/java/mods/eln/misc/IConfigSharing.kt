package mods.eln.misc

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

interface IConfigSharing {
    fun serializeConfig(stream: DataOutputStream)
    fun deserialize(stream: DataInputStream)
}
