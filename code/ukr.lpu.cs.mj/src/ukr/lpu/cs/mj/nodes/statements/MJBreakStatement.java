package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.exceptions.MJBreakException;

public abstract class MJBreakStatement extends MJStatementNode {

    @Specialization
    public void doBreak() {
        throw MJBreakException.SINGELTONE;
    }
}
