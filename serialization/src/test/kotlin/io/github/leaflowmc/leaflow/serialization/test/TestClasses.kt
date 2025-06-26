package io.github.leaflowmc.leaflow.serialization.test

import io.github.leaflowmc.leaflow.serialization.nbt.AnyToNbtSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val age: Int)

@Serializable
data class Car(val color: Int, val passengers: Collection<Person>)

object CarAsNbtSerializer : AnyToNbtSerializer<Car>(Car.serializer())
typealias CarAsNbt = @Serializable(CarAsNbtSerializer::class) Car

@Serializable
data class Something(val car: CarAsNbt, val person: Person)
