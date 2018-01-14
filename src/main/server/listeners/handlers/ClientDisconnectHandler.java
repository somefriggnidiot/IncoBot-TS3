package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import java.util.logging.Level;
import main.core.Executor;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;
import main.util.exception.ClientNotFoundException;

public class ClientDisconnectHandler {

   private ClientLeaveEvent event;
   private ClientInfo clientInfo;

   public ClientDisconnectHandler(ClientLeaveEvent event, boolean consoleLogging, boolean
       fileLogging) {
      this.event = event;
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
