package ukr.lpu.cs.mj.nodes.statements;

import java.util.Scanner;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJVarValueNode;
import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.symbols.MJSymbolNode;

public abstract class MJReadStatement extends MJStatementNode {
    @Child MJVarValueNode var;
    private static final String errorMessage = "Error #000: Lazy programmer";

    public MJReadStatement(String varName) {
        super();
        this.var = new MJVarValueNode(varName);
    }

    @Specialization
    public void read(VirtualFrame frame) {
        Object o = var.execute(frame);
        MJSymbolNode symbol = var.getSymbol(frame);
        Scanner s = new Scanner(System.in);
        System.out.print("> ");
        if (o instanceof Integer) {
            symbol.setResult(s.nextInt());
            return;
        }
        if (o instanceof Double) {
            symbol.setResult(s.nextDouble());
            return;
        }
        if (o instanceof String) {
            symbol.setResult(s.next());
            return;
        }
        throw new Error(errorMessage);
    }

}
