package main.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import main.conf.ConfigHandler;
import main.conf.ConnectionConfiguration;
import main.core.commands.Commands;
import main.server.ServerConnectionManager;
import main.util.MessageHandler;
import main.util.exception.CommandNotFoundException;

/**
 * Main class of the SFITS3 bot program.
 */
public class Executor implements Runnable {

   private final static Map<String, ServerConnectionManager> scmMap = new HashMap<>();
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

      new MessageHandler(Level.INFO, "System Initialized");

      in = new BufferedReader(new InputStreamReader(System.in));
      Thread t1 = new Thread(new Executor());
      t1.start();

      scmMap.put("testInstance", new ServerConnectionManager());
      new MessageHandler(Level.INFO, "Connecting...");
      scmMap.get("testInstance").connect();

      /*
       * EXECUTION LOOP
       *
       */
      Boolean exit = false;
      int thirtySecondCounter = 600;
      do {
         Thread.sleep(500);
         if (thirtySecondCounter++ == 600) {
            thirtySecondCounter = 0;
            new MessageHandler(Level.INFO, "System Running");
         }

         if (quit) {
            new MessageHandler(Level.INFO, "Disconnecting bot.");
            exit = true;
         }

      } while (!exit);

      /*
       * CLEANUP
       *
       * One-off program tear-down.
       */
      scmMap.get("testInstance").disconnect();

   }

   /**
    * Gets a {@link ServerConnectionManager} instance from the current list of managers.
    *
    * @param instanceName the name of the instance to get.
    * @return the {@code ServerConnectionManager} if it exists, otherwise {@code null}.
    */
   public static ServerConnectionManager getServer(String instanceName) {
      return scmMap.get(instanceName);
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
         } catch (IllegalArgumentException iae) {
            new MessageHandler(Level.INFO, iae.getMessage());
         } catch (CommandNotFoundException cnfe) {
            new MessageHandler(Level.INFO, cnfe.getMessage());
         } catch (Exception e) {
            System.out.println(e);
         }
         if (msg.equals("!forcequit")) {
            quit = true;
            break;
         }
      }
   }
}
