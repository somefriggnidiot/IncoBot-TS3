package main.conf;

public final class ConnectionConfiguration {
    private String instanceName;
    private String serverAddress;
    private String serverQueryPort;
    private String virtualServerId;
    private String serverQueryName;
    private String serverQueryPassword;
    private String botNickname;
    private String botSlowMode;

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getServerQueryPort() {
        return serverQueryPort;
    }

    public void setServerQueryPort(String serverQueryPort) {
        this.serverQueryPort = serverQueryPort;
    }

    public String getVirtualServerId() {
        return virtualServerId;
    }

    public void setVirtualServerId(String virtualServerId) {
        this.virtualServerId = virtualServerId;
    }

    public String getServerQueryName() {
        return serverQueryName;
    }

    public void setServerQueryName(String serverQueryName) {
        this.serverQueryName = serverQueryName;
    }

    public String getServerQueryPassword() {
        return serverQueryPassword;
    }

    public void setServerQueryPassword(String serverQueryPassword) {
        this.serverQueryPassword = serverQueryPassword;
    }

    public String getBotNickname() {
        return botNickname;
    }

    public void setBotNickname(String botNickname) {
        this.botNickname = botNickname;
    }

    public String getBotSlowMode() {
        return botSlowMode;
    }

    public void setBotSlowMode(String botSlowMode) {
        this.botSlowMode = botSlowMode;
    }
}
