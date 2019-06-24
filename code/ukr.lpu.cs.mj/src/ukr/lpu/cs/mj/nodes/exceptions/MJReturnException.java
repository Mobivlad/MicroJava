package ukr.lpu.cs.mj.nodes.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public class MJReturnException extends ControlFlowException {
    private static final long serialVersionUID = 3551209188681948944L;
    private final Object result;

    public Object getResult() {
        return result;
    }

    public MJReturnException(Object result) {
        super();
        this.result = result;
    }
}
