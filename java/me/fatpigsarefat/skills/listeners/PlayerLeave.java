// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import org.bukkit.event.EventPriority;
import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.Listener;

public class PlayerLeave implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerLeave(final PlayerQuitEvent e) {
        if (PlayerSkills.potionEffect.containsKey(e.getPlayer())) {
            PlayerSkills.potionEffect.remove(e.getPlayer());
        }
    }
}
