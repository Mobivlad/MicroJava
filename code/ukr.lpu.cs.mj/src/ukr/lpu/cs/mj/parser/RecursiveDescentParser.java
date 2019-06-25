package ukr.lpu.cs.mj.parser;

import static ukr.lpu.cs.mj.parser.Token.Kind.and;
import static ukr.lpu.cs.mj.parser.Token.Kind.doublenumber;
import static ukr.lpu.cs.mj.parser.Token.Kind.stringConst;
import static ukr.lpu.cs.mj.parser.Token.Kind.assign;
import static ukr.lpu.cs.mj.parser.Token.Kind.break_;
import static ukr.lpu.cs.mj.parser.Token.Kind.charConst;
import static ukr.lpu.cs.mj.parser.Token.Kind.class_;
import static ukr.lpu.cs.mj.parser.Token.Kind.comma;
import static ukr.lpu.cs.mj.parser.Token.Kind.continue_;
import static ukr.lpu.cs.mj.parser.Token.Kind.else_;
import static ukr.lpu.cs.mj.parser.Token.Kind.eof;
import static ukr.lpu.cs.mj.parser.Token.Kind.final_;
import static ukr.lpu.cs.mj.parser.Token.Kind.ident;
import static ukr.lpu.cs.mj.parser.Token.Kind.if_;
import static ukr.lpu.cs.mj.parser.Token.Kind.lbrace;
import static ukr.lpu.cs.mj.parser.Token.Kind.lbrack;
import static ukr.lpu.cs.mj.parser.Token.Kind.lpar;
import static ukr.lpu.cs.mj.parser.Token.Kind.minus;
import static ukr.lpu.cs.mj.parser.Token.Kind.new_;
import static ukr.lpu.cs.mj.parser.Token.Kind.number;
import static ukr.lpu.cs.mj.parser.Token.Kind.or;
import static ukr.lpu.cs.mj.parser.Token.Kind.period;
import static ukr.lpu.cs.mj.parser.Token.Kind.plus;
import static ukr.lpu.cs.mj.parser.Token.Kind.print;
import static ukr.lpu.cs.mj.parser.Token.Kind.program;
import static ukr.lpu.cs.mj.parser.Token.Kind.rbrace;
import static ukr.lpu.cs.mj.parser.Token.Kind.rbrack;
import static ukr.lpu.cs.mj.parser.Token.Kind.read;
import static ukr.lpu.cs.mj.parser.Token.Kind.rem;
import static ukr.lpu.cs.mj.parser.Token.Kind.return_;
import static ukr.lpu.cs.mj.parser.Token.Kind.rpar;
import static ukr.lpu.cs.mj.parser.Token.Kind.semicolon;
import static ukr.lpu.cs.mj.parser.Token.Kind.slash;
import static ukr.lpu.cs.mj.parser.Token.Kind.times;
import static ukr.lpu.cs.mj.parser.Token.Kind.void_;
import static ukr.lpu.cs.mj.parser.Token.Kind.while_;

import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.ArrayList;
import java.util.List;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;

import ukr.lpu.cs.mj.MJNodeFactory;
import ukr.lpu.cs.mj.MJNodeFactory.ValType;
import ukr.lpu.cs.mj.nodes.MJExpressionNode;
import ukr.lpu.cs.mj.nodes.MJMethodBodyNode;
import ukr.lpu.cs.mj.nodes.MJMethodInvokeNode;
import ukr.lpu.cs.mj.nodes.MJProgramNode;
import ukr.lpu.cs.mj.nodes.MJStatementNode;
import ukr.lpu.cs.mj.nodes.MJSymbolNode;
import ukr.lpu.cs.mj.nodes.MJVarRefNode;
import ukr.lpu.cs.mj.nodes.MJVarValueNode;
import ukr.lpu.cs.mj.nodes.expressions.operations.*;
import ukr.lpu.cs.mj.nodes.statements.MJBlockNode;
import ukr.lpu.cs.mj.nodes.statements.MJBreakStatement;
import ukr.lpu.cs.mj.nodes.statements.MJBreakStatementNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJContinueStatement;
import ukr.lpu.cs.mj.nodes.statements.MJContinueStatementNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJDecrementStatementNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJIfNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJIncrementStatementNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJPrintNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJReadStatementNodeGen;
import ukr.lpu.cs.mj.nodes.statements.MJReturnStatement;
import ukr.lpu.cs.mj.nodes.statements.MJWhileStatement;
import ukr.lpu.cs.mj.nodes.types.MJCharConstantNode;
import ukr.lpu.cs.mj.nodes.types.MJDoubleConstantNode;
import ukr.lpu.cs.mj.nodes.types.MJIntegerConstantNode;
import ukr.lpu.cs.mj.nodes.types.MJStringConstantNode;

