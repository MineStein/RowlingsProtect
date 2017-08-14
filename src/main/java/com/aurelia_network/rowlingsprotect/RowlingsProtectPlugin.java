package com.aurelia_network.rowlingsprotect;

import com.aurelia_network.rowlingsprotect.api.RowlingsProtectApi;
import com.aurelia_network.rowlingsprotect.command.RowlingsProtectCommand;
import com.aurelia_network.rowlingsprotect.command.RowlingsProtectTabCompleter;
import com.aurelia_network.rowlingsprotect.listener.CommandListener;
import com.aurelia_network.rowlingsprotect.listener.PluginMessageListener;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright Tyler Grissom 2017
 */
public class RowlingsProtectPlugin extends JavaPlugin {

    private RowlingsProtectPlugin plugin;
    private RowlingsProtectApi api;
    private PluginMessageListener pluginMessageListener;

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    public RowlingsProtectApi getApi() {
        return api;
    }

    @Override
    public void onEnable() {
        plugin = this;
        api = new RowlingsProtectApi(this);
        pluginMessageListener = new PluginMessageListener(this);

        registerPluginMessageListeners();
        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);

        unregisterPluginMessageListeners();
    }

    private void registerPluginMessageListeners() {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "WDL|CONTROL");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "WDL|INIT", pluginMessageListener);
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "WDL|REQUEST", pluginMessageListener);

        Bukkit.getLogger().info("Registered plugin message listeners. WDL protection is enabled!");
    }

    private void unregisterPluginMessageListeners() {
        getServer().getMessenger().unregisterOutgoingPluginChannel(this, "WDL|CONTROL");
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|INIT", pluginMessageListener);
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "WDL|REQUEST", pluginMessageListener);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new CommandListener(this), this);
    }

    private void registerCommands() {
        getCommand("rowlingsprotect").setExecutor(new RowlingsProtectCommand(this));
        getCommand("rowlingsprotect").setTabCompleter(new RowlingsProtectTabCompleter(this));
    }
}
