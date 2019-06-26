package ukr.lpu.cs.mj;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.oracle.truffle.api.RootCallTarget;

import ukr.lpu.cs.mj.parser.RecursiveDescendScanner;
import ukr.lpu.cs.mj.parser.RecursiveDescentParser;

public class MJRuntime {
    public static void main(String[] args) {
        parseRD(mjTestRD);
    }

    static String mjTestRD = ""//
                    + "program Sample { "//
                    + "void main() double i;{ \n"//
                    + "read(i);  "//
                    + "print(i);\n"//
                    + "}\n" //
                    + "}\n";

    static String mjReturnRD = "program P int i;{"//
                    + "             int foo() { return \"Hello\"; }" //
                    + "             void main() { "//
                    + "                 i = foo();"//
                    + "                 print(i);"//
                    + "             }"//
                    + "}";

    static String mjProgramRD = ""//
                    + "program Sample { "//
                    + "void main() int i; int j; { \n"//
                    + "                 print(0);"//
                    + "                 print(12); \n" //
                    + "                 i = 3;\n"//
                    + "                 print(i);\n"//
                    + "                 print(i+12);\n"//
                    + "                 j=12;\n"//
                    + "                 print(i+j+12);\n"//
                    + "         }\n"//
                    + "}";

    static String whileLoopRD = "program P {"//
                    + "             void foo(int i,int j) { print(i+j);}" //
                    + "             void main () int i;{ "//
                    + "                 i = 0; "//
                    + "                 while(i<10) {"//
                    + "                     print(i); "//
                    + "                     i=i+1;"//
                    + "                     foo(i,2);" //
                    + "                 }"//
                    + "             }"//
                    + "}";
    static String ifProgram = "program P {"//
                    + "             void foo(int i,int j) {"//
                    + "                 if(i>j) {"//
                    + "                     print(i);" //
                    + "                 }else {"//
                    + "                     print(j);"//
                    + "                 }"//
                    + "                 print(0);"//
                    + "             }" //
                    + "             void main () int i;{ "//
                    + "                 i=0; "//
                    + "                 while(i<10) {"//
                    + "                     i=i+1;"//
                    + "                     foo(i,5);" //
                    + "                 }"//
                    + "             }"//
                    + "}";

    static String divAlgorithm = "program DivAlgorithm {"//
                    + "int abs(int x){" + "if(x<0){return -x;}" + "return x;" + "}             " + "int flipSign(int a) int neg;int tmp; int tmpA; {" //
                    + "                 neg = 0;"//
                    + "                 tmp = 0;" //
                    + "                 tmpA = a;"//
                    + "                 if(a < 0){"//
                    + "                     tmp = 1;"//
                    + "                 } else {"//
                    + "                     tmp = -1;"//
                    + "                 }" //
                    + "                 while(tmpA != 0) {"//
                    + "                     neg = neg + tmp;"//
                    + "                     tmpA = tmpA + tmp;"//
                    // + " print(tmpA);"//
                    // + " print(neg);"//
                    + "                 }"//
                    + "                 return neg;"//
                    + "             }"//
                    + "             int sub(int a,int b) {"//
                    + "                 return a + flipSign(b);"//
                    + "             }"//
                    + "             int mul(int a,int b) int sum;int i; {"//
                    + "                 if(a<b) {"//
                    + "                     return mul(b,a);"//
                    + "                 }"//
                    + "                 sum = 0;"//
                    + "                 i =abs(b);"//
                    + "                 while(i>0) {"//
                    + "                     sum = sum +a;"//
                    + "                     i = i-1;"//
                    + "                 }"//
                    + "                 if(b < 0){"//
                    + "                     sum = flipSign(sum);"//
                    + "                 }"//
                    + "                 return sum;"//
                    + "             }" //
                    + "             void main (){ " //
                    + "                 print(mul(-1245,88744));"//
                    + "             }"//
                    + "}";

    static void parseRD(String code) {
        InputStream is = new ByteArrayInputStream(code.getBytes());
        RecursiveDescendScanner scanner = new RecursiveDescendScanner(new InputStreamReader(is));
        RecursiveDescentParser parser = new RecursiveDescentParser(scanner);
        RootCallTarget call = parser.parse();
        call.call();
    }
}
