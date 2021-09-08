package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.Random;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Reaper extends Kit
{
    public Reaper() {
        super("Reaper", Material.WOOD_AXE, 0, 7400, 0, true, new String[] { "", "A cada hite tenha 30% de chance de", "seu oponente pegar efeito de whiter II." });
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
        if (localEntityDamageByEntityEvent.getEntity() instanceof Player && localEntityDamageByEntityEvent.getDamager() instanceof Player) {
            final Player localDamaged = (Player)localEntityDamageByEntityEvent.getEntity();
            final Player localDamager = (Player)localEntityDamageByEntityEvent.getDamager();
            final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localDamager.getUniqueId());
            if (localGamer.getKit() == this) {
                final int integerOfChance = new Random().nextInt(100);
                if (integerOfChance > 0 && integerOfChance < 31) {
                    localDamaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 160, 1));
                }
            }
        }
    }
}
