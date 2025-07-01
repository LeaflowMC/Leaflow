package io.github.leaflowmc.leaflow.server.constants

object NettyHandlerConstants {
    const val CIPHER_ENCODER = "encrypt"
    const val CIPHER_DECODER = "decrypt"

    const val LENGTH_ENCODER = "length_encoder"
    const val LENGTH_DECODER = "length_decoder"

    const val COMPRESSION_ENCODER = "zlib_encoder"
    const val COMPRESSION_DECODER = "zlib_decoder"

    const val PACKET_ENCODER = "encoder"
    const val PACKET_DECODER = "decoder"

    const val PACKET_ENCODER_SWAPPER = "encoder_config"
    const val PACKET_DECODER_SWAPPER = "decoder_config"

    const val PLAYER_CONNECTION = "connection"
}