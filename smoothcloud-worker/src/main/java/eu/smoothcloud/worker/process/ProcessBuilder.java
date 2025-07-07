package eu.smoothcloud.worker.process;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessBuilder {
    private final List<String> command = new ArrayList<>();
    @Getter
    private File directory;
    @Getter
    private boolean inheritIO = false;

    public ProcessBuilder jar(String jarFile) {
        command.clear();
        command.add("java");
        command.add("-jar");
        command.add(jarFile);
        return this;
    }

    public ProcessBuilder withArgument(String arg) {
        command.add(arg);
        return this;
    }

    public ProcessBuilder withArguments(List<String> args) {
        command.addAll(args);
        return this;
    }

    public ProcessBuilder workingDirectory(File dir) {
        this.directory = dir;
        return this;
    }

    public ProcessBuilder inheritIO(boolean inherit) {
        this.inheritIO = inherit;
        return this;
    }

    public Process start() throws IOException {
        java.lang.ProcessBuilder pb = new java.lang.ProcessBuilder(command);
        if (directory != null) {
            pb.directory(directory);
        }
        if (inheritIO) {
            pb.inheritIO();
        }
        return pb.start();
    }

    public List<String> getCommand() {
        return new ArrayList<>(command);
    }
}
