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
		/*
		플레이어가 서버에 들어온 경우 이벤트가 트리거,
		본 메서드는 해당 이벤트가 트리거 된 경우 실행되는 메서드이다.
		 */

		Player p = e.getPlayer();
		//플레이어에 대한 레이트레이싱 검사가 실행되므로 rayTrace 리스트에 플레이어를 집어넣는다.
		Ghosthunt.rayTrace.add(p);
		// RayTracePlayer 인스턴스를 새로 만들어 플레이어를 집어넣고 검사를 실행한다.
		RayTracePlayer rayTracePlayer = new RayTracePlayer(p);
		rayTracePlayer.run();
	}
}
