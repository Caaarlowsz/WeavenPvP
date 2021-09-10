package br.com.skyprogrammer.cophenix.zenixpvp.manager.kit;

import java.lang.reflect.Constructor;
import java.util.LinkedList;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;
import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.getter.ClassGetter;

public class KitManager {
	private LinkedList<Kit> linkedListOfKits;
	private Handler instanceOfHandler;

	public KitManager(final Handler instanceOfHandler) {
		this.instanceOfHandler = instanceOfHandler;
		this.linkedListOfKits = new LinkedList<Kit>();
	}

	public KitManager loadKits(final String stringOfThePackageNameToLoad) {
		int sucess = 0;
		int error = 0;
		try {
			for (final Class<?> classConstructor : ClassGetter.getClassesForPackage(this.instanceOfHandler,
					stringOfThePackageNameToLoad)) {
				if (Kit.class.isAssignableFrom(classConstructor)) {
					try {
						Kit instanceOfKit = null;
						try {
							final Constructor<?> instanceOfClassConstructor = classConstructor
									.getConstructor(this.instanceOfHandler.getClass());
							instanceOfKit = (Kit) instanceOfClassConstructor.newInstance(this.instanceOfHandler);
						} catch (Exception e) {
							instanceOfKit = (Kit) classConstructor.newInstance();
						}
						this.linkedListOfKits.add(instanceOfKit);
						this.instanceOfHandler.getServer().getPluginManager().registerEvents((Listener) instanceOfKit,
								(Plugin) this.instanceOfHandler);
						++sucess;
					} catch (Exception localException) {
						++error;
					}
				}
			}
		} catch (Exception ex) {
		}
		this.instanceOfHandler.getLogger().info("Total of loaded kits: (" + sucess + ")");
		this.instanceOfHandler.getLogger().info("Total of kits with error: (" + error + ")");
		return this;
	}

	public LinkedList<Kit> getAllKits() {
		return this.linkedListOfKits;
	}
}
