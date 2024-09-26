package eu.smoothcloud.node.network.type;

import java.io.IOException;
import java.io.InputStream;

public interface MinecraftData {

    public byte[] toBytes();

    public void fromStream(InputStream in) throws IOException;
}
