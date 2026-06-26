package software.kayera.kayeraCompass.manager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LanguageManager {

    private static FileConfiguration langConfig;
    private static String locale;
    private static JavaPlugin plugin;

    public static void init(JavaPlugin plugin, FileConfiguration config) {
        LanguageManager.plugin = plugin;
        locale = config.getString("locale", "en");
        File langFile = new File(plugin.getDataFolder(), "lang_" + locale + ".yml");

        if (!langFile.exists()) {
            InputStream defaultLangFile = LanguageManager.class.getResourceAsStream("/lang/lang_" + locale + ".yml");
            if (defaultLangFile != null) {
                try {
                    Files.copy(defaultLangFile, langFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    plugin.getLogger().severe("Failed to copy default language file: " + e.getMessage());
                }
            } else {
                plugin.getLogger().warning("Default language file not found: lang_" + locale + ".yml");
            }
        }

        langConfig = YamlConfiguration.loadConfiguration(langFile);
    }

    public static String getMessage(String key) {
        String message = langConfig.getString(key, "Message not found: " + key);
        return translateHexColors(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static String translateHexColors(String message) {
        Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            StringBuilder replacement = new StringBuilder("§x");
            for (char c : hex.toCharArray()) {
                replacement.append('§').append(c);
            }
            matcher.appendReplacement(buffer, replacement.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static void reload() {
        File langFile = new File(plugin.getDataFolder(), "lang.yml");

        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }

        langConfig = plugin.getConfig();

        try {
            langConfig.load(langFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not reload lang.yml", e);
        }
    }

}