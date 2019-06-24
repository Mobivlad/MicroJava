package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.FrameSlot;

public abstract class MJFrameSlotNode extends MJExpressionNode {

    protected final FrameSlot slot;

    public MJFrameSlotNode(FrameSlot slot) {
        this.slot = slot;
    }
}
