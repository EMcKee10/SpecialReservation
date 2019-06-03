package io.github.emckee10.specialreservation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

class SRUtil
{
  static String color(String msg)
  {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }

  static Integer parseInteger(String arg)
  {
    return Integer.parseInt(arg);
  }

  static boolean isInteger(String test)
  {
    try {
      Integer.parseInt(test);
      return true;
    }
    catch (NumberFormatException ex) {
      return false;
    }
  }

  static boolean hasStaffPermission(Player player)
  {
    return player.hasPermission("special_reservation.staffbypass");
  }

  static boolean hasSpecialPermission(Player player)
  {
    return player.hasPermission("special_reservation.specialbypass");
  }
}
