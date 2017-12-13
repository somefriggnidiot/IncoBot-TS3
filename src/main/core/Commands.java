package main.core;

import static com.google.common.base.Preconditions.*;

import main.conf.ErrorMessages;

public class Commands {
  private static final String prefix = "!";

  public static int handle(final String input) {
    int returnValue = -1;

    validate(input);

    final String command = input.substring(1);

    System.out.println(command.equals("foo") ? "Bar" : "Command not recognized.");

    return returnValue;
  }

  private static void validate(final String input) {
    checkNotNull(input);
    checkArgument(input.startsWith(prefix), String.format(ErrorMessages.COMMAND_PREFIX_NOT_RECOGNIZED, input.substring(0,1)));
  }
}
