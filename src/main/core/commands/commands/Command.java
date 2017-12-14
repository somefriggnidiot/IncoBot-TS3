package main.core.commands.commands;


abstract class Command {
   private static String input;

   Command() {}

   public Command(String input) {
      this.input = input;
   }

}
