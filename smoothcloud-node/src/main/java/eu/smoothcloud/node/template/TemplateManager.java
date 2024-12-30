package eu.smoothcloud.node.template;

import eu.smoothcloud.node.configuration.TemplatesConfiguration;
import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.util.charset.CharsetUtil;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class TemplateManager {
    private final JLineConsole console;
    private final String DEFAULT_PATH = "templates/";
    private final String[] DEFAULT_TEMPLATES = {
      "every/", "every_lobby/", "every_proxy/", "every_server/"
    };
    private Map<String, Path> templates;


    public TemplateManager(TemplatesConfiguration configuration, JLineConsole console) {
        this.console = console;
        this.templates = new HashMap<>();
        for (String template : configuration.getTemplates()) {
            this.templates.put(template, Path.of(DEFAULT_PATH + template));
        }
        File temp_dir = new File(DEFAULT_PATH);
        if(!temp_dir.exists() || !temp_dir.isDirectory())
            temp_dir.mkdirs();
        for (String def_temp : DEFAULT_TEMPLATES) {
            File def_temp_dir = new File(DEFAULT_PATH + def_temp);
            if(!def_temp_dir.exists())
                def_temp_dir.mkdirs();
        }
    }

    public Path getTemplate(String templateName) {
        return this.templates.get(templateName);
    }

    public int addTemplate(String templateName) {
        if(templateName.length() <= 2) return 1;
        if(!CharsetUtil.isValidInput(templateName)) return 2;
        if(this.templates.containsKey(templateName)) return 3;
        templates.put(templateName, Path.of(DEFAULT_PATH + templateName + "/"));
        File temp_dir = new File(this.templates.get(templateName).toUri());
        if(!temp_dir.exists())
            temp_dir.mkdirs();
        return 0;
    }

    public int renameTemplate(String templateName, String newTemplateName) {
        if(newTemplateName.length() <= 2) return 1;
        if(!CharsetUtil.isValidInput(newTemplateName)) return 2;
        if(!this.templates.containsKey(templateName)) return 3;
        Path path = this.templates.get(templateName);
        this.templates.remove(templateName, path);
        File newTemp = new File(path.toUri());
        if(newTemp.renameTo(new File(DEFAULT_PATH + newTemplateName))) {
            this.console.print("Directory Successful Renamed!");
            this.templates.put(newTemplateName, newTemp.toPath());
            return 0;
        } else {
            this.console.print("Failed to rename Directory");
            return 1;
        }
    }

    public boolean removeTemplate(String templateName) {
        if(!this.templates.containsKey(templateName)) return false;
        Path path = this.templates.get(templateName);
        this.templates.remove(templateName, path);
        File temp_dir = new File(path.toUri());
        temp_dir.delete();
        return true;
    }
}
