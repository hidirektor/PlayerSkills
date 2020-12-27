// 
// Decompiled by Procyon v0.5.36
// 

package me.fatpigsarefat.skills.managers;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import org.bukkit.configuration.Configuration;
import java.io.InputStreamReader;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.HashMap;
import org.bukkit.plugin.java.JavaPlugin;

public class FileManager
{
    private final JavaPlugin plugin;
    private HashMap<String, Config> configs;
    
    public FileManager(final JavaPlugin plugin) {
        this.configs = new HashMap<String, Config>();
        this.plugin = plugin;
    }
    
    public Config getConfig(final String name) {
        if (!this.configs.containsKey(name)) {
            this.configs.put(name, new Config(name));
        }
        return this.configs.get(name);
    }
    
    public Config saveConfig(final String name) {
        return this.getConfig(name).save();
    }
    
    public Config reloadConfig(final String name) {
        return this.getConfig(name).reload();
    }
    
    public class Config
    {
        private String name;
        private File file;
        private YamlConfiguration config;
        
        public Config(final String name) {
            this.name = name + ".yml";
        }
        
        public Config save() {
            if (this.config == null || this.file == null) {
                return this;
            }
            try {
                if (this.config.getConfigurationSection("").getKeys(true).size() != 0) {
                    this.config.save(this.file);
                }
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
            return this;
        }
        
        public YamlConfiguration get() {
            if (this.config == null) {
                this.reload();
            }
            return this.config;
        }
        
        public Config saveDefaultConfig() {
            this.file = new File(FileManager.this.plugin.getDataFolder(), this.name);
            FileManager.this.plugin.saveResource(this.name, false);
            return this;
        }
        
        public Config reload() {
            if (this.file == null) {
                this.file = new File(FileManager.this.plugin.getDataFolder(), this.name);
            }
            this.config = YamlConfiguration.loadConfiguration(this.file);
            try {
                final Reader defConfigStream = new InputStreamReader(FileManager.this.plugin.getResource(this.name), "UTF8");
                if (defConfigStream != null) {
                    final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
                    this.config.setDefaults((Configuration)defConfig);
                }
            }
            catch (UnsupportedEncodingException ex) {}
            catch (NullPointerException ex2) {}
            return this;
        }
        
        public Config copyDefaults(final boolean force) {
            this.get().options().copyDefaults(force);
            return this;
        }
        
        public Config set(final String key, final Object value) {
            this.get().set(key, value);
            return this;
        }
        
        public Object get(final String key) {
            return this.get().get(key);
        }
    }
}
