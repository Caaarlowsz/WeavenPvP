package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.util.HashMap;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Ninja extends Kit
{
    public static HashMap<Player, Player> hashMapOfNinja;
    
    static {
        Ninja.hashMapOfNinja = new HashMap<Player, Player>();
    }
    
    public Ninja() {
        super("Ninja", Material.EMERALD, 0, 8900, 10, true, new String[] { "", "D\u00ea um hit em seu oponente", "e ao agachar (shift) voc\u00ea ser\u00e1", "teleportado para a localiza\u00e7\u00e3o dele." });
    }
    
    @Override
    public void removeAbilityIfHas(final Player playerToRemove) {
        if (Ninja.hashMapOfNinja.containsKey(playerToRemove)) {
            Ninja.hashMapOfNinja.remove(playerToRemove);
        }
    }
    
    @Override
    public boolean hasItem() {
        return false;
    }
    
    @EventHandler
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent localEntityDamageByEntityEvent) {
        if (localEntityDamageByEntityEvent.getEntity() instanceof Player && localEntityDamageByEntityEvent.getDamager() instanceof Player) {
            final Player entityDamaged = (Player)localEntityDamageByEntityEvent.getEntity();
            final Player entityDamager = (Player)localEntityDamageByEntityEvent.getDamager();
            final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(entityDamager.getUniqueId());
            if (localGamer.getKit() == this) {
                if (localEntityDamageByEntityEvent.isCancelled()) {
                    return;
                }
                Ninja.hashMapOfNinja.put(entityDamager, entityDamaged);
            }
        }
    }
    
    @EventHandler
    public void onPlayerToggleSneak(final PlayerToggleSneakEvent localPlayerToggleSneakEvent) {
        final Player localPlayer = localPlayerToggleSneakEvent.getPlayer();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (localGamer.getKit() == this) {
            if (!Ninja.hashMapOfNinja.containsKey(localPlayer)) {
                localPlayer.sendMessage("§c§l" + this.getName() + "§c Voc\u00ea n\u00e3o §fHITOU§c nenhum player!");
                return;
            }
            if (Ninja.hashMapOfNinja.get(localPlayer) == null) {
                localPlayer.sendMessage("§c§l" + this.getName() + "§c Este player est\u00e1 §fOFFLINE!");
                return;
            }
            if (Ninja.hashMapOfNinja.get(localPlayer).getLocation().distance(localPlayer.getLocation()) > 80.0) {
                localPlayer.sendMessage("§c§l" + this.getName() + "§c Este player est\u00e1 §fMUITO LONGE!");
                return;
            }
            if (CooldownHandler.onCooldown(localPlayer)) {
                CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
                return;
            }
            CooldownHandler.addCooldown(localPlayer, this.getCooldown());
            final Player playerToTeleport = Ninja.hashMapOfNinja.get(localPlayer);
            localPlayer.teleport((Entity)playerToTeleport);
            localPlayer.sendMessage("§2§l" + this.getName() + "§2 Voc\u00ea foi §fTELEPORTADO§2 para o player §f" + playerToTeleport.getName());
            localPlayer.playSound(localPlayer.getLocation(), Sound.ENDERMAN_TELEPORT, 1.5f, 1.5f);
        }
    }
}
