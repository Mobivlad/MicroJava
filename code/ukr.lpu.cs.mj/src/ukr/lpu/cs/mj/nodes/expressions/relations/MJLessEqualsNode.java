package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJLessEqualsNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isLessEquals(int a, int b) {
        return b >= a;
    }

    @Specialization
    protected boolean isLessEquals(double a, double b) {
        return (b - a) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isLessEquals(double a, int b) {
        return (b - a) > 1e-6 || Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isLessEquals(int a, double b) {
        return (b - a) > 1e-6 || Math.abs(a - b) < 1e-6;
    }
}
