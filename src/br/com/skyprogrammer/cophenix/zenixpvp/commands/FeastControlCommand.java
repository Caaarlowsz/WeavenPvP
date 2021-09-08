package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import java.util.HashSet;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import org.bukkit.command.CommandSender;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class FeastControlCommand extends CommandHandler
{
    public FeastControlCommand() {
        super("feastcontrol", new String[0]);
    }
    
    @Override
    public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
        if (!this.isPlayerSender(commandSender)) {
            this.sendNotPlayer(commandSender);
            return true;
        }
        final Player localPlayer = this.getPlayerInstance(commandSender);
        if (!localPlayer.hasPermission("pvp.cmd.feastcontrol")) {
            this.sendNoPermission(commandSender);
            return true;
        }
        if (commandArgs.length == 0) {
            localPlayer.sendMessage("§e§lFEAST§f Utilize: /feastcontrol <stop>, <addchest>, <removechest>, <start> ou <settime> + <time>");
            return true;
        }
        if (commandArgs.length == 1) {
            if (commandArgs[0].equalsIgnoreCase("stop")) {
                Handler.getManager().getFeastManager().stop();
                localPlayer.sendMessage("§2Voc\u00ea §fCANCELOU§2 o feast caso estivesse em progresso.");
                return true;
            }
            if (commandArgs[0].equalsIgnoreCase("start")) {
                Handler.getManager().getFeastManager().stop();
                Handler.getManager().getFeastManager().start();
                localPlayer.sendMessage("§2Voc\u00ea §fINICIOU§2 o progresso do feast!");
                return true;
            }
            if (commandArgs[0].equalsIgnoreCase("settime")) {
                localPlayer.sendMessage("§e§lTEMPO§f Utilize: /feastcontrol settime <tempo> ex: 10s, 10m ou 10h");
                return true;
            }
            if (commandArgs[0].equalsIgnoreCase("addchest")) {
                final Block targetBlock = localPlayer.getTargetBlock((HashSet)null, 200);
                if (targetBlock.getType() != Material.CHEST) {
                    localPlayer.sendMessage("§cVoc\u00ea n\u00e3o est\u00e1 §fOLHANDO§c para um §fBAU§c!");
                    return true;
                }
                Handler.getManager().getFeast().addChest(targetBlock.getLocation());
                localPlayer.sendMessage("§2Voc\u00ea §fADICIONOU§2 um §fBAU§2 para o feast!");
                return true;
            }
            else {
                if (!commandArgs[0].equalsIgnoreCase("removechest")) {
                    localPlayer.sendMessage("§e§lFEAST§f Utilize: /feastcontrol <stop>, <addchest>, <removechest>, <start> ou <settime> + <time>");
                    return true;
                }
                final Block targetBlock = localPlayer.getTargetBlock((HashSet)null, 200);
                if (targetBlock.getType() != Material.CHEST) {
                    localPlayer.sendMessage("§cVoc\u00ea n\u00e3o est\u00e1 §fOLHANDO§c para um §fBAU§c!");
                    return true;
                }
                Handler.getManager().getFeast().removeChest(targetBlock.getLocation());
                localPlayer.sendMessage("§2Voc\u00ea §fREMOVEU§2 um §fBAU§2 do feast!");
                return true;
            }
        }
        else {
            if (commandArgs.length == 2) {
                if (commandArgs[0].equalsIgnoreCase("settime")) {
                    final boolean isSecond = commandArgs[1].contains("s") || commandArgs[0].contains("S");
                    final boolean isMinute = commandArgs[1].contains("m") || commandArgs[0].contains("M");
                    final boolean isHour = commandArgs[1].contains("h") || commandArgs[0].contains("H");
                    if (isSecond && isMinute && isHour) {
                        commandSender.sendMessage("§e§lTEMPO§f Utilize apenas 's' , 'm' ou 'h' para indicar o tempo!");
                        return true;
                    }
                    if (!isSecond && !isMinute && !isHour) {
                        try {
                            final int integerOfTime = Integer.valueOf(commandArgs[1]);
                            Handler.getManager().getFeastManager().setTimer(integerOfTime);
                            commandSender.sendMessage("§2Voc\u00ea §fALTEROU§2 o tempo para §f" + commandArgs[1]);
                            return true;
                        }
                        catch (NumberFormatException localException) {
                            commandSender.sendMessage("§e§lTEMPO§f Para definir o tempo sem utilizar 's' ou 'm' voc\u00ea deve usar apenas numeros.");
                            return true;
                        }
                    }
                    if (isSecond) {
                        try {
                            final int integerOfTime = Integer.valueOf(commandArgs[1].replace("s", "").replace("S", "")) * 1;
                            Handler.getManager().getFeastManager().setTimer(integerOfTime);
                            commandSender.sendMessage("§2Voc\u00ea §fALTEROU§2 o tempo para §f" + commandArgs[1]);
                            return true;
                        }
                        catch (NumberFormatException localException) {
                            commandSender.sendMessage("§e§lTEMPO§f Para definir segundos use apenas 1 's' como string; e coloque 's' no final do valor tempo escolhido. Ex: 10s");
                            return true;
                        }
                    }
                    if (isMinute) {
                        try {
                            final int integerOfTime = 60 * Integer.valueOf(commandArgs[1].replace("m", "").replace("M", ""));
                            Handler.getManager().getFeastManager().setTimer(integerOfTime + 1);
                            commandSender.sendMessage("§2Voc\u00ea §fALTEROU§2 o tempo para §f" + commandArgs[1]);
                            return true;
                        }
                        catch (NumberFormatException localException) {
                            commandSender.sendMessage("§e§lTEMPO§f Para definir minutos use apenas 1 'm' como string; e coloque 'm' no final do valor tempo escolhido. Ex: 10m");
                            return true;
                        }
                    }
                    if (!isHour) {
                        return false;
                    }
                    try {
                        final int integerOfTime = 3600 * Integer.valueOf(commandArgs[1].replace("h", "").replace("H", ""));
                        Handler.getManager().getFeastManager().setTimer(integerOfTime);
                        commandSender.sendMessage("§2Voc\u00ea §fALTEROU§2 o tempo para §f" + commandArgs[1]);
                        return true;
                    }
                    catch (NumberFormatException localException) {
                        commandSender.sendMessage("§e§lTEMPO§f Para definir horas use apenas 1 'h' como string; e coloque 'h' no final do valor tempo escolhido. Ex: 10h");
                        return true;
                    }
                }
                localPlayer.sendMessage("§e§lFEAST§f Utilize: /feastcontrol <stop>, <addchest>, <removechest>, <start> ou <settime> + <time>");
                return true;
            }
            localPlayer.sendMessage("§e§lFEAST§f Utilize: /feastcontrol <stop>, <addchest>, <removechest>, <start> ou <settime> + <time>");
            return true;
        }
    }
}
