package br.com.skyprogrammer.cophenix.zenixpvp.commands;

import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.caaarlowsz.weavenmc.kitpvp.WeavenPvP;
import br.com.skyprogrammer.cophenix.zenixpvp.handler.CommandHandler;

public class FeastCommand extends CommandHandler {
	public FeastCommand() {
		super("feast", new String[0]);
	}

	@Override
	public boolean execute(final CommandSender commandSender, final String commandLabel, final String[] commandArgs) {
		if (!this.isPlayerSender(commandSender)) {
			return false;
		}
		final Player localPlayer = this.getPlayerInstance(commandSender);
		final List<Location> listOfLocation = WeavenPvP.getManager().getFeast().getAllChestLocations();
		if (listOfLocation.size() <= 0) {
			localPlayer.sendMessage("�cNenhum �fBAU�c foi encontrado!");
			return true;
		}
		final int randomInt = new Random().nextInt(listOfLocation.size());
		localPlayer.setCompassTarget((Location) listOfLocation.get((randomInt == 0) ? 0 : (randomInt - 1)));
		localPlayer.sendMessage("�2Bussola �fAPONTANDO�2 para o �fFEAST�2!");
		return true;
	}
}
