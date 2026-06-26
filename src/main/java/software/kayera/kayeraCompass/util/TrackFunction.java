package software.kayera.kayeraCompass.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import net.md_5.bungee.api.ChatColor;
import software.kayera.kayeraCompass.manager.LanguageManager;

public class TrackFunction {

    public static void trackPlayer(Player player1, Player player2) {
        ItemStack compass = new ItemStack(Material.COMPASS);
        CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
        compassMeta.setLodestone(player2.getLocation());
        compassMeta.setLodestoneTracked(false);

        String compassName = ChatColor.translateAlternateColorCodes('&', "&c" + player2.getName());
        compassMeta.setDisplayName(compassName);
        compass.setItemMeta(compassMeta);

        player1.getInventory().addItem(compass);
        player1.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageManager.getMessage("compass_set").replace("{player}", player2.getName())));
    }
}