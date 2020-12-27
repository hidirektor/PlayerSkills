// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.managers;

import me.fatpigsarefat.skills.utils.Skill;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.PlayerSkills;

public class ConfigurationManager
{
    private FileManager.Config gui;
    private FileManager.Config config;
    
    public ConfigurationManager() {
        this.gui = PlayerSkills.getFileManager().getConfig("gui");
        this.config = PlayerSkills.getFileManager().getConfig("config");
    }
    
    public ItemStack getItemStack(final String s, final Player player) {
        final String pathRoot = "gui.display." + s + ".";
        String name = ChatColor.translateAlternateColorCodes('&', this.gui.get().getString(pathRoot + "name"));
        name = this.placeholder(name, player);
        final ArrayList<String> lore = new ArrayList<String>();
        if (this.gui.get().contains(pathRoot + "lore")) {
            for (String key : this.gui.get().getStringList(pathRoot + "lore")) {
                key = ChatColor.translateAlternateColorCodes('&', key);
                key = this.placeholder(key, player);
                lore.add(key);
            }
        }
        final ItemStack itemToGive = new ItemStack(Material.getMaterial(this.gui.get().getString(pathRoot + "material")));
        final ItemMeta itemToGiveMeta = itemToGive.getItemMeta();
        itemToGiveMeta.setDisplayName(name);
        itemToGiveMeta.setLore((List)lore);
        itemToGive.setItemMeta(itemToGiveMeta);
        return itemToGive;
    }
    
