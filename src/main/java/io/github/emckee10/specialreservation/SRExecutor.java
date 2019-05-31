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
        //sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.numofreservedslots.usage")));
        return false;
      }
      else {
        if (!SRUtil.isInteger(args[0])) {
          //sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.numofreservedslots.usage")));
          return false;
        }
        else {
          if (SRUtil.parseInteger(args[0]) <= sr.getMaxPlayers()) {
            if (SRUtil.parseInteger(args[0]) == sr.getMaxPlayers()) {
              sender.sendMessage(SRUtil.color("&cWarning: &eNumber of reserved slots equals the capacity therefor no regular players will be able to get in."));
            }
    
            sr.setSlots(SRUtil.parseInteger(args[0]));
            sr.setLocalSlots(sr.getSlots());
            sender.sendMessage(SRUtil.color("&l&3 Success! There are now " + args[0] + " out of " + sr.getMaxPlayers() + " reserved slots on the server!"));
  
            //TODO if staff toggle is off then count these staff in warning
            if (sr.getRegularPlayers() > 0 || sr.getSpecialPlayers() > 0) {
              sender.sendMessage(SRUtil.color("&cWarning: &eServer is not empty, this could effect the way the [Special_Reservations] plugin works."));
            }
            return true;
          }
          else {
            sender.sendMessage(SRUtil.color("&cInvalid option, " + args[0] + " is greater than the capacity. capacity has been set to " + sr.getMaxPlayers()));
            return true;
          }
        }
      }
    }
    else if (command.getName().equalsIgnoreCase("ReserveSlotsToggle")) {
      if (args.length == 0) {
        //sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
        return false;
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
          //sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
          return false;
        }
      }
    }
    else if (command.getName().equalsIgnoreCase("changeFullMessage")) {
      if (args.length == 0) {
        sender.sendMessage(SRUtil.color("&cInvalid entry, you must include a message with this command"));
        return false;
      }
      else {
        StringBuilder builder = new StringBuilder();
        for (String arg : args) {
          builder.append(" ").append(arg);
        }
        String message = builder.toString().trim();
        sr.setMessage(message);
      }
    }
    return false;
  }
}


