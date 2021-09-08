package br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.constructor;

import java.util.Iterator;
import org.bukkit.scoreboard.Team;
import org.bukkit.ChatColor;
import com.google.common.base.Splitter;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import org.bukkit.scoreboard.DisplaySlot;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.ScoreboardAPI;
import java.util.Map;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHandler
{
    private Scoreboard scoreboardToCreate;
    private Objective objectiveOfScoreboard;
    private Map<Integer, String> storedLines;
    private int index;
    
    public ScoreboardHandler() {
        this.scoreboardToCreate = ScoreboardAPI.getNewScoreboard();
        this.objectiveOfScoreboard = ScoreboardAPI.registerNewObjectiveToScoreboard(this.scoreboardToCreate, "sidebar", "dummy", DisplaySlot.SIDEBAR, "");
        this.storedLines = new HashMap<Integer, String>();
        this.index = 15;
    }
    
    public Scoreboard getCreatedScoreboard() {
        return this.scoreboardToCreate;
    }
    
    public Objective getObjectiveOfCreatedScoreboard() {
        return this.objectiveOfScoreboard;
    }
    
    public Map<Integer, String> getMapOfStoredLines() {
        return this.storedLines;
    }
    
    public void addLine(final String text) {
        this.addLine(this.index--, text);
    }
    
    public void addLine(final int index, String text) {
        Preconditions.checkArgument(index > 0 && index < 16, (Object)"Parameter 'index' must be between 1 and 15");
        if (text.length() > 32) {
            text = text.substring(0, 32);
        }
        Team team = this.scoreboardToCreate.getTeam("score-" + index);
        final String str = this.getColor(index);
        if (team == null) {
            team = this.scoreboardToCreate.registerNewTeam("score-" + index);
            this.objectiveOfScoreboard.getScore(str).setScore(index);
        }
        if (!team.hasEntry(str)) {
            team.addEntry(str);
        }
        final Iterator<String> iterator = Splitter.fixedLength(16).split((CharSequence)text).iterator();
        String prefix = iterator.next();
        if (prefix.endsWith("§")) {
            prefix = this.subString(prefix);
            if (!prefix.equals(team.getPrefix())) {
                team.setPrefix(prefix);
            }
            if (iterator.hasNext()) {
                final String suffix = this.subString(iterator.next());
                if (!suffix.equals(team.getSuffix())) {
                    team.setSuffix(suffix);
                }
            }
            else if (!"".equals(team.getSuffix())) {
                team.setSuffix("");
            }
        }
        else if (iterator.hasNext()) {
            String suffix = iterator.next();
            if (!suffix.startsWith("§")) {
                suffix = this.subString(String.valueOf(ChatColor.getLastColors(prefix)) + suffix);
            }
            if (!prefix.equals(team.getPrefix())) {
                team.setPrefix(prefix);
            }
            if (!suffix.equals(team.getSuffix())) {
                team.setSuffix(suffix);
            }
        }
        else {
            if (!prefix.equals(team.getPrefix())) {
                team.setPrefix(prefix);
            }
            if (!"".equals(team.getSuffix())) {
                team.setSuffix("");
            }
        }
    }
    
    public String getColor(final int id) {
        return ChatColor.values()[id - 1].toString();
    }
    
    protected String subString(String string) {
        final int length = string.length();
        if (length > 16) {
            string = string.substring(0, 16);
        }
        if (string.endsWith("§")) {
            string = string.substring(0, length - 1);
        }
        return string;
    }
    
    public void updateLine(final int index, final String text) {
        this.storedLines.put(index, text);
        final Team teamFromScoreboard = ScoreboardAPI.getTeamFromScoreboard(this.scoreboardToCreate, "handlerTeam-" + index);
        if (text.length() < 16) {
            teamFromScoreboard.setPrefix(text);
            teamFromScoreboard.setSuffix("");
        }
        else if (text.length() >= 16) {
            String prefix = text.substring(0, 16);
            for (int i = 16; i >= 0 && text.substring(i - 1, i).contains("§"); --i) {
                prefix = text.substring(0, i - 1);
            }
            teamFromScoreboard.setPrefix(prefix);
            teamFromScoreboard.setSuffix("");
            if (text.length() > 16 && text.length() <= 32) {
                teamFromScoreboard.setSuffix(String.valueOf(ChatColor.getLastColors(text.substring(0, 16))) + text.substring(16));
            }
            else if (text.length() > 16 && text.length() > 32) {
                teamFromScoreboard.setSuffix(String.valueOf(ChatColor.getLastColors(text.substring(0, 16))) + text.substring(16).substring(0, 16));
            }
        }
    }
}
