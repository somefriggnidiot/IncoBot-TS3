package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.server.ServerConnectionManager;
import main.util.MessageHandler;
import main.util.Messages;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;

/**
 * Command used to move the bot to the caller's channel.
 */
public class ComeHereCommand {

   private TextMessageEvent event;
   private TS3ApiAsync api;

   /**
    * Create a ComeHereCommand instance to handle client executions.
    *
    * @param event the {@link TextMessageEvent} that triggered the ComeHereCommand
    * @throws AuthorizationException is the invoker of this command does not have authorization
    * to execute it.
    */
   public ComeHereCommand(TextMessageEvent event) throws AuthorizationException {
      this.event = event;
      ServerConnectionManager instance = Executor.getServer("testInstance");
      api = instance.getApiAsync();

      AccessManager accessManager = new AccessManager(new ConfigHandler(), AccessLevel.DEFAULT);
      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(event
          .getInvokerId()).getUninterruptibly().getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         this.handle();
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!comehere");
      }
   }

   private void handle() {
      final String RETURN_TEXT = "You rang?";

      try {
         ClientInfo invoker = api.getClientInfo(event.getInvokerId()).get(2500, TimeUnit
             .MILLISECONDS);
         Integer channelId = invoker.getChannelId();

         api.moveQuery(channelId);
         new MessageHandler(RETURN_TEXT).sendToChannel();
      } catch (InterruptedException | TimeoutException e) {
         new MessageHandler(String.format(Messages.ERROR_COMMAND_TIMEOUT, "ComeHere"))
             .returnToSender(event);
      }
   }
}
