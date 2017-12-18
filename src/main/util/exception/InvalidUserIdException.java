package main.util.exception;

import main.util.ErrorMessages;

public class InvalidUserIdException extends Exception {

   /**
    * Exception thrown when an ID that does not belong to anybody is being targeted in a command.
    *
    * @param id the ID being targeted.
    */
   public InvalidUserIdException(String id) {
      super(String.format(ErrorMessages.NO_USER_WITH_ID, id));
   }
}
