package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.utils.text.StringUtil;

@UtilityClass
public class PlaceholderUtil {

    public String parsePlaceholder(Player player, String placeholder) {
        return StringUtil.stripColor(PlaceholderAPI.setPlaceholders(player, placeholder));
    }

    public Object parsePlaceholderIntoObject(@Nullable Player player, String placeholder) {
        return ParserUtil.parseObject(parsePlaceholder(player, placeholder));
    }
}