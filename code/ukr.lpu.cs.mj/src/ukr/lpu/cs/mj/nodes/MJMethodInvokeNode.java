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
        FrameDescriptor f = getDesc();
        Object[] callArgs = new Object[args.size()];
        for (int i = 0; i < callArgs.length; i++)
            callArgs[i] = args.get(i).execute(frame);
        int i = 0;
        for (String s : body.getArgumentsDesc().keySet()) {
            f.addFrameSlot(s, MJNodeFactory.getSymbol(body.getArgumentsDesc().get(s), s, callArgs[i]), FrameSlotKind.Object);
            i++;
        }
        body.setLocal(f);
        /*
         * RootCallTarget call = Truffle.getRuntime().createCallTarget(body); if (body.getRetType()
         * == null) return call.call(); return MJNodeFactory.getSymbol(body.getRetType(), "",
         * call.call()).execute(frame);
         */
        return body.execute(frame);
    }

    private FrameDescriptor getDesc() {
        FrameDescriptor f = new FrameDescriptor();
        for (String s : body.getLocalDesc().keySet()) {
            f.addFrameSlot(s, MJNodeFactory.getSymbol(body.getLocalDesc().get(s), s), FrameSlotKind.Object);
        }
        return f;
    }

}
