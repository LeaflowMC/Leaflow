package io.github.leaflowmc.leaflow.math

import kotlinx.serialization.Serializable

@Serializable
data class Vector3D(
    override val x: Double,
    override val y: Double,
    override val z: Double
) : Vector3<Double>()