package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CooldownHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Hulk extends Kit
{
    public Hulk() {
        super("Hulk", Material.SADDLE, 0, 4500, 12, true, new String[] { "", "Com seu item pegue seu oponente", "e coloque-o em sua cabe\u00e7a para", "poder hita-lo avontade sem ele", "poder hitar voc\u00ea." });
    }
    
    @Override
    public void removeAbilityIfHas(final Player playerToRemove) {
    }
    
    @Override
    public boolean hasItem() {
        return true;
    }
    
    @EventHandler
    public void onInteractEntity(final PlayerInteractEntityEvent localPlayerInteractEntityEvent) {
        final Player localPlayer = localPlayerInteractEntityEvent.getPlayer();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (localGamer.getKit() == this && localPlayer.getItemInHand().getType() == this.getMaterial() && localPlayerInteractEntityEvent.getRightClicked() instanceof Player) {
            if (CooldownHandler.onCooldown(localPlayer)) {
                CooldownHandler.sendCooldownMessage(localPlayer, this.getName());
                return;
            }
            if (localPlayer.getPassenger() != null) {
                localPlayer.sendMessage("§c§l" + this.getName() + "§c Voc\u00ea j\u00e1 possui §fALGUEM§c em sua §fCABE\u00c7A!");
                return;
            }
            CooldownHandler.addCooldown(localPlayer, this.getCooldown());
            localPlayer.setPassenger(localPlayerInteractEntityEvent.getRightClicked());
            localPlayer.sendMessage("§2§l" + this.getName() + "§2 Voc\u00ea §fPEGOU§2 o player §f" + ((Player)localPlayerInteractEntityEvent.getRightClicked()).getName());
            ((Player)localPlayerInteractEntityEvent.getRightClicked()).sendMessage("§c§l" + this.getName() + "§c Voc\u00ea foi §fPEGO§c pelo player §f" + localPlayer.getName());
        }
    }
}
