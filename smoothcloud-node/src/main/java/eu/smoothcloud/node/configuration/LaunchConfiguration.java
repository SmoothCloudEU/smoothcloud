package eu.smoothcloud.node.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LaunchConfiguration implements JsonSerializable {
    private String language;
    private String host;
    private int port;
    private int memory;

    public LaunchConfiguration() {}
}
