package main.util.exception;

import main.util.ErrorMessages;

/**
 * Exception used when a command is given but cannot be matched to an existing command.
 */
public class CommandNotFoundException extends Exception {
   public CommandNotFoundException() {
      super(String.format(ErrorMessages.COMMAND_NOT_FOUND, "The input provided"));
   }

   public CommandNotFoundException(String invalidCommand) {
      super(String.format(ErrorMessages.COMMAND_NOT_FOUND, "\'" + invalidCommand + "\'"));
   }
}
