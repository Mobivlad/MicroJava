package ukr.lpu.cs.mj.nodes.types;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJUnaryExpressionNode;

public class MJBooleanConstantNode extends MJUnaryExpressionNode {
    private final boolean x;

    public MJBooleanConstantNode(boolean x) {
        this.x = x;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeBoolean(frame);
    }

    @Override
    public boolean executeBoolean(VirtualFrame frame) {
        return x;
    }
}
