package edu.uic.cs474.a4;

import java.util.Arrays;

import static edu.uic.cs474.a4.Expression.*;
import static edu.uic.cs474.a4.Value.*;
import static edu.uic.cs474.a4.Environment.*;

public class Interpreter {

    public static Value evaluate(Expression c, Environment e, Assignment4 a4) {
        switch (c.getClass().getSimpleName()) {
            case "IntConstant": {
                IntConstant cst = (IntConstant) c;
                IntValue ret = new IntValue(cst.c);
                return ret;
            }
            case "BooleanConstant": {
                BooleanConstant cst = (BooleanConstant) c;
                BooleanValue ret = new BooleanValue(cst.c);
                return ret;
            }
            case "BinaryOperationExpression": {
                BinaryOperationExpression op = (BinaryOperationExpression) c;
                IntValue leftValue = (IntValue) a4.evaluate(op.left, e);
                IntValue rightValue = (IntValue) a4.evaluate(op.right, e);
                IntValue result;

                switch (op.operator) {
                    case PLUS:
                        result = new IntValue(leftValue.v + rightValue.v);
                        break;
                    case MINUS:
                        result = new IntValue(leftValue.v - rightValue.v);
                        break;
                    case TIMES:
                        result = new IntValue(leftValue.v * rightValue.v);
                        break;
                    case DIV:
                        result = new IntValue(leftValue.v / rightValue.v);
                        break;
                    default:
                        throw new Error("Unknown binary operator:" + op.operator);
                }

                return result;
            }
            case "ComparisonExpression" : {
                ComparisonExpression comp = (ComparisonExpression) c;
                Value left  = a4.evaluate(comp.left, e);
                Value right = a4.evaluate(comp.right, e);

                switch (comp.type) {
                    case EQ:
                        boolean result = ((((IntValue)left).v) == (((IntValue)right).v));
                        return new BooleanValue(result);
                    default:
                        throw new Error("Unknown comparison type: " + comp.type);
                }
            }
            case "IfExpression" : {
                IfExpression ife = (IfExpression) c;
                BooleanValue cond = (BooleanValue) a4.evaluate(ife.condition, e);

                if (cond.b)
                    return a4.evaluate(ife.thenSide, e);
                else
                    return a4.evaluate(ife.elseSide, e);
            }
            case "LetExpression": {
                // Bind the name to the value of evaluating the initialization expression
                LetExpression let = (LetExpression) c;
                Value v = a4.evaluate(let.value, e);

                Environment newE = e.bind(let.variable, v);

                return a4.evaluate(let.body, newE);
            }
            case "VariableExpression" : {
                // Check what is the value bound to the name in the variable expression
                VariableExpression var = (VariableExpression) c;

                return e.lookup(var.variable);
            }
            case "FunctionCallExpression" : {
                FunctionCallExpression call = (FunctionCallExpression) c;

                FunctionValue f = (FunctionValue) a4.evaluate(call.function, e);

                Value[] actualValues = Arrays.stream(call.actualArguments).map(exp -> a4.evaluate(exp, e)).toArray(Value[]::new);

                Value ret = f.theFunction.apply(actualValues);

                return ret;
            }
            case "LambdaDeclarationExpression" : {
                LambdaDeclarationExpression decl = (LambdaDeclarationExpression) c;

                FunctionValue ret = new FunctionValue(actualArguments -> {
                    Environment evaluationEnvironment = e;

                    for (int i = 0 ; i < decl.formalNamesOfArguments.length ; i++) {
                        Value actualArgumentValue = actualArguments[i];
                        evaluationEnvironment = evaluationEnvironment.bind(decl.formalNamesOfArguments[i], actualArgumentValue);
                    }

                    return a4.evaluate(decl.body, evaluationEnvironment);
                });

                return ret;
            }
            case "FunctionDeclarationExpression" : {
                FunctionDeclarationExpression decl = (FunctionDeclarationExpression) c;

                Environment envThatKnowsTheNameOfTheFunction = e.bind(decl.name, null); // horrible hack

                FunctionValue ret = new FunctionValue(actualArguments -> {
                    Environment evaluationEnvironment = envThatKnowsTheNameOfTheFunction;

                    for (int i = 0 ; i < decl.formalNamesOfArguments.length ; i++) {
                        Value actualArgumentValue = actualArguments[i];
                        evaluationEnvironment = evaluationEnvironment.bind(decl.formalNamesOfArguments[i], actualArgumentValue);
                    }

                    return a4.evaluate(decl.body, evaluationEnvironment);
                });

                envThatKnowsTheNameOfTheFunction.bindings.item.value = ret; // horrible hack continues

                return ret;
            }
            default: {
                throw new Error("Unknown expression: " + c.getClass().getSimpleName());
            }
        }
    }

//    Function findFunction(Name name, List<Function> toSearch) {
//        if (toSearch == List.EMPTY)
//            throw new Error("No such function: " + name);
//
//        if (toSearch.item.name.equals(name))
//            return toSearch.item;
//
//        return findFunction(name, toSearch.rest);
//    }

    public static class List<T> {
        public final T item;
        public final List<T> rest;

        public final static List EMPTY = new List();

        private List() {
            this.item = null;
            this.rest = null;
        }

        public List(T item, List<T> rest) {
            this.item = item;
            this.rest = rest;
        }

        @Override
        public String toString() {
            if (this != EMPTY)
                return " " + item + rest.toString();
            else
                return " ";
        }
    }
}
