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

import eu.smoothcloud.node.SmoothCloudNode;
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.util.console.ConsoleColor;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class SetupMode extends Mode {
    private final SmoothCloudNode node;
    private final JLineConsole console;
    private int step = 1;
    private LaunchConfiguration configuration;

    public SetupMode(SmoothCloudNode node, JLineConsole console) {
        this.node = node;
        this.console = console;
        this.configuration = new LaunchConfiguration();
    }

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public String getPrefix() {
        return ConsoleColor.apply("&eSetup&7@&b%hostname &7» ");
    }

    @Override
    public void handleCommand(String command, String[] args) {
        switch (command) {
            default -> {
                switch (step) {
                    case 1 -> {
                        if(!command.equalsIgnoreCase("yes")) {
                            this.console.print("You musst agree the eula.");
                            return;
                        }
                        this.configuration.setEula(true);
                        System.out.print("\n");
                        this.console.print("Which language do you want to use? (en_US, de_DE)");
                        step++;
                    }
                    case 2 -> {
                        List<String> availableLanguages = new ArrayList<>(List.of("en_US", "de_DE"));
                        if (!availableLanguages.contains(command)) {
                            this.console.print("[FF3333]We doesn't support this language.");
                            return;
                        }
                        this.configuration.setLanguage(command);
                        System.out.print("\n");
                        this.console.print("Which host do you want to use? (" + String.join(", ", this.getAllAddresses()) + ")");
                        step++;
                    }
                    case 3 -> {
                        List<String> availableHosts = new ArrayList<>(this.getAllAddresses());
                        if (!availableHosts.contains(command)) {
                            this.console.print("[FF3333]We doesn't support this host.");
                            return;
                        }
                        this.configuration.setHost(command);
                        System.out.print("\n");
                        this.console.print("Which port do you want to use? (1 - 65535)");
                        step++;
                    }
                    case 4 -> {
                        try {
                            int port = Integer.parseInt(command);
                            if (port < 1 || port > 65535) {
                                this.console.print("[FF3333]This port is invalid.");
                                return;
                            }
                            this.configuration.setPort(port);
                            System.out.print("\n");
                            this.console.print("How many memory do you want to use? (minimum 512)");
                            step++;
                        } catch (NumberFormatException e) {
                            this.console.print("[FF3333]Your input is not a number.");
                        }
                    }
                    case 5 -> {
                        try {
                            int memory = Integer.parseInt(command);
                            if (memory < 512) {
                                this.console.print("[FF3333]Too little memory.");
                                return;
                            }
                            this.configuration.setMemory(memory);
                            System.out.print("\n");
                            this.console.print("How many threads do you want to use? (maximum " + Runtime.getRuntime().availableProcessors() + ")");
                            step++;
                        } catch (NumberFormatException e) {
                            this.console.print("[FF3333]Your input is not a number.");
                        }
                    }
                    case 6 -> {
                        try {
                            int threads = Integer.parseInt(command);
                            if (threads > Runtime.getRuntime().availableProcessors()) {
                                this.console.print("[FF3333]Your system doesn't support more than " + Runtime.getRuntime().availableProcessors() + " threads.");
                                return;
                            }
                            this.configuration.setThreads(threads);
                            //this.node.getThreadManager().updateMaxThreads(threads);
                            this.configuration.saveToFile(".", "config.toml");
                            System.out.print("\n");
                            this.console.print("Cloud was successfully set up.");
                            this.console.switchMode("default");
                            this.console.sendWelcomeMessage();
                        } catch (NumberFormatException e) {
                            this.console.print("[FF3333]Your input is not a number.");
                        }
                    }
                }
            }
        }
    }

    private List<String> getAllAddresses() {
        List<String> addresses = new ArrayList<>();
        addresses.add("127.0.0.1");
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress inetAddress = inetAddresses.nextElement();
                        addresses.add(inetAddress.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error retrieving network interfaces: " + e.getMessage());
        }
        return addresses;
    }
}
