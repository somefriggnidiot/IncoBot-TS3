package main.util.exception;

import main.util.Messages;

public class ClientNotFoundException extends Exception {

   /**
    * Exception thrown when attempting to access a client by ID and the target client cannot be
    * found.
    *
    * @param id the ID of the client that cannot be found.
    */
   public ClientNotFoundException(String id) {
      super(String.format(Messages.ERROR_CLIENT_NOT_FOUND_WITH_ID, id));
   }
}
