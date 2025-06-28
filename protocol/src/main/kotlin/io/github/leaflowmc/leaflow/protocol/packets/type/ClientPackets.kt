@file:Suppress("unused")

package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.listener.client.ClientLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.client.ClientStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ClientPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundEncryptionRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundLoginSuccessPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundStatusPongResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket

object ClientStatusPackets : ProtocolInfo<ClientStatusPacketListener, ClientPacket<ClientStatusPacketListener, *>>() {
    val STATUS_RESPONSE = addPacket(ClientboundStatusResponsePacket.serializer())
    val PONG_RESPONSE = addPacket(ClientboundStatusPongResponsePacket.serializer())
}

object ClientLoginPackets : ProtocolInfo<ClientLoginPacketListener, ClientPacket<ClientLoginPacketListener, *>>() {
    val DISCONNECT = skipPacket()
    val ENCRYPTION_REQUEST = addPacket(ClientboundEncryptionRequestPacket.serializer())
    val LOGIN_SUCCESS = addPacket(ClientboundLoginSuccessPacket.serializer())
    val SET_COMPRESSION = skipPacket()
    val LOGIN_PLUGIN_REQUEST = skipPacket()
    val COOKIE_REQUEST = skipPacket()
}
