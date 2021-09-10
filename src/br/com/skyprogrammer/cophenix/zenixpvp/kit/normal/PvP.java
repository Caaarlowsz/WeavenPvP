package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class PvP extends Kit {
	public PvP() {
		super("PvP", Material.STONE_SWORD, 0, 0, 0, false, new String[] { "",
				"Kit sem habilidades por\u00e9m voc\u00ea", "recebe uma espada ja com sharpness 1." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}
}
