package io.github.emckee10.specialreservation;

import org.bukkit.ChatColor;

public class SRUtil
{
  public static String color(String msg)
  {
    return ChatColor.translateAlternateColorCodes('&', msg);
  }
  
  public static Integer parseInteger(String arg)
  {
    return Integer.parseInt(arg);
  }
  
  public static boolean isInteger(String test)
  {
    try {
      Integer.parseInt(test);
      return true;
    }
    catch (NumberFormatException ex) {
      return false;
    }
  }
}
