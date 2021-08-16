/*
마인크래프트 게임모드

Ghosthunt(가제)

게임방식

팀 고스트(Ghost)와 팀 헌터(Hunter)로 분류되어 게임이 진행된다.
고스트는 헌터의 인원수에 비례하여 헌터 6명 당 고스트 1명이 배정된다.

고스트는 기본적으로 투명 효과를 가지고 헌터를 모조리 살해하여 게임에서 승리해야하며
헌터는 각종 가젯(장비)의 도움을 받아 보이지 않는 고스트를 탐지하여 고스트를 죽이거나 맵에서 주어진 미션을 달성하여 게임에서 승리해야한다.

시스템
	1. 공포 시스템
		a. 공포 시스템은 헌터에게 적용되며 두려움이 높아질수록 데미지가 낮아지고 화면이 떨리게 된다.
		b. 화면이 떨리는 것은 pitch, yaw값 랜덤 사용
	2. 가젯 시스템
		a. 가젯은 고스트, 헌터에게 모두 적용되며 가젯을 선택하는 수량에는 제한이 되어있다. 각종 효과와 성능을 보여주는 가젯을 이용하여 게임을 진행한다.
	3. 플레이어 이름 태그 제거(숨김)

헌터 팀 가젯 아이디어
	1. 나침반을 이용한 탐지
	2. 사운드를 이용한 탐지 (고스트가 가까워질수록 비프음이 빨라지고 주변 인원들 모두 들을 수 있다.)

고스트 팀 아이디어
	1. 놀리기 사운드 시스템
		a. 헌터 팀 주변에서 고스트가 사용할 수 있는 특유의 사운드를 일부러 재생하여 헌터의 공포를 상승시켜 전투에 유리하게 작용한다.
		b. 공포 상태에 빠진 헌터는 특정 행동(물 마시기, 동료와 함께 있기 등)을 완료하여 공포 수치를 낮춰야만 한다.

 */

package net.nekcraft.ghosthunt.techtest.skill;

import net.nekcraft.ghosthunt.Ghosthunt;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.NotePlayEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;

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