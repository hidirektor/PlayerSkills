// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.Listener;

public class PlayerDeath implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        if (PlayerSkills.potionEffect.containsKey(p)) {
            PlayerSkills.potionEffect.remove(p);
        }
    }
}
