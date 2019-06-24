package ukr.lpu.cs.mj.nodes.types;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJUnaryExpressionNode;

public class MJStringConstantNode extends MJUnaryExpressionNode {
    private final String x;

    public MJStringConstantNode(String x) {
        this.x = x;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeString(frame);
    }

    @Override
    public String executeString(VirtualFrame frame) {
        return x;
    }
}
