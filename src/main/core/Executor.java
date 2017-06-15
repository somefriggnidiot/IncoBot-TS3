package main.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import main.server.ServerConnectionManager;
import main.server.listeners.TextMessageListener;

public class Executor implements Runnable {

   static BufferedReader in;
   static Boolean quit = false;
   private final static Map<String, ServerConnectionManager> scmMap = new HashMap<>();
   
   public void run() {
      String msg = null;

      while (true) {
         try {
            msg = in.readLine();
         } catch (Exception e) {

         }
         if (msg.equals("!forcequit")) {
            quit = true;
            break;
         }
      }
   }

   public static void main(String[] args) throws Exception {
      /*
       * INITIALIZATION
       * 
       * One-off program setup before main execution loop.
       */

      new MessageHandler(Level.ALL, Level.INFO, "System Initialized");

      in = new BufferedReader(new InputStreamReader(System.in));
      Thread t1 = new Thread(new Executor());
      t1.start();

      scmMap.put("testInstance", new ServerConnectionManager());
      new MessageHandler(scmMap.get("testInstance").getDebugLevel(), Level.INFO, "Connecting...");
      scmMap.get("testInstance").connect();
      
      /*
       * EXECUTION LOOP
       * 
       */
      Boolean exit = false;
      int thirtySecCntr = 600;
      do {
         Thread.sleep(500);
         if (thirtySecCntr++ == 600) {
            thirtySecCntr = 0;
            new MessageHandler(Level.ALL, Level.INFO, "System Running");
         }
         
         if (quit) {
            new MessageHandler(Level.INFO, Level.INFO, "Disconnecting bot.");
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
   
   public static ServerConnectionManager getServer(String instance) {
      return scmMap.get(instance);
   }
}
