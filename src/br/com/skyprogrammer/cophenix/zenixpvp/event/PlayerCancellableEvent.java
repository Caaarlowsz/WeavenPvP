package br.com.skyprogrammer.cophenix.zenixpvp.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public class PlayerCancellableEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList instanceOfHandlerList;
    private boolean localBoolean;
    
    static {
        instanceOfHandlerList = new HandlerList();
    }
    
    public PlayerCancellableEvent(final Player playerOfEvent) {
        super(playerOfEvent);
        this.localBoolean = false;
    }
    
    public boolean isCancelled() {
        return this.localBoolean;
    }
    
    public void setCancelled(final boolean booleanToCancel) {
        this.localBoolean = booleanToCancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerCancellableEvent.instanceOfHandlerList;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerCancellableEvent.instanceOfHandlerList;
    }
}
