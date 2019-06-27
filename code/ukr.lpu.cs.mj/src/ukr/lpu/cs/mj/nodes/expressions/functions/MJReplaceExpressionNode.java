package ukr.lpu.cs.mj.nodes.expressions.functions;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(value = "text", type = MJExpressionNode.class)
@NodeChild(value = "target", type = MJExpressionNode.class)
@NodeChild(value = "replacement", type = MJExpressionNode.class)
public abstract class MJReplaceExpressionNode extends MJExpressionNode {

    @Specialization
    public String replace(String text, String target, String replacement) {
        return text.replace(target, replacement);
    }

    @Specialization
    public String replace(String text, String target, int replacement) {
        return text.replace(target, Integer.toString(replacement));
    }

    @Specialization
    public String replace(String text, String target, double replacement) {
        return text.replace(target, Double.toString(replacement));
    }

    @Specialization
    public String replace(String text, int target, String replacement) {
        return text.replace(Integer.toString(target), replacement);
    }

    @Specialization
    public String replace(String text, double target, String replacement) {
        return text.replace(Double.toString(target), replacement);
    }

    @Specialization
    public String replace(String text, int target, int replacement) {
        return text.replace(Integer.toString(target), Integer.toString(replacement));
    }

    @Specialization
    public String replace(String text, double target, double replacement) {
        return text.replace(Double.toString(target), Double.toString(replacement));
    }
}