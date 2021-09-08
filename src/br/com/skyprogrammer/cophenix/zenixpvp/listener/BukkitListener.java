package br.com.skyprogrammer.cophenix.zenixpvp.listener;

import org.bukkit.entity.Damageable;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.entity.Arrow;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.entity.Item;
import org.bukkit.event.entity.ItemSpawnEvent;
import br.com.skyprogrammer.cophenix.zenixpvp.api.item.ItemBuilder;
import br.com.skyprogrammer.cophenix.zenixpvp.api.admin.AdminMode;
import org.bukkit.scheduler.BukkitRunnable;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CombatHandler;
import org.bukkit.plugin.Plugin;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone.X1WarpListener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.block.BlockBreakEvent;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.Sound;
import org.bukkit.util.Vector;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import java.util.Iterator;
import org.bukkit.inventory.ItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.scheduler.BukkitTask;
import java.util.UUID;
import java.util.HashMap;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.ListenerHandler;

public class BukkitListener extends ListenerHandler
{
    public static HashMap<UUID, BukkitTask> localMapTask;
    
    static {
        BukkitListener.localMapTask = new HashMap<UUID, BukkitTask>();
    }
    
    public BukkitListener(final Handler instanceOfHandler) {
        super(instanceOfHandler);
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEvent(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        final Player p = (Player)event.getDamager();
        final ItemStack sword = p.getItemInHand();
        double damage = event.getDamage();
        final double swordDamage = this.getDamage(sword.getType());
        boolean isMore = false;
        if (damage > 1.0) {
            isMore = true;
        }
        if (p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            for (final PotionEffect effect : p.getActivePotionEffects()) {
                if (effect.getType().equals((Object)PotionEffectType.INCREASE_DAMAGE)) {
                    double minus;
                    if (this.isCrital(p)) {
                        minus = (swordDamage + swordDamage / 2.0) * 1.3 * (effect.getAmplifier() + 1);
                    }
                    else {
                        minus = swordDamage * 1.3 * (effect.getAmplifier() + 1);
                    }
                    damage -= minus;
                    damage += 2 * (effect.getAmplifier() + 1);
                    break;
                }
            }
        }
        if (!sword.getEnchantments().isEmpty()) {
            if (sword.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS) && this.isArthropod(event.getEntityType())) {
                damage -= 1.5 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
                damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
            }
            if (sword.containsEnchantment(Enchantment.DAMAGE_UNDEAD) && this.isUndead(event.getEntityType())) {
                damage -= 1.5 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
                damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
            }
            if (sword.containsEnchantment(Enchantment.DAMAGE_ALL)) {
                damage -= 1.25 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                damage += 1 * sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            }
        }
        if (this.isCrital(p)) {
            damage -= swordDamage / 2.0;
            ++damage;
        }
        if (isMore) {
            damage -= 2.0;
        }
        event.setDamage(damage);
    }
    
    private boolean isCrital(final Player p) {
        return p.getFallDistance() > 0.0f && !p.isOnGround() && !p.hasPotionEffect(PotionEffectType.BLINDNESS);
    }
    
