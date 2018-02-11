package main.core.models;

import static com.google.common.base.Preconditions.checkNotNull;

import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import main.core.Executor;

public class User {

   private final String uniqueId;
   private final Integer id;
   private Boolean idleExempt = false;

   /**
    * Creates a base user object with only the required information.
    *
    * @param uniqueId the unique ID of the user.
    * @param id the ID of the connection for the user.
    */
   User(String uniqueId, Integer id) {
      checkNotNull(uniqueId, "uniqueId: null");
      checkNotNull(id, "id: null");

      this.uniqueId = uniqueId;
      this.id = id;
   }

   public ClientInfo getClientInfo() {
      return Executor.getServer("testInstance").getApi().getClientInfo(this.id);
   }

   public String getUniqueId() {
      return uniqueId;
   }

   public Integer getId() {
      return id;
   }

   public Boolean getIdleExempt() {
      return this.idleExempt;
   }

   public void setIdleExempt(Boolean idleExempt) {
      this.idleExempt = idleExempt;
   }


}
