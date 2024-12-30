package eu.smoothcloud.node.configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TemplatesConfiguration implements TomlSerializable {
    private String[] templates;

    public TemplatesConfiguration(String[] templates) {
        this.templates = templates;
    }
}