public final class RecursiveDescentParser {
    /** Maximum number of global variables per program */
    protected static final int MAX_GLOBALS = 32767;

    /** Maximum number of fields per class */
    protected static final int MAX_FIELDS = 32767;

    /** Maximum number of local variables per method */
    protected static final int MAX_LOCALS = 127;

    /** Last recognized token; */
    protected Token t;

    /** Lookahead token (not recognized).) */
    protected Token la;

    /** Shortcut to kind attribute of lookahead token (la). */
    protected Token.Kind sym;

    /** According scanner */
    public final RecursiveDescendScanner scanner;

    private MJMethodBodyNode currentMethod = null;

    public RecursiveDescentParser(RecursiveDescendScanner scanner) {
        this.scanner = scanner;
        // Avoid crash when 1st symbol has scanner error.
        la = new Token(Token.Kind.none, 1, 1);
        firstExpr = EnumSet.of(ident, number, charConst, minus, lpar, new_);
        firstStat = EnumSet.of(ident, semicolon, lbrace, break_, continue_, if_, print, read, return_, while_);
        firstMethodDecl = EnumSet.of(void_, ident);
    }

    public static enum CompOp {
        eq,
        ne,
        lt,
        le,
        gt,
        ge;
        public static CompOp invert(CompOp op) {
            switch (op) {
                case eq:
                    return ne;
                case ne:
                    return eq;
                case lt:
                    return ge;
                case le:
                    return gt;
                case gt:
                    return le;
                case ge:
                    return lt;
            }
            throw new IllegalArgumentException("Unexpected compare operator");
        }
    }

    private static enum Operands {
        B(1), // byte ( 8 bit signed)
        S(2), // short (16 bit signed)
        W(4); // word (32 bit signed)

        /** Size in bytes (8 bit) */
        public final int size;

        Operands(int size) {
            this.size = size;
        }
    }

    private static final Operands[] B = new Operands[]{Operands.B};
    private static final Operands[] S = new Operands[]{Operands.S};
    private static final Operands[] W = new Operands[]{Operands.W};
    private static final Operands[] BB = new Operands[]{Operands.B, Operands.B};

    public static enum OpCode {
        load(B), //
        load_0, //
        load_1, //
        load_2, //
        load_3, //
        store(B), //
        store_0, //
        store_1, //
        store_2, //
        store_3, //
        getstatic(S), //
        putstatic(S), //
        getfield(S), //
        putfield(S), //
        const_0, //
        const_1, //
        const_2, //
        const_3, //
        const_4, //
        const_5, //
        const_m1, //
        const_(W), //
        add, //
        sub, //
        mul, //
        div, //
        rem, //
        neg, //
        shl, //
        shr, //
        inc(BB), //
        new_(S), //
        newarray(B), //
        aload, //
        astore, //
        baload, //
        bastore, //
        arraylength, //
        pop, //
        dup, //
        dup2, //
        jmp(S), //
        jeq(S), //
        jne(S), //
        jlt(S), //
        jle(S), //
        jgt(S), //
        jge(S), //
        call(S), //
        return_, //
        enter(BB), //
        exit, //
        read, //
        print, //
        bread, //
        bprint, //
        trap(B), //
        nop;

