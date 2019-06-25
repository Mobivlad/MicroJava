package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJMulNode extends MJBinaryExpressionNode {
    @Specialization
    public int mul(int a, int b) {
        return a * b;
    }

    @Specialization
    public double mul(double a, double b) {
        return a * b;
    }

    @Specialization
    public double mul(int a, double b) {
        return a * b;
    }

    @Specialization
    public double mul(double a, int b) {
        return a * b;
    }

    @Specialization
    public String mul(String a, int b) {
        StringBuilder res = new StringBuilder(a);
        for (int i = 0; i < b - 1; i++)
            res.append(a);
        return res.toString();
    }
}
