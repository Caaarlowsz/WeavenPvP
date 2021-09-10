package br.com.skyprogrammer.cophenix.zenixpvp.manager.gamer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import br.com.skyprogrammer.cophenix.zenixpvp.account.gamer.Gamer;

public class GamerManager {
	private Map<UUID, Gamer> mapOfStoredGamers;
	private HashMap<String, UUID> gamerUUID;

	public GamerManager() {
		this.mapOfStoredGamers = new HashMap<UUID, Gamer>();
		this.gamerUUID = new HashMap<String, UUID>();
	}

	public Map<UUID, Gamer> getMapOfStoredGamers() {
		return this.mapOfStoredGamers;
	}

	public void addUUIDToName(final String name, final UUID uuid) {
		this.gamerUUID.put(name, uuid);
	}

	public void removeUUIDFromName(final String name) {
		this.gamerUUID.remove(name);
	}

	public UUID getUUIDFromGamerName(final String name) {
		return this.gamerUUID.get(name);
	}

	public Collection<Gamer> getStoredGamers() {
		return this.mapOfStoredGamers.values();
	}

	public Gamer getGamer(final UUID uniqueIdToGet) {
		final Gamer storedGamer = this.mapOfStoredGamers.get(uniqueIdToGet);
		return storedGamer;
	}

	public void addGamer(final UUID uniqueIdToStore, final Gamer gamerToAdd) {
		if (this.mapOfStoredGamers.containsKey(uniqueIdToStore)) {
			this.mapOfStoredGamers.remove(uniqueIdToStore);
		}
		this.mapOfStoredGamers.put(uniqueIdToStore, gamerToAdd);
	}

	public void removeGamer(final UUID uniqueIdToRemove) {
		if (this.mapOfStoredGamers.containsKey(uniqueIdToRemove)) {
			this.mapOfStoredGamers.remove(uniqueIdToRemove);
		}
	}
}
