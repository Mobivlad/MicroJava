package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.MJSymbolNode;

@NodeChild(value = "symbol", type = MJExpressionNode.class)
@NodeChild(value = "expression", type = MJExpressionNode.class)
public abstract class MJAssignStatementNode extends MJStatementNode {

    @Specialization
    public void doAssign(MJSymbolNode node, int value) {
        node.setResult(value);
    }
}
