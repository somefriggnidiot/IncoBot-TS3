package main.util;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.conf.Configuration;
import main.core.Executor;

/**
 * Contains utility methods to format and send messages to various outputs.
 */
public class MessageHandler {
   private static Level configDebugLevel = Configuration.getLoggingLevel();
   private String message;

   public MessageHandler(String message) {
      this.message = message;
   }

   public MessageHandler sendToConsoleWith(String prefix) {
      System.out.println(String.format("[%s] [%s] %s", Util.timeStamp(), prefix, message));
      return this;
   }

   public MessageHandler sendToConsoleWith(Level loggingLevel) {
      System.out.println(String.format("[%s] [%s] %s", Util.timeStamp(), loggingLevel.getName(),
          message));
      return this;
   }

   public MessageHandler sendToServer() {
      TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
      api.sendServerMessage(message);
      return this;
   }

   public MessageHandler sendToChannel() {
      TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
      api.sendChannelMessage(message);
      return this;
   }

   public MessageHandler returnToSender(TextMessageEvent event) {
      TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
      api.sendPrivateMessage(event.getInvokerId(), message);
      return this;
   }

   public MessageHandler sendToUser(int clientId) {
      TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
      api.sendPrivateMessage(clientId, message);
      return this;
   }

//   private void handleConsoleMessage() {
//      if (!configDebugLevel.getName().isEmpty() && messageLevel.intValue() >= configDebugLevel
//          .intValue()) {
//         System.out.println(Util.timeStamp() + "[" + messageLevel.getName() + "] " + message);
//      } else if (configDebugLevel.getName().isEmpty() || configDebugLevel == null) {
//         System.out.println(prefix + " " + message);
//      }
//   }
//
//   private void handleServerMessage() {
//      TS3Api api = Executor.getServer("testInstance").getApi();
//      api.sendServerMessage(message);
//   }
//
//   private void handlePrivateMessage(final TextMessageEvent event) {
//      TS3Api api = Executor.getServer("testInstance").getApi();
//      api.sendPrivateMessage(event.getInvokerId(), message);
//   }
}
