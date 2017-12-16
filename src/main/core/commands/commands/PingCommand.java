package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.util.MessageHandler;
import main.util.MessageMode;

/**
 * Command used to do a basic health-check of the application.
 */
public class PingCommand {
   TextMessageEvent event;

   /**
    * Create a PingCommand instance to handle console executions.
    */
   public PingCommand() {
      handle();
   }

   /**
    * Create a PingCommand instance to handle client executions.
    *
    * @param event the {@link TextMessageEvent} that triggered the PingCommand.
    */
   //FIXME - Has not been completed.
   public PingCommand(TextMessageEvent event) {
      this.event = event;
   }

   private static void handle() {
      new MessageHandler(Level.INFO, "Pong!");
      new MessageHandler(MessageMode.SEND_TO_SERVER, Level.INFO, "Pong!");
   }
}
