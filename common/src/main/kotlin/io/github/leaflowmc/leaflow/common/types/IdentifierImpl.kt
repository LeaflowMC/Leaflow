package io.github.leaflowmc.leaflow.common.types

import kotlinx.serialization.Serializable

@Suppress("SERIALIZER_TYPE_INCOMPATIBLE")
@Serializable(IdentifierSerializer::class)
data class IdentifierImpl(override val namespace: String, override val path: String) : Identifier {
    init {
        require(Identifier.isValidNamespace(namespace)) { "invalid namespace: $namespace" }
        require(Identifier.isValidPath(path)) { "invalid path: $path" }
    }

    override fun toString(): String {
        return asString()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Identifier) return false

        return namespace == other.namespace &&
                path == other.path
    }

    override fun hashCode(): Int {
        var result = namespace.hashCode()
        result = 31 * result + path.hashCode()
        return result
    }
}