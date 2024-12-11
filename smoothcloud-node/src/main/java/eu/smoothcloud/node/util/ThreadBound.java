package eu.smoothcloud.node.util;

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
