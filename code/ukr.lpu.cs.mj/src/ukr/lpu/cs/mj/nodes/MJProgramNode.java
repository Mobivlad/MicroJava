package ukr.lpu.cs.mj.nodes;

import java.util.HashMap;
import java.util.Map;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

import ukr.lpu.cs.mj.MJNodeFactory;
import ukr.lpu.cs.mj.MJNodeFactory.ValType;
import ukr.lpu.cs.mj.nodes.symbols.MJDoubleSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJIntSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJStringSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJSymbolNode;

public class MJProgramNode extends RootNode {

    private final Map<String, MJMethodBodyNode> functionmap = new HashMap<>();
    private FrameDescriptor local;

    public MJProgramNode() {
        super(null, new FrameDescriptor());
        local = getFrameDescriptor();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        RootCallTarget call = Truffle.getRuntime().createCallTarget(getFunction("main"));
        return call.call(local);
    }

    public void addVar(ValType type, String name) {
        local.addFrameSlot(name, MJNodeFactory.getSymbol(type, name), FrameSlotKind.Object);
    }

    public void addVar(ValType type, String name, Object value) {
        MJSymbolNode symbol = MJNodeFactory.getSymbol(type, name);
        switch (type) {
            case INT:
                ((MJIntSymbolNode) symbol).setResult(value);
                break;
            case DOUBLE:
                ((MJDoubleSymbolNode) symbol).setResult(value);
                break;
            case STRING:
                ((MJStringSymbolNode) symbol).setResult(value);
                break;
            default:
                break;
        }
        symbol.startBeConstant();
        local.addFrameSlot(name, symbol, FrameSlotKind.Object);
    }

    public void addVars(ValType type, String args[]) {
        for (int i = 0; i < args.length; i++) {
            addVar(type, args[i]);
        }
    }

    public MJMethodBodyNode getFunction(String name) {
        CompilerAsserts.neverPartOfCompilation();
        MJMethodBodyNode method = functionmap.get(name);
        if (method == null) {
            throw new Error("No method with name '" + name + "'");
        }
        return method;
    }

    public void addFunction(MJMethodBodyNode node) {
        CompilerAsserts.neverPartOfCompilation();
        functionmap.put(node.getMethodName(), node);
    }

}
