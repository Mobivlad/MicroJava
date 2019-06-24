package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;

import ukr.lpu.cs.mj.MJTypesGen;

public abstract class MJExpressionNode extends MJStatementNode {
    public abstract Object execute(VirtualFrame frame);

    public String executeString(VirtualFrame frame) throws UnexpectedResultException {
        return MJTypesGen.expectString(execute(frame));
    }

    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        return MJTypesGen.expectDouble(execute(frame));
    }

    public char executeCharacter(VirtualFrame frame) throws UnexpectedResultException {
        return MJTypesGen.expectCharacter(execute(frame));
    }

    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        return MJTypesGen.expectInteger(execute(frame));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        execute(frame);
    }

    public boolean executeBoolean(VirtualFrame frame) throws UnexpectedResultException {
        return MJTypesGen.expectBoolean(execute(frame));
    }
}
