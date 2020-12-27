// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.managers.SkillManager;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.managers.FileManager;
import me.fatpigsarefat.skills.utils.Sound_1_9;
import me.fatpigsarefat.skills.managers.SoundManager;
import me.fatpigsarefat.skills.utils.Sound_1_7;
import me.fatpigsarefat.skills.helper.MessageHelper;
import me.fatpigsarefat.skills.PlayerSkills;
import me.fatpigsarefat.skills.events.UpgradeSkillEvent;
import org.bukkit.event.Listener;

public class UpgradeSkill implements Listener
{
    @EventHandler
    public void onUpgradeSkill(final UpgradeSkillEvent e) {
        final FileManager.Config config = PlayerSkills.getFileManager().getConfig("config");
        final Player player = e.getPlayer();
        final SkillManager sm = e.getSkillManager();
        final Skill skill = e.getSkill();
        final MessageHelper messageHelper = new MessageHelper();
        if (config.get().getBoolean("skills.permissions-use")) {
            if (player.hasPermission("playerskills." + skill.name().toLowerCase())) {
                if (sm.getSkillPoints(player) <= 0) {
                    if (e.isServerVesrionOld()) {
                        player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.ANVIL_LAND), 100.0f, 100.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.BLOCK_ANVIL_LAND), 100.0f, 100.0f);
                    }
                    player.sendMessage(messageHelper.getMessage("skill_upgrade_false", new String[0]));
                    return;
                }
                if (sm.getSkillLevel(player, skill) >= sm.getMaximumLevel(skill)) {
                    if (e.isServerVesrionOld()) {
                        player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.ANVIL_LAND), 100.0f, 100.0f);
                    }
                    else {
                        player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.BLOCK_ANVIL_LAND), 100.0f, 100.0f);
                    }
                    player.sendMessage(messageHelper.getMessage("skill_upgrade_false", new String[0]));
                    return;
                }
                sm.setSkillPoints(player, sm.getSkillPoints(player) - 1);
                sm.setSkillLevel(player, skill, sm.getSkillLevel(player, skill) + 1);
                InventoryClick.reconstructInventory(player, false);
                if (e.isServerVesrionOld()) {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.LEVEL_UP), 100.0f, 100.0f);
                }
                else {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.ENTITY_PLAYER_LEVELUP), 100.0f, 100.0f);
                }
                player.sendMessage(messageHelper.getMessage("skill_upgrade", new String[] { skill.name().toLowerCase() }));
            }
            else {
                player.sendMessage(messageHelper.getMessage("no_permissions_message", new String[0]));
            }
        }
        else {
            if (sm.getSkillPoints(player) <= 0) {
                if (e.isServerVesrionOld()) {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.ANVIL_LAND), 100.0f, 100.0f);
                }
                else {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.BLOCK_ANVIL_LAND), 100.0f, 100.0f);
                }
                player.sendMessage(messageHelper.getMessage("skill_upgrade_false", new String[0]));
                return;
            }
            if (sm.getSkillLevel(player, skill) >= sm.getMaximumLevel(skill)) {
                if (e.isServerVesrionOld()) {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.ANVIL_LAND), 100.0f, 100.0f);
                }
                else {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.BLOCK_ANVIL_LAND), 100.0f, 100.0f);
                }
                player.sendMessage(messageHelper.getMessage("skill_upgrade_false", new String[0]));
                return;
            }
            sm.setSkillPoints(player, sm.getSkillPoints(player) - 1);
            sm.setSkillLevel(player, skill, sm.getSkillLevel(player, skill) + 1);
            InventoryClick.reconstructInventory(player, false);
            if (e.isServerVesrionOld()) {
                player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.LEVEL_UP), 100.0f, 100.0f);
            }
            else {
                player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.ENTITY_PLAYER_LEVELUP), 100.0f, 100.0f);
            }
            player.sendMessage(messageHelper.getMessage("skill_upgrade", new String[] { skill.name().toLowerCase() }));
        }
    }
}
