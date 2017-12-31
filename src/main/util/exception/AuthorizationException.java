package main.util.exception;

import main.util.ErrorMessages;
import main.util.enums.AccessLevel;

public class AuthorizationException extends Exception {
   public AuthorizationException(AccessLevel level, String command) {
      super(String.format(ErrorMessages.ACCESS_LIST_CANNOT_USE_COMMAND, level.toString(), command));
   }
}
