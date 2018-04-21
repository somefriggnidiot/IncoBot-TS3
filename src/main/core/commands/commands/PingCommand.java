package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.server.ServerConnectionManager;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;

/**
 * Command used to do a basic health-check of the application.
 */
public class PingCommand {

   private TextMessageEvent event;

   /**
    * Create a PingCommand instance to handle console executions.
    */
   public PingCommand() {
      this.handle();
   }

   /**
    * Create a PingCommand instance to handle client executions.
    *
    * @param event the {@link TextMessageEvent} that triggered the PingCommand.
    * @throws AuthorizationException if the invoker of this command does not have authorization to
    * execute it.
    */
   public PingCommand(TextMessageEvent event) throws AuthorizationException {
      this.event = event;
      ServerConnectionManager instance = Executor.getServer("testInstance");
      TS3ApiAsync api = instance.getApiAsync();

      AccessManager accessManager = new AccessManager(new ConfigHandler(),
          AccessLevel.DEFAULT);
      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(event
          .getInvokerId()).getUninterruptibly().getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         this.handle();
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!ping");
      }
   }

   private void handle() {
      final String RETURN_TEXT = "Pong!";

      if (event == null) {
         new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
         return;
      }

      TextMessageTargetMode mode = event.getTargetMode();
      switch (mode) {
         case SERVER:
            new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                .sendToServer();
            break;
         case CHANNEL:
            new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                .sendToChannel();
            break;
         case CLIENT:
            new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                .returnToSender(event);
            break;
      }
   }
}
