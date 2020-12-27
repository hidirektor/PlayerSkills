// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.managers.SkillManager;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.Listener;

public class EntityDamage implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            final Player player = (Player)e.getEntity();
            if (PlayerSkills.instance.getConfig().getBoolean("worlds.restricted") && !PlayerSkills.instance.getConfig().getStringList("worlds.allowed-worlds").contains(player.getLocation().getWorld().getName())) {
                return;
            }
            final SkillManager sm = PlayerSkills.getSkillManager();
            final int skill = sm.getSkillLevel(player, Skill.RESISTANCE) - 1;
            double d = e.getDamage() / 100.0;
            d *= PlayerSkills.getSkillMultipliers().get(Skill.RESISTANCE);
            final double finalDamage = skill * d;
            e.setDamage(e.getDamage() - finalDamage);
        }
    }
}
