@file:Suppress("unused")

package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundCustomReportDetailsPacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundDisconnectPacket
import io.github.leaflowmc.leaflow.protocol.packets.common.ClientboundKeepAlivePacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundFinishConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundKnownPacksPacket
import io.github.leaflowmc.leaflow.protocol.packets.configuration.ClientboundResetChatPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundEncryptionRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ClientboundLoginSuccessPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPingPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ClientboundPongPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundAbilitiesPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundChangeDifficultyPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundGameEventPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundLoginPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundServerDataPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetDefaultSpawnPositionPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetHeldItemPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetTickingStatePacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundSetTitleTextPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundStartConfigurationPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundStepTickPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundTeleportPacket
import io.github.leaflowmc.leaflow.protocol.packets.play.clientbound.ClientboundUpdateTimePacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ClientboundStatusResponsePacket

val ClientHandshakePackets = createProtocolInfo(ProtocolStage.HANDSHAKE) {
}

val ClientStatusPackets = createProtocolInfo(ProtocolStage.STATUS) {
    addPacket<ClientboundStatusResponsePacket>()
    addPacket<ClientboundPongPacket>()
}

val ClientLoginPackets = createProtocolInfo(ProtocolStage.LOGIN) {
    skipPacket("DISCONNECT")
    addPacket<ClientboundEncryptionRequestPacket>()
    addPacket<ClientboundLoginSuccessPacket>()
    skipPacket("SET_COMPRESSION")
    skipPacket("LOGIN_PLUGIN_REQUEST")
    skipPacket("COOKIE_REQUEST")
}

val ClientConfigurationPackets = createProtocolInfo(ProtocolStage.CONFIGURATION) {
    skipPacket("COOKIE_REQUEST")
    skipPacket("PLUGIN_MESSAGE")
    addPacket<ClientboundDisconnectPacket>()
    addPacket<ClientboundFinishConfigurationPacket>()
    addPacket<ClientboundKeepAlivePacket>()
    addPacket<ClientboundPingPacket>()
    addPacket<ClientboundResetChatPacket>()
    skipPacket("REGISTRY_DATA")
    skipPacket("REMOVE_RESOURCE_PACK")
    skipPacket("ADD_RESOURCE_PACK")
    skipPacket("STORE_COOKIE")
    skipPacket("TRANSFER")
    skipPacket("FEATURE_FLAGS")
    skipPacket("UPDATE_TAGS")
    addPacket<ClientboundKnownPacksPacket>()
    addPacket<ClientboundCustomReportDetailsPacket>()
    skipPacket("SERVER_LINKS")
    skipPacket("CLEAR_DIALOG")
    skipPacket("SHOW_DIALOG")
}

