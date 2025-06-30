package io.github.leaflowmc.leaflow.server.packets.plugin_message

import io.github.leaflowmc.leaflow.server.player.PlayerConnection

interface PluginMessage {
    fun handle(connection: PlayerConnection)
}