        private final Operands[] ops;

        private OpCode(Operands... operands) {
            this.ops = operands;
        }

        protected Collection<Operands> getOps() {
            return Arrays.asList(ops);
        }

        public int numOps() {
            return ops.length;
        }

        public int getOpsSize() {
            int size = 0;
            for (Operands op : ops) {
                size += op.size;
            }
            return size;
        }

        public int code() {
            return ordinal() + 1;
        }

        public String cleanName() {
            String name = name();
            if (name.endsWith("_")) {
                name = name.substring(0, name.length() - 1);
            }
            return name;
        }

        public static OpCode get(int code) {
            if (code < 1 || code > values().length) {
                return null;
            }
            return values()[code - 1];
        }
    }

    /** Sets of starting tokens for some productions. */
    private EnumSet<Token.Kind> firstExpr, firstStat, firstMethodDecl;

    /** Reads ahead one symbol. */
    private void scan() {
        t = la;
        la = scanner.next();
        sym = la.kind;
    }

    /** Verifies symbol and reads ahead. */
    private void check(Token.Kind expected) {
        if (sym == expected) {
            scan();
        } else {
            throw new Error("Token " + expected + " excpeted");
        }
    }

    /**
     * Program = <br>
     * "program" ident <br>
     * { ConstDecl | VarDecl | ClassDecl } <br>
     * "{" { MethodDecl } "}" .
     */
    private MJProgramNode Program() {
        check(program);
        MJProgramNode prog = new MJProgramNode();
        check(ident);
        for (;;) {
            if (sym == final_) {
                prog.addVar(ConstDecl());
            } else if (sym == ident) {
                List<String> args = new ArrayList<>();
                prog.addVars(VarDecl(args), args.toArray(new String[args.size()]));
            } else if (sym == class_) {
                ClassDecl();
            } else {
                break;
            }
        }
        check(lbrace);
        for (;;) {
            if (sym == rbrace || sym == eof) {
                break;
            } else if (firstMethodDecl.contains(sym)) {
                MethodDecl(prog);
            }
        }
        check(rbrace);
        return prog;
    }

    /** ConstDecl = "final" Type ident "=" ( number | charConst ) ";" . */
    private MJSymbolNode ConstDecl() {
        check(final_);
        ValType type = Type();
        check(ident);
        MJSymbolNode constVal = MJNodeFactory.getSymbol(type, t.str);
        check(assign);
        if (sym == number) {
            scan();
            constVal.setResult(t.val);
        } else if (sym == doublenumber) {
            scan();
            constVal.setResult(t.dval);
        } else if (sym == stringConst) {
            scan();
            constVal.setResult(t.str);
        } else {
            throw new Error("Constant declaration");
        }
        constVal.startBeConstant();
        check(semicolon);
        return constVal;
    }

    /** VarDecl = Type ident { "," ident } ";" . */
    private ValType VarDecl(List<String> args) {
        ValType type = Type();
        check(ident);
        args.add(t.str);
        while (sym == comma) {
            scan();
            check(ident);
            args.add(t.str);
        }
        check(semicolon);
        return type;
    }

    /** ClassDecl = "class" ident "{" { VarDecl } "}" . */
    private void ClassDecl() {
        check(class_);
        check(ident);
        check(lbrace);
        while (sym == ident) {
            VarDecl(null);
        }
        check(rbrace);
    }

