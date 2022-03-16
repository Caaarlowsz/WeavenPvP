package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;

public abstract class ListenerHandler implements Listener {
	private WeavenPvP instanceOfHandler;

	public ListenerHandler(final WeavenPvP instanceOfHandler) {
		this.instanceOfHandler = instanceOfHandler;
		this.instanceOfHandler.getServer().getPluginManager().registerEvents((Listener) this,
				(Plugin) instanceOfHandler);
	}

	public WeavenPvP getHandler() {
		return this.instanceOfHandler;
	}
}
