package br.com.skyprogrammer.cophenix.zenixpvp.api.item;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	private String itemName;
	private Material itemMaterial;
	private int itemAmount;
	private byte itemData;
	private String[] itemLore;
	private Map<Enchantment, Integer> mapOfEnchantments;

	public ItemBuilder() {
		this.itemName = "";
		this.itemMaterial = Material.STONE;
		this.itemAmount = 1;
		this.itemData = 0;
		this.itemLore = new String[0];
		this.mapOfEnchantments = new HashMap<Enchantment, Integer>();
	}

	public ItemBuilder name(final String name) {
		this.itemName = name;
		return this;
	}

	public ItemBuilder type(final Material type) {
		this.itemMaterial = type;
		return this;
	}

	public ItemBuilder amount(final int amount) {
		this.itemAmount = amount;
		return this;
	}

	public ItemBuilder data(final int data) {
		this.itemData = (byte) data;
		return this;
	}

	public ItemBuilder lore(final String... lore) {
		this.itemLore = lore.clone();
		return this;
	}

	public ItemBuilder enchant(final Enchantment enchantment, final int enchantmentId) {
		this.mapOfEnchantments.put(enchantment, enchantmentId);
		return this;
	}

	public ItemStack build() {
		final ItemStack localItemStack = new ItemStack(this.itemMaterial, this.itemAmount, (short) this.itemData);
		final ItemMeta localItemMeta = localItemStack.getItemMeta();
		localItemMeta.setDisplayName(this.itemName);
		localItemMeta.setLore(Arrays.asList(this.itemLore));
		for (final Map.Entry<Enchantment, Integer> enchant : this.mapOfEnchantments.entrySet()) {
			final Enchantment enchantment = enchant.getKey();
			final Integer integer = enchant.getValue();
			localItemMeta.addEnchant(enchantment, (int) integer, true);
		}
		localItemStack.setItemMeta(localItemMeta);
		return localItemStack;
	}
}