    /**
     * MethodDecl = <br>
     * ( Type | "void" ) ident "(" [ FormPars ] ")" <br>
     * ( ";" | { VarDecl } Block ) .
     */
    private void MethodDecl(MJProgramNode prog) {
        ValType retType = null;
        if (sym == ident) {
            retType = Type();
        } else if (sym == void_) {
            scan();
        } else {
            throw new Error("Method declaration");
        }
        check(ident);
        MJMethodBodyNode method = new MJMethodBodyNode(retType, t.str, prog);
        prog.addFunction(method);
        currentMethod = method;
        check(lpar);
        if (sym == ident) {
            FormPars();
        }
        check(rpar);
        while (sym == ident) {
            List<String> names = new ArrayList<>();
            method.addVars(VarDecl(names), names.toArray(new String[names.size()]));
        }
        method.create();
        method.setBody(Block());
        currentMethod = null;
    }

    /** FormPars = Type ident { "," Type ident } . */
    private void FormPars() {
        ValType _type = Type();
        check(ident);
        currentMethod.addArg(_type, t.str);
        while (sym == comma) {
            scan();
            _type = Type();
            check(ident);
            currentMethod.addArg(_type, t.str);
        }
    }

    /** Type = ident . */
    private ValType Type() {
        check(ident);
        return MJNodeFactory.getType(t.str);
        /*
         * if (sym == lbrack) { scan(); check(rbrack); }
         */
    }

    /** Status: OK */
    /** Block = "{" { Statement } "}" . */
    private MJBlockNode Block() {
        check(lbrace);
        MJBlockNode a = new MJBlockNode(Statements());
        check(rbrace);
        return a;
    }

    /** Status: OK */
    private MJStatementNode[] Statements() {
        List<MJStatementNode> list = new ArrayList<>();
        for (;;) {
            if (firstStat.contains(sym)) {
                list.add(Statement());
            } else {
                break;
            }
        }
        return list.toArray(new MJStatementNode[list.size()]);
    }

    /** Status: AVERAGE */
    /**
     * Statement = <br>
     * Designator ( Assignop Expr | ActPars | "++" | "--" ) ";" <br>
     * | "if" "(" Condition ")" Statement [ "else" Statement ] <br>
     * | "while" "(" Condition ")" Statement <br>
     * | "break" ";" <br>
     * | "return" [ Expr ] ";" <br>
     * | "read" "(" Designator ")" ";" <br>
     * | "print" "(" Expr [ comma number ] ")" ";" <br>
     * | Block <br>
     * | ";" .
     */
    private MJStatementNode Statement() {
        MJStatementNode res;
        switch (sym) {
            // ----- assignment, method call, in- or decrement
            // ----- Designator ( Assignop Expr | ActPars | "++" | "--" ) ";"
            case ident: {
                String name = Designator();
                // currentMethod.getVar(name);
                switch (sym) {
                    case assign:
                    case plusas:
                    case minusas:
                    case timesas:
                    case slashas:
                    case remas:
                        MJExpressionNode left = new MJVarRefNode(currentMethod, name);
                        OpCode code = Assignop();
                        res = MJNodeFactory.getAssignStatement(code, left, Expr(), new MJVarValueNode(currentMethod, name));
                        break;
                    case lpar:
                        MJMethodBodyNode method = currentMethod.getProgram().getFunction(name);
                        res = new MJMethodInvokeNode(method, ActPars());
                        break;
                    case pplus: {
                        scan();
                        res = MJIncrementStatementNodeGen.create(
                                        new MJVarRefNode(currentMethod, name),
                                        new MJVarValueNode(currentMethod, name));
                        break;
                    }
                    case mminus: {
                        scan();
                        res = MJDecrementStatementNodeGen.create(new MJVarRefNode(currentMethod, name), new MJVarValueNode(currentMethod, name));
                        break;
                    }
                    default:
                        throw new Error("Designator Follow");
                }
                check(semicolon);
                return res;
            }
            // break;
            // ----- "if" "(" Condition ")" Statement [ "else" Statement ]
            case if_: {
                scan();
                check(lpar);
                MJExpressionNode cond = Condition();
                check(rpar);
                MJStatementNode a = Statement();
                if (sym == else_) {
                    scan();
                    return MJIfNodeGen.create(a, Statement(), cond);
                }
                return MJIfNodeGen.create(a, cond);
            }
            // break;
            // ----- "while" "(" Condition ")" Statement
            case while_: {
                scan();
                check(lpar);
                MJExpressionNode cond = Condition();
                check(rpar);
                return new MJWhileStatement(cond, Statement());
                // break;
            }
            // ----- "break" ";"
            case break_:
                scan();
                check(semicolon);
                return MJBreakStatementNodeGen.create();
            // break;

            // ----- "break" ";"
            case continue_:
                scan();
                check(semicolon);
                return MJContinueStatementNodeGen.create();
            // break;
            // ----- "return" [ Expr ] ";"
            case return_:
                scan();
                MJExpressionNode ex = null;
                if (sym != semicolon) {
                    ex = Expr();
                    check(semicolon);
                }
                return new MJReturnStatement(ex);
            // break;
            // ----- "read" "(" Designator ")" ";"
            case read: {
                scan();
                check(lpar);
                String name = Designator();
                MJExpressionNode left = new MJVarRefNode(currentMethod, name);
                check(rpar);
                check(semicolon);
                return MJReadStatementNodeGen.create(left);
                // break;
            }
            // ----- "print" "(" Expr [ comma number ] ")" ";"
            case print:
                scan();
                check(lpar);
                MJExpressionNode a = Expr();
                /*
                 * if (sym == comma) { scan(); check(number); }
                 */
                check(rpar);
                check(semicolon);
                return MJPrintNodeGen.create(a);
            // break;
            case lbrace:
                return Block();
            // break;
            case semicolon:
                scan();
                break;
            default:
                throw new Error("Invalid start...");
        }
        return null;
    }

