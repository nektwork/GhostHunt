package net.nekcraft.ghosthunt.techtest.commands;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;

public class ghtest implements TabExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(sender instanceof Player) {
			Player p = ((Player) sender).getPlayer();
			if(args[0].equalsIgnoreCase("invisible")) {
				assert p != null;
				if(p.isInvisible()) {
					p.setInvisible(false);
					p.sendMessage("투명 비활성화");
				} else {
					p.setInvisible(true);
					p.sendMessage("투명 활성화");
				}
			}
			else if(args[0].equalsIgnoreCase("hunterarmor")) {
				assert p != null;
				ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
				ItemStack chestPlate = new ItemStack(Material.LEATHER_CHESTPLATE);
				ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
				ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);

				LeatherArmorMeta leatArmor = (LeatherArmorMeta)helmet.getItemMeta();
				leatArmor.setColor(Color.BLACK);
				helmet.setItemMeta(leatArmor);

				p.getInventory().setHelmet(null);
				p.getInventory().setChestplate(null);
				p.getInventory().setLeggings(null);
				p.getInventory().setBoots(null);

				p.getInventory().setHelmet(helmet);
				p.getInventory().setChestplate(chestPlate);
				p.getInventory().setLeggings(leggings);
				p.getInventory().setBoots(boots);

				p.sendMessage("사냥꾼의 옷을 입었습니다.");

				for(Player target : Bukkit.getOnlinePlayers()) {
					target.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, 0.5f, 0.7f);
				}

			}
		}

		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(sender instanceof Player) {
			Player p = ((Player) sender).getPlayer();
			if(args.length > 0) {
				ArrayList<String> cmdList = new ArrayList<>();
				cmdList.add("invisible");
				cmdList.add("hunterarmor");
				Collections.sort(cmdList);
				return cmdList;
			}
		}
		return null;
	}
}
