package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJGreaterEqualsNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isGreaterEquals(int a, int b) {
        return a >= b;
    }

    @Specialization
    protected boolean isGreaterEquals(double a, double b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isGreaterEquals(double a, int b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isGreaterEquals(int a, double b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }
}
