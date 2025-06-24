package io.github.leaflowmc.leaflow.serialization.test

import kotlinx.serialization.Serializable

@Serializable
data class Person(val name: String, val age: Int)

@Serializable
data class Car(val color: Int, val passengers: Collection<Person>)