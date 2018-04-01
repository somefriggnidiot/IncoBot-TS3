package main.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import main.core.commands.Commands;
import main.server.ServerConnectionManager;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.exception.CommandNotFoundException;

/**
 * Main class of the SFITS3 bot program.
 */
public class Executor implements Runnable {

   private final static Map<String, ServerConnectionManager> instances = new HashMap<>();
   static BufferedReader in;
   static Boolean quit = false;

   /**
    * Begins bot services and logging. This function is run automatically upon execution and should
    * not be called in other classes!
    *
    * @param args arguments to be passed through upon Jar execution.
    * @throws Exception if there are any errors encountered during execution.
    */
   public static void main(String[] args) throws Exception {
      /*
       * INITIALIZATION
       *
       * One-off program setup before main execution loop.
       */

      new MessageHandler("System Initialized").sendToConsoleWith(Level.INFO);

      in = new BufferedReader(new InputStreamReader(System.in));
      Thread t1 = new Thread(new Executor());
      t1.start();

      //First Time Setup check will go here.

      //Load and connect instance.
      instances.put("testInstance", new ServerConnectionManager());
      new MessageHandler("Connecting...").sendToConsoleWith(Level.INFO);
      instances.get("testInstance").connect();

      /*
       * EXECUTION LOOP
       *
       */
      Boolean exit = false;
      int fiveMinuteTimer = 600 * 6;
      do {
         Thread.sleep(500);
         if (fiveMinuteTimer++ == (600 * 6)) {
            fiveMinuteTimer = 0;
            new MessageHandler("System Running").sendToConsoleWith(Level.INFO);
         }

         if (quit) {
            new MessageHandler("Disconnecting From Server").sendToConsoleWith(Level.INFO);
            exit = true;
         }

      } while (!exit);

      /*
       * CLEANUP
       *
       * One-off program tear-down.
       */
      instances.get("testInstance").disconnect();

   }

   /**
    * Gets a {@link ServerConnectionManager} instance from the current list of managers.
    *
    * @param instanceName the name of the instance to get.
    * @return the {@code ServerConnectionManager} if it exists, otherwise {@code null}.
    */
   public static ServerConnectionManager getServer(String instanceName) {
      return instances.get(instanceName);
   }

   /**
    * Read loop to accept commands from the program's console. This function is run automatically
    * upon execution and should not be called in other classes!
    */
   public void run() {
      String msg = null;

      while (true) {
         try {
            msg = in.readLine();
            Commands.handle(msg);
         } catch (IllegalArgumentException | CommandNotFoundException e) {
            new MessageHandler(e.getMessage()).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
         } catch (RuntimeException rte) {
            new MessageHandler(rte.getMessage()).sendToConsoleWith(Level.SEVERE);
            break;
         } catch (Exception ex) {
            new MessageHandler(ex.getMessage()).sendToConsoleWith(Level.WARNING);
            if (ex.getMessage().equals("Unhandled exception")) {
               ex.printStackTrace();
            }
         }
         if ("!forcequit".equals(msg)) {
            quit = true;
            break;
         }
      }
   }
}
