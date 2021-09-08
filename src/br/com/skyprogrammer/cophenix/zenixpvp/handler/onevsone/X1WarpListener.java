package br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventPriority;
import br.com.weaven.core.bukkit.api.vanish.Vanish;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.entity.Damageable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Arrays;
import org.bukkit.inventory.Inventory;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.plugin.Plugin;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.entity.Player;
import java.util.HashMap;
import org.bukkit.event.Listener;

public final class X1WarpListener implements Listener
{
    public static final HashMap<Player, String> batalhando;
    public static boolean wait;
    public static UUID firstMatch;
    public static UUID secondMatch;
    public static UUID thirdMatch;
    public static final ArrayList<Player> cooldown;
    public static final HashMap<Player, Player> challenge;
    public static final HashMap<Player, Player> challengec;
    public static final ArrayList<Player> fighting;
    public static final HashMap<Player, String> playerfigh;
    
    static {
        batalhando = new HashMap<Player, String>();
        X1WarpListener.wait = false;
        cooldown = new ArrayList<Player>();
        challenge = new HashMap<Player, Player>();
        challengec = new HashMap<Player, Player>();
        fighting = new ArrayList<Player>();
        playerfigh = new HashMap<Player, String>();
    }
    
    public static ItemStack searchingItem() {
        final ItemStack i = new ItemStack(351, 1, (short)10);
        final ItemMeta ik = i.getItemMeta();
        ik.setDisplayName("§e§lProcurando partidas");
        i.setItemMeta(ik);
        return i;
    }
    
    public static ItemStack customItem() {
        final ItemStack i = new ItemStack(Material.IRON_FENCE, 1, (short)0);
        final ItemMeta ik = i.getItemMeta();
        ik.setDisplayName("§b§l1v1 Customizado");
        i.setItemMeta(ik);
        return i;
    }
    
    public static ItemStack backItem() {
        final ItemStack i = new ItemStack(351, 1, (short)8);
        final ItemMeta ik = i.getItemMeta();
        ik.setDisplayName("§e§l1v1 R\u00e1pido");
        i.setItemMeta(ik);
        return i;
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent e) {
        final ItemStack i = e.getPlayer().getItemInHand();
        if (i.getType() == Material.getMaterial(351)) {
            if (i.getItemMeta().getDisplayName().equals("§e§l1v1 R\u00e1pido")) {
                e.getPlayer().updateInventory();
                e.getPlayer().setItemInHand(searchingItem());
                e.getPlayer().updateInventory();
                e.getPlayer().sendMessage("§eO 1v1 R\u00e1pido est\u00e1 procurando algu\u00e9m para voc\u00ea batalhar!");
                X1WarpListener.secondMatch = null;
                if (X1WarpListener.firstMatch == null) {
                    X1WarpListener.firstMatch = e.getPlayer().getUniqueId();
                    return;
                }
                final Player findToChallenge = Bukkit.getPlayer(X1WarpListener.firstMatch);
                teleportToFight(e.getPlayer(), findToChallenge);
                X1WarpListener.fighting.add(e.getPlayer());
                X1WarpListener.fighting.add(findToChallenge);
                X1WarpListener.playerfigh.put(e.getPlayer(), findToChallenge.getName());
                X1WarpListener.playerfigh.put(findToChallenge, e.getPlayer().getName());
                X1WarpListener.batalhando.put(e.getPlayer(), findToChallenge.getName());
                X1WarpListener.batalhando.put(findToChallenge, e.getPlayer().getName());
                e.getPlayer().sendMessage("§9O 1v1 R\u00e1pido encontrou algu\u00e9m para voc\u00ea lutar! O player escolhido foi §e" + findToChallenge.getName());
                findToChallenge.sendMessage("§9O 1v1 R\u00e1pido encontrou algu\u00e9m para voc\u00ea lutar! O player escolhido foi §e" + e.getPlayer().getName());
                X1WarpListener.firstMatch = null;
                X1WarpListener.secondMatch = null;
            }
            else if (i.getItemMeta().getDisplayName().equals("§e§lProcurando partidas")) {
                X1WarpListener.firstMatch = null;
                X1WarpListener.secondMatch = null;
                e.getPlayer().setItemInHand(backItem());
                e.getPlayer().updateInventory();
                e.getPlayer().sendMessage("§eO 1v1 R\u00e1pido parou de procurar algu\u00e9m para voc\u00ea batalhar!");
            }
        }
    }
    
