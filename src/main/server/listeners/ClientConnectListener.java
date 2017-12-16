package main.server.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import main.server.listeners.handlers.ClientJoinHandler;

/**
 * Custom implementation of {@link ClientJoinEvent}.
 */
public class ClientConnectListener extends TS3EventAdapter {
   private boolean consoleLoggingOn = false;
   private boolean fileLoggingOn = false;

   @Override
   public void onClientJoin(ClientJoinEvent newClient) {
      consoleLoggingOn = true;
      fileLoggingOn = false;

      new ClientJoinHandler(newClient, consoleLoggingOn, fileLoggingOn);
   }
}
