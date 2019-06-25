package ukr.lpu.cs.mj.nodes;

import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.MJNodeFactory;

public class MJMethodInvokeNode extends MJExpressionNode {
    private MJMethodBodyNode body;
    private List<MJExpressionNode> args;

    public MJMethodInvokeNode(MJMethodBodyNode body, List<MJExpressionNode> args) {
        this.body = body;
        this.args = args;
    }

    public MJMethodInvokeNode(MJMethodBodyNode body) {
        this.body = body;
        this.args = new ArrayList<>();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] callArgs = new Object[args.size()];
        for (int i = 0; i < callArgs.length; i++)
            callArgs[i] = args.get(i).execute(frame);
        RootCallTarget call = Truffle.getRuntime().createCallTarget(body);
        if (body.getRetType() == null)
            return call.call(callArgs);
        return MJNodeFactory.getSymbol(body.getRetType(), "", call.call(callArgs)).execute(frame);
        // return body.execute(frame);
    }

}
