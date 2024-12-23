package eu.smoothcloud.node.configuration;

public class MessageConfiguration implements TomlSerializable {
    private String prefix;
    private String setupLanguageQuestion;
    private String setupHostQuestion;
    private String setupPortQuestion;
    private String setupMemoryQuestion;
    private String setupThreadsQuestion;
    
    public MessageConfiguration() {
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSetupLanguageQuestion() {
        return setupLanguageQuestion;
    }

    public String getSetupHostQuestion() {
        return setupHostQuestion;
    }

    public String getSetupPortQuestion() {
        return setupPortQuestion;
    }

    public String getSetupMemoryQuestion() {
        return setupMemoryQuestion;
    }

    public String getSetupThreadsQuestion() {
        return setupThreadsQuestion;
    }
}
