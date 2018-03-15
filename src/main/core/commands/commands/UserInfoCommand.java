package main.core.commands.commands;

import com.github.theholywaffle.teamspeak3.TS3ApiAsync;
import com.github.theholywaffle.teamspeak3.api;
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
     * Command used to do a check on user's information.
     */
    public class UserInfoCommand {

        private TextMessageEvent event;

        /**
         * Create a UserCommand instance to handle console executions.
         */
        public UserInfoCommand() { this.handle(); }

        /**
         * Create a UserInfoCommand instance to handle client executions.
         *
         * @param event the {@link TextMessageEvent} that triggered the PingCommand.
         * @throws AuthorizationException if the invoker of this command does not have authorization to
         * execute it.
         */
        public UserInfoCommand(TextMessageEvent event) throws AuthorizationException {
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
                throw new AuthorizationException(invokerAccessLevel, "!UserInfo");
            }
        }

        private void handle() {
            ServerConnectionManager instance = Executor.getServer("testInstance");
            TS3API api = instance.getAPI();
            final String RETURN_TEXT = ("User's Online: ");
            final String [] RETURN_NAMES = api.getClients();


            if (event == null) {
                new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE);
                return;
            }

            TextMessageTargetMode mode = event.getTargetMode();
            if (mode == TextMessageTargetMode.SERVER) {
                new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                        .sendToServer();
                for (int j = 0; j < RETURN_NAMES.length; j++) {
                    new MessageHandler(RETURN_NAMES[j]).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                            .sendToServer();
                }
            } else if (mode == TextMessageTargetMode.CHANNEL) {
                new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                        .sendToChannel();
                for (int j = 0; j < RETURN_NAMES.length; j++) {
                    new MessageHandler(RETURN_NAMES[j]).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                            .sendToChannel();
                }
            } else if (mode == TextMessageTargetMode.CLIENT) {
                new MessageHandler(RETURN_TEXT).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                        .returnToSender(event);
                for (int j = 0; j < RETURN_NAMES.length; j++) {
                    new MessageHandler(RETURN_NAMES[j]).sendToConsoleWith(LogPrefix.COMMAND_RESPONSE)
                            .returnToSender(event);
                }
            }
        }
    }
