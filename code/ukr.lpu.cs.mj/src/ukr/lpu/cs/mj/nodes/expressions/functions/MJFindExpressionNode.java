package ukr.lpu.cs.mj.nodes.expressions.functions;

import com.oracle.truffle.api.dsl.Specialization;
import ukr.lpu.cs.mj.nodes.expressions.MJBinaryExpressionNode;

public abstract class MJFindExpressionNode extends MJBinaryExpressionNode {

    @Specialization
    public int find(String text, String pattern) {
        return text.indexOf(pattern);
    }

    @Specialization
    public int find(String text, int pattern) {
        return text.indexOf(Integer.toString(pattern));
    }

    @Specialization
    public int find(String text, double pattern) {
        return text.indexOf(Double.toString(pattern));
    }

}
