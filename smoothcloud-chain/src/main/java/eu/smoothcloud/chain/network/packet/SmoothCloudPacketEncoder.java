/*
 * Copyright (c) 2024-2025 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
