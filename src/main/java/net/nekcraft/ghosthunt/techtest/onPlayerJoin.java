package net.nekcraft.ghosthunt.techtest;

import net.nekcraft.ghosthunt.Ghosthunt;
import net.nekcraft.ghosthunt.techtest.functions.RayTracePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class onPlayerJoin implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		Ghosthunt.rayTrace.add(p);
		RayTracePlayer rayTracePlayer = new RayTracePlayer(p);
		rayTracePlayer.run();
	}
}
