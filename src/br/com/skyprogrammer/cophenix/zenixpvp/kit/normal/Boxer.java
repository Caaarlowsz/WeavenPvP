package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Boxer extends Kit
{
    public Boxer() {
        super("Boxer", Material.IRON_SWORD, 0, 10500, 0, true, new String[] { "", "D\u00ea 0.25 de dano \u00e1 mais em seus", "oponentes e receba 0.25 de dano a menos." });
    }
    
    @Override
    public void removeAbilityIfHas(final Player playerToRemove) {
    }
    
    @Override
    public boolean hasItem() {
        return false;
    }
    
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
        if (localEntityDamageByEntityEvent.getEntity() instanceof Player && localEntityDamageByEntityEvent.getDamager() instanceof Player) {
            final Player localDamaged = (Player)localEntityDamageByEntityEvent.getEntity();
            final Player localDamager = (Player)localEntityDamageByEntityEvent.getDamager();
            final Gamer localGamerDamaged = Handler.getManager().getGamerManager().getGamer(localDamaged.getUniqueId());
            final Gamer localGamerDamager = Handler.getManager().getGamerManager().getGamer(localDamager.getUniqueId());
            double doubleOfTheDamage = localEntityDamageByEntityEvent.getDamage();
            if (localGamerDamager.getKit() == this) {
                doubleOfTheDamage += 0.25;
            }
            if (localGamerDamaged.getKit() == this) {
                doubleOfTheDamage -= 0.25;
            }
            localEntityDamageByEntityEvent.setDamage(doubleOfTheDamage);
        }
    }
}
