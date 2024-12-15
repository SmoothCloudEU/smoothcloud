package eu.smoothcloud.chain.network.packet;

import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.MessageToByteEncoder;

public class SmoothCloudPacketEncoder extends MessageToByteEncoder<SmoothPacket> {

    @Override
    protected Buffer allocateBuffer(ChannelHandlerContext ctx, SmoothPacket packet) throws Exception {
        int estimatedSize = 4 + 4;
        estimatedSize += 10;

        estimatedSize += packet.getEstimatedSize();

        return ctx.bufferAllocator().allocate(estimatedSize);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, SmoothPacket packet, Buffer out) {
        PacketUtils.encode(out, packet);
    }
}
