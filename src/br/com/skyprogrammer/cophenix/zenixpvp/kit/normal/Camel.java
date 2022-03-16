package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Camel extends Kit {
	public Camel() {
		super("Camel", Material.SAND, 0, 7000, 0, true,
				new String[] { "", "Quando estiver na areia receba", "velocidade II e for\u00e7a I." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent localPlayerMoveEvent) {
		final Player localPlayer = localPlayerMoveEvent.getPlayer();
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this) {
			final Location localLocation = localPlayer.getLocation().clone().add(0.0, -1.0, 0.0);
			final Block localBlock = localLocation.getBlock();
			if (localBlock.getType() == Material.SAND) {
				localPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 300, 1));
				localPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 0));
			}
		}
	}
}
