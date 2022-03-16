package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.ScoreboardAPI;

public class ScoreboardCommand extends CommandHandler {
	public ScoreboardCommand() {
		super("score", new String[] { "sidebar" });
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (!this.isPlayerSender(commandSender)) {
			this.sendNotPlayer(commandSender);
			return true;
		}
		final Player localPlayer = this.getPlayerInstance(commandSender);
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getScoreboardHandler() == null) {
			WeavenPvP.getManager().getScoreboardManager().createScoreboardToPlayer(localPlayer);
			localPlayer.sendMessage("�2Voc\u00ea agora est\u00e1 com sua scoreboard �fATIVADA");
			return true;
		}
		localGamer.setScoreboardHandler(null);
		final Scoreboard bukkitNewScoreboard = ScoreboardAPI.getNewScoreboard();
		localPlayer.setScoreboard(bukkitNewScoreboard);
		localPlayer.sendMessage("�2Voc\u00ea agora est\u00e1 com sua scoreboard �fDESATIVADA");
		return true;
	}
}
