package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Thor extends Kit {
	public Thor() {
		super("Thor", Material.GOLD_AXE, 0, 8000, 9, true,
				new String[] { "", "Com seu machado fa\u00e7a cair um raio", "para onde voce estiver olhando." });
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
			CooldownHandler.addCooldown(localPlayer, this.getCooldown());
			final Block blockToThor = localPlayer.getTargetBlock(null, 100).getRelative(BlockFace.UP);
			localPlayer.getWorld().strikeLightning(blockToThor.getLocation());
			new BukkitRunnable() {
				public void run() {
					if (blockToThor.getType() == Material.FIRE) {
						blockToThor.setType(Material.AIR);
					}
				}
			}.runTaskLater((Plugin) Handler.getInstance(), 25L);
		}
	}
}
