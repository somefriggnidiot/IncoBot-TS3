# IncoBot TS3
[![Build Status](https://travis-ci.org/somefriggnidiot/IncoBot-TS3.svg?branch=master)](https://travis-ci.org/somefriggnidiot/IncoBot-TS3)  
Interactive console application for TS3 server administration, featuring front-and-back-end operation of most aspects.

# Current Features
Syntax notes:
- Any command with multiple syntaxes will allow any of those listed.
- Parameters with [brackets] are required, and can be any valid value.
- Parameters with <angled_brackets> are required, and can only be one of the values denoted 
within, separated by a pipe. Example: `<on | off>` 
- Parameters with (parentheses) are optional.
- Do not include the brackets or parentheses when calling the command.

## Passive Administration
### Access Control Lists
Grant clients the ability to use certain levels of commands, or prevent them from using commands 
altogether by adding them to a controlled server group. Groups can be used as an access group by 
adding the group ID to the ServerGroupAccessConfiguration.yaml file.

Example Configuration for Groups:
```yaml
owner-groups:
- 10101

super-admin-groups:

admin-groups:
- 10102
- 10103

moderator-groups:

sponsor-groups:
- 10110

blacklisted-groups:
- 10104
- 10105
- 10106
```
  
### Idle Checker Configuration
Establish a set time in minutes before idle users are automatically moved to a designated channel. Users can be exempt from the check by being added to one of the groups listed as an ignore 
group in the IdleChecker.yaml file. IdleChecker must be restarted for config changes to take 
effect if changes are made to the file while it is running.  
For control information, see [Idle Checker](#idle-checker).

Example Configuration for Idle Checker:
```yaml
idle-max-time-minutes: 60
idle-destination-channel: 90210
idle-ignore-groups:
 - 10101
 - 10102
 - 10103
```

## Active Administration
### Idle Checker
Dual-side support for controlling whether or not the bot will check for (and move) idle users.
**Minimum Permission Level:** Admin  
**Syntax:** `!idlechecker <enable | disable>`  
**Use:** Enables or disables the IdleChecker functionality. When enabled, users will be moved to 
the designated idle channel after a certain threshold of time.  
For additional information, see [Idle Checker Configuration](#idle-checker-configuration).  
  
### Kick
Dual-side support for forced disconnects of connected clients.  
**Minimum Permission Level:** Moderator  
**Syntax:** `!kick [clientId] [reason]`  
**Use:** Kicks the client matching the clientId from the server with the provided reason.  
  
## Communication Abilities
### Ping/Pong
Dual-side support for simple bot health-check by having it return a simple phrase when pinged.  
**Minimum Permission Level:** Default  
**Syntax:** `!ping`  
**Use:** Elicits a basic response ("Pong!") from the bot to function as a basic health check or open a line of communication with the query.  

# Upcoming Features
## Passive Administration

## Active Administration
### UserInfo
Dual-side support for listing information pertaining to connected users.

## Communication Abilities

# Long-Term Goals  
## Passive Administration
### Idle Check 
Provide a way to easily manage idle users by either messaging, moving, or kicking after a specified threshold. 
### Passive Role Assignment
Allow the bot to assign roles to users who match certain criteria. More info TBD.

## Active Administration
### Move
Remote and back-end support for moving players between channels.
### Ban
Remote and back-end support for banning players from the server.
### Timeout
Remote and back-end support for temporarily confining players to a channel.

## Communication & Information Dissemination Commands
### Message Server
Remote and back-end support for sending messages to the server.
### Message Channel
Remote and back-end support for sending messages to one or many individual channels.
### Message User
Remote and back-end support for privately messaging a single user.
### Message Users
Remote and back-end support for privately messaging group of users.
### Poke Users
Remote and back-end support for poking a user.
