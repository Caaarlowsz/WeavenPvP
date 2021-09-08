package br.com.skyprogrammer.cophenix.zenixpvp.kit.normal;

import org.bukkit.event.EventHandler;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;

public class Fisherman extends Kit
{
    public Fisherman() {
        super("Fisherman", Material.FISHING_ROD, 0, 5000, 0, true, new String[] { "", "Com sua vara, pesque seus inimigos", "e traga eles para a sua localiza\u00e7\u00e3o." });
    }
    
    @Override
    public void removeAbilityIfHas(final Player playerToRemove) {
    }
    
    @Override
    public boolean hasItem() {
        return true;
    }
    
    @EventHandler
    public void onPlayerFish(final PlayerFishEvent localPlayerFishEvent) {
        final Player localPlayer = localPlayerFishEvent.getPlayer();
        final Entity localCaughtedEntity = localPlayerFishEvent.getCaught();
        final Block localBlockOfHook = localPlayerFishEvent.getHook().getLocation().getBlock();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (localCaughtedEntity != null && localCaughtedEntity != localBlockOfHook && localGamer.getKit() == this) {
            localCaughtedEntity.teleport(localPlayer.getPlayer().getLocation());
        }
    }
}
