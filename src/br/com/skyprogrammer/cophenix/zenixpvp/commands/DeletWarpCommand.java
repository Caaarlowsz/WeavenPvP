package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class DeletWarpCommand extends CommandHandler {
	public DeletWarpCommand() {
		super("deletwarp", new String[] { "deletarwarp" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (!this.isPlayerSender(commandSender)) {
			this.sendNotPlayer(commandSender);
			return true;
		}
		final Player localPlayer = this.getPlayerInstance(commandSender);
		if (!localPlayer.hasPermission("pvp.cmd.deletwarp")) {
			this.sendNoPermission(commandSender);
			return true;
		}
		if (commandArgs.length == 0) {
			localPlayer.sendMessage("�e�lDELETWARP�f Utilize: /deletwarp <nome da warp>");
			return true;
		}
		if (commandArgs.length != 1) {
			localPlayer.sendMessage("�e�lDELETWARP�f Utilize: /deletwarp <nome da warp>");
			return true;
		}
		if (!WeavenPvP.getManager().getWarps().hasWarp(commandArgs[0])) {
			localPlayer.sendMessage("�cEsta �fWARP NUNCA�c foi setada!");
			return true;
		}
		WeavenPvP.getManager().getWarps().delet(commandArgs[0]);
		localPlayer.sendMessage("�2Voc\u00ea �fDELETOU�2 a �fWARP " + commandArgs[0].toUpperCase());
		return true;
	}
}
