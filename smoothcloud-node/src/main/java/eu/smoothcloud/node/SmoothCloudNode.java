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

import eu.smoothcloud.chain.CloudChain;
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.thread.ThreadBound;
import eu.smoothcloud.thread.ThreadManager;

public class SmoothCloudNode {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private LaunchConfiguration launchConfiguration;
    private Console console;
    private int threads = Runtime.getRuntime().availableProcessors();

    private CloudChain chain;

    public SmoothCloudNode() {
        ThreadManager manager = new ThreadManager(launchConfiguration.getThreads() > 0 ? launchConfiguration.getThreads() : threads);
        manager.startTask("Console", this::startConsole);
        print(console);

        ThreadBound<CloudChain> cloudChain = new ThreadBound<>(manager, "CloudChain", new CloudChain());
        cloudChain.runOnThread(CloudChain::initialize);
    }

    private void startCloudChain() {
        this.chain = new CloudChain();
    }

    private void startConsole() {
        this.console = new Console();
        console.start();
    }

    private void print(Console console) {
        console.print("&eInitialize Cloud");
    }
}
