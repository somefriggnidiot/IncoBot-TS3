package main.core.functions;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import java.util.logging.Level;
import main.server.listeners.handlers.TextMessageHandler;
import main.util.MessageHandler;
import main.util.Messages;

public class DadModeMessageChecker {

   private static Boolean isActive = false;

   /**
    * Enables the functionality of the DadMode command. While enabled, all messages sent to the
    * server or the channel in which the bot resides that start with some variation of "I am [X]"
    * will be reconstructed into the terrible dad joke, "Hi [X], I'm dad!"
    *
    * @return a String indicating the success or failure of enabling this functionality.
    */
   public static String start() {
      if (!isActive) {
         TextMessageHandler.setDadModeIsActive(true);
         if (TextMessageHandler.getDadModeIsActive()) {
            isActive = true;
            return String.format("Dad mode on"); //Success
         } else {
            isActive = false;
            return String.format("Dad mode failed to turn on"); //Failure
         }
      } else {
         return String.format("Dad mode already on"); //already on
      }
   }

   /**
    * Disables the functionality of the DadMode command.
    *
    * @return a String indicating the success or failure of disabling this functionality.
    */
   public static String stop() {
      if (isActive) {
         TextMessageHandler.setDadModeIsActive(false);
         if (!TextMessageHandler.getDadModeIsActive()) {
            isActive = false;
            return String.format("Dad mode off"); //Success, off
         } else {
            isActive = true;
            return String.format("Dad mode failed to turn off"); //Failure, on
         }
      } else {
         return String.format("Dad mode already off"); //already off
      }
   }

   /**
    * Transforms an applicable message into its terrible dad-joke variant and, depending on the
    * medium in which the triggering message was received, sends it to the server or the channel of
    * the bot. (Or eats it, if the message was sent privately.)
    *
    * @param input the message triggering Dad Mode.
    * @param target the medium in which the message was received.
    */
   public static void sendDadMessage(String input, TextMessageTargetMode target) {
      String dadness = "";

      if (input.toLowerCase().startsWith("i'm")) {
         dadness = input.substring(4);
      } else if (input.toLowerCase().startsWith("im")) {
         dadness = input.substring(3);
      } else if (input.toLowerCase().startsWith("i am")) {
         dadness = input.substring(5);
      }

      if (dadness.isEmpty()) {
         new MessageHandler("DAD MODE TRIGGERED BUT NO DAD-ABLE MESSAGE RECEIVED")
             .sendToConsoleWith(Level.WARNING);
      }

      MessageHandler handler = new MessageHandler(String.format(Messages.DAD_RESPONSE, dadness));
      if (target == TextMessageTargetMode.SERVER) {
         handler.sendToServer();
      } else if (target == TextMessageTargetMode.CHANNEL) {
         handler.sendToChannel();
      }
   }
}
