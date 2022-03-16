package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Flash extends Kit {
	public Flash() {
		super("Flash", Material.REDSTONE_TORCH_ON, 0, 8500, 30, true,
				new String[] { "", "Com seu item teleporte para o", "lugar que voce aponta rapidamente." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onPlayerInteract(final PlayerInteractEvent localPlayerInteractEvent) {
		final Player localPlayer = localPlayerInteractEvent.getPlayer();
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localGamer.getKit() == this && localPlayer.getItemInHand().getType() == this.getMaterial()) {
			localPlayerInteractEvent.setCancelled(true);
			if (CooldownHandler.onCooldown(localPlayer)) {
				CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
				return;
			}
			final Block localTargetBlock = localPlayer.getTargetBlock(null, 200).getRelative(BlockFace.UP);
			if (localTargetBlock.getType() == null) {
				localPlayer.sendMessage("�c�l" + this.getName() + "�c O bloco escolhido nao pode ser �fNULO!");
				return;
			}
			CooldownHandler.addCooldown(localPlayer, this.getCooldown());
			localPlayer.teleport(localTargetBlock.getLocation());
			localPlayer.sendMessage("�2�l" + this.getName() + "�2 Voc\u00ea foi �fTELEPORTADO!");
		}
	}
}
