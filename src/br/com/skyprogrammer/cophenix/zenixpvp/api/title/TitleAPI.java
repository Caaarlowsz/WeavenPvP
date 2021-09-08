package br.com.skyprogrammer.cophenix.zenixpvp.api.title;

import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.ChatSerializer;
import java.util.ArrayList;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.minecraft.server.v1_7_R4.Packet;
import org.spigotmc.ProtocolInjector;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class TitleAPI
{
    private String title;
    private ChatColor titleColor;
    private String subtitle;
    private ChatColor subtitleColor;
    private int fadeInTime;
    private int stayTime;
    private int fadeOutTime;
    private boolean ticks;
    
    public TitleAPI(final String title) {
        this.title = "";
        this.titleColor = ChatColor.WHITE;
        this.subtitle = "";
        this.subtitleColor = ChatColor.WHITE;
        this.fadeInTime = -1;
        this.stayTime = -1;
        this.fadeOutTime = -1;
        this.ticks = false;
        this.title = title;
    }
    
    public TitleAPI(final String title, final String subtitle) {
        this.title = "";
        this.titleColor = ChatColor.WHITE;
        this.subtitle = "";
        this.subtitleColor = ChatColor.WHITE;
        this.fadeInTime = -1;
        this.stayTime = -1;
        this.fadeOutTime = -1;
        this.ticks = false;
        this.title = title;
        this.subtitle = subtitle;
    }
    
    public TitleAPI(final TitleAPI title) {
        this.title = "";
        this.titleColor = ChatColor.WHITE;
        this.subtitle = "";
        this.subtitleColor = ChatColor.WHITE;
        this.fadeInTime = -1;
        this.stayTime = -1;
        this.fadeOutTime = -1;
        this.ticks = false;
        this.title = title.title;
        this.subtitle = title.subtitle;
        this.titleColor = title.titleColor;
        this.subtitleColor = title.subtitleColor;
        this.fadeInTime = title.fadeInTime;
        this.fadeOutTime = title.fadeOutTime;
        this.stayTime = title.stayTime;
        this.ticks = title.ticks;
    }
    
    public TitleAPI(final String title, final String subtitle, final int fadeInTime, final int stayTime, final int fadeOutTime) {
        this.title = "";
        this.titleColor = ChatColor.WHITE;
        this.subtitle = "";
        this.subtitleColor = ChatColor.WHITE;
        this.fadeInTime = -1;
        this.stayTime = -1;
        this.fadeOutTime = -1;
        this.ticks = false;
        this.title = title;
        this.subtitle = subtitle;
        this.fadeInTime = fadeInTime;
        this.stayTime = stayTime;
        this.fadeOutTime = fadeOutTime;
    }
    
    public void setTitle(final String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public void setSubtitle(final String subtitle) {
        this.subtitle = subtitle;
    }
    
    public String getSubtitle() {
        return this.subtitle;
    }
    
    public void setTitleColor(final ChatColor color) {
        this.titleColor = color;
    }
    
    public void setSubtitleColor(final ChatColor color) {
        this.subtitleColor = color;
    }
    
    public void setFadeInTime(final int time) {
        this.fadeInTime = time;
    }
    
    public void setFadeOutTime(final int time) {
        this.fadeOutTime = time;
    }
    
    public void setStayTime(final int time) {
        this.stayTime = time;
    }
    
    public void setTimingsToTicks() {
        this.ticks = true;
    }
    
    public void setTimingsToSeconds() {
        this.ticks = false;
    }
    
    public void broadcastToPlayers() {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player localOnlinePlayers = onlinePlayers[i];
            this.sendToPlayer(localOnlinePlayers);
        }
    }
    
    public void clearTitle(final Player playerToClear) {
        final EntityPlayer localEntityPlayer = ((CraftPlayer)playerToClear).getHandle();
        final PlayerConnection localPlayerConnection = localEntityPlayer.playerConnection;
        if (localPlayerConnection.networkManager.getVersion() < 47) {
            return;
        }
        final ProtocolInjector.PacketTitle localPacketTitle = new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.CLEAR);
        localPlayerConnection.sendPacket((Packet)localPacketTitle);
    }
    
    public void resetTitle(final Player playerToReset) {
        final EntityPlayer localEntityPlayer = ((CraftPlayer)playerToReset).getHandle();
        final PlayerConnection localPlayerConnection = localEntityPlayer.playerConnection;
        if (localPlayerConnection.networkManager.getVersion() < 47) {
            return;
        }
        final ProtocolInjector.PacketTitle localPacketTitle = new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.RESET);
        localPlayerConnection.sendPacket((Packet)localPacketTitle);
    }
    
    public void sendToPlayer(final Player playerToSend) {
        this.resetTitle(playerToSend);
        final EntityPlayer localEntityPlayer = ((CraftPlayer)playerToSend).getHandle();
        final PlayerConnection localPlayerConnection = localEntityPlayer.playerConnection;
        if (localPlayerConnection.networkManager.getVersion() < 47) {
            final ArrayList<String> arrayListOfLines = new ArrayList<String>();
            if (!this.title.isEmpty()) {
                arrayListOfLines.add(this.title);
            }
            if (!this.subtitle.isEmpty()) {
                arrayListOfLines.add(this.subtitle);
            }
            final String[] arrayOfString = new String[arrayListOfLines.size()];
            arrayListOfLines.toArray(arrayOfString);
            return;
        }
        ProtocolInjector.PacketTitle packetTitle = new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TIMES, this.fadeInTime * (this.ticks ? 1 : 20), this.stayTime * (this.ticks ? 1 : 20), this.fadeOutTime * (this.ticks ? 1 : 20));
        if (this.fadeInTime != -1 && this.fadeOutTime != -1 && this.stayTime != -1) {
            localPlayerConnection.sendPacket((Packet)packetTitle);
        }
        IChatBaseComponent serializer = ChatSerializer.a("{text:\"" + ChatColor.translateAlternateColorCodes('&', this.title) + "\",color:" + this.titleColor.name().toLowerCase() + "}");
        packetTitle = new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, serializer);
        localPlayerConnection.sendPacket((Packet)packetTitle);
        if (this.subtitle != "") {
            serializer = ChatSerializer.a("{text:\"" + ChatColor.translateAlternateColorCodes('&', this.subtitle) + "\",color:" + this.subtitleColor.name().toLowerCase() + "}");
            packetTitle = new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, serializer);
            localPlayerConnection.sendPacket((Packet)packetTitle);
        }
    }
}
