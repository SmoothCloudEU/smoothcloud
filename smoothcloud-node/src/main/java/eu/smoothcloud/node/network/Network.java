package eu.smoothcloud.node.network;

import de.syntaxjason.core.NetbridgeInstance;
import de.syntaxjason.core.event.EventManager;
import de.syntaxjason.core.event.connect.ChannelConnectEvent;
import de.syntaxjason.core.event.connect.ChannelDisconnectEvent;
import de.syntaxjason.core.event.message.ChannelMessageEvent;
import de.syntaxjason.server.NetbridgeServer;
import eu.smoothcloud.node.network.type.VarInt;
import eu.smoothcloud.node.network.type.VarString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Network {

    private final NetbridgeServer server;
    private EventManager eventManager;

    public Network(NetbridgeInstance<NetbridgeServer> instance) {
        server = instance.getInstance();
        this.eventManager = server.getEventManager();
        registerHandlers();
        server.start();
    }

    private void registerHandlers() {
            eventManager.registerHandler(ChannelMessageEvent.class, this::onMessage);
            eventManager.registerHandler(ChannelConnectEvent.class, this::onConnect);
            eventManager.registerHandler(ChannelDisconnectEvent.class, this::onDisconnect);
    }

    private void onMessage(ChannelMessageEvent event) {
        try {
            byte[] messageBytes = event.getMessage();
            System.out.println(Arrays.toString(messageBytes));

            int packetId = messageBytes[1] & 0xFF;
            System.out.println("Empfangenes Paket-ID: " + packetId);

            switch (packetId) {
                case 0x00 -> handleMinecraftHandshake(event, messageBytes);
                case 0x01 -> handleMinecraftPing(event, messageBytes);
                case 0x02 -> handleMinecraftLogin(event, messageBytes);
                case 0x03 -> handleMinecraftChat(event, messageBytes);
                default -> System.out.println("Unbekanntes Paket: " + packetId);
            }

        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void onConnect(ChannelConnectEvent event) {
        System.out.println("Client connected: " + event.getChannel().getRemoteAddress());
    }

    private void onDisconnect(ChannelDisconnectEvent event) {
        System.out.println("Client disconnected: " + event.getChannel().getRemoteAddress());
    }

    private void handleMinecraftHandshake(ChannelMessageEvent event, byte[] data) throws IOException {
        System.out.println("Minecraft Handshake empfangen.");

        int protocolVersion = readVarInt(data, 2);
        System.out.println("Protokoll-Version: " + protocolVersion);

        int currentIndex = 2 + getVarIntSize(protocolVersion);

        String serverAddress = readString(data, currentIndex);
        currentIndex += getVarIntSize(serverAddress.length()) + serverAddress.length();
        System.out.println("Server-Adresse: " + serverAddress);

        int serverPort = ((data[currentIndex] & 0xFF) << 8) | (data[currentIndex + 1] & 0xFF);
        currentIndex += 2;
        System.out.println("Server-Port: " + serverPort);

        int requestedProtocol = readVarInt(data, currentIndex);
        System.out.println("Requested Protocol: " + requestedProtocol);

        int nextState = requestedProtocol;
        System.out.println("Nächster Zustand (Next State): " + nextState);

        VarInt varInt = new VarInt(protocolVersion);
        byte[] s = serverAddress.getBytes(StandardCharsets.UTF_8);
        VarInt port = new VarInt(serverPort);
        VarInt rProtocol = new VarInt(requestedProtocol);

        switch (nextState) {
            case 1:
                System.out.println("Wechsel zu: Status-Check (Ping)");
                ByteBuffer buffer = ByteBuffer.allocate(4096);
                buffer.put(varInt.toBytes());
                buffer.put(s);
                buffer.put(port.toBytes());
                buffer.put(rProtocol.toBytes());
                event.getChannel().send(buffer.array());
                break;
            case 2:
                System.out.println("Wechsel zu: Login");
                break;
            default:
                System.out.println("Unbekannter Handshake-Zustand: " + nextState);
        }
    }


    private void handleMinecraftPing(ChannelMessageEvent event, byte[] data) throws IOException {
        System.out.println("Minecraft Ping empfangen.");

        String jsonResponse = "{" +
                "\"version\": {" +
                "\"name\": \"1.21\"," +
                "\"protocol\": 767" +
                "}," +
                "\"players\": {" +
                "\"max\": 100," +
                "\"online\": 5," +
                "\"sample\": [" +
                "{" +
                "\"name\": \"ezTxmMC\"," +
                "\"id\": \"fcae1f93-bc70-4f9f-86e3-3cf132e1c372\"" +
                "}" +
                "]" +
                "}," +
                "\"description\": {" +
                "\"text\": \"Hello, world!\"" +
                "}," +
                "\"favicon\": \"data:image/png;base64,<data>\"," +
                "\"enforcesSecureChat\": false" +
                "}";

        byte[] jsonResponseBytes = jsonResponse.getBytes("UTF-8");
        byte[] lengthBytes = encode(jsonResponseBytes.length);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        outputStream.write(0x00);
        outputStream.write(lengthBytes);
        outputStream.write(jsonResponseBytes);

        event.getChannel().send(outputStream.toByteArray());
        System.out.println("Ping-Antwort gesendet.");
    }

    private void handleMinecraftLogin(ChannelMessageEvent event, byte[] data) throws IOException {
        System.out.println("Minecraft Login-Paket empfangen.");
        String playerName = readString(data, 2);
        System.out.println("Spieler-Name: " + playerName);
    }

    private void handleMinecraftChat(ChannelMessageEvent event, byte[] data) throws IOException {
        System.out.println("Minecraft Chat-Nachricht empfangen.");
        String chatMessage = readString(data, 2);
        System.out.println("Nachricht: " + chatMessage);
        event.getChannel().send(("Broadcast: " + chatMessage).getBytes());
    }

    public int readVarInt(byte[] data, int startIndex) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;

        do {
            read = data[startIndex + numRead];
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new IOException("VarInt ist zu lang");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public String readString(byte[] data, int startIndex) throws IOException {
        int stringLength = readVarInt(data, startIndex);

        int stringStartIndex = startIndex + getVarIntSize(stringLength);

        byte[] stringBytes = new byte[stringLength];
        System.arraycopy(data, stringStartIndex, stringBytes, 0, stringLength);

        return new String(stringBytes, "UTF-8");
    }

    public int getVarIntSize(int value) {
        int size = 0;
        do {
            size++;
            value >>>= 7;
        } while (value != 0);
        return size;
    }

    public byte[] encode(int value) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        while ((value & 0xFFFFFF80) != 0L) {
            output.write(value & 0x7F | 0x80);
            value >>>= 7;
        }
        output.write(value & 0x7F);
        return output.toByteArray();
    }

}
