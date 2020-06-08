package fr.auretechno.spawn;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum MenuItems {
	
	COMPASS(Material.COMPASS, "§7>> §6Menu §7<<"), 
	ELYTRA(Material.SLIME_BLOCK, "§l§aMap custom"),
	GLASS_AIR(Material.GRAY_STAINED_GLASS_PANE, " "), 
	OBSIKABRAIN(Material.OBSIDIAN, "§5Hikabrain"),
	LGMC(Material.PAPER, "§cLoup-§8Garou");
	
	private Material material;
	private String name;
	
	private MenuItems(Material m, String n) {
		this.material = m;
		this.name = n;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public String getName() {
		return name;
	}
	
	public ItemStack getItemStack(){
		ItemStack is = new ItemStack(getMaterial());
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(name);
		is.setItemMeta(im);
		return is;
	}
}