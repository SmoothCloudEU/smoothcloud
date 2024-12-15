package eu.smoothcloud.chain.network;

import eu.smoothcloud.chain.network.packet.SmoothCloudPacketDecoder;
import eu.smoothcloud.chain.network.packet.SmoothCloudPacketEncoder;
import io.netty5.channel.ChannelInitializer;
import io.netty5.channel.ChannelPipeline;
import io.netty5.channel.socket.SocketChannel;

public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new SmoothCloudPacketDecoder());
        pipeline.addLast(new SmoothCloudPacketEncoder());
        pipeline.addLast(new CloudChainHandler());
    }
}
