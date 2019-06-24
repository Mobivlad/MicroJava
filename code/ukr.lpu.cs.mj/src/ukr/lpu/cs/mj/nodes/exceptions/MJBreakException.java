package ukr.lpu.cs.mj.nodes.exceptions;

import com.oracle.truffle.api.nodes.ControlFlowException;

public class MJBreakException extends ControlFlowException {
    private static final long serialVersionUID = -1472062953729092225L;
    public static MJBreakException SINGELTONE = new MJBreakException();

    private MJBreakException() {
    }
}
