package dev.larrox.locatorBarRemover.util;

import dev.larrox.locatorBarRemover.LocatorBarRemover;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;

import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class Gamerules {

    private final LocatorBarRemover plugin;

    public Gamerules(LocatorBarRemover plugin) {
        this.plugin = plugin;
    }

    public void apply() {
        String gamerule = "locatorBar";
        boolean value = plugin.getConfig().getBoolean("value", false);
        List<String> ignoredWorlds = plugin.getConfig().getStringList("ignored-worlds");

        for (World world : Bukkit.getWorlds()) {
            if (!ignoredWorlds.contains(world.getName())) {
                if (world.isGameRule(gamerule)) {
                    if (!world.getGameRuleValue(GameRule.LOCATOR_BAR)) {
                        getLogger().info("Gamerule " + gamerule + " already set in world: " + world.getName() + ", skipping.");
                        continue;
                    }
                    world.setGameRuleValue(gamerule, Boolean.toString(value));
                    getLogger().info("Set gamerule " + gamerule + "=" + value + " in world: " + world.getName());
                } else {
                    getLogger().warning("World '" + world.getName() + "' does not support gamerule: " + gamerule);
                    Bukkit.getServer().getVersion();
                }
            }
        }
    }

}
