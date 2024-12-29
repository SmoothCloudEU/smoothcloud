package eu.smoothcloud.launcher;

import java.net.URL;
import java.net.URLClassLoader;

public class SmoothCloudClassLoader extends URLClassLoader {

    public SmoothCloudClassLoader() {
        super(new URL[0], getSystemClassLoader());
    }

    @Override
    public void addURL(URL url) {
        super.addURL(url);
    }

    static {
        registerAsParallelCapable();
    }
}
