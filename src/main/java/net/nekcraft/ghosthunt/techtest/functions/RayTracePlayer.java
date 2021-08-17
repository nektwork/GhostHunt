package net.nekcraft.ghosthunt.techtest.functions;

import net.nekcraft.ghosthunt.Ghosthunt;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;

import java.util.function.Predicate;

public class RayTracePlayer {
	Player p;
	public static Plugin plugin = Ghosthunt.getPlugin(Ghosthunt.class);

	public RayTracePlayer(Player p) {
		this.p = p;
	}

	public void run() {
		new BukkitRunnable(){
			public void run() {
				try {
					Predicate<Entity> check = (entity) -> entity.getType() == EntityType.PLAYER;
					Predicate<Entity> check2 = (entity) -> {
						Player target = (Player) entity;
						return !target.equals(p);
					};
					RayTraceResult res = p.getWorld().rayTraceEntities(p.getEyeLocation(), p.getLocation().getDirection(), 30.0, check.and(check2));
					if(res != null && res.getHitEntity() instanceof Player) {
						Player target = (Player) res.getHitEntity();
						displayInfo(p, target);
					}
				} catch(Exception e) {
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, 0L, 20L);
	}

	private void displayInfo(Player source, Player target) {

		int distance = (int)source.getLocation().toVector().subtract(target.getLocation().toVector()).length();

		source.resetTitle();
		source.sendTitle(" ", ChatColor.GREEN + target.getDisplayName() + ChatColor.DARK_GRAY + "[" + ChatColor.DARK_GREEN + distance + "m" + ChatColor.DARK_GRAY + "][" + ChatColor.GREEN + "건강함" + ChatColor.DARK_GRAY + "][" + ChatColor.RED + "졸도함" + ChatColor.DARK_GRAY + "]", 0, 20, 0);
	}
}
