package eu.smoothcloud.chain.network;

import io.netty5.bootstrap.ServerBootstrap;
import io.netty5.channel.*;
import io.netty5.channel.epoll.Epoll;
import io.netty5.channel.epoll.EpollHandler;
import io.netty5.channel.epoll.EpollServerSocketChannel;
import io.netty5.channel.group.ChannelGroupFuture;
import io.netty5.channel.nio.NioHandler;
import io.netty5.channel.socket.nio.NioServerSocketChannel;
import io.netty5.util.concurrent.Future;

public class Network {

    private static final int PORT = 52301;

    public Network() {
    }

    public void run() throws InterruptedException {
        Class<? extends ServerChannel> serverChannelClass = useEpoll() ? EpollServerSocketChannel.class : NioServerSocketChannel.class;

        MultithreadEventLoopGroup bossGroup = new MultithreadEventLoopGroup(1, getHandler());
        MultithreadEventLoopGroup workerGroup = new MultithreadEventLoopGroup(1, getHandler());

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(serverChannelClass)
                .childHandler(new ServerInitializer());

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
