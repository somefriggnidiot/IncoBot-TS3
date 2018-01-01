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

   @JsonProperty("owner-groups")
   public List<Integer> getOwnerGroups() {
      return ownerGroups;
   }

   @JsonProperty("owner-groups")
   public void setOwnerGroups(List<Integer> ownerGroups) {
      this.ownerGroups = ownerGroups;
   }

   @JsonProperty("owner-groups")
   public void addToOwnerGroups(Integer group) {
      if(!ownerGroups.contains(group)) {
         ownerGroups.add(group);
      }
   }

   @JsonProperty("owner-groups")
   public void removeFromOwnerGroups(Integer group) {
      if(ownerGroups.contains(group)) {
         ownerGroups.remove(group);
      }
   }

   @JsonProperty("super-admin-groups")
   public List<Integer> getSuperAdminGroups() {
      return superAdminGroups;
   }

   @JsonProperty("super-admin-groups")
   public void setSuperAdminGroups(List<Integer> superAdminGroups) {
      this.superAdminGroups = superAdminGroups;
   }

   @JsonProperty("super-admin-groups")
   public void addToSuperAdminGroups(Integer group) {
      if(!superAdminGroups.contains(group)) {
         superAdminGroups.add(group);
      }
   }

   @JsonProperty("super-admin-groups")
   public void removeFromSuperAdminGroups(Integer group) {
      if(superAdminGroups.contains(group)) {
         superAdminGroups.remove(group);
      }
   }

   @JsonProperty("admin-groups")
   public List<Integer> getAdminGroups() {
      return adminGroups;
   }

   @JsonProperty("admin-groups")
   public void setAdminGroups(List<Integer> adminGroups) {
      this.adminGroups = adminGroups;
   }

   @JsonProperty("admin-groups")
   public void addToAdminGroups(Integer group) {
      if(!adminGroups.contains(group)) {
         adminGroups.add(group);
      }
   }

   @JsonProperty("admin-groups")
   public void removeFromAdminGroups(Integer group) {
      if(adminGroups.contains(group)) {
         adminGroups.remove(group);
      }
   }

   @JsonProperty("moderator-groups")
   public List<Integer> getModeratorGroups() {
      return moderatorGroups;
   }

   @JsonProperty("moderator-groups")
   public void setModeratorGroups(List<Integer> moderatorGroups) {
      this.moderatorGroups = moderatorGroups;
   }

   @JsonProperty("moderator-groups")
   public void addToModeratorGroups(Integer group) {
      if(!moderatorGroups.contains(group)) {
         moderatorGroups.add(group);
      }
   }

   @JsonProperty("moderator-groups")
   public void removeFromModeratorGroups(Integer group) {
      if(moderatorGroups.contains(group)) {
         moderatorGroups.remove(group);
      }
   }

   @JsonProperty("sponsor-groups")
   public List<Integer> getSponsorGroups() {
      return sponsorGroups;
   }

   @JsonProperty("sponsor-groups")
   public void setSponsorGroups(List<Integer> sponsorGroups) {
      this.sponsorGroups = sponsorGroups;
   }

   @JsonProperty("sponsor-groups")
   public void addToSponsorGroups(Integer group) {
      if(!sponsorGroups.contains(group)) {
         sponsorGroups.add(group);
      }
   }

   @JsonProperty("sponsor-groups")
   public void removeFromSponsorGroups(Integer group) {
      if(sponsorGroups.contains(group)) {
         sponsorGroups.remove(group);
      }
   }

   @JsonProperty("blacklisted-groups")
   public List<Integer> getBlacklistedGroups() {
      return blacklistedGroups;
   }

   @JsonProperty("blacklisted-groups")
   public void setBlacklistedGroups(List<Integer> blacklistedGroups) {
      this.blacklistedGroups = blacklistedGroups;
   }

   @JsonProperty("blacklisted-groups")
   public void addToBlacklistedGroups(Integer group) {
      if(!blacklistedGroups.contains(group)) {
         blacklistedGroups.add(group);
      }
   }

   @JsonProperty("blacklisted-groups")
   public void removeFromBlacklistedGroups(Integer group) {
      if(blacklistedGroups.contains(group)) {
         blacklistedGroups.remove(group);
      }
   }
}
