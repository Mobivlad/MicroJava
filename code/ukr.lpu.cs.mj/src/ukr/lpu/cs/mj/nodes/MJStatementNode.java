package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class MJStatementNode extends MJNode {
    public abstract void executeVoid(VirtualFrame frame);
}