    private boolean isArthropod(final EntityType type) {
        switch (type) {
            case CAVE_SPIDER: {
                return true;
            }
            case SPIDER: {
                return true;
            }
            case SILVERFISH: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private boolean isUndead(final EntityType type) {
        switch (type) {
            case SKELETON: {
                return true;
            }
            case ZOMBIE: {
                return true;
            }
            case WITHER_SKULL: {
                return true;
            }
            case PIG_ZOMBIE: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    private double getDamage(final Material type) {
        double damage = 1.0;
        if (type.toString().contains("DIAMOND_")) {
            damage = 8.0;
        }
        else if (type.toString().contains("IRON_")) {
            damage = 7.0;
        }
        else if (type.toString().contains("STONE_")) {
            damage = 6.0;
        }
        else if (type.toString().contains("WOOD_")) {
            damage = 5.0;
        }
        else if (type.toString().contains("GOLD_")) {
            damage = 5.0;
        }
        if (!type.toString().contains("_SWORD")) {
            --damage;
            if (!type.toString().contains("_AXE")) {
                --damage;
                if (!type.toString().contains("_PICKAXE")) {
                    --damage;
                    if (!type.toString().contains("_SPADE")) {
                        damage = 1.0;
                    }
                }
            }
        }
        return damage;
    }
    
    @EventHandler
    public void onInteract(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock() != null && (event.getClickedBlock().getType() == Material.WALL_SIGN || event.getClickedBlock().getType() == Material.SIGN_POST)) {
            final Sign s = (Sign)event.getClickedBlock().getState();
            final String[] lines = s.getLines();
            if (lines.length > 0 && lines[0].equals("§3Celtz") && lines.length > 1 && lines[1].equals("§bSopas") && lines.length > 2 && lines[2].equals("§6§m>-----<") && lines.length > 3 && lines[3].equals(" ")) {
                event.setCancelled(true);
                final Inventory inv = Bukkit.createInventory((InventoryHolder)player, 54, "§bSopas");
                final ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
                for (int i = 0; i < 54; ++i) {
                    inv.setItem(i, sopa);
                }
                player.openInventory(inv);
            }
            else if (lines.length > 0 && lines[0].equals("§3Celtz") && lines.length > 1 && lines[1].equals("§bRecraft") && lines.length > 2 && lines[2].equals("§6§m>-----<") && lines.length > 3 && lines[3].equals(" ")) {
                event.setCancelled(true);
                final ItemStack cogu1 = new ItemStack(Material.BROWN_MUSHROOM, 64);
                final ItemStack cogu2 = new ItemStack(Material.RED_MUSHROOM, 64);
                final ItemStack pote = new ItemStack(Material.BOWL, 64);
                player.getInventory().addItem(new ItemStack[] { cogu1 });
                player.getInventory().addItem(new ItemStack[] { cogu2 });
                player.getInventory().addItem(new ItemStack[] { pote });
                player.updateInventory();
                player.sendMessage("§6§lRECRAFT§f Voc\u00ea recebeu seu §6§lRECRAFT§f!");
            }
        }
    }
    
    @EventHandler
    public void jumpAlto(final PlayerMoveEvent paramPlayerMoveEvent) {
        final Player localPlayer = paramPlayerMoveEvent.getPlayer();
        final Block localBlock1 = paramPlayerMoveEvent.getTo().getBlock();
        final Location localLocation = localBlock1.getLocation();
        localLocation.setY(localLocation.getY() - 1.0);
        final Block localBlock2 = localLocation.getBlock();
        if (localBlock2.getType() == Material.SPONGE) {
            localPlayer.setFallDistance(-50.0f);
            localPlayer.setVelocity(new Vector(0, 5, 0));
            localPlayer.setFallDistance(-50.0f);
            localPlayer.playSound(localPlayer.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        }
    }
    
    @EventHandler(priority = EventPriority.MONITOR)
    public void onRepair(final PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (player.getItemInHand().getType() == Material.DIAMOND_SWORD || player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.WOOD_SWORD || player.getItemInHand().getType() == Material.STONE_SWORD || player.getItemInHand().getType() == Material.IRON_SWORD || player.getItemInHand().getType() == Material.GOLD_SWORD || player.getItemInHand().getType() == Material.FISHING_ROD || player.getItemInHand().getType() == Material.DIAMOND_AXE || player.getItemInHand().getType() == Material.GOLD_AXE || player.getItemInHand().getType() == Material.STONE_AXE || player.getItemInHand().getType() == Material.WOOD_AXE || player.getItemInHand().getType() == Material.IRON_AXE) {
            player.getItemInHand().setDurability((short)0);
            player.updateInventory();
        }
    }
    
    @EventHandler
    public void onPlace(final BlockPlaceEvent localBlockPlaceEvent) {
        final Player localPlayer = localBlockPlaceEvent.getPlayer();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (!localGamer.isBuildEnabled()) {
            localBlockPlaceEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onBreak(final BlockBreakEvent localBlockBreakEvent) {
        final Player localPlayer = localBlockBreakEvent.getPlayer();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (!localGamer.isBuildEnabled()) {
            localBlockBreakEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent localPlayerMoveEvent) {
        final Player localPlayer = localPlayerMoveEvent.getPlayer();
        if (BukkitListener.localMapTask.containsKey(localPlayer.getUniqueId())) {
            BukkitListener.localMapTask.get(localPlayer.getUniqueId()).cancel();
            BukkitListener.localMapTask.remove(localPlayer.getUniqueId());
            localPlayer.sendMessage("§cSeu §fTELEPORTE§c foi §fCANCELADO§c pois voce se §fMEXEU§c!");
        }
    }
    
    @EventHandler
    public void onCommandCombat(final PlayerCommandPreprocessEvent localPlayerCommandPreprocessEvent) {
        final Player localPlayer = localPlayerCommandPreprocessEvent.getPlayer();
        final String localMessage = localPlayerCommandPreprocessEvent.getMessage().toLowerCase();
        if (localMessage.equalsIgnoreCase("/admin") && localPlayer.hasPermission("core.cmd.admin")) {
            Bukkit.getScheduler().runTaskLater((Plugin)this.getHandler(), () -> X1WarpListener.update1v1Vanish(), 10L);
        }
        if (CombatHandler.onCombat(localPlayer) && (localMessage.startsWith("/spawn") || localMessage.startsWith("/warp"))) {
            localPlayerCommandPreprocessEvent.setCancelled(true);
            if (localPlayer.getFallDistance() > 0.0f || !localPlayer.isOnGround()) {
                localPlayer.sendMessage("§cVoc\u00ea precisa estar §fNO CHAO§c para se §fTELEPORTAR§c!");
                return;
            }
            if (BukkitListener.localMapTask.containsKey(localPlayer.getUniqueId())) {
                BukkitListener.localMapTask.get(localPlayer.getUniqueId()).cancel();
                BukkitListener.localMapTask.remove(localPlayer.getUniqueId());
                localPlayer.sendMessage("§cVoc\u00ea §fCANCELOU§c o §fTELEPORTE§c!");
                return;
            }
            BukkitListener.localMapTask.put(localPlayer.getUniqueId(), new BukkitRunnable() {
                public void run() {
                    localPlayer.performCommand(localMessage);
                    BukkitListener.localMapTask.remove(localPlayer.getUniqueId());
                }
            }.runTaskLater((Plugin)this.getHandler(), 100L));
        }
    }
    
    @EventHandler
    public void onCompass(final PlayerInteractEvent localPlayerInteractEvent) {
        final Player localPlayer = localPlayerInteractEvent.getPlayer();
        final Material localMaterial = localPlayer.getItemInHand().getType();
        if (localMaterial == null || localMaterial != Material.COMPASS) {
            return;
        }
        Player localPlayerToTarget = null;
        double distance = 500.0;
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player players = onlinePlayers[i];
            if (!AdminMode.getInstance().isAdmin(players)) {
                final double distancePlayerToVictm = localPlayer.getLocation().distance(players.getLocation());
                if (distancePlayerToVictm < distance && distancePlayerToVictm > 10.0) {
                    distance = distancePlayerToVictm;
                    localPlayerToTarget = players;
                }
            }
        }
        if (localPlayerToTarget == null) {
            localPlayer.sendMessage("§eNenhum jogador localizado.");
            localPlayer.setCompassTarget(localPlayer.getWorld().getSpawnLocation());
        }
        else {
            localPlayer.setCompassTarget(localPlayerToTarget.getLocation());
            localPlayer.sendMessage("§eBussola apontando para §f" + localPlayerToTarget.getName());
        }
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onSoup(final PlayerInteractEvent localPlayerInteractEvent) {
        final Player localPlayer = localPlayerInteractEvent.getPlayer();
        final Material localMaterial = localPlayer.getItemInHand().getType();
        final Gamer localGamer = Handler.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
        if (localGamer.getWarp().equalsIgnoreCase("Spawn")) {
            return;
        }
        if (localMaterial == null || localMaterial != Material.MUSHROOM_SOUP) {
            return;
        }
        if ((localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK || localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR) && ((Damageable)localPlayer).getHealth() < ((Damageable)localPlayer).getMaxHealth()) {
            final int restores = 7;
            localPlayerInteractEvent.setCancelled(true);
            if (((Damageable)localPlayer).getHealth() + restores <= ((Damageable)localPlayer).getMaxHealth()) {
                localPlayer.setHealth(((Damageable)localPlayer).getHealth() + restores);
            }
            else {
                localPlayer.setHealth(((Damageable)localPlayer).getMaxHealth());
            }
            localPlayer.setItemInHand(new ItemBuilder().type(Material.BOWL).build());
        }
    }
    
    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent localItemSpawnEvent) {
        final Item localItem = localItemSpawnEvent.getEntity();
        Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Handler.getInstance(), () -> localItem.remove(), 100L);
    }
    
    @EventHandler
    public void onSignChange(final SignChangeEvent event) {
        if (event.getLine(0).contains("&")) {
            event.setLine(0, event.getLine(0).replace("&", "§"));
        }
        if (event.getLine(1).contains("&")) {
            event.setLine(1, event.getLine(1).replace("&", "§"));
        }
        if (event.getLine(2).contains("&")) {
            event.setLine(2, event.getLine(2).replace("&", "§"));
        }
        if (event.getLine(3).contains("&")) {
            event.setLine(3, event.getLine(3).replace("&", "§"));
        }
        if (event.getLine(0).equalsIgnoreCase("sopa") || event.getLine(0).equalsIgnoreCase("sopas")) {
            event.setLine(0, "§3Celtz");
            event.setLine(1, "§bSopas");
            event.setLine(2, "§6§m>-----<");
            event.setLine(3, " ");
        }
        if (event.getLine(0).equalsIgnoreCase("recraft") || event.getLine(0).equalsIgnoreCase("recrafts")) {
            event.setLine(0, "§3Celtz");
            event.setLine(1, "§bRecraft");
            event.setLine(2, "§6§m>-----<");
            event.setLine(3, " ");
        }
        if (event.getLine(0).contains("&")) {
            event.setLine(0, event.getLine(0).replace("&", "§"));
        }
        if (event.getLine(1).contains("&")) {
            event.setLine(1, event.getLine(1).replace("&", "§"));
        }
        if (event.getLine(2).contains("&")) {
            event.setLine(2, event.getLine(2).replace("&", "§"));
        }
        if (event.getLine(3).contains("&")) {
            event.setLine(3, event.getLine(3).replace("&", "§"));
        }
    }
    
    @EventHandler
    public void onInteractRepair(final PlayerInteractEvent localPlayerInteractEvent) {
        final Player localPlayer = localPlayerInteractEvent.getPlayer();
        final ItemStack localItemStack = localPlayer.getItemInHand();
        if ((localPlayerInteractEvent.getAction() == Action.LEFT_CLICK_AIR || localPlayerInteractEvent.getAction() == Action.LEFT_CLICK_BLOCK || localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR || localPlayerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK) && (localItemStack.getType() == Material.FISHING_ROD || localItemStack.getType().toString().contains("_SWORD") || localItemStack.getType().toString().contains("_AXE"))) {
            localItemStack.setDurability((short)0);
        }
    }
    
    @EventHandler
    public void onDropItem(final PlayerDropItemEvent localPlayerDropItemEvent) {
        final Material localMaterial = localPlayerDropItemEvent.getItemDrop().getItemStack().getType();
        if (!localMaterial.toString().contains("MUSHROOM") && localMaterial != Material.BOWL && localMaterial != Material.ENDER_PEARL && localMaterial != Material.EXP_BOTTLE && localMaterial != Material.GOLDEN_APPLE && localMaterial != Material.GLASS_BOTTLE) {
            localPlayerDropItemEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onProjectile(final ProjectileHitEvent localProjectileHitEvent) {
        if (localProjectileHitEvent.getEntity() instanceof Arrow) {
            Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)Handler.getInstance(), (Runnable)new Runnable() {
                @Override
                public void run() {
                    localProjectileHitEvent.getEntity().remove();
                }
            }, 20L);
        }
    }
    
    @EventHandler
    public void onFood(final FoodLevelChangeEvent localFoodLevelChangeEvent) {
        localFoodLevelChangeEvent.setFoodLevel(20);
        localFoodLevelChangeEvent.setCancelled(true);
    }
    
    @EventHandler
    public void onAchievement(final PlayerAchievementAwardedEvent localPlayerAchievementAwardedEvent) {
        localPlayerAchievementAwardedEvent.setCancelled(true);
    }
    
    @EventHandler
    public void onCreatureSpawn(final CreatureSpawnEvent localCreatureSpawnEvent) {
        if (localCreatureSpawnEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DISPENSE_EGG && localCreatureSpawnEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM && localCreatureSpawnEvent.getSpawnReason() != CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
            localCreatureSpawnEvent.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onWeather(final WeatherChangeEvent localWeatherChangeEvent) {
        localWeatherChangeEvent.setCancelled(true);
    }
    
    @EventHandler
    public void onPortal(final PlayerPortalEvent localPlayerPortalEvent) {
        localPlayerPortalEvent.setCancelled(true);
    }
    
    @EventHandler
    public void onPickUp(final PlayerPickupItemEvent localPlayerPickupItemEvent) {
        final Player player = localPlayerPickupItemEvent.getPlayer();
        final Gamer gamer = Handler.getManager().getGamerManager().getGamer(player.getUniqueId());
        final Material localMaterial = localPlayerPickupItemEvent.getItem().getItemStack().getType();
        if (gamer.getWarp().equalsIgnoreCase("1v1")) {
            localPlayerPickupItemEvent.setCancelled(true);
        }
        else if (!localMaterial.toString().contains("MUSHROOM") && localMaterial != Material.BOWL && localMaterial != Material.ENDER_PEARL && localMaterial != Material.EXP_BOTTLE && localMaterial != Material.GOLDEN_APPLE && localMaterial != Material.GLASS_BOTTLE) {
            localPlayerPickupItemEvent.setCancelled(true);
        }
    }
}
