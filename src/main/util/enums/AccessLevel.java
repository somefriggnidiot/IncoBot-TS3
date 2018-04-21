package main.util.enums;

public enum AccessLevel {
   OWNER(100),
   SUPER_ADMIN(90),
   ADMIN(80),
   MODERATOR(70),
   SPONSOR(25),
   DEFAULT(20),
   BLACKLISTED(0);

   private final int value;

   AccessLevel(int value) {
      this.value = value;
   }

   public int getValue() {
      return value;
   }
}
