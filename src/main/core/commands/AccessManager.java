package main.core.commands;

import java.io.File;
import java.util.List;
import main.conf.ConfigHandler;
import main.conf.ServerGroupAccessConfiguration;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;

/**
 * Utility class for handling access lists / permissions in commands.
 */
public class AccessManager {

   private ConfigHandler configHandler;
   private ServerGroupAccessConfiguration groupAccessConfig;
   private AccessLevel requiredLevel;

   /**
    * Creates a new {@link AccessManager} with a given minimum required {@link AccessLevel}.
    *
    * @param requiredLevel the minimum {@link AccessLevel} required by this instance.
    */
   public AccessManager(ConfigHandler configHandler, AccessLevel requiredLevel) {
      this.requiredLevel = requiredLevel;
      this.configHandler = configHandler;
      this.groupAccessConfig = configHandler.readServerGroupAccessConfig(
              new File("./config/ServerGroupAccess.yaml"));
   }

   /**
    * Continues execution if the level being checked passes an access check.
    *
    * @param query the {@link AccessLevel} being compared to the minimum acceptable value for
    * this {@link AccessManager}
    * @throws AuthorizationException if the provided level does not pass an authorization check.
    */
   public void checkAccess(AccessLevel query) throws AuthorizationException {
      if (query.getValue() < requiredLevel.getValue()) {
         throw new AuthorizationException(query.toString(), requiredLevel.toString());
      }
   }

   /**
    * Determines the single {@link AccessLevel} to be assigned to a client.
    *
    * @param serverGroups an array of TeamSpeak 3 ServerGroup IDs of groups to which the client
    * belongs.
    * @return the applicable {@link AccessLevel} of the client.
    */
   public AccessLevel getAccessLevel(int[] serverGroups) {
      AccessLevel accessLevel = AccessLevel.DEFAULT;
      List<Integer> ownerGroups = groupAccessConfig.getOwnerGroups();
      List<Integer> superAdminGroups = groupAccessConfig.getSuperAdminGroups();
      List<Integer> adminGroups = groupAccessConfig.getAdminGroups();
      List<Integer> moderatorGroups = groupAccessConfig.getModeratorGroups();
      List<Integer> sponsorGroups = groupAccessConfig.getSponsorGroups();
      List<Integer> blacklistGroups = groupAccessConfig.getBlacklistedGroups();

      for (int serverGroup : serverGroups) {
         if (ownerGroups.contains(serverGroup)) {
            return AccessLevel.OWNER;
         } else if (ifApplicable(accessLevel, AccessLevel.SUPER_ADMIN, superAdminGroups)
             && superAdminGroups.contains(serverGroup)) {
            accessLevel = AccessLevel.SUPER_ADMIN;
         } else if (ifApplicable(accessLevel, AccessLevel.ADMIN, adminGroups)
             && adminGroups.contains(serverGroup)) {
            accessLevel = AccessLevel.ADMIN;
         } else if (ifApplicable(accessLevel, AccessLevel.MODERATOR, moderatorGroups)
             && moderatorGroups.contains(serverGroup)) {
            accessLevel = AccessLevel.MODERATOR;
         } else if (ifApplicable(accessLevel, AccessLevel.SPONSOR, sponsorGroups)
             && sponsorGroups.contains(serverGroup)) {
            accessLevel = AccessLevel.SPONSOR;
         }

         if (ifApplicable(accessLevel, AccessLevel.BLACKLISTED, blacklistGroups)
             && blacklistGroups.contains(serverGroup)) {
            accessLevel = AccessLevel.BLACKLISTED;
         }
      }

      return accessLevel;
   }

   private boolean ifApplicable(AccessLevel currentAccessLevel, AccessLevel checkedAccessLevel,
       List<Integer> groupList) {

      if (groupList == null) {
         return false;
      }

      if (checkedAccessLevel != AccessLevel.BLACKLISTED) {
         return (currentAccessLevel.getValue() < checkedAccessLevel.getValue());
      } else {
         return (currentAccessLevel == AccessLevel.DEFAULT
             || currentAccessLevel == AccessLevel.SPONSOR);
      }
   }
}
