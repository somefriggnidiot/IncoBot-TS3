package main.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import main.util.MessageHandler;

/**
 * Utility class for managing reading from and writing to configuration files.
 */
public class ConfigHandler {

   /**
    * Reads a YAML file and maps the contents to a {@link ConnectionConfiguration} object.
    *
    * @param file the path of the YAML file.
    * @return a {@link ConnectionConfiguration} object with the contents of the file, or null if the
    * file cannot be read.
    */
   public static ConnectionConfiguration readConnectionConfig(final File file) {
      final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      try {
         return mapper.readValue(file, ConnectionConfiguration.class);
      } catch (IOException e) {
         new MessageHandler("Could not read file: \"/config/ConnectionConfig.yaml\"")
             .sendToConsoleWith(Level.SEVERE);
         return null;
      }
   }

   /**
    * Reads a YAML file and maps the contents to a {@link IdleCheckConfiguration} object.
    *
    * @param file the path of the YAML file.
    * @return a {@link IdleCheckConfiguration} object with the contents of the file, or null if the
    * file cannot be read.
    */
   public static IdleCheckConfiguration readIdleCheckConfig(final File file) {
      final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      try {
         return mapper.readValue(file, IdleCheckConfiguration.class);
      } catch (IOException e) {
         new MessageHandler("Could not read file: \"/config/IdleChecker.yaml\"")
             .sendToConsoleWith(Level.SEVERE);
         return null;
      }
   }

   /**
    * Reads a YAML file and maps the contents to a {@link ServerGroupAccessConfiguration} object.
    *
    * @param file the path of the YAML file.
    * @return a {@link ConnectionConfiguration} object with the contents of the file, or null if the
    * file cannot be read.
    */
   public ServerGroupAccessConfiguration readServerGroupAccessConfig(final File file) {
      final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

      try {
         return mapper.readValue(file, ServerGroupAccessConfiguration.class);
      } catch (IOException e) {
         new MessageHandler("Could not read file: \"/config/ServerGroupAccess.yaml\"")
             .sendToConsoleWith(Level.SEVERE);
         return null;
      }
   }
}
