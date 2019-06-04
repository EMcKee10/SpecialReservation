package io.github.emckee10.specialreservation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SRExecutor implements CommandExecutor
{

  private final SR sr;

  SRExecutor(SR sr)
  {
    this.sr = sr;
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    boolean result;
    switch (command.getName().toLowerCase())
    {
      case "reserve":
        result = changeReservedNum(sender, args);
        break;
      case "rstoggle":
        result = rstoggle(sender, args);
        break;
      case "specialfullmessage":
        result = changeSpecialFullMessage(sender, args);
        break;
      case "regularfullmessage":
        result = changeRegularFullMessage(sender, args);
        break;
      case "specialonline":
        result = specialOnline(sender, args);
        break;
      case "staffonline":
        result = staffOnline(sender, args);
        break;
      case "regularonline":
        result = regularOnline(sender, args);
        break;
      default:
        result = false;
    }
    return result;
  }

  private boolean specialOnline(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      int count = 0;
      StringBuilder name = new StringBuilder();
      for (Player p : sr.getServer().getOnlinePlayers())
      {
        if (SRUtil.hasSpecialPermission(p))
        {
          if (count == 0)
          {
            name.append(p.getName());
          } else
            name.append(", ").append(p.getName());
          count++;
        }
      }
      String message = name.toString().trim();
      sender.sendMessage("There are " + count + " special players");
      if (count != 0)
        sender.sendMessage("These players are [" + message + "]");
      return true;
    } else
      return false;
  }

  private boolean staffOnline(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      int count = 0;
      StringBuilder name = new StringBuilder();
      for (Player p : sr.getServer().getOnlinePlayers())
      {
        if (SRUtil.hasStaffPermission(p))
        {
          if (count == 0)
          {
            name.append(p.getName());
          } else
            name.append(", ").append(p.getName());
          count++;
        }

      }
      String message = name.toString().trim();
      sender.sendMessage("There are " + count + " staff players");
      if (count != 0)
        sender.sendMessage("These players are [" + message + "]");
      return true;
    } else
      return false;
  }

  private boolean regularOnline(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      int count = 0;
      StringBuilder name = new StringBuilder();
      for (Player p : sr.getServer().getOnlinePlayers())
      {
        if (!(SRUtil.hasSpecialPermission(p) || SRUtil.hasStaffPermission(p)))
        {
          if (count == 0)
          {
            name.append(p.getName());
          } else
            name.append(", ").append(p.getName());
          count++;
        }
      }
      String message = name.toString().trim();
      sender.sendMessage("There are " + count + " regular players");
      if (count != 0)
        sender.sendMessage("These players are [" + message + "]");
      return true;
    } else
      return false;
  }

  private boolean changeSpecialFullMessage(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      sender.sendMessage(SRUtil.color("&cInvalid entry. You must include a message with this command"));
      return false;
    } else
    {
      StringBuilder builder = new StringBuilder();
      for (String arg : args)
      {
        builder.append(" ").append(arg);
      }
      String message = builder.toString().trim();
      sr.setSpecialMessage(message);
      sender.sendMessage(SRUtil.color("&2Success! The special full message now will display as: " + message));
      return true;
    }
  }

  private boolean changeRegularFullMessage(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      sender.sendMessage(SRUtil.color("&cInvalid entry. You must include a message with this command"));
      return false;
    } else
    {
      StringBuilder builder = new StringBuilder();
      for (String arg : args)
      {
        builder.append(" ").append(arg);
      }
      String message = builder.toString().trim();
      sr.setRegularMessage(message);
      sender.sendMessage(SRUtil.color("&2Success! The regular full message now will display as: " + message));
      return true;
    }
  }

  private boolean rstoggle(CommandSender sender, String[] args)
  {
    if (args.length == 0)
    {
      sender.sendMessage(SRUtil.color("&cIncorrect command usage, try /ReserveSlotsToggle <true/false>."));
      return true;
    } else
    {
      if (args[0].equalsIgnoreCase("true"))
      {
        if (!sr.isToggle())
        {
          sr.setToggle(true);
          sr.reload();
          sender.sendMessage(SRUtil.color("&2Reserved Slots is now turned on"));
        } else
        {
          sender.sendMessage(SRUtil.color("&eReserved Slots is already on"));
        }
        return true;
      } else if (args[0].equalsIgnoreCase("false"))
      {
        if (sr.isToggle())
        {
          sr.setToggle(false);
          sender.sendMessage(SRUtil.color("&2Reserved Slots is now turned off"));
        } else
        {
          sender.sendMessage(SRUtil.color("&eReserved Slots is already off"));
        }
        return true;
      } else
      {
        sender.sendMessage(SRUtil.color("&cIncorrect command usage, try /ReserveSlotsToggle <true/false>."));
        return true;
      }
    }
  }

  private boolean changeReservedNum(CommandSender sender, String[] args)
  {
    if (args.length != 1)
    {
      sender.sendMessage(SRUtil.color("&cUsage: /<NumOfReservedSlots> [Integer of reserved slots for the server]"));
      return true;
    } else
    {
      if (!SRUtil.isInteger(args[0]))
      {
        sender.sendMessage(SRUtil.color("&cUsage: /<NumOfReservedSlots> [Integer of reserved slots for the server]"));
        return true;
      } else
      {
        if ((SRUtil.parseInteger(args[0]) >= 0))
        {
          if (SRUtil.parseInteger(args[0]) <= sr.getMaxPlayers())
          {
            if (SRUtil.parseInteger(args[0]) == sr.getMaxPlayers())
            {
              sender.sendMessage(SRUtil.color("&cWarning: &eNumber of reserved slots equals the capacity, therefore no regular players will be able to get in."));
            }

            sr.setSlots(SRUtil.parseInteger(args[0]));
            sr.setLocalSlots(sr.getSlots());
            sender.sendMessage(SRUtil.color("&l&2 Success! There are now " + args[0] + " out of " + sr.getMaxPlayers() + " reserved slots on the server!"));

            //TODO if staff toggle is off then count these staff in warning
            if (sr.getRegularPlayers() > 0 || sr.getSpecialPlayers() > 0)
            {
              sender.sendMessage(SRUtil.color("&cWarning: &eServer is not empty, excluding staff. This could negatively effect the way the [Special_Reservations] plugin works. It is suggested, that you expel all non-staff before changing the number of reserved slots."));
            }
            return true;
          } else
          {
            sender.sendMessage(SRUtil.color("&cInvalid option: " + args[0] + " is greater than the capacity. Capacity is set to " + sr.getMaxPlayers()));
            return true;
          }
        } else
        {
          sender.sendMessage(SRUtil.color("&cInvalid option: " + args[0] + " is less than zero. You can not have a negative number of slots"));
          return true;
        }
      }
    }
  }
}