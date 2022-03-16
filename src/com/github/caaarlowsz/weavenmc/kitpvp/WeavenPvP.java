package com.github.caaarlowsz.weavenmc.kitpvp;

import com.github.caaarlowsz.kitpvpapi.KitPvP;
import com.github.caaarlowsz.kitpvpapi.KitPvPAPI;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.skyprogrammer.cophenix.zenixpvp.manager.Manager;

public class WeavenPvP extends JavaPlugin implements KitPvP {

	@Override
	public void onEnable() {
		super.onEnable();
		KitPvPAPI.setInstance(this);

		// TODO: Remover quando melhorar a classe principal
		this.enable();
	}

	@Override
	public void onDisable() {
		super.onDisable();
		KitPvPAPI.setInstance(null);

		// TODO: Remover quando melhorar a classe principal
		this.disable();
	}

	// TODO: Melhorar a classe principal

	private static WeavenPvP instanceOfHandler;
	private static Manager instanceOfManager;

	public void onLoad() {
		super.onLoad();
		WeavenPvP.instanceOfHandler = this;
	}

	public void enable() {
		super.onEnable();
		WeavenPvP.instanceOfManager = new Manager(this);
	}

	public void disable() {
		super.onDisable();
	}

	public static Manager getManager() {
		return WeavenPvP.instanceOfManager;
	}

	public static WeavenPvP getInstance() {
		return WeavenPvP.instanceOfHandler;
	}
}
