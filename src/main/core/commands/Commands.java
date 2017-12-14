package main.core.commands;

import static com.google.common.base.Preconditions.checkArgument;

import main.conf.ErrorMessages;
import main.core.commands.commands.PingCommand;
import main.util.exception.CommandNotFoundException;
import org.apache.commons.lang3.StringUtils;

public class Commands {
   private static String prefix = "!";


   public static void handle(String input) throws CommandNotFoundException {
      validate(input);

      final String[] command = input.split("\\s", 2);
      final String action = command[0].substring(1);

      switch (action.toLowerCase()) {
         case "ping":
            new PingCommand();
            return;
         default:
            throw(new CommandNotFoundException(command[0]));
      }
   }

   public static String getPrefix() {
      return prefix;
   }
   //
//   private static final String prefix = "!";
//
//   public static void handle(final String input) {
//      validate(input);
//
//      int returnValue = -1;
//      final String[] command = input.split("\\s", 2);
//      final String action = command[0].substring(1);
//
//      switch (action) {
//         case "foo":
//            new MessageHandler(Level.INFO, "Bar");
//            return;
//         default:
//            throw(new CommandNotFoundException(action));
//      }
//   }
//
   private static void validate(final String input) {
      checkArgument(!StringUtils.isBlank(input), String.format(ErrorMessages.INPUT_BLANK));
      checkArgument(input.startsWith(prefix),
          String.format(ErrorMessages.COMMAND_PREFIX_NOT_RECOGNIZED, input.substring(0, 1)));
   }
}
