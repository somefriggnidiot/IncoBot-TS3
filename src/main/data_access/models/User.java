package main.data_access.models;

import com.github.theholywaffle.teamspeak3.api.wrapper.Client;
import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Data model class for TeamSpeak server users.
 */
@Entity
public class User {
   //Persistent Fields
   @Id @GeneratedValue(strategy = GenerationType.AUTO) private Integer id;
   private Integer tsdbid;
   private String uid;
   private String name;
   private Boolean isOnline;
   private Date createdAt;
   private Date updatedAt;
   private Date lastSeen;

   /**
    * Creates a new {@link User} with the provided TeamSpeak database ID.
    * @param tsDatabaseId the TeamSpeak database ID of the user,
    * retrieved by calling {@link Client#getDatabaseId()}
    */
   User (Integer tsDatabaseId) {
      this.tsdbid = tsDatabaseId;
   }

   /**
    * @return the unique identifier of this {@code User}.
    */
   public Integer getId() {
      return id;
   }

   /**
    * @return the unique identifier of this {@code User} in the TeamSpeak server's database.
    */
   public Integer getTsdbid() {
      return tsdbid;
   }

   /**
    * @return the unique ID of the TeamSpeak client.
    */
   public String getUid() {
      return uid;
   }

   /**
    * @param uid the unique ID of the TeamSpeak client.
    */
   public void setUid(String uid) {
      this.uid = uid;
   }

   /**
    * @return the nickname in use by this TeamSpeak client.
    */
   public String getName() {
      return name;
   }

   /**
    * @param name the nickname in use by this TeamSpeak client.
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @return whether or not this {@code User} is online.
    */
   public Boolean getOnlineStatus() {
      return isOnline;
   }

   /**
    * @param online whether or not this {@code User} is online.
    */
   public void setOnline(Boolean online) {
      isOnline = online;
   }

   /**
    * @return the date this {@code User} was first seen on this server.
    */
   public Date getCreatedAt() {
      return createdAt;
   }

   /**
    * @param createdAt the date this {@code User} was first seen on this server.
    */
   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   /**
    * @return the date of the most recent update to this {@code User}.
    */
   public Date getUpdatedAt() {
      return updatedAt;
   }

   /**
    * @param updatedAt the date of the most recent update to this {@code User}.
    */
   public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
   }

   /**
    * @return the date this {@code User} last connected to the server.
    */
   public Date getLastSeen() {
      return lastSeen;
   }

   /**
    * @param lastSeen the date this {@code User} last connected to the server.
    */
   public void setLastSeen(Date lastSeen) {
      this.lastSeen = lastSeen;
   }
}
