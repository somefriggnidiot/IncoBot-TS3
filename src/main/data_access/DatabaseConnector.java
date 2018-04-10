package main.data_access;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConnector {
   private static String USER_TABLE = "./db/users.odb";
   private EntityManagerFactory managerFactory;
   private EntityManager manager;

   public EntityManager getEntityManager(Table table) {
      if (table == Table.USER) {
         this.managerFactory = Persistence.createEntityManagerFactory(USER_TABLE);
         this.manager = managerFactory.createEntityManager();
      }

      return manager;
   }

   public void close() {
      manager.close();
      managerFactory.close();
   }

   public enum Table {
      USER
   }
}
