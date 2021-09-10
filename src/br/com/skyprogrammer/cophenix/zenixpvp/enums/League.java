package br.com.skyprogrammer.cophenix.zenixpvp.enums;

public enum League {
	UNRANKED("UNRANKED", 0, "§f§l-", "§f§lUNRANKED", Integer.MIN_VALUE, 499),
	PRIMARY("PRIMARY", 1, "§a§l\u2630", "§a§lPRIMARY", 500, 1499),
	ADVANCED("ADVANCED", 2, "§e§l\u2632", "§e§lADVANCED", 1500, 3499),
	EXPERT("EXPERT", 3, "§1§l\u2637", "§1§lEXPERT", 3500, 5999),
	SILVER("SILVER", 4, "§7§l\u2736", "§7§lSILVER", 6000, 8999), GOLD("GOLD", 5, "§6§l\u2737", "§6§lGOLD", 9000, 12999),
	DIAMOND("DIAMOND", 6, "§b§l\u2726", "§b§lDIAMOND", 13000, 16999),
	EMERALD("EMERALD", 7, "§2§l\u2725", "§2§lEMERALD", 17000, 21999),
	CRYSTAL("CRYSTAL", 8, "§9§l\u2756", "§9§lCRYSTAL", 22000, 26999),
	SAPPHIRE("SAPPHIRE", 9, "§3§l\u2741", "§3§lSAPPHIRE", 27000, 31999),
	ELITE("ELITE", 10, "§5§l\u2739", "§5§lELITE", 32000, 36999),
	MASTER("MASTER", 11, "§c§l\u272b", "§c§lMASTER", 37000, 41999),
	LEGENDARY("LEGENDARY", 12, "§4§l\u272a", "§4§lLEGENDARY", 42000, 49999),
	ZENIX("ZENIX", 13, "§8§lZ", "§8§lZENIX", 50000, Integer.MAX_VALUE);

	private String symbol;
	private String name;
	private int minXp;
	private int maxXp;

	private League(final String s, final int n, final String symbol, final String name, final int minXp,
			final int maxXp) {
		this.symbol = symbol;
		this.name = name;
		this.minXp = minXp;
		this.maxXp = maxXp;
	}

	public String getSymbol() {
		return this.symbol;
	}

	public String getSymbolNoBold() {
		return this.symbol.replace("§l", "");
	}

	public String getName() {
		return this.name;
	}

	public String getNameNoBold() {
		return this.name.replace("§l", "");
	}

	public int getMinXp() {
		return this.minXp;
	}

	public int getMaxXp() {
		return this.maxXp;
	}

	public League getNextLeague() {
		return values()[this.ordinal() + 1];
	}

	public League getPreviousLeague() {
		return values()[this.ordinal() - 1];
	}
}
