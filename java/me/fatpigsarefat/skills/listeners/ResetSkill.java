// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.managers.SkillManager;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.utils.Sound_1_9;
import me.fatpigsarefat.skills.managers.SoundManager;
import me.fatpigsarefat.skills.utils.Sound_1_7;
import me.fatpigsarefat.skills.PlayerSkills;
import me.fatpigsarefat.skills.helper.MessageHelper;
import me.fatpigsarefat.skills.events.ResetSkillEvent;
import org.bukkit.event.Listener;

public class ResetSkill implements Listener
{
    @EventHandler
    public void onSkillReset(final ResetSkillEvent e) {
        final MessageHelper messageHelper = new MessageHelper();
        final Player player = e.getPlayer();
        final SkillManager sm = e.getSkillManager();
        final Skill skill = e.getSkill();
        if (!PlayerSkills.allowReset) {
            return;
        }
        sm.setSkillPoints(player, sm.getSkillPoints(player) + sm.getSkillLevel(player, skill) - 1);
        sm.setSkillLevel(player, skill, 1);
        InventoryClick.reconstructInventory(player, false);
        player.sendMessage(messageHelper.getMessage("skill_reset", new String[] { skill.name().toLowerCase() }));
        if (e.isServerVesrionOld()) {
            player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.ORB_PICKUP), 100.0f, 100.0f);
        }
        else {
            player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.ENTITY_EXPERIENCE_ORB_PICKUP), 100.0f, 100.0f);
        }
    }
}
