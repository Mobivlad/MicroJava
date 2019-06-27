package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJGreaterNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isGreater(int a, int b) {
        return a > b;
    }

    @Specialization
    protected boolean isGreater(double a, double b) {
        return (a - b) > 1e-6;
    }

    @Specialization
    protected boolean isGreater(double a, int b) {
        return (a - b) > 1e-6;
    }

    @Specialization
    protected boolean isGreater(int a, double b) {
        return (a - b) > 1e-6;
    }
}
