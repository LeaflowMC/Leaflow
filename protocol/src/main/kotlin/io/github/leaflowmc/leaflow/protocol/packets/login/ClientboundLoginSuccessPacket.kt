package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.common.GameProfile
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.type.ClientLoginPackets
import io.github.leaflowmc.leaflow.protocol.packets.type.PacketType
import kotlinx.serialization.Serializable

@Serializable
class ClientboundLoginSuccessPacket(
    val gameProfile: GameProfile
) : ClientPacket<ClientLoginPacketListener, ClientboundLoginSuccessPacket> {
    override val type: PacketType<ClientboundLoginSuccessPacket>
        get() = ClientLoginPackets.LOGIN_SUCCESS

    override fun handle(listener: ClientLoginPacketListener) {
        listener.loginSuccess(this)
    }

    override val terminal: Boolean
        get() = true
}