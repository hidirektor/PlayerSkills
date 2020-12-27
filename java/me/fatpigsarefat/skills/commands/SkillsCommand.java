// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.commands;

import me.fatpigsarefat.skills.listeners.InventoryClick;
import org.bukkit.ChatColor;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.entity.Player;
import me.fatpigsarefat.skills.helper.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class SkillsCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final MessageHelper messageHelper = new MessageHelper();
        if (!cmd.getName().equalsIgnoreCase("skills") || !(sender instanceof Player)) {
            return false;
        }
        final Player player = (Player)sender;
        if (PlayerSkills.instance.getConfig().getBoolean("worlds.restricted") && !PlayerSkills.instance.getConfig().getStringList("worlds.allowed-worlds").contains(player.getLocation().getWorld().getName())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageHelper.getMessage("deny_message", new String[0])));
            return true;
        }
        InventoryClick.reconstructInventory(player, true);
        return true;
    }
}
