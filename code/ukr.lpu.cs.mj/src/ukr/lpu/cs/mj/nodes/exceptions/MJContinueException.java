package ukr.lpu.cs.mj.nodes.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public class MJContinueException extends ControlFlowException {
    private static final long serialVersionUID = 9038895441829208333L;
    public static MJContinueException SINGELTONE = new MJContinueException();

    private MJContinueException() {
    }
}
