package ukr.lpu.cs.mj.nodes.expressions;

import com.oracle.truffle.api.dsl.NodeChild;

@NodeChild(value = "op", type = MJExpressionNode.class)
public abstract class MJUnaryExpressionNode extends MJExpressionNode {
}