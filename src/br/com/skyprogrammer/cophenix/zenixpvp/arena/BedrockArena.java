package br.com.skyprogrammer.cophenix.zenixpvp.arena;

import org.bukkit.entity.Item;
import org.bukkit.World;
import org.bukkit.Bukkit;
import java.util.Iterator;
import org.bukkit.Material;
import java.util.LinkedList;
import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.Location;

public class BedrockArena
{
    private int size;
    private Location location;
    private List<Block> blocks;
    private boolean build;
    
    public BedrockArena(final int size, final Location location) {
        this.build = true;
        this.size = size;
        this.location = location;
        this.blocks = new LinkedList<Block>();
        final List<Location> b = new LinkedList<Location>();
        for (int blockX = -size; blockX <= size; ++blockX) {
            for (int blockZ = -size; blockZ <= size; ++blockZ) {
                for (int blockY = -1; blockY <= size; ++blockY) {
                    this.blocks.add(location.clone().add((double)blockX, (double)blockY, (double)blockZ).getBlock());
                    if (blockY == size) {
                        b.add(location.clone().add((double)blockX, (double)blockY, (double)blockZ));
                    }
                    else if (blockY == -1) {
                        b.add(location.clone().add((double)blockX, (double)blockY, (double)blockZ));
                    }
                    else if (blockX == -size || blockZ == -size || blockX == size || blockZ == size) {
                        b.add(location.clone().add((double)blockX, (double)blockY, (double)blockZ));
                    }
                }
            }
        }
        for (final Location arena : b) {
            arena.getBlock().setType(Material.BEDROCK);
        }
    }
    
    public boolean canBuild() {
        return this.build;
    }
    
    public void setBuild(final boolean build) {
        this.build = build;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public void delet() {
        for (final Block block : this.blocks) {
            block.setType(Material.AIR);
        }
    }
    
    public void undo() {
        this.delet();
    }
    
    public void clear() {
        for (final Block block : this.blocks) {
            if (block.getType() != Material.BEDROCK) {
                block.setType(Material.AIR);
            }
        }
        for (final World world : Bukkit.getWorlds()) {
            for (final Item drops : world.getEntitiesByClass((Class)Item.class)) {
                drops.remove();
            }
        }
    }
}
