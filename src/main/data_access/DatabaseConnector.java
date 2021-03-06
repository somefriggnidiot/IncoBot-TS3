package main.data_access;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Handles connections to the embedded database.
 */
public class DatabaseConnector {

   private static final String USER_TABLE = "./db/users.odb";
   private EntityManagerFactory managerFactory;
   private EntityManager manager;

   /**
    * Generates an {@link EntityManager} for the given {@link Table}
    *
    * @param table the {@code Table} with which a connection should be established.
    * @return an {@code EntityManager} that manages transactions to the provided {@code Table}.
    */
   public EntityManager getEntityManager(Table table) {
      if (table == Table.USER) {
         this.managerFactory = Persistence.createEntityManagerFactory(USER_TABLE);
         this.manager = managerFactory.createEntityManager();
      }

      return manager;
   }

   /**
    * Closes the connection this {@link DatabaseConnector} is utilizing.
    */
   public void close() {
      manager.close();
      managerFactory.close();
   }

   /**
    * Existing database tables in use by this program.
    */
   public enum Table {
      USER
   }
}
