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
          if (SRUtil.parseInteger(args[0]) <= sr.getMaxPlayers()) {
            if (SRUtil.parseInteger(args[0]) == sr.getMaxPlayers()) {
              sender.sendMessage(SRUtil.color("&cWarning: &eNumber of reserved slots equals the capacity therefor no regular players will be able to get in."));
            }
    
            sr.setSlots(SRUtil.parseInteger(args[0]));
            sr.setLocalSlots(sr.getSlots());
            sender.sendMessage(SRUtil.color("&l&3 Success! There are now " + args[0] + " out of " + sr.getMaxPlayers() + " reserved slots on the server!"));
    
            //TODO if staff toggle is off then cont these staff in warning
            if (sr.getRegularPlayers() > 0 || sr.getSpecialPlayers() > 0) {
              sender.sendMessage(SRUtil.color("&cWarning: &eServer isnt empty, this could effect the way the [Special_Reservations] plugin works."));
            }
    
            return true;
          }
          else {
            sender.sendMessage(SRUtil.color("&cInvalid option, " + args[0] + " is greater than the capacity."));
          }
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


