package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;

public class MJAssignStatementNode extends MJStatementNode {
    private String name;
    private MJExpressionNode val;

    public MJAssignStatementNode(String name, MJExpressionNode val) {
        this.name = name;
        this.val = val;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void executeVoid(VirtualFrame frame) {
        VirtualFrame f0 = frame;
        FrameDescriptor desc = f0.getFrameDescriptor();
        if (desc.findFrameSlot(name) == null) {
            desc = ((VirtualFrame) f0.getArguments()[0]).getFrameDescriptor();
            f0 = (VirtualFrame) f0.getArguments()[0];
        }
        FrameSlot f = desc.findFrameSlot(name);
        Object value = val.execute(frame);
        if (value instanceof Integer) {
            switch (f.getKind()) {
                case Int:
                    f0.setInt(f, (int) value);
                    break;
                case Double:
                    f0.setDouble(f, ((int) value));
                    break;
                default:
                    throw new Error("Cannot assign value");
            }
        }
        if (value instanceof Double) {
            switch (f.getKind()) {
                case Int:
                    f0.setInt(f, (int) ((double) value));
                    break;
                case Double:
                    f0.setDouble(f, ((double) value));
                    break;
                default:
                    throw new Error("Cannot assign value");
            }
        }
        if (value instanceof String) {
            switch (f.getKind()) {
                case Int:
                    f0.setInt(f, Integer.parseInt((String) value));
                    break;
                case Double:
                    f0.setDouble(f, Double.parseDouble((String) value));
                    break;
                case Object:
                    f0.setObject(f, value);
                    break;
                default:
                    throw new Error("Cannot assign value");
            }
        }
        return;
    }
}
