package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;
import br.com.skyprogrammer.cophenix.zenixpvp.api.item.ItemBuilder;
import br.com.skyprogrammer.cophenix.zenixpvp.api.title.TitleAPI;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.Kit;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.None;
import br.com.skyprogrammer.cophenix.zenixpvp.kit.normal.PvP;

public class WarpHandler {
	public static void removeKit(final Player playerToRemove) {
		for (final Kit allKitOfList : WeavenPvP.getManager().getKitManager().getAllKits()) {
			allKitOfList.removeAbilityIfHas(playerToRemove);
		}
		final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(playerToRemove.getUniqueId());
		localGamer.setKit(new None());
	}

	public static void defaultItens(final Player player, final boolean compass) {
		final ItemStack soupItemStack = new ItemBuilder().type(Material.MUSHROOM_SOUP).build();
		final ItemStack bowlItemStack = new ItemBuilder().type(Material.BOWL).amount(64).build();
		final ItemStack redItemStack = new ItemBuilder().type(Material.RED_MUSHROOM).amount(64).build();
		final ItemStack brownItemStack = new ItemBuilder().type(Material.BROWN_MUSHROOM).amount(64).build();
		final ItemStack compassItemStack = new ItemBuilder().name("�e�lBussola").type(Material.COMPASS).amount(1)
				.build();
		for (int i = 1; i < 36; ++i) {
			player.getInventory().addItem(new ItemStack[] { soupItemStack });
		}
		player.getInventory().setItem(13, bowlItemStack);
		player.getInventory().setItem(14, redItemStack);
		player.getInventory().setItem(15, brownItemStack);
		if (compass) {
			player.getInventory().setItem(8, compassItemStack);
		}
		player.updateInventory();
	}

	public static class Spawn extends ListenerHandler {
		public static final String WARP_NAME = "Spawn";
		private static HashMap<UUID, LinkedList<Kit>> mapOfLinkedList;

		static {
			Spawn.mapOfLinkedList = new HashMap<UUID, LinkedList<Kit>>();
		}

		public Spawn(final WeavenPvP instanceOfHandler) {
			super(instanceOfHandler);
		}

		@EventHandler
		public void onMoveSpawn(final PlayerMoveEvent event) {
			final Player player = event.getPlayer();
			final Gamer gamer = WeavenPvP.getManager().getGamerManager().getGamer(player.getUniqueId());
			if (WeavenPvP.getManager().getProtectionManager().isProtected(player)) {
				Location location = null;
				if (WeavenPvP.getManager().getWarps().hasWarp("spawn")) {
					location = WeavenPvP.getManager().getWarps().getLocation("spawn");
				} else {
					location = player.getWorld().getSpawnLocation();
				}
				if (player.getLocation().distance(location) > 15.0 && !gamer.getWarp().equalsIgnoreCase("1v1")) {
					gamer.setWarp("Arena");
					if (gamer.getKit().getName().equalsIgnoreCase("Nenhum")) {
						player.closeInventory();
						player.getInventory().clear();
						player.getInventory().setArmorContents((ItemStack[]) null);
						final Kit kit = new PvP();
						gamer.setKit(kit);
						player.getInventory().setItem(0, kit.getItemStack());
						WarpHandler.defaultItens(player, true);
					}
					WeavenPvP.getManager().getProtectionManager().setProtected(player, false);
					player.sendMessage("�3�lPROTECTION�f Voc\u00ea �b�lPERDEU�f sua prote\u00e7\u00e3o de spawn.");
				}
			}
		}

