package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.core.functions.DadModeMessageChecker;
import main.server.ServerConnectionManager;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;

public class DadModeCommand {

   private ServerConnectionManager instance;
   private TS3Api api;

   /**
    * Creates a DadModeCommand instance to handle console execution.
    *
    * @param input the string read in from the console that triggered this command.
    */
   public DadModeCommand(String input) {
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApi();

      try {
         handle(input, null);
      } catch (Exception e) {
         new MessageHandler(e.getMessage()).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
      }
   }

   /**
    * Create a DadModeCommand instance to handle client execution.
    *
    * @param event the {@link TextMessageEvent} containing the call for this command.
    * @throws AuthorizationException if the invoker of this command does not have authorization to
    * execute it.
    */
   public DadModeCommand(TextMessageEvent event) throws AuthorizationException {
      this.instance = Executor.getServer("testInstance");
      this.api = instance.getApi();

      AccessManager accessManager = new AccessManager(new ConfigHandler(), AccessLevel.ADMIN);

      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(event
          .getInvokerId()).getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         handle(event.getMessage(), event);
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!dadmode");
      } catch (Exception e) {
         new MessageHandler(e.getMessage()).returnToSender(event);
      }
   }

   private void handle(String input, TextMessageEvent event) throws Exception {
      String[] params = input.split("\\s", 2);
      MessageHandler messageHandler;

      switch (params[1]) {
         case "enable":
         case "enabled":
         case "on":
            messageHandler = new MessageHandler(DadModeMessageChecker.start());
            break;
         case "disable":
         case "disabled":
         case "off":
            messageHandler = new MessageHandler(DadModeMessageChecker.stop());
            break;
         default:
            throw new Exception("Unrecognized action. Please refer to documentation. Accepted "
                + "actions: 'enable', 'disable'");
      }

      messageHandler.sendToConsoleWith(LogPrefix.DAD_MODE);

      if (event != null) {
         if (event.getTargetMode() == TextMessageTargetMode.SERVER) {
            messageHandler.sendToServer();
         } else if (event.getTargetMode() == TextMessageTargetMode.CHANNEL) {
            messageHandler.sendToChannel();
         } else if (event.getTargetMode() == TextMessageTargetMode.CLIENT) {
            messageHandler.returnToSender(event);
         }
      }
   }
}