    @EventHandler
    public void onChallengeInteract(final PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        final Player challenged = (Player)e.getRightClicked();
        final Gamer g1 = Handler.getManager().getGamerManager().getGamer(e.getPlayer().getUniqueId());
        final Gamer g2 = Handler.getManager().getGamerManager().getGamer(challenged.getUniqueId());
        final ItemStack i = e.getPlayer().getItemInHand();
        if (g1.getWarp().equalsIgnoreCase("1v1") && g2.getWarp().equalsIgnoreCase("1v1")) {
            if (e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD && i.getItemMeta().getDisplayName().equals("§6§l1v1 Normal")) {
                if (X1WarpListener.cooldown.contains(e.getPlayer())) {
                    e.getPlayer().sendMessage("§eAguarde para desafiar novamente");
                    return;
                }
                if (X1WarpListener.challenge.containsKey(challenged) && X1WarpListener.challenge.get(challenged) == e.getPlayer()) {
                    if (X1WarpListener.firstMatch == e.getPlayer().getUniqueId()) {
                        X1WarpListener.firstMatch = null;
                    }
                    if (X1WarpListener.firstMatch == challenged.getUniqueId()) {
                        X1WarpListener.firstMatch = null;
                    }
                    teleportToFight(e.getPlayer(), challenged);
                    X1WarpListener.fighting.add(e.getPlayer());
                    X1WarpListener.fighting.add(challenged);
                    X1WarpListener.playerfigh.put(e.getPlayer(), challenged.getName());
                    X1WarpListener.playerfigh.put(challenged, e.getPlayer().getName());
                    X1WarpListener.batalhando.put(challenged, e.getPlayer().getName());
                    X1WarpListener.batalhando.put(e.getPlayer(), challenged.getName());
                    challenged.sendMessage("§b" + e.getPlayer().getName() + "§2 aceitou seu desafio");
                    e.getPlayer().sendMessage("§2Voce aceitou o desafio de §b" + challenged.getName());
                    X1WarpListener.challenge.remove(challenged);
                    if (X1WarpListener.challenge.containsKey(e.getPlayer())) {
                        X1WarpListener.challenge.remove(e.getPlayer());
                    }
                    return;
                }
                if (X1WarpListener.playerfigh.containsKey(challenged)) {
                    return;
                }
                e.getPlayer().sendMessage("§7Voc\u00ea enviou um desafio de 1v1 normal para §b" + challenged.getName());
                challenged.sendMessage("§eVoc\u00ea recebeu desafio de 1v1 normal de §7" + e.getPlayer().getName());
                X1WarpListener.cooldown.add(e.getPlayer());
                X1WarpListener.challenge.put(e.getPlayer(), challenged);
                Bukkit.getScheduler().runTaskLater((Plugin)Handler.getInstance(), (Runnable)new Runnable() {
                    @Override
                    public void run() {
                        if (X1WarpListener.cooldown.contains(e.getPlayer())) {
                            X1WarpListener.cooldown.remove(e.getPlayer());
                        }
                        if (X1WarpListener.challenge.containsKey(e.getPlayer())) {
                            X1WarpListener.challenge.remove(e.getPlayer());
                        }
                    }
                }, 100L);
            }
            if (e.getPlayer().getItemInHand().getType() == Material.IRON_FENCE && i.getItemMeta().getDisplayName().equals("§b§l1v1 Customizado")) {
                if (X1WarpListener.cooldown.contains(e.getPlayer())) {
                    e.getPlayer().sendMessage("§eAguarde para desafiar novamente");
                    return;
                }
                if (X1WarpListener.challengec.containsKey(challenged) && X1WarpListener.challengec.get(challenged) == e.getPlayer()) {
                    if (X1WarpListener.firstMatch == e.getPlayer().getUniqueId()) {
                        X1WarpListener.firstMatch = null;
                    }
                    if (X1WarpListener.firstMatch == challenged.getUniqueId()) {
                        X1WarpListener.firstMatch = null;
                    }
                    teleportToCustomFight(challenged, e.getPlayer());
                    X1WarpListener.fighting.add(e.getPlayer());
                    X1WarpListener.fighting.add(challenged);
                    X1WarpListener.playerfigh.put(e.getPlayer(), challenged.getName());
                    X1WarpListener.playerfigh.put(challenged, e.getPlayer().getName());
                    X1WarpListener.batalhando.put(challenged, e.getPlayer().getName());
                    X1WarpListener.batalhando.put(e.getPlayer(), challenged.getName());
                    challenged.sendMessage("§b" + e.getPlayer().getName() + "§2 aceitou seu desafio");
                    e.getPlayer().sendMessage("§2Voce aceitou o desafio de §b" + challenged.getName());
                    X1WarpListener.challengec.remove(challenged);
                    if (X1WarpListener.challengec.containsKey(e.getPlayer())) {
                        X1WarpListener.challengec.remove(e.getPlayer());
                    }
                    if (X1WarpListener.challenge.containsKey(e.getPlayer())) {
                        X1WarpListener.challenge.remove(e.getPlayer());
                    }
                    if (X1WarpListener.challenge.containsKey(challenged)) {
                        X1WarpListener.challenge.remove(challenged);
                    }
                    return;
                }
                if (X1WarpListener.playerfigh.containsKey(challenged)) {
                    return;
                }
                Inventory1v1Custom.setDefaultCustoms(e.getPlayer(), challenged.getName());
                Inventory1v1Custom.openCustomInventory(e.getPlayer(), challenged);
            }
        }
    }
    
