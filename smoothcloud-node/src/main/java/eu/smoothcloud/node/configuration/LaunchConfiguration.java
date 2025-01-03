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

package eu.smoothcloud.node.configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Setter
@Getter
public class LaunchConfiguration implements TomlSerializable {
    private boolean eula;
    private String language;
    private String host;
    private int port;
    private int memory;
    private int threads;

    public LaunchConfiguration() {
    }

    public LaunchConfiguration(boolean eula, String language, String host, int port, int memory, int threads, ArrayList<String> test) {
        this.eula = eula;
        this.language = language;
        this.host = host;
        this.port = port;
        this.memory = memory;
        this.threads = threads;
    }
}
