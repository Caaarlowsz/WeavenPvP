package br.com.skyprogrammer.cophenix.zenixpvp.config.warp;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;

public class WarpConfig {
	private static FileConfiguration fileConfiguration;
	private static File file;

	public WarpConfig(final WeavenPvP handlerInstance, final String exactlyNameOfTheFile) {
		if (!handlerInstance.getDataFolder().exists()) {
			handlerInstance.getDataFolder().mkdir();
		}
		WarpConfig.file = new File("plugins/" + handlerInstance.getDataFolder().getName(), exactlyNameOfTheFile);
		if (WarpConfig.file.exists()) {
			try {
				WarpConfig.file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		WarpConfig.fileConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(WarpConfig.file);
	}

	public FileConfiguration getFile() {
		return WarpConfig.fileConfiguration;
	}

	public void save() {
		try {
			WarpConfig.fileConfiguration.save(WarpConfig.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean hasWarp(String stringOfWarpNameToCheck) {
		stringOfWarpNameToCheck = stringOfWarpNameToCheck.toLowerCase();
		return this.getFile().get("Warps." + stringOfWarpNameToCheck) != null;
	}

	public void delet(String stringOfWarpNameToDelet) {
		stringOfWarpNameToDelet = stringOfWarpNameToDelet.toLowerCase();
		this.getFile().set("Warps." + stringOfWarpNameToDelet, (Object) null);
		this.save();
	}

	public Location getLocation(String stringOfWarpNameToGet) {
		stringOfWarpNameToGet = stringOfWarpNameToGet.toLowerCase();
		final double x = this.getFile().getDouble("Warps." + stringOfWarpNameToGet + ".X");
		final double y = this.getFile().getDouble("Warps." + stringOfWarpNameToGet + ".Y");
		final double z = this.getFile().getDouble("Warps." + stringOfWarpNameToGet + ".Z");
		final float Pitch = (float) this.getFile().getDouble("Warps." + stringOfWarpNameToGet + ".Pitch");
		final float Yaw = (float) this.getFile().getDouble("Warps." + stringOfWarpNameToGet + ".Yaw");
		final World world = Bukkit.getWorld(this.getFile().getString("Warps." + stringOfWarpNameToGet + ".World"));
		final Location localLocationToReturn = new Location(world, x, y, z, Yaw, Pitch);
		return localLocationToReturn;
	}

	public void set(final Player playerToSet, String stringOfWarpNameToSet) {
		stringOfWarpNameToSet = stringOfWarpNameToSet.toLowerCase();
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".X", (Object) playerToSet.getLocation().getX());
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".Y", (Object) playerToSet.getLocation().getY());
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".Z", (Object) playerToSet.getLocation().getZ());
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".Pitch", (Object) playerToSet.getLocation().getPitch());
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".Yaw", (Object) playerToSet.getLocation().getYaw());
		this.getFile().set("Warps." + stringOfWarpNameToSet + ".World",
				(Object) playerToSet.getLocation().getWorld().getName());
		this.save();
	}
}
