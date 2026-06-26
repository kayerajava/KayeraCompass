package software.kayera.kayeraCompass;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitLamp;
import software.kayera.kayeraCompass.command.ReloadCommand;
import software.kayera.kayeraCompass.command.TrackCommand;
import software.kayera.kayeraCompass.listener.CompassListener;
import software.kayera.kayeraCompass.manager.ConfigManager;
import software.kayera.kayeraCompass.manager.LanguageManager;
import software.kayera.kayeraCompass.util.EnableDisableMessages;
import software.kayera.kayeraCompass.util.UpdateCheck;

public final class KayeraCompass extends JavaPlugin {
    private static KayeraCompass instance;

    @Override
    public void onEnable() {
        instance = this;

        long totalStart = System.currentTimeMillis();

        long t = System.currentTimeMillis();
        ConfigManager.init(this);
        long cfgMs = System.currentTimeMillis() - t;

        t = System.currentTimeMillis();
        LanguageManager.init(this, ConfigManager.getConfig());
        long langMs = System.currentTimeMillis() - t;

        var lamp = BukkitLamp.builder(this).build();
        lamp.register(new TrackCommand(getConfig()));
        lamp.register(new ReloadCommand(getConfig()));
        getServer().getPluginManager().registerEvents(new CompassListener(this), this);
        UpdateCheck.checkForUpdate();

        long totalMs = System.currentTimeMillis() - totalStart;
        EnableDisableMessages.enable(cfgMs, langMs, totalMs);
    }

    @Override
    public void onDisable() {
        EnableDisableMessages.disable();
    }

    public static KayeraCompass getInstance(){
        return instance;
    }
}