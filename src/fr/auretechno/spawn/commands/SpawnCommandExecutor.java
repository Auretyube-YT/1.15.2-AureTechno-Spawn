package fr.auretechno.spawn.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommandExecutor implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(label.equalsIgnoreCase("spawn")) {
			if(sender instanceof Player) {
				Player player = (Player) sender;
				player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 74.5, 0.5));
				return true;
			} else {
				sender.sendMessage("§2[§cAure§eTechno§2] §4Juste un joueur peut executer cette commande !");
				return false;
			}
		}
		return false;
	}

}
