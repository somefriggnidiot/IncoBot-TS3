package main.core.commands;

import static com.google.common.base.Preconditions.checkArgument;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.util.ErrorMessages;
import main.core.commands.commands.PingCommand;
import main.util.exception.CommandNotFoundException;
import org.apache.commons.lang3.StringUtils;

/**
 * Core command handler class. In charge of routing commands to their correct handlers.
 */
public class Commands {
   private static String prefix = "!";

   /**
    * Route message contents from console to the appropriate command if applicable.
    *
    * @param input the contents of the message being checked.
    * @throws CommandNotFoundException if the command being referenced is not known.
    */
   public static void handle(String input) throws CommandNotFoundException {
      validate(input);

      final String[] command = input.split("\\s", 2);
      final String action = command[0].substring(1);

      switch (action.toLowerCase()) {
         case "ping":
            new PingCommand();
            return;
         case "forcequit":
            return;
         default:
            throw (new CommandNotFoundException(command[0]));
      }
   }

   /**
    * Route message contents from the client to the appropriate command if applicable.
    *
    * @param event the contents of the message being checked.
    * @throws CommandNotFoundException if the command being referenced is not known.
    */
   public static void handle(TextMessageEvent event) throws CommandNotFoundException {
      validate(event.getMessage());
      String input = event.getMessage();

      final String[] command = input.split("\\s", 2);
      final String action = command[0].substring(1);

      switch (action.toLowerCase()) {
         case "ping":
            new PingCommand(event);
            return;
         default:
            throw (new CommandNotFoundException(command[0]));
      }
   }

   /** Returns the prefix used to denote commands. */
   public static String getPrefix() {
      return prefix;
   }

   private static void validate(final String input) {
      checkArgument(!StringUtils.isBlank(input), ErrorMessages.INPUT_BLANK);
      checkArgument(input.startsWith(prefix),
          String.format(ErrorMessages.COMMAND_PREFIX_NOT_RECOGNIZED, input.substring(0, 1)));
   }
}
