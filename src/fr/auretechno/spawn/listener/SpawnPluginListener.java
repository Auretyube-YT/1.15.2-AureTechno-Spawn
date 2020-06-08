package fr.auretechno.spawn.listener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.auretechno.SpawnMain;
import fr.auretechno.spawn.MenuItems;

public class SpawnPluginListener implements Listener {
	
	private SpawnMain main;
	public Inventory MENU_INV = Bukkit.createInventory(null, 54, "§8Menu");
	
	public SpawnPluginListener(SpawnMain main) {
		this.main = main;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		player.setHealth(20);
		player.setFoodLevel(20);
		event.setJoinMessage("§2[§cAure§eTechno§2] §6Le joueur §l§a" + player.getDisplayName() + " §r§6à rejoint le fucking serveur !");
		
		player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 74.5, 0.5));
		player.playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 1, 1);
		
		if(!main.getBuilders().containsKey(player.getUniqueId())) {
			main.getConfig().createSection(player.getUniqueId().toString());
			main.getConfig().set(player.getUniqueId().toString() + ".builder", false);;
			main.getBuilders().put(player.getUniqueId(), false);
			main.saveConfig();
			player.getInventory().clear();
			player.getInventory().setItem(4, MenuItems.COMPASS.getItemStack());
			
			player.updateInventory();
		} else if(main.getBuilders().containsKey(player.getUniqueId())) {
			if(!main.getBuilders().get(player.getUniqueId())) {
				player.getInventory().clear();
				player.getInventory().setItem(4, MenuItems.COMPASS.getItemStack());
				
				player.updateInventory();
			}
		}
	}
	
	@EventHandler
	public static void onPlayerFoodChange(FoodLevelChangeEvent event) {
		event.setFoodLevel(20);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		event.setDamage(0);
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		if(!main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			main.getConfig().createSection(event.getPlayer().getUniqueId().toString());
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".builder", false);
			main.saveConfig();
			event.setCancelled(true);
		} else if(main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			if(!main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(true);
			} else if(main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack itemStack = event.getItem();
		
		if(action != null)
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK || action == Action.PHYSICAL) {
			if(itemStack.getType() == MenuItems.COMPASS.getMaterial() && itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().equals(MenuItems.COMPASS.getName())) {
				MENU_INV.setItem(10, MenuItems.ELYTRA.getItemStack());
				MENU_INV.setItem(11, MenuItems.OBSIKABRAIN.getItemStack());
				MENU_INV.setItem(12, MenuItems.LGMC.getItemStack());
				for(int i = 0; i < 9; i++) {
					MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
				}
				for(int i = 1; i < 5; i++) {
					MENU_INV.setItem(i * 9, MenuItems.GLASS_AIR.getItemStack());
				}
				for(int i = 1; i < 5; i++) {
					MENU_INV.setItem(i * 9 + 8, MenuItems.GLASS_AIR.getItemStack());
				}
				for(int i = 45; i < MENU_INV.getSize(); i++) {
					MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
				}
				player.openInventory(MENU_INV);
			}
		}
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		event.setFormat("%1$s §7>> §6%2$s");
	}
	
	@EventHandler
	public void onPlayerClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Inventory inv = event.getClickedInventory();
		ItemStack is = event.getCurrentItem();
		
		if(inv != null)
		
		if(!main.getBuilders().containsKey(player.getUniqueId())) {
			main.getConfig().createSection(player.getUniqueId().toString());
			main.getConfig().set(player.getUniqueId().toString() + ".builder", false);
			main.saveConfig();
			event.setCancelled(true);
			if(inv == player.getInventory()) {
				if(is.getType() == Material.COMPASS && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(MenuItems.COMPASS.getName())) {
					player.closeInventory();
					MENU_INV.setItem(10, MenuItems.ELYTRA.getItemStack());
					MENU_INV.setItem(11, MenuItems.OBSIKABRAIN.getItemStack());
					MENU_INV.setItem(12, MenuItems.LGMC.getItemStack());
					for(int i = 0; i < 9; i++) {
						MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
					}
					for(int i = 1; i < 5; i++) {
						MENU_INV.setItem(i * 9, MenuItems.GLASS_AIR.getItemStack());
					}
					for(int i = 1; i < 5; i++) {
						MENU_INV.setItem(i * 9 + 8, MenuItems.GLASS_AIR.getItemStack());
					}
					for(int i = 45; i < MENU_INV.getSize(); i++) {
						MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
					}
					player.openInventory(MENU_INV);
				}
			}
		} else if(main.getBuilders().containsKey(player.getUniqueId())) {
			if(!main.getBuilders().get(player.getUniqueId())) {
				event.setCancelled(true);
				if(inv == player.getInventory()) {
					if(is.getType() == Material.COMPASS && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(MenuItems.COMPASS.getName())) {
						player.closeInventory();
						MENU_INV.setItem(10, MenuItems.ELYTRA.getItemStack());
						MENU_INV.setItem(11, MenuItems.OBSIKABRAIN.getItemStack());
						MENU_INV.setItem(12, MenuItems.LGMC.getItemStack());
						for(int i = 0; i < 9; i++) {
							MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
						}
						for(int i = 1; i < 5; i++) {
							MENU_INV.setItem(i * 9, MenuItems.GLASS_AIR.getItemStack());
						}
						for(int i = 1; i < 5; i++) {
							MENU_INV.setItem(i * 9 + 8, MenuItems.GLASS_AIR.getItemStack());
						}
						for(int i = 45; i < MENU_INV.getSize(); i++) {
							MENU_INV.setItem(i, MenuItems.GLASS_AIR.getItemStack());
						}
						player.openInventory(MENU_INV);
					}
				}
			} else if(main.getBuilders().get(player.getUniqueId())) {
				event.setCancelled(false);
			}
		}
		
		
		if(inv == MENU_INV) {
			event.setCancelled(true);
			if(is.getType() == MenuItems.ELYTRA.getMaterial() && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(MenuItems.ELYTRA.getName())) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				
				try {
					out.writeUTF("Connect");
					out.writeUTF("elytra-parkour");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				player.sendPluginMessage(main, "BungeeCord", b.toByteArray());
			} else if(is.getType() == Material.OBSIDIAN && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(MenuItems.OBSIKABRAIN.getName())) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				
				try {
					out.writeUTF("Connect");
					out.writeUTF("hikabrain");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				player.sendPluginMessage(main, "BungeeCord", b.toByteArray());
			} else if(is.getType() == Material.PAPER && is.hasItemMeta() && is.getItemMeta().getDisplayName().equals(MenuItems.LGMC.getName())) {
				ByteArrayOutputStream b = new ByteArrayOutputStream();
				DataOutputStream out = new DataOutputStream(b);
				
				try {
					out.writeUTF("Connect");
					out.writeUTF("lg");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				player.sendPluginMessage(main, "BungeeCord", b.toByteArray());
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if(!main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			main.getConfig().createSection(event.getPlayer().getUniqueId().toString());
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".builder", false);
			main.saveConfig();
			event.setCancelled(true);
		} else if(main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			if(!main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(true);
			} else if(main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(false);
			}
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockPlaceEvent event) {
		if(!main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			main.getConfig().createSection(event.getPlayer().getUniqueId().toString());
			main.getConfig().set(event.getPlayer().getUniqueId().toString() + ".builder", false);
			main.saveConfig();
			event.setCancelled(true);
		} else if(main.getBuilders().containsKey(event.getPlayer().getUniqueId())) {
			if(!main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(true);
			} else if(main.getBuilders().get(event.getPlayer().getUniqueId())) {
				event.setCancelled(false);
			}
		}
	}
	
}