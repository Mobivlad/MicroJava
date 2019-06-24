package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJModNode extends MJBinaryExpressionNode {
    @Specialization
    public int mod(int a, int b) {
        return a % b;
    }

    @Specialization
    public double mod(double a, double b) {
        return a % b;
    }

    @Specialization
    public double mod(int a, double b) {
        return a % b;
    }

    @Specialization
    public double mod(double a, int b) {
        return a % b;
    }
}
