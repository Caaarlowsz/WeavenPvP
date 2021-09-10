package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Vector;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;
import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.constructor.GrapplerHook;
import net.minecraft.server.v1_7_R4.EntityHuman;

public class Grappler extends Kit {
	private HashMap<UUID, GrapplerHook> hashMapOfGrappler;

	public Grappler() {
		super("Grappler", Material.LEASH, 0, 10000, 7, true, new String[] { "", "Tenha um la\u00e7o com uma corda que",
				"agarra em qualquer coisa podendo", "ir ou escalar em qualquer lugar." });
		this.hashMapOfGrappler = new HashMap<UUID, GrapplerHook>();
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
		if (this.hashMapOfGrappler.containsKey(playerToRemove.getUniqueId())) {
			this.hashMapOfGrappler.remove(playerToRemove.getUniqueId());
		}
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	private void inPlayerItemHeldGrappler(final PlayerItemHeldEvent localPlayerItemHeldEvent) {
		if (this.hashMapOfGrappler.containsKey(localPlayerItemHeldEvent.getPlayer().getUniqueId())) {
			this.hashMapOfGrappler.get(localPlayerItemHeldEvent.getPlayer().getUniqueId()).remove();
			this.hashMapOfGrappler.remove(localPlayerItemHeldEvent.getPlayer().getUniqueId());
		}
	}

	@EventHandler
	private void onInteractGrappler(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player player = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(player.getUniqueId());
		if (player.getItemInHand().getType() == this.getMaterial() && localGamer.getKit() == this) {
			localPlayerInteractEvent.setCancelled(true);
			if (CooldownHandler.onCooldown(player)) {
				player.setVelocity(new Vector(0.0, -1.0, 0.0));
				CooldownHandler.sendCooldownMessage(player, this.getName());
				return;
			}
			final Location location = player.getLocation();
			if (localPlayerInteractEvent.getAction() == Action.LEFT_CLICK_AIR
					|| localPlayerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
				if (this.hashMapOfGrappler.containsKey(player.getUniqueId())) {
					this.hashMapOfGrappler.get(player.getUniqueId()).remove();
				}
				final Object localObject = location.getDirection();
				final GrapplerHook localGrapplerHook = new GrapplerHook(player.getWorld(),
						(EntityHuman) ((CraftPlayer) player).getHandle());
				localGrapplerHook.spawn(player.getEyeLocation().add(((Vector) localObject).getX(),
						((Vector) localObject).getY(), ((Vector) localObject).getZ()));
				localGrapplerHook.move(5.0 * ((Vector) localObject).getX(), 5.0 * ((Vector) localObject).getY(),
						5.0 * ((Vector) localObject).getZ());
				this.hashMapOfGrappler.put(player.getUniqueId(), localGrapplerHook);
			} else if (this.hashMapOfGrappler.containsKey(player.getUniqueId())
					&& this.hashMapOfGrappler.get(player.getUniqueId()).isHooked()) {
				final Object localObject = this.hashMapOfGrappler.get(player.getUniqueId()).getBukkitEntity()
						.getLocation();
				final double d1 = ((Location) localObject).distance(location);
				final double d2 = (1.0 + 0.07 * d1) * (((Location) localObject).getX() - location.getX()) / d1;
				final double d3 = (1.0 + 0.03 * d1) * (((Location) localObject).getY() - location.getY()) / d1;
				final double d4 = (1.0 + 0.07 * d1) * (((Location) localObject).getZ() - location.getZ()) / d1;
				player.setVelocity(new Vector(d2, d3, d4));
			}
		}
	}

	@EventHandler
	public void onGrapplerCombat(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
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
	public void onGrapplerFallDamage(final EntityDamageEvent localEntityDamageEvent) {
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
