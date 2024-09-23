package eu.smoothcloud.node.network;

import de.syntaxjason.core.NetbridgeInstance;
import de.syntaxjason.core.event.EventManager;
import de.syntaxjason.core.event.connect.ChannelConnectEvent;
import de.syntaxjason.core.event.connect.ChannelDisconnectEvent;
import de.syntaxjason.core.event.message.ChannelMessageEvent;
import de.syntaxjason.server.NetbridgeServer;

public class Network {

    private final NetbridgeServer server;
    private EventManager eventManager;

    public Network(NetbridgeInstance<NetbridgeServer> instance) {
        server = instance.getInstance();
        this.eventManager = server.getEventManager();
        registerHandlers();
    }

    private void registerHandlers() {
        eventManager.registerHandler(ChannelMessageEvent.class, this::onMessage);
        eventManager.registerHandler(ChannelConnectEvent.class, this::onConnect);
        eventManager.registerHandler(ChannelDisconnectEvent.class, this::onDisconnect);
    }

    private void onMessage(ChannelMessageEvent event) {

    }

    private void onConnect(ChannelConnectEvent event) {

    }

    private void onDisconnect(ChannelDisconnectEvent event) {

    }

}
