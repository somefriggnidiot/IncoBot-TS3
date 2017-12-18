package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.core.Executor;
import main.server.ServerConnectionManager;
import main.util.ErrorMessages;
import main.util.MessageHandler;
import main.util.exception.ArgumentMissingException;
import main.util.exception.IllegalTargetException;
import main.util.exception.InvalidUserIdException;

/**
 * Command used to forcefully disconnect a client from the server.
 */
public class KickCommand {

   private TextMessageEvent event;

   /**
    * Create a KickCommand instance to handle console execution.
    *
    * @param input the string read in from the console that triggered this command.
    */
   public KickCommand(String input) {
      try {
         handle(input);
      } catch (ArgumentMissingException | IllegalTargetException | InvalidUserIdException e) {
         new MessageHandler(e.getMessage()).sendToConsoleWith("COMMAND RESPONSE");
      }
   }

   /**
    * Create a KickCommand instance to handle client execution.
    *
    * @param event the {@link TextMessageEvent} containing the call for this command.
    */
   public KickCommand(TextMessageEvent event) {
      this.event = event;

      try {
         handle(event.getMessage());
      } catch (ArgumentMissingException | IllegalTargetException | InvalidUserIdException e) {
         new MessageHandler(e.getMessage()).sendToUser(event.getInvokerId());
      }
   }

   private void handle(String input) throws ArgumentMissingException, IllegalTargetException,
       InvalidUserIdException {
      ServerConnectionManager instance = Executor.getServer("testInstance");
      TS3ApiAsync api = instance.getApiAsync();
      String[] params = input.split("\\s", 3);
      int target = Integer.parseInt(params[1]);
      String reason;
      String targetName;

      if (target == instance.getBotId()) {
         throw new IllegalTargetException();
      }

      //Set reason, throwing ArgumentMissingException if not present.
      try {
         reason = params[2];
      } catch (IndexOutOfBoundsException e) {
         throw new ArgumentMissingException("kick", "reason");
      }

      //Determine target by ID, throwing InvalidUserIdException if no connected client has that ID.
      try {
         targetName = api.getClientInfo(target).getUninterruptibly().getNickname();
      } catch (Exception e) {
         if (e.getCause().getMessage().contains("invalid clientID")) {
            throw new InvalidUserIdException(String.valueOf(target));
         } else {
            new MessageHandler(ErrorMessages.UNKNOWN_ERROR)
                .sendToConsoleWith(Level.WARNING)
                .sendToUser(event.getInvokerId());
         }
         return;
      }

      //Log to console.
      if (event != null) {
         new MessageHandler(String.format("%s attempted to kick %s from the server for: %s", event
             .getInvokerName(), targetName, reason))
             .sendToConsoleWith("KICK");
      } else {
         new MessageHandler(String.format("Attempting to kick %s from the server for: %s",
             targetName, reason))
             .sendToConsoleWith("KICK");
      }

      //Execute kick.
      api.kickClientFromServer(reason, target).onFailure(e ->
          api.kickClientFromServer(reason, target));
   }
}
