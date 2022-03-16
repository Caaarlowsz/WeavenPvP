package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Monk extends Kit {
	public Monk() {
		super("Monk", Material.BLAZE_POWDER, 0, 3000, 12, true,
				new String[] { "", "Ao usar seu item bagun\u00e7e o inventario", "de seus oponentes os embolando",
						"e tendo a chance para mat\u00e1-los." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return true;
	}

	@EventHandler
	public void onPlayerInteractMonk(final PlayerInteractEntityEvent localPlayerInteractEntityEvent) {
		final Player localPlayer = localPlayerInteractEntityEvent.getPlayer();
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
		if (localPlayerInteractEntityEvent.getRightClicked() instanceof Player) {
			final Player localRightClickedPlayer = (Player) localPlayerInteractEntityEvent.getRightClicked();
			if (localPlayer.getItemInHand().getType() == Material.BLAZE_ROD && localGamer.getKit() == this) {
				if (CooldownHandler.onCooldown(localPlayer)) {
					CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
					return;
				}
				CooldownHandler.addCooldown(localPlayer, this.getCooldown());
				final int integerWhitRandomValue = new Random()
						.nextInt(localRightClickedPlayer.getInventory().getSize() - 10 + 1 + 10);
				final ItemStack randomItemStackOfInventory = localRightClickedPlayer.getInventory()
						.getItem(integerWhitRandomValue);
				final ItemStack newItemInHand = localRightClickedPlayer.getItemInHand();
				localRightClickedPlayer.setItemInHand(randomItemStackOfInventory);
				localRightClickedPlayer.getInventory().setItem(integerWhitRandomValue, newItemInHand);
				localPlayer.sendMessage("�2�l" + this.getName() + "�2 Voc\u00ea �fMONKOU�2 o player �f"
						+ localRightClickedPlayer.getName());
				localRightClickedPlayer.sendMessage("�c�l" + this.getName() + "�c O player �f" + localPlayer.getName()
						+ "�c acabou de �fBAGUN\u00c7AR�c seu �fINVENTARIO!");
			}
		}
	}
}
