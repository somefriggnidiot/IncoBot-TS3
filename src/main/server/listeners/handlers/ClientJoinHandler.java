package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import main.core.Config;
import main.core.Executor;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;
import main.util.Util;

/**
 * Logic and helper functions used to handle the firing of a {@link ClientJoinEvent}.
 */
public class ClientJoinHandler {

   private final TS3Api api = Executor.getServer("testInstance").getApi();
   private ClientJoinEvent event;
   private Client client;
   private ClientInfo clientInfo;
   private List<ServerGroup> serverGroups;

   /**
    * Creates a new {@link ClientJoinHandler} with the provided {@link ClientJoinEvent} and logging
    * settings.
    *
    * @param event the {@code ClientJoinEvent} being acted upon.
    * @param consoleLogging whether or not this event should be logged to the console.
    * @param fileLogging whether or not this event should be logged to a file.
    */
   public ClientJoinHandler(ClientJoinEvent event, boolean consoleLogging, boolean fileLogging) {
      this.event = event;
      this.client = api.getClientByUId(event.getUniqueClientIdentifier());
      this.clientInfo = api.getClientInfo(client.getId());
      this.serverGroups = api.getServerGroupsByClient(client);

      if (event.getUniqueClientIdentifier().contains("Query")) {
         Executor.getServer("testInstance")
             .addConnectedClient(client.getId(), new ClientInfo(-1, null));
         return;
      } else {
         Executor.getServer("testInstance").addConnectedClient(event.getClientId(), clientInfo);
      }

      if (consoleLogging) {
         logToConsole();
      }
      if (fileLogging) {
         logToFile();
      }

      checkMembership();
   }

   private void logToFile() {
      // TODO Auto-generated method stub
      return;
   }

   /**
    * Checks a user's membership status against the forums and assigns ServerGroups accordingly.
    */
   private void checkMembership() {
      String userGroup = "";
      String clientUid = event.getInvokerUniqueId();
      try {
         String webUid = URLEncoder.encode(clientUid, "UTF-8");
         String urlWithId = "http://www.foundinaction.com/ts3botuidchecker.php?uid=" + webUid;
         URL url = new URL(urlWithId);
         URLConnection conn = url.openConnection();
         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

         String inputLine;

         while ((inputLine = br.readLine()) != null) {
            userGroup = inputLine;
         }
         br.close();

      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   /**
    * Prints a formatted summary of the event to the console.
    */
   private void logToConsole() {
      final String channelName = api.getChannelInfo(event.getClientTargetId()).getName();

      new MessageHandler(String.format(Messages.USER_CONNECTED, event.getClientNickname(),
          event.getUniqueClientIdentifier(), channelName)).sendToConsoleWith(LogPrefix.CONNECTION);
   }
}