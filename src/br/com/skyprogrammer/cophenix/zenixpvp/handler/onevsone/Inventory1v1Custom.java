package br.com.skyprogrammer.cophenix.zenixpvp.handler.onevsone;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;

public final class Inventory1v1Custom implements Listener {
	public static final HashMap<Player, String> playername;
	public static final HashMap<Player, String> armaduras;
	public static final HashMap<Player, Material> espada;
	public static final HashMap<Player, String> recrafttype;
	public static final HashMap<Player, Boolean> recraft;
	public static final HashMap<Player, Boolean> sharpness;
	public static final HashMap<Player, Boolean> fullsoup;

	static {
		playername = new HashMap<Player, String>();
		armaduras = new HashMap<Player, String>();
		espada = new HashMap<Player, Material>();
		recrafttype = new HashMap<Player, String>();
		recraft = new HashMap<Player, Boolean>();
		sharpness = new HashMap<Player, Boolean>();
		fullsoup = new HashMap<Player, Boolean>();
	}

	public static final ItemStack newItem(final Material mat, final String name, final String[] desc) {
		final ItemStack item = new ItemStack(mat);
		final ItemMeta itemk = item.getItemMeta();
		itemk.setDisplayName(name);
		itemk.setLore(Arrays.asList(desc));
		item.setItemMeta(itemk);
		return item;
	}

	public static final ItemStack newItem(final Material mat, final String name, final String[] desc, final int qt,
			final byte bt) {
		final ItemStack item = new ItemStack(mat, qt, (short) bt);
		final ItemMeta itemk = item.getItemMeta();
		itemk.setDisplayName(name);
		itemk.setLore(Arrays.asList(desc));
		item.setItemMeta(itemk);
		return item;
	}

	public static final ItemStack newItem(final Material mat, final String name, final int qt, final byte bt) {
		final ItemStack item = new ItemStack(mat, qt, (short) bt);
		final ItemMeta itemk = item.getItemMeta();
		itemk.setDisplayName(name);
		item.setItemMeta(itemk);
		return item;
	}

