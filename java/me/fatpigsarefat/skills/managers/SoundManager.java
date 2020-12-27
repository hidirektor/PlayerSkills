// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.managers;

import me.fatpigsarefat.skills.utils.Sound_1_9;
import org.bukkit.Sound;
import me.fatpigsarefat.skills.utils.Sound_1_7;

public class SoundManager
{
    public static Sound getSound(final Sound_1_7 sound1) {
        Sound sound2 = null;
        if (sound1 != null) {
            for (final Sound sounds : Sound.values()) {
                if (sounds.name() == sound1.name()) {
                    sound2 = sounds;
                }
            }
        }
        return sound2;
    }
    
    public static Sound getSound(final Sound_1_9 sound1) {
        Sound sound2 = null;
        if (sound1 != null) {
            for (final Sound sounds : Sound.values()) {
                if (sounds.name() == sound1.name()) {
                    sound2 = sounds;
                }
            }
        }
        return sound2;
    }
    
    public static Sound getSound(final Sound_1_7 sound1, final Sound_1_9 sound2) {
        Sound sound3 = null;
        if (sound1 != null && sound2 != null) {
            for (final Sound sounds : Sound.values()) {
                if (sounds.name() == sound1.name()) {
                    sound3 = sounds;
                }
                else if (sounds.name() == sound2.name()) {
                    sound3 = sounds;
                }
            }
        }
        return sound3;
    }
}
