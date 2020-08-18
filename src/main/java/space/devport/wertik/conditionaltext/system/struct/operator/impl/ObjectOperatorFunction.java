package space.devport.wertik.conditionaltext.system.struct.operator.impl;

import space.devport.wertik.conditionaltext.system.struct.operator.OperatorFunction;

public interface ObjectOperatorFunction extends OperatorFunction<Object, Object, Boolean> {
    @Override
    Boolean apply(Object input1, Object input2);
}