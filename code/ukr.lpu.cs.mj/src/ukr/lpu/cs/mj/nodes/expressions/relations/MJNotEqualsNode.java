package ukr.lpu.cs.mj.nodes.expressions.relations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJNotEqualsNode extends MJBinaryExpressionNode {
    @Specialization
    protected boolean isNotEquals(int a, int b) {
        return a != b;
    }

    @Specialization
    protected boolean isNotEquals(int a, double b) {
        return Math.abs(a - b) >= 1e-6;
    }

    @Specialization
    protected boolean isNotEquals(double a, int b) {
        return Math.abs(a - b) >= 1e-6;
    }

    @Specialization
    protected boolean isNotEquals(double a, double b) {
        return Math.abs(a - b) >= 1e-6;
    }

    @Specialization
    protected boolean isNotEquals(String a, String b) {
        return !a.equals(b);
    }

    @Specialization
    protected boolean isEquals(char a, char b) {
        return a != b;
    }
}
