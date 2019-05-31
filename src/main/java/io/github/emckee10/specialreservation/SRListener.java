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
  
  public static boolean hasSpecialPermission(Player player)
  {
    return player.hasPermission("special_reservation.specialbypass");
  }
  
  @EventHandler
  public void onLogin(PlayerLoginEvent event)
  {
    if (sr.isToggle()) {
      if (hasStaffPermission(event.getPlayer())) {
        event.allow();
        //TODO add staff toggle switch
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() >= sr.getSlots() && sr.getRegularPlayers() < getRegularPlayerSlots()) {
        sr.setRegularPlayers(sr.getRegularPlayers() + 1);
        event.allow();
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() < sr.getSlots()) {
        sr.setSpecialPlayers(sr.getSpecialPlayers() + 1);
        event.allow();
      }
      else if (!hasSpecialPermission(event.getPlayer()) && !hasStaffPermission(event.getPlayer()) && sr.getRegularPlayers() < getRegularPlayerSlots()) {
        sr.setRegularPlayers(sr.getRegularPlayers() + 1);
        event.allow();
      }
      else {
        event.setResult(PlayerLoginEvent.Result.KICK_FULL);
      }
      //TODO set full customizable full message, saved in message.yml
    }
  }
  
  @EventHandler
  public void onLogout(PlayerQuitEvent event)
  {
    if (sr.isToggle()) {
      if (hasStaffPermission(event.getPlayer())) {
        //TODO add staff toggle switch
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() > 0) {
        sr.setSpecialPlayers(sr.getSpecialPlayers() - 1);
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() <= 0) {
        sr.setRegularPlayers(sr.getRegularPlayers() - 1);
      }
      else if (!hasSpecialPermission(event.getPlayer()) && !hasStaffPermission(event.getPlayer())) {
        sr.setRegularPlayers(sr.getRegularPlayers() - 1);
      }
    }
  }
  
  private int getRegularPlayerSlots()
  {
    return sr.getMaxPlayers() - sr.getLocalSlots();
  }
  
  private boolean hasStaffPermission(Player player)
  {
    return player.hasPermission("special_reservation.staffbypass");
  }
}
