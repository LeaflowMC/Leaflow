package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.common.serializer.UUIDAsLongs
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import kotlinx.serialization.Serializable

@Serializable
class ServerboundLoginStartPacket(
    val name: String,
    val uuid: UUIDAsLongs
) : ServerPacket<ServerLoginPacketListener> {
    override fun handle(listener: ServerLoginPacketListener) {
        listener.loginStart(this)
    }

}