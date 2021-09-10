package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;

import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class IpCommand extends CommandHandler {
	public IpCommand() {
		super("ip", new String[0]);
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		commandSender.sendMessage("§2Voc\u00ea est\u00e1 conectado em: §fpvp.celtzmc.com");
		return false;
	}
}
