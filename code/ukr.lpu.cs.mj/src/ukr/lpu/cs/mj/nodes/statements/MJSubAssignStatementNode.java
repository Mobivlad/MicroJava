package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.MJSymbolNode;

@NodeChild(value = "symbol", type = MJExpressionNode.class)
@NodeChild(value = "expression1", type = MJExpressionNode.class)
@NodeChild(value = "expression2", type = MJExpressionNode.class)
public abstract class MJSubAssignStatementNode extends MJStatementNode {

    @Specialization
    public void doAssign(MJSymbolNode node, int value1, int value2) {
        node.setResult(value1 - value2);
    }

    @Specialization
    public void doAssign(MJSymbolNode node, double value1, double value2) {
        node.setResult(value1 - value2);
    }
}
