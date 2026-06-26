package software.kayera.kayeraCompass.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private static FileConfiguration config;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin) {
        ConfigManager.plugin = plugin;
        ConfigManager.config = plugin.getConfig();
        plugin.saveDefaultConfig();
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }
}