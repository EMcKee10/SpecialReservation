package io.github.emckee10.specialreservation;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    if (sender instanceof Player) {
      Player player = (Player) sender;
    
      if (command.getName().equalsIgnoreCase("NumOfReservedSlots")) {
        if (args.length == 0 || args.length > 1) {
          sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.numofreservedslots.usage")));
        }
        else {
          if (SRUtil.isInteger(args[0])) {
            sr.setSlots(SRUtil.parseInteger(args[0]));
          }
        }
      }
      else if (command.getName().equalsIgnoreCase("ReserveSlotsToggle")) {
        if (args.length == 0) {
          sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
        }
        else {
          if (args[0].equalsIgnoreCase("true")) {
          
          }
          else if (args[0].equalsIgnoreCase("false")) {
  
          }
          else {
            sender.sendMessage(SRUtil.color(sr.getConfig().getString("commands.reserveslotstoggle.usage")));
          }
        }
      
      
      }
    }
    return false;
  }
  
  
}


