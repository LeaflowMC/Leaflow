package io.github.leaflowmc.leaflow.server.encryption

import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.handler.codec.MessageToMessageDecoder
import javax.crypto.Cipher

class PacketDecryptor(cipher: Cipher) : MessageToMessageDecoder<ByteBuf>() {
    private val cipher = CipherBase(cipher)

    override fun decode(
        ctx: ChannelHandlerContext,
        buffer: ByteBuf,
        out: MutableList<Any>
    ) {
        out.add(cipher.decipher(ctx, buffer))
    }
}