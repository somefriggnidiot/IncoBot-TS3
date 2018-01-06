package main.server;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import java.io.File;
import java.util.logging.Level;
import main.conf.ConfigHandler;
import main.conf.ConnectionConfiguration;
import main.server.listeners.ClientConnectListener;
import main.server.listeners.TextMessageListener;
import main.util.MessageHandler;
import main.util.Util;

/**
 * {@code ServerConnectionManagers} controls basic server connection configuration and actions.
 */
public class ServerConnectionManager {

   ConnectionConfiguration connectionConfig = ConfigHandler.readConnectionConfig(
       new File("./config/ConnectionConfig.yaml"));
   private TS3Query serverQuery;
   private TS3Config config;
   private Level serverDebugLevel;
   private TS3Api api;
   private TS3ApiAsync apiAsync;
   private Integer botClientId;

   /**
    * Creates a basic SCM with default parameters.
    */
   public ServerConnectionManager() {
      config = new ServerConfigBuilder()
          .withHost(connectionConfig.getServerAddress())
          .withQueryPort(connectionConfig.getServerQueryPort())
          .withDebugLevel(Level.OFF)
          .withFloodRate(FloodRate.UNLIMITED)
          .withReconnectStrategy(ReconnectStrategy.exponentialBackoff())
          .withConnectionHandler(new ConnectionHandler() {
             public void onConnect(TS3Query ts3Query) {
                TS3Api api = ts3Query.getApi();
                api.login(connectionConfig.getServerQueryName(),
                    connectionConfig.getServerQueryPassword());
                api.selectVirtualServerById(connectionConfig.getVirtualServerId());
                api.setNickname(connectionConfig.getBotNickname());
                api.registerAllEvents();
                botClientId = api.whoAmI().getId();
             }

             public void onDisconnect(TS3Query ts3Query) {
             }
          })
          .build();

      serverDebugLevel = Level.INFO;
   }

   /**
    * Initiates the connection to the server.
    */
   public void connect() {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new MessageHandler("Connection Initiated").sendToConsoleWith(Level.CONFIG);
            serverQuery = new TS3Query(config);
            serverQuery.connect();

            apiAsync = serverQuery.getAsyncApi();
            api = serverQuery.getApi();

            addListeners(serverQuery.getApi());
            api.addTS3Listeners(new TextMessageListener());
            api.addTS3Listeners(new ClientConnectListener());

            //TODO: Remove; added for testing.
            new MessageHandler("Blah!").sendToServer();
            for (Client client : api.getClients()) {
               new MessageHandler("I'm alive!").sendToUser(client.getId());
            }

            new MessageHandler("Connected!").sendToConsoleWith(Level.INFO);
         }
      });
   }

   /**
    * Disconnects the query from the server.
    */
   public void disconnect() {
      serverQuery.exit();
   }

   /**
    * Creates a {@link TS3Config} prepared to connect to the given host.
    *
    * @param host the host to which this SCM will attempt a connection.
    * @param debugLevel the logging level to be used by the server.
    * @param floodRate the {@code FloodRate} to be used by the query when connected to the server.
    * @return a ready-to-connect {@code TS3Config}.
    */
   public TS3Config createServerConfig(String host, Level debugLevel, FloodRate floodRate) {
      serverDebugLevel = debugLevel;

      return new ServerConfigBuilder()
          .withHost(host)
          .withDebugLevel(debugLevel)
          .withFloodRate(floodRate)
          .build();
   }

   /**
    * @return this {@code ServerConnectionManager}'s {@link TS3Config}.
    */
   public TS3Config getConfig() {
      return config;
   }

   /**
    * @return the logging level used by this {@code ServerConnectionManager}.
    */
   public Level getDebugLevel() {
      return serverDebugLevel;
   }

   /**
    * @return this {@code ServerConnectionManager}'s {@link TS3Api}.
    */
   public TS3Api getApi() {
      return api;
   }

   /**
    * @return this {@code ServerConnectionManager}'s {@link TS3ApiAsync}.
    */
   public TS3ApiAsync getApiAsync() {
      return apiAsync;
   }

   /**
    * @return this {@code ServerConnectionManager}'s connection Id.
    */
   public Integer getBotId() {
      return botClientId;
   }

   /**
    * Adds listeners and their handlers to the provided {@link TS3Api}.
    *
    * @param api the {@code TS3Appi} to be receiving the listeners.
    */
   @Deprecated
   private void addListeners(final TS3Api api) {
      api.addTS3Listeners(new TS3EventAdapter() {
         @Override
         public void onTextMessage(TextMessageEvent messageEvent) {
            new MessageHandler(String.format("[MESSAGE - SERVER] %s -- %s", messageEvent
                .getInvokerName(), messageEvent.getMessage()));
         }
      });
   }

}
