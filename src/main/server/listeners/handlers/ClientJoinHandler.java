package main.server.listeners.handlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroup;

import main.core.Config;
import main.core.Executor;
import main.util.Util;

/**
 * Logic and helper functions used to handle 
 * the firing of a {@link ClientConnectListener}
 */
public class ClientJoinHandler {
      ClientJoinEvent event;
      Client client;
      ClientInfo clientInfo;
      List<ServerGroup> serverGroups;
      
      final TS3Api api = Executor.getServer("testInstance").getApi();
      
      public ClientJoinHandler(ClientJoinEvent event, boolean consoleLogging, boolean fileLogging) {
         this.event = event;
         this.client = api.getClientByUId(event.getUniqueClientIdentifier());
         this.clientInfo = api.getClientInfo(client.getId());
         this.serverGroups = api.getServerGroupsByClient(client);

         if(consoleLogging) logToConsole();
         if(fileLogging) logToFile();
         
         checkMembership();
      }

      private void logToFile() {
         // TODO Auto-generated method stub
         return;
      }

      /**
       * Checks a user's membership status against the forums 
       * and assigns ServerGroups accordingly.
       */
      private void checkMembership() {
         String userGroup = "";
         String clientUid = event.getInvokerUniqueId();
         try {
            String webUid = URLEncoder.encode(clientUid, "UTF-8");
            String urlWithId = "http://www.foundinaction.com/ts3botuidchecker.php?uid=" + webUid;
            URL url = new URL(urlWithId);
            URLConnection conn = url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String inputLine;

            while ((inputLine = br.readLine()) != null) {
               userGroup = inputLine;
            }
            br.close();
            
            assignGroups(userGroup);
         } catch (MalformedURLException e) {
            e.printStackTrace();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }

      /**
       * Prints a formatted summary of the event to the console.
       */
      private void logToConsole() { 
         final String channelName = api.getChannelInfo(event.getClientTargetId()).getName();
         
         String logMessage = Util.timeStamp() + "[CONNECTION] " 
               + event.getClientNickname()
               + " (" + event.getUniqueClientIdentifier() + ") "
               + "connected to \"" + channelName + "\"";
         
         System.out.println(logMessage);
      }
      
      /**
       * Assigns ServerGroups based on the given usergroup.
       * 
       * @param userGroup  the usergroup being used to determine ServerGroups.
       */
      private void assignGroups(String userGroup) {
         if (userGroup != "") {  //Check for empty.
            if (userGroup.startsWith("registered")) { //For users who default to Registered. 
               /*    ASSIGN TO
                *       registeredGroupId
                *    ASSIGN TO EXTRA
                *       none
                *    REMOVE FROM
                *       communityMemberGroupId
                *       divisionManagerGroupId
                *       communityManagerGroupId
                *       seniorCommunityManagerGroupId
                *       founderGroupId
                */
               if (!isGroupMember(Config.registeredGroupId)) {
                  api.addClientToServerGroup(Config.registeredGroupId, clientInfo.getDatabaseId());
               }
               
               removeIfMemberOf(Config.communityMemberGroupId);
               removeIfMemberOf(Config.divisionManagerGroupId);
               removeIfMemberOf(Config.communityManagerGroupId);
               removeIfMemberOf(Config.seniorCommunityManagerGroupId);
               removeIfMemberOf(Config.founderGroupId);
            }
            else if (userGroup.startsWith("community member")) { //For users who default to Community Member
               /*    ASSIGN TO
                *       communityMemberGroupId
                *    ASSIGN TO EXTRA
                *       none
                *    REMOVE FROM
                *       registeredGroupId
                *       divisionManagerGroupId
                *       communityManagerGroupId
                *       seniorCommunityManagerGroupId
                *       founderGroupId
                */

               if (!isGroupMember(Config.communityMemberGroupId)) {
                  api.addClientToServerGroup(Config.communityMemberGroupId, clientInfo.getDatabaseId());
               }
               
               removeIfMemberOf(Config.registeredGroupId);
               removeIfMemberOf(Config.divisionManagerGroupId);
               removeIfMemberOf(Config.communityManagerGroupId);
               removeIfMemberOf(Config.seniorCommunityManagerGroupId);
               removeIfMemberOf(Config.founderGroupId);
            }
            else if (userGroup.startsWith("division manager")) { //For users who default to Division Manager
               /*    ASSIGN TO
                *       divisionManagerGroupId
                *    ASSIGN TO EXTRA
                *       communityMemberGroupId
                *    REMOVE FROM
                *       registeredGroupId
                *       communityManagerGroupId
                *       seniorCommunityManagerGroupId
                *       founderGroupId
                */

               if (!isGroupMember(Config.divisionManagerGroupId)) {
                  api.addClientToServerGroup(Config.divisionManagerGroupId, clientInfo.getDatabaseId());
               }

               if (!isGroupMember(Config.communityMemberGroupId)) {
                  api.addClientToServerGroup(Config.communityMemberGroupId, clientInfo.getDatabaseId());
               }

               removeIfMemberOf(Config.registeredGroupId);
               removeIfMemberOf(Config.communityManagerGroupId);
               removeIfMemberOf(Config.seniorCommunityManagerGroupId);
               removeIfMemberOf(Config.founderGroupId);
            }
            else if (userGroup.startsWith("community manager")) { //For users who default to a CM role.
               /*    ASSIGN TO
                *       communityManagerGroupId
                *    ASSIGN TO EXTRA
                *       communityMemberGroupId
                *    REMOVE FROM
                *       registeredGroupId
                *       divisionManagerGroupId
                *       seniorCommunityManagerGroupId
                *       founderGroupId
                */

               if (!isGroupMember(Config.communityManagerGroupId)) {
                  api.addClientToServerGroup(Config.communityManagerGroupId, clientInfo.getDatabaseId());
               }

               if (!isGroupMember(Config.communityMemberGroupId)) {
                  api.addClientToServerGroup(Config.communityMemberGroupId, clientInfo.getDatabaseId());
               }

               removeIfMemberOf(Config.registeredGroupId);
               removeIfMemberOf(Config.divisionManagerGroupId);
               removeIfMemberOf(Config.seniorCommunityManagerGroupId);
               removeIfMemberOf(Config.founderGroupId);
            }
            else if (userGroup.startsWith("senior community manager")) { //For users who default to a SCM role.
               /*    ASSIGN TO
                *       seniorCommunityManagerGroupId
                *    ASSIGN TO EXTRA
                *       communityMemberGroupId
                *    REMOVE FROM
                *       registeredGroupId
                *       divisionManagerGroupId
                *       communityManagerGroupId
                *       founderGroupId
                */

               if (!isGroupMember(Config.seniorCommunityManagerGroupId)) {
                  api.addClientToServerGroup(Config.seniorCommunityManagerGroupId, clientInfo.getDatabaseId());
               }
               
               if (!isGroupMember(Config.communityMemberGroupId)) {
                  api.addClientToServerGroup(Config.communityMemberGroupId, clientInfo.getDatabaseId());
               }
              
               removeIfMemberOf(Config.registeredGroupId);
               removeIfMemberOf(Config.divisionManagerGroupId);
               removeIfMemberOf(Config.communityManagerGroupId);
               removeIfMemberOf(Config.founderGroupId);
            }
            else if (userGroup.startsWith("founder")) { //For users who default to a founder role.
               /*    ASSIGN TO
                *       founderGroupId
                *    ASSIGN TO EXTRA
                *       communityMemberGroupId
                *    REMOVE FROM
                *       registeredGroupId
                *       divisionManagerGroupId
                *       communityManagerGroupId
                *       seniorCommunityManagerGroupId
                */
               
               if (!isGroupMember(Config.founderGroupId)) {
                  api.addClientToServerGroup(Config.founderGroupId, clientInfo.getDatabaseId());
               }
               
               if (!isGroupMember(Config.communityMemberGroupId)) {
                  api.addClientToServerGroup(Config.communityMemberGroupId, clientInfo.getDatabaseId());
               }
               
               removeIfMemberOf(Config.registeredGroupId);
               removeIfMemberOf(Config.divisionManagerGroupId);
               removeIfMemberOf(Config.communityManagerGroupId);
               removeIfMemberOf(Config.seniorCommunityManagerGroupId);
            }
         } else { //Not a member; remove from all groups.
            System.out.println(Util.timeStamp() + "[PERMCHECK] " + client.getNickname() + " is not registered on the forums.");
            
            removeIfMemberOf(Config.registeredGroupId);
            removeIfMemberOf(Config.communityMemberGroupId);
            removeIfMemberOf(Config.divisionManagerGroupId);
            removeIfMemberOf(Config.communityManagerGroupId);
            removeIfMemberOf(Config.seniorCommunityManagerGroupId);
            removeIfMemberOf(Config.founderGroupId);
         }
      }
      
      /**
       * Determines if the client is a member of the given ServerGroup.
       * 
       * @param groupId  the id of the group in question.
       * @return {@code true} if the user is a member of the group.
       */
      private boolean isGroupMember(Integer groupId) {
         for (ServerGroup serverGroup : serverGroups)
         {
            if (serverGroup.getId() == groupId) {
               return true;
            }
         }
         return false;
      }
      
      /**
       * Checks if a client is a member of a group, and removes them if so. 
       * If they are not a member of the group, nothing is changed.
       * 
       * @param groupId  the group the client will be removed from if they 
       * are a member of it.
       */
      private void removeIfMemberOf(Integer groupId) {
         if (isGroupMember(groupId)) {
            api.removeClientFromServerGroup(groupId, clientInfo.getDatabaseId());
         }
      }
   }