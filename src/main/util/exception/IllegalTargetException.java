package main.util.exception;

import main.util.ErrorMessages;

public class IllegalTargetException extends Exception {

   /**
    * Exception thrown when the application query connection is being targeted in a command which
    * cannot be executed against it.
    */
   public IllegalTargetException() {
      super(ErrorMessages.CANNOT_TARGET_BOT);
   }

}
