package br.com.skyprogrammer.cophenix.zenixpvp.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardAPI {
	public static Scoreboard getNewScoreboard() {
		final Scoreboard newBukkitScorebord = Bukkit.getScoreboardManager().getNewScoreboard();
		return newBukkitScorebord;
	}

	public static Scoreboard getMainScoreboard() {
		final Scoreboard mainBukkitScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		return mainBukkitScoreboard;
	}

	public static Objective registerNewObjectiveToScoreboard(final Scoreboard scoreboardToRegister,
			final String objectiveId, final String objectiveSubId, final DisplaySlot objectiveDisplaySlot,
			final String objectiveDisplayName) {
		final Objective newObjectiveToScoreboard = scoreboardToRegister.registerNewObjective(objectiveId,
				objectiveSubId);
		newObjectiveToScoreboard.setDisplaySlot(objectiveDisplaySlot);
		newObjectiveToScoreboard.setDisplayName(objectiveDisplayName);
		return newObjectiveToScoreboard;
	}

	public static Objective getObjectiveFromScoreboard(final Scoreboard scoreboardToGet,
			final DisplaySlot objectiveDisplaySlot) {
		final Objective objectFromScoreboard = scoreboardToGet.getObjective(objectiveDisplaySlot);
		return objectFromScoreboard;
	}

	public static Objective getObjectiveFromScoreboard(final Scoreboard scoreboardToGet, final String objectiveId) {
		final Objective objectFromScreboard = scoreboardToGet.getObjective(objectiveId);
		return objectFromScreboard;
	}

	public static Team registerNewTeamToScoreboard(final Scoreboard scoreboardToRegister, final String teamId,
			final String defaultTeamPrefix, final String defaultTeamSuffix) {
		final Team newTeamToScoreboard = scoreboardToRegister.registerNewTeam(teamId);
		newTeamToScoreboard.setPrefix(defaultTeamPrefix);
		newTeamToScoreboard.setSuffix(defaultTeamSuffix);
		return newTeamToScoreboard;
	}

	public static Team registerNewTeamToScoreboard(final Scoreboard scoreboardToRegister, final String teamId) {
		final Team newTeamToScoreboard = registerNewTeamToScoreboard(scoreboardToRegister, teamId, "", "");
		return newTeamToScoreboard;
	}

	public static Team getTeamFromScoreboard(final Scoreboard scoreboardToGet, final String teamId) {
		final Team teamFromScoreboard = scoreboardToGet.getTeam(teamId);
		return teamFromScoreboard;
	}
}
