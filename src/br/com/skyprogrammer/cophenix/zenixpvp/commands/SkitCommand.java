package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class SkitCommand extends CommandHandler {
	public SkitCommand() {
		super("skit", new String[] { "setkit" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (this.isPlayerSender(commandSender)) {
			final Player localPlayer = this.getPlayerInstance(commandSender);
			if (!localPlayer.hasPermission("pvp.cmd.skit")) {
				this.sendNoPermission(commandSender);
				return true;
			}
			if (commandArgs.length == 0) {
				localPlayer.sendMessage("§c§lSKIT§f Utilize: /skit <distancia>");
				return true;
			}
			try {
				final int integerOfDistance = Integer.valueOf(commandArgs[0]);
				for (final Entity localEntities : localPlayer.getNearbyEntities((double) integerOfDistance,
						(double) integerOfDistance, (double) integerOfDistance)) {
					if (!(localEntities instanceof Player)) {
						continue;
					}
					((Player) localEntities).getInventory().setContents(localPlayer.getInventory().getContents());
					((Player) localEntities).getInventory()
							.setArmorContents(localPlayer.getInventory().getArmorContents());
				}
				localPlayer.sendMessage("§2Kit §fAPLICADO§2 em um raio de §f" + commandArgs[0] + "!");
				return true;
			} catch (NumberFormatException localException) {
				localPlayer.sendMessage("§cUtilize apenas §fNUMEROS§c para indicar a §fDISTANCIA!");
				return true;
			}
		}
		this.sendNotPlayer(commandSender);
		return true;
	}
}
