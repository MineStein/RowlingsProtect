package com.aurelia_network.rowlingsprotect.listener;

import com.aurelia_network.rowlingsprotect.RowlingsProtectPlugin;
import com.aurelia_network.rowlingsprotect.api.RowlingsProtectApi;
import org.bukkit.entity.Player;

/**
 * Copyright Tyler Grissom 2017
 */
public class PluginMessageListener implements org.bukkit.plugin.messaging.PluginMessageListener {

    private RowlingsProtectPlugin plugin;

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    public PluginMessageListener(RowlingsProtectPlugin plugin) {
        this.plugin = plugin;
    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if ((channel.equals("WDL|INIT")) || (channel.contains("WLD")) || (channel.equals("WDL|CONTROL")) || (channel.contains("REQUEST"))) {
            RowlingsProtectApi api = plugin.getApi();

            api.executeWdlJoinCommands(player);
        }
    }
}
