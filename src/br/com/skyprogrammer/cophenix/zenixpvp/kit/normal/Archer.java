package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Archer extends Kit {
	public Archer() {
		super("Archer", Material.BOW, 0, 3500, 0, true, new String[] { "", "Seja um arqueiro com flechas poderosas",
				"para eliminar seus inimigos de", "perto ou de longe." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return true;
	}
}
