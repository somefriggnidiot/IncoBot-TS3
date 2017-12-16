package main.server;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import java.util.logging.Level;

public class ServerConfigBuilder {
   private TS3Config config;
   private String host;
   private Level debugLevel;
   private FloodRate floodRate;
   private ReconnectStrategy reconnectStrategy;
   private ConnectionHandler connectionHandler;
   private int queryPort;

   public ServerConfigBuilder() {
      config = new TS3Config();
   }

   public TS3Config build() {
      return this.config;
   }

   public ServerConfigBuilder withHost(String host) {
      this.host = host;
      this.config.setHost(host);
      return this;
   }

   public ServerConfigBuilder withDebugLevel(Level debugLevel) {
      this.debugLevel = debugLevel;
      this.config.setDebugLevel(debugLevel);
      return this;
   }

   public ServerConfigBuilder withFloodRate(FloodRate floodRate) {
      this.floodRate = floodRate;
      this.config.setFloodRate(floodRate);
      return this;
   }

   public ServerConfigBuilder withReconnectStrategy(ReconnectStrategy reconnectStrategy) {
      this.reconnectStrategy = reconnectStrategy;
      this.config.setReconnectStrategy(reconnectStrategy);
      return this;
   }

   public ServerConfigBuilder withConnectionHandler(ConnectionHandler connectionHandler) {
      this.connectionHandler = connectionHandler;
      this.config.setConnectionHandler(connectionHandler);
      return this;
   }

   public ServerConfigBuilder withQueryPort(int queryPort) {
      this.queryPort = queryPort;
      this.config.setQueryPort(queryPort);
      return this;
   }

   public String getHost() {
      return host;
   }

   public Level getDebugLevel() {
      return debugLevel;
   }

   public FloodRate getFloodRate() {
      return floodRate;
   }

   public ReconnectStrategy getReconnectStrategy() {
      return reconnectStrategy;
   }

   public ConnectionHandler getConnectionHandler() {
      return connectionHandler;
   }

   public int getQueryPort() {
      return queryPort;
   }

}