    private String placeholder(String s, final Player player) {
        final SkillManager sm = PlayerSkills.getSkillManager();
        int strength = 1;
        int criticals = 1;
        int resistance = 1;
        int archery = 1;
        int health = 1;
        strength = sm.getSkillLevel(player, Skill.STRENGTH);
        criticals = sm.getSkillLevel(player, Skill.CRITICALS);
        resistance = sm.getSkillLevel(player, Skill.RESISTANCE);
        archery = sm.getSkillLevel(player, Skill.ARCHERY);
        health = sm.getSkillLevel(player, Skill.HEALTH);
        if (s.contains("strength")) {
            s = s.replace("%strength%", "[" + strength + "/" + sm.getMaximumLevel(Skill.STRENGTH) + "]");
            s = s.replace("%strengthincrement%", PlayerSkills.getSkillMultipliers().get(Skill.STRENGTH).toString());
            final int strengthSkillBefore = (sm.getSkillLevel(player, Skill.STRENGTH) - 1) * PlayerSkills.getSkillMultipliers().get(Skill.STRENGTH) + 100;
            int strengthSkillAfter = sm.getSkillLevel(player, Skill.STRENGTH) * PlayerSkills.getSkillMultipliers().get(Skill.STRENGTH) + 100;
            if (sm.getSkillLevel(player, Skill.STRENGTH) >= sm.getMaximumLevel(Skill.STRENGTH)) {
                strengthSkillAfter = strengthSkillBefore;
            }
            s = s.replace("%strengthprogress%", ChatColor.GREEN.toString() + strengthSkillBefore + "%  " + ChatColor.GRAY + " >>>   " + ChatColor.GREEN + strengthSkillAfter + "%");
        }
        if (s.contains("criticals")) {
            s = s.replace("%criticals%", "[" + criticals + "/" + sm.getMaximumLevel(Skill.CRITICALS) + "]");
            s = s.replace("%criticalsincrement%", PlayerSkills.getSkillMultipliers().get(Skill.CRITICALS).toString());
            final int criticalsSkillBefore = (sm.getSkillLevel(player, Skill.CRITICALS) - 1) * PlayerSkills.getSkillMultipliers().get(Skill.CRITICALS) + 150;
            int criticalsSkillAfter = sm.getSkillLevel(player, Skill.CRITICALS) * PlayerSkills.getSkillMultipliers().get(Skill.CRITICALS) + 150;
            if (sm.getSkillLevel(player, Skill.CRITICALS) >= sm.getMaximumLevel(Skill.CRITICALS)) {
                criticalsSkillAfter = criticalsSkillBefore;
            }
            s = s.replace("%criticalsprogress%", ChatColor.GREEN.toString() + criticalsSkillBefore + "%  " + ChatColor.GRAY + " >>>   " + ChatColor.GREEN + criticalsSkillAfter + "%");
        }
        if (s.contains("resistance")) {
            s = s.replace("%resistance%", "[" + resistance + "/" + sm.getMaximumLevel(Skill.RESISTANCE) + "]");
            s = s.replace("%resistanceincrement%", PlayerSkills.getSkillMultipliers().get(Skill.RESISTANCE).toString());
            final int resistanceSkillBefore = (sm.getSkillLevel(player, Skill.RESISTANCE) - 1) * PlayerSkills.getSkillMultipliers().get(Skill.RESISTANCE);
            int resistanceSkillAfter = sm.getSkillLevel(player, Skill.RESISTANCE) * PlayerSkills.getSkillMultipliers().get(Skill.RESISTANCE);
            if (sm.getSkillLevel(player, Skill.RESISTANCE) >= sm.getMaximumLevel(Skill.RESISTANCE)) {
                resistanceSkillAfter = resistanceSkillBefore;
            }
            s = s.replace("%resistanceprogress%", ChatColor.GREEN.toString() + resistanceSkillBefore + "%  " + ChatColor.GRAY + " >>>   " + ChatColor.GREEN + resistanceSkillAfter + "%");
        }
        if (s.contains("archery")) {
            s = s.replace("%archery%", "[" + archery + "/" + sm.getMaximumLevel(Skill.ARCHERY) + "]");
            s = s.replace("%archeryincrement%", PlayerSkills.getSkillMultipliers().get(Skill.ARCHERY).toString());
            final int archerySkillBefore = (sm.getSkillLevel(player, Skill.ARCHERY) - 1) * PlayerSkills.getSkillMultipliers().get(Skill.ARCHERY) + 100;
            int archerySkillAfter = sm.getSkillLevel(player, Skill.ARCHERY) * PlayerSkills.getSkillMultipliers().get(Skill.ARCHERY) + 100;
            if (sm.getSkillLevel(player, Skill.ARCHERY) >= sm.getMaximumLevel(Skill.ARCHERY)) {
                archerySkillAfter = archerySkillBefore;
            }
            s = s.replace("%archeryprogress%", ChatColor.GREEN.toString() + archerySkillBefore + "%  " + ChatColor.GRAY + " >>>   " + ChatColor.GREEN + archerySkillAfter + "%");
        }
        if (s.contains("health")) {
            s = s.replace("%health%", "[" + health + "/" + sm.getMaximumLevel(Skill.HEALTH) + "]");
            s = s.replace("%healthincrement%", PlayerSkills.getSkillMultipliers().get(Skill.HEALTH).toString());
            final int healthSkillBefore = (sm.getSkillLevel(player, Skill.HEALTH) - 1) * PlayerSkills.getSkillMultipliers().get(Skill.HEALTH) + 20;
            int healthSkillAfter = sm.getSkillLevel(player, Skill.HEALTH) * PlayerSkills.getSkillMultipliers().get(Skill.HEALTH) + 20;
            if (sm.getSkillLevel(player, Skill.HEALTH) >= sm.getMaximumLevel(Skill.HEALTH)) {
                healthSkillAfter = healthSkillBefore;
            }
            s = s.replace("%healthprogress%", ChatColor.GREEN.toString() + healthSkillBefore + "HP " + ChatColor.GRAY + " >>>   " + ChatColor.GREEN + healthSkillAfter + "HP");
        }
        int xpPrice = 1;
        xpPrice = this.config.get().getInt("xp.price");
        if (this.config.get().getBoolean("xp.add-total-to-price")) {
            xpPrice += sm.getTotalPointsSpent(player) * this.config.get().getInt("xp.add-total-to-price-multiplier");
        }
        s = s.replace("%pointsprice%", xpPrice + "XP");
        s = s.replace("%points%", sm.getSkillPoints(player) + "");
        s = s.replace("%username%", player.getName());
        s = s.replace("%expierencelevel%", player.getLevel() + "");
        if (s.contains("expierence")) {
            String expierenceBar = "";
            for (double f = 0.0; f <= player.getExp(); f += 0.03) {
                expierenceBar = expierenceBar + ChatColor.GREEN + "|";
            }
            for (int toAdd = 30 - ChatColor.stripColor(expierenceBar).length(), i = 0; i <= toAdd; ++i) {
                expierenceBar = expierenceBar + ChatColor.GRAY + "|";
            }
            s = s.replace("%expierencebar%", expierenceBar);
        }
        s = s.replace("%totalspent%", sm.getTotalPointsSpent(player) + "");
        s = s.replace("%strength-points-spend%", strength - 1 + "");
        s = s.replace("%criticals-points-spend%", criticals - 1 + "");
        s = s.replace("%resistance-points-spend%", resistance - 1 + "");
        s = s.replace("%archery-points-spend%", archery - 1 + "");
        s = s.replace("%health-points-spend%", health - 1 + "");
        return s;
    }
}
