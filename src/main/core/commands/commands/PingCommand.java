package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.server.ServerConnectionManager;
import main.util.MessageHandler;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;

/**
 * Command used to do a basic health-check of the application.
 */
public class PingCommand {
   private TextMessageEvent event;
   private ServerConnectionManager instance;
   private TS3ApiAsync api;
   private AccessManager accessManager = new AccessManager(new ConfigHandler(),
       AccessLevel.DEFAULT);

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
    * @throws AuthorizationException if the invoker of this command does not have authorization
    * to execute it.
    */
   public PingCommand(TextMessageEvent event) throws AuthorizationException {
      this.event = event;
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApiAsync();

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
         new MessageHandler(RETURN_TEXT).sendToConsoleWith("COMMAND RESPONSE");
         return;
      }

      TextMessageTargetMode mode = event.getTargetMode();
      if (mode == TextMessageTargetMode.SERVER) {
         new MessageHandler(RETURN_TEXT).sendToConsoleWith("COMMAND RESPONSE").sendToServer();
      } else if (mode == TextMessageTargetMode.CHANNEL) {
         new MessageHandler(RETURN_TEXT).sendToConsoleWith("COMMAND RESPONSE").sendToChannel();
      } else if (mode == TextMessageTargetMode.CLIENT) {
         new MessageHandler(RETURN_TEXT).sendToConsoleWith("COMMAND RESPONSE").returnToSender(event);
      }
   }
}
