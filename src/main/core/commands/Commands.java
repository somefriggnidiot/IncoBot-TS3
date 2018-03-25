package main.core.commands;

import static com.google.common.base.Preconditions.checkArgument;

import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.core.Executor;
import main.core.commands.commands.DadModeCommand;
import main.core.commands.commands.IdleCheckerCommand;
import main.core.commands.commands.KickCommand;
import main.core.commands.commands.PingCommand;
import main.util.Messages;
import main.util.exception.AuthorizationException;
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
         case "dadmode":
            new DadModeCommand(input);
            return;
         case "idlechecker":
            new IdleCheckerCommand(input);
            return;
         case "kick":
            new KickCommand(input);
            return;
         case "ping":
            new PingCommand();
            return;
         case "forcequit":
            Executor.getServer("testInstance").disconnect();
            return;
         case "debug::printusers":
            Executor.getServer("testInstance").printUserList();
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
    * @throws AuthorizationException if the invoking user does not have clearance to use the command
    * being referenced.
    */
   public static void handle(TextMessageEvent event)
       throws CommandNotFoundException, AuthorizationException {
      validate(event.getMessage());
      String input = event.getMessage();

      final String[] command = input.split("\\s", 2);
      final String action = command[0].substring(1);

      switch (action.toLowerCase()) {
         case "dadmode":
            new DadModeCommand(event);
            return;
         case "idlechecker":
            new IdleCheckerCommand(event);
            return;
         case "kick":
            new KickCommand(event);
            return;
         case "ping":
            new PingCommand(event);
            return;
         default:
            throw (new CommandNotFoundException(command[0]));
      }
   }

   /**
    * Returns the prefix used to denote commands.
    */
   public static String getPrefix() {
      return prefix;
   }

   private static void validate(final String input) {
      checkArgument(!StringUtils.isBlank(input), Messages.ERROR_INPUT_BLANK);
      checkArgument(input.startsWith(prefix),
          String.format(Messages.ERROR_COMMAND_PREFIX_NOT_RECOGNIZED, input.substring(0, 1)));
   }
}
