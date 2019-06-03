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
    if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL || (event.getResult() == PlayerLoginEvent.Result.ALLOWED))
    {
      if (sr.isToggle())
      {
        if (hasStaffPermission(event.getPlayer()))
        {
          event.allow();
          System.out.println("Staff player entered");
          //TODO add staff toggle switch
        } else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() >= sr.getSlots() && sr.getRegularPlayers() < getRegularPlayerSlots())
        {
          System.out.println("Special player entered regular slot");
          sr.setRegularPlayers(sr.getRegularPlayers() + 1);
          event.allow();
        } else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() < sr.getSlots())
        {
          System.out.println("Special player entered special slot");
          sr.setSpecialPlayers(sr.getSpecialPlayers() + 1);
          event.allow();
        } else if (!hasSpecialPermission(event.getPlayer()) && !hasStaffPermission(event.getPlayer()) && sr.getRegularPlayers() < getRegularPlayerSlots())
        {
          System.out.println("Regular player entered regular slot");
          sr.setRegularPlayers(sr.getRegularPlayers() + 1);
          event.allow();
        } else
        {
          event.disallow(PlayerLoginEvent.Result.KICK_OTHER, SRUtil.color(sr.getMessage()));
        }
      }
    }
  }

  private int getRegularPlayerSlots()
  {
    return sr.getMaxPlayers() - sr.getLocalSlots();
  }

  @EventHandler
  public void onLogout(PlayerQuitEvent event)
  {
    if (sr.isToggle()) {
      if (hasStaffPermission(event.getPlayer())) {
        System.out.println("Staff player exited");
        //TODO add staff toggle switch
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() > 0) {
        System.out.println("Special player left special slot");
        sr.setSpecialPlayers(sr.getSpecialPlayers() - 1);
      }
      else if (hasSpecialPermission(event.getPlayer()) && sr.getSpecialPlayers() <= 0) {
        System.out.println("Special player left regular slot");
        sr.setRegularPlayers(sr.getRegularPlayers() - 1);
      }
      else if (!hasSpecialPermission(event.getPlayer()) && !hasStaffPermission(event.getPlayer())) {
        System.out.println("Regular player left regular slot");
        sr.setRegularPlayers(sr.getRegularPlayers() - 1);

      }
    }
  }
  
  private boolean hasStaffPermission(Player player)
  {
    return player.hasPermission("special_reservation.staffbypass");
  }
}
