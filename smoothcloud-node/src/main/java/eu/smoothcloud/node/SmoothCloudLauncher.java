package eu.smoothcloud.node;

import de.syntaxjason.core.network.ServerSocket;
import de.syntaxjason.core.network.channel.Channel;
import de.syntaxjason.core.network.channel.NetbridgeChannel;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;

public class SmoothCloudLauncher {

    private static HashMap<String, Channel> connections = new HashMap<>();

    public static void main(String[] args) throws Exception {

        new SmoothCloudNode();

    }
}
