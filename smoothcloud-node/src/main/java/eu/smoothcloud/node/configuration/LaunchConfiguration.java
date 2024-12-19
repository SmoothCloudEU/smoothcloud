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

package eu.smoothcloud.node.configuration;

public class LaunchConfiguration implements JsonSerializable {
    private String language;
    private String host;
    private int port;
    private int memory;
    private int threads;

    public LaunchConfiguration() {
    }

    public LaunchConfiguration(String language, String host, int port, int memory, int threads) {
        this.language = language;
        this.host = host;
        this.port = port;
        this.memory = memory;
        this.threads = threads;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public String getLanguage() {
        return language;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getMemory() {
        return memory;
    }

    public int getThreads() {
        return threads;
    }
}
