package net.nekcraft.ghosthunt.techtest.skill;

import net.nekcraft.ghosthunt.Ghosthunt;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public class UseSkill implements Listener {
	public static Plugin plugin = Ghosthunt.getPlugin(Ghosthunt.class);

	@EventHandler
	public void useSkill3(PlayerInteractEvent e) {
		//아이템1 테스트
		Player p = e.getPlayer();

		if(e.getItem() != null) {

			Boolean isSlotHand = e.getHand() == EquipmentSlot.HAND;
			Boolean isHandItem = e.getItem().getType().equals(Material.DIRT);
			Boolean isRightAir = e.getAction().equals(Action.RIGHT_CLICK_AIR);
			Boolean isRightBlock = e.getAction().equals(Action.RIGHT_CLICK_BLOCK);

			if (isSlotHand && isHandItem && (isRightAir || isRightBlock)) {
				ghostScream(p);
			}
		}


	}
	private void ghostScream(Player source) {


		Location sourceLocation = source.getLocation();

		for(Player target : Bukkit.getOnlinePlayers()) {
			if(target != source) {
				if(sourceLocation.distance(target.getLocation()) < 5) {
					new BukkitRunnable(){
						int counter = 0;
						double posX, posY, posZ;
						public void run(){
							float randomDir1 = (float) (Math.random() * 50) - 25;
							float randomDir2 = (float) (Math.random() * 50) - 25;
							Location targetLocation = target.getLocation();
							if(counter == 0) {
								posX = targetLocation.getX();
								posY = targetLocation.getY();
								posZ = targetLocation.getZ();
							}
							else {
								targetLocation.setX(posX);
								targetLocation.setY(posY);
								targetLocation.setZ(posZ);
							}
							targetLocation.setPitch(targetLocation.getPitch() + randomDir1);
							targetLocation.setYaw(targetLocation.getYaw() + randomDir2);
							target.teleport(targetLocation);

							float randomSound = (float) (Math.random() * 1.5f) - 0.5f;
							double randomXZ = (Math.random() * 1) - 0.5;
							source.getWorld().playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 0.5f, randomSound);
							source.getWorld().spawnParticle(Particle.WATER_DROP, target.getLocation(), 10, randomXZ, 1.25, randomXZ);

							counter++;
							if(counter > 50) this.cancel();
						}
					}.runTaskTimer(plugin, 0L, 0L);

					// 플레이어가 비명을 지른다
					source.getWorld().playSound(target.getLocation(), Sound.ENTITY_COW_HURT, 1.0f, 1.3f);
				}
			}
		}
	}
}