		@EventHandler(priority = EventPriority.HIGH)
		public void onDamageCamper(final EntityDamageByEntityEvent event) {
			if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
				final Player entity = (Player) event.getEntity();
				final Player damager = (Player) event.getDamager();
				final Gamer gamer = WeavenPvP.getManager().getGamerManager().getGamer(damager.getUniqueId());
				if (WeavenPvP.getManager().getProtectionManager().isProtected(damager)
						&& !WeavenPvP.getManager().getProtectionManager().isProtected(entity)
						&& !gamer.getWarp().equalsIgnoreCase("1v1")) {
					event.setCancelled(false);
					gamer.setWarp("Arena");
					if (gamer.getKit().getName().equalsIgnoreCase("Nenhum")) {
						damager.closeInventory();
						damager.getInventory().clear();
						damager.getInventory().setArmorContents((ItemStack[]) null);
						final Kit kit = new PvP();
						gamer.setKit(kit);
						damager.getInventory().setItem(0, kit.getItemStack());
						WarpHandler.defaultItens(damager, true);
					}
					WeavenPvP.getManager().getProtectionManager().setProtected(damager, false);
					damager.sendMessage("�3�lPROTECTION�f Voc\u00ea �b�lPERDEU�f sua prote\u00e7\u00e3o de spawn.");
				} else if (WeavenPvP.getManager().getProtectionManager().isProtected(entity)) {
					event.setCancelled(true);
				}
			}
		}

