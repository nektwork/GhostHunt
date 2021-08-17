package net.nekcraft.ghosthunt;

import net.nekcraft.ghosthunt.techtest.PlayerMove;
import net.nekcraft.ghosthunt.techtest.commands.ghtest;
import net.nekcraft.ghosthunt.techtest.onPlayerJoin;
import net.nekcraft.ghosthunt.techtest.skill.UseSkill;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Ghosthunt extends JavaPlugin {

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
