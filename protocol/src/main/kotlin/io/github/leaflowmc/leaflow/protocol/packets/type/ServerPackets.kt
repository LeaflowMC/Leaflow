@file:Suppress("unused")

package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.common.ServerboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundAcknowledgeFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundClientInfoPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ServerboundKnownPacksPacket
import io.github.leaflowmc.leaflow.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundEncryptionResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginAcknowledgedPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginStartPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPongPacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ServerboundStatusRequestPacket

val ServerHandshakePackets = createProtocolInfo(ProtocolStage.HANDSHAKE) {
    addPacket<ServerboundHandshakePacket>()
}

val ServerStatusPackets = createProtocolInfo(ProtocolStage.STATUS) {
    addPacket<ServerboundStatusRequestPacket>()
    addPacket<ServerboundPingPacket>()
}

val ServerLoginPackets = createProtocolInfo(ProtocolStage.LOGIN) {
    addPacket<ServerboundLoginStartPacket>()
    addPacket<ServerboundEncryptionResponsePacket>()
    skipPacket("LOGIN_PLUGIN_RESPONSE")
    addPacket<ServerboundLoginAcknowledgedPacket>()
    skipPacket("COOKIE_RESPONSE")
}

val ServerConfigurationPackets = createProtocolInfo(ProtocolStage.CONFIGURATION) {
    addPacket<ServerboundClientInfoPacket>()
    skipPacket("COOKIE_RESPONSE")
    skipPacket("PLUGIN_MESSAGE")
    addPacket<ServerboundAcknowledgeFinishConfigurationPacket>()
    addPacket<ServerboundKeepAlivePacket>()
    addPacket<ServerboundPongPacket>()
    skipPacket("RESOURCE_PACK_RESPONSE")
    addPacket<ServerboundKnownPacksPacket>()
    skipPacket("CUSTOM_CLICK_ACTION")
}

val ServerPlayPackets = createProtocolInfo(ProtocolStage.PLAY) {
    skipPacket("CONFIRM_TELEPORTATION")
    skipPacket("QUERY_BLOCK_ENTITY_TAG")
    skipPacket("BUNDLE_ITEM_SELECTED")
    skipPacket("CHANGE_DIFFICULTY")
    skipPacket("ACKNOWLEDGE_MESSAGE")
    skipPacket("CHAT_COMMAND")
    skipPacket("SIGNED_CHAT_COMMAND")
    skipPacket("CHAT_MESSAGE")
    skipPacket("PLAYER_SESSION")
    skipPacket("CHUNK_BATCH_RECEIVED")
    skipPacket("CLIENT_STATUS")
    skipPacket("CLIENT_TICK_END")
    skipPacket("CLIENT_INFORMATION")
    skipPacket("COMMAND_SUGGESTIONS_REQUEST")
    skipPacket("ACKNOWLEDGE_CONFIGURATION")
    skipPacket("CLICK_CONTAINER_BUTTON")
    skipPacket("CLICK_CONTAINER")
    skipPacket("CLOSE_CONTAINER")
    skipPacket("CHANGE_CONTAINER_SLOT_STATE")
    skipPacket("COOKIE_RESPONSE")
    skipPacket("PLUGIN_MESSAGE")
    skipPacket("DEBUG_SAMPLE_SUBSCRIPTION")
    skipPacket("EDIT_BOOK")
    skipPacket("QUERY_ENTITY_TAG")
    skipPacket("INTERACT")
    skipPacket("JIGSAW_GENERATE")
    addPacket<ServerboundKeepAlivePacket>()
    skipPacket("LOCK_DIFFICULTY")
    skipPacket("SET_PLAYER_POSITION")
    skipPacket("SET_PLAYER_POSITION_AND_ROTATION")
    skipPacket("SET_PLAYER_ROTATION")
    skipPacket("SET_PLAYER_MOVEMENT_FLAGS")
    skipPacket("MOVE_VEHICLE")
    skipPacket("PADDLE_BOAT")
    skipPacket("PICK_ITEM_FROM_BLOCK")
    skipPacket("PICK_ITEM_FROM_ENTITY")
    addPacket<ServerboundPingPacket>()
    skipPacket("PLACE_RECIPE")
    skipPacket("PLAYER_ABILITIES")
    skipPacket("PLAYER_ACTION")
    skipPacket("PLAYER_COMMAND")
    skipPacket("PLAYER_INPUT")
    skipPacket("PLAYER_LOADED")
    skipPacket("PONG")
    skipPacket("CHANGE_RECIPE_BOOK_SETTINGS")
    skipPacket("SET_SEEN_RECIPE")
    skipPacket("RENAME_ITEM")
    skipPacket("RESOURCE_PACK_RESPONSE")
    skipPacket("SEEN_ADVANCEMENTS")
    skipPacket("SELECT_TRADE")
    skipPacket("SET_BEACON_EFFECT")
    skipPacket("SET_HELD_ITEM")
    skipPacket("PROGRAM_COMMAND_BLOCK")
    skipPacket("PROGRAM_COMMAND_BLOCK_MINECART")
    skipPacket("SET_CREATIVE_MODE_SLOT")
    skipPacket("PROGRAM_JIGSAW_BLOCK")
    skipPacket("PROGRAM_STRUCTURE_BLOCK")
    skipPacket("SET_TEST_BLOCK")
    skipPacket("UPDATE_SIGN")
    skipPacket("SWING_ARM")
    skipPacket("TELEPORT_TO_ENTITY")
    skipPacket("TEST_INSTANCE_BLOCK_ACTION")
    skipPacket("USE_ITEM_ON")
    skipPacket("USE_ITEM")
    skipPacket("CUSTOM_CLICK_ACTION")
}

fun getServerProtocolFor(stage: ProtocolStage): ProtocolInfo {
    return when (stage) {
        ProtocolStage.HANDSHAKE -> ServerHandshakePackets
        ProtocolStage.STATUS -> ServerStatusPackets
        ProtocolStage.LOGIN -> ServerLoginPackets
        ProtocolStage.CONFIGURATION -> ServerConfigurationPackets
        ProtocolStage.PLAY -> ServerPlayPackets
    }
}
