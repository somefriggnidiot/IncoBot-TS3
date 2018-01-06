package main.util.exception;

import main.util.Messages;

public class IllegalTargetException extends Exception {

   /**
    * Exception thrown when the application query connection is being targeted in a command which
    * cannot be executed against it.
    */
   public IllegalTargetException() {
      super(Messages.CANNOT_TARGET_BOT);
   }

}
