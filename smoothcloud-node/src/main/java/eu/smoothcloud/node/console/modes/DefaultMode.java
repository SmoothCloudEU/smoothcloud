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

package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.node.template.TemplateManager;
import eu.smoothcloud.util.console.ConsoleColor;

public class DefaultMode extends Mode {
    private final JLineConsole console;
    private final TemplateManager templateManager;

    public DefaultMode(JLineConsole console, TemplateManager templateManager) {
        this.console = console;
        this.templateManager = templateManager;
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public String getPrefix() {
        return ConsoleColor.apply("[00FFFF-00BFFF]SmoothCloud&7@&b%hostname &7» ");
    }

    @Override
    public void handleCommand(String command, String[] args) {
        switch (command.toLowerCase()) {
            case "help" -> {
                this.console.print("&7-------------------------------&bHelp&7-------------------------------");
                this.console.print(" &bexit                     &7- &fShutdown the cloud");
                this.console.print(" &bhelp                     &7- &fShow this help menu.");
                this.console.print(" &binfo version             &7- &fDisplays the current version of the cloud.");
                this.console.print(" &binfo group <group>       &7- &fDisplays information about the group. ");
                this.console.print(" &binfo service <service>   &7- &fDisplays information about the service. ");
                this.console.print("&7-------------------------------&bHelp&7-------------------------------");
            }
            case "info" -> {
                if (args.length == 0) {
                    this.console.print("&fUsage:");
                    this.console.print("&7- &binfo version");
                    this.console.print("&7- &binfo group <group>");
                    return;
                }
                switch (args[0].toLowerCase()) {
                    case "group" -> {

                    }
                    case "service" -> {
                        
                    }
                    case "version" ->
                            this.console.print("&fYour smoothcloud version is &b1.0.0/{commit-short}@development&7.");
                    default -> {
                        this.console.print("&fUsage:");
                        this.console.print("&7- &binfo version");
                        this.console.print("&7- &binfo group <group>");
                    }
                }
            }
            case "create" -> {
                switch (args[0].toLowerCase()) {
                    case "template" -> {
                        switch (this.templateManager.addTemplate(args[1])) {
                            case 0 -> this.console.print("&fYour Template with the Name: &b" + args[1] +  " &fis created!");
                            case 1 -> this.console.print("[FF3333]The Entered Template Name: &b" + args[1] + " [FF3333]is to short!");
                            case 2 -> this.console.print("[FF3333]The Entered Template Name: &b" + args[1] + " [FF3333]contains forbidden special character!");
                            case 3 -> this.console.print("[FF3333]The Entered Template Name: &b" + args[1] + " [FF3333]already exists!");
                        }
                    }
                    default -> {
                        this.console.print("&fUsage:");
                        this.console.print("&7- &bcreate template <templateName>");
                    }
                }
            }
            case "rename" -> {
                switch (args[0].toLowerCase()) {
                    case "template" -> {
                        switch (this.templateManager.renameTemplate(args[1], args[2])) {
                            case 0 -> this.console.print("&fYour Template with the Name: &b" + args[1] +  " &fis renamed to &b" + args[2] + " &f!");
                            case 1 -> this.console.print("[FF3333]The Entered NewTemplate Name: &b" + args[2] + " [FF3333]is to short!");
                            case 2 -> this.console.print("[FF3333]The Entered NewTemplate Name: &b" + args[2] + " [FF3333]contains forbidden special character!");
                            case 3 -> this.console.print("[FF3333]The Entered Template Name: &b" + args[1] + " [FF3333]don't exists!");
                        }
                    }
                    default -> {
                        this.console.print("&fUsage:");
                        this.console.print("&7- &brename template <templateName> <newTemplateName>");
                    }
                }
            }
            case "remove" -> {
                switch (args[0].toLowerCase()) {
                    case "template" -> {
                        if (this.templateManager.removeTemplate(args[1])) {
                            this.console.print("&fYour Template with the Name: &b" + args[1] +  "&fis removed!");
                        } else {
                            this.console.print("[FF3333]The Entered Template Name: &b" + args[1] + " [FF3333]don't exists!");
                        }
                    }
                    default -> {
                        this.console.print("&fUsage:");
                        this.console.print("&7- &bremove template <templateName>");
                    }
                }
            }
            case "clear" -> {
                this.console.clear();
            }
            default ->
                    this.console.print("[FF3333]The command &b" + command + " [FF3333]can not be executed by the console.");
        }
    }
}
