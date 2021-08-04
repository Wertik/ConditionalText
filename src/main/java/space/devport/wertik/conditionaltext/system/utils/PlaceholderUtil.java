package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;
import space.devport.dock.util.StringUtil;

@UtilityClass
public class PlaceholderUtil {

    public String parsePlaceholders(@Nullable OfflinePlayer player, String placeholder) {
        return StringUtil.stripColor(PlaceholderAPI.setPlaceholders(player, placeholder));
    }

    public Object parsePlaceholderIntoObject(@Nullable OfflinePlayer player, String placeholder) {
        return ParserUtil.parseObject(parsePlaceholders(player, placeholder));
    }
}