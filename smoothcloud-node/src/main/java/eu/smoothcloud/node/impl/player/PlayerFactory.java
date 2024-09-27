package eu.smoothcloud.node.impl.player;

import eu.smoothcloud.api.player.CloudPlayer;

import java.util.UUID;

public class PlayerFactory {

    public static CloudPlayer createCloudPlayer(String name) {
        return new CloudPlayerImpl();
    }

    public static CloudPlayer createCloudPlayer(UUID uuid) {
       return new CloudPlayerImpl();
    }

}
