package io.github.leaflowmc.leaflow.serialization.annotations

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialInfo
import kotlinx.serialization.descriptors.SerialDescriptor

@OptIn(ExperimentalSerializationApi::class)
@SerialInfo
@Target(AnnotationTarget.PROPERTY)
annotation class ProtocolEnumKind(val kind: Kind) {
    companion object {
        val DEFAULT = Kind.VAR_INT
    }

    enum class Kind {
        UNSIGNED_BYTE,
        VAR_INT;
    }
}

fun SerialDescriptor.protocolEnumKind(): ProtocolEnumKind.Kind {
    annotations.forEach {
        if (it is ProtocolEnumKind) {
            return it.kind
        }
    }

    return ProtocolEnumKind.DEFAULT
}