	public static final void loadItensCustom(final Player bp1, final Player bp2) {
		bp1.getInventory().clear();
		bp2.getInventory().clear();
		bp1.getInventory().setArmorContents((ItemStack[]) null);
		bp2.getInventory().setArmorContents((ItemStack[]) null);
		if (Inventory1v1Custom.armaduras.containsKey(bp1) && Inventory1v1Custom.armaduras.get(bp1) != "SEM") {
			if (Inventory1v1Custom.armaduras.get(bp1) == "LEATHER") {
				bp1.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
				bp2.getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			} else if (Inventory1v1Custom.armaduras.get(bp1) == "IRON") {
				bp1.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
				bp2.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			} else if (Inventory1v1Custom.armaduras.get(bp1) == "DIAMOND") {
				bp1.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				bp1.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				bp1.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				bp1.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
				bp2.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				bp2.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				bp2.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				bp2.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
			}
		}
		if (Inventory1v1Custom.fullsoup.containsKey(bp1)) {
			if (Inventory1v1Custom.fullsoup.get(bp1)) {
				for (int i = 1; i < 36; ++i) {
					bp1.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
					bp2.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
				}
			} else {
				for (int i = 1; i < 9; ++i) {
					bp1.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
					bp2.getInventory().setItem(i, new ItemStack(Material.MUSHROOM_SOUP));
				}
			}
		}
		if (Inventory1v1Custom.espada.containsKey(bp1)) {
			if (Inventory1v1Custom.espada.get(bp1) == Material.WOOD_SWORD) {
				if (Inventory1v1Custom.sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaMadeira(true));
					bp2.getInventory().setItem(0, espadaMadeira(true));
				} else {
					bp1.getInventory().setItem(0, espadaMadeira(false));
					bp2.getInventory().setItem(0, espadaMadeira(false));
				}
			}
			if (Inventory1v1Custom.espada.get(bp1) == Material.STONE_SWORD) {
				if (Inventory1v1Custom.sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaPedra(true));
					bp2.getInventory().setItem(0, espadaPedra(true));
				} else {
					bp1.getInventory().setItem(0, espadaPedra(false));
					bp2.getInventory().setItem(0, espadaPedra(false));
				}
			}
			if (Inventory1v1Custom.espada.get(bp1) == Material.IRON_SWORD) {
				if (Inventory1v1Custom.sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaFerro(true));
					bp2.getInventory().setItem(0, espadaFerro(true));
				} else {
					bp1.getInventory().setItem(0, espadaFerro(false));
					bp2.getInventory().setItem(0, espadaFerro(false));
				}
			}
			if (Inventory1v1Custom.espada.get(bp1) == Material.DIAMOND_SWORD) {
				if (Inventory1v1Custom.sharpness.get(bp1)) {
					bp1.getInventory().setItem(0, espadaDiamante(true));
					bp2.getInventory().setItem(0, espadaDiamante(true));
				} else {
					bp1.getInventory().setItem(0, espadaDiamante(false));
					bp2.getInventory().setItem(0, espadaDiamante(false));
				}
			}
		}
		if (Inventory1v1Custom.recraft.containsKey(bp1) && Inventory1v1Custom.recraft.get(bp1)) {
			if (Inventory1v1Custom.recrafttype.get(bp1) == "COGUMELO") {
				bp1.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (short) 0));
				bp1.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64, (short) 0));
				bp1.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64, (short) 0));
				bp2.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (short) 0));
				bp2.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64, (short) 0));
				bp2.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64, (short) 0));
			} else if (Inventory1v1Custom.recrafttype.get(bp1) == "COCOABEAN") {
				bp1.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (short) 0));
				bp1.getInventory().setItem(14, new ItemStack(Material.getMaterial(351), 64, (short) 3));
				bp1.getInventory().setItem(15, new ItemStack(Material.getMaterial(351), 64, (short) 3));
				bp2.getInventory().setItem(13, new ItemStack(Material.BOWL, 64, (short) 0));
				bp2.getInventory().setItem(14, new ItemStack(Material.getMaterial(351), 64, (short) 3));
				bp2.getInventory().setItem(15, new ItemStack(Material.getMaterial(351), 64, (short) 3));
			}
		}
		bp1.updateInventory();
		bp2.updateInventory();
	}

	public static final void removeDefaultCustoms(final Player bp) {
		if (Inventory1v1Custom.playername.containsKey(bp)) {
			Inventory1v1Custom.playername.remove(bp);
		}
		if (Inventory1v1Custom.armaduras.containsKey(bp)) {
			Inventory1v1Custom.armaduras.remove(bp);
		}
		if (Inventory1v1Custom.espada.containsKey(bp)) {
			Inventory1v1Custom.espada.remove(bp);
		}
		if (Inventory1v1Custom.recrafttype.containsKey(bp)) {
			Inventory1v1Custom.recrafttype.remove(bp);
		}
		if (Inventory1v1Custom.sharpness.containsKey(bp)) {
			Inventory1v1Custom.sharpness.remove(bp);
		}
		if (Inventory1v1Custom.fullsoup.containsKey(bp)) {
			Inventory1v1Custom.fullsoup.remove(bp);
		}
	}

	public static final ItemStack espadaDiamante(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.DIAMOND_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaFerro(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.IRON_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaPedra(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.STONE_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final ItemStack espadaMadeira(final boolean enchanted) {
		final ItemStack i = new ItemStack(Material.WOOD_SWORD);
		final ItemMeta ik = i.getItemMeta();
		if (enchanted) {
			ik.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		}
		i.setItemMeta(ik);
		return i;
	}

	public static final void setDefaultCustoms(final Player bp, final String playerName) {
		removeDefaultCustoms(bp);
		Inventory1v1Custom.playername.put(bp, playerName);
		Inventory1v1Custom.armaduras.put(bp, "LEATHER");
		Inventory1v1Custom.espada.put(bp, Material.WOOD_SWORD);
		Inventory1v1Custom.recrafttype.put(bp, "COGUMELO");
		Inventory1v1Custom.recraft.put(bp, false);
		Inventory1v1Custom.sharpness.put(bp, true);
		Inventory1v1Custom.fullsoup.put(bp, false);
	}

	public static final void openCustomInventory(final Player bp, final Player customer) {
		final Inventory custom = Bukkit.createInventory((InventoryHolder) bp, 54, "�c1v1 contra " + customer.getName());
		for (int i = 0; i < 54; ++i) {
			custom.setItem(i, newItem(Material.STAINED_GLASS_PANE, "�b�l-", 1, (byte) 8));
		}
		custom.setItem(43, newItem(Material.WOOL, "�a�lDesafiar Jogador", 1, (byte) 5));
		custom.setItem(44, newItem(Material.WOOL, "�a�lDesafiar Jogador", 1, (byte) 5));
		custom.setItem(52, newItem(Material.WOOL, "�a�lDesafiar Jogador", 1, (byte) 5));
		custom.setItem(53, newItem(Material.WOOL, "�a�lDesafiar Jogador", 1, (byte) 5));
		if (Inventory1v1Custom.espada.containsKey(bp)) {
			if (Inventory1v1Custom.espada.get(bp) == Material.WOOD_SWORD) {
				custom.setItem(20, newItem(Material.WOOD_SWORD, "�6Espada de Madeira",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua espada!", "" }));
			} else if (Inventory1v1Custom.espada.get(bp) == Material.STONE_SWORD) {
				custom.setItem(20, newItem(Material.STONE_SWORD, "�6Espada de Pedra",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua espada!", "" }));
			} else if (Inventory1v1Custom.espada.get(bp) == Material.IRON_SWORD) {
				custom.setItem(20, newItem(Material.IRON_SWORD, "�6Espada de Ferro",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua espada!", "" }));
			} else if (Inventory1v1Custom.espada.get(bp) == Material.DIAMOND_SWORD) {
				custom.setItem(20, newItem(Material.DIAMOND_SWORD, "�6Espada de Diamante",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua espada!", "" }));
			}
		}
		if (Inventory1v1Custom.armaduras.containsKey(bp)) {
			if (Inventory1v1Custom.armaduras.get(bp) == "LEATHER") {
				custom.setItem(21, newItem(Material.LEATHER_CHESTPLATE, "�eArmadura de Couro",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua armadura!", "" }));
			} else if (Inventory1v1Custom.armaduras.get(bp) == "IRON") {
				custom.setItem(21, newItem(Material.IRON_CHESTPLATE, "�eArmadura de Ferro",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua armadura!", "" }));
			} else if (Inventory1v1Custom.armaduras.get(bp) == "DIAMOND") {
				custom.setItem(21, newItem(Material.DIAMOND_CHESTPLATE, "�eArmadura de Diamante",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua armadura!", "" }));
			} else if (Inventory1v1Custom.armaduras.get(bp) == "SEM") {
				custom.setItem(21, newItem(Material.GOLD_HELMET, "�eSem Armadura",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de sua armadura!", "" }));
			}
		}
		if (Inventory1v1Custom.recrafttype.containsKey(bp)) {
			if (Inventory1v1Custom.recrafttype.get(bp) == "COGUMELO") {
				custom.setItem(22, newItem(Material.RED_MUSHROOM, "�bRecrafts de Cogumelo",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de seu recraft!", "" }));
			} else if (Inventory1v1Custom.recrafttype.get(bp) == "COCOABEAN") {
				custom.setItem(22, newItem(Material.COCOA, "�bRecrafts de Cocoabean",
						new String[] { "�3Clique aqui para mudar", "�3o tipo de seu recraft!", "" }));
			}
		}
		if (Inventory1v1Custom.recraft.containsKey(bp)) {
			if (Inventory1v1Custom.recraft.get(bp)) {
				custom.setItem(23, newItem(Material.BROWN_MUSHROOM, "�aCom Recraft",
						new String[] { "�3Clique aqui para", "�3desativar o recraft!", "" }));
			} else if (!Inventory1v1Custom.recraft.get(bp)) {
				custom.setItem(23, newItem(Material.MAGMA_CREAM, "�cSem Recraft",
						new String[] { "�3Clique aqui para", "�3ativar o recraft!", "" }));
			}
		}
		if (Inventory1v1Custom.sharpness.containsKey(bp)) {
			if (Inventory1v1Custom.sharpness.get(bp)) {
				custom.setItem(24, newItem(Material.ENCHANTED_BOOK, "�3Com sharpness",
						new String[] { "�3Clique aqui para", "�3tirar a afia\u00e7ao da espada!", "" }));
			} else if (!Inventory1v1Custom.sharpness.get(bp)) {
				custom.setItem(24, newItem(Material.BOOK, "�3Sem sharpness",
						new String[] { "�3Clique aqui para", "�3colocar afia\u00e7ao na espada!", "" }));
			}
		}
		if (Inventory1v1Custom.fullsoup.containsKey(bp)) {
			if (Inventory1v1Custom.fullsoup.get(bp)) {
				custom.setItem(29, newItem(Material.MUSHROOM_SOUP, "�2Full sopa",
						new String[] { "�3Clique aqui para", "�3usar 1 hotbar apenas", "" }));
			} else if (!Inventory1v1Custom.fullsoup.get(bp)) {
				custom.setItem(29, newItem(Material.BOWL, "�21 Hotbar",
						new String[] { "�3Clique aqui para", "�3usar full sopa", "" }));
			}
		}
		bp.openInventory(custom);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public final void onCustomItensChange(final InventoryClickEvent e) {
		if (e.getWhoClicked() instanceof Player) {
			final Player bp = (Player) e.getWhoClicked();
			if (e.getInventory().getName().equalsIgnoreCase("�c1v1 contra " + Inventory1v1Custom.playername.get(bp))
					&& e.getCurrentItem() != null) {
				if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.espada.put(bp, Material.STONE_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.STONE_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.espada.put(bp, Material.IRON_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.IRON_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.espada.put(bp, Material.DIAMOND_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.espada.put(bp, Material.WOOD_SWORD);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.armaduras.put(bp, "IRON");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.IRON_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.armaduras.put(bp, "DIAMOND");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.DIAMOND_CHESTPLATE) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.armaduras.put(bp, "SEM");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.GOLD_HELMET) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.armaduras.put(bp, "LEATHER");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.RED_MUSHROOM) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.recrafttype.put(bp, "COCOABEAN");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.COCOA) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.recrafttype.put(bp, "COGUMELO");
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.BROWN_MUSHROOM) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.recraft.put(bp, false);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.MAGMA_CREAM) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.recraft.put(bp, true);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.ENCHANTED_BOOK) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.sharpness.put(bp, false);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.BOOK) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.sharpness.put(bp, true);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.MUSHROOM_SOUP) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.fullsoup.put(bp, false);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.BOWL) {
					e.setCancelled(true);
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					Inventory1v1Custom.fullsoup.put(bp, true);
					openCustomInventory(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
				}
				if (e.getCurrentItem().getType() == Material.WOOL) {
					e.setCancelled(true);
					bp.closeInventory();
					if (Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)) == null) {
						bp.closeInventory();
						bp.sendMessage("�cEste jogador est\u00e1 offline.");
						return;
					}
					bp.sendMessage("�7Voc\u00ea enviou um desafio de 1v1 customizado para �b"
							+ Inventory1v1Custom.playername.get(bp));
					Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp))
							.sendMessage("�eVoc\u00ea recebeu desafio de 1v1 customizado de �7" + bp.getName());
					X1WarpListener.cooldown.add(bp);
					X1WarpListener.challengec.put(bp, Bukkit.getPlayer((String) Inventory1v1Custom.playername.get(bp)));
					Bukkit.getScheduler().runTaskLater((Plugin) WeavenPvP.getInstance(), (Runnable) new Runnable() {
						@Override
						public void run() {
							if (X1WarpListener.cooldown.contains(bp)) {
								X1WarpListener.cooldown.remove(bp);
							}
							if (X1WarpListener.challengec.containsKey(bp)) {
								X1WarpListener.challengec.remove(bp);
							}
						}
					}, 100L);
				} else {
					e.setCancelled(true);
				}
			}
		}
	}
}
