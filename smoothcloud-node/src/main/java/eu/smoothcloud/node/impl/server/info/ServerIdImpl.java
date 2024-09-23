package eu.smoothcloud.node.impl.server.info;

import eu.smoothcloud.api.server.info.ServerId;
import eu.smoothcloud.api.servergroup.ServerGroup;

import java.util.UUID;

public class ServerIdImpl implements ServerId {

    @Override
    public ServerGroup getServerGroup() {
        return null;
    }

    @Override
    public String getServerId() {
        return "";
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getGameId() {
        return "";
    }

    @Override
    public String getServerGroupName() {
        return "";
    }

    @Override
    public String getWrapperId() {
        return "";
    }

    @Override
    public UUID getUniqueId() {
        return null;
    }
}
