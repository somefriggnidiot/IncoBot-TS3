package main.conf;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for idle-checker configuration.
 */
public class IdleCheckConfiguration {

   @JsonProperty("idle-max-time-minutes")
   private Integer maxTimeMinutes;
   @JsonProperty("idle-destination-channel")
   private Integer destinationChannel;
   @JsonProperty("idle-ignore-groups")
   private List<Integer> ignoreGroups;

   /**
    * @return the maximum time in minutes a user may remain idle before being moved.
    */
   @JsonProperty("idle-max-time-minutes")
   public Integer getMaxTimeMinutes() {
      return maxTimeMinutes;
   }

   /**
    * @param maxTimeMinutes the maximum time in minutes a user may remain idle before being moved.
    */
   @JsonProperty("idle-max-time-minutes")
   public void setMaxTimeMinutes(Integer maxTimeMinutes) {
      this.maxTimeMinutes = maxTimeMinutes;
   }

   /**
    * @return the ID of the channel idle users will be moved to.
    */
   @JsonProperty("idle-destination-channel")
   public Integer getDestinationChannel() {
      return destinationChannel;
   }

   /**
    * @param destinationChannel the ID of the channel idle users will be moved to.
    */
   @JsonProperty("idle-destination-channel")
   public void setDestinationChannel(Integer destinationChannel) {
      this.destinationChannel = destinationChannel;
   }

   /**
    * @return a list of group IDs for groups that will be exempt from the idle checker.
    */
   @JsonProperty("idle-ignore-groups")
   public List<Integer> getIgnoreGroups() {
      return ignoreGroups;
   }

   /**
    * @param ignoreGroups a list of group IDs for groups that will be exempt from the idle checker.
    */
   @JsonProperty("idle-ignore-groups")
   public void setIgnoreGroups(List<Integer> ignoreGroups) {
      this.ignoreGroups = new ArrayList<>(ignoreGroups);
   }

   /**
    * @param ignoreGroup a single group ID to add to the list of groups exempt from the idle
    * checker.
    */
   @JsonProperty("idle-ignore-groups")
   public void addIgnoreGroup(Integer ignoreGroup) {
      if (!this.ignoreGroups.contains(ignoreGroup)) {
         ignoreGroups.add(ignoreGroup);
      }
   }

   /**
    * @param ignoreGroup a single group ID to remove from the list of groups exempt from the idle
    * checker.
    */
   @JsonProperty("idle-ignore-groups")
   public void removeIgnoreGroup(Integer ignoreGroup) {
      if (this.ignoreGroups.contains(ignoreGroup)) {
         ignoreGroups.remove(Integer.valueOf(ignoreGroup));
      }
   }

}
