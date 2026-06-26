package software.kayera.kayeraCompass.listener;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.plugin.java.JavaPlugin;
import net.md_5.bungee.api.ChatColor;
import software.kayera.kayeraCompass.manager.LanguageManager;

public class CompassListener implements Listener {

    private final JavaPlugin plugin;

    public CompassListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.COMPASS && item.hasItemMeta()) {
            CompassMeta compassMeta = (CompassMeta) item.getItemMeta();
            String compassName = ChatColor.stripColor(compassMeta.getDisplayName());

            Player player = event.getPlayer();
            Player target = Bukkit.getPlayerExact(compassName);

            if (target != null && target.isOnline()) {
                compassMeta.setLodestone(target.getLocation());
                compassMeta.setLodestoneTracked(false);
                item.setItemMeta(compassMeta);

                double distance = player.getLocation().distance(target.getLocation());
                String distanceMessage = LanguageManager.getMessage("distance_message")
                        .replace("{distance}", String.format("%.2f", distance))
                        .replace("{target}", target.getName());
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(distanceMessage));
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.5F);
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("player_not_found")));
            }
        }
    }
}