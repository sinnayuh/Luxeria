package codes.sinister.luxeria.modal.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class TranslateUtil {
    private static final Map<String, String> CUSTOM_COLORS = new HashMap<>();

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
            .character('&')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private static final MiniMessage MINI_MESSAGE = MiniMessage.miniMessage();

    static {
        registerCustomColor("lilac", "#c8a2c8");
    }

    /**
     * Register a new custom color code
     *
     * @param code The code to use (without &)
     * @param hexColor The hex color code (e.g. "#FF0000")
     */
    public static void registerCustomColor(String code, String hexColor) {
        // Convert hex color to Minecraft's format
        String minecraftColor = net.md_5.bungee.api.ChatColor.of(hexColor).toString();
        CUSTOM_COLORS.put("&" + code, minecraftColor);
    }

    /**
     * Translates a string with color codes using & symbol into a Component
     * Supports both legacy color codes, hex colors, and custom colors
     *
     * @param text The text to translate
     * @return A component with the translated colors
     */
    public static Component translate(String text) {
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }

        // First replace custom colors
        String processed = text;
        for (Map.Entry<String, String> entry : CUSTOM_COLORS.entrySet()) {
            processed = processed.replace(entry.getKey(), entry.getValue());
        }

        // Then translate regular color codes
        return LEGACY_SERIALIZER.deserialize(ChatColor.translateAlternateColorCodes('&', processed));
    }

    /**
     * Translates multiple strings and joins them together with newlines
     *
     * @param texts The texts to translate and join
     * @return A component with all texts translated and joined with newlines
     */
    public static Component translate(String... texts) {
        if (texts == null || texts.length == 0) {
            return Component.empty();
        }

        Component result = Component.empty();
        for (int i = 0; i < texts.length; i++) {
            result = result.append(translate(texts[i]));
            // Add newline if not the last element
            if (i < texts.length - 1) {
                result = result.append(Component.newline());
            }
        }
        return result;
    }

    /**
     * Converts a MiniMessage formatted string to a Component
     * Useful when you want to use MiniMessage format instead of legacy color codes
     *
     * @param miniMessage The MiniMessage formatted string
     * @return A component with the parsed MiniMessage format
     */
    public static Component parseMiniMessage(String miniMessage) {
        if (miniMessage == null || miniMessage.isEmpty()) {
            return Component.empty();
        }
        return MINI_MESSAGE.deserialize(miniMessage);
    }

    /**
     * Strips all color codes from a string
     *
     * @param text The text to strip colors from
     * @return The text without color codes
     */
    public static String stripColor(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        return ChatColor.stripColor(text);
    }
}