package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.WarpHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;

public class SpawnCommand extends CommandHandler {
	public SpawnCommand() {
		super("spawn", new String[0]);
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (this.isPlayerSender(commandSender)) {
			final Player localPlayer = this.getPlayerInstance(commandSender);
			if (X1WarpListener.firstMatch == localPlayer.getUniqueId()) {
				X1WarpListener.firstMatch = null;
			}
			WarpHandler.Spawn.setItensToPlayer(localPlayer);
			localPlayer.sendMessage("§2Voc\u00ea foi §fTELEPORTADO§2 ao §fSPAWN§2!");
			return true;
		}
		this.sendNotPlayer(commandSender);
		return true;
	}
}
