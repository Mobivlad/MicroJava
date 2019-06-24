package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "op1", type = MJExpressionNode.class)
@NodeChild(value = "op2", type = MJExpressionNode.class)
public abstract class MJBinaryExpressionNode extends MJExpressionNode {
}