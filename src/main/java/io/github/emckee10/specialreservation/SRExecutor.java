package io.github.emckee10.specialreservation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SRExecutor implements CommandExecutor
{
  
  public final SR sr;
  
  public SRExecutor(SR sr)
  {
    this.sr = sr;
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    boolean result;
    switch (command.getName().toLowerCase())
    {
      case "reserved":
        result = changeReservedNum(sender, args);
        break;
      case "rstoggle":
        result = rstoggle(sender, args);
        break;
      case "changefullmessage":
        result = changeFullMessage(sender, args);
        break;
      default:
        result = false;
    }
    return result;
  }

  private boolean changeFullMessage(CommandSender sender, String[] args)
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
      sr.setMessage(message);
      sender.sendMessage(SRUtil.color("&2Success! The message you display when full is: " + message));
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
        if (!sr.Toggle)
        {
          if (sr.getRegularPlayers() > 0 || sr.getSpecialPlayers() > 0)
          {
            sender.sendMessage(SRUtil.color("&cServer is not empty (op/staff not included in this), clear all regular and special players before turning [Special Reservations] on"));
          } else
          {
            sr.setToggle(true);
            sender.sendMessage(SRUtil.color("&2Reserved Slots is now turned on"));
          }
        } else
        {
          sender.sendMessage(SRUtil.color("&eReserved Slots is already on"));
        }
        return true;
      } else if (args[0].equalsIgnoreCase("false"))
      {
        if (sr.Toggle)
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

    if (args.length == 0 || args.length > 1)
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
            sender.sendMessage(SRUtil.color("&cWarning: &eServer is not empty, this could effect the way the [Special_Reservations] plugin works."));
          }
          return true;
        } else
        {
          sender.sendMessage(SRUtil.color("&cInvalid option. " + args[0] + " is greater than the capacity. Capacity is set to " + sr.getMaxPlayers()));
          return true;
        }
      }
    }

  }
}