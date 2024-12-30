/*
 * Copyright (c) 2024 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.node;

import eu.smoothcloud.api.INode;
import eu.smoothcloud.node.configuration.*;
import eu.smoothcloud.node.console.JLineConsole;
import lombok.Getter;
import org.json.JSONArray;

@Getter
public class SmoothCloudNode implements INode {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private final int threads;
    private final LaunchConfiguration launchConfiguration;
    private TemplatesConfiguration templatesConfiguration;
    private MessageConfiguration messageConfiguration;
    private JLineConsole console;

    public SmoothCloudNode() {
        this.threads = Runtime.getRuntime().availableProcessors();
        this.launchConfiguration = TomlSerializable.loadFromFile(".", "config.toml", LaunchConfiguration.class);
        this.templatesConfiguration = JsonSerializable.loadFromFile("storage", "templates.json", TemplatesConfiguration.class);
        if (this.templatesConfiguration == null) {
            this.templatesConfiguration = new TemplatesConfiguration(new JSONArray());
            this.templatesConfiguration.saveToFile("storage", "templates.json");
        }
        this.startConsole();
    }

    private void startConsole() {
        this.console = new JLineConsole();
        this.initializeConsole();
        this.console.start();
    }

    private void initializeConsole() {
        if (this.launchConfiguration == null) {
            this.messageConfiguration = TomlSerializable.loadFromFile(".", "storage/language/en_US.toml", MessageConfiguration.class);
            this.console.switchMode("setup");
            this.console.print("Switched to setup.");
            this.console.print("Do you agree the Minecraft Eula? https://www.minecraft.net/en-us/eula");
        } else {
            this.messageConfiguration = TomlSerializable.loadFromFile(".", "storage/language/" + this.launchConfiguration.getLanguage() + ".toml", MessageConfiguration.class);
        }
        this.console.print(this.console.prefix(), false);
    }

}
