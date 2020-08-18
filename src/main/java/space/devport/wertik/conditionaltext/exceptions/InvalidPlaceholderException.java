package space.devport.wertik.conditionaltext.exceptions;

import com.google.common.base.Strings;

public class InvalidPlaceholderException extends Exception {

    public InvalidPlaceholderException(String placeholder) {
        super("Invalid placeholder: " + (Strings.isNullOrEmpty(placeholder) ? "null" : placeholder));
    }

    public InvalidPlaceholderException(String placeholder, String message) {
        super("Invalid placeholder: " + (Strings.isNullOrEmpty(placeholder) ? "null" : placeholder) + ", reason: " + message);
    }
}