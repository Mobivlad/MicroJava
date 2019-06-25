package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJNode;

public abstract class MJStatementNode extends MJNode {
    public abstract void executeVoid(VirtualFrame frame);
}
