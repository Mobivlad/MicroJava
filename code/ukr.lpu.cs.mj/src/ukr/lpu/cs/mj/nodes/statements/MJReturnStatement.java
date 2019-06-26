package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.exceptions.MJReturnException;
import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

public abstract class MJReturnStatement extends MJStatementNode {
    @Child private MJExpressionNode valueNode;

    public MJReturnStatement(MJExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Specialization
    public void doReturn(VirtualFrame frame) {
        if (valueNode == null)
            throw new MJReturnException(null);
        throw new MJReturnException(valueNode.execute(frame));
    }
}
