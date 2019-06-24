package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJEqualsNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isEquals(int a, int b) {
        return a == b;
    }

    @Specialization
    protected boolean isEquals(int a, double b) {
        return Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isEquals(double a, int b) {
        return Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isEquals(double a, double b) {
        return Math.abs(a - b) < 1e-6;
    }

    @Specialization
    protected boolean isEquals(String a, String b) {
        return a.equals(b);
    }

    @Specialization
    protected boolean isEquals(char a, char b) {
        return a == b;
    }
}
