package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import org.bukkit.command.CommandSender;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class AplicateCommand extends CommandHandler
{
    public AplicateCommand(final String commandName, final String[] commandAliases) {
        super(commandName, commandAliases);
    }
    
    @Override
    public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
        return false;
    }
}
