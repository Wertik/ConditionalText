package space.devport.wertik.conditionaltext.system.struct.operator.struct.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import space.devport.utils.ConsoleOutput;

@RequiredArgsConstructor
public class OperatorWrapper {

    @Getter
    private final String sign;
    @Getter
    private final ObjectOperator function;

    public boolean apply(Object input, Object required) {
        boolean bool = function.apply(input, required);
        ConsoleOutput.getInstance().debug(input.toString() + " " + sign + " " + required + " -> " + bool);
        return bool;
    }
}