    /** Status: NOT OK */
    /** ActPars = "(" [ Expr { "," Expr } ] ")" . */
    private List<MJExpressionNode> ActPars() {
        List<MJExpressionNode> args = new ArrayList<>();
        check(lpar);
        if (firstExpr.contains(sym)) {
            for (;;) {
                args.add(Expr());
                if (sym == comma) {
                    scan();
                } else {
                    break;
                }
            }
        }
        check(rpar);
        return args;
    }

    /** Condition = CondTerm { "||" CondTerm } . */
    private MJExpressionNode Condition() {
        MJExpressionNode a = CondTerm();
        while (sym == or) {
            scan();
            a = MJOrExpressionNodeGen.create(a, CondTerm());
        }
        return a;
    }

    /** Status: OK */
    /** CondTerm = CondFact { "&&" CondFact } . */
    private MJExpressionNode CondTerm() {
        MJExpressionNode a = CondFact();
        while (sym == and) {
            scan();
            a = MJAndExpressionNodeGen.create(a, CondFact());
        }
        return a;
    }

    /** Status: OK */
    /** CondFact = Expr Relop Expr . */
    private MJExpressionNode CondFact() {
        MJExpressionNode a = Expr();
        return MJNodeFactory.getCompExpression(Relop(), a, Expr());
    }

    /** Status: OK */
    /** Expr = [ "-" ] Term { Addop Term } . */
    private MJExpressionNode Expr() {
        MJExpressionNode a;
        if (sym == minus) {
            scan();
            a = MJNegateNodeGen.create(Term());
        } else {
            a = Term();
        }
        while (sym == plus || sym == minus) {
            OpCode opCode = Addop();
            a = MJNodeFactory.getOpExpression(opCode, a, Term());
        }
        return a;
    }

    /** Status: OK */
    /** Term = Factor { Mulop Factor } . */
    private MJExpressionNode Term() {
        MJExpressionNode a = Factor();
        while (sym == times || sym == slash || sym == rem) {
            OpCode opCode = Mulop();
            a = MJNodeFactory.getOpExpression(opCode, a, Factor());
        }
        return a;
    }

