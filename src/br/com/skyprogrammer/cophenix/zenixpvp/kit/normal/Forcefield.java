package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Forcefield extends Kit {
	private ArrayList<Player> arrayListOfForcefield;

	public Forcefield() {
		super("Forcefield", Material.NETHER_FENCE, 0, 14000, 50, true, new String[] { "", "Ao usar seu item ganhe as",
				"'habilidades' do hack forcefield e tenha", "um range de 6 blocos." });
		this.arrayListOfForcefield = new ArrayList<Player>();
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
		if (this.arrayListOfForcefield.contains(playerToRemove)) {
			this.arrayListOfForcefield.remove(playerToRemove);
		}
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onInteractForcefield(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player localPlayer = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this) {
			if (localPlayer.getItemInHand().getType() != this.getMaterial()) {
				if (this.arrayListOfForcefield.contains(localPlayer)) {
					for (final Entity entities : localPlayer.getNearbyEntities(6.0, 6.0, 6.0)) {
						double damageByHit = 4.0;
						if (localPlayer.getItemInHand().getType().toString().contains("_SWORD")) {
							damageByHit = 4.5;
						}
						if (entities instanceof Damageable) {
							((Damageable) entities).damage(damageByHit, (Entity) localPlayer);
							if (!(entities instanceof Player)) {
								continue;
							}
							((Player) entities).sendMessage("§c§l" + this.getName()
									+ "§c Voc\u00ea est\u00e1 no §fFORCEFIELD§c do player §f" + localPlayer.getName());
						}
					}
				}
			} else {
				if (CooldownHandler.onCooldown(localPlayer)) {
					CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
					return;
				}
				CooldownHandler.addCooldown(localPlayer, this.getCooldown());
				this.arrayListOfForcefield.add(localPlayer);
				localPlayer.sendMessage("§2§l" + this.getName()
						+ "§2 Voc\u00ea §fATIVOU§2 seu §fFORCEFIELD!§2 Bata com §fESPADA§2 para dar mais §fDANO!");
				Handler.getInstance().getServer().getScheduler().scheduleSyncDelayedTask((Plugin) Handler.getInstance(),
						() -> this.removeAbilityIfHas(localPlayer), 300L);
			}
		}
	}
}
