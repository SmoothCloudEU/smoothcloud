package eu.smoothcloud.node.configuration;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;

@Getter
@Setter
public class TemplatesConfiguration implements JsonSerializable {
    private JSONArray templates;

    public TemplatesConfiguration() {
    }

    public TemplatesConfiguration(JSONArray templates) {
        this.templates = templates;
    }
}
