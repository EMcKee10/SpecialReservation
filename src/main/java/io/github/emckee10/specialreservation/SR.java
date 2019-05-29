package io.github.emckee10.specialreservation;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SR extends JavaPlugin
{
  public boolean Toggle = false;
  public int slots;
  public int maxPlayers;
  private File slotsFile;
  private FileConfiguration fileConfiguration;

    @Override
    public void onEnable()
    {
      maxPlayers = this.getServer().getMaxPlayers();
        createCustomConfig();
        getServer().getPluginManager().registerEvents(new SRListener(this), this);
      Objects.requireNonNull(this.getCommand("NumOfReservedSlots")).setExecutor(new SRExecutor(this));
      Objects.requireNonNull(this.getCommand("ReserveSlotsToggle")).setExecutor(new SRExecutor(this));
        
    }
    
    private void createCustomConfig()
    {
      slotsFile = new File(this.getDataFolder(), "slots.yml");
  
      if (!slotsFile.exists()) {
        slotsFile.getParentFile().mkdirs();
        saveResource("slots.yml", false);
      }
  
      fileConfiguration = new YamlConfiguration();
      try {
        fileConfiguration.load(slotsFile);
      }
      catch (IOException | InvalidConfigurationException e) {
        System.out.println("There was a problem loading your yml file");
        e.printStackTrace();
      }
    }
    
    @Override
    public void onDisable()
    {
    
    }
  
  public int getMaxPlayers()
  {
    return maxPlayers;
  }
  
  public void setMaxPlayers(int maxPlayers)
  {
    this.maxPlayers = maxPlayers;
  }
  
  public boolean isToggle()
  {
    return Toggle;
  }
  
  public void setToggle(boolean toggle)
  {
    Toggle = toggle;
  }
  
  public int getSlots()
  {
    return slots;
  }
  
  public void setSlots(int slots)
  {
    this.slots = slots;
  }
  
  public File getSlotsFile()
  {
    return slotsFile;
  }
  
  public FileConfiguration getFileConfiguration()
  {
    return fileConfiguration;
  }
    
}
