package br.com.skyprogrammer.cophenix.zenixpvp.api.admin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.HashSet;
import java.util.UUID;
import java.util.Set;

public class AdminMode
{
    private static final AdminMode instanceOfAdminMode;
    private Set<UUID> setOfAdmins;
    
    static {
        instanceOfAdminMode = new AdminMode();
    }
    
    public AdminMode() {
        this.setOfAdmins = new HashSet<UUID>();
    }
    
    public void addAdmin(final UUID uniqueIdToAdd) {
        if (!this.setOfAdmins.contains(uniqueIdToAdd)) {
            this.setOfAdmins.add(uniqueIdToAdd);
        }
    }
    
    public boolean isAdmin(final Player playerToCheck) {
        return (boolean)this.setOfAdmins.contains(playerToCheck.getUniqueId());
    }
    
    public void removeAdmin(final UUID uniqueIdToRemove) {
        if (this.setOfAdmins.contains(uniqueIdToRemove)) {
            this.setOfAdmins.remove(uniqueIdToRemove);
        }
    }
    
    public void alert(final String messageToAlert, final String permissionToReceive) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player localOnlinePlayer = onlinePlayers[i];
            if (localOnlinePlayer.hasPermission(permissionToReceive)) {
                localOnlinePlayer.sendMessage("§7(!) " + messageToAlert);
            }
        }
    }
    
    public int getSizeOfPlayersInAdmin() {
        return this.setOfAdmins.size();
    }
    
    public static AdminMode getInstance() {
        return AdminMode.instanceOfAdminMode;
    }
}
