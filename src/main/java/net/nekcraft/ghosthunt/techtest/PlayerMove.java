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
		/*
		플레이어가 움직인 경우 이벤트가 트리거,
		본 메서드는 해당 이벤트가 트리거 된 경우 실행되는 메서드이다.
		 */

		Player p = e.getPlayer();

		// 만약 플레이어가 움직이고는 있는데, 레이 트레이싱 검사 목록에서 빠진 경우 검사를 진행한다.
		if(!Ghosthunt.rayTrace.contains(p))  {
			Ghosthunt.rayTrace.add(p);
			RayTracePlayer rayTracePlayer = new RayTracePlayer(p);
			rayTracePlayer.run();
		}
	}
}
