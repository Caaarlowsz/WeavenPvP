package br.com.skyprogrammer.cophenix.zenixpvp.manager.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.Manager;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.constructor.ScoreboardHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings.StringAnimated;
import br.com.weaven.master.account.WavePlayer;

public class ScoreboardManager {
	private Manager instanceOfManager;
	private StringAnimated instanceOfStringAnimated;
	private String animationOfString;

	public ScoreboardManager(final Manager instanceOfManager) {
		this.instanceOfManager = instanceOfManager;
		this.instanceOfStringAnimated = new StringAnimated(" CELTZ PVP ", "�f�l", "�3�l", "�b�l", 3);
		this.animationOfString = this.instanceOfStringAnimated.next();
		this.initialize();
	}

	public void initialize() {
		new BukkitRunnable() {
			public void run() {
				ScoreboardManager.access$1(ScoreboardManager.this,
						ScoreboardManager.this.instanceOfStringAnimated.next());
				Player[] onlinePlayers;
				for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
					final Player localPlayer = onlinePlayers[i];
					final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
					final ScoreboardHandler localScoreboardHandler = localGamer.getScoreboardHandler();
					if (localScoreboardHandler != null) {
						final WavePlayer wp = WavePlayer.getPlayer(localPlayer.getUniqueId());
						final Objective objectiveFromScoreboardHandler = localScoreboardHandler
								.getObjectiveOfCreatedScoreboard();
						objectiveFromScoreboardHandler.setDisplayName(ScoreboardManager.this.animationOfString);
						localScoreboardHandler.addLine(14, "�fKills: �3" + localGamer.getKills());
						localScoreboardHandler.addLine(13, "�fDeaths: �3" + localGamer.getDeaths());
						localScoreboardHandler.addLine(11, "�fKillStreak: �b" + localGamer.getKillStreak());
						localScoreboardHandler.addLine(10, "�fXP: �b" + wp.getXp());
						localScoreboardHandler.addLine(8, "�fKit: �e" + localGamer.getKit().getName());
					}
				}
			}
		}.runTaskTimerAsynchronously((Plugin) WeavenPvP.getInstance(), 2L, 2L);
	}

	public void createScoreboardToPlayer(final Player playerToCreate) {
		final Gamer localGamer = this.instanceOfManager.getGamerManager().getGamer(playerToCreate.getUniqueId());
		final WavePlayer wp = WavePlayer.getPlayer(playerToCreate.getUniqueId());
		localGamer.setScoreboardHandler(null);
		final ScoreboardHandler localScoreboardHandler = new ScoreboardHandler();
		final Objective objectiveFromScoreboardHandler = localScoreboardHandler.getObjectiveOfCreatedScoreboard();
		objectiveFromScoreboardHandler.setDisplayName(this.animationOfString);
		localScoreboardHandler.addLine("");
		localScoreboardHandler.addLine("�fKills: �3" + localGamer.getKills());
		localScoreboardHandler.addLine("�fDeaths: �3" + localGamer.getDeaths());
		localScoreboardHandler.addLine("");
		localScoreboardHandler.addLine("�fKill Streak: �3" + localGamer.getKillStreak());
		localScoreboardHandler.addLine("�fXP: �b" + wp.getXp());
		localScoreboardHandler.addLine("");
		localScoreboardHandler.addLine("�fKit: �e" + localGamer.getKit().getName());
		localScoreboardHandler.addLine("");
		localScoreboardHandler.addLine("�bwww.celtzmc.com");
		final Scoreboard createdScoreboard = localScoreboardHandler.getCreatedScoreboard();
		playerToCreate.setScoreboard(createdScoreboard);
		localGamer.setScoreboardHandler(localScoreboardHandler);
	}

	static /* synthetic */ void access$1(final ScoreboardManager scoreboardManager, final String animationOfString) {
		scoreboardManager.animationOfString = animationOfString;
	}
}
