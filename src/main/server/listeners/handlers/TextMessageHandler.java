package main.server.listeners.handlers;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import main.core.Executor;
import main.core.Util;

public class TextMessageHandler {
      TextMessageEvent event;
      private final Integer botId = Executor.getServer("testInstance").getBotId();
      
      public TextMessageHandler(TextMessageEvent event, boolean consoleLogging, boolean fileLogging) {
         this.event = event;
         
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
         } else if (target == TextMessageTargetMode.CLIENT && invokerId == botId) { //Bot sent private message to user.
            final String targetUser = Executor.getServer("testInstance").getApi()
                  .getClientInfo(event.getInt("target")).getNickname();
            logMessage += "BOT to " + targetUser + ": " + message;
         }
         
         System.out.println(logMessage);
      }
   }