package ukr.lpu.cs.mj.nodes.types;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.expressions.MJUnaryExpressionNode;

public class MJCharConstantNode extends MJUnaryExpressionNode {
    private final char x;

    public MJCharConstantNode(char x) {
        this.x = x;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeCharacter(frame);
    }

    @Override
    public char executeCharacter(VirtualFrame frame) {
        return x;
    }
}
