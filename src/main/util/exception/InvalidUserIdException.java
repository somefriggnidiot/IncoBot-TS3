package main.util.exception;

import main.util.Messages;

public class InvalidUserIdException extends Exception {

   /**
    * Exception thrown when an ID that does not belong to anybody is being targeted in a command.
    *
    * @param id the ID being targeted.
    */
   public InvalidUserIdException(String id) {
      super(String.format(Messages.ERROR_NO_USER_WITH_ID, id));
   }
}
