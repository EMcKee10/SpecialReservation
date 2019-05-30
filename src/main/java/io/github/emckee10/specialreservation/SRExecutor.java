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
    if (command.getName().equalsIgnoreCase("NumOfReservedSlots")) {
      if (args.length == 0 || args.length > 1) {
        sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.numofreservedslots.usage")));
        return true;
      }
      else {
        if (!SRUtil.isInteger(args[0])) {
          sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.numofreservedslots.usage")));
          return true;
        }
        else {
          sr.setSlots(SRUtil.parseInteger(args[0]));
          sr.setLocalSlots(sr.getSlots());
          return true;
        }
      }
    }
    else if (command.getName().equalsIgnoreCase("ReserveSlotsToggle")) {
      if (args.length == 0) {
        sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
        return true;
      }
      else {
        if (args[0].equalsIgnoreCase("true")) {
          if (!sr.Toggle) {
            sr.setToggle(true);
          }
          return true;
        }
        else if (args[0].equalsIgnoreCase("false")) {
          if (sr.Toggle) {
            sr.setToggle(false);
          }
          return true;
        }
        else {
          sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
          return true;
        }
      }
    }
    return false;
  }
}


