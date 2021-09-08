package br.com.skyprogrammer.cophenix.zenixpvp.api.tab;

import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.Packet;
import org.spigotmc.ProtocolInjector;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TabAPI
{
    public static void setTabTitleToPlayer(final Player playerToSet, String stringAbove, String stringBelow) {
        final CraftPlayer localCraftPlayer = (CraftPlayer)playerToSet;
        if (localCraftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
            return;
        }
        final PlayerConnection localPlayerConnection = localCraftPlayer.getHandle().playerConnection;
        if (stringAbove == null) {
            stringAbove = "";
        }
        stringAbove = ChatColor.translateAlternateColorCodes('&', stringAbove);
        if (stringBelow == null) {
            stringBelow = "";
        }
        stringBelow = ChatColor.translateAlternateColorCodes('&', stringBelow);
        stringAbove = stringAbove.replaceAll("%player%", playerToSet.getDisplayName());
        stringBelow = stringBelow.replaceAll("%player%", playerToSet.getDisplayName());
        final IChatBaseComponent firstIChatBaseComponent = ChatSerializer.a("{'color': 'white', 'text': '" + stringAbove + "'}");
        final IChatBaseComponent secondIChatBaseComponent = ChatSerializer.a("{'color': 'white', 'text': '" + stringBelow + "'}");
        localPlayerConnection.sendPacket((Packet)new ProtocolInjector.PacketTabHeader(firstIChatBaseComponent, secondIChatBaseComponent));
    }
}
