package br.com.skyprogrammer.cophenix.zenixpvp.kit;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class None extends Kit {
	public None() {
		super("Nenhum", Material.STONE, 0, 0, 0, false, new String[] { "" });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return false;
	}
}
