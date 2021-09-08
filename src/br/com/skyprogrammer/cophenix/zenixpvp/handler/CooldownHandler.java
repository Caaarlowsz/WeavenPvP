package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.Sound;
import org.bukkit.Bukkit;
import br.com.weaven.core.bukkit.event.update.UpdateEvent;
import org.bukkit.entity.Player;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownHandler extends ListenerHandler
{
    public static ConcurrentHashMap<UUID, Long> concurrentMapOfCooldown;
    
    static {
        CooldownHandler.concurrentMapOfCooldown = new ConcurrentHashMap<UUID, Long>();
    }
    
    public CooldownHandler(final Handler instanceOfHandler) {
        super(instanceOfHandler);
    }
    
    public static void addCooldown(final Player player, final double cooldown) {
        CooldownHandler.concurrentMapOfCooldown.put(player.getUniqueId(), (long)(System.currentTimeMillis() + cooldown * 1000.0));
    }
    
    public static void addCooldown(final Player playerToAdd, final int integerToTheTimeOfCooldown) {
        addCooldown(playerToAdd, (double)integerToTheTimeOfCooldown);
    }
    
    public static double getCooldown(final Player player) {
        return (double)(CooldownHandler.concurrentMapOfCooldown.contains(player.getUniqueId()) ? 0L : ((CooldownHandler.concurrentMapOfCooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 10L));
    }
    
    public static boolean onCooldown(final Player player) {
        return CooldownHandler.concurrentMapOfCooldown.containsKey(player.getUniqueId()) && getCooldown(player) / 100.0 >= 0.0;
    }
    
    public static void removeCooldown(final Player playerToRemove) {
        if (CooldownHandler.concurrentMapOfCooldown.containsKey(playerToRemove.getUniqueId())) {
            CooldownHandler.concurrentMapOfCooldown.remove(playerToRemove.getUniqueId());
        }
    }
    
    public static void sendCooldownMessage(final Player playerToSend, final String nameOfTheKit) {
        final double cooldown = getCooldown(playerToSend) / 100.0;
        playerToSend.sendMessage("§c§l" + nameOfTheKit + " §cVoc\u00ea est\u00e1 em §fCOOLDOWN§c de §f" + cooldown);
    }
    
    @EventHandler
    public void onUpdateEventCooldown(final UpdateEvent localUpdateEvent) {
        final Object o;
        int length;
        int i = 0;
        final Player[] array;
        Player localOnlinePlayers;
        long cooldown;
        Bukkit.getScheduler().runTaskAsynchronously((Plugin)Handler.getInstance(), () -> {
            Bukkit.getOnlinePlayers();
            for (length = o.length; i < length; ++i) {
                localOnlinePlayers = array[i];
                if (!(!CooldownHandler.concurrentMapOfCooldown.containsKey(localOnlinePlayers.getUniqueId()))) {
                    cooldown = CooldownHandler.concurrentMapOfCooldown.get(localOnlinePlayers.getUniqueId());
                    if (cooldown < System.currentTimeMillis()) {
                        CooldownHandler.concurrentMapOfCooldown.remove(localOnlinePlayers.getUniqueId());
                        localOnlinePlayers.playSound(localOnlinePlayers.getLocation(), Sound.ORB_PICKUP, 1.5f, 1.5f);
                        localOnlinePlayers.sendMessage("§2Voc\u00ea saiu de §fCOOLDOWN!");
                    }
                }
            }
        });
    }
}
