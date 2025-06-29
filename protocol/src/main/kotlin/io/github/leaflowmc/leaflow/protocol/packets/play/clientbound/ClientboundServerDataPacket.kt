package io.github.leaflowmc.leaflow.protocol.packets.play.clientbound

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.text.component.TextComponentAsNbt
import kotlinx.serialization.Serializable

@Serializable
class ClientboundServerDataPacket(
    val motd: TextComponentAsNbt,
    val icon: ByteArray?
) : ClientPacket<ClientPlayPacketListener> {
    override fun handle(listener: ClientPlayPacketListener) {
        listener.serverData(this)
    }
}