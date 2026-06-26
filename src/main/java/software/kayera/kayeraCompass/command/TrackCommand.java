package software.kayera.kayeraCompass.command;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import net.md_5.bungee.api.ChatColor;
import software.kayera.kayeraCompass.manager.LanguageManager;

import static software.kayera.kayeraCompass.util.TrackFunction.trackPlayer;

public class TrackCommand {

    private final FileConfiguration config;

    public TrackCommand(FileConfiguration config) {
        this.config = config;
    }

    @Command({"kc track <target>", "kayeracompass track <target>", "kcompass track <target>", "kc tr <target>", "kcompass tr <target>", "kayeracompass tr <target>", "cmp track <target>", "cmp tr <target>"})
    public void trackerSingle(BukkitCommandActor actor, @Named("target") Player target) {
        CommandSender commandSender = actor.asPlayer();

        if (commandSender instanceof Player) {
            Player sender = (Player) commandSender;

            if (config.getBoolean("track-command-permission", false) && !sender.hasPermission("kcompass.track")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("no_permission")));
                return;
            }

            trackPlayer(sender, target);
            sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("only_players")));
        }
    }

    @Command({"kc track <target> <sender>", "kcompass track <target> <sender>", "kayeracompass track <target> <sender>", "kc tr <target> <sender>", "kcompass tr <target> <sender>", "kayeracompass tr <target> <sender>", "cmp track <target> <sender>", "cmp tr <target> <sender>"})
    public void trackerDouble(BukkitCommandActor actor, @Named("target") Player target, @Named("sender") Player sender) {
        CommandSender commandSender = actor.asPlayer();

        if (commandSender instanceof Player) {
            Player senderPlayer = (Player) commandSender;

            if (config.getBoolean("track-command-permission", false) && !senderPlayer.hasPermission("kcompass.track")) {
                senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("no_permission")));
                return;
            }

            if (!senderPlayer.hasPermission("kcompass.track.other")) {
                senderPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("no_permission")));
                return;
            }

            trackPlayer(sender, target);
            sender.playSound(sender.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
        } else {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("only_players")));
        }
    }
}