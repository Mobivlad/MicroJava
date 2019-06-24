package ukr.lpu.cs.mj.pesamples;

public class Arg extends Expression {
    final int index;

    public Arg(int index) {
        this.index = index;
    }

    @Override
    int execute(int[] arguments) {
        return arguments[index];
    }
}