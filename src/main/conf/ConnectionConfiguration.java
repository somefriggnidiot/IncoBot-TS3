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

   @JsonProperty("instance-name")
   public String getInstanceName() {
      return instanceName;
   }

   @JsonProperty("instance-name")
   public void setInstanceName(String instanceName) {
      this.instanceName = instanceName;
   }

   @JsonProperty("server-address")
   public String getServerAddress() {
      return serverAddress;
   }

   @JsonProperty("server-address")
   public void setServerAddress(String serverAddress) {
      this.serverAddress = serverAddress;
   }

   @JsonProperty("server-query-port")
   public int getServerQueryPort() {
      return serverQueryPort;
   }

   @JsonProperty("server-query-port")
   public void setServerQueryPort(int serverQueryPort) {
      this.serverQueryPort = serverQueryPort;
   }

   @JsonProperty("virtual-server-id")
   public int getVirtualServerId() {
      return virtualServerId;
   }

   @JsonProperty("virtual-server-id")
   public void setVirtualServerId(int virtualServerId) {
      this.virtualServerId = virtualServerId;
   }

   @JsonProperty("server-query-name")
   public String getServerQueryName() {
      return serverQueryName;
   }

   @JsonProperty("server-query-name")
   public void setServerQueryName(String serverQueryName) {
      this.serverQueryName = serverQueryName;
   }

   @JsonProperty("server-query-password")
   public String getServerQueryPassword() {
      return serverQueryPassword;
   }

   @JsonProperty("server-query-password")
   public void setServerQueryPassword(String serverQueryPassword) {
      this.serverQueryPassword = serverQueryPassword;
   }

   @JsonProperty("bot-nickname")
   public String getBotNickname() {
      return botNickname;
   }

   @JsonProperty("bot-nickname")
   public void setBotNickname(String botNickname) {
      this.botNickname = botNickname;
   }

   @JsonProperty("bot-slow-mode")
   public String getBotSlowMode() {
      return botSlowMode;
   }

   @JsonProperty("bot-slow-mode")
   public void setBotSlowMode(String botSlowMode) {
      this.botSlowMode = botSlowMode;
   }
}
