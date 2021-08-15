package space.devport.wertik.conditionaltext.system.struct.operator.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class OperatorWrapper {

    @Getter
    private final String sign;
    @Getter
    private final ObjectOperator function;

    public boolean apply(Object input, Object required) {
        return function.apply(input, required);
    }
}