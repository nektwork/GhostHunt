package net.nekcraft.ghosthunt;

import net.nekcraft.ghosthunt.techtest.skill.UseSkill;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ghosthunt extends JavaPlugin {

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
	}

	public void registerCommands() {
		this.getCommand("ght").setExecutor(new ghtest());
	}
}
