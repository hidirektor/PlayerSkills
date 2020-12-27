// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.listeners;

import me.fatpigsarefat.skills.events.ResetSkillEvent;
import org.bukkit.event.Event;
import me.fatpigsarefat.skills.events.UpgradeSkillEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.Bukkit;
import me.fatpigsarefat.skills.managers.ConfigurationManager;
import org.bukkit.event.EventHandler;
import me.fatpigsarefat.skills.managers.SkillManager;
import me.fatpigsarefat.skills.managers.FileManager;
import me.fatpigsarefat.skills.utils.Skill;
import me.fatpigsarefat.skills.utils.Sound_1_9;
import me.fatpigsarefat.skills.managers.SoundManager;
import me.fatpigsarefat.skills.utils.Sound_1_7;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import me.fatpigsarefat.skills.PlayerSkills;
import org.bukkit.event.inventory.InventoryClickEvent;
import me.fatpigsarefat.skills.helper.MessageHelper;
import org.bukkit.event.Listener;

public class InventoryClick implements Listener
{
    private MessageHelper messageHelper;
    
    public InventoryClick() {
        this.messageHelper = new MessageHelper();
    }
    
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        final FileManager.Config gui = PlayerSkills.getFileManager().getConfig("gui");
        final FileManager.Config config = PlayerSkills.getFileManager().getConfig("config");
        if (e.getClickedInventory() != null && e.getClickedInventory().getName().equals(ChatColor.translateAlternateColorCodes('&', gui.get().getString("gui.title")))) {
            e.setCancelled(true);
            final Player player = (Player)e.getWhoClicked();
            final SkillManager sm = PlayerSkills.getSkillManager();
            final boolean serverVersion = PlayerSkills.instance.serverVersionOnePointSeven;
            InventoryAction a;
            if (gui.get().getBoolean("gui.display.points-purchase.right-click")) {
                a = InventoryAction.PICKUP_HALF;
            }
            else {
                a = InventoryAction.PICKUP_ALL;
            }
            if (e.getSlot() == gui.get().getInt("gui.display.points-purchase.slot") && e.getAction().equals((Object)a)) {
                if (player.getLevel() >= sm.getPointPrice(player)) {
                    if (config.get().getBoolean("points.restriction")) {
                        if (sm.getTotalPointsSpent(player) != config.get().getInt("points.restriction-per")) {
                            sm.buySkillPoint(player, serverVersion);
                            reconstructInventory(player, false);
                        }
                        else {
                            player.sendMessage(this.messageHelper.getMessage("points_limit", new String[] { config.get().getString("points.restriction-per") }));
                        }
                    }
                    else {
                        sm.buySkillPoint(player, serverVersion);
                        reconstructInventory(player, false);
                    }
                }
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-all.slot")) {
                if (!PlayerSkills.allowReset) {
                    return;
                }
                sm.resetAll(player);
                reconstructInventory(player, false);
                if (serverVersion) {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_7.EXPLODE), 100.0f, 100.0f);
                }
                else {
                    player.playSound(player.getLocation(), SoundManager.getSound(Sound_1_9.ENTITY_GENERIC_EXPLODE), 100.0f, 100.0f);
                }
                player.sendMessage(this.messageHelper.getMessage("skill_full_reset", new String[0]));
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.strength-normal.slot")) {
                this.updateSkill(sm, serverVersion, player, Skill.STRENGTH);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.criticals-normal.slot")) {
                this.updateSkill(sm, serverVersion, player, Skill.CRITICALS);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.resistance-normal.slot")) {
                this.updateSkill(sm, serverVersion, player, Skill.RESISTANCE);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.archery-normal.slot")) {
                this.updateSkill(sm, serverVersion, player, Skill.ARCHERY);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.health-normal.slot")) {
                this.updateSkill(sm, serverVersion, player, Skill.HEALTH);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-strength.slot")) {
                this.resetSkill(sm, serverVersion, player, Skill.STRENGTH);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-criticals.slot")) {
                this.resetSkill(sm, serverVersion, player, Skill.CRITICALS);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-resistance.slot")) {
                this.resetSkill(sm, serverVersion, player, Skill.RESISTANCE);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-archery.slot")) {
                this.resetSkill(sm, serverVersion, player, Skill.ARCHERY);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.reset-health.slot")) {
                this.resetSkill(sm, serverVersion, player, Skill.HEALTH);
            }
            else if (e.getSlot() == gui.get().getInt("gui.display.close.slot")) {
                //String cmd = "dm open Infgenelmenu %player%".replace("%player%", e.getWhoClicked().getName());
                String cmd = gui.get().getString("gui.display.close.command").replace("%player%", e.getWhoClicked().getName());
                Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), cmd);
            }
        }
    }
    
    public static void reconstructInventory(final Player player, final boolean completeUpdate) {
        final FileManager.Config gui = PlayerSkills.getFileManager().getConfig("gui");
        final SkillManager sm = PlayerSkills.getSkillManager();
        final ConfigurationManager cm = new ConfigurationManager();
        final Inventory inv = Bukkit.createInventory((InventoryHolder)null, gui.get().getInt("gui.size") * 9, ChatColor.translateAlternateColorCodes('&', gui.get().getString("gui.title")));
        int strength = 1;
        int criticals = 1;
        int resistance = 1;
        int archery = 1;
        int health = 1;
        int close = 1;
        strength = sm.getSkillLevel(player, Skill.STRENGTH);
        criticals = sm.getSkillLevel(player, Skill.CRITICALS);
        resistance = sm.getSkillLevel(player, Skill.RESISTANCE);
        archery = sm.getSkillLevel(player, Skill.ARCHERY);
        health = sm.getSkillLevel(player, Skill.HEALTH);
        final ItemStack skillpointsIs = new ItemStack(cm.getItemStack("points-purchase", player));
        if (sm.getSkillPoints(player) > 0) {
            skillpointsIs.setAmount(sm.getSkillPoints(player));
        }
        else {
            skillpointsIs.setAmount(1);
        }
        final ItemStack playerstatsIs = cm.getItemStack("stats", player);
        playerstatsIs.setAmount(1);
        final ItemStack strengthIs = cm.getItemStack("strength-normal", player);
        strengthIs.setAmount(strength);
        final ItemStack criticalsIs = cm.getItemStack("criticals-normal", player);
        criticalsIs.setAmount(criticals);
        final ItemStack resistanceIs = cm.getItemStack("resistance-normal", player);
        resistanceIs.setAmount(resistance);
        final ItemStack archeryIs = cm.getItemStack("archery-normal", player);
        archeryIs.setAmount(archery);
        final ItemStack healthIs = cm.getItemStack("health-normal", player);
        healthIs.setAmount(health);
        final ItemStack rs = cm.getItemStack("reset-strength", player);
        rs.setAmount(1);
        final ItemStack clse = cm.getItemStack("close", player);
        clse.setAmount(1);
        final ItemStack rc = cm.getItemStack("reset-criticals", player);
        rc.setAmount(1);
        final ItemStack rr = cm.getItemStack("reset-resistance", player);
        rr.setAmount(1);
        final ItemStack ra = cm.getItemStack("reset-archery", player);
        ra.setAmount(1);
        final ItemStack rh = cm.getItemStack("reset-health", player);
        rh.setAmount(1);
        final ItemStack barrier2Is = cm.getItemStack("reset-all", player);
        barrier2Is.setAmount(1);
        inv.setItem(gui.get().getInt("gui.display.close.slot"), clse);
        inv.setItem(gui.get().getInt("gui.display.points-purchase.slot"), skillpointsIs);
        inv.setItem(gui.get().getInt("gui.display.stats.slot"), playerstatsIs);
        inv.setItem(gui.get().getInt("gui.display.strength-normal.slot"), strengthIs);
        inv.setItem(gui.get().getInt("gui.display.criticals-normal.slot"), criticalsIs);
        inv.setItem(gui.get().getInt("gui.display.resistance-normal.slot"), resistanceIs);
        inv.setItem(gui.get().getInt("gui.display.archery-normal.slot"), archeryIs);
        inv.setItem(gui.get().getInt("gui.display.health-normal.slot"), healthIs);
        if (PlayerSkills.allowReset) {
            inv.setItem(gui.get().getInt("gui.display.reset-strength.slot"), rs);
            inv.setItem(gui.get().getInt("gui.display.reset-criticals.slot"), rc);
            inv.setItem(gui.get().getInt("gui.display.reset-resistance.slot"), rr);
            inv.setItem(gui.get().getInt("gui.display.reset-archery.slot"), ra);
            inv.setItem(gui.get().getInt("gui.display.reset-health.slot"), rh);
            inv.setItem(gui.get().getInt("gui.display.reset-all.slot", 5), barrier2Is);
        }
        if (!completeUpdate) {
            player.getOpenInventory().getTopInventory().setContents(inv.getContents());
        }
        else {
            player.closeInventory();
            player.openInventory(inv);
        }
        player.updateInventory();
    }
    
    public void updateSkill(final SkillManager sm, final boolean serverVersion, final Player player, final Skill skill) {
        Bukkit.getPluginManager().callEvent((Event)new UpgradeSkillEvent(player, sm, skill, serverVersion));
    }
    
    public void resetSkill(final SkillManager sm, final boolean serverVersion, final Player player, final Skill skill) {
        Bukkit.getPluginManager().callEvent((Event)new ResetSkillEvent(player, sm, skill, serverVersion));
    }
}
