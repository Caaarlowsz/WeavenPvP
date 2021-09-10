package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Fireman extends Kit {
	public Fireman() {
		super("Fireman", Material.LAVA_BUCKET, 0, 6000, 0, true,
				new String[] { "", "N\u00e3o receba dano para nenhum", "elemento relacionado \u00e1 fogo." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	@EventHandler
	public void onDamageFire(final EntityDamageEvent localEntityDamageEvent) {
		if (localEntityDamageEvent.getEntity() instanceof Player) {
			final Player localPlayer = (Player) localEntityDamageEvent.getEntity();
			final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
			if (localGamer.getKit() == this && (localEntityDamageEvent.getCause() == EntityDamageEvent.DamageCause.FIRE
					|| localEntityDamageEvent.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK
					|| localEntityDamageEvent.getCause() == EntityDamageEvent.DamageCause.LAVA)) {
				localEntityDamageEvent.setCancelled(true);
			}
		}
	}
}
