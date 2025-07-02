package io.github.leaflowmc.leaflow.common.types

import kotlinx.serialization.Serializable

@Serializable(IdentifierSerializer::class)
interface Identifier {
    val namespace: String
    val path: String

    companion object {
        const val DEFAULT_NAMESPACE = "minecraft"

        fun of(namespace: String, path: String): Identifier = IdentifierImpl(namespace, path)
        fun ofVanilla(path: String): Identifier = IdentifierImpl(DEFAULT_NAMESPACE, path)

        /**
         * Parses the identifier string (like `"minecraft:zombie"`)
         * and returns an [Identifier]
         */
        fun of(id: String): Identifier {
            val index = id.indexOf(':')

            if (index < 0) return ofVanilla(id)

            val path = id.substring(index + 1)
            if (index == 0) return ofVanilla(path)

            val namespace = id.substring(0, index)

            return of(namespace, path)
        }

        fun isValidNamespace(namespace: String): Boolean {
            return namespace.all(::validInNamespace)
        }

        fun isValidPath(path: String): Boolean {
            return path.all(::validInPath)
        }

        fun validInNamespace(character: Char): Boolean {
            return character == '_' ||
                    character == '-' ||
                    character == '.' ||
                    (character >= 'a' && character <= 'z') ||
                    (character >= '0' && character <= '9')
        }

        fun validInPath(character: Char): Boolean {
            return character == '_' ||
                    character == '-' ||
                    character == '.' ||
                    (character >= 'a' && character <= 'z') ||
                    (character >= '0' && character <= '9') ||
                    character == '/'
        }
    }
}

fun Identifier.asString() = "$namespace:$path"
fun Identifier.asMinimalString(): String {
    return if (namespace == Identifier.DEFAULT_NAMESPACE) {
        path
    } else {
        asString()
    }
}

operator fun Identifier.component1() = namespace
operator fun Identifier.component2() = path
