package main.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Utilities for SFITS3 bot. Contains functions and resources necessary to be
 */
public class Util {

   /**
    * Returns a log-ready time stamp of the format {@code "[yyyy-MM-dd HH:mm:ss] "}
    *
    * @return a formatted string displaying the current system time.
    */
   public static String timeStamp() {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      return (dateFormat.format(System.currentTimeMillis()));
   }
}
