package eu.smoothcloud.chain.network;

import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.*;
import io.netty5.channel.epoll.Epoll;
import io.netty5.channel.epoll.EpollHandler;
import io.netty5.channel.epoll.EpollServerSocketChannel;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.nio.NioServerSocketChannel;
import io.netty5.util.concurrent.Future;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import java.util.List;

public class Network {

    protected static final WriteBufferWaterMark WATER_MARK = new WriteBufferWaterMark(1 << 20, 1 << 21);

    private static final int PORT = 52301;

    private final List<String> dummyServers = new ObjectArrayList<>();

    public Network() {
    }

    public void run() throws InterruptedException {
        Class<? extends ServerChannel> serverChannelClass = useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;

        MultithreadEventLoopGroup bossGroup = new MultithreadEventLoopGroup(1, getHandler());
        MultithreadEventLoopGroup workerGroup = new MultithreadEventLoopGroup(1, getHandler());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(serverChannelClass)
                .option(ChannelOption.IP_TOS, 0x18)
                .option(ChannelOption.SO_REUSEADDR, true)
                .childHandler(new ServerInitializer())
                .option(ChannelOption.IP_TOS, 0x18)
                .option(ChannelOption.AUTO_READ, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.WRITE_BUFFER_WATER_MARK, WATER_MARK);

        System.out.println("Using " + serverChannelClass.getSimpleName() + " for event loops.");
        System.out.println("Starting server on port " + PORT + "...");

        Future<Channel> future = bootstrap.bind(PORT);
        future.getNow().closeFuture().asStage().sync();
    }

    private IoHandlerFactory getHandler() {
        if (useEpoll()) {
            return EpollHandler.newFactory();
        }
        return NioHandler.newFactory();
    }

    /**
     * Überprüft, ob Epoll verwendet werden kann.
     *
     * @return true, wenn Epoll verfügbar ist, sonst false.
     */
    private boolean useEpoll() {
        return Epoll.isAvailable();
    }

}
