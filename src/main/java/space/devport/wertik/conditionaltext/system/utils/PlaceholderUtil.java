package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.util.StringUtil;

@Log
@UtilityClass
public class PlaceholderUtil {

    // Parse PlaceholderAPI placeholders.
    @NotNull
    public String parsePlaceholders(@Nullable OfflinePlayer player, @NotNull String placeholder) {
        return StringUtil.stripColor(PlaceholderAPI.setPlaceholders(player, placeholder)); // Strip colors from the result
    }

    // Parse PlaceholderAPI placeholders and then into a comparable object.
    @NotNull
    public Object parsePlaceholderIntoObject(@Nullable OfflinePlayer player, @NotNull String placeholder) {
        return ParseUtil.parseObject(parsePlaceholders(player, placeholder));
    }
}