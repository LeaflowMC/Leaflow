package io.github.leaflowmc.leaflow.protocol.packets.login

import io.github.leaflowmc.leaflow.common.types.GameProfile
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import kotlinx.serialization.Serializable

@Serializable
class ClientboundLoginSuccessPacket(
    val gameProfile: GameProfile
) : ClientPacket<ClientLoginPacketListener> {
    override fun handle(listener: ClientLoginPacketListener) {
        listener.loginSuccess(this)
    }

    override val terminal: Boolean
        get() = true
}