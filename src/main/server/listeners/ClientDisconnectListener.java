package main.server.listeners;

import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import main.server.listeners.handlers.ClientDisconnectHandler;

public class ClientDisconnectListener extends TS3EventAdapter {

   //TODO Read from config.
   private boolean consoleLoggingOn = false;
   private boolean fileLoggingOn = false;

   @Override
   public void onClientLeave(ClientLeaveEvent disconnectEvent) {
      this.consoleLoggingOn = true; //TODO Add config.
      this.fileLoggingOn = false; //TODO Add config.

      new ClientDisconnectHandler(disconnectEvent, consoleLoggingOn, fileLoggingOn);
   }
}
