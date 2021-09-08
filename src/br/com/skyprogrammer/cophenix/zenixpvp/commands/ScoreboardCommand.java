package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.scoreboard.Scoreboard;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.entity.Player;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.ScoreboardAPI;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.constructor.ScoreboardHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.command.CommandSender;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class ScoreboardCommand extends CommandHandler
{
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
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (localGamer.getScoreboardHandler() == null) {
            Handler.getManager().getScoreboardManager().createScoreboardToPlayer(localPlayer);
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
