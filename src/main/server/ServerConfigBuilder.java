package main.server;

import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import java.util.logging.Level;

/**
 * Utility class for building a {@link TS3Config} instance.
 */
public class ServerConfigBuilder {

   private final TS3Config config;
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

   /**
    * Specifies a host value for the {@link TS3Config}. This value is used to connect to a physical
    * server.
    *
    * @param host the IP address or URL of the physical server
    * @return {@link ServerConfigBuilder} with this host value.
    */
   public ServerConfigBuilder withHost(String host) {
      this.host = host;
      this.config.setHost(host);
      return this;
   }

   /**
    * Specifies the server debug level to be used for the connection generated using this {@link
    * TS3Config}. This {@link Level} indicates to the query connection what level information should
    * be retrieved.
    *
    * @param debugLevel the {@link Level} of information to retrieve.
    * @return a {@link ServerConfigBuilder} with this debug level.
    */
   public ServerConfigBuilder withDebugLevel(Level debugLevel) {
      this.debugLevel = debugLevel;
      this.config.setDebugLevel(debugLevel);
      return this;
   }

   /**
    * Specifies the flood rate to be used for the connection generated using this {@link TS3Config}.
    * As either {@link FloodRate} or {@link int}, this determines the time in milliseconds that the
    * program will wait for between executing commands to the server.
    */
   public ServerConfigBuilder withFloodRate(FloodRate floodRate) {
      this.floodRate = floodRate;
      this.config.setFloodRate(floodRate);
      return this;
   }

   /**
    * Specifies the reconnect strategy to be used with this {@link TS3Config}. This strategy is used
    * to determine what steps will be taken when the connection to the host is lost unexpectedly.
    *
    * @param reconnectStrategy the {@link ReconnectStrategy} to be employed.
    * @return a {@link ServerConfigBuilder} with this reconnect strategy.
    */
   public ServerConfigBuilder withReconnectStrategy(ReconnectStrategy reconnectStrategy) {
      this.reconnectStrategy = reconnectStrategy;
      this.config.setReconnectStrategy(reconnectStrategy);
      return this;
   }

   /**
    * Specifies the connection handler to be used with this {@link TS3Config}. This handler
    * determines the steps to be taken (and commands to be executed) when a connection is
    * established with the host.
    *
    * @param connectionHandler the {@link ConnectionHandler} to be employed.
    * @return a {@link ServerConfigBuilder} with this connection handler.
    */
   public ServerConfigBuilder withConnectionHandler(ConnectionHandler connectionHandler) {
      this.connectionHandler = connectionHandler;
      this.config.setConnectionHandler(connectionHandler);
      return this;
   }

   /**
    * Specifies the query port for this {@link TS3Config}. This value is used in conjunction with
    * the host value to establish a socket.
    *
    * @param queryPort the value of the query port.
    * @return a {@link ServerConfigBuilder} with this query port.
    */
   public ServerConfigBuilder withQueryPort(int queryPort) {
      this.queryPort = queryPort;
      this.config.setQueryPort(queryPort);
      return this;
   }

   /**
    * Returns the host address in use by this query connection.
    */
   public String getHost() {
      return host;
   }

   /**
    * Returns the debug level in use by this query connection.
    */
   public Level getDebugLevel() {
      return debugLevel;
   }

   /**
    * Returns the maximum floodrate in use by this query connection.
    */
   public FloodRate getFloodRate() {
      return floodRate;
   }

   /**
    * Returns the reconnect strategy in use by this query connection.
    */
   public ReconnectStrategy getReconnectStrategy() {
      return reconnectStrategy;
   }

   /**
    * Returns the connection handler in use by this query connection.
    */
   public ConnectionHandler getConnectionHandler() {
      return connectionHandler;
   }

   /**
    * Returns the query port in user by this query connection.
    */
   public int getQueryPort() {
      return queryPort;
   }

}
