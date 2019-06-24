package ukr.lpu.cs.mj.nodes.types;

import com.oracle.truffle.api.frame.VirtualFrame;
import ukr.lpu.cs.mj.nodes.MJUnaryExpressionNode;

public class MJIntegerConstantNode extends MJUnaryExpressionNode {
    private final int x;

    public MJIntegerConstantNode(int x) {
        this.x = x;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeInt(frame);
    }

    @Override
    public int executeInt(VirtualFrame frame) {
        return x;
    }
}
