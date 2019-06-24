package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;

@NodeChild(value = "conditionNode", type = MJExpressionNode.class)
public abstract class MJIfNode extends MJStatementNode {
    @Child private MJStatementNode thenPartNode;
    @Child private MJStatementNode elsePartNode;

    public MJIfNode(MJStatementNode thenPartNode, MJStatementNode elsePartNode) {
        this.thenPartNode = thenPartNode;
        this.elsePartNode = elsePartNode;
    }

    public MJIfNode(MJStatementNode thenPartNode) {
        this.thenPartNode = thenPartNode;
        this.elsePartNode = null;
    }

    @Specialization
    public void doVoid(VirtualFrame frame, boolean condition) {
        if (condition) {
            thenPartNode.executeVoid(frame);
        } else {
            if (elsePartNode != null)
                elsePartNode.executeVoid(frame);
        }
    }

}
