package space.devport.wertik.conditionaltext.system.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.java.Log;
import org.jetbrains.annotations.NotNull;
import space.devport.wertik.conditionaltext.ConditionalTextPlugin;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Log
@UtilityClass
public class ParserUtil {

    @NotNull
    public Object parseObject(String input) {

        Object out = null;

        try {
            out = Integer.parseInt(input);
        } catch (NumberFormatException ignored) {
            // Not an integer
        }

        if (out == null)
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException ignored) {
                // Not a double
            }

        if (out == null)
            try {
                return LocalTime.parse(input, ConditionalTextPlugin.getInstance().getTimeFormatter());
            } catch (DateTimeParseException ignored) {
                // Not a date
            }

        if (out == null)
            out = input;

        log.fine(String.format("Parsed: '%s' -> (%s)", input, out.getClass().getSimpleName()));
        return out;
    }
}