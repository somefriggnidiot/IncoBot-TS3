package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.util.MessageHandler;
import main.util.MessageMode;

/**
 * Command used to do a basic health-check of the application.
 */
public class PingCommand {
   TextMessageEvent event;
   private final String returnText = "Pong!";

   /**
    * Create a PingCommand instance to handle console executions.
    */
   public PingCommand() {
      this.handle();
   }

   /**
    * Create a PingCommand instance to handle client executions.
    *
    * @param event the {@link TextMessageEvent} that triggered the PingCommand.
    */
   public PingCommand(TextMessageEvent event) {
      this.event = event;
      this.handle();
   }

   private void handle() {
      if (event == null) {
         new MessageHandler(returnText).sendToConsoleWith("COMMAND RESPONSE");
         return;
      }

      TextMessageTargetMode mode = event.getTargetMode();
      if (mode == TextMessageTargetMode.SERVER) {
         new MessageHandler(returnText).sendToConsoleWith("COMMAND RESPONSE").sendToServer();
      } else if (mode == TextMessageTargetMode.CHANNEL) {
         new MessageHandler(returnText).sendToConsoleWith("COMMAND RESPONSE").sendToChannel();
      } else if (mode == TextMessageTargetMode.CLIENT) {
         new MessageHandler(returnText).sendToConsoleWith("COMMAND RESPONSE").returnToSender(event);
      }

   }
}
