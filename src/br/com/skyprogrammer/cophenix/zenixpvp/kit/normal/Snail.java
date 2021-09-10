package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Snail extends Kit {
	public Snail() {
		super("Snail", Material.WEB, 0, 3000, 0, true, new String[] { "", "A cada hite tenha 30% de chance de",
				"seu oponente pegar efeito de lentid\u00e3o II." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
		if (localEntityDamageByEntityEvent.getEntity() instanceof Player
				&& localEntityDamageByEntityEvent.getDamager() instanceof Player) {
			final Player localDamaged = (Player) localEntityDamageByEntityEvent.getEntity();
			final Player localDamager = (Player) localEntityDamageByEntityEvent.getDamager();
			final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localDamager.getUniqueId());
			if (localGamer.getKit() == this) {
				final int integerOfChance = new Random().nextInt(100);
				if (integerOfChance > 0 && integerOfChance < 31) {
					localDamaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 160, 1));
				}
			}
		}
	}
}
