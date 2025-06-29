package io.github.leaflowmc.leaflow.common.utils

import kotlin.time.Duration.Companion.seconds

inline val Int.ticks get() = seconds / 20
inline val Long.ticks get() = seconds / 20
inline val Double.ticks get() = seconds / 20
