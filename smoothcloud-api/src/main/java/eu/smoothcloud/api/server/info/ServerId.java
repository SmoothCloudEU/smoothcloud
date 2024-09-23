package eu.smoothcloud.api.server.info;

import eu.smoothcloud.api.servergroup.ServerGroup;

import java.util.UUID;

public interface ServerId {

    String getServerGroupName();
    ServerGroup getServerGroup();
    int getId();
    UUID getUniqueId();
    String getWrapperId();
    String getServerId();
    String getGameId();

}
