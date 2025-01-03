/*
 * Copyright (c) 2025 SmoothCloud team & contributors
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

package eu.smoothcloud.node.template;

import eu.smoothcloud.node.configuration.TemplatesConfiguration;
import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.util.charset.CharsetUtil;
import eu.smoothcloud.util.converter.CloudList;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    private final JLineConsole console;
    private final String DEFAULT_PATH = "templates/";
    private final String[] DEFAULT_TEMPLATES = {
            "every/", "every_lobby/", "every_proxy/", "every_server/"
    };
    @Getter
    private Map<String, Path> templates;
    private TemplatesConfiguration configuration;


    public TemplateManager(TemplatesConfiguration configuration, JLineConsole console) {
        this.console = console;
        this.configuration = configuration;
        this.templates = new HashMap<>();
        for (String template : configuration.getTemplates()) {
            this.templates.put(template, Path.of(DEFAULT_PATH + template));
        }
        File temp_dir = new File(DEFAULT_PATH);
        if (!temp_dir.exists() || !temp_dir.isDirectory()) {
            temp_dir.mkdirs();
            temp_dir.setWritable(true, false);
        }
        for (String def_temp : DEFAULT_TEMPLATES) {
            File def_temp_dir = new File(DEFAULT_PATH + def_temp);
            if (!def_temp_dir.exists()) {
                def_temp_dir.mkdirs();
                def_temp_dir.setWritable(true, false);
            }
        }
    }

    private void saveTemplates() {
        CloudList<String> templates = new CloudList<>(String.class);
        this.templates.forEach((name, path) -> templates.add(name));
        this.configuration.setTemplates(templates.convertToArray());
        this.configuration.saveToFile("storage", "templates.toml");
    }

    public Path getTemplate(String templateName) {
        return this.templates.get(templateName);
    }

    public int addTemplate(String templateName) {
        if (templateName.length() <= 2) return 1;
        if (!CharsetUtil.isValidInput(templateName)) return 2;
        if (this.templates.containsKey(templateName)) return 3;
        templates.put(templateName, Path.of(DEFAULT_PATH + templateName + "/"));
        File temp_dir = new File(this.templates.get(templateName).toUri());
        if (!temp_dir.exists()) {
            temp_dir.mkdirs();
            temp_dir.setWritable(true, false);
        }
        saveTemplates();
        return 0;
    }

    public int renameTemplate(String templateName, String newTemplateName) {
        try {
            if (newTemplateName.length() <= 2) return 1;
            if (!CharsetUtil.isValidInput(newTemplateName)) return 2;
            if (!this.templates.containsKey(templateName)) return 3;
            Path existingTemp = this.templates.get(templateName);
            Path newTemp = Path.of(DEFAULT_PATH + newTemplateName);
            Files.move(
                    existingTemp,
                    newTemp,
                    StandardCopyOption.ATOMIC_MOVE
            );
            this.templates.remove(templateName, existingTemp);
            this.templates.put(newTemplateName, newTemp);
            saveTemplates();
            return 0;
        } catch (IOException e) {
            return 4;
        }
    }

    public boolean removeTemplate(String templateName) {
        if (!this.templates.containsKey(templateName)) return false;
        Path path = this.templates.get(templateName);
        this.templates.remove(templateName, path);
        File temp_dir = new File(path.toUri());
        temp_dir.delete();
        saveTemplates();
        return true;
    }

}
