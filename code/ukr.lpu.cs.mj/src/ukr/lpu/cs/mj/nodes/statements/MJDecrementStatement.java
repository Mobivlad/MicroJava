package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJVarValueNode;

public abstract class MJDecrementStatement extends MJStatementNode {

    @Child MJVarValueNode var;
    private static final String errorMessage = "Error #000: Lazy programmer";

    public MJDecrementStatement(String varName) {
        super();
        this.var = new MJVarValueNode(varName);
    }

    @Specialization
    public void doInc(VirtualFrame frame) {
        Object o = var.execute(frame);
        if (o instanceof Integer) {
            var.getSymbol(frame).setResult((int) o - 1);
            return;
        }
        if (o instanceof Double) {
            var.getSymbol(frame).setResult((double) o - 1);
            return;
        }
        throw new Error(errorMessage);
    }
}
