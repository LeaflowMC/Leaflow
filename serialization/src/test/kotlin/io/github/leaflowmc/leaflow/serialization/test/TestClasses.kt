package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.serialization.annotations.AsNbt
import io.github.leaflowmc.leaflow.serialization.annotations.NotLengthPrefixed
import io.github.leaflowmc.leaflow.serialization.annotations.ProtocolEnumKind
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class Person(val name: String, val age: Int)

@Serializable
data class Car(val color: Int, val passengers: Collection<Person>)

@Serializable
data class Something(
    @AsNbt
    val car: Car,
    val person: Person
)

@OptIn(ExperimentalSerializationApi::class)
@Serializable
@JsonClassDiscriminator("glasses_for")
sealed interface Glasses

@Serializable
@SerialName("sun")
data class SunGlasses(
    @SerialName("tint_level")
    val tintLevel: Float
) : Glasses

@Serializable
@SerialName("vision")
data class VisionGlasses(
    @SerialName("vision_left")
    val visionLeft: Int,
    @SerialName("vision_right")
    val visionRight: Int
) : Glasses

enum class TestEnum {
    ZERO,
    ONE,
    TWO;
}

@Serializable
data class ClassWithEnum(
    val number: Int,
    @ProtocolEnumKind(ProtocolEnumKind.Kind.UNSIGNED_BYTE)
    val byteEnum: TestEnum,
    @ProtocolEnumKind(ProtocolEnumKind.Kind.VAR_INT)
    val varIntEnum: TestEnum
)

@Suppress("EqualsOrHashCode")
@Serializable
class ClassWithFixedLength(
    @NotLengthPrefixed(69)
    val list: IntArray,
    @NotLengthPrefixed(5)
    val str: String,
    @NotLengthPrefixed
    val data: ByteArray
) {
    init {
        require(list.size == 69) { "list size ${list.size} != 69" }
        require(str.length == 5) { "string size ${str.length} != 5" }
    }

    override fun equals(other: Any?): Boolean {
        if (other !is ClassWithFixedLength) return false
        return other.list.contentEquals(this.list) &&
                other.data.contentEquals(this.data) &&
                other.str == this.str
    }
}
