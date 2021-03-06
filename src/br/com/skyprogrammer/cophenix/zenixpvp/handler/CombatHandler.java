package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;

public class CombatHandler extends ListenerHandler {
	public static ConcurrentHashMap<Player, String> concurrentMapOfCombat;
	public static ConcurrentHashMap<Player, BukkitTask> concurrentMapOfTask;

	static {
		CombatHandler.concurrentMapOfCombat = new ConcurrentHashMap<Player, String>();
		CombatHandler.concurrentMapOfTask = new ConcurrentHashMap<Player, BukkitTask>();
	}

	public CombatHandler(final WeavenPvP instanceOfHandler) {
		super(instanceOfHandler);
	}

	public static void addCombat(final Player playerToAdd, final Player toCombat) {
		CombatHandler.concurrentMapOfCombat.put(playerToAdd, toCombat.getName());
		CombatHandler.concurrentMapOfTask.put(playerToAdd, new BukkitRunnable() {
			public void run() {
				CombatHandler.removeCombat(playerToAdd);
			}
		}.runTaskLater((Plugin) WeavenPvP.getInstance(), 160L));
	}

	public static boolean onCombat(final Player playerToCheck) {
		return CombatHandler.concurrentMapOfCombat.containsKey(playerToCheck);
	}

	public static void removeCombat(final Player playerToRemove) {
		if (CombatHandler.concurrentMapOfCombat.containsKey(playerToRemove)) {
			CombatHandler.concurrentMapOfCombat.remove(playerToRemove);
		}
		if (CombatHandler.concurrentMapOfTask.containsKey(playerToRemove)) {
			if (CombatHandler.concurrentMapOfTask.get(playerToRemove) != null) {
				CombatHandler.concurrentMapOfTask.get(playerToRemove).cancel();
			}
			CombatHandler.concurrentMapOfTask.remove(playerToRemove);
		}
	}

	@EventHandler
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
		if (localEntityDamageByEntityEvent.isCancelled()) {
			return;
		}
		if (localEntityDamageByEntityEvent.getEntity() instanceof Player
				&& localEntityDamageByEntityEvent.getDamager() instanceof Player) {
			final Player localEntity = (Player) localEntityDamageByEntityEvent.getEntity();
			final Player localDamager = (Player) localEntityDamageByEntityEvent.getDamager();
			addCombat(localEntity, localDamager);
		}
	}
}
