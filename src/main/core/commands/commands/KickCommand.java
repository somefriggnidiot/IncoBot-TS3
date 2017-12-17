package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import java.util.Objects;
import main.core.Executor;
import main.util.MessageHandler;

public class KickCommand {
   TextMessageEvent event;

   /**
    * Create a KickCommand instance to handle console executions.
    * @param input
    */
   public KickCommand(String input) {

   }

   /**
    *
    * @param event
    */
   public KickCommand(TextMessageEvent event) throws Exception {
      this.event = event;
      String[] params = event.getMessage().split("\\s", 3);

      int target = Integer.parseInt(params[1]);
      String reason = params[2];

      handle(target, reason);
   }

   private void handle(int target, String reason) throws Exception {
      TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
      String targetName = null;
      try {
         targetName = api.getClientInfo(target).getUninterruptibly().getNickname();
      } catch (Exception e) {
         throw new Exception("No user is connected with that ID.");
      }
      api.kickClientFromServer(reason, target);
      new MessageHandler(String.format("%s attempted to kick %s from the server for: %s", event
          .getInvokerName(), targetName, event.getReasonMessage()))
          .sendToConsoleWith("KICK");
   }
}
