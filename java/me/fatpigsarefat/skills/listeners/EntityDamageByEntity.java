// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.managers.SkillManager;
import org.bukkit.entity.Arrow;
import org.bukkit.Material;
import org.bukkit.potion.PotionEffectType;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.Listener;

public class EntityDamageByEntity implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityByEntityDamage(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            final Player player = (Player)e.getDamager();
            if (PlayerSkills.instance.getConfig().getBoolean("worlds.restricted") && !PlayerSkills.instance.getConfig().getStringList("worlds.allowed-worlds").contains(player.getLocation().getWorld().getName())) {
                return;
            }
            final SkillManager sm = PlayerSkills.getSkillManager();
            final int skill = sm.getSkillLevel(player, Skill.STRENGTH) - 1;
            double d = e.getDamage() / 100.0;
            d *= PlayerSkills.getSkillMultipliers().get(Skill.STRENGTH);
            final double finalDamage = skill * d;
            e.setDamage(e.getDamage() + finalDamage);
            final boolean result = player.getFallDistance() > 0.0f && !player.isOnGround() && !player.hasPotionEffect(PotionEffectType.BLINDNESS) && player.getVehicle() == null && !player.isSprinting() && !player.getLocation().getBlock().isLiquid() && !player.getLocation().add(0.0, 0.0, 1.0).getBlock().getType().equals((Object)Material.LADDER);
            final double dmg = e.getDamage();
            if (result && dmg > 0.0) {
                final int sk = sm.getSkillLevel(player, Skill.CRITICALS) - 1;
                double damage = e.getDamage() / 150.0;
                damage *= PlayerSkills.getSkillMultipliers().get(Skill.CRITICALS);
                final double fdamage = sk * damage;
                e.setDamage(e.getDamage() + fdamage);
            }
        }
        else if (e.getDamager() instanceof Arrow) {
            final Arrow arrow = (Arrow)e.getDamager();
            if (arrow.getShooter() instanceof Player) {
                final Player player2 = (Player)arrow.getShooter();
                if (PlayerSkills.instance.getConfig().getBoolean("worlds.restricted") && !PlayerSkills.instance.getConfig().getStringList("worlds.allowed-worlds").contains(player2.getLocation().getWorld().getName())) {
                    return;
                }
                final SkillManager sm2 = PlayerSkills.getSkillManager();
                final int skill2 = sm2.getSkillLevel(player2, Skill.ARCHERY) - 1;
                double d2 = e.getDamage() / 100.0;
                d2 *= PlayerSkills.getSkillMultipliers().get(Skill.ARCHERY);
                final double finalDamage2 = skill2 * d2;
                e.setDamage(e.getDamage() + finalDamage2);
            }
        }
    }
}
