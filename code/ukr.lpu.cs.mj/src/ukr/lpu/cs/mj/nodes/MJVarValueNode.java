package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

public class MJVarValueNode extends MJExpressionNode {
    private String name;

    public MJVarValueNode(String name) {
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        VirtualFrame f0 = frame;
        FrameDescriptor desc = frame.getFrameDescriptor();
        if (desc.findFrameSlot(name) == null) {
            f0 = (VirtualFrame) frame.getArguments()[0];
            desc = f0.getFrameDescriptor();
        }
        FrameSlot f = desc.findFrameSlot(name);
        return f0.getValue(f);
    }
}
