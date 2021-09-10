package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Kangaroo extends Kit {
	public static final List<UUID> listOfKangaroo;

	static {
		listOfKangaroo = new ArrayList<UUID>();
	}

	public Kangaroo() {
		super("Kangaroo", Material.FIREWORK, 0, 7500, 7, true, new String[] { "", "Com seu foguete se locomova mais",
				"r\u00e1pido pelo mapa e impe\u00e7a que", "seus inimigos fujam de voc\u00ea." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
		if (Kangaroo.listOfKangaroo.contains(playerToRemove.getUniqueId())) {
			Kangaroo.listOfKangaroo.remove(playerToRemove.getUniqueId());
		}
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onInteract(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player localPlayer = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if ((localGamer.getKit() == this & localPlayerInteractEvent.getAction() != Action.PHYSICAL)
				&& localPlayer.getItemInHand().getType() == this.getMaterial()) {
			localPlayerInteractEvent.setCancelled(true);
			if (Kangaroo.listOfKangaroo.contains(localPlayer.getUniqueId())) {
				return;
			}
			if (CooldownHandler.onCooldown(localPlayer)) {
				localPlayer.setVelocity(new Vector(0.0, -1.0, 0.0));
				CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
				return;
			}
			Vector localVector = localPlayer.getEyeLocation().getDirection();
			if (localPlayer.isSneaking()) {
				localVector = localVector.multiply(1.8f).setY(0.5f);
			} else {
				localVector = localVector.multiply(0.5f).setY(1.0f);
			}
			localPlayer.setFallDistance(-1.0f);
			localPlayer.setVelocity(localVector);
			Kangaroo.listOfKangaroo.add(localPlayer.getUniqueId());
		}
	}

	@EventHandler
	public void onDamage(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
		if (!(localEntityDamageByEntityEvent.getEntity() instanceof Player)) {
			return;
		}
		final Player localPlayer = (Player) localEntityDamageByEntityEvent.getEntity();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() != this) {
			return;
		}
		if (localEntityDamageByEntityEvent.isCancelled()) {
			return;
		}
		if (!(localEntityDamageByEntityEvent.getDamager() instanceof Player)) {
			return;
		}
		CooldownHandler.addCooldown(localPlayer, this.getCooldown());
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent localPlayerMoveEvent) {
		if (Kangaroo.listOfKangaroo.contains(localPlayerMoveEvent.getPlayer().getUniqueId())
				&& (localPlayerMoveEvent.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN)
						.getType() != Material.AIR || localPlayerMoveEvent.getPlayer().isOnGround())) {
			Kangaroo.listOfKangaroo.remove(localPlayerMoveEvent.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	public void onDamage(final EntityDamageEvent localEntityDamageEvent) {
		if (localEntityDamageEvent.getEntity() instanceof Player) {
			final Player localPlayer = (Player) localEntityDamageEvent.getEntity();
			final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
			if (localGamer.getKit() == this && localEntityDamageEvent.getCause() == EntityDamageEvent.DamageCause.FALL
					&& localEntityDamageEvent.getDamage() > 12.0) {
				localEntityDamageEvent.setDamage(12.0);
			}
		}
	}
}
