# CostEnchantment

![Paper 1.18.2](https://img.shields.io/badge/Paper-1.18.2-blue) ![Minecraft](https://img.shields.io/badge/Minecraft-yellow) ![JavaPlugin](https://img.shields.io/badge/JavaPlugin-green)
[![GitHub release](https://img.shields.io/github/v/release/phquathi/costenchantment)](https://github.com/phquathi/costenchantment/releases)
## 介绍

基于paper的我的世界服务器插件。CostEnchantment 允许服务器管理员自定义特定**铁砧**附魔的经验值花费。通过此插件，你可以为不同的附魔设定特定的经验花费，从而更好地控制游戏经济。

版本只试过1.18.2，其他版本请自行试验，如遇问题请发issue

## 功能

- 自定义铁砧附魔的经验值花费。
- 通过命令实时修改和重载附魔花费设置。

## 安装

1. 点击下载发行版[![GitHub release](https://img.shields.io/github/v/release/phquathi/costenchantment)](https://github.com/phquathi/costenchantment/releases)
2. 将 JAR 文件放入服务器的 `plugins` 目录中。
3. 启动或重启服务器，插件会自动生成配置文件。

## 配置

插件的配置文件会在首次启动时生成，位于 `plugins/CostEnchantment/config.yml`。即可在该文件中手动设置附魔的经验花费。

```yaml
anvilEnchantmentCosts:
  protection: 5
  sharpness: 10
  efficiency: 8
  # 在此添加更多附魔及其对应的花费
```

## 命令

- `/ce` 或 `/costenchantment` - 主命令，以下为具体子命令：
    - `/ce help` - 显示帮助信息。
    - `/ce reload` - 重载插件配置文件。
    - `/ce set [附魔] [花费]` - 设置特定附魔的经验花费。

### 示例

- 重载插件配置：
  ```
  /ce reload
  ```

- 设置 `sharpness` 附魔的花费为 `15`：
  ```
  /ce set sharpness 15
  ```

## 权限

插件支持以下权限节点：

- `costenchantment.use` - 允许使用 CostEnchantment 插件的命令，默认对所有玩家开放。
- `costenchantment.reload` - 允许重载插件配置，默认仅限 OP。
- `costenchantment.set` - 允许设置附魔花费，默认仅限 OP。

## 事件监听

插件会监听 `PrepareAnvilEvent` 事件，根据配置文件中设定的附魔花费来调整铁砧的修理费用。

## 问题反馈

如在使用过程中遇到任何问题，请通过 GitHub 提交 issue 或联系我。