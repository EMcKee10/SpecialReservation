package io.github.emckee10.specialreservation;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;

public class SR extends JavaPlugin
{
  public boolean Toggle;
  public int slots;
  public int maxPlayers;
  public int regularPlayers;
  public int SpecialPlayers;
  private File slotsFile;
  private FileConfiguration fileConfiguration;
  private Logger logger;
  
  @Override
  public void onEnable()
  {
    Toggle = false;
    maxPlayers = this.getServer().getMaxPlayers();
    regularPlayers = this.getServer().getOnlinePlayers().size();
    for (Player p : this.getServer().getOnlinePlayers()) {
      if (SRListener.hasSpecialPermission(p)) {
        SpecialPlayers++;
      }
    
    }
    createSlotsConfig();
    getServer().getPluginManager().registerEvents(new SRListener(this), this);
    Objects.requireNonNull(this.getCommand("NumOfReservedSlots")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("ReserveSlotsToggle")).setExecutor(new SRExecutor(this));
  }
  
  private void createSlotsConfig()
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
  
  public int getSlots()
  {
    return this.getSlotsConfiguration().getInt("slots.numberofreservedslots");
  }
  
  public void setSlots(int slots)
  {
    this.getSlotsConfiguration().set("slots.numberofreservedslots", slots);
    try {
      this.getSlotsConfiguration().save(this.getSlotsFile());
    }
    catch (IOException e) {
      this.logger.severe("An error has occurred while trying to set the number of slots");
    }
  }
  
  public int getMaxPlayers()
  {
    return maxPlayers;
  }
  
  public void setMaxPlayers(int maxPlayers)
  {
    this.maxPlayers = maxPlayers;
  }
  
  public int getRegularPlayers()
  {
    return regularPlayers;
  }
  
  public void setRegularPlayers(int regularPlayers)
  {
    this.regularPlayers = regularPlayers;
  }
  
  public int getSpecialPlayers()
  {
    return SpecialPlayers;
  }
  
  public void setSpecialPlayers(int specialPlayers)
  {
    SpecialPlayers = specialPlayers;
  }
  
  public boolean isToggle()
  {
    return Toggle;
  }
  
  public void setToggle(boolean toggle)
  {
    Toggle = toggle;
  }
  
  public int getLocalSlots()
  {
    return slots;
  }
  
  public void setLocalSlots(int slots)
  {
    this.slots = slots;
  }
  
  public File getSlotsFile()
  {
    return slotsFile;
  }
  
  public FileConfiguration getSlotsConfiguration()
  {
    return fileConfiguration;
  }
  
}
