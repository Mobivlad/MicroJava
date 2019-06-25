package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJSubNode extends MJBinaryExpressionNode {
    @Specialization
    public int sub(int a, int b) {
        return a - b;
    }

    @Specialization
    public double sub(double a, double b) {
        return a - b;
    }

    @Specialization
    public double sub(int a, double b) {
        return a - b;
    }

    @Specialization
    public double sub(double a, int b) {
        return a - b;
    }

    @Specialization
    public char sub(char a, int b) {
        return (char) (a - b);
    }

    public String sub(String a, String b) {
        return a.replaceFirst(b, "");
    }
}
