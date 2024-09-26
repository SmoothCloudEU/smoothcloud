package eu.smoothcloud.api.server;

import eu.smoothcloud.api.server.info.ServerId;
import eu.smoothcloud.api.server.info.ServerInfo;
import eu.smoothcloud.api.server.info.ServerMeta;
import eu.smoothcloud.api.servergroup.ServerGroup;
import eu.smoothcloud.api.wrapper.Wrapper;

public interface Server {

    ServerId getServerId();
    ServerMeta getServerMeta();
    Wrapper getWrapper();
    ServerGroup getServerGroup();
    ServerInfo getServerInfo();
    ServerInfo getLastServerInfo();

    String toHash();
    Server fromHash();

}
