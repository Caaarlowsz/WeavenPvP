package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;

public class WarpCommand extends CommandHandler {
	public WarpCommand() {
		super("warp", new String[] { "warps" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (this.isPlayerSender(commandSender)) {
			final Player localPlayer = this.getPlayerInstance(commandSender);
			if (commandArgs.length == 0) {
				localPlayer.sendMessage("�e�lWARP�f Utilize: /warp <nome da warp>");
				return true;
			}
			if (commandArgs.length == 1) {
				if (!WeavenPvP.getManager().getWarps().hasWarp(commandArgs[0])) {
					localPlayer.sendMessage("�cEsta �fWARP�c n\u00e3o foi �fENCONTRADA�c!");
					return true;
				}
				localPlayer.teleport(WeavenPvP.getManager().getWarps().getLocation(commandArgs[0]));
				if (!commandArgs[0].equalsIgnoreCase("fps")) {
					if (commandArgs[0].equalsIgnoreCase("1v1")) {
						final Gamer gamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
						gamer.setWarp("1v1");
						X1WarpListener.loadWarp1v1Methods(localPlayer);
					} else if (!commandArgs[0].equalsIgnoreCase("challenge")) {
						commandArgs[0].equalsIgnoreCase("rdm");
					}
				}
				localPlayer
						.sendMessage("�2Voc\u00ea foi �fTELEPORTADO�2 para a �fWARP " + commandArgs[0].toUpperCase());
				return true;
			}
		}
		return false;
	}
}
