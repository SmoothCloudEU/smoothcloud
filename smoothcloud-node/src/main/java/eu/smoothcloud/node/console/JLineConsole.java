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

import eu.smoothcloud.node.console.modes.*;
import eu.smoothcloud.util.console.ConsoleColor;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class JLineConsole {
    private final Terminal terminal;
    private final LineReader reader;

    private boolean isRunning;
    private boolean isPaused;
    private Mode currentMode;

    public JLineConsole() {
        try (Terminal terminal = TerminalBuilder.terminal()){
            terminal.enterRawMode();
            this.reader = LineReaderBuilder.builder().terminal(terminal).build();
            this.terminal = terminal;
            this.isRunning = true;
            this.isPaused = false;
            this.currentMode = new JLineDefaultMode(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        while (this.isRunning) {
            String input = this.reader.readLine(this.prefix()).trim();

            if (this.isPaused) {
                if (input.equalsIgnoreCase("resume")) {
                    this.isPaused = false;
                    this.print("Resumed console.");
                }
                continue;
            }

            if (input.equalsIgnoreCase("")) {
                this.print("[FF3333]The input field can not be empty.");
                this.prefix();
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
        this.print("Exited console.");
        System.exit(0);
    }

    public void switchMode(String modeName) {
        switch (modeName.toLowerCase()) {
            case "setup" -> this.currentMode = new JLineSetupMode(this);
            case "default" -> this.currentMode = new JLineDefaultMode(this);
            default -> this.print("Unknown mode: " + modeName);
        }
    }

    public String prefix() {
        String prefix = this.currentMode != null ? this.currentMode.getName() : "NoMode";
        return ConsoleColor.apply("\r" + prefix);
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
        this.reader.getTerminal().puts(InfoCmp.Capability.clear_screen);
        this.reader.getTerminal().flush();
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
