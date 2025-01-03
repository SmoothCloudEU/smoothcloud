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

package eu.smoothcloud.util.thread;

import java.util.concurrent.Future;

public class ThreadBound<T> {

    private final String taskName;
    private final ThreadManager threadManager;
    private final T instance;

    public ThreadBound(ThreadManager manager, String taskName, T instance) {
        this.taskName = taskName;
        this.threadManager = manager;
        this.instance = instance;
    }

    public <R> Future<R> callOnThread(CallableInstance<T, R> callable) {
        return threadManager.submitTask(taskName, () -> callable.call(instance));
    }

    public void runOnThread(ConsumerInstance<T> consumer) {
        threadManager.startTask(taskName, () -> consumer.accept(instance));
    }

    public T getInstance() {
        return instance;
    }

    @FunctionalInterface
    public interface CallableInstance<T, R> {
        R call(T instance);
    }

    @FunctionalInterface
    public interface ConsumerInstance<T> {
        void accept(T instance);
    }

}
