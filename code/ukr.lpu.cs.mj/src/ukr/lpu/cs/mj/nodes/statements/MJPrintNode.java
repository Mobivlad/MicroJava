package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(type = MJExpressionNode.class)
public abstract class MJPrintNode extends MJStatementNode {

    @Specialization
    public void print(String s) {
        System.out.println(s);
    }

    @Specialization
    public void print(int s) {
        System.out.println(s);
    }

    @Specialization
    public void print(double s) {
        System.out.println(s);
    }

    @Specialization
    public void print(char s) {
        System.out.println(s);
    }

    @Specialization
    public void print(boolean s) {
        System.out.println(s);
    }

}
