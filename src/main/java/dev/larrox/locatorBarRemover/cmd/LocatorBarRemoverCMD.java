package dev.larrox.locatorBarRemover.cmd;

import dev.larrox.locatorBarRemover.LocatorBarRemover;
import dev.larrox.locatorBarRemover.util.Gamerules;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocatorBarRemoverCMD implements CommandExecutor, TabCompleter {

    private final LocatorBarRemover plugin;
    private final Gamerules gamerules;
    private final String reloadPermission = "locatorbarremover.reload";

    public LocatorBarRemoverCMD(LocatorBarRemover plugin, Gamerules gamerules) {
        this.plugin = plugin;
        this.gamerules = gamerules;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(reloadPermission)) {
            sender.sendMessage("§cYou don't have permission.");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage("§8[§aLocatorBarRemover§8] §7usage: /locatorbarremover <reload/run/all>");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload" -> {
                plugin.reloadConfig();
                sender.sendMessage("§aLocatorBarRemover config reloaded.");
            }

            case "run" -> {
                gamerules.apply();
                sender.sendMessage("§aLocatorBarRemover gamerules applied.");
            }

            case "all" -> {
                plugin.reloadConfig();
                gamerules.apply();
                sender.sendMessage("§aLocatorBarRemover config reloaded and gamerules applied.");
            }
            default -> {
                sender.sendMessage("§cUnknown subcommand. Use: /" + label + " reload");
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!sender.hasPermission(reloadPermission)) {
            return List.of();
        }

        if (args.length == 1) {
            List<String> options = Arrays.asList("reload", "run", "all");
            String input = args[0].toLowerCase();
            List<String> matches = new ArrayList<>();
            for (String option : options) {
                if (option.startsWith(input)) {
                    matches.add(option);
                }
            }
            return matches;
        }
        return List.of();
    }
}
