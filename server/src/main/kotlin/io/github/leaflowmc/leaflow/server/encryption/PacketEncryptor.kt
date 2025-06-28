package io.github.leaflowmc.leaflow.server.encryption

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToByteEncoder
import javax.crypto.Cipher

class PacketEncryptor(cipher: Cipher) : MessageToByteEncoder<ByteBuf>() {
    private val cipher = CipherBase(cipher)

    override fun encode(
        ctx: ChannelHandlerContext,
        buffer: ByteBuf,
        out: ByteBuf
    ) {
        cipher.encipher(buffer, out)
    }
}