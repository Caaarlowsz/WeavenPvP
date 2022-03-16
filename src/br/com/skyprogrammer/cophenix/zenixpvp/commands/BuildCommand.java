package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class BuildCommand extends CommandHandler {
	public BuildCommand() {
		super("build", new String[] { "builder" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (!this.isPlayerSender(commandSender)) {
			this.sendNotPlayer(commandSender);
			return true;
		}
		final Player localPlayer = this.getPlayerInstance(commandSender);
		if (!localPlayer.hasPermission("pvp.cmd.build")) {
			this.sendNoPermission(commandSender);
			return true;
		}
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.isBuildEnabled()) {
			localGamer.setBuildEnabled(false);
			localPlayer.sendMessage("�cVoc\u00ea �fDESATIVOU�c o modo builder!");
			return true;
		}
		localGamer.setBuildEnabled(true);
		localPlayer.sendMessage("�2Voc\u00ea �fATIVOU�2 o modo builder!");
		return true;
	}
}
