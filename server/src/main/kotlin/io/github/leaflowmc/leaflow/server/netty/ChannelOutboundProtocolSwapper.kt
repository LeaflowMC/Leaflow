package io.github.leaflowmc.leaflow.server.netty

import io.github.leaflowmc.leaflow.protocol.packets.Packet
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelDuplexHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelPromise
import io.netty.handler.codec.DecoderException
import io.netty.util.ReferenceCountUtil

class ChannelOutboundProtocolSwapper : ChannelDuplexHandler() {
    override fun write(ctx: ChannelHandlerContext, msg: Any, promise: ChannelPromise) {
        if (msg is Task) {
            try {
                msg.run(ctx)
            } finally {
                ReferenceCountUtil.release(msg)
            }
            promise.setSuccess()
        } else {
            ctx.write(msg, promise)
        }
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        if (msg is Packet<*>) {
            ReferenceCountUtil.release(msg)

            throw DecoderException("Can't process packet (${msg::class.simpleName}): outbound handler isn't configured")
        }

        ctx.fireChannelRead(msg)
    }

    fun interface Task {
        fun run(ctx: ChannelHandlerContext)
    }
}
