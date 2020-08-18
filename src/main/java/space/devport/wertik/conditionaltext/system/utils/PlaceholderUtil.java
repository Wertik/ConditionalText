package space.devport.wertik.conditionaltext.system.utils;

import com.google.common.base.Strings;
import lombok.experimental.UtilityClass;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;
import space.devport.wertik.conditionaltext.exceptions.InvalidPlaceholderException;

@UtilityClass
public class PlaceholderUtil {

    public int parsePlaceholder(@Nullable Player player, String placeholder) throws InvalidPlaceholderException {

        if (Strings.isNullOrEmpty(placeholder)) throw new InvalidPlaceholderException(placeholder);

        String valueString = PlaceholderAPI.setPlaceholders(player, placeholder);

        int value;
        try {
            value = Integer.parseInt(valueString);
        } catch (NumberFormatException ignored) {
            throw new InvalidPlaceholderException(placeholder, "bad return type, expected int");
        }

        return value;
    }
}