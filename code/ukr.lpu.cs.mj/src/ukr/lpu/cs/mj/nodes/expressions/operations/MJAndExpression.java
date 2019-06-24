package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJBinaryExpressionNode;

public abstract class MJAndExpression extends MJBinaryExpressionNode {
    @Specialization
    public boolean and(boolean a, boolean b) {
        return a && b;
    }
}
