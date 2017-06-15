package main.server;

import java.util.logging.Level;

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

import main.core.MessageHandler;
import main.core.Util;
import main.server.ServerConfigBuilder;
import main.server.listeners.ClientConnectListener;
import main.server.listeners.TextMessageListener;

public class ServerConnectionManager {
   TS3Query serverQuery;
   TS3Config config;
   Level serverDebugLevel;
   TS3Api api;
   TS3ApiAsync apiAsync;
   Integer botClientId;

   public ServerConnectionManager() {
      config = new ServerConfigBuilder()
            .withHost("localhost")
            .withQueryPort(10011)
            .withDebugLevel(Level.INFO)
            .withFloodRate(FloodRate.UNLIMITED)
            .withReconnectStrategy(ReconnectStrategy.exponentialBackoff())
            .withConnectionHandler(new ConnectionHandler() {
               public void onConnect(TS3Query ts3Query) {
                  TS3Api api = ts3Query.getApi();
                  api.login("serveradmin", "MlLkGLQF");
                  api.selectVirtualServerById(1);
                  api.setNickname("Bot Johnson");
                  api.registerAllEvents();
                  botClientId = api.whoAmI().getId();
               }
   
               public void onDisconnect(TS3Query ts3Query) {
                  return;
               }
            })
            .build();
      
      serverDebugLevel = Level.INFO;
   }

   public void connect() {
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            new MessageHandler(serverDebugLevel, Level.CONFIG, "Connection initiated.");
            serverQuery = new TS3Query(config);
            serverQuery.connect();
            
            apiAsync = serverQuery.getAsyncApi();
            api = serverQuery.getApi();
            
            addListeners(serverQuery.getApi());
            api.addTS3Listeners(new TextMessageListener());
            api.addTS3Listeners(new ClientConnectListener());
            
            api.sendServerMessage("Blah");
            
            for (Client client : api.getClients()) {
               api.sendPrivateMessage(client.getId(), "asdfa");
            }

            new MessageHandler(serverDebugLevel, Level.INFO, "Connected!");
         }
      });
   }
   
   public void disconnect() {
      serverQuery.exit();
   }

   public TS3Config createServerConfig(String host, Level debugLevel, FloodRate floodRate) {
      serverDebugLevel = debugLevel;
      
      return new ServerConfigBuilder()
            .withHost(host)
            .withDebugLevel(debugLevel)
            .withFloodRate(floodRate)
            .build();
   }

   public TS3Config getConfig() {
      return config;
   }
   
   public Level getDebugLevel() {
      return serverDebugLevel;
   }
   
   public TS3Api getApi() {
      return api;
   }
   
   public Integer getBotId() {
      return botClientId;
   }
   
   private void addListeners(final TS3Api api) {
      api.addTS3Listeners(new TS3EventAdapter() {
         @Override
         public void onTextMessage(TextMessageEvent messageEvent) {
            new MessageHandler(serverDebugLevel, Level.ALL, Util.timeStamp() + "[MESSAGE - SERVER] "
                  + messageEvent.getInvokerName() + " -- " + messageEvent.getMessage());
         }
      });
   }

}
