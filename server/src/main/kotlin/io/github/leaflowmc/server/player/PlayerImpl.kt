package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.packets.ProtocolStage
import io.ktor.network.sockets.Socket

open class PlayerImpl(
    val socket: Socket
) : Player {
    override var protocolStage = ProtocolStage.HANDSHAKE
}