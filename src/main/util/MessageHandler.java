package main.util;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.conf.Configuration;
import main.core.Executor;

/**
 * Contains utility methods to format and send messages to various outputs.
 */
public class MessageHandler {
   private static Level configDebugLevel = Configuration.getLoggingLevel();
   Level messageLevel;
   String prefix, message;

   /**
    * Log a message to the console if it's of a certain level.
    *
    * @param configDebugLevel The logging level being used by the config file.
    * @param messageLevel The logging level of the message.
    * @param message The message being displayed.
    */
   @Deprecated
   public MessageHandler(Level configDebugLevel, Level messageLevel, String message) {
      this.configDebugLevel = configDebugLevel;
      this.messageLevel = messageLevel;
      this.message = message;
      handleConsoleMessage();
   }

   /**
    * Log a message to the console if it's of a certain level.
    *
    * @param messageLevel The logging level of the message.
    * @param message The message being displayed.
    */
   public MessageHandler(Level messageLevel, String message) {
      this.messageLevel = messageLevel;
      this.message = message;
      System.out.println(Util.timeStamp() + "[" + messageLevel.getName() + "] " + message);
   }

   /**
    * Log a message to the console regardless of the config's logging level.
    *
    * @param prefix The prefix for the message.
    * @param message The message being displayed.
    */
   public MessageHandler(String prefix, String message) {
      this.prefix = prefix;
      this.message = message;
      handleConsoleMessage();
   }

   public MessageHandler(final MessageMode mode, final Level messageLevel, final String message) {
      this.messageLevel = messageLevel;
      this.message = message;

      if (mode == MessageMode.CONSOLE) {
         handleConsoleMessage();
      } else if (mode == MessageMode.SEND_TO_SERVER) {
         handleServerMessage();
      }
   }

   public MessageHandler(final MessageMode mode, String message, final TextMessageEvent event) {
      this.message = message;

      if (mode == MessageMode.RETURN_TO_SENDER) {
         handlePrivateMessage(event);
      }
   }

   private void handleConsoleMessage() {
      if (!configDebugLevel.getName().isEmpty() && messageLevel.intValue() >= configDebugLevel
          .intValue()) {
         System.out.println(Util.timeStamp() + "[" + messageLevel.getName() + "] " + message);
      } else if (configDebugLevel.getName().isEmpty() || configDebugLevel == null) {
         System.out.println(prefix + " " + message);
      }
   }

   private void handleServerMessage() {
      TS3Api api = Executor.getServer("testInstance").getApi();
      api.sendServerMessage(message);
   }

   private void handlePrivateMessage(final TextMessageEvent event) {
      TS3Api api = Executor.getServer("testInstance").getApi();
      api.sendPrivateMessage(event.getInvokerId(), message);
   }
}
