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

package eu.smoothcloud.node.console;

import eu.smoothcloud.node.console.modes.DefaultMode;
import eu.smoothcloud.node.console.modes.Mode;
import eu.smoothcloud.node.console.modes.SetupMode;
import eu.smoothcloud.util.console.ConsoleColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Console {

    private boolean isRunning;
    private boolean isPaused;
    private Mode currentMode;

    public Console() {
        this.isRunning = true;
        this.isPaused = false;
        this.currentMode = new DefaultMode(this);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (this.isRunning) {
            if (this.isPaused) {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("resume")) {
                    this.isPaused = false;
                    this.print("Resumed console.");
                }
                continue;
            }

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("")) {
                this.print("[FF3333]The input field can not be empty.");
                this.print("", false);
                continue;
            }
            String[] inputParts = input.split(" ");
            String command = inputParts[0].toLowerCase();
            String[] args = Arrays.copyOfRange(inputParts, 1, inputParts.length);

            switch (command) {
                case "exit" -> {
                    this.isRunning = false;
                    this.print("Exiting console...");
                }
                case "pause" -> {
                    this.isPaused = true;
                    this.print("Paused console.");
                }
                default -> {
                    this.currentMode.handleCommand(command, args);
                }
            }
        }
        scanner.close();
        this.print("Exited console.");
        System.exit(0);
    }

    public void switchMode(String modeName) {
        switch (modeName.toLowerCase()) {
            case "setup" -> this.currentMode = new SetupMode(this);
            case "default" -> this.currentMode = new DefaultMode(this);
            default -> this.print("Unknown mode: " + modeName);
        }
    }

    public void print(String message) {
        if (this.currentMode == null) {
            throw new IllegalStateException("Current mode is not initialized");
        }
        this.print(message, true);
    }

    public void print(String message, boolean newLine) {
        String prefix = this.currentMode != null ? this.currentMode.getName() : "NoMode";
        String coloredMessage = ConsoleColor.apply(prefix + message);
        if (newLine) {
            System.out.println(coloredMessage);
            return;
        }
        System.out.print(coloredMessage);
    }

    public void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public Mode getCurrentMode() {
        return this.currentMode;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}
