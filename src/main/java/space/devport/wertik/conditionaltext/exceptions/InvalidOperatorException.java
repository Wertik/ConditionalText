package space.devport.wertik.conditionaltext.exceptions;

public class InvalidOperatorException extends Exception {

    public InvalidOperatorException() {
        super("Invalid operator");
    }

    public InvalidOperatorException(String sign) {
        super("Invalid operator sign: " + sign);
    }
}