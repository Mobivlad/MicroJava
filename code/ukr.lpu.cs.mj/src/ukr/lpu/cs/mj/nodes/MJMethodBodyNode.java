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

public class MJMethodBodyNode extends RootNode {
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
        super(null, new FrameDescriptor());
        this.program = program;
        this.local = getFrameDescriptor();
        this.retType = retType;
        this.global = program.getFrameDescriptor();
        this.methodName = str;
    }

    public void create() {
        for (String s : desc.keySet()) {
            switch (desc.get(s)) {
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
        for (String s : arguments.keySet()) {
            switch (arguments.get(s)) {
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
        Object[] args = frame.getArguments();
        int i = 1;
        for (String s : arguments.keySet()) {
            frame.setObject(local.findFrameSlot(s), args[i]);
            i++;
        }
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

    public void addVar(ValType type, String arg) {
        desc.put(arg, type);
    }

    public void addArg(ValType type, String arg) {
        arguments.put(arg, type);
    }

}
