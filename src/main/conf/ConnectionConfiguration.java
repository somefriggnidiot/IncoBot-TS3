package main.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

public final class ConnectionConfiguration {

   @JsonProperty("instance-name")
   private String instanceName;
   @JsonProperty("server-address")
   private String serverAddress;
   @JsonProperty("server-query-port")
   private int serverQueryPort;
   @JsonProperty("virtual-server-id")
   private int virtualServerId;
   @JsonProperty("server-query-name")
   private String serverQueryName;
   @JsonProperty("server-query-password")
   private String serverQueryPassword;
   @JsonProperty("bot-nickname")
   private String botNickname;
   @JsonProperty("bot-slow-mode")
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

   public int getServerQueryPort() {
      return serverQueryPort;
   }

   public void setServerQueryPort(int serverQueryPort) {
      this.serverQueryPort = serverQueryPort;
   }

   public int getVirtualServerId() {
      return virtualServerId;
   }

   public void setVirtualServerId(int virtualServerId) {
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
