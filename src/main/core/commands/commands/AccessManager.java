package main.core.commands.commands;

import java.io.File;
import java.util.List;
import main.conf.ConfigHandler;
import main.conf.ServerGroupAccessConfiguration;
import main.util.enums.AccessLevel;

public class AccessManager {
   private ServerGroupAccessConfiguration groupAccessConfig = ConfigHandler
       .readServerGroupAccessConfig(
           new File("./config/ServerGroupAccess.yaml"));
   private AccessLevel requiredLevel;

   /**
    * Creates a new {@link AccessManager} with a given minimum required {@link AccessLevel}.
    *
    * @param requiredLevel the minimum {@link AccessLevel} required by this instance.
    */
   public AccessManager(AccessLevel requiredLevel) {
      this.requiredLevel = requiredLevel;
   }

   /**
    * Determines if the provided {@link AccessLevel} is at least as high as that required by this
    * {@link AccessManager}
    *
    * @param query the {@link AccessLevel} to be compared to the required {@code AccessLevel} of
    * this {@code AccessManager}.
    * @return {@code true} if the provided level is the same as or higher than the required level
    * for this {@code AccessManager}, otherwise {@code false}.
    */
   public boolean hasAccess(AccessLevel query) {
      return query.getValue() >= requiredLevel.getValue();
   }

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
            accessLevel =  AccessLevel.ADMIN;
         } else if (ifApplicable(accessLevel, AccessLevel.MODERATOR, moderatorGroups)
             && moderatorGroups.contains(serverGroup)) {
            accessLevel =  AccessLevel.MODERATOR;
         } else if (ifApplicable(accessLevel, AccessLevel.SPONSOR, sponsorGroups)
             && sponsorGroups.contains(serverGroup)) {
            accessLevel =  AccessLevel.SPONSOR;
         } else if (ifApplicable(accessLevel, AccessLevel.BLACKLISTED, blacklistGroups)
             && blacklistGroups.contains(serverGroup)) {
            accessLevel =  AccessLevel.BLACKLISTED;
         }
      }

      return accessLevel;
   }

   private boolean ifApplicable(AccessLevel currentAccessLevel, AccessLevel checkedAccessLevel,
       List<Integer> groupList) {
      return (groupList != null
          && (currentAccessLevel.getValue() < checkedAccessLevel.getValue()));
   }
}
