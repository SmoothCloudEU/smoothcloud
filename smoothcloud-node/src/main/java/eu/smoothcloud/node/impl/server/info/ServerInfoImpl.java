package eu.smoothcloud.node.impl.server.info;

import eu.smoothcloud.api.player.CloudPlayer;
import eu.smoothcloud.api.server.info.ServerConfig;
import eu.smoothcloud.api.server.info.ServerId;
import eu.smoothcloud.api.server.info.ServerInfo;
import eu.smoothcloud.api.server.info.ServerState;
import eu.smoothcloud.api.servergroup.template.ServerGroupTemplate;

import java.util.List;

public class ServerInfoImpl implements ServerInfo {

    @Override
    public ServerId getServerId() {
        return null;
    }

    @Override
    public int getMaxPlayers() {
        return 0;
    }

    @Override
    public int getMemory() {
        return 0;
    }

    @Override
    public int getOnlineCount() {
        return 0;
    }

    @Override
    public ServerConfig getServerConfig() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public List<CloudPlayer> getPlayers() {
        return List.of();
    }

    @Override
    public ServerGroupTemplate getServerGroupTemplate() {
        return null;
    }

    @Override
    public ServerState getServerState() {
        return null;
    }

    @Override
    public boolean isIngame() {
        return false;
    }

    @Override
    public boolean isOnline() {
        return false;
    }

    @Override
    public String getHost() {
        return "";
    }

    @Override
    public String getMotd() {
        return "";
    }
}
