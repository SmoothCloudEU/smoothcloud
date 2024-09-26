package eu.smoothcloud.node.network.type;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class VarString implements MinecraftData {
    public String s;

    public VarString() {}

    public VarString(String s) {
        this.s = s;
    }

    @Override
    public byte[] toBytes() {
        return ArrayUtils.addAll(new VarInt(s.length()).toBytes(), s.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void fromStream(InputStream in) throws IOException {
        VarInt length = new VarInt();
        length.fromStream(in);
        System.out.println("length = " + length);
        byte[] tmp = new byte[length.num];
        in.read(tmp);
        s = new String(tmp, StandardCharsets.UTF_8);
    }

    @Override
    public String toString() {
        return "VarString{" +
                "s='" + s + '\'' +
                '}';
    }
}
