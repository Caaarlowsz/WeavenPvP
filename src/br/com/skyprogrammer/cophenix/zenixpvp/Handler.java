package br.com.skyprogrammer.cophenix.zenixpvp;

import br.com.skyprogrammer.cophenix.zenixpvp.manager.Manager;
import org.bukkit.plugin.java.JavaPlugin;

public class Handler extends JavaPlugin
{
    private static Handler instanceOfHandler;
    private static Manager instanceOfManager;
    
    public void onLoad() {
        super.onLoad();
        Handler.instanceOfHandler = this;
    }
    
    public void onEnable() {
        super.onEnable();
        Handler.instanceOfManager = new Manager(this);
    }
    
    public void onDisable() {
        super.onDisable();
    }
    
    public static Manager getManager() {
        return Handler.instanceOfManager;
    }
    
    public static Handler getInstance() {
        return Handler.instanceOfHandler;
    }
}