val ClientPlayPackets = createProtocolInfo(ProtocolStage.PLAY) {
    skipPacket("BUNDLE_DELIMITER")
    skipPacket("SPAWN_ENTITY")
    skipPacket("Entity_Animation")
    skipPacket("AWARD_STATISTICS")
    skipPacket("ACKNOWLEDGE_BLOCK_CHANGE")
    skipPacket("SET_BLOCK_DESTROY_STAGE")
    skipPacket("BLOCK_ENTITY_DATA")
    skipPacket("BLOCK_ACTION")
    skipPacket("BLOCK_UPDATE")
    skipPacket("BOSS_BAR")
    addPacket<ClientboundChangeDifficultyPacket>()
    skipPacket("CHUNK_BATCH_FINISHED")
    skipPacket("CHUNK_BATCH_START")
    skipPacket("CHUNK_BIOMES")
    skipPacket("CLEAR_TITLES")
    skipPacket("COMMAND_SUGGESTIONS_RESPONSE")
    skipPacket("COMMANDS")
    skipPacket("CLOSE_CONTAINER")
    skipPacket("SET_CONTAINER_CONTENT")
    skipPacket("SET_CONTAINER_PROPERTY")
    skipPacket("SET_CONTAINER_SLOT")
    skipPacket("COOKIE_REQUEST")
    skipPacket("SET_COOLDOWN")
    skipPacket("CHAT_SUGGESTIONS")
    skipPacket("PLUGIN_MESSAGE")
    skipPacket("DAMAGE_EVENT")
    skipPacket("DEBUG_SAMPLE")
    skipPacket("DELETE_MESSAGE")
    addPacket<ClientboundDisconnectPacket>()
    skipPacket("DISGUISED_CHAT_MESSAGE")
    skipPacket("ENTITY_EVENT")
    skipPacket("TELEPORT_ENTITY")
    skipPacket("EXPLOSION")
    skipPacket("UNLOAD_CHUNK")
    addPacket<ClientboundGameEventPacket>()
    skipPacket("OPEN_HORSE_SCREEN")
    skipPacket("HURT_ANIMATION")
    skipPacket("INITIALIZE_WORLD_BORDER")
    addPacket<ClientboundKeepAlivePacket>()
    skipPacket("CHUNK_DATA_AND_UPDATE_LIGHT")
    skipPacket("WORLD_EVENT")
    skipPacket("PARTICLE")
    skipPacket("UPDATE_LIGHT")
    addPacket<ClientboundLoginPacket>()
    skipPacket("MAP_DATA")
    skipPacket("MERCHANT_OFFERS")
    skipPacket("UPDATE_ENTITY_POSITION")
    skipPacket("UPDATE_ENTITY_POSITION_AND_ROTATION")
    skipPacket("MOVE_MINECART_ALONG_TRACK")
    skipPacket("UPDATE_ENTITY_ROTATION")
    skipPacket("MOVE_VEHICLE")
    skipPacket("OPEN_BOOK")
    skipPacket("OPEN_SCREEN")
    skipPacket("OPEN_SIGN_EDITOR")
    addPacket<ClientboundPingPacket>()
    addPacket<ClientboundPongPacket>()
    skipPacket("PLACE_GHOST_RECIPE")
    addPacket<ClientboundAbilitiesPacket>()
    skipPacket("PLAYER_CHAT_MESSAGE")
    skipPacket("END_COMBAT")
    skipPacket("ENTER_COMBAT")
    skipPacket("COMBAT_DEATH")
    skipPacket("PLAYER_INFO_REMOVE")
    skipPacket("PLAYER_INFO_UPDATE")
    skipPacket("LOOK_AT")
    addPacket<ClientboundTeleportPacket>()
    skipPacket("PLAYER_ROTATION")
    skipPacket("RECIPE_BOOK_ADD")
    skipPacket("RECIPE_BOOK_REMOVE")
    skipPacket("RECIPE_BOOK_SETTINGS")
    skipPacket("REMOVE_ENTITIES")
    skipPacket("REMOVE_ENTITY_EFFECT")
    skipPacket("RESET_SCORE")
    skipPacket("REMOVE_RESOURCE_PACK")
    skipPacket("ADD_RESOURCE_PACK")
    skipPacket("RESPAWN")
    skipPacket("SET_HEAD_ROTATION")
    skipPacket("UPDATE_SECTION_BLOCKS")
    skipPacket("SELECT_ADVANCEMENTS_TAB")
    addPacket<ClientboundServerDataPacket>()
    skipPacket("SET_ACTION_BAR_TEXT")
    skipPacket("SET_BORDER_CENTER")
    skipPacket("SET_BORDER_LERP_SIZE")
    skipPacket("SET_BORDER_SIZE")
    skipPacket("SET_BORDER_WARNING_DELAY")
    skipPacket("SET_BORDER_WARNING_DISTANCE")
    skipPacket("SET_CAMERA")
    skipPacket("SET_CENTER_CHUNK")
    skipPacket("SET_RENDER_DISTANCE")
    skipPacket("SET_CURSOR_ITEM")
    addPacket<ClientboundSetDefaultSpawnPositionPacket>()
    skipPacket("DISPLAY_OBJECTIVE")
    skipPacket("SET_ENTITY_METADATA")
    skipPacket("LINK_ENTITIES")
    skipPacket("SET_ENTITY_VELOCITY")
    skipPacket("SET_EQUIPMENT")
    skipPacket("SET_EXPERIENCE")
    skipPacket("SET_HEALTH")
    addPacket<ClientboundSetHeldItemPacket>()
    skipPacket("UPDATE_OBJECTIVES")
    skipPacket("SET_PASSENGERS")
    skipPacket("SET_PLAYER_INVENTORY_SLOT")
    skipPacket("UPDATE_TEAMS")
    skipPacket("UPDATE_SCORE")
    skipPacket("SET_SIMULATION_DISTANCE")
    skipPacket("SET_SUBTITLE_TEXT")
    addPacket<ClientboundUpdateTimePacket>()
    addPacket<ClientboundSetTitleTextPacket>()
    skipPacket("SET_TITLE_ANIMATION_TIMES")
    skipPacket("ENTITY_SOUND_EFFECT")
    skipPacket("SOUND_EFFECT")
    addPacket<ClientboundStartConfigurationPacket>()
    skipPacket("STOP_SOUND")
    skipPacket("STORE_COOKIE")
    skipPacket("SYSTEM_CHAT_MESSAGE")
    skipPacket("SET_TAB_LIST_HEADER_AND_FOOTER")
    skipPacket("TAG_QUERY_RESPONSE")
    skipPacket("PICKUP_ITEM")
    skipPacket("SYNCHRONIZE_VEHICLE_POSITION")
    skipPacket("TEST_INSTANCE_BLOCK_STATUS")
    addPacket<ClientboundSetTickingStatePacket>()
    addPacket<ClientboundStepTickPacket>()
    skipPacket("TRANSFER")
    skipPacket("UPDATE_ADVANCEMENTS")
    skipPacket("UPDATE_ATTRIBUTES")
    skipPacket("ENTITY_EFFECT")
    skipPacket("UPDATE_RECIPES")
    skipPacket("UPDATE_TAGS")
    skipPacket("PROJECTILE_POWER")
    addPacket<ClientboundCustomReportDetailsPacket>()
    skipPacket("SERVER_LINKS")
    skipPacket("WAYPOINT")
    skipPacket("CLEAR_DIALOG")
    skipPacket("SHOW_DIALOG")
}

fun getClientProtocolFor(protocolStage: ProtocolStage): ProtocolInfo {
    return when (protocolStage) {
        ProtocolStage.HANDSHAKE -> ClientHandshakePackets
        ProtocolStage.STATUS -> ClientStatusPackets
        ProtocolStage.LOGIN -> ClientLoginPackets
        ProtocolStage.CONFIGURATION -> ClientConfigurationPackets
        ProtocolStage.PLAY -> ClientPlayPackets
    }
}
