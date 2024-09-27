package eu.smoothcloud.node.network.entity;

public class NetworkPinger {

    private String address;
    private int protocolVersion;
    private int state;

    public NetworkPinger(String address, int protocolVersion, int state) {
        this.address = address;
        this.protocolVersion = protocolVersion;
        this.state = state;
    }

    public boolean passedCheck() {
        return protocolVersion >= 759;
    }

    public String getAddress() {
        return address;
    }

    public int getProtocolVersion() {
        return protocolVersion;
    }

    public int getState() {
        return state;
    }
}
