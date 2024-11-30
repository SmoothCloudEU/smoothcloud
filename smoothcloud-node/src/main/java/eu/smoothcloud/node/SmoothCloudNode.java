package eu.smoothcloud.node;

import de.syntaxjason.core.NetbridgeInstance;
import de.syntaxjason.server.NetbridgeServer;
import eu.smoothcloud.api.SmoothCloudAPI;
import eu.smoothcloud.node.network.Network;
import eu.smoothcloud.node.terminal.CloudTerminal;
import lombok.Getter;

import java.io.IOException;

@Getter
public class SmoothCloudNode implements SmoothCloudAPI {
    private final CloudTerminal cloudTerminal;
    private NetbridgeInstance<NetbridgeServer> networkInstance;

    public SmoothCloudNode() {
        startNetwork("localhost", 25565);
        this.cloudTerminal = new CloudTerminal();
        this.cloudTerminal.run();
    }

    private void startNetwork(String hostAddress, int port) {
        networkInstance = new NetbridgeServer(port, hostAddress);
        new Network(networkInstance.getInstance());
    }
}
