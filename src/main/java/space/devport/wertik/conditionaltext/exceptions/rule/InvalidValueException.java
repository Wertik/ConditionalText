package space.devport.wertik.conditionaltext.exceptions.rule;

public class InvalidValueException extends Exception {
    public InvalidValueException() {
        super("Invalid value");
    }

    public InvalidValueException(String message) {
        super(message);
    }
}
