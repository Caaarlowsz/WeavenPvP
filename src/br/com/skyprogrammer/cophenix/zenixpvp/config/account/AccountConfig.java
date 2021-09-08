package br.com.skyprogrammer.cophenix.zenixpvp.config.account;

import br.com.skyprogrammer.cophenix.zenixpvp.enums.Tag;
import java.util.UUID;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.IOException;
import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import java.io.File;
import org.bukkit.configuration.file.FileConfiguration;

public class AccountConfig
{
    private static FileConfiguration fileConfiguration;
    private static File file;
    
    public AccountConfig(final Handler handlerInstance, final String exactlyNameOfTheFile) {
        if (!handlerInstance.getDataFolder().exists()) {
            handlerInstance.getDataFolder().mkdir();
        }
        AccountConfig.file = new File("plugins/" + handlerInstance.getDataFolder().getName(), exactlyNameOfTheFile);
        if (AccountConfig.file.exists()) {
            try {
                AccountConfig.file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        AccountConfig.fileConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(AccountConfig.file);
    }
    
    public FileConfiguration getFile() {
        return AccountConfig.fileConfiguration;
    }
    
    public void save() {
        try {
            AccountConfig.fileConfiguration.save(AccountConfig.file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public boolean isRegistered(final UUID uniqueIdToCheck) {
        return this.getFile().get("Accounts.Premium." + uniqueIdToCheck) != null;
    }
    
    public boolean isRegistered(final String nickNameToCheck) {
        return this.getFile().get("Accounts.Pirate." + nickNameToCheck.toLowerCase()) != null;
    }
    
    public void register(final UUID uniqueIdToRegister, final String nickNameToRegister) {
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".NickName", (Object)nickNameToRegister);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".Kills", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".Deaths", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".KillStreak", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".XP", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".Money", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".SimpleBox", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".IntermediaryBox", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".AdvancedBox", (Object)0);
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".Group", (Object)Tag.MEMBRO.toString());
        this.getFile().set("Accounts.Premium." + uniqueIdToRegister + ".Lenth", (Object)(-1));
        this.save();
    }
    
    public void register(final String nickNameToRegister) {
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".NickName", (Object)nickNameToRegister);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".Kills", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".Deaths", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".KillStreak", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".XP", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".Money", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".SimpleBox", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".IntermediaryBox", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".AdvancedBox", (Object)0);
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".Group", (Object)Tag.MEMBRO.toString());
        this.getFile().set("Accounts.Pirate." + nickNameToRegister.toLowerCase() + ".Lenth", (Object)(-1));
        this.save();
    }
    
    public String getNameFormat(final UUID uniqueIdToGet) {
        final String nameFormatToGet = this.getFile().getString("Accounts.Premium." + uniqueIdToGet + ".NickName");
        return nameFormatToGet;
    }
    
    public String getNameFormat(final String nickNameToGet) {
        final String nameFormatToGet = this.getFile().getString("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".NickName");
        return nameFormatToGet;
    }
    
    public int getKills(final UUID uniqueIdToGet) {
        final int killsToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".Kills");
        return killsToGet;
    }
    
    public int getKills(final String nickNameToGet) {
        final int killsToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".Kills");
        return killsToGet;
    }
    
    public int getDeaths(final UUID uniqueIdToGet) {
        final int deathsToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".Deaths");
        return deathsToGet;
    }
    
    public int getDeaths(final String nickNameToGet) {
        final int deathsToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".Deaths");
        return deathsToGet;
    }
    
    public int getStreak(final UUID uniqueIdToGet) {
        final int streakToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".KillStreak");
        return streakToGet;
    }
    
    public int getStreak(final String nickNameToGet) {
        final int streakToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".KillStreak");
        return streakToGet;
    }
    
    public int getXp(final UUID uniqueIdToGet) {
        final int xpToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".XP");
        return xpToGet;
    }
    
    public int getXp(final String nickNameToGet) {
        final int xpToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".XP");
        return xpToGet;
    }
    
    public int getMoney(final UUID uniqueIdToGet) {
        final int moneyToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".Money");
        return moneyToGet;
    }
    
    public int getMoney(final String nickNameToGet) {
        final int moneyToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".Money");
        return moneyToGet;
    }
    
    public int getSimpleBox(final UUID uniqueIdToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".SimpleBox");
        return boxToGet;
    }
    
    public int getSimpleBox(final String nickNameToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".SimpleBox");
        return boxToGet;
    }
    
    public int getIntermediaryBox(final UUID uniqueIdToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".IntermediaryBox");
        return boxToGet;
    }
    
    public int getIntermediaryBox(final String nickNameToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".IntermediaryBox");
        return boxToGet;
    }
    
    public int getAdvancedBox(final UUID uniqueIdToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Premium." + uniqueIdToGet + ".AdvancedBox");
        return boxToGet;
    }
    
    public int getAdvancedBox(final String nickNameToGet) {
        final int boxToGet = this.getFile().getInt("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".AdvancedBox");
        return boxToGet;
    }
    
    public String getGroup(final UUID uniqueIdToGet) {
        final String groupToGet = this.getFile().getString("Accounts.Premium." + uniqueIdToGet + ".Group");
        return groupToGet;
    }
    
    public String getGroup(final String nickNameToGet) {
        final String groupToGet = this.getFile().getString("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".Group");
        return groupToGet;
    }
    
    public long getLenth(final UUID uniqueIdToGet) {
        final long longToGet = this.getFile().getLong("Accounts.Premium." + uniqueIdToGet + ".Lenth");
        return longToGet;
    }
    
    public long getLenth(final String nickNameToGet) {
        final long longToGet = this.getFile().getLong("Accounts.Pirate." + nickNameToGet.toLowerCase() + ".Lenth");
        return longToGet;
    }
}
