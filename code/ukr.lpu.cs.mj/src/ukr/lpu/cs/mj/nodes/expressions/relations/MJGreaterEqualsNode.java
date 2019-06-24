package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJGreaterEqualsNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isGreated(int a, int b) {
        return a >= b;
    }

    @Specialization
    protected boolean isGreated(double a, double b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isGreated(double a, int b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isGreated(int a, double b) {
        return (a - b) > 1e-6 || Math.abs(a - b) < 1e-6;
    }
}
