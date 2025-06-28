package io.github.leaflowmc.leaflow.server.packets

import io.github.leaflowmc.leaflow.common.GameProfile
import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.listener.server.ServerLoginPacketListener
import io.github.leaflowmc.leaflow.protocol.packets.login.*
import io.github.leaflowmc.leaflow.server.constants.EncryptionConstants.ENCRYPTION_ALGORITHM
import io.github.leaflowmc.leaflow.server.constants.EncryptionConstants.SERVER_KEY_PAIR_ALGORITHM
import io.github.leaflowmc.leaflow.server.player.PlayerConnection
import io.github.leaflowmc.leaflow.server.utils.AuthUtils
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

class ServerLoginPacketListenerImpl(
    val playerConnection: PlayerConnection
) : ServerLoginPacketListener {
    private var requestedName: String? = null
    private val verifyToken = Random.nextBytes(4)

    override fun loginStart(packet: ServerboundLoginStartPacket) {
        if (!playerConnection.server.authEnabled) {
            finishLogin(GameProfile.forOfflinePlayer(packet.name))
            return
        }

        requestedName = packet.name

        playerConnection.sendPacket(
            ClientboundEncryptionRequestPacket(
                "",
                playerConnection.server.keyPair.public.encoded,
                verifyToken,
                true
            )
        )
    }

    override fun encryptionResponse(packet: ServerboundEncryptionResponsePacket) {
        val cipher = Cipher.getInstance(SERVER_KEY_PAIR_ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, playerConnection.server.keyPair.private)

        val verifyToken = cipher.doFinal(packet.verifyToken)
        check(this.verifyToken.contentEquals(verifyToken)) { "Couldn't verify the verification token" }

        val secret = SecretKeySpec(cipher.doFinal(packet.sharedSecret), ENCRYPTION_ALGORITHM)
        playerConnection.setEncryptionKey(secret)

        val name = checkNotNull(requestedName) {
            "username not initialized. got 'encryption response' before 'login hello'?"
        }
        val serverId = AuthUtils.serverId("", secret, playerConnection.server.keyPair.public)

        val profile = AuthUtils.playerHasJoined(name, serverId)

        finishLogin(profile)
    }

    override fun loginAcknowledged(packet: ServerboundLoginAcknowledgedPacket) {
        playerConnection.setProtocol(ProtocolStage.CONFIGURATION)
    }

    private fun finishLogin(profile: GameProfile) {
        playerConnection.sendPacket(
            ClientboundLoginSuccessPacket(profile)
        )
    }

    override fun dispose() {
    }
}
