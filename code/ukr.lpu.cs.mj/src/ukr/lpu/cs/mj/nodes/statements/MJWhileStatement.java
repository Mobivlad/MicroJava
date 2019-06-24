package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.exceptions.MJBreakException;
import ukr.lpu.cs.mj.nodes.exceptions.MJContinueException;

public class MJWhileStatement extends MJStatementNode {
    @Child protected LoopNode loopNode;

    public MJWhileStatement(MJExpressionNode conditionNode, MJStatementNode blockNode) {
        loopNode = Truffle.getRuntime().createLoopNode(new RepeatingWhileNode(conditionNode, blockNode));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        loopNode.executeLoop(frame);
    }

    private static class RepeatingWhileNode extends Node implements RepeatingNode {

        @Child private MJExpressionNode conditionNode;
        @Child private MJStatementNode blockNode;

        public RepeatingWhileNode(MJExpressionNode conditionNode, MJStatementNode blockNode) {
            this.conditionNode = conditionNode;
            this.blockNode = blockNode;
        }

        @Override
        public boolean executeRepeating(VirtualFrame frame) {
            try {
                if (!conditionNode.executeBoolean(frame)) {
                    return false;
                }
            } catch (UnexpectedResultException e) {
                throw new UnsupportedOperationException(e);
            }
            try {
                blockNode.executeVoid(frame);
            } catch (MJContinueException e) {
                return true;
            } catch (MJBreakException e) {
                return false;
            }
            return true;

        }
    }
}
