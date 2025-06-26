package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.common.serializer.AnyToNbtSerializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator

@Serializable
data class Person(val name: String, val age: Int)

@Serializable
data class Car(val color: Int, val passengers: Collection<Person>)

object CarAsNbtSerializer : AnyToNbtSerializer<Car>(Car.serializer())
typealias CarAsNbt = @Serializable(CarAsNbtSerializer::class) Car

@Serializable
data class Something(val car: CarAsNbt, val person: Person)

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