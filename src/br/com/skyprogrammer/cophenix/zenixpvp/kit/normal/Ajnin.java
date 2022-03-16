package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Ajnin extends Kit {
	public static HashMap<Player, Player> hashMapOfAjnin;

	static {
		Ajnin.hashMapOfAjnin = new HashMap<Player, Player>();
	}

	public Ajnin() {
		super("Ajnin", Material.NETHER_STAR, 0, 7500, 10, true, new String[] { "", "D\u00ea um hit em seu oponente",
				"e ao agachar (shift) ele ser\u00e1", "teleportado para sua localiza\u00e7ao." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
		if (Ajnin.hashMapOfAjnin.containsKey(playerToRemove)) {
			Ajnin.hashMapOfAjnin.remove(playerToRemove);
		}
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	@EventHandler
	public void onEntityDamageByEntity(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
		if (localEntityDamageByEntityEvent.getEntity() instanceof Player
				&& localEntityDamageByEntityEvent.getDamager() instanceof Player) {
			final Player entityDamaged = (Player) localEntityDamageByEntityEvent.getEntity();
			final Player entityDamager = (Player) localEntityDamageByEntityEvent.getDamager();
			final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(entityDamager.getUniqueId());
			if (localGamer.getKit() == this) {
				if (localEntityDamageByEntityEvent.isCancelled()) {
					return;
				}
				Ajnin.hashMapOfAjnin.put(entityDamager, entityDamaged);
			}
		}
	}

	@EventHandler
	public void onPlayerToggleSneak(final PlayerToggleSneakEvent localPlayerToggleSneakEvent) {
		final Player localPlayer = localPlayerToggleSneakEvent.getPlayer();
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this) {
			if (!Ajnin.hashMapOfAjnin.containsKey(localPlayer)) {
				localPlayer.sendMessage("�c�l" + this.getName() + "�c Voc\u00ea n\u00e3o �fHITOU�c nenhum player!");
				return;
			}
			if (Ajnin.hashMapOfAjnin.get(localPlayer) == null) {
				localPlayer.sendMessage("�c�l" + this.getName() + "�c Este player est\u00e1 �fOFFLINE!");
				return;
			}
			if (Ajnin.hashMapOfAjnin.get(localPlayer).getLocation().distance(localPlayer.getLocation()) > 80.0) {
				localPlayer.sendMessage("�c�l" + this.getName() + "�c Este player est\u00e1 �fMUITO LONGE!");
				return;
			}
			if (WeavenPvP.getManager().getProtectionManager().isProtected(Ajnin.hashMapOfAjnin.get(localPlayer))) {
				return;
			}
			if (CooldownHandler.onCooldown(localPlayer)) {
				CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
				return;
			}
			CooldownHandler.addCooldown(localPlayer, this.getCooldown());
			final Player playerToTeleport = Ajnin.hashMapOfAjnin.get(localPlayer);
			playerToTeleport.teleport((Entity) localPlayer);
			localPlayer.sendMessage("�2�l" + this.getName() + "�2 Voc\u00ea teleportou o player �f"
					+ playerToTeleport.getName() + "�2 para sua localiza\u00e7\u00e3o!");
			localPlayer.playSound(localPlayer.getLocation(), Sound.ENDERMAN_TELEPORT, 1.5f, 1.5f);
		}
	}
}
