package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerLoginPackets
import kotlinx.serialization.Serializable

@Serializable
class ServerboundLoginAcknowledgedPacket : ServerPacket<ServerLoginPacketListener, ServerboundLoginAcknowledgedPacket> {
    override val type: PacketType<ServerboundLoginAcknowledgedPacket>
        get() = ServerLoginPackets.LOGIN_ACKNOWLEDGED

    override fun handle(listener: ServerLoginPacketListener) {
        listener.loginAcknowledged(this)
    }

    override val terminal: Boolean
        get() = true
}