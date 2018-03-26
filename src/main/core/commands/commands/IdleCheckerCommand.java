package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.core.functions.IdleChecker;
import main.server.ServerConnectionManager;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.enums.AccessLevel;
import main.util.exception.ArgumentMissingException;
import main.util.exception.AuthorizationException;

/**
 * Command used to manage the {@link IdleChecker} functionality.
 */
public class IdleCheckerCommand {

   private ServerConnectionManager instance;
   private TS3ApiAsync api;

   /**
    * Create an IdleCheckerCommand to handle console execution.
    *
    * @param input the string read in from the console that triggered this command.
    */
   public IdleCheckerCommand(String input) {
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApiAsync();

      try {
         handle(input, null);
      } catch (Exception e) {
         new MessageHandler(e.getMessage()).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
      }
   }

   /**
    * Create an IdleCheckerCommand instance to handle client execution.
    *
    * @param event the {@link TextMessageEvent} containing the call for this command.
    * @throws AuthorizationException if the invoker of this command does not have authorization to
    * execute it.
    */
   public IdleCheckerCommand(TextMessageEvent event) throws AuthorizationException {
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApiAsync();

      AccessManager accessManager = new AccessManager(new ConfigHandler(), AccessLevel.ADMIN);

      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(event
          .getInvokerId()).getUninterruptibly().getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         handle(event.getMessage(), event);
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!idlechecker");
      } catch (Exception e) {
         new MessageHandler(e.getMessage()).sendToUser(event.getInvokerId());
      }
   }

   private void handle(String input, TextMessageEvent event) throws Exception {
      String[] params = input.split("\\s", 2);
      MessageHandler messageHandler;

      if (params.length == 2) {
         switch (params[1]) {
            case "enable":
            case "on":
               messageHandler = new MessageHandler(IdleChecker.start());
               break;
            case "disable":
            case "off":
               messageHandler = new MessageHandler(IdleChecker.stop());
               break;
            case "status":
               messageHandler = new MessageHandler(IdleChecker.getStatusReport());
               break;
            default:
               throw new Exception("Unrecognized action. Please refer to documentation. Accepted "
                   + "actions: 'enable', 'disable'");
         }
      } else if (params.length == 1) {
         messageHandler = new MessageHandler(IdleChecker.getStatusReport());
      } else {
         throw new ArgumentMissingException("idlechecker", "action");
      }

      if (event != null) {
         if (event.getTargetMode() == TextMessageTargetMode.SERVER) {
            messageHandler.sendToServer();
         } else if (event.getTargetMode() == TextMessageTargetMode.CHANNEL) {
            messageHandler.sendToChannel();
         } else if (event.getTargetMode() == TextMessageTargetMode.CLIENT) {
            messageHandler.returnToSender(event);
         }
      } else {
         messageHandler.sendToConsoleWith(LogPrefix.IDLE);
      }
   }
}
