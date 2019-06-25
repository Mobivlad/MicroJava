package ukr.lpu.cs.mj;

import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.MJSymbolNode;
import ukr.lpu.cs.mj.nodes.expressions.operations.MJAddNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.operations.MJDivNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.operations.MJModNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.operations.MJMulNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.operations.MJSubNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJEqualsNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJGreaterEqualsNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJGreaterNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJLessEqualsNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJLessNodeGen;
import ukr.lpu.cs.mj.nodes.expressions.relations.MJNotEqualsNodeGen;
import ukr.lpu.cs.mj.nodes.statements.*;
import ukr.lpu.cs.mj.nodes.symbols.MJDoubleSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJIntSymbolNode;
import ukr.lpu.cs.mj.nodes.symbols.MJStringSymbolNode;
import ukr.lpu.cs.mj.parser.RecursiveDescentParser.CompOp;
import ukr.lpu.cs.mj.parser.RecursiveDescentParser.OpCode;

public class MJNodeFactory {
    public static MJExpressionNode getCompExpression(CompOp op, MJExpressionNode... args) {
        switch (op) {
            case eq:
                return MJEqualsNodeGen.create(args[0], args[1]);
            case ge:
                return MJGreaterEqualsNodeGen.create(args[0], args[1]);
            case gt:
                return MJGreaterNodeGen.create(args[0], args[1]);
            case le:
                return MJLessEqualsNodeGen.create(args[0], args[1]);
            case lt:
                return MJLessNodeGen.create(args[0], args[1]);
            case ne:
                return MJNotEqualsNodeGen.create(args[0], args[1]);
            default:
                break;
        }
        return null;
    }

    public static enum ValType {
        INT,
        STRING,
        DOUBLE
    }

    public static ValType getType(String s) {
        switch (s) {
            case "int":
                return ValType.INT;
            case "double":
                return ValType.DOUBLE;
            case "string":
                return ValType.STRING;
            default:
                return null;
        }
    }

    public static MJSymbolNode getSymbol(ValType t, String name, Object value) {
        switch (t) {
            case INT: {
                MJIntSymbolNode ret = new MJIntSymbolNode(name);
                ret.setResult(value);
                return ret;
            }
            case DOUBLE: {
                MJDoubleSymbolNode ret = new MJDoubleSymbolNode(name);
                ret.setResult(value);
                return ret;
            }
            case STRING: {
                MJStringSymbolNode ret = new MJStringSymbolNode(name);
                ret.setResult(value);
                return ret;
            }
            default:
                return null;
        }
    }

    public static MJSymbolNode getSymbol(ValType t, String name) {
        switch (t) {
            case INT: {
                MJIntSymbolNode ret = new MJIntSymbolNode(name);
                ret.setResult(0);
                return ret;
            }
            case DOUBLE: {
                MJDoubleSymbolNode ret = new MJDoubleSymbolNode(name);
                ret.setResult(0.0);
                return ret;
            }
            case STRING: {
                MJStringSymbolNode ret = new MJStringSymbolNode(name);
                ret.setResult("");
                return ret;
            }
            default:
                return null;
        }
    }

    public static MJExpressionNode getOpExpression(OpCode op, MJExpressionNode... args) {
        switch (op) {
            case add:
                return MJAddNodeGen.create(args[0], args[1]);
            case sub:
                return MJSubNodeGen.create(args[0], args[1]);
            case mul:
                return MJMulNodeGen.create(args[0], args[1]);
            case div:
                return MJDivNodeGen.create(args[0], args[1]);
            case rem:
                return MJModNodeGen.create(args[0], args[1]);
            default:
                break;
        }
        return null;
    }

    public static MJStatementNode getAssignStatement(OpCode op, MJExpressionNode symbol, MJExpressionNode... args) {
        switch (op) {
            case store:
                return MJAssignStatementNodeGen.create(symbol, args[0]);
            case add:
                return MJAddAssignStatementNodeGen.create(symbol, args[1], args[0]);
            case sub:
                return MJSubAssignStatementNodeGen.create(symbol, args[1], args[0]);
            case mul:
                return MJMulAssignStatementNodeGen.create(symbol, args[1], args[0]);
            case div:
                return MJDivAssignStatementNodeGen.create(symbol, args[1], args[0]);
            case rem:
                return MJRemAssignStatementNodeGen.create(symbol, args[1], args[0]);
            default:
                break;
        }
        return null;
    }
}
