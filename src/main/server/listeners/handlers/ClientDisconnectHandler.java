package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import java.util.logging.Level;
import javax.persistence.EntityManager;
import main.core.Executor;
import main.data_access.DatabaseConnector;
import main.data_access.DatabaseConnector.Table;
import main.data_access.models.User;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;
import main.util.exception.ClientNotFoundException;

public class ClientDisconnectHandler {

   private ClientLeaveEvent event;
   private ClientInfo clientInfo;
   private TS3Api api;

   public ClientDisconnectHandler(ClientLeaveEvent event, boolean consoleLogging, boolean
       fileLogging) {
      this.event = event;
      this.api = Executor.getServer("testInstance").getApi();
      try {
         this.clientInfo = Executor.getServer("testInstance")
             .removeConnectedClient(event.getClientId());
      } catch (ClientNotFoundException e) {
         new MessageHandler(e.getMessage()).sendToConsoleWith(Level.WARNING);
      }

      if (clientInfo == null || clientInfo.getId() == -1 || clientInfo.getNickname().isEmpty()) {
         return;
      }

      logToConsole();
      updateDatabase();
   }

   private void logToFile() {
      //TODO Auto-generated method stub
   }

   private void logToConsole() {
      if (clientInfo == null) {
         return;
      }
      String message;
      String victimName = clientInfo.getNickname();
      String victimUid = clientInfo.getUniqueIdentifier();
      String invokerName = event.getInvokerName();
      String invokerUid = event.getInvokerUniqueId();
      String reason = event.getReasonMessage();

      if (event.getReasonId() == 5) {
         message = String
             .format(Messages.USER_KICKED, victimName, victimUid, invokerName, invokerUid, reason);
      } else if (event.getReasonId() == 3) {
         message = String.format(Messages.USER_LOST_CONNECTION, victimName, victimUid);
      } else if (event.getReasonId() == 6) {
         message = String
             .format(Messages.USER_BANNED, victimName, victimUid, invokerName, invokerUid,
                 getBanLengthFormatted(event.get("bantime")), reason);
      } else {
         message = String.format(Messages.USER_DISCONNECTED, victimName, victimUid, reason);
      }

      new MessageHandler(message).sendToConsoleWith(LogPrefix.DISCONNECTION);
   }

   private void updateDatabase() {
      //Create connection
      DatabaseConnector connector = new DatabaseConnector();
      EntityManager em = connector.getEntityManager(Table.USER);

      //Check to see if user exists already
      User user = em.find(User.class, clientInfo.getDatabaseId());

      if (user != null) {
         //Update User
         em.getTransaction().begin();
         user.setLastSeen(api.getHostInfo().getTimeStamp());
         em.getTransaction().commit();
      } else {
         //Create User
         user = new User(api.getDatabaseClientInfo(clientInfo.getDatabaseId()));
         user.setLastSeen(api.getHostInfo().getTimeStamp());

         em.getTransaction().begin();
         em.persist(user);
         em.getTransaction().commit();
      }

      connector.close();
   }

   private String getBanLengthFormatted(String banLength) {
      final Integer banLengthValue = Integer.parseInt(banLength);
      Integer banLengthDays = banLengthValue / 86400;
      Integer banLengthHours = (banLengthValue % 86400) / 3600;
      Integer banLengthMinutes = (banLengthValue % 3600) / 60;
      Integer banLengthSeconds = banLengthValue % 60;

      return String.format("%s Days %s Hours %s Minutes %s Seconds", banLengthDays.toString(),
          banLengthHours.toString(), banLengthMinutes.toString(), banLengthSeconds.toString());
   }
}
