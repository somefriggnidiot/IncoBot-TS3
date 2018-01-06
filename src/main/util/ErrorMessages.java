package main.util;

/**
 * Generic error messages to be used across the application.
 */
public class ErrorMessages {
   public final static String ACCESS_LIST_CANNOT_USE_COMMAND = "The '%s' access list does not "
       + "have permissions to use command '%s'";
   public final static String CANNOT_TARGET_BOT = "You cannot target the bot with that "
       + "command.";
   public final static String COMMAND_NOT_FOUND = "%s is not a supported command.";
   public final static String COMMAND_PREFIX_NOT_RECOGNIZED = "'%s' is not a recognized command"
       + " prefix!";
   public final static String INPUT_BLANK = "Space may be the final frontier, but sending me spaces"
       + " does nothing!";
   public final static String LEVEL_LOWER_THAN_REQUIRED = "The provided access level of %s is "
       + "lower than the required access level of %s.";
   public final static String MISSING_ARGUMENT = "Command '%s' requires argument '%s'.";
   public final static String NO_USER_WITH_ID = "No user is currently connected using id: %s";
   public final static String UNKNOWN_ERROR = "Somewhere, something broke. Contact someone who "
       + "knows what they're doing.";
   public final static String USER_CONNECTED = "%s (%s) connected to \"%s\".";
}
