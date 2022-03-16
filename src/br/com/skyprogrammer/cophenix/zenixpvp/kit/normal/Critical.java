package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import java.util.Random;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Critical extends Kit {
	public Critical() {
		super("Critical", Material.REDSTONE, 0, 9500, 0, true, new String[] { "", "A cada hit em seu oponente tenha",
				"30% de chance de dar um golpe critico", "que aumenta 2.50 \u00e1 mais do dano recebido." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
		if (localEntityDamageByEntityEvent.getEntity() instanceof Player
				&& localEntityDamageByEntityEvent.getDamager() instanceof Player) {
			final Player localDamaged = (Player) localEntityDamageByEntityEvent.getEntity();
			final Player localDamager = (Player) localEntityDamageByEntityEvent.getDamager();
			final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localDamager.getUniqueId());
			if (localGamer.getKit() == this) {
				final int integerOfChance = new Random().nextInt(100);
				if (integerOfChance > 0 && integerOfChance < 31) {
					localDamager.getWorld().playEffect(localDamager.getLocation(), Effect.STEP_SOUND,
							(Object) Material.REDSTONE_BLOCK, 10);
					localEntityDamageByEntityEvent.setDamage(localEntityDamageByEntityEvent.getDamage() + 2.5);
					localDamaged.sendMessage("�c�l" + this.getName()
							+ "�c Voc\u00ea recebeu um golpe �fCRITICO�c do player �f" + localDamager.getName());
					localDamager.sendMessage("�2�l" + this.getName()
							+ "�2 Voc\u00ea deu um golpe �fCRITICO�2 no player �f" + localDamaged.getName());
				}
			}
		}
	}
}
