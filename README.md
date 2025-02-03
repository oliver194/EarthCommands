# EarthCommands Spigot Plugin

## Overview

**EarthCommands**, A plugin designed for my Earth Server. The plugin includes commands for viewing server information, accessing the web map, teleporting to random locations, and more. It also integrates with Vault for managing taxes.

## Features

- **/serverinfo**: Displays basic information about the server.
- **/map**: Provides a link to the server's web map.
- **/about**: Provides information about the server.
- **/rtp**: Teleports the player to a random location within the server world.
- **/taxes**: Triggers the collection of taxes (requires Vault API integration).

## Commands

### `/serverinfo`
- **Description**: Displays basic information about the server, such as the version, software type, processor information, operating system, java version and ram usage.
- **Usage**: `/serverinfo`
- **Permission**: `earthcommands.serverinfo`

### `/map`
- **Description**: Provides a direct link to the server’s web map.
- **Usage**: `/map`
- **Permission**: `earthcommands.map`

### `/about`
- **Description**: Displays information about the server, such as server name, owner, server location, start date.
- **Usage**: `/about`
- **Permission**: `earthcommands.about`

### `/rtp`
- **Description**: Teleports the player to a random location on the server.
- **Usage**: `/rtp`
- **Permission**: `earthcommands.rtp`

### `/taxes`
- **Description**: Triggers the collection of taxes for the server (requires Vault API for functionality).
- **Usage**: `/taxes`
- **Permission**: `earthcommands.taxes`

## Installation

1. Download the EarthCommands plugin `.jar` file.
2. Place the `.jar` file into the `plugins` folder of your Spigot server.
3. Restart the server or run `/reload` to load the plugin. **You may encounter issues when doing /reload, restarting the server is recommended.**
4. After installation, the commands will be available for players with the appropriate permissions.

## Dependencies

- **Vault API**: EarthCommands uses the Vault API to manage the collection of taxes. Please ensure that Vault and an Economy plugin is installed on your server for the taxes feature to function properly.

## Permissions

To use the plugin's features, players need the appropriate permissions:

- **earthcommands.serverinfo**: Access to the `/serverinfo` command.
- **earthcommands.map**: Access to the `/map` command.
- **earthcommands.about**: Access to the `/about` command.
- **earthcommands.rtp**: Access to the `/rtp` command.
- **earthcommands.taxes**: Access to the `/taxes` command.

These permissions can be configured using a permissions plugin (e.g., LuckPerms).

## License

EarthCommands is licensed under the MIT License. You are free to modify, distribute, and use this plugin as long as the original copyright and license notices are retained.

## No Support Provided

Please note that **no official support is provided** for this plugin.