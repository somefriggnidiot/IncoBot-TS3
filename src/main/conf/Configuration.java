package main.conf;

import java.util.logging.Level;

public class Configuration {
  private static boolean setupComplete;
  private static Level loggingLevel;

  public static boolean isSetupComplete() {
    return setupComplete;
  }

  public static void setSetupComplete(boolean setupComplete) {
    Configuration.setupComplete = setupComplete;
  }

  public static Level getLoggingLevel() {
    return loggingLevel;
  }

  public static void setLoggingLevel(Level loggingLevel) {
    Configuration.loggingLevel = loggingLevel;
  }
}
