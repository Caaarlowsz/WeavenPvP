package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class AntiStomper extends Kit
{
    public AntiStomper() {
        super("AntiStomper", Material.DIAMOND_HELMET, 0, 3500, 0, true, new String[] { "", "Leve dano extremamente reduzido", "para players com o kit Stomper." });
    }
    
    @Override
    public void removeAbilityIfHas(final Player playerToRemove) {
    }
    
    @Override
    public boolean hasItem() {
        return false;
    }
}
