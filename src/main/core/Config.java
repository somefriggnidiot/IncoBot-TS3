package main.core;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import main.conf.Configuration;

/**
 * @deprecated Use {@link Configuration}
 */
@Deprecated
public class Config {
   public static final int
       registeredGroupId = 36077,
       communityMemberGroupId = 46495,
       divisionManagerGroupId = 46322,
       communityManagerGroupId = 45994,
       seniorCommunityManagerGroupId = 45995,
       founderGroupId = 45939;
   public static final int
       birthdayGroupId = 46806;
   private static Properties prop = new Properties();
   private static InputStream propInput = null;
   private static OutputStream propOutput = null;

   /*
    *  Standard Config Commands
    */
   public static void loadConfig() {
      try {
         propInput = new FileInputStream("bot.cfg");
         prop.load(propInput);
      } catch (IOException iox) {
         System.out.println("An error was encountered while loading the configuration.");
      } finally {
         if (propInput != null) {
            try {
               propInput.close();
            } catch (IOException iox2) {
               iox2.printStackTrace();
            }
         }
      }
   }

   public static void saveConfig() {
      try {
         propOutput = new FileOutputStream("bot.cfg");
         prop.store(propOutput, null);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static void closeConfig() {
      try {
         propInput.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   public static String getProperty(String propKey) {
      return prop.getProperty(propKey);
   }

   public static String getProperty(String propKey, String defaultValue) {
      return prop.getProperty(propKey, defaultValue);
   }

   public static void setProperty(String propKey, String propValue) {
      prop.setProperty(propKey, propValue);
   }


   /*
    *    Permission Level Fields
    */
   public static int getBotOwnerGroup() {
      return Integer.parseInt(prop.getProperty("bot_owner_group_id"));
   }

   public static int getBotAdminGroup() {
      return Integer.parseInt(prop.getProperty("bot_admin_group_id"));
   }

   public static int getBotBlockedGroup() {
      return Integer.parseInt(prop.getProperty("bot_blocked_group_id"));
   }

   /*
    *    Gui Rememberance Fields
    */
   public static boolean getServerActivityFlag() {
      boolean display = false;
      if (prop.getProperty("gui_show_server_activity", "0").equals("1")) {
         display = true;
      }

      return display;
   }

   public static void setServerActivityFlag(boolean displayServerActivity) {
      if (displayServerActivity) {
         prop.setProperty("gui_show_server_activity", "1");
      } else if (!displayServerActivity) {
         prop.setProperty("gui_show_server_activity", "0");
      }
   }

   public static boolean getChatDisplayFlag() {
      boolean display = false;
      if (prop.getProperty("gui_show_chat_activity", "0").equals("1")) {
         display = true;
      }

      return display;
   }

   public static void setChatDisplayFlag(boolean displayChat) {
      if (displayChat) {
         prop.setProperty("gui_show_chat_activity", "1");
      } else if (!displayChat) {
         prop.setProperty("gui_show_chat_activity", "0");
      }
   }

   public static boolean getChatCommandsFlag() {
      return (prop.getProperty("gui_enable_chat_commands", "0").equals("1"));
   }

   public static void setChatCommandsFlag(boolean enableChatCommands) {
      if (enableChatCommands) {
         prop.setProperty("gui_enable_chat_commands", "1");
      } else if (!enableChatCommands) {
         prop.setProperty("gui_enable_chat_commands", "0");
      }
   }


   /*
    *     ConnectionConfig Fields
    */
   public static String getServerAddress() {
      return prop.getProperty("server_address");
   }

   public static int getServerQueryPort() {
      return Integer.parseInt(prop.getProperty("server_query_port"));
   }

   public static String getServerQueryLoginName() {
      return prop.getProperty("server_query_name");
   }

   public static String getServerQueryLoginPass() {
      return prop.getProperty("server_query_pass");
   }

   public static String getServerBotName() {
      return prop.getProperty("bot_name");
   }

   public static int getServerVirtualServerId() {
      return Integer.parseInt(prop.getProperty("server_virtual_server_id"));
   }

   public static boolean getServerSlowModeFlag() {
      return (prop.getProperty("bot_slow_mode", "1").equals("1"));
   }

   /*
    *    WelcomeMessage Fields
    */
   public static String getNewUserMessage() {
      return prop.getProperty("welcome_new_user", "");
   }

   public static void setNewUserMessage(String message) {
      prop.setProperty("welcome_new_user", message);
   }

   public static String getNormalUserMessage() {
      return prop.getProperty("welcome_normal_user", "");
   }

   public static void setNormalUserMessage(String message) {
      prop.setProperty("welcome_normal_user", message);
   }

   public static String getBotStaffMessage() {
      return prop.getProperty("welcome_bot_staff", "");
   }

   public static void setBotStaffMessage(String message) {
      prop.setProperty("welcome_bot_staff", message);
   }

   public static String getUniversalWelcomeMessage() {
      return prop.getProperty("welcome_all_users", "");
   }

   public static void setUniversalWelcomeMessage(String message) {
      prop.setProperty("welcome_all_users", message);
   }

   /*
    *    IdleChecker Fields
    */
   public static int getIdleIgnoreGroup() {
      return Integer.parseInt(prop.getProperty("idle_ignore_group", "-1"));
   }

   public static void setIdleIgnoreGroup(String idleIgnoreGroup) {
      prop.setProperty("idle_ignore_group", idleIgnoreGroup);
   }

   public static int getMaxIdleTimeMinutes() {
      return Integer.parseInt(prop.getProperty("idle_max_time_minutes", ""));
   }

   public static void setMaxIdleTimeMinutes(String maxIdleTimeMinutes) {
      prop.setProperty("idle_max_time_minutes", maxIdleTimeMinutes);
   }

   public static int getIdleChannelId() {
      return Integer.parseInt(prop.getProperty("idle_channel_id", "-1"));
   }

   public static void setIdleChannelId(String idleChannelId) {
      prop.setProperty("idle_channel_id", idleChannelId);
   }

   public static int getIdleIgnoreChannelId() {
      return Integer.parseInt(prop.getProperty("idle_ignore_channel_id", "-1"));
   }

   public static void setIdleIgnoreChannelId(String idleIgnoreChannelId) {
      prop.setProperty("idle_ignore_channel_id", idleIgnoreChannelId);
   }

   /*
    *     Timeout Fields
    */
   public static int getTimeoutChannelId() {
      return Integer.parseInt(prop.getProperty("timeout_channel_id", "-1"));
   }

   public static void setTimeoutChannelId(String timeoutChannelId) {
      prop.setProperty("timeout_channel_id", timeoutChannelId);
   }

   public static int getTimeoutGroupId() {
      return Integer.parseInt(prop.getProperty("timeout_group_id", "-1"));
   }

   public static void setTimeoutGroupId(String timeoutGroupId) {
      prop.setProperty("timeout_group_id", timeoutGroupId);
   }

   /*
    *    Suspension Fields
    */
   public static int getSuspendedChannelId() {
      return Integer.parseInt(prop.getProperty("suspended_channel_id", "-1"));
   }

   public static void setSuspendedChannelId(String timeoutChannelId) {
      prop.setProperty("suspended_channel_id", timeoutChannelId);
   }

   public static int getSuspendedGroupId() {
      return Integer.parseInt(prop.getProperty("suspended_group_id", "-1"));
   }

   public static void setSuspendedGroupId(String timeoutGroupId) {
      prop.setProperty("suspended_group_id", timeoutGroupId);
   }


   /*
    *    Nanny Mode Fields
    */
   public static boolean getNannyModeFlag() {
      boolean display = false;
      if (prop.getProperty("nanny_mode_on", "0").equals("1")) {
         display = true;
      }

      return display;
   }

   public static void setNannyModeFlag(boolean isActive) {
      if (isActive) {
         prop.setProperty("nanny_mode_on", "1");
      } else if (!isActive) {
         prop.setProperty("nanny_mode_on", "0");
      }
   }
}