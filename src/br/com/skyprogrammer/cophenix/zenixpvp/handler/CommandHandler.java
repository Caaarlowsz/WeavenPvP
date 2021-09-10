package br.com.skyprogrammer.cophenix.zenixpvp.handler;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandHandler extends Command {
	public CommandHandler(final String commandName, final String... commandAliases) {
		super(commandName, "", "/", Arrays.asList(commandAliases));
	}

	public boolean isPlayerSender(final CommandSender commandSenderToCheck) {
		return commandSenderToCheck instanceof Player;
	}

	public Player getPlayerInstance(final CommandSender instanceOfCommandSender) {
		return (Player) instanceOfCommandSender;
	}

	public void sendPlayerOffline(final CommandSender commandSenderToSend, final String stringNameOfTheOfflinePlayer) {
		final OfflinePlayer localOfflinePlayer = Bukkit.getOfflinePlayer(stringNameOfTheOfflinePlayer);
		commandSenderToSend
				.sendMessage("§c§lOFFLINE§f O player " + localOfflinePlayer.getName() + " est\u00e1 offline.");
	}

	public void sendNoPermission(final CommandSender commandSenderToSend) {
		commandSenderToSend.sendMessage("§cVoc\u00ea n\u00e3o tem §fPERMISSAO§c para executar este comando!");
	}

	public void sendNotPlayer(final CommandSender commandSenderToSend) {
		commandSenderToSend.sendMessage("§4§lPLAYERS§f Comando apenas para §c§lPLAYERS!");
	}

	public boolean isInteger(final String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public abstract boolean execute(final CommandSender p0, final String p1, final String[] p2);
}
