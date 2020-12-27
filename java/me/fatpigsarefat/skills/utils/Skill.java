// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.utils;

public enum Skill
{
    STRENGTH, 
    CRITICALS, 
    RESISTANCE, 
    ARCHERY, 
    HEALTH;
    
    public static Skill getSkillByName(final String s) {
        switch (s) {
            case "strength": {
                return Skill.STRENGTH;
            }
            case "criticals": {
                return Skill.CRITICALS;
            }
            case "resistance": {
                return Skill.RESISTANCE;
            }
            case "archery": {
                return Skill.ARCHERY;
            }
            case "health": {
                return Skill.HEALTH;
            }
            default: {
                return null;
            }
        }
    }
}
