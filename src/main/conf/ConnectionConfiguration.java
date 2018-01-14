package main.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for the connection-related configuration.
 */
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

   /**
    * Returns the instance name of the connection.
    */
   @JsonProperty("instance-name")
   public String getInstanceName() {
      return instanceName;
   }

   /**
    * Sets the instance name of the connection.
    */
   @JsonProperty("instance-name")
   public void setInstanceName(String instanceName) {
      this.instanceName = instanceName;
   }

   /**
    * Returns the server address to which connection will be established.
    */
   @JsonProperty("server-address")
   public String getServerAddress() {
      return serverAddress;
   }

   /**
    * Sets the server address to which connection will be established.
    */
   @JsonProperty("server-address")
   public void setServerAddress(String serverAddress) {
      this.serverAddress = serverAddress;
   }

   /**
    * Returns the server query port to which connection will be established.
    */
   @JsonProperty("server-query-port")
   public int getServerQueryPort() {
      return serverQueryPort;
   }

   /**
    * Sets the server query port to which connection will be established.
    */
   @JsonProperty("server-query-port")
   public void setServerQueryPort(int serverQueryPort) {
      this.serverQueryPort = serverQueryPort;
   }

   /**
    * Returns the virtual server id to which connection will be established.
    */
   @JsonProperty("virtual-server-id")
   public int getVirtualServerId() {
      return virtualServerId;
   }

   /**
    * Sets the virtual server id to which connection will be established.
    */
   @JsonProperty("virtual-server-id")
   public void setVirtualServerId(int virtualServerId) {
      this.virtualServerId = virtualServerId;
   }

   /**
    * Returns the server query name to be used for authentication.
    */
   @JsonProperty("server-query-name")
   public String getServerQueryName() {
      return serverQueryName;
   }

   /**
    * Sets the server query name to be used for authentication.
    */
   @JsonProperty("server-query-name")
   public void setServerQueryName(String serverQueryName) {
      this.serverQueryName = serverQueryName;
   }

   /**
    * Returns the server query password to be used for authentication.
    */
   @JsonProperty("server-query-password")
   public String getServerQueryPassword() {
      return serverQueryPassword;
   }

   /**
    * Sets the server query name to be used for authentication.
    */
   @JsonProperty("server-query-password")
   public void setServerQueryPassword(String serverQueryPassword) {
      this.serverQueryPassword = serverQueryPassword;
   }

   /**
    * Returns the name to be displayed.
    */
   @JsonProperty("bot-nickname")
   public String getBotNickname() {
      return botNickname;
   }

   /**
    * Sets the name to be displayed.
    */
   @JsonProperty("bot-nickname")
   public void setBotNickname(String botNickname) {
      this.botNickname = botNickname;
   }

   /**
    * Returns whether or not the bot is operating in slow mode.
    */
   @JsonProperty("bot-slow-mode")
   public String getBotSlowMode() {
      return botSlowMode;
   }

   /**
    * Sets whether or not the bot is operating in slow mode.
    */
   @JsonProperty("bot-slow-mode")
   public void setBotSlowMode(String botSlowMode) {
      this.botSlowMode = botSlowMode;
   }
}
