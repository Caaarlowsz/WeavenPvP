package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Grandpa extends Kit {
	public Grandpa() {
		super("Grandpa", Material.STICK, 0, 3500, 0, true,
				new String[] { "", "Tenha um stick com repuls\u00e3o 2", "para isolar seus oponentes." });
	}

	@Override
	public void removeAbilityIfHas(final Player playerToRemove) {
	}

	@Override
	public boolean hasItem() {
		return true;
	}
}
