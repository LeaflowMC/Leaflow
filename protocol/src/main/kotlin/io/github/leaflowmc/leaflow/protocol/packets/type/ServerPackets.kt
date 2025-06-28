@file:Suppress("unused")

package io.github.leaflowmc.leaflow.protocol.packets.type

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerHandshakePacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerPlayPacketListener
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerStatusPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.ServerPacket
import io.github.leaflowmc.leaflow.protocol.packets.handshake.ServerboundHandshakePacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundEncryptionResponsePacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginAcknowledgedPacket
import io.github.leaflowmc.leaflow.protocol.packets.login.ServerboundLoginStartPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundPlayPingRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.ping.ServerboundStatusPingRequestPacket
import io.github.leaflowmc.leaflow.protocol.packets.status.ServerboundStatusRequestPacket

object ServerHandshakePackets : ProtocolInfo<ServerHandshakePacketListener, ServerPacket<ServerHandshakePacketListener, *>>() {
    val HANDSHAKE = addPacket(ServerboundHandshakePacket.serializer())
}

object ServerStatusPackets : ProtocolInfo<ServerStatusPacketListener, ServerPacket<ServerStatusPacketListener, *>>() {
    val STATUS_REQUEST = addPacket(ServerboundStatusRequestPacket.serializer())
    val PING_REQUEST = addPacket(ServerboundStatusPingRequestPacket.serializer())
}

object ServerLoginPackets : ProtocolInfo<ServerLoginPacketListener, ServerPacket<ServerLoginPacketListener, *>>() {
    val LOGIN_START = addPacket(ServerboundLoginStartPacket.serializer())
    val ENCRYPTION_RESPONSE = addPacket(ServerboundEncryptionResponsePacket.serializer())
    val LOGIN_PLUGIN_RESPONSE = skipPacket()
    val LOGIN_ACKNOWLEDGED = addPacket(ServerboundLoginAcknowledgedPacket.serializer())
    val COOKIE_RESPONSE = skipPacket()
}

object ServerPlayPackets : ProtocolInfo<ServerPlayPacketListener, ServerPacket<ServerPlayPacketListener, *>>() {
    val CONFIRM_TELEPORTATION = skipPacket()
    val QUERY_BLOCK_ENTITY_TAG = skipPacket()
    val BUNDLE_ITEM_SELECTED = skipPacket()
    val CHANGE_DIFFICULTY = skipPacket()
    val ACKNOWLEDGE_MESSAGE = skipPacket()
    val CHAT_COMMAND = skipPacket()
    val SIGNED_CHAT_COMMAND = skipPacket()
    val CHAT_MESSAGE = skipPacket()
    val PLAYER_SESSION = skipPacket()
    val CHUNK_BATCH_RECEIVED = skipPacket()
    val CLIENT_STATUS = skipPacket()
    val CLIENT_TICK_END = skipPacket()
    val CLIENT_INFORMATION = skipPacket()
    val COMMAND_SUGGESTONS_REQUEST = skipPacket()
    val ACKNOWLEDGE_CONFIGURATION = skipPacket()
    val CLICK_CONTAINER_BUTTON = skipPacket()
    val CLICK_CONTAINER = skipPacket()
    val CLOSE_CONTAINER = skipPacket()
    val CHANGE_CONTAINER_SLOT_STATE = skipPacket()
    val COOKIE_RESPONSE = skipPacket()
    val SERVERBOUND_PLUGIN_MESSAGE = skipPacket()
    val DEBUG_SAMPLE_SUBSCRIPTION = skipPacket()
    val EDIT_BOOK = skipPacket()
    val QUERY_ENTITY_TAG = skipPacket()
    val INTERACT = skipPacket()
    val JIGSAW_GENERATE = skipPacket()
    val SERVERBOUND_KEEP_ALIVE = skipPacket()
    val LOCK_DIFFICULTY = skipPacket()
    val SET_PLAYER_POSITION = skipPacket()
    val SET_PLAYER_POSITION_AND_ROTATION = skipPacket()
    val SET_PLAYER_ROTATION = skipPacket()
    val SET_PLAYER_MOVEMENT_FLAGS = skipPacket()
    val MOVE_VEHICLE = skipPacket()
    val PADDLE_BOAT = skipPacket()
    val PICK_ITEM_FROM_BLOCK = skipPacket()
    val PICK_ITEM_FROM_ENTITY = skipPacket()
    val PING_REQUEST = addPacket(ServerboundPlayPingRequestPacket.serializer())
    val PLACE_RECIPE = skipPacket()
    val PLAYER_ABILITIES = skipPacket()
    val PLAYER_ACTION = skipPacket()
    val PLAYER_COMMAND = skipPacket()
    val PLAYER_INPUT = skipPacket()
    val PLAYER_LOADED = skipPacket()
    val PONG = skipPacket()
    val CHANGE_RECIPE_BOOK_SETTINGS = skipPacket()
    val SET_SEEN_RECIPE = skipPacket()
    val RENAME_ITEM = skipPacket()
    val RESOURCE_PACK_RESPONSE = skipPacket()
    val SEEN_ADVANCEMENTS = skipPacket()
    val SELECT_TRADE = skipPacket()
    val SET_BEACON_EFFECT = skipPacket()
    val SET_HELD_ITEM = skipPacket()
    val PROGRAM_COMMAND_BLOCK = skipPacket()
    val PROGRAM_COMMAND_BLOCK_MINECART = skipPacket()
    val SET_CREATIVE_MODE_SLOT = skipPacket()
    val PROGRAM_JIGSAW_BLOCK = skipPacket()
    val PROGRAM_STRUCTURE_BLOCK = skipPacket()
    val SET_TEST_BLOCK = skipPacket()
    val UPDATE_SIGN = skipPacket()
    val SWING_ARM = skipPacket()
    val TELEPORT_TO_ENTITY = skipPacket()
    val TEST_INSTANCE_BLOCK_ACTION = skipPacket()
    val USE_ITEM_ON = skipPacket()
    val USE_ITEM = skipPacket()
}

fun getServerProtocolFor(stage: ProtocolStage): ProtocolInfo<ServerPacketListener, ServerPacket<ServerPacketListener, *>> {
    @Suppress("UNCHECKED_CAST")
    return when (stage) {
        ProtocolStage.HANDSHAKE -> ServerHandshakePackets
        ProtocolStage.STATUS -> ServerStatusPackets
        ProtocolStage.LOGIN -> ServerLoginPackets
        ProtocolStage.CONFIGURATION -> TODO()
        ProtocolStage.PLAY -> ServerPlayPackets
    } as ProtocolInfo<ServerPacketListener, ServerPacket<ServerPacketListener, *>>
}