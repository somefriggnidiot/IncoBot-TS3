package main.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import java.io.File;
import java.io.IOException;

public class ConfigHandler {

      public static ConnectionConfiguration readConfig(final File file) {
         final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

         try {
            return mapper.readValue(file, ConnectionConfiguration.class);
         } catch (IOException e) {
            e.printStackTrace();
            return null;
         }
      }
}
