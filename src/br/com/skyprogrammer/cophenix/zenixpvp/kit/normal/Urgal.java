package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Urgal extends Kit {
	public Urgal() {
		super("Urgal", Material.POTION, 0, 15000, 110, true, new String[] { "",
				"Use sua po\u00e7\u00e3o e receba for\u00e7a I", "para matar seus oponentes mais r\u00e1pido." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onThor(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player localPlayer = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this && localPlayer.getItemInHand().getType() == this.getMaterial()) {
			localPlayerInteractEvent.setCancelled(true);
			if (CooldownHandler.onCooldown(localPlayer)) {
				CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
				return;
			}
			CooldownHandler.addCooldown(localPlayer, 60);
			localPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 400, 0));
			localPlayer.sendMessage("§2§l" + this.getName() + "§2 Voc\u00ea recebeu §fFOR\u00c7A I!");
		}
	}
}
