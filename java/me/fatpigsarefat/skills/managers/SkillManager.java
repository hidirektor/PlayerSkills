// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.managers;

import me.fatpigsarefat.skills.utils.Sound_1_9;
import me.fatpigsarefat.skills.utils.Sound_1_7;
import org.bukkit.OfflinePlayer;
import me.fatpigsarefat.skills.utils.Skill;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.PlayerSkills;

public class SkillManager
{
    private FileManager.Config data;
    private FileManager.Config config;
    
    public SkillManager() {
        this.data = PlayerSkills.getFileManager().getConfig("data");
        this.config = PlayerSkills.getFileManager().getConfig("config");
    }
    
    public int getSkillLevel(final Player player, final Skill skill) {
        if (this.data.get().contains(player.getUniqueId() + "." + skill.toString())) {
            return this.data.get().getInt(player.getUniqueId() + "." + skill.toString());
        }
        return 1;
    }
    
    public int getSkillPoints(final Player player) {
        if (this.data.get().contains(player.getUniqueId() + ".points")) {
            return this.data.get().getInt(player.getUniqueId() + ".points");
        }
        return 0;
    }
    
    public int getTotalPointsSpent(final Player player) {
        final int points = this.getSkillPoints(player);
        final int damage = this.getSkillLevel(player, Skill.STRENGTH) - 1;
        final int criticals = this.getSkillLevel(player, Skill.CRITICALS) - 1;
        final int resistance = this.getSkillLevel(player, Skill.RESISTANCE) - 1;
        final int archery = this.getSkillLevel(player, Skill.ARCHERY) - 1;
        final int health = this.getSkillLevel(player, Skill.HEALTH) - 1;
        final int total = points + damage + criticals + resistance + archery + health;
        return total;
    }
    
    public void setSkillPoints(final Player player, final int points) {
        this.data.set(player.getUniqueId() + ".points", points);
        this.data.save();
    }
    
    public void setSkillLevel(final Player player, final Skill skill, final int level) {
        this.data.set(player.getUniqueId() + "." + skill.toString(), level);
        this.data.save();
    }
    
    public int getSkillLevel(final OfflinePlayer player, final Skill skill) {
        if (!player.hasPlayedBefore()) {
            return 1;
        }
        if (this.data.get().contains(player.getUniqueId() + "." + skill.toString())) {
            return this.data.get().getInt(player.getUniqueId() + "." + skill.toString());
        }
        return 1;
    }
    
    public int getSkillPoints(final OfflinePlayer player) {
        if (!player.hasPlayedBefore()) {
            return 0;
        }
        if (this.data.get().contains(player.getUniqueId() + ".points")) {
            return this.data.get().getInt(player.getUniqueId() + ".points");
        }
        return 0;
    }
    
    public int getPointPrice(final Player player) {
        int xpPrice = 1;
        xpPrice = this.config.get().getInt("xp.price");
        if (this.config.get().getBoolean("xp.add-total-to-price")) {
            xpPrice += this.getTotalPointsSpent(player) * this.config.get().getInt("xp.add-total-to-price-multiplier");
        }
        return xpPrice;
    }
    
    public void resetAll(final Player player) {
        this.setSkillPoints(player, 0);
        this.setSkillLevel(player, Skill.HEALTH, 1);
        this.setSkillLevel(player, Skill.ARCHERY, 1);
        this.setSkillLevel(player, Skill.RESISTANCE, 1);
        this.setSkillLevel(player, Skill.CRITICALS, 1);
        this.setSkillLevel(player, Skill.STRENGTH, 1);
    }
    
    public void buySkillPoint(final Player player, final boolean serverVersion) {
        player.setLevel(player.getLevel() - this.getPointPrice(player));
        this.setSkillPoints(player, this.getSkillPoints(player) + 1);
        if (serverVersion) {
            player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.LEVEL_UP), 100.0f, 100.0f);
        }
        else {
            player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.ENTITY_PLAYER_LEVELUP), 100.0f, 100.0f);
        }
    }
    
    public int getTotalPointsSpent(final OfflinePlayer player) {
        if (!player.hasPlayedBefore()) {
            return 0;
        }
        final int points = this.getSkillPoints(player);
        final int damage = this.getSkillLevel(player, Skill.STRENGTH) - 1;
        final int criticals = this.getSkillLevel(player, Skill.CRITICALS) - 1;
        final int resistance = this.getSkillLevel(player, Skill.RESISTANCE) - 1;
        final int archery = this.getSkillLevel(player, Skill.ARCHERY) - 1;
        final int health = this.getSkillLevel(player, Skill.HEALTH) - 1;
        final int total = points + damage + criticals + resistance + archery + health;
        return total;
    }
    
    public void setSkillPoints(final OfflinePlayer player, final int points) {
        this.data.set(player.getUniqueId() + ".points", points);
        this.data.save();
    }
    
    public void setSkillLevel(final OfflinePlayer player, final Skill skill, final int level) {
        this.data.set(player.getUniqueId() + "." + skill.toString(), level);
        this.data.save();
    }
    
    public int getMaximumLevel(final Skill skill) {
        return PlayerSkills.instance.getConfig().getInt("skills." + skill.toString().toLowerCase() + ".max-level");
    }
}
