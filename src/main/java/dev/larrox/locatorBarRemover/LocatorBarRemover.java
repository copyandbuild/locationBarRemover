package dev.larrox.locatorBarRemover;

import dev.larrox.locatorBarRemover.cmd.ReloadCMD;
import dev.larrox.locatorBarRemover.util.Gamerules;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class LocatorBarRemover extends JavaPlugin {

    @Override
    public void onEnable() {
        checkServerVersion();
        saveDefaultConfig();
        Gamerules gamerules = new Gamerules(this);
        getCommand("locatorbarremover").setExecutor(new ReloadCMD(this, gamerules));

    }

    private void checkServerVersion() {
        new BukkitRunnable() {
            @Override
            public void run() {
                String bukkitVersion = Bukkit.getBukkitVersion();
                String versionOnly = bukkitVersion.split("-")[0];

                if (!isVersionAtLeast(versionOnly, "1.21.6")) {
                    getLogger().warning("This plugin requires Minecraft version 1.21.6 or higher!");
                    getLogger().warning("Detected version: " + versionOnly);
                    getLogger().warning("Disabling plugin...");
                    Bukkit.getPluginManager().disablePlugin(LocatorBarRemover.this);
                } else {
                    getLogger().info("Running on compatible version: " + versionOnly);
                    Gamerules gamerules = new Gamerules(LocatorBarRemover.this);
                    gamerules.apply();
                }
            }
        }.runTaskLater(this, 20L * 10);
    }

    private boolean isVersionAtLeast(String current, String required) {
        String[] cur = current.split("\\.");
        String[] req = required.split("\\.");

        for (int i = 0; i < Math.max(cur.length, req.length); i++) {
            int curPart = i < cur.length ? Integer.parseInt(cur[i]) : 0;
            int reqPart = i < req.length ? Integer.parseInt(req[i]) : 0;

            if (curPart < reqPart) return false;
            if (curPart > reqPart) return true;
        }
        return true;
    }
}
