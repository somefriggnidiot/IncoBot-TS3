package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
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
         handle(input);
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
         handle(event.getMessage());
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!idlechecker");
      } catch (Exception e) {
         new MessageHandler(e.getMessage()).sendToUser(event.getInvokerId());
      }
   }

   private void handle(String input) throws Exception {
      String[] params = input.split("\\s", 2);

      if (params.length != 2) {
         throw new ArgumentMissingException("idlechecker", "action");
      }

      switch (params[1]) {
         case "enable":
            IdleChecker.start();
            return;
         case "disable":
            IdleChecker.stop();
            return;
         default:
            throw new Exception("Unrecognized action. Please refer to documentation. Accepted "
                + "actions: 'enable', 'disable'");
      }
   }
}
