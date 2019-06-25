package ukr.lpu.cs.mj.nodes.expressions.operations;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJOrExpression extends MJBinaryExpressionNode {
    @Specialization
    public boolean or(boolean a, boolean b) {
        return a || b;
    }
}
