package net.nekcraft.ghosthunt.techtest;

import net.nekcraft.ghosthunt.Ghosthunt;
import net.nekcraft.ghosthunt.techtest.functions.RayTracePlayer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.function.Predicate;

public class PlayerMove implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if(!Ghosthunt.rayTrace.contains(p))  {
			Ghosthunt.rayTrace.add(p);
			RayTracePlayer rayTracePlayer = new RayTracePlayer(p);
			rayTracePlayer.run();
		}
	}
}
