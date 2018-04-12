package main.server.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import main.server.listeners.handlers.ClientMovedHandler;

/**
 * Custom implementation of {@link ClientMovedEvent} listener.
 */
public class ClientMovedListener extends TS3EventAdapter {

   //TODO Read values from config.
   private boolean consoleLoggingOn = false;
   private boolean fileLoggingOn = false;

   @Override
   public void onClientMoved(ClientMovedEvent movedEvent) {
      consoleLoggingOn = true;
      fileLoggingOn = false;

      new ClientMovedHandler(movedEvent, consoleLoggingOn, fileLoggingOn);
   }
}
