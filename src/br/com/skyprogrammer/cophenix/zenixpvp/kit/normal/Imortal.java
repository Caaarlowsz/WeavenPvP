package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Imortal extends Kit {
	private ArrayList<Player> arrayListOfImortal;

	public Imortal() {
		super("Imortal", Material.BEDROCK, 0, 9500, 44, true, new String[] { "", "Use seu item e fique completamente",
				"invuner\u00e1vel \u00e1 danos por 10 segundos." });
		this.arrayListOfImortal = new ArrayList<Player>();
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
		if (this.arrayListOfImortal.contains(playerToRemove)) {
			this.arrayListOfImortal.remove(playerToRemove);
		}
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onInteractImortal(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player localPlayer = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this && localPlayer.getItemInHand().getType() == this.getMaterial()) {
			if (CooldownHandler.onCooldown(localPlayer)) {
				CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
				return;
			}
			CooldownHandler.addCooldown(localPlayer, this.getCooldown());
			this.arrayListOfImortal.add(localPlayer);
			localPlayer.sendMessage("§2§l" + this.getName() + "§2 Voc\u00ea §fATIVOU§2 sua §fIMORTALIDADE!");
			Handler.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Handler.getInstance(),
					() -> this.removeAbilityIfHas(localPlayer), 200L);
		}
	}

	@EventHandler
	public void onDamageImortal(final EntityDamageEvent localEntityDamageEvent) {
		if (localEntityDamageEvent.getEntity() instanceof Player) {
			final Player localPlayer = (Player) localEntityDamageEvent.getEntity();
			final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
			if (localGamer.getKit() == this && this.arrayListOfImortal.contains(localPlayer)) {
				localEntityDamageEvent.setCancelled(true);
			}
		}
	}
}
