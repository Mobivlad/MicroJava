package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJLessNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isLess(int a, int b) {
        return b > a;
    }

    @Specialization
    protected boolean isLess(double a, double b) {
        return (b - a) > 1e-6;
    }

    @Specialization
    protected boolean isLess(double a, int b) {
        return (b - a) > 1e-6;
    }

    @Specialization
    protected boolean isLess(int a, double b) {
        return (b - a) > 1e-6;
    }
}
