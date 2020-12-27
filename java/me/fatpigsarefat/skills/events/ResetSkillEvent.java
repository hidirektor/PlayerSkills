// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.events;

import org.bukkit.event.HandlerList;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.managers.SkillManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ResetSkillEvent extends Event
{
    private Player player;
    private SkillManager skillManager;
    private Skill skill;
    private boolean serverVesrion;
    private static final HandlerList handlers;
    
    public ResetSkillEvent(final Player player, final SkillManager skillManager, final Skill skill, final boolean serverVesrion) {
        this.player = player;
        this.skillManager = skillManager;
        this.skill = skill;
        this.serverVesrion = serverVesrion;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public SkillManager getSkillManager() {
        return this.skillManager;
    }
    
    public Skill getSkill() {
        return this.skill;
    }
    
    public boolean isServerVesrionOld() {
        return this.serverVesrion;
    }
    
    public HandlerList getHandlers() {
        return ResetSkillEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ResetSkillEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
