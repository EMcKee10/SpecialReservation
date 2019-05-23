package io.github.emckee10.specialreservation;

import org.bukkit.plugin.java.JavaPlugin;

public class SR extends JavaPlugin
{
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
    }
    @Override
    public void onDisable() {
        getLogger().info("onDisable is called!");
    }
}
