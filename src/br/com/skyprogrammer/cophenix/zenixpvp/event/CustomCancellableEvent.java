package br.com.skyprogrammer.cophenix.zenixpvp.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class CustomCancellableEvent extends Event implements Cancellable
{
    private static final HandlerList instanceOfHandlerList;
    private boolean localBoolean;
    
    static {
        instanceOfHandlerList = new HandlerList();
    }
    
    public HandlerList getHandlers() {
        return CustomCancellableEvent.instanceOfHandlerList;
    }
    
    public static HandlerList getHandlerList() {
        return CustomCancellableEvent.instanceOfHandlerList;
    }
    
    public boolean isCancelled() {
        return this.localBoolean;
    }
    
    public void setCancelled(final boolean booleanToCancelTheEvent) {
        this.localBoolean = booleanToCancelTheEvent;
    }
}
