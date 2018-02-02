package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import java.util.logging.Level;
import main.core.Executor;
import main.util.MessageHandler;
import main.util.Messages;

/**
 * Logic and helper functions used to handle the firing of a {@link ClientMovedEvent}.
 */
public class ClientMovedHandler {

   private final TS3Api api = Executor.getServer("testInstance").getApi();
   private ClientMovedEvent event;
   private String movedName;
   private String movedUid;
   private String invokerName;
   private String invokerUid;
   private String channelName;

   /**
    * Creates a new {@link ClientMovedHandler} with the provided {@link ClientMovedEvent} and
    * logging settings.
    *
    * @param event the {@code ClientMovedEvent} being acted upon.
    * @param consoleLogging whether or not this event should be logged to the console.
    * @param fileLogging whether or not this event should be logged to a file.
    */
   public ClientMovedHandler(ClientMovedEvent event, boolean consoleLogging, boolean fileLogging) {
      ClientInfo movedClient;
      movedClient = api.getClientInfo(event.getClientId());

      this.event = event;
      this.movedName = movedClient.getNickname();
      this.movedUid = movedClient.getUniqueIdentifier();
      this.invokerName = event.getInvokerName();
      this.invokerUid = event.getInvokerUniqueId();
      this.channelName = api.getChannelInfo(event.getTargetChannelId()).getName();

      if (consoleLogging) {
         getMessageHandler().sendToConsoleWith(Level.INFO);
      }

      if (fileLogging) {
         logToFile();
      }
   }

   private void logToFile() {
      // TODO Auto-generated method stub
   }

   private MessageHandler getMessageHandler() {
      if (invokerName.isEmpty()) {
         return new MessageHandler(String.format(Messages.USER_MOVED, movedName, movedUid,
             channelName));
      } else {
         return new MessageHandler(
             String.format(Messages.USER_WAS_MOVED, invokerName, invokerUid, movedName, movedUid,
                 channelName));
      }
   }
}
