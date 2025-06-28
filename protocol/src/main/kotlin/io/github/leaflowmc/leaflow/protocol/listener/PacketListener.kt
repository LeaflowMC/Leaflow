package io.github.leaflowmc.leaflow.protocol.listener

import io.github.leaflowmc.leaflow.common.Disposable

interface PacketListener : Disposable {
    override fun dispose()
}