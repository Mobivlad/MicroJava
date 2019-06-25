package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJUnaryExpressionNode;

public abstract class MJNegateNode extends MJUnaryExpressionNode {
    @Specialization
    protected int minus(int a) {
        return -a;
    }

    @Specialization
    protected double minus(double a) {
        return -a;
    }
}
