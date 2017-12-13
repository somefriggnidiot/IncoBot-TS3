package main.core.commands;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.logging.Level;
import main.conf.ErrorMessages;
import main.core.MessageHandler;
import main.util.exception.CommandNotFoundException;
import org.apache.commons.lang3.StringUtils;

public class Command {

   private static final String prefix = "!";

   public static void handle(final String input) throws CommandNotFoundException {
      validate(input);

      int returnValue = -1;
      final String[] command = input.split("\\s", 2);
      final String action = command[0].substring(1);

      switch (action) {
         case "foo":
            new MessageHandler(Level.INFO, "Bar");
            return;
         default:
            throw(new CommandNotFoundException(action));
      }
   }

   private static void validate(final String input) {
      checkArgument(!StringUtils.isBlank(input), String.format(ErrorMessages.INPUT_BLANK));
      checkArgument(input.startsWith(prefix),
          String.format(ErrorMessages.COMMAND_PREFIX_NOT_RECOGNIZED, input.substring(0, 1)));
   }
}
