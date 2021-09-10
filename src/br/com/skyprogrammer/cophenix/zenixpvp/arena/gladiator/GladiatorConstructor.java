package br.com.skyprogrammer.cophenix.zenixpvp.arena.gladiator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;

public class GladiatorConstructor {
	private List<Block> blocks;
	private List<Location> arenaBlocks;
	private int arenaSize;
	private Location arenaLocation;
	private Location side1;
	private Location side2;
	private Material arenaMaterial;
	private int tentatives;
	private BukkitTask whiterTask;
	private BukkitTask cancelFight;
	private Location lastPlayer1Location;
	private Location lastPlayer2Location;
	private Player player1;
	private Player player2;

	public GladiatorConstructor(final int arenaSize, final Location arenaLocation, final int tentatives) {
		this(arenaSize, arenaLocation, Material.GLASS, tentatives);
	}

	public GladiatorConstructor(final int arenaSize, final Location arenaLocation, final Material arenaMaterial,
			final int tentatives) {
		this.blocks = new ArrayList<Block>();
		this.arenaBlocks = new ArrayList<Location>();
		this.arenaSize = arenaSize;
		this.arenaLocation = arenaLocation;
		this.arenaMaterial = arenaMaterial;
		this.tentatives = tentatives;
	}

	public GladiatorConstructor generate() {
		for (int blockX = -this.arenaSize; blockX <= this.arenaSize; ++blockX) {
			for (int blockZ = -this.arenaSize; blockZ <= this.arenaSize; ++blockZ) {
				for (int blockY = -1; blockY <= this.arenaSize; ++blockY) {
					this.blocks.add(this.arenaLocation.clone().add((double) blockX, (double) blockY, (double) blockZ)
							.getBlock());
					final Block blocksLocation = this.arenaLocation.clone()
							.add((double) blockX, (double) blockY, (double) blockZ).getBlock();
					if (!blocksLocation.isEmpty()) {
						final Random random = new Random();
						final int x = random
								.nextInt(this.arenaLocation.getBlockX() + this.arenaSize + this.tentatives * 5);
						final int z = random
								.nextInt(this.arenaLocation.getBlockZ() + this.arenaSize + this.tentatives * 5);
						final Location otherLocation = new Location(this.arenaLocation.getWorld(), (double) x,
								(double) (this.arenaLocation.getBlockY() + -this.tentatives), (double) z);
						final GladiatorConstructor gladiator = new GladiatorConstructor(this.arenaSize, otherLocation,
								this.arenaMaterial, this.tentatives + 1);
						return gladiator;
					}
					if (blockY == this.arenaSize) {
						this.arenaBlocks
								.add(this.arenaLocation.clone().add((double) blockX, (double) blockY, (double) blockZ));
					} else if (blockY == -1) {
						this.arenaBlocks
								.add(this.arenaLocation.clone().add((double) blockX, (double) blockY, (double) blockZ));
					} else if (blockX == -this.arenaSize || blockZ == -this.arenaSize || blockX == this.arenaSize
							|| blockZ == this.arenaSize) {
						this.arenaBlocks
								.add(this.arenaLocation.clone().add((double) blockX, (double) blockY, (double) blockZ));
					}
				}
			}
		}
		for (final Location arenaBlocksLocation : this.arenaBlocks) {
			arenaBlocksLocation.getBlock().setType(this.arenaMaterial);
		}
		(this.side1 = new Location(this.arenaLocation.getWorld(), this.arenaLocation.getX() + 8.0,
				this.arenaLocation.getY() + 1.0, this.arenaLocation.getZ() + 8.0)).setYaw(135.0f);
		(this.side2 = new Location(this.arenaLocation.getWorld(), this.arenaLocation.getX() - 8.0,
				this.arenaLocation.getY() + 1.0, this.arenaLocation.getZ() - 8.0)).setYaw(-45.0f);
		return this;
	}

	public void clearInside() {
		for (final Block block : this.blocks) {
			if (!this.arenaBlocks.contains(block.getLocation())) {
				block.setType(Material.AIR);
			}
		}
	}

	public void setLastLocation(final Location player1, final Location player2) {
		this.lastPlayer1Location = player1;
		this.lastPlayer2Location = player2;
	}

	public void teleport(final Player player1, final Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		player1.teleport(this.side1);
		player2.teleport(this.side2);
	}

	public boolean cancelWhiterTask() {
		if (this.player1 != null) {
			for (final PotionEffect effect : this.player1.getActivePotionEffects()) {
				if (effect.getType() == PotionEffectType.WITHER) {
					this.player1.removePotionEffect(effect.getType());
				}
			}
		}
		if (this.player2 != null) {
			for (final PotionEffect effect : this.player2.getActivePotionEffects()) {
				if (effect.getType() == PotionEffectType.WITHER) {
					this.player2.removePotionEffect(effect.getType());
				}
			}
		}
		try {
			this.whiterTask.cancel();
			return true;
		} catch (NullPointerException localException) {
			return false;
		}
	}

	public boolean cancelFightEnd() {
		if (this.player1 != null && this.lastPlayer1Location != null) {
			this.player1.teleport(this.lastPlayer1Location);
		}
		if (this.player2 != null && this.lastPlayer2Location != null) {
			this.player2.teleport(this.lastPlayer2Location);
		}
		try {
			this.cancelFight.cancel();
			return true;
		} catch (NullPointerException localException) {
			return false;
		}
	}

	public void setCancelFightEnd(final long timeBeforeCancel) {
		this.cancelFight = new BukkitRunnable() {
			public void run() {
				GladiatorConstructor.this.destroy();
			}
		}.runTaskLater((Plugin) Handler.getInstance(), timeBeforeCancel);
	}

	public void startWhiter(final long timeBeforeStart) {
		this.whiterTask = new BukkitRunnable() {
			public void run() {
				if (GladiatorConstructor.this.player1 != null) {
					GladiatorConstructor.this.player1
							.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10000, 2));
				}
				if (GladiatorConstructor.this.player2 != null) {
					GladiatorConstructor.this.player2
							.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 10000, 2));
				}
			}
		}.runTaskLater((Plugin) Handler.getInstance(), timeBeforeStart);
	}

	public void destroy() {
		this.cancelWhiterTask();
		this.cancelFightEnd();
		for (final Block block : this.blocks) {
			block.setType(Material.AIR);
		}
		for (final Location location : this.arenaBlocks) {
			location.getBlock().setType(Material.AIR);
		}
		this.player1 = null;
		this.player2 = null;
		this.side1 = null;
		this.side2 = null;
		this.lastPlayer1Location = null;
		this.lastPlayer2Location = null;
		this.cancelFight = null;
		this.whiterTask = null;
		this.blocks = null;
		this.arenaBlocks = null;
		this.arenaLocation = null;
		this.arenaMaterial = null;
		this.tentatives = 0;
		this.arenaSize = 0;
	}
}
