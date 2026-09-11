package net.minecraftforge.network;

public enum NetworkDirection {
    PLAY_TO_SERVER(false, true),
    PLAY_TO_CLIENT(true, false);

    private final boolean targetClient;
    private final boolean targetServer;

    NetworkDirection(boolean targetClient, boolean targetServer) {
        this.targetClient = targetClient;
        this.targetServer = targetServer;
    }

    public boolean targetClient() {
        return targetClient;
    }

    public boolean targetServer() {
        return targetServer;
    }

    public NetworkDirection reply() {
        return this == PLAY_TO_SERVER ? PLAY_TO_CLIENT : PLAY_TO_SERVER;
    }
}