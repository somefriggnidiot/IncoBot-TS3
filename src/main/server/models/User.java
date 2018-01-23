package main.server.models;

import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import main.core.Executor;

public class User {
   private ClientInfo clientInfo;
   private int clientId;
   private String currentChannel;

   User(ClientInfo clientInfo) {
      this.clientInfo = clientInfo;
      this.clientId = clientInfo.getId();
      this.currentChannel = Executor.getServer("testInstance").getApi().getChannelInfo
          (clientInfo.getChannelId()).getName();
   }

}
