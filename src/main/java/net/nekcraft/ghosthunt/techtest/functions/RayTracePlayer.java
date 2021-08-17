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
		/*
		 클래스와 이름이 같은 이 메서드는 생성자(Constructor) 라고 부른다.
		 인스턴스와 클래스, 생성자, new와 같은 것들은 아래 링크로 한방에 이해할 수 있다.
		 https://whatisthenext.tistory.com/36
		 */
		this.p = p;
	}

	public void run() {
		new BukkitRunnable(){
			public void run() {
				try {
					/*
					레이 트레이싱은 말 그대로 특정 위치에서 특정 방향으로 레이저를 쏴서 맞는 물체가 있는지 없는지 검사한다.

					레이트레이싱은 다음과 같이 사용한다.
					World.rayTraceEntities(시작 위치, 방향, 최대 거리, 조건식)
					애초에 특정 위치에서 레이저를 쏘는 것이지, 플레이어 또는 플레이어의 시점과는 연관되어 있지 않다.
					이 때문에 레이트레이싱을 조건 없이 사용하면 자기 자신이 감지되거나, 플레이어가 아닌 돼지 같은 엔티티가 감지되는 문제가 있다.
					때문에 조건식을 추가하여 자기 자신을 뺀 플레이어들만 검사하게 할 수 있다.

					Predicate<Entity> 뒤에 붙은 저 괴상하게 생긴 화살표는 lambda식이다.
					저 람다식을 사용하면 간단하게 함수를 변수 안에다 쑤셔넣을 수 있다.
					check2가 람다식으로 함수를 구현한 예이다.
					Predicate<> var = (검사할 대상) -> [검사할 조건]
					즉 check는 검사 대상 entity를 받아와서 PLAYER인지 검사하는 것이라고 볼 수 있다.
					check에서 조건을 통과하면 check2로 넘어간다.
					check2에서는 검사 대상 entity가 PLAYER라는 사실을 알았으므로
					entity를 안심하고 Player로 캐스팅해도 된다.
					다음 레이 트레이싱으로 감지한 대상이 자신인지 확인한다.
					 */
					Predicate<Entity> check = (entity) -> entity.getType() == EntityType.PLAYER;
					Predicate<Entity> check2 = (entity) -> {
						Player target = (Player) entity;
						return !target.equals(p); // 같으면 참, 다르면 거짓, 그러나 앞의 느낌표를 붙여 같지 않으면 참, 같으면 거짓으로 만든다. 즉 같다면 검사 대상에서 제외한다.
					};
					RayTraceResult res = p.getWorld().rayTraceEntities(p.getEyeLocation(), p.getLocation().getDirection(), 30.0, check.and(check2));
					if(res != null && res.getHitEntity() instanceof Player) {
						Player target = (Player) res.getHitEntity();
						displayInfo(p, target);
					}
				} catch(Exception e) {
					// 플레이어가 나갔거나, 모종의 이유로 플레이어의 위치를 가져오지 못하는 등 오류가 발생하면 무조건적으로 검사를 종료한다.\
					// 검사가 끝났으므로 rayTrace 리스트에서 플레이어를 뺀다.
					// this.cancel을 통해 버킷스케쥴러의 무한루프를 끝낼 수 있다.
					Ghosthunt.rayTrace.remove(p);
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
