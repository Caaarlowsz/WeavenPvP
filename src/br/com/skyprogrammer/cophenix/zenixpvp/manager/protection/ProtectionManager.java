package br.com.skyprogrammer.cophenix.zenixpvp.manager.protection;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.entity.Player;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import java.util.UUID;
import java.util.HashMap;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.ListenerHandler;

public class ProtectionManager extends ListenerHandler
{
    private HashMap<UUID, Boolean> a;
    private boolean worldPvP;
    private boolean worldDamage;
    
    public ProtectionManager(final Handler instanceOfHandler) {
        super(instanceOfHandler);
        this.worldPvP = true;
        this.worldDamage = true;
        this.a = new HashMap<UUID, Boolean>();
    }
    
    public boolean isProtected(final Player playerToCheck) {
        return this.a.get(playerToCheck.getUniqueId());
    }
    
    public void setProtected(final Player playerOfAction, final boolean booleanToProtect) {
        if (this.a.containsKey(playerOfAction.getUniqueId())) {
            this.a.remove(playerOfAction.getUniqueId());
        }
        this.a.put(playerOfAction.getUniqueId(), booleanToProtect);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
        if (localEntityDamageByEntityEvent.getEntity() instanceof Player && localEntityDamageByEntityEvent.getDamager() instanceof Player) {
            final Player localEntity = (Player)localEntityDamageByEntityEvent.getEntity();
            final Player damager = (Player)localEntityDamageByEntityEvent.getDamager();
            final Gamer gamer = Handler.getManager().getGamerManager().getGamer(damager.getUniqueId());
            if (gamer.getWarp().equalsIgnoreCase("1v1")) {
                if (!X1WarpListener.playerfigh.containsKey(damager)) {
                    localEntityDamageByEntityEvent.setCancelled(true);
                }
                else if (!X1WarpListener.playerfigh.get(damager).equalsIgnoreCase(localEntity.getName())) {
                    localEntityDamageByEntityEvent.setCancelled(true);
                }
            }
            else if (this.isProtected(localEntity) || !this.worldDamage || !this.worldPvP) {
                localEntityDamageByEntityEvent.setCancelled(true);
            }
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(final EntityDamageEvent localEntityDamageEvent) {
        if (localEntityDamageEvent.getEntity() instanceof Player) {
            final Player localEntity = (Player)localEntityDamageEvent.getEntity();
            final Gamer gamer = Handler.getManager().getGamerManager().getGamer(localEntity.getUniqueId());
            if (gamer.getWarp().equalsIgnoreCase("1v1")) {
                if (!X1WarpListener.playerfigh.containsKey(localEntity)) {
                    localEntityDamageEvent.setCancelled(true);
                }
            }
            else if (this.isProtected(localEntity) || !this.worldDamage) {
                localEntityDamageEvent.setCancelled(true);
            }
        }
    }
}
