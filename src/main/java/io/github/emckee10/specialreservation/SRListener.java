package io.github.emckee10.specialreservation;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

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
    if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
      if (event.getPlayer().hasPermission("sr.bypasslimit")) {
        event.allow();
      }
    }
  }
  
}
