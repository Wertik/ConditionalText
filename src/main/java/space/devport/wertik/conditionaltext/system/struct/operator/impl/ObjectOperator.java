package space.devport.wertik.conditionaltext.system.struct.operator.impl;

import space.devport.wertik.conditionaltext.system.struct.operator.TernaryFunction;

public interface ObjectOperator extends TernaryFunction<Object, Object, Boolean> {
    @Override
    Boolean apply(Object input, Object required);
}