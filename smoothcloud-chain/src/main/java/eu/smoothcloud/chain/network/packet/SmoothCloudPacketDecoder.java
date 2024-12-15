package eu.smoothcloud.chain.network.packet;

import io.netty5.buffer.Buffer;
import io.netty5.channel.ChannelHandlerContext;
import io.netty5.handler.codec.ByteToMessageDecoder;
import io.netty5.handler.codec.CorruptedFrameException;

public class SmoothCloudPacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Buffer buffer) throws Exception {
        if (!ctx.channel().isActive()) {
            buffer.skipReadableBytes(buffer.readableBytes());
            return;
        }

        byte[] buf = new byte[3];
        int[] index = new int[]{0};

        for (int i = 0; i < buf.length; i++) {
            if (buffer.readableBytes() <= 0) {
                return;
            }

            buf[i] = buffer.readByte();
            if (buf[i] >= 0) {
                int length = SmoothPacket.readVarInt(buf, index);
                if (length == 0) {
                    throw new CorruptedFrameException("Empty Packet!");
                }

                if (buffer.readableBytes() < length) {
                    return;
                }

                Buffer payload = buffer.readSplit(length);

                ctx.fireChannelRead(payload);
                return;
            }
        }

        throw new CorruptedFrameException("Packet length wider than 21-bit");
    }

}
