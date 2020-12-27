// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.trait;

import net.citizensnpcs.api.util.DataKey;
import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.listeners.InventoryClick;
import me.fatpigsarefat.skills.helper.MessageHelper;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import net.citizensnpcs.api.trait.Trait;

public class SkillsTrait extends Trait
{
    public SkillsTrait() {
        super("playerskills");
    }
    
    @EventHandler
    public void click(final NPCRightClickEvent event) {
        final MessageHelper messageHelper = new MessageHelper();
        if (event.getClicker().hasPermission("playerskills.npc-use")) {
            InventoryClick.reconstructInventory(event.getClicker(), true);
        }
        else {
            event.getClicker().sendMessage(messageHelper.getMessage("no_permissions_message", new String[0]));
        }
    }
    
    public void load(final DataKey key) {
    }
    
    public void save(final DataKey key) {
    }
    
    public void onAttach() {
    }
    
    public void onDespawn() {
    }
    
    public void onSpawn() {
    }
    
    public void onRemove() {
    }
}
