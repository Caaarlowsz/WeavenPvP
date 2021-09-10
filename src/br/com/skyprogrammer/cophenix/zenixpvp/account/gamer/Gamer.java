package br.com.skyprogrammer.cophenix.zenixpvp.account.gamer;

import java.util.UUID;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.None;
import br.com.skyprogrammer.cophenix.zenixpvp.scoreboard.constructor.ScoreboardHandler;

public class Gamer {
	private String gamerName;
	private UUID gamerUniqueId;
	private String gamerFakeName;
	private boolean isPremiumAccount;
	private Kit gamerKit;
	private String gamerWarp;
	private int gamerKills;
	private int gamerDeaths;
	private int gamerKillStreak;
	private int gamerSimpleBoxes;
	private int gamerIntermediaryBoxes;
	private int gamerAdvancedBoxes;
	private ScoreboardHandler gamerScoreboardHandler;
	private String nameOfPlayerBattling;
	private boolean isBuildEnabled;

	public Gamer(final UUID gamerUniqueId, final String gamerName) {
		this.gamerUniqueId = gamerUniqueId;
		this.gamerName = gamerName;
		this.gamerFakeName = "";
		this.isPremiumAccount = false;
		this.gamerKit = new None();
		this.gamerWarp = "Spawn";
		this.gamerKills = 0;
		this.gamerDeaths = 0;
		this.gamerKillStreak = 0;
		this.gamerSimpleBoxes = 0;
		this.gamerIntermediaryBoxes = 0;
		this.gamerAdvancedBoxes = 0;
		this.gamerScoreboardHandler = null;
		this.nameOfPlayerBattling = "Ningu\u00e9m";
		this.isBuildEnabled = false;
	}

	public Gamer update() {
		if (this.isPremiumAccount) {
			this.gamerKills = Handler.getManager().getAccounts().getKills(this.gamerUniqueId);
			this.gamerDeaths = Handler.getManager().getAccounts().getDeaths(this.gamerUniqueId);
			this.gamerKillStreak = Handler.getManager().getAccounts().getStreak(this.gamerUniqueId);
			this.gamerSimpleBoxes = Handler.getManager().getAccounts().getSimpleBox(this.gamerUniqueId);
			this.gamerIntermediaryBoxes = Handler.getManager().getAccounts().getIntermediaryBox(this.gamerUniqueId);
			this.gamerAdvancedBoxes = Handler.getManager().getAccounts().getAdvancedBox(this.gamerUniqueId);
		} else {
			this.gamerKills = Handler.getManager().getAccounts().getKills(this.gamerName);
			this.gamerDeaths = Handler.getManager().getAccounts().getDeaths(this.gamerName);
			this.gamerKillStreak = Handler.getManager().getAccounts().getStreak(this.gamerName);
			this.gamerSimpleBoxes = Handler.getManager().getAccounts().getSimpleBox(this.gamerName);
			this.gamerIntermediaryBoxes = Handler.getManager().getAccounts().getIntermediaryBox(this.gamerName);
			this.gamerAdvancedBoxes = Handler.getManager().getAccounts().getAdvancedBox(this.gamerName);
		}
		return this;
	}

