package eu.smoothcloud.node.data;

import eu.smoothcloud.api.server.Server;
import eu.smoothcloud.api.servergroup.template.ServerGroupTemplate;
import me.syntaxjason.ConfigurationType;
import me.syntaxjason.annotation.ConfigParam;
import me.syntaxjason.annotation.ConfigSource;
import me.syntaxjason.config.JsonConfiguration;

import java.io.IOException;
import java.util.List;

public class GroupData extends JsonConfiguration {

    public GroupData(String filePath) throws IOException {
        super(filePath);
    }

    public String getGroupName() {
        return getConfigValue("groupName", String.class);
    }

    ServerGroupTemplate getServerGroupTemplate() {
        return null;
    }

    String getVersion() {
        return getConfigValue("version", String.class);
    }

    String getWrapperName() {
        return getConfigValue("wrapper", String.class);
    }

    int getMaxOnlineCount() {
        return 0;
    }

    long getMaxMemory() {
        return 0;
    }

    List<Server> getServerList() {
        return null;
    }
}
