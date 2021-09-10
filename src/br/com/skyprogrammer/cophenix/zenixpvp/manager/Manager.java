package br.com.skyprogrammer.cophenix.zenixpvp.manager;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.api.tab.TabAPI;
import br.com.skyprogrammer.cophenix.zenixpvp.config.account.AccountConfig;
import br.com.skyprogrammer.cophenix.zenixpvp.config.feast.FeastConfig;
import br.com.skyprogrammer.cophenix.zenixpvp.config.warp.WarpConfig;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.WarpHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.Inventory1v1Custom;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;
import br.com.skyprogrammer.cophenix.zenixpvp.listener.BukkitListener;
import br.com.skyprogrammer.cophenix.zenixpvp.listener.PlayerListener;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.feast.FeastManager;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.gamer.GamerManager;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.kit.KitManager;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.protection.ProtectionManager;
import br.com.skyprogrammer.cophenix.zenixpvp.manager.scoreboard.ScoreboardManager;
import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.loaders.CommandLoader;
import br.com.weaven.master.account.WavePlayer;

public class Manager {
	private Handler instanceOfHandler;
	private GamerManager instanceOfGamerManager;
	private ScoreboardManager instanceOfScoreboardManager;
	private KitManager instanceOfKitManager;
	private FeastManager instanceOfFeastManager;
	private ProtectionManager instanceOfProtectionManager;
	private AccountConfig instanceOfAccountConfig;
	private WarpConfig instanceOfWarpConfig;
	private FeastConfig instanceOfFeastConfig;

	public Manager(final Handler instanceOfHandler) {
		this.instanceOfHandler = instanceOfHandler;
		this.instanceOfGamerManager = new GamerManager();
		this.instanceOfScoreboardManager = new ScoreboardManager(this);
		this.instanceOfKitManager = new KitManager(this.instanceOfHandler)
				.loadKits("br.com.skyprogrammer.cophenix.zenixpvp.kit.normal");
		this.instanceOfFeastManager = new FeastManager(this);
		this.instanceOfHandler.saveDefaultConfig();
		this.instanceOfProtectionManager = new ProtectionManager(this.instanceOfHandler);
		new PlayerListener(this.instanceOfHandler);
		new BukkitListener(this.instanceOfHandler);
		new CooldownHandler(this.instanceOfHandler);
		new WarpHandler.Spawn(this.instanceOfHandler);
		Bukkit.getPluginManager().registerEvents((Listener) new Inventory1v1Custom(), (Plugin) instanceOfHandler);
		Bukkit.getPluginManager().registerEvents((Listener) new X1WarpListener(), (Plugin) instanceOfHandler);
		this.instanceOfAccountConfig = new AccountConfig(this.instanceOfHandler, "accounts.yml");
		this.instanceOfWarpConfig = new WarpConfig(this.instanceOfHandler, "warps.yml");
		this.instanceOfFeastConfig = new FeastConfig(this.instanceOfHandler, "feast.yml");
		CommandLoader.loadCommands(this.instanceOfHandler, "br.com.skyprogrammer.cophenix.zenixpvp.commands");
		new BukkitRunnable() {
			public void run() {
				Player[] onlinePlayers;
				for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
					final Player localOnlinePlayers = onlinePlayers[i];
					final WavePlayer wp = WavePlayer.getPlayer(localOnlinePlayers.getUniqueId());
					final int integerOfPingMs = ((CraftPlayer) localOnlinePlayers).getHandle().ping;
					final int integerOfOnlinePlayers = Bukkit.getOnlinePlayers().length;
					TabAPI.setTabTitleToPlayer(localOnlinePlayers,
							"§6§lCELTZ §e§lKITPVP§eMoedas: §f" + wp.getMoney() + " §9§l- §ePing: §f" + integerOfPingMs
									+ "\n§eNo momento temos §f" + integerOfOnlinePlayers + "§e jogadores no kitpvp!\n",
							"\n   §bNick: §f" + localOnlinePlayers.getName() + " §9§l- §bLiga: "
									+ wp.getLeague().toString() + " §9§l- §bXP: §f" + wp.getXp()
									+ "\n§bMais informa\u00e7\u00f5es em §f" + "www.celtzmc.com");
				}
			}
		}.runTaskTimer((Plugin) this.instanceOfHandler, 40L, 40L);
		this.instanceOfFeastManager.start();
	}

	public ProtectionManager getProtectionManager() {
		return this.instanceOfProtectionManager;
	}

	public GamerManager getGamerManager() {
		return this.instanceOfGamerManager;
	}

	public ScoreboardManager getScoreboardManager() {
		return this.instanceOfScoreboardManager;
	}

	public KitManager getKitManager() {
		return this.instanceOfKitManager;
	}

	public FeastManager getFeastManager() {
		return this.instanceOfFeastManager;
	}

	public AccountConfig getAccounts() {
		return this.instanceOfAccountConfig;
	}

	public WarpConfig getWarps() {
		return this.instanceOfWarpConfig;
	}

	public FeastConfig getFeast() {
		return this.instanceOfFeastConfig;
	}
}
