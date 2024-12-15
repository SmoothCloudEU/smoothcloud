package eu.smoothcloud.chain.network;

import eu.smoothcloud.chain.network.packet.SmoothPacket;
import eu.smoothcloud.chain.network.packet.lifecycle.HeartbeatPacket;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.channel.SimpleChannelInboundHandler;

public class CloudChainHandler extends SimpleChannelInboundHandler<SmoothPacket> {

    /*
    TODO:


     */


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, SmoothPacket packet) throws Exception {
        if(packet.getPacketType() == 1) {
            HeartbeatPacket heartbeatPacket = (HeartbeatPacket) packet;
        }
    }

}
