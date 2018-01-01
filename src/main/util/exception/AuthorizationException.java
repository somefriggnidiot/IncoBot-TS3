package main.util.exception;

import main.util.ErrorMessages;
import main.util.enums.AccessLevel;

public class AuthorizationException extends Exception {
   public AuthorizationException(String required, String given) {
      super(String.format(ErrorMessages.LEVEL_LOWER_THAN_REQUIRED, required, given));
   }

   public AuthorizationException(AccessLevel level, String command) {
      super(String.format(ErrorMessages.ACCESS_LIST_CANNOT_USE_COMMAND, level.toString(), command));
   }
}
