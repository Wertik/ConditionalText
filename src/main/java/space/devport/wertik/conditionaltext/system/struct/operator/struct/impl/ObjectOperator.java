package space.devport.wertik.conditionaltext.system.struct.operator.struct.impl;

import space.devport.wertik.conditionaltext.system.struct.operator.struct.TernaryFunction;

public interface ObjectOperator extends TernaryFunction<Object, Object, Boolean> {
    @Override
    Boolean apply(Object input, Object required);
}