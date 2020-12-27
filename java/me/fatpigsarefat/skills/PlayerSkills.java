package me.fatpigsarefat.skills;

import java.util.HashMap;
import java.util.Iterator;
import me.fatpigsarefat.skills.commands.SkillsAdminCommand;
import me.fatpigsarefat.skills.commands.SkillsCommand;
import me.fatpigsarefat.skills.listeners.EntityDamage;
import me.fatpigsarefat.skills.listeners.EntityDamageByEntity;
import me.fatpigsarefat.skills.listeners.InventoryClick;
import me.fatpigsarefat.skills.listeners.PlayerDeath;
import me.fatpigsarefat.skills.listeners.PlayerLeave;
import me.fatpigsarefat.skills.listeners.ResetSkill;
import me.fatpigsarefat.skills.listeners.UpgradeSkill;
import me.fatpigsarefat.skills.managers.FileManager;
import me.fatpigsarefat.skills.managers.SkillManager;
import me.fatpigsarefat.skills.trait.SkillsTrait;
import me.fatpigsarefat.skills.utils.Skill;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

public class PlayerSkills extends JavaPlugin {
    public static PlayerSkills instance;
    private static SkillManager sm;
    private static HashMap<Skill, Integer> skillMultipliers = new HashMap();
    public static HashMap<Player, PotionEffect> potionEffect = new HashMap();
    public boolean serverVersionOnePointSeven = false;
    public static boolean allowReset = true;
    private String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    private static FileManager fileManager;

    public void onEnable() {
        instance = this;
        fileManager = new FileManager(this);
        fileManager.getConfig("config").copyDefaults(true).save();
        fileManager.getConfig("messages").copyDefaults(true).save();
        fileManager.getConfig("gui").copyDefaults(true).save();
        fileManager.getConfig("data").copyDefaults(true).save();
        sm = new SkillManager();
        if (Bukkit.getPluginManager().getPlugin("Citizens") != null) {
            try {
                CitizensAPI.getTraitFactory().registerTrait(TraitInfo.create(SkillsTrait.class).withName("playerskills"));
                this.getLogger().info("Successfully hooked into Citizens.");
            } catch (NoClassDefFoundError | NullPointerException var3) {
                this.getLogger().info("An error occured when trying to register a trait. Your Citizens version might not be supported.");
            }
        } else {
            this.getLogger().info("Citizens not found. NPCs will not be available.");
        }

        Bukkit.getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntity(), this);
        Bukkit.getPluginManager().registerEvents(new EntityDamage(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeave(), this);
        Bukkit.getPluginManager().registerEvents(new UpgradeSkill(), this);
        Bukkit.getPluginManager().registerEvents(new ResetSkill(), this);
        Bukkit.getPluginCommand("skills").setExecutor(new SkillsCommand());
        Bukkit.getPluginCommand("skillsadmin").setExecutor(new SkillsAdminCommand());
        this.checkIfHealth();
        Iterator var1 = fileManager.getConfig("config").get().getConfigurationSection("skills").getKeys(false).iterator();

        while(var1.hasNext()) {
            String s = (String)var1.next();
            skillMultipliers.put(Skill.getSkillByName(s), this.getConfig().getInt("skills." + s + ".increment"));
        }

        allowReset = fileManager.getConfig("gui").get().getBoolean("gui.reset-enabled");
        this.getLogger().info("Your server is running version " + this.version);
        if (this.version.equals("1_7_R1") || this.version.equals("1_7_R2") || this.version.equals("1_7_R3") || this.version.equals("1_7_R4") || this.version.equals("1_8_R1") || this.version.equals("1_8_R2") || this.version.equals("1_8_R3")) {
            this.serverVersionOnePointSeven = true;
        }

    }

    public void checkIfHealth() {
        BukkitScheduler healthCheck = this.getServer().getScheduler();
        healthCheck.scheduleSyncRepeatingTask(this, () -> {
            Iterator var1 = Bukkit.getOnlinePlayers().iterator();

            while(true) {
                while(true) {
                    while(var1.hasNext()) {
                        Player player = (Player)var1.next();
                        int level = (sm.getSkillLevel(player, Skill.HEALTH) - 1) * 2;
                        int additionalEffects = 0;
                        PotionEffect hb = null;
                        Iterator var6 = player.getActivePotionEffects().iterator();

                        while(var6.hasNext()) {
                            PotionEffect effect = (PotionEffect)var6.next();
                            if (effect.getType().toString().equals("PotionEffectType[21, HEALTH_BOOST]")) {
                                hb = effect;
                                additionalEffects += 4 * (effect.getAmplifier() + 1);
                            }
                        }

                        if (!this.version.equals("v1_12_R1") | !this.version.equals("v1_13_R1")) {
                            if (fileManager.getConfig("config").get().getBoolean("worlds.restricted") && !fileManager.getConfig("config").get().getStringList("worlds.allowed-worlds").contains(player.getLocation().getWorld().getName())) {
                                if (player.getMaxHealth() != (double)(20 + additionalEffects)) {
                                    player.setMaxHealth(20.0D);
                                    player.sendMessage("max health set");
                                }
                            } else if (player.getMaxHealth() != (double)(20 + level + additionalEffects)) {
                                player.setMaxHealth((double)(20 + level + additionalEffects));
                                if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                                    potionEffect.put(player, hb);
                                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                                } else if (potionEffect.containsKey(player)) {
                                    player.addPotionEffect((PotionEffect)potionEffect.get(player));
                                    potionEffect.remove(player);
                                }
                            }
                        } else {
                            AttributeInstance healthAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
                            if (fileManager.getConfig("config").get().getBoolean("worlds.restricted") && !fileManager.getConfig("config").get().getStringList("worlds.allowed-worlds").contains(player.getLocation().getWorld().getName())) {
                                if (healthAttribute.getValue() != (double)(20 + additionalEffects)) {
                                    healthAttribute.setBaseValue(20.0D);
                                    player.sendMessage("max health set");
                                }
                            } else if (healthAttribute.getValue() != (double)(20 + level + additionalEffects)) {
                                healthAttribute.setBaseValue((double)(20 + level + additionalEffects));
                                if (player.hasPotionEffect(PotionEffectType.HEALTH_BOOST)) {
                                    potionEffect.put(player, hb);
                                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                                } else if (potionEffect.containsKey(player)) {
                                    player.addPotionEffect((PotionEffect)potionEffect.get(player));
                                    potionEffect.remove(player);
                                }
                            }
                        }
                    }

                    return;
                }
            }
        }, 10L, 10L);
    }

    public static HashMap<Skill, Integer> getSkillMultipliers() {
        return skillMultipliers;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static SkillManager getSkillManager() {
        return sm;
    }
}