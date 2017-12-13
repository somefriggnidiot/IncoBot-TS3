package main.core;

import java.util.logging.Level;
import main.conf.Configuration;

public class MessageHandler {
   private static Level configDebugLevel = Configuration.getLoggingLevel();
   Level messageLevel;
   String prefix, message;

   /**
    * Log a message to the console if it's of a certain level.
    * 
    * @param configDebugLevel  The logging level being used by the config file.
    * @param messageLevel  The logging level of the message.
    * @param message  The message being displayed.
    */
   @Deprecated
   public MessageHandler(Level configDebugLevel, Level messageLevel, String message) {
      this.configDebugLevel = configDebugLevel;
      this.messageLevel = messageLevel;
      this.message = message;
      handle();
   }

  /**
   * Log a message to the console if it's of a certain level.
   *
   * @param messageLevel  The logging level of the message.
   * @param message  The message being displayed.
   */
  public MessageHandler(Level messageLevel, String message) {
    this.messageLevel = messageLevel;
    this.message = message;
    System.out.println(Util.timeStamp() + "[" + messageLevel.getName() + "] " + message);
  }
   
   /**
    * Log a message to the console regardless of the config's logging level.
    * 
    * @param prefix  The prefix for the message.
    * @param messageLevel  The logging level of the message.
    * @param message  The message being displayed.
    */
   public MessageHandler(String prefix, Level messageLevel, String message) {
      this.prefix = prefix;
      this.messageLevel = messageLevel;
      this.message = message;
      handle();
   }

   private void handle() {
      if (!configDebugLevel.getName().isEmpty() && messageLevel.intValue() >= configDebugLevel.intValue()) {
         System.out.println(Util.timeStamp() + "[" + messageLevel.getName() + "] " + message);
      }
      else if (configDebugLevel.getName().isEmpty() || configDebugLevel == null) {
         System.out.println(prefix + message);
      }
   }
}
