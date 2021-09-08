package br.com.skyprogrammer.cophenix.zenixpvp.enums;

public enum Tag
{
    MEMBRO("MEMBRO", 0, "§7Membro", "§7", "", "r", "§7§lMEMBROS", "tag.membro", new String[] { "membro", "normal", "default" }), 
    LIGHT("LIGHT", 1, "§aLight", "§a§lLIGHT §a", "", "q", "§a§lLIGHTS", "tag.light", new String[] { "light" }), 
    PREMIUM("PREMIUM", 2, "§6Premium", "§6§lPREMIUM §6", "", "p", "§6§lPREMIUMS", "tag.premium", new String[] { "premium" }), 
    BETA("BETA", 3, "§1Beta", "§1§lBETA §1", "", "o", "§1§lBETAS", "tag.beta", new String[] { "beta" }), 
    ULTIMATE("ULTIMATE", 4, "§dUltimate", "§d§lULTIMATE §d", "", "n", "§d§lULTIMATES", "tag.ultimate", new String[] { "ultimate" }), 
    ELITE("ELITE", 5, "§bElite", "§b§lELITE §b", "", "m", "§b§lELITES", "tag.elite", new String[] { "elite" }), 
    YOUTUBER("YOUTUBER", 6, "§bYoutuber", "§b§lYOUTUBER §b", "", "l", "§b§lYOUTUBERS", "tag.yt", new String[] { "yt", "youtuber" }), 
    DESIGNER("DESIGNER", 7, "§2Designer", "§2§lDESIGNER §2", "", "k", "§2§lDESIGNERS", "tag.designer", new String[] { "designer" }), 
    AJUDANTE("AJUDANTE", 8, "§9Ajudante", "§9§lAJUDANTE §9", "", "j", "§9§lAJUDANTES", "tag.ajudante", new String[] { "ajudante", "helper" }), 
    BUILDER("BUILDER", 9, "§eBuilder", "§e§lBUILDER §e", "", "i", "§e§lBUILDERS", "tag.builder", new String[] { "builder", "construtor" }), 
    YOUTUBERPLUS("YOUTUBERPLUS", 10, "§3Youtuberplus", "§3§lYOUTUBER+ §3", "", "h", "§3§lYOUTUBERSPLUS", "tag.ytplus", new String[] { "yt+", "youtuber+", "ytplus", "youtuberplus" }), 
    TRIAL("TRIAL", 11, "§5Trial", "§5§lTRIAL §5", "", "g", "§5§lTRIAIS", "tag.trial", new String[] { "trial", "tmod", "trial-mod" }), 
    MOD("MOD", 12, "§5Mod", "§5§lMOD §5", "", "f", "§5§lMODERADORES", "tag.mod", new String[] { "mod", "moderador" }), 
    MODGC("MODGC", 13, "§5ModGc", "§5§lMODGC §5", "", "e", "§5§lMODERADORES-GCS", "tag.modgc", new String[] { "modgc", "gcdetector" }), 
    MODPLUS("MODPLUS", 14, "§5Mod+", "§5§lMOD+ §5", "", "e", "§5§lMODERADORESPLUS", "tag.mod+", new String[] { "mod+", "modplus", "moderadorplus" }), 
    ADMIN("ADMIN", 15, "§cAdmin", "§c§lADMIN §c", "", "d", "§c§lADMINISTRADORES", "tag.admin", new String[] { "admin", "adm", "administrador" }), 
    GERENTE("GERENTE", 16, "§cGerente", "§c§lGERENTE §c", "", "c", "§c§lGERENTES", "tag.gerente", new String[] { "gerente", "ger" }), 
    DEV("DEV", 17, "§3Dev", "§3§lDEV §3", "", "b", "§3§lDEVELOPERS", "tag.dev", new String[] { "dev", "developer", "programmer" }), 
    DIRETOR("DIRETOR", 18, "§4Diretor", "§4§lDIRETOR §4", "", "b", "§4§lDIRETORES", "tag.diretor", new String[] { "diretor" }), 
    DONO("DONO", 19, "§4Dono", "§4§lDONO §4", "", "a", "§4§lDONOS", "tag.dono", new String[] { "dono", "master", "owner" });
    
    private String tagName;
    private String tagPrefix;
    private String tagSuffix;
    private String tagTeamName;
    private String tagNameDouble;
    private String tagPermission;
    private String[] tagAliases;
    
    private Tag(final String s, final int n, final String tagName, final String tagPrefix, final String tagSuffix, final String tagTeamName, final String tagNameDouble, final String tagPermission, final String... tagAliases) {
        this.tagName = tagName;
        this.tagPrefix = tagPrefix;
        this.tagSuffix = tagSuffix;
        this.tagTeamName = tagTeamName;
        this.tagNameDouble = tagNameDouble;
        this.tagPermission = tagPermission;
        this.tagAliases = tagAliases.clone();
    }
    
    public Tag setSuffix(final String newTagSuffix) {
        this.tagSuffix = newTagSuffix;
        return this;
    }
    
    public String getName() {
        return this.tagName;
    }
    
    public String getPrefix() {
        return this.tagPrefix;
    }
    
    public String getSuffix() {
        return this.tagSuffix;
    }
    
    public String getTeamName() {
        return this.tagTeamName;
    }
    
    public String getNameDouble() {
        return this.tagNameDouble;
    }
    
    public String getPermission() {
        return this.tagPermission;
    }
    
    public String[] getAliases() {
        return this.tagAliases;
    }
}
