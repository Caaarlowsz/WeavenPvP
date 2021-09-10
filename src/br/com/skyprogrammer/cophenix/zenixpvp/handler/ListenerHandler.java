package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;

public abstract class ListenerHandler implements Listener {
	private Handler instanceOfHandler;

	public ListenerHandler(final Handler instanceOfHandler) {
		this.instanceOfHandler = instanceOfHandler;
		this.instanceOfHandler.getServer().getPluginManager().registerEvents((Listener) this,
				(Plugin) instanceOfHandler);
	}

	public Handler getHandler() {
		return this.instanceOfHandler;
	}
}
