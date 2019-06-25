package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJVarValueNode;
import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(value = "expression", type = MJExpressionNode.class)
public abstract class MJAssignStatementNode extends MJStatementNode {
    @Child MJVarValueNode var;

    public MJAssignStatementNode(String varName) {
        super();
        this.var = new MJVarValueNode(varName);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, int value) {
        var.getSymbol(frame).setResult(value);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, double value) {
        var.getSymbol(frame).setResult(value);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, String value) {
        var.getSymbol(frame).setResult(value);
    }
}
