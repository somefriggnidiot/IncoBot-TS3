package main.core.commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import main.conf.ConfigHandler;
import main.conf.ServerGroupAccessConfiguration;
import main.util.enums.AccessLevel;
import main.util.exception.AuthorizationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
public class AccessManagerTest {

   @Test
   public void checkAccessDoesNothingOnSuccess() throws AuthorizationException {
      AccessManager am = new AccessManager(new ConfigHandler(), AccessLevel.DEFAULT);
      am.checkAccess(AccessLevel.DEFAULT);
   }

   @Test(expected = AuthorizationException.class)
   public void checkAccessThrowsExceptionOnFailure() throws AuthorizationException {
      AccessManager am = new AccessManager(new ConfigHandler(), AccessLevel.MODERATOR);
      am.checkAccess(AccessLevel.DEFAULT);
   }

   @Test
   public void getAccessLevelIgnoresBlacklistForOwners() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(groupList).when(accessConfigMock).getOwnerGroups();
      doReturn(null).when(accessConfigMock).getSuperAdminGroups();
      doReturn(null).when(accessConfigMock).getAdminGroups();
      doReturn(null).when(accessConfigMock).getModeratorGroups();
      doReturn(null).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.OWNER, returnedLevel);
   }

   @Test
   public void getAccessLevelIgnoresBlacklistForSuperAdmins() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> ownerList = new ArrayList<>();
      ownerList.add(0);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(ownerList).when(accessConfigMock).getOwnerGroups();
      doReturn(groupList).when(accessConfigMock).getSuperAdminGroups();
      doReturn(null).when(accessConfigMock).getAdminGroups();
      doReturn(null).when(accessConfigMock).getModeratorGroups();
      doReturn(null).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.SUPER_ADMIN, returnedLevel);
   }

   @Test
   public void getAccessLevelIgnoresBlacklistForAdmins() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> ownerList = new ArrayList<>();
      ownerList.add(0);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(ownerList).when(accessConfigMock).getOwnerGroups();
      doReturn(null).when(accessConfigMock).getSuperAdminGroups();
      doReturn(groupList).when(accessConfigMock).getAdminGroups();
      doReturn(null).when(accessConfigMock).getModeratorGroups();
      doReturn(null).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.ADMIN, returnedLevel);
   }

   @Test
   public void getAccessLevelIgnoresBlacklistForModerators() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> ownerList = new ArrayList<>();
      ownerList.add(0);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(ownerList).when(accessConfigMock).getOwnerGroups();
      doReturn(null).when(accessConfigMock).getSuperAdminGroups();
      doReturn(null).when(accessConfigMock).getAdminGroups();
      doReturn(groupList).when(accessConfigMock).getModeratorGroups();
      doReturn(null).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.MODERATOR, returnedLevel);
   }

   @Test
   public void getAccessLevelAppliesBlacklistForBlacklistedSponsors() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> ownerList = new ArrayList<>();
      ownerList.add(0);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(ownerList).when(accessConfigMock).getOwnerGroups();
      doReturn(null).when(accessConfigMock).getSuperAdminGroups();
      doReturn(null).when(accessConfigMock).getAdminGroups();
      doReturn(null).when(accessConfigMock).getModeratorGroups();
      doReturn(groupList).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.BLACKLISTED, returnedLevel);
   }

   @Test
   public void getAccessLevelAppliesBlacklistForBlacklistedUsers() {
      ConfigHandler configHandlerMock = mock(ConfigHandler.class);
      ServerGroupAccessConfiguration accessConfigMock = mock(ServerGroupAccessConfiguration.class);

      List<Integer> ownerList = new ArrayList<>();
      ownerList.add(0);

      List<Integer> groupList = new ArrayList<>();
      groupList.add(1);

      doReturn(ownerList).when(accessConfigMock).getOwnerGroups();
      doReturn(null).when(accessConfigMock).getSuperAdminGroups();
      doReturn(null).when(accessConfigMock).getAdminGroups();
      doReturn(null).when(accessConfigMock).getModeratorGroups();
      doReturn(null).when(accessConfigMock).getSponsorGroups();
      doReturn(groupList).when(accessConfigMock).getBlacklistedGroups();

      doReturn(accessConfigMock).when(configHandlerMock).readServerGroupAccessConfig(any(File
          .class));

      AccessManager am = new AccessManager(configHandlerMock, AccessLevel.DEFAULT);

      int[] groupArray = {1};
      AccessLevel returnedLevel = am.getAccessLevel(groupArray);

      assertEquals(AccessLevel.BLACKLISTED, returnedLevel);
   }
}
