package main.util.exception;

import main.util.ErrorMessages;

public class ArgumentMissingException extends Exception {

   /**
    * Exception thrown when a command is missing required arguments.
    *
    * @param command the command being executed without proper arguments.
    * @param argument the missing required argument.
    */
   public ArgumentMissingException(String command, String argument) {
      super(String.format(ErrorMessages.MISSING_ARGUMENT, command, argument));
   }
}
