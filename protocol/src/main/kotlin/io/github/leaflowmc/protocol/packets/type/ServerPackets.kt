package io.github.leaflowmc.protocol.packets.type

import io.github.leaflowmc.protocol.ProtocolStage
import io.github.leaflowmc.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.protocol.packets.ServerPacket
import io.github.leaflowmc.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.protocol.packets.ping.ServerboundStatusPingRequestPacket
import io.github.leaflowmc.protocol.packets.status.ServerboundStatusRequestPacket

object ServerHandshakePackets : ProtocolInfo<ServerHandshakePacketListener, ServerPacket<ServerHandshakePacketListener, *>>() {
    val HANDSHAKE = addPacket(ServerboundHandshakePacket.serializer())
}

object ServerStatusPackets : ProtocolInfo<ServerStatusPacketListener, ServerPacket<ServerStatusPacketListener, *>>() {
    val STATUS_REQUEST = addPacket(ServerboundStatusRequestPacket.serializer())
    val PING_REQUEST = addPacket(ServerboundStatusPingRequestPacket.serializer())
}

fun getServerProtocolFor(stage: ProtocolStage): ProtocolInfo<ServerPacketListener, ServerPacket<ServerPacketListener, *>> {
    @Suppress("UNCHECKED_CAST")
    return when (stage) {
        ProtocolStage.HANDSHAKE -> ServerHandshakePackets
        ProtocolStage.STATUS -> ServerStatusPackets
        ProtocolStage.LOGIN -> TODO()
        ProtocolStage.CONFIGURATION -> TODO()
        ProtocolStage.PLAY -> TODO()
    } as ProtocolInfo<ServerPacketListener, ServerPacket<ServerPacketListener, *>>
}