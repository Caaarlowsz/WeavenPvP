package br.com.skyprogrammer.cophenix.zenixpvp.kit;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import br.com.skyprogrammer.cophenix.zenixpvp.api.item.ItemBuilder;

public abstract class Kit implements Listener {
	private String kitName;
	private Material kitMaterial;
	private List<String> kitDescription;
	private int dataByte;
	private int kitPrice;
	private int kitCooldown;
	private boolean kitNeedPermission;

	public Kit(final String kitName, final Material kitMaterial, final int dataByte, final int kitPrice,
			final int kitCooldown, final boolean kitNeedPermission, final String... kitDescription) {
		final List<String> listOfKitDescription = new LinkedList<String>();
		int integerOfIdList = 0;
		for (final String stringsOfDescription : kitDescription) {
			++integerOfIdList;
			if (!stringsOfDescription.startsWith("§")) {
				if (integerOfIdList == 2) {
					listOfKitDescription.add("§f" + stringsOfDescription);
				} else {
					listOfKitDescription.add("§7" + stringsOfDescription);
				}
			}
		}
		this.kitName = kitName;
		this.kitMaterial = kitMaterial;
		this.dataByte = dataByte;
		this.kitPrice = kitPrice;
		this.kitCooldown = kitCooldown;
		this.kitNeedPermission = kitNeedPermission;
		this.kitDescription = listOfKitDescription;
	}

	public abstract void removeAbilityIfHas(final Player p0);

	public abstract boolean hasItem();

	public ItemStack getItemStackMenu() {
		ItemStack localKitItemStack = null;
		final List<String> listOfKitDescription = new LinkedList<String>();
		for (final String stringOfList : this.kitDescription) {
			listOfKitDescription.add(stringOfList);
		}
		listOfKitDescription.add("");
		listOfKitDescription.add("§aClique para escolher!");
		if (this.kitName == "Bow") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.ARROW_DAMAGE, 5)
					.enchant(Enchantment.ARROW_INFINITE, 5).enchant(Enchantment.ARROW_FIRE, 5).build();
		} else if (this.kitName == "PvP") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.DAMAGE_ALL, 1)
					.build();
		} else if (this.kitName == "Grandpa") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.KNOCKBACK, 2)
					.build();
		} else {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).build();
		}
		return localKitItemStack;
	}

	public ItemStack getItemStackShop() {
		ItemStack localKitItemStack = null;
		final List<String> listOfKitDescription = new LinkedList<String>();
		for (final String stringOfList : this.kitDescription) {
			listOfKitDescription.add(stringOfList);
		}
		listOfKitDescription.add("");
		listOfKitDescription.add("§2Pre\u00e7o: §f" + this.kitPrice);
		listOfKitDescription.add("§2Clique para comprar este kit!");
		if (this.kitName == "Arrow") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.ARROW_DAMAGE, 5)
					.enchant(Enchantment.ARROW_INFINITE, 5).enchant(Enchantment.ARROW_FIRE, 5).build();
		} else if (this.kitName == "PvP") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.DAMAGE_ALL, 1)
					.build();
		} else if (this.kitName == "Grandpa") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.KNOCKBACK, 2)
					.build();
		} else {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).build();
		}
		return localKitItemStack;
	}

	public ItemStack getItemStack() {
		ItemStack localKitItemStack = null;
		final List<String> listOfKitDescription = new LinkedList<String>();
		for (final String stringOfList : this.kitDescription) {
			listOfKitDescription.add(stringOfList);
		}
		listOfKitDescription.add("");
		if (this.kitName == "Arrow") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.ARROW_DAMAGE, 5)
					.enchant(Enchantment.ARROW_INFINITE, 5).enchant(Enchantment.ARROW_FIRE, 5).build();
		} else if (this.kitName == "PvP") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.DAMAGE_ALL, 1)
					.build();
		} else if (this.kitName == "Grandpa") {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).enchant(Enchantment.KNOCKBACK, 2)
					.build();
		} else {
			localKitItemStack = new ItemBuilder().name("§a" + this.kitName).type(this.kitMaterial).data(this.dataByte)
					.lore((String[]) listOfKitDescription.toArray(new String[0])).build();
		}
		return localKitItemStack;
	}

	public String getName() {
		return this.kitName;
	}

	public Material getMaterial() {
		return this.kitMaterial;
	}

	public byte getDataByte() {
		return (byte) this.dataByte;
	}

	public List<String> getDescription() {
		return this.kitDescription;
	}

	public int getPrice() {
		return this.kitPrice;
	}

	public int getCooldown() {
		return this.kitCooldown;
	}

	public boolean needPermission() {
		return this.kitNeedPermission;
	}

	public Kit setNeedPermissiom(final boolean ifTheKitNeedPermission) {
		this.kitNeedPermission = ifTheKitNeedPermission;
		return this;
	}
}
