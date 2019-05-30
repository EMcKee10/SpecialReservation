package io.github.emckee10.specialreservation;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SRListener implements Listener
{
  private final SR sr;
  
  public SRListener(SR sr)
  {
    this.sr = sr;
  }
  
  @EventHandler
  public void onLogin(PlayerLoginEvent event)
  {
    if (sr.isToggle()) {
      if (hasStaffPermission(event.getPlayer())) {
        //do nothing
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() >= sr.getSlots()) {
        sr.setRegularPlayers(sr.getRegularPlayers() + 1);
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() < sr.getSlots()) {
        sr.setSpecialPlayers(sr.getSpecialPlayers() + 1);
      }
      else if (!hasSpecialPermission(event.getPlayer()) && !hasStaffPermission(event.getPlayer()) && sr.getRegularPlayers() < getRegularPlayers()) {
        sr.setRegularPlayers(sr.getRegularPlayers() + 1);
      }
    
    
      if (hasStaffPermission(event.getPlayer())) {
        event.allow();
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() <= sr.getLocalSlots()) {
        event.allow();
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getRegularPlayers() < getRegularPlayers()) {
        event.allow();
      }
      else if (sr.getRegularPlayers() < getRegularPlayers()) {
        event.allow();
      }
    }
  }
  
  @EventHandler
  public void onLogout(PlayerQuitEvent event)
  {
    if (event.getPlayer().hasPermission("special_reservation.specialbypass")) {
      sr.setSpecialPlayers(sr.getSpecialPlayers() - 1);
    }
    else if (event.getPlayer().hasPermission("special_reservation.staffbypass")) {
      return;
    }
    else {
      sr.setRegularPlayers(sr.getRegularPlayers() - 1);
    }
    return;
  }
  
  private int getRegularPlayers()
  {
    return sr.getMaxPlayers() - sr.getLocalSlots();
  }
  
  private boolean hasSpecialPermission(Player player)
  {
    return player.hasPermission("special_reservation.specialbypass");
  }
  
  private boolean hasStaffPermission(Player player)
  {
    return player.hasPermission("special_reservation.staffbypass");
  }
}
