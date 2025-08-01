package dev.larrox.locatorBarRemover.cmd;

import dev.larrox.locatorBarRemover.LocatorBarRemover;
import dev.larrox.locatorBarRemover.util.Gamerules;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReloadCMD implements CommandExecutor, TabCompleter {

    private final LocatorBarRemover plugin;
    private final Gamerules gamerules;

    public ReloadCMD(LocatorBarRemover plugin, Gamerules gamerules) {
        this.plugin = plugin;
        this.gamerules = gamerules;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("locatorbarremover.reload")) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        if (args.length == 0) {
            gamerules.apply();
            sender.sendMessage("§aGamerule applied to all applicable worlds.");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage("§aLocatorBarRemover config reloaded.");
            }
            default -> {
                sender.sendMessage("§cUnknown subcommand. Use: /" + label + " reload");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            if ("reload".startsWith(args[0].toLowerCase())) {
                completions.add("reload");
            }
            return completions;
        }
        return Collections.emptyList();
    }
}
