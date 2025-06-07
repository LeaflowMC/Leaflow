package io.github.leaflowmc.server.player

import io.github.leaflowmc.protocol.packets.ProtocolStage

interface Player {
    var protocolStage: ProtocolStage
}