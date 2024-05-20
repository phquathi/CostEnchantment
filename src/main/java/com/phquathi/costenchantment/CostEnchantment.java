package com.phquathi.costenchantment;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.bukkit.enchantments.EnchantmentOffer;

import java.util.HashMap;
import java.util.Map;



public final class CostEnchantment extends JavaPlugin implements @NotNull Listener {

    private final Map<Enchantment, Integer> enchantmentCosts = new HashMap<>();
    private final Map<Enchantment, Integer> anvilEnchantmentCosts = new HashMap<>();


    @Override
    public void onEnable() {
        try {
            this.getCommand("ce").setExecutor(this);
            this.getCommand("costenchantment").setExecutor(this);

            // 加载配置文件
            saveDefaultConfig();
            reloadConfig();
            loadEnchantmentCosts();

            this.getServer().getPluginManager().registerEvents(this, this);

        } catch (Exception e) {
            // 异常处理
            getLogger().severe("启动 CostEnchantment 插件时出错!");
            e.printStackTrace();
            this.getServer().getPluginManager().disablePlugin(this);
        }
        getLogger().info("CostEnchantment插件已经启动! 作者：phquathi");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ce") || cmd.getName().equalsIgnoreCase("costenchantment")) {
            if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
                // 显示帮助
                displayHelp(sender);
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("costenchantment.reload")) {
                    this.reloadConfig();
                    loadEnchantmentCosts(); // 重新加载附魔费用
                    sender.sendMessage(ChatColor.GREEN + "costEnchantment 插件已重载!");
                } else {
                    sender.sendMessage(ChatColor.RED + "你没有权限进行此操作.");
                }
                return true;
            }
            if (args[0].equalsIgnoreCase("set") && args.length == 3) {
                setEnchantmentCost(sender, args);
                return true;
            }
        }
        return false;
    }

    private void displayHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "/ce reload - 重载插件");
        sender.sendMessage(ChatColor.AQUA + "/ce set [附魔] [花费] - 更改附魔费用");
    }

    private void setEnchantmentCost(CommandSender sender, String[] args) {
        if (sender.hasPermission("costenchantment.set")) {
            String enchantName = args[1].toUpperCase();
            int cost;
            try {
                cost = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "无效的经验值!");
                return;
            }

            Enchantment enchantment = Enchantment.getByName(enchantName);
            if (enchantment == null) {
                sender.sendMessage(ChatColor.RED + "无效的附魔名称!");
                return;
            }

            anvilEnchantmentCosts.put(enchantment, cost);
            getConfig().set("anvilEnchantmentCosts." + enchantName, cost);
            saveConfig();

            sender.sendMessage(ChatColor.GREEN + "附魔费用已更新!");
        } else {
            sender.sendMessage(ChatColor.RED + "你没有权限进行此操作.");
        }
    }

    private void loadEnchantmentCosts() {
        if (getConfig().contains("anvilEnchantmentCosts")) {
            for (String key : getConfig().getConfigurationSection("anvilEnchantmentCosts").getKeys(false)) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key.toLowerCase()));
                if (enchantment != null) {
                    anvilEnchantmentCosts.put(enchantment, getConfig().getInt("anvilEnchantmentCosts." + key));
                }
            }
        }
    }


    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        AnvilInventory inv = event.getInventory();
        ItemStack result = inv.getResult();
        double enchantLevel = 0;
        if (result != null) {
            Map<Enchantment, Integer> resultEnchants = result.getEnchantments();
            int extraCost = 0;
            for (Map.Entry<Enchantment, Integer> entry : resultEnchants.entrySet()) {
                Enchantment enchantment = entry.getKey();
                //Bukkit.getServer().getLogger().info("附魔: " + enchantment.getName() + ", 等级: " + level);
                if (anvilEnchantmentCosts.containsKey(enchantment)) {
                    extraCost += anvilEnchantmentCosts.get(enchantment);
                    //Bukkit.getServer().getLogger().info("花费: " + extraCost);
                }
                enchantLevel = entry.getValue();
            }
            double res = enchantLevel/10.0;
            if ( res == 0.1 ) res = 0;
            extraCost *= (1 + res) ;
            if (extraCost > 0) {
                inv.setRepairCost(extraCost/2);
            }
        }
    }


    @Override
    public void onDisable() {
        getLogger().info("CostEnchantment插件已禁用！感谢使用，作者：phquathi");
    }
}
