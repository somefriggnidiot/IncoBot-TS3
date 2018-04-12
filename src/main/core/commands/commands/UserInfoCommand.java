package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.CommandFuture;
import com.github.theholywaffle.teamspeak3.api.TextMessageTargetMode;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.exception.TS3Exception;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import javax.annotation.Nullable;
import main.conf.ConfigHandler;
import main.core.Executor;
import main.core.commands.AccessManager;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;


/**
 * Command used to do a check on user's information.
 */
public class UserInfoCommand {

   private final TS3ApiAsync api;
   private TextMessageEvent event;

   /**
    * Create a UserCommand instance to handle console executions.
    */
   public UserInfoCommand(String input) {
      api = Executor.getServer("testInstance").getApiAsync();

      this.handle(input, null);
   }

   /**
    * Create a UserInfoCommand instance to handle client executions.
    *
    * @param event the {@link TextMessageEvent} that triggered the PingCommand.
    * @throws AuthorizationException if the invoker of this command does not have authorization to
    * execute it.
    */
   public UserInfoCommand(TextMessageEvent event) throws AuthorizationException {
      this.event = event;
      api = Executor.getServer("testInstance").getApiAsync();

      AccessManager accessManager = new AccessManager(new ConfigHandler(),
          AccessLevel.MODERATOR);
      AccessLevel invokerAccessLevel = accessManager.getAccessLevel(api.getClientInfo(event
          .getInvokerId()).getUninterruptibly().getServerGroups());

      try {
         accessManager.checkAccess(invokerAccessLevel);
         this.handle(event.getMessage(), event);
      } catch (AuthorizationException e) {
         throw new AuthorizationException(invokerAccessLevel, "!UserInfo");
      }
   }

   private void handle(String input, TextMessageEvent event) {
      List<Client> clients;
      String[] params = input.split("\\s", 2);

      try {
         if (params.length == 1) {
            clients = api.getClients().get(2000, TimeUnit.MILLISECONDS);
         } else {
            if (params[1].equalsIgnoreCase("@me")) {
               if (event == null) {
                  clients = Lists.newArrayList(api.getClientByNameExact(Executor.getServer
                      ("testInstance").getBotNickname(), false).get(2000, TimeUnit.MILLISECONDS));
               } else {
                  clients = Lists
                      .newArrayList(api.getClientByNameExact(event.getInvokerName(), false)
                          .get(2000, TimeUnit.MILLISECONDS));
               }
            } else {
               try {
                  CommandFuture<List<Client>> clientCommand = api.getClientsByName(params[1]);
                  clients = clientCommand.get(2000, TimeUnit.MILLISECONDS);
               } catch (TS3Exception e) {
                  MessageHandler handler = new MessageHandler(
                      String.format("No online clients had names containing "
                          + "\"%s\"", params[1]));
                  if (event == null) {
                     handler.sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
                  } else {
                     handler.returnToSender(event);
                  }
                  return;
               }
            }
         }

         printList(compileResponse(clients), event != null ? event.getTargetMode() : null);

      } catch (InterruptedException | TimeoutException e) {
         MessageHandler messager = new MessageHandler(String.format(Messages.ERROR_COMMAND_TIMEOUT,
             "UserInfo")).sendToConsoleWith(Level.WARNING);
         if (event != null) {
            messager.returnToSender(event);
         }
      }
   }

   private void printList(String text, @Nullable TextMessageTargetMode mode) {
      if (event == null || mode == null) {
         new MessageHandler(text).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
         return;
      }

      if (mode == TextMessageTargetMode.SERVER) {
         new MessageHandler(text).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
             .sendToServer();

      } else if (mode == TextMessageTargetMode.CHANNEL) {
         new MessageHandler(text).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
             .sendToChannel();

      } else if (mode == TextMessageTargetMode.CLIENT) {
         new MessageHandler(text).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
             .returnToSender(event);

      }
   }

   private String compileResponse(List<Client> clients) {
      StringBuilder returnText;

      if (event == null) {
         returnText = new StringBuilder("\n| Name | UID | Client ID |\n");
      } else {
         returnText = new StringBuilder(".\n[b][u]| Name | UID | Client ID |[/u][/b]\n");
      }

      for (Client client : clients) {
         returnText.append(String
             .format("| %s | %s | %s |\n", client.getNickname(), client.getUniqueIdentifier(),
                 client.getId()));
      }

      return returnText.toString();
   }
}
