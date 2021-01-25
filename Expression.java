package edu.uic.cs474.a4;

import static edu.uic.cs474.a4.Environment.*;

public abstract class Expression {

    public static class IntConstant extends Expression {
        final int c;

        public IntConstant(int c) {
            this.c = c;
        }
    }

    public static class BooleanConstant extends Expression {
        final boolean c;

        public BooleanConstant(boolean c) {
            this.c = c;
        }
    }

    public static class BinaryOperationExpression extends Expression {
        enum Operator { PLUS, MINUS, TIMES, DIV}

        final Expression left, right;
        final BinaryOperationExpression.Operator operator;

        public BinaryOperationExpression(BinaryOperationExpression.Operator op, Expression left, Expression right) {
            this.left = left;
            this.right = right;
            this.operator = op;
        }
    }

    //    if (condition) {
//        thenSide;
//    } else {
//        elseSide;
//    }
    public static class IfExpression extends Expression {
        final Expression condition, thenSide, elseSide;

        public IfExpression(Expression condition, Expression thenSide, Expression elseSide) {
            this.condition = condition;
            this.thenSide = thenSide;
            this.elseSide = elseSide;
        }
    }

    public static class ComparisonExpression extends Expression {
        enum Type { EQ }

        final ComparisonExpression.Type type;
        final Expression left, right;

        public ComparisonExpression(ComparisonExpression.Type type, Expression left, Expression right) {
            this.type = type;
            this.left = left;
            this.right = right;
        }
    }

    public static class LetExpression extends Expression {
        final Name variable;
        final Expression value;
        final Expression body;

        public LetExpression(Name variable, Expression value, Expression body) {
            this.variable = variable;
            this.value = value;
            this.body = body;
        }
    }

    public static class VariableExpression extends Expression {
        final Name variable;

        public VariableExpression(Name variable) {
            this.variable = variable;
        }
    }

    // name(arg1, arg2)
    // safeDivision(474 , 0)
    // fact(10)
    public static class FunctionCallExpression extends Expression {
        final Expression function;
        final Expression[] actualArguments;

        public FunctionCallExpression(Expression function, Expression... actualArguments) {
            this.function = function;
            this.actualArguments = actualArguments;
        }
    }

    // function_name(arg1, arg2, arg3) { body }
    // formal name
    public static class FunctionDeclarationExpression extends Expression {
        final Name name;
        final Expression body;
        final Name[] formalNamesOfArguments;

        public FunctionDeclarationExpression(Name name, Expression body, Name... formalNamesOfArguments) {
            this.name = name;
            this.body = body;
            this.formalNamesOfArguments = formalNamesOfArguments;
        }
    }

    public static class LambdaDeclarationExpression extends Expression {
        final Expression body;
        final Name[] formalNamesOfArguments;

        public LambdaDeclarationExpression(Expression body, Name... formalNamesOfArguments) {
            this.body = body;
            this.formalNamesOfArguments = formalNamesOfArguments;
        }
    }
}
