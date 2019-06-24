package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;

public class MJVarValueNode extends MJExpressionNode {
    private MJMethodBodyNode method;
    private String name;

    public MJVarValueNode(MJMethodBodyNode method, String name) {
        this.method = method;
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return method.getVar(name).execute(frame);
    }
}
