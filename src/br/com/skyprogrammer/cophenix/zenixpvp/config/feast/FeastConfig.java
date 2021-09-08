package br.com.skyprogrammer.cophenix.zenixpvp.config.feast;

import java.util.Iterator;
import org.bukkit.Bukkit;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class FeastConfig
{
    private static FileConfiguration fileConfiguration;
    private static File file;
    
    public FeastConfig(final Handler handlerInstance, final String exactlyNameOfTheFile) {
        if (!handlerInstance.getDataFolder().exists()) {
            handlerInstance.getDataFolder().mkdir();
        }
        FeastConfig.file = new File("plugins/" + handlerInstance.getDataFolder().getName(), exactlyNameOfTheFile);
        if (FeastConfig.file.exists()) {
            try {
                FeastConfig.file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        FeastConfig.fileConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(FeastConfig.file);
    }
    
    public FileConfiguration getFile() {
        return FeastConfig.fileConfiguration;
    }
    
    public void save() {
        try {
            FeastConfig.fileConfiguration.save(FeastConfig.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addChest(final Location locationToAdd) {
        this.getFile().set(String.valueOf(String.valueOf(locationToAdd.getBlockX()).replace(".", ",")) + "=" + String.valueOf(locationToAdd.getBlockY()).replace(".", ",") + "=" + String.valueOf(locationToAdd.getBlockZ()).replace(".", ",") + "=" + String.valueOf(locationToAdd.getPitch()).replace(".", ",") + "=" + String.valueOf(locationToAdd.getYaw()).replace(".", ",") + ".World", (Object)locationToAdd.getWorld().getName());
        this.save();
    }
    
    public void removeChest(final Location locationToRemove) {
        this.getFile().set(String.valueOf(String.valueOf(locationToRemove.getBlockX()).replace(".", ",")) + "=" + String.valueOf(locationToRemove.getBlockY()).replace(".", ",") + "=" + String.valueOf(locationToRemove.getBlockZ()).replace(".", ",") + "=" + String.valueOf(locationToRemove.getPitch()).replace(".", ",") + "=" + String.valueOf(locationToRemove.getYaw()).replace(".", ","), (Object)null);
        this.save();
    }
    
    public boolean hasLocation() {
        return (boolean)(this.getFile().getKeys(false).size() >= 1);
    }
    
    public List<Location> getAllChestLocations() {
        final List<Location> listOfLocations = new LinkedList<Location>();
        if (this.getFile().getKeys(false).size() >= 1) {
            for (final String locationsString : this.getFile().getKeys(false)) {
                final String[] arrayOfString = locationsString.split("=");
                final double x = Double.valueOf(arrayOfString[0].replace(",", "."));
                final double y = Double.valueOf(arrayOfString[1].replace(",", "."));
                final double z = Double.valueOf(arrayOfString[2].replace(",", "."));
                final float pitch = Float.valueOf(arrayOfString[3].replace(",", "."));
                final float yaw = Float.valueOf(arrayOfString[4].replace(",", "."));
                listOfLocations.add(new Location(Bukkit.getWorld("world"), x, y, z, pitch, yaw));
            }
        }
        return listOfLocations;
    }
}
