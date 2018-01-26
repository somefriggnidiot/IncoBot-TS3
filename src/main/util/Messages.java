package main.util;

/**
 * Generic error messages to be used across the application.
 */
public class Messages {

   public final static String CLIENT_MOVED_FOR_INACTIVITY = "%s (%s) has been moved for being "
       + "idle for %s minutes.";
   public final static String ERROR_ACCESS_LIST_CANNOT_USE_COMMAND =
       "The '%s' access list does not "
           + "have permissions to use command '%s'";
   public final static String ERROR_CANNOT_TARGET_BOT = "You cannot target the bot with that "
       + "command.";
   public final static String ERROR_CLIENT_NOT_FOUND_WITH_ID = "No client could be found for id: "
       + "%s";
   public final static String ERROR_COMMAND_NOT_FOUND = "%s is not a supported command.";
   public final static String ERROR_COMMAND_PREFIX_NOT_RECOGNIZED =
       "'%s' is not a recognized command"
           + " prefix!";
   public final static String ERROR_INPUT_BLANK =
       "Space may be the final frontier, but sending me spaces"
           + " does nothing!";
   public final static String ERROR_LEVEL_LOWER_THAN_REQUIRED =
       "The provided access level of %s is "
           + "lower than the required access level of %s.";
   public final static String ERROR_MISSING_ARGUMENT = "Command '%s' requires argument '%s'.";
   public final static String ERROR_NO_USER_WITH_ID = "No user is currently connected using id: %s";
   public final static String ERROR_UNKNOWN_ERROR =
       "Somewhere, something broke. Contact someone who "
           + "knows what they're doing.";
   public final static String IDLE_CHECK_DISABLED = "Idle Check disabled. Idle users will no "
       + "longer be automatically moved.";
   public final static String IDLE_CHECK_ENABLED = "Idle Check enabled. Idle users will now be "
       + "automatically moved to \"%s\" after %s minutes.";
   public final static String SUCCESSFULLY_CONNECTED = "Successfully connected to \"%s\" as "
       + "\"%s\"!";
   public final static String USER_BANNED = "%s (%s) was banned from the server by %s (%s) for %s"
       + " for reason: %s";
   public final static String USER_CONNECTED = "%s (%s) connected to \"%s\".";
   public final static String USER_DISCONNECTED = "%s (%s) disconnected from the server with "
       + "reason: %s";
   public final static String USER_KICKED = "%s (%s) was kicked from the server by %s (%s) for "
       + "reason: %s";
   public final static String USER_LOST_CONNECTION = "%s (%s) lost connection to the server.";
   public final static String YOU_HAVE_BEEN_MOVED = "You have been moved to \"%s\" for being idle"
       + " longer than %s minutes.";
}
