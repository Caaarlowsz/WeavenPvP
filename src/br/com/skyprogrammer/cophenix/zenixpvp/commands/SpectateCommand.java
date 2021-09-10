package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class SpectateCommand extends CommandHandler {
	public SpectateCommand() {
		super("espectar", new String[] { "spectar", "spec", "follow" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (this.isPlayerSender(commandSender)) {
			final Player localPlayer = this.getPlayerInstance(commandSender);
			if (!localPlayer.hasPermission("pvp.cmd.spectate")) {
				this.sendNoPermission(commandSender);
				return true;
			}
			if (commandArgs.length == 0) {
				localPlayer.sendMessage("§b§lESPECTAR§f Utilize: /espectar <player>");
				return true;
			}
		}
		return false;
	}
}
