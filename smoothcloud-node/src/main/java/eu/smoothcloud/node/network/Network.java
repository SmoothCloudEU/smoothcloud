package eu.smoothcloud.node.network;

import de.syntaxjason.core.event.connect.ChannelConnectEvent;
import de.syntaxjason.core.event.connect.ChannelDisconnectEvent;
import de.syntaxjason.core.event.message.ChannelMessageEvent;
import de.syntaxjason.server.NetbridgeServer;
import eu.smoothcloud.node.network.entity.NetworkPinger;
import eu.smoothcloud.node.protocol.PacketDelivery;
import eu.smoothcloud.node.protocol.packets.HandshakePacket;
import eu.smoothcloud.node.protocol.packets.ResponsePacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Network {

    private NetbridgeServer server;

    public Network(NetbridgeServer server) {
        this.server = server;
        registerHandlers();
        server.start();
    }

    public void stop() {
        server.stop();
    }

    private void registerHandlers() {
        this.server.getEventManager().registerHandler(ChannelConnectEvent.class, this::handleConnect);
        this.server.getEventManager().registerHandler(ChannelDisconnectEvent.class, this::handleDisconnect);
        this.server.getEventManager().registerHandler(ChannelMessageEvent.class, this::handleMessage);
    }

    private void handleConnect(ChannelConnectEvent channelConnectEvent) {
        System.out.println("Client connected: " + channelConnectEvent.getChannel().getRemoteAddress());
    }

    private void handleDisconnect(ChannelDisconnectEvent channelDisconnectEvent) {
        System.out.println("Client disconnected: " + channelDisconnectEvent.getChannel().getRemoteAddress());
    }

    private void handleMessage(ChannelMessageEvent event) {
        try {
            int packetId = event.getMessage()[1];
            if (packetId == 0x00) {
                HandshakePacket packet = new HandshakePacket(event.getChannel());
                packet.decode(event.getMessage());
                new PacketDelivery(event.getChannel(), packet).sendPacket(0x00);
                return;
            }
            if(packetId == 0x01) {
                ResponsePacket responsePacket = new ResponsePacket();
                new PacketDelivery(event.getChannel(), responsePacket).sendPacket(0x01);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
