package main.util.exception;

import main.conf.ErrorMessages;

public class CommandNotFoundException extends Exception {
   public CommandNotFoundException() {
      super(String.format(ErrorMessages.COMMAND_NOT_FOUND, "The input provided"));
   }

   public CommandNotFoundException(String invalidCommand) {
      super(String.format(ErrorMessages.COMMAND_NOT_FOUND, "\'" + invalidCommand + "\'"));
   }
}
