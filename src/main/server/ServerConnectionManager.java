package main.server;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import main.conf.ConfigHandler;
import main.conf.ConnectionConfiguration;
import main.server.listeners.ClientConnectListener;
import main.server.listeners.ClientDisconnectListener;
import main.server.listeners.ClientMovedListener;
import main.server.listeners.TextMessageListener;
import main.util.MessageHandler;
import main.util.Messages;

/**
 * {@code ServerConnectionManagers} controls basic server connection configuration and actions.
 */
public class ServerConnectionManager {

   private final ConnectionConfiguration connectionConfig = ConfigHandler.readConnectionConfig(
       new File("./config/ConnectionConfig.yaml"));
   private final TS3Config config;
   private final HashMap<Integer, ClientInfo> connectedUserList = new HashMap<>();
   private TS3Query serverQuery;
   private Level serverDebugLevel;
   private TS3Api api;
   private TS3ApiAsync apiAsync;
   private Integer botClientId;
   private String botNickname;

   /**
    * Creates a basic SCM with default parameters.
    */
   public ServerConnectionManager() {
      config = new ServerConfigBuilder()
          .withHost(connectionConfig.getServerAddress())
          .withQueryPort(connectionConfig.getServerQueryPort())
          .withDebugLevel(Level.OFF)
          .withFloodRate(FloodRate.DEFAULT)
          .withReconnectStrategy(ReconnectStrategy.exponentialBackoff())
          .withConnectionHandler(new ConnectionHandler() {
             public void onConnect(TS3Query ts3Query) {
                TS3Api api = ts3Query.getApi();
                api.login(connectionConfig.getServerQueryName(),
                    connectionConfig.getServerQueryPassword());
                api.selectVirtualServerById(connectionConfig.getVirtualServerId());
                api.setNickname(connectionConfig.getBotNickname());
                botNickname = connectionConfig.getBotNickname();
                api.registerAllEvents();
                botClientId = api.whoAmI().getId();

                compileOnlineUserList(api);
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
            new MessageHandler("Connection Initiated").sendToConsoleWith(Level.INFO);
            serverQuery = new TS3Query(config);
            try {
               serverQuery.connect();
            } catch (Exception e) {
               new MessageHandler(String.format("Error attempting to connect to \"%s\", please "
                       + "ensure that the address provided is correct.",
                   connectionConfig.getServerAddress())).sendToConsoleWith(Level.SEVERE);
               System.exit(0);
            }

            apiAsync = serverQuery.getAsyncApi();
            api = serverQuery.getApi();

            api.addTS3Listeners(new TextMessageListener());
            api.addTS3Listeners(new ClientConnectListener());
            api.addTS3Listeners(new ClientDisconnectListener());
            api.addTS3Listeners(new ClientMovedListener());

            try {
               new MessageHandler(String.format(Messages.SUCCESSFULLY_CONNECTED, api.getServerInfo()
                   .getName(), api.whoAmI().getNickname())).sendToConsoleWith(Level.INFO);
            } catch (NullPointerException e) {
               new MessageHandler("An unknown error occurred when attempting to retrieve bot "
                   + "information. Please ensure you have the correct login information in the "
                   + "connection configuration.")
                   .sendToConsoleWith
                       (Level.SEVERE);
               System.exit(0);
            }
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
    * @return the name being used on the server that this {@code ServerConnectionManager}'s
    * connected to.
    */
   public String getBotNickname() {
      return botNickname;
   }

   public void addConnectedClient(int clientId, ClientInfo clientInfo) {
      if (!connectedUserList.containsKey(clientId) || clientId == -1) {
         connectedUserList.put(clientId, clientInfo);
      }
   }

   public ClientInfo removeConnectedClient(int clientId) {
      ClientInfo clientInfo = connectedUserList.remove(clientId);

      return clientInfo.getId() == -1 ? null : clientInfo;
   }

   public void printUserList() { //TODO Refactor to be viable, likely as part of clientinfo command.
      for (ClientInfo client : connectedUserList.values()) {
         System.out.println(
             client.getId() + " : " + client.getNickname() + " : " + client.getUniqueIdentifier());
      }
   }

   private void compileOnlineUserList(TS3Api api) {
      List<Client> clients = api.getClients();

      for (Client client : clients) {
         final int id = client.getId();
         addConnectedClient(id, api.getClientInfo(id));
      }
   }
}
