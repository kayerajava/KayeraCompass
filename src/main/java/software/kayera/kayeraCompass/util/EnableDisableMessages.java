package software.kayera.kayeraCompass.util;

import org.bukkit.Bukkit;
import software.kayera.kayeraCompass.KayeraCompass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnableDisableMessages {

    private static final Pattern HEX = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static void enable(long cfg, long lang, long totalMs) {
        String v = getVersion();
        send(h("&f"));
        send(h("&9│   &bkCompass  &8│  &7v" + v + "  &8│  &7by &3Kayera"));
        send(h("&9│"));
        send(h("&9│  " + slot(cfg)  + " &#AAAAAA Config Manager    &bloaded"));
        send(h("&9│  " + slot(lang) + " &#AAAAAA Language Manager  &bloaded"));
        send(h("&9│"));
        send(h("&9│  &7Plugin enabled in &f" + totalMs + "ms &8│ &3Ready!"));
        send(h("&f"));
    }

    public static void disable() {
        send(h("&f"));
        send(h("&9│  &bkCompass &8│ &fPlugin disabling..."));
        send(h("&9│  &7Saving data & cleaning up..."));
        send(h("&f"));
    }

    private static String slot(long ms) {
        return h("&#474747[&#FFFFFF" + ms + "ms&#474747]");
    }

    private static void send(String msg) {
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    private static String h(String raw) {
        return hex(raw.replace("&", "§").replace("§§", "&"));
    }

    private static String hex(String msg) {
        Matcher m = HEX.matcher(msg);
        StringBuilder sb = new StringBuilder();
        while (m.find()) {
            StringBuilder code = new StringBuilder("§x");
            for (char c : m.group(1).toCharArray()) code.append('§').append(c);
            m.appendReplacement(sb, code.toString());
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static String getVersion() {
        try {
            return KayeraCompass.getInstance().getDescription().getVersion();
        } catch (Exception e) {
            return "?";
        }
    }
}