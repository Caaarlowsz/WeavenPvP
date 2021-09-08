package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class StoreCommand extends CommandHandler
{
    public StoreCommand() {
        super("loja", new String[] { "store", "vip" });
    }
    
    @Override
    public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
        commandSender.sendMessage("§f§lADQUIRA §e§lVIP §f§lEM §3§lWWW.CELTZMC.COM");
        return false;
    }
}
