# CostEnchantment

![Paper 1.18.2](https://img.shields.io/badge/Paper-1.18.2-blue) ![Minecraft](https://img.shields.io/badge/Minecraft-yellow) ![JavaPlugin](https://img.shields.io/badge/JavaPlugin-green)
[![GitHub release](https://img.shields.io/github/v/release/phquathi/costenchantment)](https://github.com/phquathi/costenchantment/releases)

[简体中文](README.md) | [English](README_en.md)

## Introduction

CostEnchantment is a Minecraft server plugin based on Paper. This plugin allows server administrators to customize the experience cost for specific **anvil** enchantments. With CostEnchantment, you can set specific experience costs for different enchantments, providing better control over the game economy.

This version has only been tested with 1.18.2. For other versions, please test it yourself. If you encounter any issues, please submit an issue.

## Features

- Customize the experience cost for anvil enchantments.
- Modify and reload enchantment costs in real-time through commands.

## Installation

1. Download the latest release [![GitHub release](https://img.shields.io/github/v/release/phquathi/costenchantment)](https://github.com/phquathi/costenchantment/releases)
2. Place the JAR file into the `plugins` directory of your server.
3. Start or restart your server. The plugin will automatically generate the configuration file.

## Configuration

The plugin’s configuration file will be generated on the first startup and can be found at `plugins/CostEnchantment/config.yml`. You can manually set the experience costs for enchantments in this file.

```yaml
anvilEnchantmentCosts:
  protection: 5
  sharpness: 10
  efficiency: 8
  # Add more enchantments and their corresponding costs here
```

## Commands

- `/ce` or `/costenchantment` - Main command, with the following subcommands:
    - `/ce help` - Displays help information.
    - `/ce reload` - Reloads the plugin configuration file.
    - `/ce set [enchantment] [cost]` - Sets the experience cost for a specific enchantment.

### Examples

- Reload the plugin configuration:
  ```
  /ce reload
  ```

- Set the cost for `sharpness` enchantment to `15`:
  ```
  /ce set sharpness 15
  ```

## Permissions

The plugin supports the following permission nodes:

- `costenchantment.use` - Allows the use of CostEnchantment commands, open to all players by default.
- `costenchantment.reload` - Allows reloading the plugin configuration, default to OPs only.
- `costenchantment.set` - Allows setting enchantment costs, default to OPs only.

## Event Listener

The plugin listens to the `PrepareAnvilEvent` to adjust anvil repair costs based on the enchantment costs set in the configuration file.

## Issue Reporting

If you encounter any problems while using the plugin, please submit an issue on GitHub or contact me directly.