	public Gamer updateData() {
		if (this.isPremiumAccount) {
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".NickName",
					(Object) this.gamerName);
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".Kills",
					(Object) this.gamerKills);
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".Deaths",
					(Object) this.gamerDeaths);
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".KillStreak",
					(Object) this.gamerKillStreak);
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".SimpleBox",
					(Object) this.gamerSimpleBoxes);
			Handler.getManager().getAccounts().getFile().set(
					"Accounts.Premium." + this.gamerUniqueId + ".IntermediaryBox",
					(Object) this.gamerIntermediaryBoxes);
			Handler.getManager().getAccounts().getFile().set("Accounts.Premium." + this.gamerUniqueId + ".AdvancedBox",
					(Object) this.gamerAdvancedBoxes);
			Handler.getManager().getAccounts().save();
		} else {
			Handler.getManager().getAccounts().getFile()
					.set("Accounts.Pirate." + this.gamerName.toLowerCase() + ".NickName", (Object) this.gamerName);
			Handler.getManager().getAccounts().getFile()
					.set("Accounts.Pirate." + this.gamerName.toLowerCase() + ".Kills", (Object) this.gamerKills);
			Handler.getManager().getAccounts().getFile()
					.set("Accounts.Pirate." + this.gamerName.toLowerCase() + ".Deaths", (Object) this.gamerDeaths);
			Handler.getManager().getAccounts().getFile().set(
					"Accounts.Pirate." + this.gamerName.toLowerCase() + ".KillStreak", (Object) this.gamerKillStreak);
			Handler.getManager().getAccounts().getFile().set(
					"Accounts.Pirate." + this.gamerName.toLowerCase() + ".SimpleBox", (Object) this.gamerSimpleBoxes);
			Handler.getManager().getAccounts().getFile().set(
					"Accounts.Pirate." + this.gamerName.toLowerCase() + ".IntermediaryBox",
					(Object) this.gamerIntermediaryBoxes);
			Handler.getManager().getAccounts().getFile().set(
					"Accounts.Pirate." + this.gamerName.toLowerCase() + ".AdvancedBox",
					(Object) this.gamerAdvancedBoxes);
			Handler.getManager().getAccounts().save();
		}
		return this;
	}

	public Gamer setName(final String newGamerName) {
		this.gamerName = newGamerName;
		return this;
	}

	public Gamer setUniqueId(final UUID newGamerUniqueId) {
		this.gamerUniqueId = newGamerUniqueId;
		return this;
	}

	public Gamer setFakeName(final String newFakeName) {
		this.gamerFakeName = newFakeName;
		return this;
	}

	public Gamer setIsPremiumAccount(final boolean isPremiumAccount) {
		this.isPremiumAccount = isPremiumAccount;
		return this;
	}

	public Gamer setKit(final Kit newValueOfKit) {
		this.gamerKit = newValueOfKit;
		return this;
	}

	public Gamer setWarp(final String newStringOfWarp) {
		this.gamerWarp = newStringOfWarp;
		return this;
	}

	public Gamer setKills(final int integerOfKills) {
		this.gamerKills = integerOfKills;
		return this;
	}

	public Gamer setDeaths(final int integerOfDeaths) {
		this.gamerDeaths = integerOfDeaths;
		return this;
	}

	public Gamer setKillStreak(final int integerOfKillStreak) {
		this.gamerKillStreak = integerOfKillStreak;
		return this;
	}

	public Gamer setSimpleBoxes(final int integerOfSimpleBoxes) {
		this.gamerSimpleBoxes = integerOfSimpleBoxes;
		return this;
	}

	public Gamer setIntermediaryBoxes(final int integerOfIntermediaryBoxes) {
		this.gamerIntermediaryBoxes = integerOfIntermediaryBoxes;
		return this;
	}

	public Gamer setAdvancedBoxes(final int integerOfAdvancedBoxes) {
		this.gamerAdvancedBoxes = integerOfAdvancedBoxes;
		return this;
	}

	public Gamer setScoreboardHandler(final ScoreboardHandler instanceOfScoreboardHandler) {
		this.gamerScoreboardHandler = instanceOfScoreboardHandler;
		return this;
	}

	public Gamer setNameOfPlayerBattling(final String nameOfPlayerBattling) {
		this.nameOfPlayerBattling = nameOfPlayerBattling;
		return this;
	}

	public Gamer setBuildEnabled(final boolean ifBuildIsEnabled) {
		this.isBuildEnabled = ifBuildIsEnabled;
		return this;
	}

	public String getName() {
		return this.gamerName;
	}

	public UUID getUniqueId() {
		return this.gamerUniqueId;
	}

	public String getFakeName() {
		return this.gamerFakeName;
	}

	public boolean isPremiumAccount() {
		return this.isPremiumAccount;
	}

	public Kit getKit() {
		return this.gamerKit;
	}

	public String getWarp() {
		return this.gamerWarp;
	}

	public int getKills() {
		return this.gamerKills;
	}

	public int getDeaths() {
		return this.gamerDeaths;
	}

	public int getKillStreak() {
		return this.gamerKillStreak;
	}

	public int getSimpleBoxes() {
		return this.gamerSimpleBoxes;
	}

	public int getIntermediaryBoxes() {
		return this.gamerIntermediaryBoxes;
	}

	public int getAdvancedBoxes() {
		return this.gamerAdvancedBoxes;
	}

	public ScoreboardHandler getScoreboardHandler() {
		return this.gamerScoreboardHandler;
	}

	public String getNameOfPlayerBattling() {
		return this.nameOfPlayerBattling;
	}

	public boolean isBuildEnabled() {
		return this.isBuildEnabled;
	}
}
