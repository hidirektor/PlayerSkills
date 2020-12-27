// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.helper;

import java.util.List;
import me.fatpigsarefat.skills.PlayerSkills;
import me.fatpigsarefat.skills.managers.FileManager;

public class MessageHelper
{
    private FileManager.Config config;
    
    public MessageHelper() {
        this.config = PlayerSkills.getFileManager().getConfig("messages");
    }
    
    private String getPrefix() {
        return this.config.get().getString("prefix") + " ";
    }
    
    public String getMessage(final String key, final String[] args) {
        String message = this.getPrefix() + this.config.get().getString(key);
        message = message.replace("&", "§");
        if (args == null) {
            return message;
        }
        for (int i = 0; i < args.length; ++i) {
            message = message.replace("{" + i + "}", args[i]);
        }
        return message;
    }
    
    public List<String> getMessageList(final String key) {
        return (List<String>)this.config.get().getStringList(key);
    }
}
