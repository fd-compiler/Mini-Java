package Main;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import Parser.*;
import ErrorDetection.*;

public class test {
    static public void main(String []args) throws Exception{
        String inputFile = "C:\\Users\\69411\\Desktop\\ljl2.expr";
        CharStream input = CharStreams.fromFileName(inputFile);

        miniJavaLexer lexer = new miniJavaLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        miniJavaParser parser = new miniJavaParser(tokens);
        ParseTree tree = parser.goal();

        //miniJavaBaseVisitor<Integer> eval = new miniJavaBaseVisitor<>();
        astGenerator eval = new astGenerator();
        eval.visit(tree);
        ErrorDetector detector = new ErrorDetector(eval.root);
        detector.checkClasses();
        detector.recursiveCheck();
        RuntimeInterpreter interpreter = new RuntimeInterpreter(eval.root);
        interpreter.interpret();
    }
}