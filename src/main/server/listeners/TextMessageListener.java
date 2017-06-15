package main.server.listeners;

import com.github.theholywaffle.teamspeak3.api.event.TS3EventAdapter;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;

import main.server.listeners.handlers.TextMessageHandler;

public class TextMessageListener extends TS3EventAdapter {
   
   @Override
   public void onTextMessage(TextMessageEvent messageEvent) {
      boolean consoleLoggingOn = true;
      boolean fileLoggingOn = false;
      
      new TextMessageHandler(messageEvent, consoleLoggingOn, fileLoggingOn);
   }
}
