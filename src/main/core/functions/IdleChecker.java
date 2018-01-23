package main.core.functions;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api.CommandFuture;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import main.conf.ConfigHandler;
import main.conf.IdleCheckConfiguration;
import main.core.Executor;
import main.util.LogPrefix;
import main.util.MessageHandler;
import main.util.Messages;

/**
 * Functionality for looped/scheduled checking of online clients for idle users.
 */
public class IdleChecker extends TimerTask {

   private IdleCheckConfiguration config = ConfigHandler.readIdleCheckConfig(
       new File("./config/IdleChecker.yaml"));
   private TS3ApiAsync api = Executor.getServer("testInstance").getApiAsync();
   private int maxIdleTimeMilliseconds = config.getMaxTimeMinutes() * 60000;
   private int botClientId = Executor.getServer("testInstance").getBotId();

   private static IdleChecker idleChecker;
   private static Timer idleCheckerTimer;

   @Override
   public void run() {
      CommandFuture<List<Client>> onlineClients = api.getClients();
      onlineClients.onSuccess(e -> {
         List<Client> applicableClients = getApplicableClients(e);
         moveIdleUsersFromList(applicableClients);
      });
   }

   /**
    * Begins execution of the Idle Checker loop.
    */
   public static void start() {
      idleChecker = new IdleChecker();
      idleCheckerTimer = new Timer();
      idleCheckerTimer.schedule(idleChecker, 0, 1000);
      new MessageHandler(String.format(Messages.IDLE_CHECK_ENABLED, idleChecker.api
          .getChannelInfo(idleChecker.config.getDestinationChannel()).getUninterruptibly()
          .getName(), idleChecker.config.getMaxTimeMinutes())).sendToConsoleWith(LogPrefix.IDLE);
   }

   /**
    * Stops execution of the Idle Checker loop.
    */
   public static void stop() {
      idleChecker.cancel();
      idleCheckerTimer.cancel();
      idleCheckerTimer.purge();
      new MessageHandler(Messages.IDLE_CHECK_DISABLED).sendToConsoleWith(LogPrefix.IDLE);
   }

   /**
    * Compiles a list of clients that can be moved if idle. Clients are determined to be movable
    * if they are not in one of the ignored groups, and they are not the this program.
    *
    * @param onlineClients a list of all clients online.
    * @return a list of all online, movable clients.
    */
   private List<Client> getApplicableClients(List<Client> onlineClients) {
      int[] ignoredGroups = config.getIgnoreGroups();
      List<Client> applicableClients = new ArrayList<>();
      Boolean ignore;

      for (Client client : onlineClients) {
         if (client.getId() == botClientId) {
            continue;
         }
         ignore = false;
         int[] serverGroups = client.getServerGroups();

         for (int serverGroup : serverGroups) {
            for (int ignoredGroup : ignoredGroups) {
               ignore = serverGroup == ignoredGroup;
               if (ignore) {
                  break;
               }
            }
            if (ignore) {
               break;
            }
         }

         if (!ignore) {
            applicableClients.add(client);
         }
      }

      return applicableClients;
   }

   /**
    * Moves listed clients if they are idle longer than the threshold time.
    *
    * @param clients a list of clients that should be moved if idle.
    */
   private void moveIdleUsersFromList(List<Client> clients) {
      for (Client client : clients) {
         if (client.getIdleTime() > maxIdleTimeMilliseconds &&
             client.getChannelId() != config.getDestinationChannel()) {
            CommandFuture<Boolean> operation = api.moveClient(client.getId(), config
                .getDestinationChannel());
            operation.onSuccess(e -> {
               String channelName = api.getChannelInfo(config.getDestinationChannel())
                   .getUninterruptibly().getName();
               new MessageHandler(String
                   .format(Messages.CLIENT_MOVED_FOR_INACTIVITY, client.getNickname(),
                       client.getUniqueIdentifier(), (client.getIdleTime() / 60000)))
                   .sendToConsoleWith(LogPrefix.IDLE);
               new MessageHandler(String
                   .format(Messages.YOU_HAVE_BEEN_MOVED, channelName, config.getMaxTimeMinutes()))
                   .sendToUser(client.getId());
            });
         }
      }
   }
}
