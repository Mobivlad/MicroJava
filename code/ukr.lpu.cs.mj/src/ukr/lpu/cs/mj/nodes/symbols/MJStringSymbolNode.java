package ukr.lpu.cs.mj.nodes.symbols;

import com.oracle.truffle.api.frame.VirtualFrame;

import ukr.lpu.cs.mj.nodes.MJSymbolNode;

public class MJStringSymbolNode extends MJSymbolNode {
    public MJStringSymbolNode(String symbol) {
        super(symbol);
    }

    private String value;

    @Override
    public void setResult(String value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");

        this.value = value;
    }

    @Override
    public void setResult(Object value) {
        if (value instanceof Integer)
            setResult((int) value);
        if (value instanceof Double)
            setResult((double) value);
        if (value instanceof String)
            setResult((String) value);
    }

    @Override
    public void setResult(double value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");

        this.value = String.valueOf(value);
    }

    @Override
    public void setResult(int value) {
        if (isConstant)
            throw new ClassCastException("Cannot assign " + value + "to constant");

        this.value = String.valueOf(value);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return value;
    }
}
