package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.node.group.Group;
import eu.smoothcloud.node.group.GroupType;
import eu.smoothcloud.node.template.TemplateManager;
import eu.smoothcloud.util.console.ConsoleColor;

public class GroupSetupMode extends Mode {
    private final JLineConsole console;
    private final TemplateManager templateManager;
    private final GroupType type;
    private Group group;
    private int step = 1;

    public GroupSetupMode(JLineConsole console, TemplateManager templateManager, GroupType type) {
        this.console = console;
        this.templateManager = templateManager;
        this.type = type;
    }

    @Override
    public String getName() {
        return "group-setup";
    }

    @Override
    public String getPrefix() {
        return ConsoleColor.apply("&eGroup-Setup&7@&b%hostname &7» ");
    }

    @Override
    public void handleCommand(String command, String[] args) {
        switch (step) {
            case 1 -> {
                this.group = new Group(type, command);
                this.console.print("Group name: " + group.getName());
                this.console.print("Which template should be used?");
                step++;
            }
            case 2 -> {
                this.group.setTemplate(command);
                if (command.equalsIgnoreCase("create")) {
                    this.templateManager.addTemplate(group.getName());
                }
                this.console.print("Template name: " + group.getTemplate());
                this.console.print("Which software do you want to use?");
                step++;
            }
            case 3 -> {
                this.group.setSoftware(command);
                this.console.print("Software name: " + group.getSoftware());
                this.console.print("Which version of the software to you want to use?");
                step++;
            }
            case 4 -> {
                this.group.setVersion(command);
                this.console.print("Version: " + group.getVersion());
                this.console.print("Which permission do you want to use?");
                step++;
            }
            case 5 -> {
                this.group.setPermission(command);
                this.console.print("Permission: " + group.getPermission());
                this.console.print("Which java version do you want to use?");
                step++;
            }
            case 6 -> {
                this.group.setJava(command);
                this.console.print("Java version: " + group.getJava());
                this.console.print("Group creation was successfully.");
                this.console.switchMode("default");
                this.console.clear();
                this.console.sendWelcomeMessage();
            }
        }
    }
}
