/*
 * Copyright (c) 2024-2025 SmoothCloud team & contributors
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
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.configuration.MessageConfiguration;
import eu.smoothcloud.node.configuration.TemplatesConfiguration;
import eu.smoothcloud.node.configuration.TomlSerializable;
import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.node.template.TemplateManager;
import eu.smoothcloud.util.converter.CloudList;
import lombok.Getter;

@Getter
public class SmoothCloudNode implements INode {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private int threads;
    private final LaunchConfiguration launchConfiguration;
    private TemplatesConfiguration templatesConfiguration;
    private MessageConfiguration messageConfiguration;
    private JLineConsole console;
    private TemplateManager templateManager;

    public SmoothCloudNode() {
        this.threads = 2;
        this.launchConfiguration = TomlSerializable.loadFromFile(".", "config.toml", LaunchConfiguration.class);
        this.templatesConfiguration = TomlSerializable.loadFromFile("storage", "templates.toml", TemplatesConfiguration.class);
        if (this.templatesConfiguration == null) {
            this.templatesConfiguration = new TemplatesConfiguration(new CloudList<>(String.class).convertToArray());
            this.templatesConfiguration.saveToFile("storage", "templates.toml");
        }
        this.templateManager = new TemplateManager(this.templatesConfiguration, this.console);
        this.startConsole();
    }

    private void startConsole() {
        this.console = new JLineConsole(this.templateManager);
        this.initializeConsole();
        this.console.start();
    }

    private void initializeConsole() {
        if (this.launchConfiguration == null) {
            this.messageConfiguration = TomlSerializable.loadFromFile(".", "storage/language/en_US.toml", MessageConfiguration.class);
            this.console.switchMode("setup");
            this.console.print("Switched to setup.");
            this.console.print("Do you agree to the Minecraft EULA? https://www.minecraft.net/en-us/eula (yes or no)");
        } else {
            this.threads = this.launchConfiguration.getThreads();
            this.messageConfiguration = TomlSerializable.loadFromFile(".", "storage/language/" + this.launchConfiguration.getLanguage() + ".toml", MessageConfiguration.class);
        }
        this.console.print(this.console.prefix(), false);
    }

}
