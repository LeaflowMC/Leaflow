package io.github.leaflowmc.leaflow.server

import io.github.leaflowmc.leaflow.protocol.ProtocolStage
import io.github.leaflowmc.leaflow.protocol.packets.type.ServerHandshakePackets
import io.github.leaflowmc.leaflow.server.netty.PacketDecoder
import io.github.leaflowmc.leaflow.server.netty.PacketEncoder
import io.github.leaflowmc.leaflow.server.netty.PacketSizeDecoder
import io.github.leaflowmc.leaflow.server.netty.PacketSizeEncoder
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.MultiThreadIoEventLoopGroup
import io.netty.channel.nio.NioIoHandler
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.net.InetSocketAddress

class LeaflowServerImpl(
    override val address: String,
    override val port: Int,
    override val factory: LeaflowFactory
) : LeaflowServer {
    companion object {
        val LOGGER: Logger = LogManager.getLogger()
    }

    private val bossGroup: MultiThreadIoEventLoopGroup
    private val workerGroup: MultiThreadIoEventLoopGroup

    init {
        val nioFactory = NioIoHandler.newFactory()
        bossGroup = MultiThreadIoEventLoopGroup(nioFactory)
        workerGroup = MultiThreadIoEventLoopGroup(nioFactory)
    }

    val scope = CoroutineScope(Dispatchers.Default)

    override fun start() {
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .childHandler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        val playerConnection = factory.createPlayerConnection(this@LeaflowServerImpl,
                            ProtocolStage.HANDSHAKE)

                        ch.pipeline()
                            .addLast("length_encoder", PacketSizeEncoder())
                            .addLast("length_decoder", PacketSizeDecoder())

                            .addLast("encoder",
                                PacketEncoder())
                            .addLast("decoder",
                                PacketDecoder(ProtocolStage.HANDSHAKE))
                            .addLast("connection", playerConnection)
                    }
                })

            val future = b.bind(address, port).sync()

            val socket = future.channel().localAddress() as InetSocketAddress
            LOGGER.info("Server is listening on ${socket.address}:${socket.port}")

            future.channel().closeFuture().sync() // wait until eternity
        } finally {
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

    class Builder {
        var address = "0.0.0.0"
        var port = 25565
        var factory: LeaflowFactory = LeaflowFactoryImpl

        fun build(): LeaflowServerImpl {
            return LeaflowServerImpl(address, port, factory)
        }
    }
}

fun createLeaflowServer(block: LeaflowServerImpl.Builder.() -> Unit = {}): LeaflowServer {
    return LeaflowServerImpl.Builder().apply(block).build()
}
