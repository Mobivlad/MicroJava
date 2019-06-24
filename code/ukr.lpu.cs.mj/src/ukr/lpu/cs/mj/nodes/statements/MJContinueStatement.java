package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.exceptions.MJContinueException;

public abstract class MJContinueStatement extends MJStatementNode {

    @Specialization
    public void doContinue() {
        throw MJContinueException.SINGELTONE;
    }
}
