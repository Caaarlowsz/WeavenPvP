package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.loaders;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.getter.ClassGetter;

public class CommandLoader {
	public static void loadCommands(final JavaPlugin instanceOfJavaPlugin, final String stringOfThePackageNameToLoad) {
		int sucess = 0;
		int error = 0;
		try {
			for (final Class<?> classConstructor : ClassGetter.getClassesForPackage(instanceOfJavaPlugin,
					stringOfThePackageNameToLoad)) {
				if (Command.class.isAssignableFrom(classConstructor)) {
					try {
						Command instanceOfCommand = null;
						try {
							final Constructor<?> instanceOfClassConstructor = classConstructor
									.getConstructor(instanceOfJavaPlugin.getClass());
							instanceOfCommand = (Command) instanceOfClassConstructor.newInstance(instanceOfJavaPlugin);
						} catch (Exception e) {
							instanceOfCommand = (Command) classConstructor.newInstance();
						}
						registerCommand(instanceOfCommand);
						++sucess;
					} catch (Exception e2) {
						++error;
					}
				}
			}
		} catch (Exception ex) {
		}
		instanceOfJavaPlugin.getLogger().info("Total of loaded commands: (" + sucess + ")");
		instanceOfJavaPlugin.getLogger().info("Total of commands with error: (" + error + ")");
	}

	private static void registerCommand(final Command instanceOfCommand) {
		try {
			final Field commandField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandField.setAccessible(true);
			final CommandMap commandMap = (CommandMap) commandField.get(Bukkit.getServer());
			commandMap.register("kitpvp", instanceOfCommand);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
			ex.printStackTrace();
		}
	}
}
