package eu.smoothcloud.api.server.info;

import eu.smoothcloud.api.player.CloudPlayer;
import eu.smoothcloud.api.servergroup.template.ServerGroupTemplate;

import java.util.List;

public interface ServerInfo {

    ServerId getServerId();
    String getHost();
    int getPort();
    boolean isOnline();
    boolean isIngame();
    List<CloudPlayer> getPlayers();
    int getMemory();
    String getMotd();
    int getOnlineCount();
    int getMaxPlayers();
    ServerState getServerState();
    ServerConfig getServerConfig();
    ServerGroupTemplate getServerGroupTemplate();

}
