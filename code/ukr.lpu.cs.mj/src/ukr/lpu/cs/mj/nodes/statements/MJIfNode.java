package ukr.lpu.cs.mj.nodes.statements;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.CompilerDirectives;

import ukr.lpu.cs.mj.nodes.expressions.MJExpressionNode;

@NodeChild(value = "conditionNode", type = MJExpressionNode.class)
public abstract class MJIfNode extends MJStatementNode {
    @Child private MJStatementNode thenPartNode;
    @Child private MJStatementNode elsePartNode;

    // Block of elements for optimizations
    @CompilerDirectives.CompilationFinal private int thenCount;
    @CompilerDirectives.CompilationFinal private int elseCount;

    // BranchProfile called only first time non optimizes code
    private final BranchProfile thenProfile = BranchProfile.create();
    private final BranchProfile elseProfile = BranchProfile.create();

    // End of block of elements for optimizations

    public MJIfNode(MJStatementNode thenPartNode, MJStatementNode elsePartNode) {
        this.thenPartNode = thenPartNode;
        this.elsePartNode = elsePartNode;
    }

    public MJIfNode(MJStatementNode thenPartNode) {
        this.thenPartNode = thenPartNode;
        this.elsePartNode = null;
    }

    @Specialization
    public void doVoid(VirtualFrame frame, boolean condition) {
        if (CompilerDirectives.injectBranchProbability(getBranchProbability(), condition)) {
            if (CompilerDirectives.inInterpreter()) {
                thenCount++;
            }
            thenProfile.enter();
            thenPartNode.executeVoid(frame);
        } else {
            if (CompilerDirectives.inInterpreter()) {
                elseCount++;
            }
            elseProfile.enter();
            elsePartNode.executeVoid(frame);
        }
        /*
         * if (condition) { thenPartNode.executeVoid(frame); } else { if (elsePartNode != null)
         * elsePartNode.executeVoid(frame); }
         */
    }

    private double getBranchProbability() {
        final int totalCount = thenCount + elseCount;

        if (totalCount == 0) {
            return 0;
        } else {
            return (double) thenCount / (double) (thenCount + elseCount);
        }
    }

}
