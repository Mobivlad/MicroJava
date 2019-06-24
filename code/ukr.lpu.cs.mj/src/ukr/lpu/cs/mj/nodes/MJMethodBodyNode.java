package ukr.lpu.cs.mj.nodes;

import java.util.HashMap;

import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import ukr.lpu.cs.mj.MJNodeFactory.ValType;
import ukr.lpu.cs.mj.nodes.exceptions.MJReturnException;

public class MJMethodBodyNode extends MJExpressionNode {
    private String methodName;
    private FrameDescriptor global;
    private MJProgramNode program;
    private ValType retType;
    private HashMap<String, ValType> desc = new HashMap<>();
    private HashMap<String, ValType> arguments = new HashMap<>();
    private FrameDescriptor local;
    private MJStatementNode body;

    public ValType getRetType() {
        return retType;
    }

    public MJMethodBodyNode(ValType retType, String str, MJProgramNode program) {
        // super(null);
        this.program = program;
        this.retType = retType;
        this.global = program.getFrameDescriptor();
        this.methodName = str;
    }

    public void setLocal(FrameDescriptor local) {
        this.local = local;
    }

    public HashMap<String, ValType> getLocalDesc() {
        return desc;
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
        try {
            body.executeVoid(frame);
        } catch (MJReturnException ex) {
            return ex.getResult();
        }
        return null;
    }

    public void addVars(ValType type, String args[]) {
        for (String arg : args) {
            desc.put(arg, type);
        }
    }

    public void addArg(ValType type, String arg) {
        arguments.put(arg, type);
    }

    public MJSymbolNode getVar(String name) {
        FrameSlot f = local.findFrameSlot(name);
        if (f == null)
            f = global.findFrameSlot(name);
        if (f == null) {
            throw new Error("No variable with name '" + name + "'");
        }
        return (MJSymbolNode) f.getInfo();

    }

}
