package net.nekcraft.ghosthunt.techtest;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class TargetName {
	@EventHandler
	public void targetCrosshair(PlayerInteractAtEntityEvent e) {
		Player source = e.getPlayer();


	}
}
