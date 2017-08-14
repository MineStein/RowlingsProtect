package com.aurelia_network.rowlingsprotect.command;

import com.aurelia_network.rowlingsprotect.RowlingsProtectPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

/**
 * Copyright Tyler Grissom 2017
 */
public class RowlingsProtectTabCompleter implements TabCompleter {

    private RowlingsProtectPlugin plugin;

    public RowlingsProtectPlugin getPlugin() {
        return plugin;
    }

    public RowlingsProtectTabCompleter(RowlingsProtectPlugin plugin) {
        this.plugin = plugin;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 0) {
            return Collections.singletonList("reload");
        }

        return null;
    }
}