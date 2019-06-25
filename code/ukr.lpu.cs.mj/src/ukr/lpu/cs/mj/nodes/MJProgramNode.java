package ukr.lpu.cs.mj.nodes;

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
        new MJMethodInvokeNode(getFunction("main")).execute(frame);
        return null;
        /*
         * RootCallTarget call = Truffle.getRuntime().createCallTarget(getFunction("main")); return
         * call.call();
         */
    }

    public void addVar(MJSymbolNode node) {
        local.addFrameSlot(node.getSymbol(), node, FrameSlotKind.Object);
    }

    public void addVars(ValType type, String args[]) {
        for (String s : args) {
            local.addFrameSlot(s, MJNodeFactory.getSymbol(type, s), FrameSlotKind.Object);
        }
    }

    public void addConstant(MJSymbolNode var) {
        local.addFrameSlot(var.getSymbol(), var, FrameSlotKind.Object);
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
