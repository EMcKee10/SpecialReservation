package io.github.emckee10.specialreservation;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SRExecutor implements CommandExecutor
{
  public final SR specialReservation;
  
  public SRExecutor(SR sr)
  {
    this.specialReservation = sr;
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
  {
    if (sender instanceof Player) {
      Player player = (Player) sender;
    }
    
    if (command.getName().equalsIgnoreCase("NumOfReserved")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.RED + "Incorrect command usage, try /numofreserverved <Integer>.");
      }
      else {
        
        
      }
    }
    else if (command.getName().equalsIgnoreCase("default")) {
    
      
    }
    
    return false;
  }
}


