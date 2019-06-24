package ukr.lpu.cs.mj.nodes.statements;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.symbols.*;

@NodeChild(value = "op", type = MJExpressionNode.class)
public abstract class MJReadStatement extends MJStatementNode {
    @Specialization
    public void read(MJIntSymbolNode i) {
        Scanner s = new Scanner(System.in);
        System.out.print("> ");
        try {
            i.setResult(s.nextInt());
        } catch (InputMismatchException e) {
            throw new Error("Input value is not integer");
        }
    }

    @Specialization
    public void read(MJDoubleSymbolNode i) {
        Scanner s = new Scanner(System.in);
        System.out.print("> ");
        try {
            i.setResult(s.nextDouble());
        } catch (InputMismatchException e) {
            throw new Error("Input value is not double");
        }

    }

    @Specialization
    public void read(MJStringSymbolNode i) {
        Scanner s = new Scanner(System.in);
        System.out.print("> ");
        i.setResult(s.next());
    }
}
