package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import java.util.logging.Level;
import main.core.Executor;
import main.core.commands.Commands;
import main.util.MessageHandler;
import main.util.MessageMode;
import main.util.Util;
import main.server.listeners.TextMessageListener;
import main.util.exception.CommandNotFoundException;

/**
 * Logic and helper functions used to handle 
 * the firing of a {@link TextMessageListener}
 */
public class TextMessageHandler {
      TextMessageEvent event;
      String message;
      private final Integer botId = Executor.getServer("testInstance").getBotId();
      
      public TextMessageHandler(TextMessageEvent event, boolean consoleLogging, boolean fileLogging) {
         this.event = event;
         this.message = event.getMessage();

         if(message.startsWith(Commands.getPrefix())) {
            try {
               Commands.handle(event.getMessage());
            } catch (Exception e) {
               new MessageHandler(Level.INFO, e.getMessage());
               new MessageHandler(MessageMode.RETURN_TO_SENDER, e.getMessage(), event);
            }
         }

         if(consoleLogging) logToConsole();
         if(fileLogging) logToFile();
      }
      
      
      private void logToFile() {
         // TODO Auto-generated method stub
         return;
      }

      /**
       * Prints the message contents to console.
       * Messages are handled differently based on the {@code TextMessageTargetMode}.
       */
      private void logToConsole() {
         final int invokerId = event.getInvokerId();
         final TextMessageTargetMode target = event.getTargetMode();
         final String targetMode = target.toString().toLowerCase();
         final String invokerUid = event.getInvokerUniqueId();
         final String invokerName = event.getInvokerName();
         final String message = event.getMessage();
         
         String logMessage;
         logMessage = Util.timeStamp() + "[MESSAGE] ";
         
         if (target != TextMessageTargetMode.CLIENT && invokerId != botId) { //User sent server/channel message.
            logMessage += invokerName + " (" + invokerUid + ") to " + targetMode + ": " + message;
         } else if (target == TextMessageTargetMode.CLIENT && invokerId != botId) { //User sent private message to bot.
            logMessage += invokerName + " (" + invokerUid + ") to BOT: " + message;
         } else if (target != TextMessageTargetMode.CLIENT && invokerId == botId) { //Bot sent server/channel message.
            logMessage += "BOT to " + targetMode + ": " + message;
         } else { //Bot sent private message to user.
            final String targetUser = Executor.getServer("testInstance").getApi()
                  .getClientInfo(event.getInt("target")).getNickname();
            logMessage += "BOT to " + targetUser + ": " + message;
         }

//         new MessageHandler("[MESSAGE]", logMessage);
         System.out.println(logMessage);
      }
   }