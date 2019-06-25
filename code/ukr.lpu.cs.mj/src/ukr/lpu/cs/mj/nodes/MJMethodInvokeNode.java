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
        Object[] callArgs = new Object[args.size() + 1];
        callArgs[0] = frame.getArguments()[0];
        for (int i = 1; i < callArgs.length; i++)
            callArgs[i] = args.get(i - 1).execute(frame);
        RootCallTarget call = Truffle.getRuntime().createCallTarget(body);
        /*
         * if (body.getRetType()== null) return call.call(); return
         * MJNodeFactory.getSymbol(body.getRetType(), "",call.call()).execute(frame);
         */
        return call.call(callArgs);
    }
}
