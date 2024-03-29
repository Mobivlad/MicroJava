package ukr.lpu.cs.mj.nodes;

import java.util.HashMap;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;

import ukr.lpu.cs.mj.MJNodeFactory;
import ukr.lpu.cs.mj.MJNodeFactory.ValType;
import ukr.lpu.cs.mj.nodes.exceptions.MJReturnException;
import ukr.lpu.cs.mj.nodes.statements.MJStatementNode;
import ukr.lpu.cs.mj.nodes.symbols.MJDoubleSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJIntSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJStringSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJSymbolNode;

public class MJMethodBodyNode extends RootNode {
    private String methodName;
    private MJProgramNode program;
    private ValType retType;
    private HashMap<String, ValType> arguments = new HashMap<>();
    private FrameDescriptor local;
    private MJStatementNode body;

    public ValType getRetType() {
        return retType;
    }

    public MJMethodBodyNode(ValType retType, String str, MJProgramNode program) {
        super(null, new FrameDescriptor());
        this.program = program;
        this.retType = retType;
        this.local = getFrameDescriptor();
        this.methodName = str;
    }

    public HashMap<String, ValType> getArgumentsDesc() {
        return arguments;
    }

    public MJProgramNode getProgram() {
        return program;
    }

    public MJStatementNode getBody() {
        return body;
    }

    public void setBody(MJStatementNode body) {
        this.body = body;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String name) {
        this.methodName = name;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object[] args = frame.getArguments();
        int i = 1;
        for (String s : arguments.keySet()) {
            switch (arguments.get(s)) {
                case INT:
                    ((MJIntSymbolNode) local.findFrameSlot(s).getInfo()).setResult(args[i]);
                    break;
                case DOUBLE:
                    ((MJDoubleSymbolNode) local.findFrameSlot(s).getInfo()).setResult(args[i]);
                    break;
                case STRING:
                    ((MJStringSymbolNode) local.findFrameSlot(s).getInfo()).setResult(args[i]);
                    break;
                default:
                    break;
            }
            i++;
        }
        try {
            body.executeVoid(frame);
        } catch (MJReturnException ex) {
            return ex.getResult();
        }
        return null;
    }

    public void create() {
        for (String s : arguments.keySet()) {
            local.addFrameSlot(s, MJNodeFactory.getSymbol(arguments.get(s), s), FrameSlotKind.Object);
        }
    }

    public void addVars(ValType type, String args[]) {
        for (String s : args) {
            local.addFrameSlot(s, MJNodeFactory.getSymbol(type, s), FrameSlotKind.Object);
        }
    }

    public void addArg(ValType type, String arg) {
        arguments.put(arg, type);
    }

    public MJSymbolNode getVar(String name) {
        FrameDescriptor desk = local;
        FrameSlot f = desk.findFrameSlot(name);
        if (f == null)
            desk = program.getFrameDescriptor();
        f = desk.findFrameSlot(name);
        if (f == null) {
            throw new Error("No variable with name '" + name + "'");
        }
        return (MJSymbolNode) f.getInfo();

    }

}
