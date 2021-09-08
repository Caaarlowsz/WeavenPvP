package br.com.skyprogrammer.cophenix.zenixpvp.listener;

import br.com.weaven.master.account.WavePlayer;
import br.com.weaven.core.bukkit.api.vanish.Vanish;
import org.bukkit.inventory.Inventory;
import org.bukkit.Material;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;
import org.bukkit.Bukkit;
import br.com.skyprogrammer.cophenix.zenixpvp.api.title.TitleAPI;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.WarpHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import java.util.UUID;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import java.util.concurrent.Executors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.Executor;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.ListenerHandler;

public class PlayerListener extends ListenerHandler
{
    private static final Executor executor;
    
    static {
        executor = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("Async Thread").build());
    }
    
    public PlayerListener(final Handler instanceOfHandler) {
        super(instanceOfHandler);
    }
    
    @EventHandler
    public void onAsyncPlayerPreLogin(final AsyncPlayerPreLoginEvent localAsyncPlayerPreLoginEvent) {
        final UUID localUniqueId = localAsyncPlayerPreLoginEvent.getUniqueId();
        final String localName = localAsyncPlayerPreLoginEvent.getName();
        final Gamer localGamer = new Gamer(localUniqueId, localName).setIsPremiumAccount(false);
        if (!Handler.getManager().getAccounts().isRegistered(localGamer.getName().toLowerCase())) {
            Handler.getManager().getAccounts().register(localGamer.getName().toLowerCase());
        }
        localGamer.update();
        Handler.getManager().getGamerManager().addGamer(localUniqueId, localGamer);
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent localPlayerJoinEvent) {
        localPlayerJoinEvent.setJoinMessage((String)null);
        final Player localPlayer = localPlayerJoinEvent.getPlayer();
        Handler.getManager().getScoreboardManager().createScoreboardToPlayer(localPlayer);
        if (Handler.getManager().getWarps().hasWarp("spawn")) {
            localPlayer.teleport(Handler.getManager().getWarps().getLocation("spawn"));
        }
        else {
            localPlayer.teleport(localPlayer.getWorld().getSpawnLocation());
        }
        WarpHandler.Spawn.setItensToPlayer(localPlayer);
        final TitleAPI localTitleAPI = new TitleAPI("§b§lCELTZ§3§lP§f§lVP", "§fBatalhe contra outros jogadores!");
        localTitleAPI.sendToPlayer(localPlayer);
        localPlayer.sendMessage(" ");
        localPlayer.sendMessage("§b§lCELTZ§3§lP§f§lVP");
        localPlayer.sendMessage("§fBatalhe contra outros jogadores!");
        localPlayer.sendMessage(" ");
        Bukkit.getScheduler().runTaskLater((Plugin)this.getHandler(), () -> X1WarpListener.update1v1Vanish(), 10L);
    }
    
    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent localPlayerQuitEvent) {
        localPlayerQuitEvent.setQuitMessage((String)null);
        final Player localPlayer = localPlayerQuitEvent.getPlayer();
        if (X1WarpListener.firstMatch == localPlayer.getUniqueId()) {
            X1WarpListener.firstMatch = null;
        }
        Handler.getManager().getGamerManager().removeGamer(localPlayer.getUniqueId());
    }
    
    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        final Player player = event.getPlayer();
        final Gamer gamer = Handler.getManager().getGamerManager().getGamer(player.getUniqueId());
        new BukkitRunnable() {
            public void run() {
                if (gamer.getWarp().equalsIgnoreCase("1v1")) {
                    player.performCommand("warp 1v1");
                    player.teleport(Handler.getManager().getWarps().getLocation("1v1"));
                }
                else {
                    WarpHandler.Spawn.setItensToPlayer(player);
                }
            }
        }.runTask((Plugin)Handler.getPlugin((Class)Handler.class));
    }
    
    public void checkKillStreak(final Player checker, final int i) {
        final String value = String.valueOf(i);
        if ((value.endsWith("0") || value.endsWith("5")) && i >= 10) {
            Bukkit.broadcastMessage("§4§lKILLSTREAK §1§l" + checker.getName() + " §fconseguiu um §6§lKILLSTREAK DE " + i);
        }
    }
    
    public void checkLostKillStreak(final Player killer, final Player loster, final int i) {
        if (i >= 10) {
            Bukkit.broadcastMessage("§4§lKILLSTREAK §1§l" + loster.getName() + "§f perdeu seu §6§lKILLSTREAK DE " + i + " PARA §c§l" + killer.getName());
        }
    }
    
    @EventHandler
    public void onDeath(final PlayerDeathEvent event) {
        event.setDeathMessage((String)null);
        if (event.getEntity() instanceof Player) {
            final Player player = event.getEntity();
            final Gamer gamer = Handler.getManager().getGamerManager().getGamer(player.getUniqueId());
            player.getWorld().strikeLightningEffect(player.getLocation());
            if (event.getEntity().getKiller() instanceof Player) {
                final Player killer = event.getEntity().getKiller();
                final Gamer gamerKiller = Handler.getManager().getGamerManager().getGamer(killer.getUniqueId());
                if (gamerKiller.getWarp().equalsIgnoreCase("1v1") && X1WarpListener.playerfigh.containsKey(player)) {
                    event.getDrops().clear();
                    X1WarpListener.fighting.remove(killer);
                    X1WarpListener.fighting.remove(player);
                    X1WarpListener.playerfigh.remove(player);
                    X1WarpListener.playerfigh.remove(killer);
                    final int sopsK = X1WarpListener.itemsInInventory((Inventory)killer.getInventory(), new Material[] { Material.MUSHROOM_SOUP });
                    X1WarpListener.defaultItens(killer);
                    killer.teleport(Handler.getManager().getWarps().getLocation("1v1"));
                    if (X1WarpListener.batalhando.containsKey(player)) {
                        X1WarpListener.batalhando.remove(player);
                    }
                    if (X1WarpListener.batalhando.containsKey(killer)) {
                        X1WarpListener.batalhando.remove(killer);
                    }
                    Vanish.updateVanished(killer);
                    Vanish.updateVanished(player);
                    killer.sendMessage("§cVoce venceu o 1v1 contra " + player.getName() + " com " + X1WarpListener.cora(killer) + " cora\u00e7oes e " + sopsK + " sopas restantes");
                    player.sendMessage("§c" + killer.getName() + " venceu o 1v1 com " + X1WarpListener.cora(killer) + " cora\u00e7oes e " + sopsK + " sopas restantes");
                    killer.setHealth(20.0);
                }
                final Gamer gamer2;
                final Player player2;
                final Player loster;
                final WavePlayer accKiller;
                final Gamer gamer3;
                PlayerListener.executor.execute(() -> {
                    gamer2.setKillStreak(0);
                    gamer2.setDeaths(gamer2.getDeaths() + 1);
                    gamer2.updateData();
                    this.checkLostKillStreak(player2, loster, gamer2.getKillStreak());
                    accKiller = WavePlayer.getPlayer(player2.getUniqueId());
                    accKiller.addXp(17);
                    accKiller.addMoney(80);
                    gamer3.setKills(gamer3.getKills() + 1);
                    gamer3.setKillStreak(gamer3.getKillStreak() + 1);
                    player2.sendMessage("§6§lKILL§f Voc\u00ea matou §e§l" + loster.getName() + "§f!");
                    player2.sendMessage("§9§lXP§f Voc\u00ea recebeu §9§l17XP§f!");
                    player2.sendMessage("§6§lCOINS§f Voc\u00ea recebeu §b§l80COINS§f!");
                    loster.sendMessage("§4§lDEATH§f Voc\u00ea §c§lMORREU§f para §c" + player2.getName());
                    this.checkKillStreak(player2, gamer3.getKillStreak());
                    accKiller.update();
                    gamer3.updateData();
                    return;
                });
            }
            Bukkit.getScheduler().runTaskLater((Plugin)Handler.getInstance(), () -> player.spigot().respawn(), 1L);
        }
    }
}
