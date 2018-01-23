package main.conf;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IdleCheckConfiguration {
   @JsonProperty("idle-max-time-minutes")
   private int maxTimeMinutes;

   @JsonProperty("idle-destination-channel")
   private int destinationChannel;

   @JsonProperty("idle-ignore-groups")
   private int[] ignoreGroups;

   public int getMaxTimeMinutes() {
      return maxTimeMinutes;
   }

   public void setMaxTimeMinutes(int maxTimeMinutes) {
      this.maxTimeMinutes = maxTimeMinutes;
   }

   public int getDestinationChannel() {
      return destinationChannel;
   }

   public void setDestinationChannel(int destinationChannel) {
      this.destinationChannel = destinationChannel;
   }

   public int[] getIgnoreGroups() {
      return ignoreGroups;
   }

   public void setIgnoreGroups(int[] ignoreGroups) {
      this.ignoreGroups = ignoreGroups;
   }

}
