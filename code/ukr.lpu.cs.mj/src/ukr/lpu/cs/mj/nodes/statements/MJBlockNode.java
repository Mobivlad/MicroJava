package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJStatementNode;

public class MJBlockNode extends MJStatementNode {
    @Children private final MJStatementNode[] bodyNodes;

    public MJBlockNode(MJStatementNode[] nodes) {
        this.bodyNodes = nodes;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        for (MJStatementNode node : bodyNodes) {
            if (node != null) {
                node.executeVoid(frame);
            }
        }
    }
}
