package main.conf;

import java.util.logging.Level;

/**
 * DTO for the general configuration of the program.
 */
public class Configuration {

   private static boolean setupComplete;
   private static Level loggingLevel;

   /**
    * Returns whether or not the initial setup has been completed.
    */
   public static boolean isSetupComplete() {
      return setupComplete;
   }

   /**
    * Sets whether or not the initial setup has been completed.
    */
   public static void setSetupComplete(boolean setupComplete) {
      Configuration.setupComplete = setupComplete;
   }

   /**
    * Returns the logging level of the program.
    */
   public static Level getLoggingLevel() {
      return loggingLevel;
   }

   /**
    * Sets the logging level of the program.
    */
   public static void setLoggingLevel(Level loggingLevel) {
      Configuration.loggingLevel = loggingLevel;
   }
}
