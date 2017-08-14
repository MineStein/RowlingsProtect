package com.aurelia_network.rowlingsprotect.listener;

import com.aurelia_network.rowlingsprotect.RowlingsProtectPlugin;
import com.aurelia_network.rowlingsprotect.api.RowlingsProtectApi;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

/**
 * Copyright Tyler Grissom 2017
 */
public class CommandListener implements Listener {

    private RowlingsProtectPlugin plugin;

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    public CommandListener(RowlingsProtectPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        RowlingsProtectApi api = plugin.getApi();
        boolean isProtected = api.isProtectedCommand(event.getMessage());

        if (!player.hasPermission(api.getAdminPermission()) && isProtected) {
            player.sendMessage(api.getPrefix() + "§cYou don't have permission to view that. Admins have been notified.");

            api.notifyAdmins(api.getPrefix() + "§4" + player.getName() + " §cattempted to view unauthorized info. They performed the §4" + event.getMessage() + " §ccommand.", Sound.ENTITY_GHAST_HURT);
        }
    }
}
