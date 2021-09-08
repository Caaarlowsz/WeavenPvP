package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.entity.Player;
import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.strings.StringFormal;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.command.CommandSender;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class SetWarpCommand extends CommandHandler
{
    public SetWarpCommand() {
        super("set", new String[] { "setwarp", "warpset" });
    }
    
    @Override
    public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
        if (!this.isPlayerSender(commandSender)) {
            this.sendNotPlayer(commandSender);
            return true;
        }
        final Player localPlayer = this.getPlayerInstance(commandSender);
        if (!localPlayer.hasPermission("pvp.cmd.setwarp")) {
            this.sendNoPermission(commandSender);
            return true;
        }
        if (commandArgs.length == 0) {
            localPlayer.sendMessage("§e§lWARP§f Utilize: /setwarp <nome da warp>");
            return true;
        }
        if (commandArgs.length != 1) {
            localPlayer.sendMessage("§e§lWARP§f Utilize: /setwarp <nome da warp>");
            return true;
        }
        final String stringOfTheWarpName = commandArgs[0];
        if (stringOfTheWarpName.length() > 16) {
            localPlayer.sendMessage("§cO nome desta §fWARP§c est\u00e1 §fGRANDE DEMAIS§c!");
            return true;
        }
        Handler.getManager().getWarps().set(localPlayer, stringOfTheWarpName);
        localPlayer.sendMessage("§2A §fWARP§2 com o nome §f" + StringFormal.format(stringOfTheWarpName) + "§2 foi setada neste local!");
        localPlayer.sendMessage("§ePara §fDELETA-LA§e utilize: §f/deletwarp <nome da warp>");
        return true;
    }
}
