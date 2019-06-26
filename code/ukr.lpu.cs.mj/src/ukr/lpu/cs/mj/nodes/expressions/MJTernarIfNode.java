package ukr.lpu.cs.mj.nodes.expressions;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

@NodeChild(value = "op1", type = MJExpressionNode.class)
@NodeChild(value = "op2", type = MJExpressionNode.class)
@NodeChild(value = "op3", type = MJExpressionNode.class)
public abstract class MJTernarIfNode extends MJExpressionNode {
    @Specialization
    public int f(boolean cond, int a, int b) {
        return cond ? a : b;
    }

    @Specialization
    public String f(boolean cond, String a, String b) {
        return cond ? a : b;
    }

    @Specialization
    public double f(boolean cond, int a, double b) {
        return cond ? a : b;
    }

    @Specialization
    public double f(boolean cond, double a, int b) {
        return cond ? a : b;
    }

    @Specialization
    public double f(boolean cond, double a, double b) {
        return cond ? a : b;
    }
}
