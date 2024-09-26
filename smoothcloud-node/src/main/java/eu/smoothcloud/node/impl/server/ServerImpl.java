package eu.smoothcloud.node.impl.server;

import eu.smoothcloud.api.server.Server;
import eu.smoothcloud.api.server.info.ServerId;
import eu.smoothcloud.api.server.info.ServerInfo;
import eu.smoothcloud.api.server.info.ServerMeta;
import eu.smoothcloud.api.servergroup.ServerGroup;
import eu.smoothcloud.api.wrapper.Wrapper;

public class ServerImpl implements Server {

    @Override
    public ServerGroup getServerGroup() {
        return null;
    }

    @Override
    public ServerId getServerId() {
        return null;
    }

    @Override
    public ServerInfo getLastServerInfo() {
        return null;
    }

    @Override
    public String toHash() {
        return null;
    }

    @Override
    public Server fromHash() {
        return null;
    }

    @Override
    public ServerInfo getServerInfo() {
        return null;
    }

    @Override
    public ServerMeta getServerMeta() {
        return null;
    }

    @Override
    public Wrapper getWrapper() {
        return null;
    }
}
