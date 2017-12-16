package main.util;

public enum MessageMode {
   CONSOLE(1),
   LOG(2),
   RETURN_TO_SENDER(4),
   SEND_TO_CHANNEL(8),
   SEND_TO_SERVER(16);

   private int value;

   MessageMode(int value) {
      this.value = value;
   }
}
