package com.mindovercnc.linuxcnc.screen

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import okio.Path
import okio.Path.Companion.toPath

object PathSerializer : KSerializer<Path> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Path", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Path {
        return decoder.decodeString().toPath()
    }

    override fun serialize(encoder: Encoder, value: Path) {
        encoder.encodeString(value.toString())
    }
}
