package space.devport.wertik.conditionaltext.system.struct.operator;

public interface TernaryFunction<S, U, V> {
    V apply(S input1, U input2);
}