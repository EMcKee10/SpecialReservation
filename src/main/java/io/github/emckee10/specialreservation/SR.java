package io.github.emckee10.specialreservation;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SR extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        createCustomConfig();
    
        getServer().getPluginManager().registerEvents(new SRListener(this), this);
        Objects.requireNonNull(this.getCommand("numofreserved")).setExecutor(new SRExecutor(this));
        
    }
    
    private void createCustomConfig()
    {
    }
    
    @Override
    public void onDisable()
    {
    
    }
}
