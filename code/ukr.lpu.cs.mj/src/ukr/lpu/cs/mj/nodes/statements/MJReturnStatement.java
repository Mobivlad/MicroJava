package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.exceptions.MJReturnException;
import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

public class MJReturnStatement extends MJStatementNode {
    @Child private MJExpressionNode valueNode;

    public MJReturnStatement(MJExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        if (valueNode == null)
            throw new MJReturnException(null);
        throw new MJReturnException(valueNode.execute(frame));
    }
}
