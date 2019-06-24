package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJDivNode extends MJBinaryExpressionNode {
    @Specialization
    public int div(int a, int b) {
        return a / b;
    }

    @Specialization
    public double div(double a, double b) {
        return a / b;
    }

    @Specialization
    public double div(int a, double b) {
        return a / b;
    }

    @Specialization
    public double div(double a, int b) {
        return a / b;
    }

    @Specialization
    public String div(String a, int b) {
        if (a.length() < b)
            return "";
        return a.substring(0, a.length() / b);
    }
}
