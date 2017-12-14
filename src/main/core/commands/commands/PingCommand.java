package main.core.commands.commands;

import java.util.logging.Level;
import main.util.MessageHandler;
import main.util.MessageMode;

public class PingCommand {

   public PingCommand() {
      handle();
   }

   private static void handle() {
      new MessageHandler(Level.INFO, "Pong!");
      new MessageHandler(MessageMode.SEND_TO_SERVER, Level.INFO, "Pong!");
   }
}
