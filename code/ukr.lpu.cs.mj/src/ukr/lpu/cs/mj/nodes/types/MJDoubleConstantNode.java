package ukr.lpu.cs.mj.nodes.types;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJUnaryExpressionNode;

public class MJDoubleConstantNode extends MJUnaryExpressionNode {
    private final double x;

    public MJDoubleConstantNode(double x) {
        this.x = x;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return executeDouble(frame);
    }

    @Override
    public double executeDouble(VirtualFrame frame) {
        return x;
    }
}
