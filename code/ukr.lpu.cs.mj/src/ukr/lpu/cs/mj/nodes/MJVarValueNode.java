package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.symbols.MJSymbolNode;

public class MJVarValueNode extends MJExpressionNode {
    private String name;

    public MJVarValueNode(String name) {
        this.name = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return getSymbol(frame).execute(frame);
    }

    public MJSymbolNode getSymbol(VirtualFrame frame) {
        FrameDescriptor desk = frame.getFrameDescriptor();
        FrameSlot f = desk.findFrameSlot(name);
        if (f == null)
            desk = (FrameDescriptor) frame.getArguments()[0];
        f = desk.findFrameSlot(name);
        if (f == null) {
            throw new Error("No variable with name '" + name + "'");
        }
        return (MJSymbolNode) f.getInfo();
    }

}
