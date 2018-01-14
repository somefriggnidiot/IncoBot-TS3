package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.logging.Level;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.server.ServerConnectionManager;
import main.util.Messages;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.enums.AccessLevel;
import main.util.exception.ArgumentMissingException;
import main.util.exception.AuthorizationException;
import main.util.exception.IllegalTargetException;
import main.util.exception.InvalidUserIdException;

/**
 * Command used to forcefully disconnect a client from the server.
 */
public class KickCommand {

   private TextMessageEvent event;
   private ServerConnectionManager instance;
   private TS3ApiAsync api;

   /**
    * Create a KickCommand instance to handle console execution.
    *
    * @param input the string read in from the console that triggered this command.
    */
   public KickCommand(String input) {
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApiAsync();

      try {
         handle(input);
      } catch (ArgumentMissingException | IllegalTargetException | InvalidUserIdException e) {
         new MessageHandler(e.getMessage()).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
      }
   }

   /**
    * Create a KickCommand instance to handle client execution.
    *
    * @param event the {@link TextMessageEvent} containing the call for this command.
    * @throws AuthorizationException if the invoker of this command does not have authorization
    * to execute it.
    */
   public KickCommand(TextMessageEvent event) throws AuthorizationException {
      this.event = event;
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApiAsync();

      AccessManager accessManager = new AccessManager(new ConfigHandler(), AccessLevel.MODERATOR);

      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(
          event.getInvokerId()).getUninterruptibly().getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         handle(event.getMessage());
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!kick");
      } catch (ArgumentMissingException | IllegalTargetException | InvalidUserIdException e) {
         new MessageHandler(e.getMessage()).sendToUser(event.getInvokerId());
      }
   }

   private void handle(String input) throws ArgumentMissingException, IllegalTargetException,
       InvalidUserIdException {
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
            new MessageHandler(Messages.ERROR_UNKNOWN_ERROR)
                .sendToConsoleWith(Level.WARNING)
                .sendToUser(event.getInvokerId());
         }
         return;
      }

      //Log to console.
      if (event != null) {
         new MessageHandler(String.format("%s attempted to kick %s from the server for: %s", event
             .getInvokerName(), targetName, reason))
             .sendToConsoleWith(LogPrefix.KICK);
      } else {
         new MessageHandler(String.format("Attempting to kick %s from the server for: %s",
             targetName, reason))
             .sendToConsoleWith(LogPrefix.KICK);
      }

      //Execute kick.
      api.kickClientFromServer(reason, target).onFailure(e ->
          api.kickClientFromServer(reason, target));
   }
}
