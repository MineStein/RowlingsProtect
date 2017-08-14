package com.aurelia_network.rowlingsprotect.command;

import com.aurelia_network.rowlingsprotect.RowlingsProtectPlugin;
import com.aurelia_network.rowlingsprotect.api.RowlingsProtectApi;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Copyright Tyler Grissom 2017
 */
public class RowlingsProtectCommand implements CommandExecutor {

    private RowlingsProtectPlugin plugin;

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    public RowlingsProtectCommand(RowlingsProtectPlugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        RowlingsProtectApi api = plugin.getApi();

        if (!sender.hasPermission(api.getAdminPermission())) {
            sender.sendMessage(api.getPrefix() + "§cYou don't have permission!");

            return true;
        }

        String pluginInfo = api.getPrefix() + "§7Server is running §6§lRowlingsProtect &7version &e" + plugin.getDescription().getVersion();

        if (args.length == 0) {
            sender.sendMessage(pluginInfo);
        } else {
            if (args[0].equalsIgnoreCase("reload")) {
                try {
                    sender.sendMessage(api.getPrefix() + "§7Attempting to reload configuration...");

                    api.reloadConfigs();

                    sender.sendMessage(api.getPrefix() + "§aConfiguration reloaded.");
                } catch (Exception e) {
                    sender.sendMessage(api.getPrefix() + "§cThere was an error reloading the configuration!");
                }
            } else {
                sender.sendMessage(pluginInfo);
            }
        }

        return true;
    }
}
