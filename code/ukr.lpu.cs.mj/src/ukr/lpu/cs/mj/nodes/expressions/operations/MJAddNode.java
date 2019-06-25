package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJAddNode extends MJBinaryExpressionNode {
    @Specialization
    public int add(int a, int b) {
        return a + b;
    }

    @Specialization
    public double add(double a, double b) {
        return a + b;
    }

    @Specialization
    public double add(int a, double b) {
        return a + b;
    }

    @Specialization
    public double add(double a, int b) {
        return a + b;
    }

    @Specialization
    public char add(char a, int b) {
        return (char) (a + b);
    }

    @Specialization
    public String add(String a, String b) {
        return new StringBuilder(a).append(b).toString();
    }
}
