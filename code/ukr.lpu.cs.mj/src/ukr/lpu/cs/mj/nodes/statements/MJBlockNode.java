package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class MJBlockNode extends MJStatementNode {
    @Children private final MJStatementNode[] bodyNodes;

    public MJBlockNode(MJStatementNode[] nodes) {
        this.bodyNodes = nodes;
    }

    @Specialization
    public void startBlock(VirtualFrame frame) {
        for (MJStatementNode node : bodyNodes) {
            if (node != null) {
                node.executeVoid(frame);
            }
        }
    }
}
