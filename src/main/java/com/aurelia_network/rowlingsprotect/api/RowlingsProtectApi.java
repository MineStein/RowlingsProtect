package com.aurelia_network.rowlingsprotect.api;

import com.aurelia_network.rowlingsprotect.RowlingsProtectPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Copyright Tyler Grissom 2017
 */
public class RowlingsProtectApi {

    private RowlingsProtectPlugin plugin;
    private FileConfiguration config;
    private final String prefix = "§6(§eRowlingsProtect§6) §7";

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    private FileConfiguration getConfig() {
        return config;
    }

    public String getPrefix() {
        return prefix;
    }

    public RowlingsProtectApi(RowlingsProtectPlugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }

    public Permission getAdminPermission() {
        return new Permission(getConfig().getString("admin-permission", "rowlingsprotect.admin"));
    }

    public boolean hasAdminPermission(CommandSender sender) {
        return sender.hasPermission(getAdminPermission());
    }

    public void notifyAdmins(String message, Sound sound) {
        notifyAdmins(message, null, sound);
    }

    public void notifyAdmins(String message, Collection<Player> skip, Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (skip.contains(player)) continue;

            if (hasAdminPermission(player)) {
                player.sendMessage(message);

                if (sound != null) player.playSound(player.getLocation(), sound, 1F, 1F);
            }
        }
    }

    public List<String> getProtectedCommands() {
        List<String> list = new ArrayList<String>();
        List<String> protectedCommands = config.getStringList("protected-commands");

        for (String command : protectedCommands) {
            list.add(command.startsWith("/") ? command : "/" + command);
        }

        return list;
    }

    public boolean isProtectedCommand(String command) {
        List<String> protectedCommands = getProtectedCommands();
        boolean isProtected = false;

        for (String cmd : protectedCommands) {
            if (cmd.startsWith(command)) isProtected = true;
        }

        return isProtected;
    }

    public List<String> getWdlJoinCommands() {
        return plugin.getConfig().getStringList("wdl-join-commands");
    }

    public void executeWdlJoinCommands(OfflinePlayer player) {
        Bukkit.getLogger().warning(player.getName() + " joined with WDL. Beginning command dispatch...");

        List<String> cmds = new ArrayList<String>();

        cmds.add("ban " + player.getName() + " §4§lBanned! §7You joined with a WDL modification.\n\n§3Appeal at §bhttp://aurelia-network.com/banappeal");
        cmds.add("broadcast §cBanned §4" + player.getName() + " §cfor joining with a WDL modification.");

        for (String command : cmds) {
            boolean dispatched = Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);

            if (dispatched) Bukkit.getLogger().info("Dispatched " + command);
            else Bukkit.getLogger().severe("Failed to dispatch " + command);
        }

        notifyAdmins(getPrefix() + "§aSuccessfully caught §2" + player.getName() + " §ausing WDL mods and dispatched commands on them.", Sound.ENTITY_PLAYER_LEVELUP);
    }

    public void reloadConfigs() throws Exception {
        try {
            plugin.saveConfig();
            plugin.reloadConfig();
        } catch (Exception exception) {
            throw new Exception("Failed to reload RowlingsProtect configuration.");
        }
    }
}
