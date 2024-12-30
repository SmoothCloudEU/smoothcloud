package eu.smoothcloud.node.configuration;

import lombok.Getter;

@Getter
public class MessageConfiguration implements TomlSerializable {
    private String prefix;
    private String setupLanguageQuestion;
    private String setupHostQuestion;
    private String setupPortQuestion;
    private String setupMemoryQuestion;
    private String setupThreadsQuestion;
    
    public MessageConfiguration() {
    }
}
