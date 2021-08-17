package net.nekcraft.ghosthunt;

import net.nekcraft.ghosthunt.techtest.PlayerMove;
import net.nekcraft.ghosthunt.techtest.commands.ghtest;
import net.nekcraft.ghosthunt.techtest.onPlayerJoin;
import net.nekcraft.ghosthunt.techtest.skill.UseSkill;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Ghosthunt extends JavaPlugin {

	/*
	rayTrace는 rayTrace 검사가 진행중인 플레이어들의 목록을 저장하는 ArrayList이다.
	해당 플레이어에 대해 rayTrace 검사가 시작되면 해당 리스트에 플레이어를 집어넣어 후에 해당 플레이어가
	rayTrace 리스트 안에 있는지 확인하여 rayTrace 검사가 중복으로 실행되지 않도록 한다.
	static을 추가하면 정적 변수가 된다. 즉 메인 클래스의 인스턴스를 굳이 받아오지 않아도 접근할 수 있다.
	 */
	public static ArrayList<Player> rayTrace = new ArrayList<>();

	@Override
	public void onEnable() {

		registerCommands();
		registerEvents();
	}

	@Override
	public void onDisable() {

	}

	public void registerEvents() {
		getServer().getPluginManager().registerEvents(new UseSkill(), this);
		getServer().getPluginManager().registerEvents(new onPlayerJoin(), this);
		getServer().getPluginManager().registerEvents(new PlayerMove(), this);
	}

	public void registerCommands() {
		this.getCommand("ght").setExecutor(new ghtest());
	}
}
