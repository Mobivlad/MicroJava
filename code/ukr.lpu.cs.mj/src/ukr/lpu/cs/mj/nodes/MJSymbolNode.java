package ukr.lpu.cs.mj.nodes;

public abstract class MJSymbolNode extends MJExpressionNode {
    protected final String symbol;
    protected boolean isConstant = false;

    public MJSymbolNode(String symbol) {
        this.symbol = symbol;
    }

    public void startBeConstant() {
        isConstant = true;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setResult(int value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");
        throw new ClassCastException("Cannot assign " + value + "to symbol" + symbol);
    }

    public void setResult(double value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");
        throw new ClassCastException("Cannot assign " + value + "to symbol" + symbol);
    }

    public void setResult(String value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");
        throw new ClassCastException("Cannot assign " + value + "to symbol" + symbol);
    }

    public void setResult(Object value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");
        throw new ClassCastException("Cannot assign " + value + "to symbol" + symbol);
    }
}
