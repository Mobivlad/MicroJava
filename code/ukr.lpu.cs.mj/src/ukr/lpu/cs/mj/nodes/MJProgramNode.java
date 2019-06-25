package ukr.lpu.cs.mj.nodes;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

import ukr.lpu.cs.mj.MJNodeFactory;
import ukr.lpu.cs.mj.MJNodeFactory.ValType;

public class MJProgramNode extends RootNode {

    private FrameDescriptor functionmap = new FrameDescriptor();
    private FrameDescriptor local;

    public MJProgramNode() {
        super(null, new FrameDescriptor());
        local = getFrameDescriptor();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        /*
         * new MJMethodInvokeNode(getFunction("main")).execute(frame); return null;
         */
        System.out.println(frame);
        RootCallTarget call = Truffle.getRuntime().createCallTarget(getFunction("main"));
        return call.call(frame);
    }

    public void addVars(ValType type, String args[]) {
        for (String s : args) {
            switch (type) {
                case INT:
                    local.addFrameSlot(s, FrameSlotKind.Int);
                    break;
                case DOUBLE:
                    local.addFrameSlot(s, FrameSlotKind.Double);
                    break;
                case STRING:
                    local.addFrameSlot(s, FrameSlotKind.Object);
                    break;
            }
        }
    }

    public void addConstant(String name, ValType t, Object val) {
        if (t == ValType.INT)
            local.addFrameSlot(name, val, FrameSlotKind.Int);
        if (t == ValType.DOUBLE)
            local.addFrameSlot(name, val, FrameSlotKind.Double);
        if (t == ValType.STRING)
            local.addFrameSlot(name, val, FrameSlotKind.Object);
    }

    public boolean consistVar(String name) {
        return local.findFrameSlot(name) != null;
    }

    public MJMethodBodyNode getFunction(String name) {
        if (functionmap.findFrameSlot(name) == null) {
            throw new Error("No method with name '" + name + "'");
        }
        return (MJMethodBodyNode) functionmap.findFrameSlot(name).getInfo();
    }

    public void addFunction(MJMethodBodyNode node) {
        functionmap.addFrameSlot(node.getMethodName(), node, FrameSlotKind.Object);
    }

}
