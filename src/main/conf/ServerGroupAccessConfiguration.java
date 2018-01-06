package main.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * DTO for server-group access lists.
 */
public class ServerGroupAccessConfiguration {

   @JsonProperty("owner-groups")
   private List<Integer> ownerGroups;
   @JsonProperty("super-admin-groups")
   private List<Integer> superAdminGroups;
   @JsonProperty("admin-groups")
   private List<Integer> adminGroups;
   @JsonProperty("moderator-groups")
   private List<Integer> moderatorGroups;
   @JsonProperty("sponsor-groups")
   private List<Integer> sponsorGroups;
   @JsonProperty("blacklisted-groups")
   private List<Integer> blacklistedGroups;

   /**
    * @return A list of IDs for groups designated as owners.
    */
   @JsonProperty("owner-groups")
   public List<Integer> getOwnerGroups() {
      return ownerGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as owners.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("owner-groups")
   public void addToOwnerGroups(Integer group) {
      if (!ownerGroups.contains(group)) {
         ownerGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as owners.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("owner-groups")
   public void removeFromOwnerGroups(Integer group) {
      if (ownerGroups.contains(group)) {
         ownerGroups.remove(group);
      }
   }

   /**
    * @return A list of IDs for groups designated as super admins.
    */
   @JsonProperty("super-admin-groups")
   public List<Integer> getSuperAdminGroups() {
      return superAdminGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as super admins.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("super-admin-groups")
   public void addToSuperAdminGroups(Integer group) {
      if (!superAdminGroups.contains(group)) {
         superAdminGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as super admins.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("super-admin-groups")
   public void removeFromSuperAdminGroups(Integer group) {
      if (superAdminGroups.contains(group)) {
         superAdminGroups.remove(group);
      }
   }

   /**
    * @return A list of IDs for groups designated as admins.
    */
   @JsonProperty("admin-groups")
   public List<Integer> getAdminGroups() {
      return adminGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as admins.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("admin-groups")
   public void addToAdminGroups(Integer group) {
      if (!adminGroups.contains(group)) {
         adminGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as admins.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("admin-groups")
   public void removeFromAdminGroups(Integer group) {
      if (adminGroups.contains(group)) {
         adminGroups.remove(group);
      }
   }

   /**
    * @return A list of IDs for groups designated as moderators.
    */
   @JsonProperty("moderator-groups")
   public List<Integer> getModeratorGroups() {
      return moderatorGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as moderators.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("moderator-groups")
   public void addToModeratorGroups(Integer group) {
      if (!moderatorGroups.contains(group)) {
         moderatorGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as moderators.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("moderator-groups")
   public void removeFromModeratorGroups(Integer group) {
      if (moderatorGroups.contains(group)) {
         moderatorGroups.remove(group);
      }
   }

   /**
    * @return A list of IDs for groups designated as sponsors.
    */
   @JsonProperty("sponsor-groups")
   public List<Integer> getSponsorGroups() {
      return sponsorGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as sponsors.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("sponsor-groups")
   public void addToSponsorGroups(Integer group) {
      if (!sponsorGroups.contains(group)) {
         sponsorGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as sponsors.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("sponsor-groups")
   public void removeFromSponsorGroups(Integer group) {
      if (sponsorGroups.contains(group)) {
         sponsorGroups.remove(group);
      }
   }

   /**
    * @return A list of IDs for groups designated as blacklisted.
    */
   @JsonProperty("blacklisted-groups")
   public List<Integer> getBlacklistedGroups() {
      return blacklistedGroups;
   }

   /**
    * Adds a group ID to the list of groups designated as blacklisted.
    *
    * @param group the ID of the group being added.
    */
   @JsonProperty("blacklisted-groups")
   public void addToBlacklistedGroups(Integer group) {
      if (!blacklistedGroups.contains(group)) {
         blacklistedGroups.add(group);
      }
   }

   /**
    * Removes a group ID from the list of groups designated as blacklisted.
    *
    * @param group the ID of the group being removed.
    */
   @JsonProperty("blacklisted-groups")
   public void removeFromBlacklistedGroups(Integer group) {
      if (blacklistedGroups.contains(group)) {
         blacklistedGroups.remove(group);
      }
   }
}
