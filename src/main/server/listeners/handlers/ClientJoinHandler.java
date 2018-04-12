package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import javax.persistence.EntityManager;
import main.core.Executor;
import main.data_access.DatabaseConnector;
import main.data_access.DatabaseConnector.Table;
import main.data_access.models.User;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;

/**
 * Logic and helper functions used to handle the firing of a {@link ClientJoinEvent}.
 */
public class ClientJoinHandler {

   private final TS3Api api = Executor.getServer("testInstance").getApi();
   private final ClientJoinEvent event;
   private final Client client;
   private final ClientInfo clientInfo;

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

      updateDatabase();
   }

   private void logToFile() {
      // TODO Auto-generated method stub
   }

   private void updateDatabase() {
      //Create connection
      DatabaseConnector connector = new DatabaseConnector();
      EntityManager em = connector.getEntityManager(Table.USER);

      //Check to see if user exists already
      User user = em.find(User.class, event.getClientDatabaseId());

      if (user != null) {
         //Update User
         em.getTransaction().begin();
         user.setLastSeen(api.getHostInfo().getTimeStamp());
         em.getTransaction().commit();
      } else {
         //Create User
         user = new User(api.getDatabaseClientInfo(event.getClientDatabaseId()));
         user.setUid(event.getUniqueClientIdentifier());
         user.setLastSeen(api.getHostInfo().getTimeStamp());

         em.getTransaction().begin();
         em.persist(user);
         em.getTransaction().commit();
      }

      connector.close();
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
