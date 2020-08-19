package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.text.StringUtil;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;

@UtilityClass
public class PlaceholderUtil {

    public Object parsePlaceholder(@Nullable Player player, String placeholder) {
        String parsedString = StringUtil.stripColor(PlaceholderAPI.setPlaceholders(player, placeholder));

        return parseObject(parsedString);
    }

    public Object parseObject(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Input is not an integer.");
        }

        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Input is not a double.");
        }

        ConditionalTextPlugin.getInstance().getConsoleOutput().debug("Returning as string.");
        return input;
    }
}