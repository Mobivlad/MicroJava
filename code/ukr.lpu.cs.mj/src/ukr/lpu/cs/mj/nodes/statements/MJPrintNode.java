package ukr.lpu.cs.mj.nodes.statements;

import java.util.List;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(type = MJExpressionNode.class)
public abstract class MJPrintNode extends MJStatementNode {

    private List<MJExpressionNode> list;

    public MJPrintNode(List<MJExpressionNode> list) {
        super();
        this.list = list;
    }

    @Specialization
    public void print(VirtualFrame f, String s) {
        String _s = s;
        for (MJExpressionNode ex : list) {
            Object value = ex.execute(f);
            if (value instanceof Integer) {
                _s = _s.replaceFirst("%i", value.toString());
            }
            if (value instanceof Double) {
                _s = _s.replaceFirst("%d", value.toString());
            }
            if (value instanceof String) {
                _s = _s.replaceFirst("%s", value.toString());
            }
        }
        System.out.println(_s);
    }

    @Specialization
    public void print(int s) {
        System.out.println(s);
    }

    @Specialization
    public void print(double s) {
        System.out.println(s);
    }

}
