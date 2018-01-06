package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.core.Executor;
import main.core.commands.Commands;
import main.server.listeners.TextMessageListener;
import main.util.LogPrefix;
import main.util.MessageHandler;

/**
 * Logic and helper functions used to handle the firing of a {@link TextMessageListener}
 */
public class TextMessageHandler {
   private final Integer botId = Executor.getServer("testInstance").getBotId();
   private TextMessageEvent event;
   private String message;

   public TextMessageHandler(TextMessageEvent event, boolean consoleLogging, boolean fileLogging) {
      this.event = event;
      this.message = event.getMessage();

      if (message.startsWith(Commands.getPrefix())) {
         try {
            Commands.handle(event);
         } catch (Exception e) {
            new MessageHandler(e.getMessage())
                .sendToConsoleWith(Level.INFO)
                .returnToSender(event);
         }
      }

      if (consoleLogging) {
         logToConsole();
      }
      if (fileLogging) {
         logToFile();
      }
   }


   private void logToFile() {
      // TODO Auto-generated method stub
   }

   /**
    * Prints the message contents to console. Messages are handled differently based on the {@code
    * TextMessageTargetMode}.
    */
   private void logToConsole() {
      final int invokerId = event.getInvokerId();
      final TextMessageTargetMode target = event.getTargetMode();
      final String invokerUid = event.getInvokerUniqueId();
      final String invokerName = event.getInvokerName();

      if (target != TextMessageTargetMode.CLIENT && invokerId != botId) {
         //User sent server/channel message.
         new MessageHandler(String.format("%s (%s) to %s: %s", invokerName, invokerUid, target,
             message))
             .sendToConsoleWith(LogPrefix.MESSAGE);
      } else if (target == TextMessageTargetMode.CLIENT && invokerId != botId) {
         //User sent private message to bot.
         new MessageHandler(String.format("%s to BOT: %s", invokerName, message))
             .sendToConsoleWith(LogPrefix.MESSAGE);
      } else if (target != TextMessageTargetMode.CLIENT) {
         //Bot sent server/channel message.
         new MessageHandler(String.format("BOT to SERVER: %s", message)).sendToConsoleWith
             (LogPrefix.MESSAGE);
      } else { //Bot sent private message to user.
         final String targetUser = Executor.getServer("testInstance").getApi()
             .getClientInfo(event.getInt("target")).getNickname();
         new MessageHandler(String.format("BOT to %s: %s", targetUser, message))
             .sendToConsoleWith(LogPrefix.MESSAGE);
      }
   }
}