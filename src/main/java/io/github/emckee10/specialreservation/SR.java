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
  boolean Toggle;
  private int slots;
  private int maxPlayers;
  private int regularPlayers;
  private int SpecialPlayers;
  private File slotsFile;
  private File messageFile;
  private FileConfiguration slotsConfiguration;
  private FileConfiguration messageConfiguration;
  private Logger logger;

  @Override
  public void onEnable()
  {
    Toggle = false;
    maxPlayers = this.getServer().getMaxPlayers();
    regularPlayers = this.getServer().getOnlinePlayers().size();
    reload();

    createConfig();
    getServer().getPluginManager().registerEvents(new SRListener(this), this);
    Objects.requireNonNull(this.getCommand("reserved")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("rstoggle")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("changeFullMessage")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("SpecialOnline")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("StaffOnline")).setExecutor(new SRExecutor(this));
    Objects.requireNonNull(this.getCommand("RegularOnline")).setExecutor(new SRExecutor(this));
  }


  @Override
  public void onDisable()
  {

  }


  @SuppressWarnings("ResultOfMethodCallIgnored")
  private void createConfig()
  {
    slotsFile = new File(this.getDataFolder(), "slots.yml");
    messageFile = new File(this.getDataFolder(), "message.yml");

    if (!slotsFile.exists())
    {
      slotsFile.getParentFile().mkdirs();
      saveResource("slots.yml", false);
    }
    if (!messageFile.exists())
    {
      messageFile.getParentFile().mkdirs();
      saveResource("message.yml", false);
    }

    slotsConfiguration = new YamlConfiguration();
    messageConfiguration = new YamlConfiguration();

    try
    {
      slotsConfiguration.load(slotsFile);
      messageConfiguration.load(messageFile);
    } catch (IOException | InvalidConfigurationException e)
    {
      System.out.println("There was a problem loading your yml files");
      e.printStackTrace();
    }
  }

  int getSlots()
  {
    return this.getSlotsConfiguration().getInt("slots.numberofreservedslots");
  }

  void setSlots(int slots)
  {
    this.getSlotsConfiguration().set("slots.numberofreservedslots", slots);
    try
    {
      this.getSlotsConfiguration().save(this.getSlotsFile());
    } catch (IOException e)
    {
      this.logger.severe("An error has occurred while trying to set the number of slots");
    }
  }

  String getMessage()
  {
    return this.getMessageConfiguration().getString("message.full");
  }

  void setMessage(String message)
  {
    this.getMessageConfiguration().set("message.full", message);
    try
    {
      this.getMessageConfiguration().save(this.getMessageFile());
    } catch (IOException e)
    {
      this.logger.severe("An error has occurred while trying to set the full message.");
    }
  }

  int getMaxPlayers()
  {
    return maxPlayers;
  }

  int getRegularPlayers()
  {
    return regularPlayers;
  }

  void setRegularPlayers(int regularPlayers)
  {
    this.regularPlayers = regularPlayers;
  }

  int getSpecialPlayers()
  {
    return SpecialPlayers;
  }

  void setSpecialPlayers(int specialPlayers)
  {
    SpecialPlayers = specialPlayers;
  }

  boolean isToggle()
  {
    return Toggle;
  }

  void setToggle(boolean toggle)
  {
    Toggle = toggle;
  }

  int getLocalSlots()
  {
    return slots;
  }

  void setLocalSlots(int slots)
  {
    this.slots = slots;
  }

  private File getMessageFile()
  {
    return this.messageFile;
  }

  private FileConfiguration getMessageConfiguration()
  {
    return this.messageConfiguration;
  }

  private File getSlotsFile()
  {
    return slotsFile;
  }

  private FileConfiguration getSlotsConfiguration()
  {
    return slotsConfiguration;
  }

  void reload()
  {
    setRegularPlayers(0);
    setSpecialPlayers(0);
    for (Player p : this.getServer().getOnlinePlayers())
    {
      if (!SRUtil.hasStaffPermission(p))
      {
        if (SRUtil.hasSpecialPermission(p))
          setSpecialPlayers(getSpecialPlayers() + 1);
        else
          setRegularPlayers(getRegularPlayers());
      }

    }
  }

}