    /** Status: AVERAGE */
    /**
     * Factor = <br>
     * Designator [ ActPars ] <br>
     * | number <br>
     * | charConst <br>
     * | "new" ident [ "[" Expr "]" ] <br>
     * | "(" Expr ")" .
     */
    private MJExpressionNode Factor() {
        switch (sym) {
            case ident:
                String name = Designator();
                if (sym == lpar) {
                    MJMethodBodyNode method = currentMethod.getProgram().getFunction(name);
                    MJMethodInvokeNode invoke = new MJMethodInvokeNode(method, ActPars());
                    return invoke;
                } else {
                    return new MJVarValueNode(currentMethod, name);
                }
                // break;
            case number:
                scan();
                return new MJIntegerConstantNode(t.val);
            // break;
            case doublenumber:
                scan();
                return new MJDoubleConstantNode(t.dval);
            // break;
            case stringConst:
                scan();
                return new MJStringConstantNode(t.str);
            // break;
            case charConst:
                scan();
                return new MJCharConstantNode((char) t.val);
            // break;
            case new_:
                scan();
                check(ident);
                if (sym == lbrack) {
                    scan();
                    Expr();
                    check(rbrack);
                }
                break;
            case lpar:
                scan();
                MJExpressionNode ret = Expr();
                check(rpar);
                return ret;
            // break;
            default:
                throw new Error("Invalid fact");
        }
        return null;
    }

    /** Designator = ident { "." ident | "[" Expr "]" } . */
    private String Designator() {
        check(ident);
        String name = t.str;
        while (sym == period || sym == lbrack) {
            if (sym == period) {
                scan();
                check(ident);
            } else {
                scan();
                Expr();
                check(rbrack);
            }
        }
        return name;
    }

    /** Assignop = "=" | "+=" | "-=" | "*=" | "/=" | "%=" . */
    private OpCode Assignop() {
        OpCode op = OpCode.store;
        switch (sym) {
            case assign:
                op = OpCode.store;
                scan();
                break;
            case plusas:
                op = OpCode.add;
                scan();
                break;
            case minusas:
                op = OpCode.sub;
                scan();
                break;
            case timesas:
                op = OpCode.mul;
                scan();
                break;
            case slashas:
                op = OpCode.div;
                scan();
                break;
            case remas:
                op = OpCode.rem;
                scan();
                break;
            default:
                throw new Error("invalid assign operation");
        }
        return op;
    }

    /** Relop = "==" | "!=" | ">" | ">=" | "<" | "<=" . */
    private CompOp Relop() {
        CompOp op = CompOp.eq;
        switch (sym) {
            case eql:
                op = CompOp.eq;
                scan();
                break;
            case neq:
                op = CompOp.ne;
                scan();
                break;
            case lss:
                op = CompOp.lt;
                scan();
                break;
            case leq:
                op = CompOp.le;
                scan();
                break;
            case gtr:
                op = CompOp.gt;
                scan();
                break;
            case geq:
                op = CompOp.ge;
                scan();
                break;
            default:
                throw new Error("invalid rel operation");
        }
        return op;
    }

    /** Addop = "+" | "-" . */
    private OpCode Addop() {
        OpCode op = OpCode.add;
        switch (sym) {
            case plus:
                op = OpCode.add;
                scan();
                break;
            case minus:
                op = OpCode.sub;
                scan();
                break;
            default:
                throw new Error("invalid add operation");
        }
        return op;
    }

    /** Mulop = "*" | "/" | "%" . */
    private OpCode Mulop() {
        OpCode op = OpCode.mul;
        switch (sym) {
            case times:
                op = OpCode.mul;
                scan();
                break;
            case slash:
                op = OpCode.div;
                scan();
                break;
            case rem:
                op = OpCode.rem;
                scan();
                break;
            default:
                throw new Error("invalid mul operation");
        }
        return op;
    }

    public RootCallTarget parse() {
        scan(); // scan first symbol
        RootCallTarget call = Truffle.getRuntime().createCallTarget(Program()); // start analysis
        check(eof);
        return call;
    }
}