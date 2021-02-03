package space.devport.wertik.conditionaltext.system.struct.operator.struct.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import space.devport.utils.logging.DebugLevel;

@Log
@RequiredArgsConstructor
public class OperatorWrapper {

    @Getter
    private final String sign;
    @Getter
    private final ObjectOperator function;

    public boolean apply(Object input, Object required) {
        boolean bool = function.apply(input, required);
        log.log(DebugLevel.DEBUG, String.format("'%s' (%s) %s '%s' (%s) -> %b",
                required, required.getClass().getSimpleName(), sign, input, input.getClass().getSimpleName(), bool));
        return bool;
    }
}