    public static int itemsInInventory(final Inventory inventory, final Material[] search) {
        final List wanted = Arrays.asList(search);
        int found = 0;
        ItemStack[] arrayOfItemStack;
        for (int j = (arrayOfItemStack = inventory.getContents()).length, i = 0; i < j; ++i) {
            final ItemStack item = arrayOfItemStack[i];
            if (item != null && wanted.contains(item.getType())) {
                found += item.getAmount();
            }
        }
        return found;
    }
    
    public static String cora(final Player p) {
        final Damageable vida = (Damageable)p;
        return NumberFormat.getCurrencyInstance().format(vida.getHealth() / 2.0).replace("$", "").replace("R", "").replace(",", ".");
    }
    
    public static void show(final Player p) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player t = onlinePlayers[i];
            p.showPlayer(t);
        }
    }
    
    public static void update1v1Vanish(final Player player) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (X1WarpListener.playerfigh.containsKey(player)) {
                final Player figh = Bukkit.getPlayer((String)X1WarpListener.playerfigh.get(player));
                if (!player.canSee(figh)) {
                    player.showPlayer(figh);
                }
                if (p.getUniqueId() != figh.getUniqueId() && p.getUniqueId() != player.getUniqueId()) {
                    player.hidePlayer(p);
                }
            }
        }
    }
    
    public static void update1v1Vanish() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            update1v1Vanish(p);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(final PlayerQuitEvent e) {
        e.setQuitMessage((String)null);
        if (X1WarpListener.playerfigh.containsKey(e.getPlayer())) {
            final Player w = Bukkit.getPlayerExact((String)X1WarpListener.playerfigh.get(e.getPlayer()));
            final Gamer g1 = Handler.getManager().getGamerManager().getGamer(w.getUniqueId());
            final Gamer g2 = Handler.getManager().getGamerManager().getGamer(e.getPlayer().getUniqueId());
            X1WarpListener.fighting.remove(e.getPlayer());
            X1WarpListener.fighting.remove(w);
            X1WarpListener.playerfigh.remove(e.getPlayer());
            X1WarpListener.playerfigh.remove(w);
            defaultItens(w);
            w.teleport(Handler.getManager().getWarps().getLocation("1v1"));
            Vanish.updateVanished(w);
            update1v1Vanish();
            if (X1WarpListener.batalhando.containsKey(w)) {
                X1WarpListener.batalhando.remove(w);
            }
            if (X1WarpListener.batalhando.containsKey(e.getPlayer())) {
                X1WarpListener.batalhando.remove(e.getPlayer());
            }
            w.sendMessage("§c" + e.getPlayer().getName() + " deslogou.");
            g1.setKills(g1.getKills() + 1);
            g1.setKillStreak(g1.getKillStreak() + 1);
            g1.updateData();
            g2.setDeaths(g2.getDeaths() + 1);
            g2.setKillStreak(0);
            g2.updateData();
            w.setHealth(20);
        }
    }
    
    public static ItemStack newItem(final Material material, final int qnt, final byte color) {
        final ItemStack i = new ItemStack(material, qnt, (short)color);
        return i;
    }
    
    public static ItemStack newItem(final Material material, final String name, final int qnt, final byte color) {
        final ItemStack i = new ItemStack(material, qnt, (short)color);
        final ItemMeta ik = i.getItemMeta();
        ik.setDisplayName(name);
        i.setItemMeta(ik);
        return i;
    }
    
    public static ItemStack newItemEnchant(final Material material, final Enchantment ench, final int qnt, final byte color) {
        final ItemStack i = new ItemStack(material, qnt, (short)color);
        final ItemMeta ik = i.getItemMeta();
        ik.addEnchant(ench, 1, true);
        i.setItemMeta(ik);
        return i;
    }
    
    public static void prepareInventory(final Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents((ItemStack[])null);
        p.getInventory().setBoots(newItem(Material.IRON_BOOTS, 1, (byte)0));
        p.getInventory().setLeggings(newItem(Material.IRON_LEGGINGS, 1, (byte)0));
        p.getInventory().setChestplate(newItem(Material.IRON_CHESTPLATE, 1, (byte)0));
        p.getInventory().setHelmet(newItem(Material.IRON_HELMET, 1, (byte)0));
        p.getInventory().setItem(0, newItemEnchant(Material.DIAMOND_SWORD, Enchantment.DAMAGE_ALL, 1, (byte)0));
        for (int i = 8; i > 0; --i) {
            p.getInventory().setItem(i, newItem(Material.MUSHROOM_SOUP, 1, (byte)0));
        }
        p.updateInventory();
    }
    
    public static final void loadWarp1v1Methods(final Player bp) {
        Handler.getManager().getProtectionManager().setProtected(bp, true);
        final Gamer gamer = Handler.getManager().getGamerManager().getGamer(bp.getUniqueId());
        defaultItens(bp);
        gamer.setWarp("1v1");
    }
    
    public static void defaultItens(final Player p) {
        final Gamer gamer = Handler.getManager().getGamerManager().getGamer(p.getUniqueId());
        gamer.setWarp("1v1");
        Handler.getManager().getProtectionManager().setProtected(p, true);
        p.getInventory().clear();
        p.getInventory().setArmorContents((ItemStack[])null);
        p.getInventory().setItem(3, newItem(Material.BLAZE_ROD, "§6§l1v1 Normal", 1, (byte)0));
        p.getInventory().setItem(4, customItem());
        p.getInventory().setItem(5, backItem());
        p.updateInventory();
    }
    
    public static void teleportToFight(final Player p1, final Player p2) {
        Handler.getManager().getProtectionManager().setProtected(p1, false);
        Handler.getManager().getProtectionManager().setProtected(p2, false);
        p1.teleport(Handler.getManager().getWarps().getLocation("1v1loc1"));
        p2.teleport(Handler.getManager().getWarps().getLocation("1v1loc2"));
        prepareInventory(p1);
        prepareInventory(p2);
        update1v1Vanish(p1);
        update1v1Vanish(p2);
    }
    
    public static void teleportToCustomFight(final Player p1, final Player p2) {
        Handler.getManager().getProtectionManager().setProtected(p1, false);
        Handler.getManager().getProtectionManager().setProtected(p2, false);
        p1.teleport(Handler.getManager().getWarps().getLocation("1v1loc1"));
        p2.teleport(Handler.getManager().getWarps().getLocation("1v1loc2"));
        Inventory1v1Custom.loadItensCustom(p1, p2);
        update1v1Vanish(p1);
        update1v1Vanish(p2);
    }
}
