package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJVarValueNode;
import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(value = "expression", type = MJExpressionNode.class)
public abstract class MJAddAssignStatementNode extends MJStatementNode {
    @Child MJVarValueNode var;
    private static final String errorMessage = "Error #000: Lazy programmer";

    public MJAddAssignStatementNode(String varName) {
        super();
        this.var = new MJVarValueNode(varName);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, int value) {
        Object o = var.execute(frame);
        if (o instanceof Integer) {
            var.getSymbol(frame).setResult((int) o + value);
            return;
        }
        if (o instanceof Double) {
            var.getSymbol(frame).setResult((double) o + value);
            return;
        }
        throw new Error(errorMessage);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, double value) {
        Object o = var.execute(frame);
        if (o instanceof Integer) {
            var.getSymbol(frame).setResult((int) o + value);
            return;
        }
        if (o instanceof Double) {
            var.getSymbol(frame).setResult((double) o + value);
            return;
        }
        throw new Error(errorMessage);
    }

    @Specialization
    public void doAssign(VirtualFrame frame, String value) {
        Object o = var.execute(frame);
        if (o instanceof String) {
            var.getSymbol(frame).setResult(((String) o).concat(value));
            return;
        }
        throw new Error(errorMessage);
    }
}