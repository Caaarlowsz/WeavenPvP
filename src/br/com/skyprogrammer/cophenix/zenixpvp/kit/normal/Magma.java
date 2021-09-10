package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;
import br.com.weaven.core.bukkit.event.update.UpdateEvent;

public class Magma extends Kit {
	public Magma() {
		super("Magma", Material.WATER_BUCKET, 0, 9000, 0, true,
				new String[] { "", "N\u00e3o receba nenhum dano para elementos", "semelhantes a fogo e a cada hite em",
						"seus oponentes tenha 30% de chance", "deles pegarem fogo porem se estiver",
						"na agua voce recebe 3.50 de", "dano por segundo." });
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
					localDamaged.setFireTicks(150);
				}
			}
		}
	}

	@EventHandler
	public void onUpdateMagma(final UpdateEvent localUpdateEvent) {
		Player[] onlinePlayers;
		for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
			final Player localOnlinePlayers = onlinePlayers[i];
			final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localOnlinePlayers.getUniqueId());
			if (localGamer.getKit() == this) {
				final Location localLocation = localOnlinePlayers.getLocation().clone().add(0.0, -1.0, 0.0).add(0.0,
						1.0, 0.0);
				final Block localBlock = localLocation.getBlock();
				if (localBlock.getType() == Material.WATER || localBlock.getType() == Material.STATIONARY_WATER) {
					localOnlinePlayers.damage(3.5);
				}
			}
		}
	}
}