		@EventHandler
		public void onPlayerInteract(final PlayerInteractEvent localPlayerInteractEvent) {
			final Player localPlayer = localPlayerInteractEvent.getPlayer();
			final Action localAction = localPlayerInteractEvent.getAction();
			final Material localMaterial = localPlayer.getItemInHand().getType();
			final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(localPlayer.getUniqueId());
			if (localGamer.getWarp().equalsIgnoreCase("Spawn")) {
				if (localMaterial == Material.CHEST && localAction.toString().contains("RIGHT")) {
					localPlayerInteractEvent.setCancelled(true);
					firstInventoryOfKits(localPlayer);
					localPlayer.playSound(localPlayer.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
				} else if (localMaterial == Material.PAPER && localAction.toString().contains("RIGHT")) {
					localPlayerInteractEvent.setCancelled(true);
					inventoryOfWarps(localPlayer);
					localPlayer.playSound(localPlayer.getLocation(), Sound.FIREWORK_LAUNCH, 1.0f, 1.0f);
				}
			}
		}

		public int randomIntenger() {
			int i = new Random().nextInt(5);
			if (i >= 0 || i < 5) {
				i = 1;
			}
			return i;
		}

		@EventHandler
		public void onInventoryClick(final InventoryClickEvent localInventoryClickEvent) {
			if (localInventoryClickEvent.getWhoClicked() instanceof Player) {
				final Player localPlayer = (Player) localInventoryClickEvent.getWhoClicked();
				final ItemStack localClickedItem = localInventoryClickEvent.getCurrentItem();
				final String localInventoryName = localInventoryClickEvent.getInventory().getName();
				if (localInventoryName.equalsIgnoreCase("�7Selecione seu kit (1)") && localClickedItem != null) {
					localInventoryClickEvent.setCancelled(true);
					if (localClickedItem.getType() == Material.CHEST) {
						firstInventoryOfKits(localPlayer);
					} else if (localClickedItem.getType() == Material.INK_SACK) {
						if (localClickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("�aDescer")) {
							secondInventoryOfKits(localPlayer);
						}
					} else {
						for (final Kit localKit : WeavenPvP.getManager().getKitManager().getAllKits()) {
							if (localClickedItem.getType() == localKit.getMaterial()) {
								localPlayer.closeInventory();
								localPlayer.getInventory().clear();
								localPlayer.getInventory().setArmorContents((ItemStack[]) null);
								if (localKit.getName() == "PvP") {
									localPlayer.getInventory().setItem(0, localKit.getItemStack());
								} else {
									localPlayer.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
								}
								final Gamer localGamer = WeavenPvP.getManager().getGamerManager()
										.getGamer(localPlayer.getUniqueId());
								localGamer.setKit(localKit);
								if (localKit.hasItem()) {
									localPlayer.getInventory().setItem(1, localKit.getItemStack());
								}
								WarpHandler.defaultItens(localPlayer, true);
								localPlayer.playSound(localPlayer.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
								localPlayer.sendMessage(
										"�2Voc\u00ea selecionou o kit �f" + localKit.getName().toUpperCase());
								final TitleAPI title = new TitleAPI("�e�lKIT " + localKit.getName().toUpperCase(),
										"�eSelecionado!");
								title.sendToPlayer(localPlayer);
								localGamer.setWarp("Arena");
								break;
							}
						}
					}
				} else if (localInventoryName.equalsIgnoreCase("�7Selecione seu kit (2)") && localClickedItem != null) {
					localInventoryClickEvent.setCancelled(true);
					if (localClickedItem.getType() == Material.CHEST) {
						firstInventoryOfKits(localPlayer);
					} else if (localClickedItem.getType() == Material.INK_SACK) {
						if (localClickedItem.getItemMeta().getDisplayName().equalsIgnoreCase("�aSubir")) {
							firstInventoryOfKits(localPlayer);
						}
					} else {
						for (final Kit localKit : WeavenPvP.getManager().getKitManager().getAllKits()) {
							if (localClickedItem.getType() == localKit.getMaterial()) {
								localPlayer.closeInventory();
								localPlayer.getInventory().clear();
								localPlayer.getInventory().setArmorContents((ItemStack[]) null);
								if (localKit.getName() == "PvP") {
									localPlayer.getInventory().setItem(0, localKit.getItemStack());
								} else {
									localPlayer.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
								}
								final Gamer localGamer = WeavenPvP.getManager().getGamerManager()
										.getGamer(localPlayer.getUniqueId());
								localGamer.setKit(localKit);
								if (localKit.hasItem()) {
									localPlayer.getInventory().setItem(1, localKit.getItemStack());
								}
								if (localKit.getName() == "Archer") {
									localPlayer.getInventory().setItem(1, new ItemStack(Material.ARROW, 10));
								}
								WarpHandler.defaultItens(localPlayer, true);
								localPlayer.playSound(localPlayer.getLocation(), Sound.LEVEL_UP, 1.5f, 1.5f);
								localPlayer.sendMessage(
										"�2Voc\u00ea selecionou o kit �f" + localKit.getName().toUpperCase());
								final TitleAPI title = new TitleAPI("�e�lKIT " + localKit.getName().toUpperCase(),
										"�eSelecionado!");
								title.sendToPlayer(localPlayer);
								localGamer.setWarp("Arena");
								break;
							}
						}
					}
				} else if (localInventoryName.equalsIgnoreCase("�7Menu de Warps") && localClickedItem != null) {
					localInventoryClickEvent.setCancelled(true);
					if (localClickedItem.getType() == Material.GLASS) {
						localPlayer.performCommand("warp fps");
					} else if (localClickedItem.getType() == Material.BLAZE_ROD) {
						localPlayer.performCommand("warp 1v1");
					} else if (localClickedItem.getType() == Material.LAVA_BUCKET) {
						localPlayer.performCommand("warp challenge");
					} else if (localClickedItem.getType() == Material.COOKIE) {
						localPlayer.performCommand("warp rdm");
					}
				}
			}
		}

		public static void setItensToPlayer(final Player playerToSet) {
			WarpHandler.removeKit(playerToSet);
			if (WeavenPvP.getManager().getWarps().hasWarp("spawn")) {
				playerToSet.teleport(WeavenPvP.getManager().getWarps().getLocation("spawn"));
			} else {
				playerToSet.teleport(playerToSet.getWorld().getSpawnLocation());
			}
			CooldownHandler.removeCooldown(playerToSet);
			final Gamer localGamer = WeavenPvP.getManager().getGamerManager().getGamer(playerToSet.getUniqueId());
			localGamer.setWarp("Spawn");
			playerToSet.setFireTicks(0);
			playerToSet.setGameMode(GameMode.SURVIVAL);
			playerToSet.setHealth(20);
			playerToSet.setFoodLevel(20);
			playerToSet.getInventory().clear();
			playerToSet.getInventory().setArmorContents((ItemStack[]) null);
			for (final PotionEffect localPotionEffect : playerToSet.getActivePotionEffects()) {
				playerToSet.removePotionEffect(localPotionEffect.getType());
			}
			final ItemStack chestItemStack = new ItemBuilder().name("�eSeletor de Kits �7(Clique para utilizar)")
					.type(Material.CHEST).build();
			final ItemStack compassItemStack = new ItemBuilder().name("�eSeletor de Warps �7(Clique para utilizar)")
					.type(Material.PAPER).build();
			final ItemStack diamondItemStack = new ItemBuilder().name("�eStatus �7(Clique para utilizar)")
					.type(Material.GOLD_INGOT).build();
			playerToSet.getInventory().setItem(0, chestItemStack);
			playerToSet.getInventory().setItem(1, compassItemStack);
			playerToSet.getInventory().setItem(8, diamondItemStack);
			playerToSet.getInventory().setHeldItemSlot(0);
			playerToSet.updateInventory();
			WeavenPvP.getManager().getProtectionManager().setProtected(playerToSet, true);
			playerToSet.sendMessage("�3�lPROTECTION�f Voc\u00ea �b�lRECEBEU�f sua prote\u00e7\u00e3o de spawn.");
		}

		public static int getInWarp(final String warpName) {
			int integer = 0;
			Player[] onlinePlayers;
			for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
				final Player p = onlinePlayers[i];
				final Gamer gamer = WeavenPvP.getManager().getGamerManager().getGamer(p.getUniqueId());
				if (gamer.getWarp().equalsIgnoreCase(warpName)) {
					++integer;
				}
			}
			return integer;
		}

		public static void inventoryOfWarps(final Player playerToOpen) {
			final Inventory localInventory = Bukkit.createInventory((InventoryHolder) playerToOpen, 9,
					"�7Menu de Warps");
			final ItemStack glassItemStack = new ItemBuilder().name("�e�lFPS")
					.lore("", "�f" + getInWarp("Fps") + "�2 jogando agora", "").type(Material.GLASS).build();
			final ItemStack blazeItemStack = new ItemBuilder().name("�e�l1v1")
					.lore("", "�f" + getInWarp("1v1") + "�2 jogando agora", "").type(Material.BLAZE_ROD).build();
			final ItemStack lavaItemStack = new ItemBuilder().name("�e�lLava Challenge")
					.lore("", "�f" + getInWarp("Challenge") + "�2 jogando agora", "").type(Material.LAVA_BUCKET)
					.build();
			final ItemStack cookietemStack = new ItemBuilder().name("�e�lRei da Mesa")
					.lore("", "�f" + getInWarp("Rdm") + "�2 jogando agora", "").type(Material.COOKIE).build();
			localInventory.setItem(0, glassItemStack);
			localInventory.setItem(1, blazeItemStack);
			localInventory.setItem(2, lavaItemStack);
			localInventory.setItem(3, cookietemStack);
			playerToOpen.openInventory(localInventory);
		}

		public static void firstInventoryOfKits(final Player playerToOpen) {
			final Inventory localInventory = Bukkit.createInventory((InventoryHolder) playerToOpen, 54,
					"�7Selecione seu kit (1)");
			final LinkedList<Integer> linkedListOfInteger = new LinkedList<Integer>();
			for (int i = 11; i < 17; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 20; i < 26; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 29; i < 35; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 38; i < 44; ++i) {
				linkedListOfInteger.add(i);
			}
			final LinkedList<Kit> linkedListOfKit = WeavenPvP.getManager().getKitManager().getAllKits();
			final LinkedList<Kit> linkedLitOfKitToSave = new LinkedList<Kit>();
			int integerOfMaxSize = 0;
			for (final Kit kitOfSession : linkedListOfKit) {
				if (kitOfSession == null) {
					continue;
				}
				if (kitOfSession.needPermission()
						&& playerToOpen.hasPermission("pvpkit." + kitOfSession.getName().toLowerCase())
						&& integerOfMaxSize < linkedListOfInteger.size()) {
					localInventory.setItem((int) linkedListOfInteger.get(integerOfMaxSize),
							kitOfSession.getItemStackMenu());
					++integerOfMaxSize;
				} else if (!kitOfSession.needPermission() && integerOfMaxSize < linkedListOfInteger.size()) {
					localInventory.setItem((int) linkedListOfInteger.get(integerOfMaxSize),
							kitOfSession.getItemStackMenu());
					++integerOfMaxSize;
				} else {
					linkedLitOfKitToSave.add(kitOfSession);
				}
			}
			Spawn.mapOfLinkedList.put(playerToOpen.getUniqueId(), linkedLitOfKitToSave);
			final boolean nextPage = linkedLitOfKitToSave.size() == 0;
			final ItemStack chestItemStack = new ItemBuilder().name("�7Seus kits (in\u00edcio)").type(Material.CHEST)
					.build();
			final ItemStack greenSackItemStack = new ItemBuilder().name(nextPage ? "�cDescer" : "�aDescer")
					.type(Material.INK_SACK).data(nextPage ? 1 : 10).build();
			final ItemStack redSackItemStack = new ItemBuilder().name("�cSubir").type(Material.INK_SACK).data(1)
					.build();
			localInventory.setItem(18, chestItemStack);
			localInventory.setItem(49, redSackItemStack);
			localInventory.setItem(50, greenSackItemStack);
			playerToOpen.openInventory(localInventory);
		}

		public static void secondInventoryOfKits(final Player playerToOpen) {
			final Inventory localInventory = Bukkit.createInventory((InventoryHolder) playerToOpen, 54,
					"�7Selecione seu kit (2)");
			final LinkedList<Integer> linkedListOfInteger = new LinkedList<Integer>();
			for (int i = 11; i < 17; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 20; i < 26; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 29; i < 35; ++i) {
				linkedListOfInteger.add(i);
			}
			for (int i = 38; i < 44; ++i) {
				linkedListOfInteger.add(i);
			}
			final LinkedList<Kit> linkedListOfKit = Spawn.mapOfLinkedList.get(playerToOpen.getUniqueId());
			final LinkedList<Kit> linkedLitOfKitToSave = new LinkedList<Kit>();
			int integerOfMaxSize = 0;
			for (final Kit kitOfSession : linkedListOfKit) {
				if (kitOfSession == null) {
					continue;
				}
				if (kitOfSession.needPermission()
						&& playerToOpen.hasPermission("pvpkit." + kitOfSession.getName().toLowerCase())
						&& integerOfMaxSize < linkedListOfInteger.size()) {
					localInventory.setItem((int) linkedListOfInteger.get(integerOfMaxSize),
							kitOfSession.getItemStackMenu());
					++integerOfMaxSize;
				} else if (!kitOfSession.needPermission() && integerOfMaxSize < linkedListOfInteger.size()) {
					localInventory.setItem((int) linkedListOfInteger.get(integerOfMaxSize),
							kitOfSession.getItemStackMenu());
					++integerOfMaxSize;
				} else {
					linkedLitOfKitToSave.add(kitOfSession);
				}
			}
			Spawn.mapOfLinkedList.put(playerToOpen.getUniqueId(), linkedLitOfKitToSave);
			final ItemStack chestItemStack = new ItemBuilder().name("�7Seus kits (in\u00edcio)").type(Material.CHEST)
					.build();
			final ItemStack redSackGrayItemStack = new ItemBuilder().name("�cDescer").type(Material.INK_SACK).data(1)
					.build();
			final ItemStack greenSackGrayItemStack = new ItemBuilder().name("�aSubir").type(Material.INK_SACK).data(10)
					.build();
			localInventory.setItem(18, chestItemStack);
			localInventory.setItem(49, greenSackGrayItemStack);
			localInventory.setItem(50, redSackGrayItemStack);
			playerToOpen.openInventory(localInventory);
		}
	}
}
