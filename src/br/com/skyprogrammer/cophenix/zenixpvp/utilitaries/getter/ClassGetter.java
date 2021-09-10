package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.getter;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.bukkit.plugin.java.JavaPlugin;

public class ClassGetter {
	public static ArrayList<Class<?>> getClassesForPackage(final JavaPlugin plugin, final String pkgname) {
		final ArrayList<Class<?>> arrayOfClasses = new ArrayList<Class<?>>();
		final CodeSource localSourceCode = plugin.getClass().getProtectionDomain().getCodeSource();
		if (localSourceCode != null) {
			final URL localResource = localSourceCode.getLocation();
			localResource.getPath();
			processJarfile(localResource, pkgname, arrayOfClasses);
		}
		return arrayOfClasses;
	}

	private static Class<?> loadClass(final String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
		} catch (NoClassDefFoundError noClassDefFoundError) {
			return null;
		}
	}

	@SuppressWarnings("resource")
	private static void processJarfile(final URL resource, final String pkgname, final ArrayList<Class<?>> classes) {
		final String relPath = pkgname.replace('.', '/');
		final String resPath = resource.getPath();
		final String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		JarFile jarFile;
		try {
			jarFile = new JarFile(jarPath);
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
		}
		final Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			final JarEntry entry = entries.nextElement();
			final String entryName = entry.getName();
			String className = null;
			if (entryName.endsWith(".class") && entryName.startsWith(relPath)
					&& entryName.length() > relPath.length() + "/".length()) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}
			if (className != null) {
				classes.add(loadClass(className));
			}
		}
	}
}
