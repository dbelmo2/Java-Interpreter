package edu.uic.cs474.a4;

import edu.uic.cs474.a4.solution.A4Solution;

import java.util.Optional;

import static edu.uic.cs474.a4.Environment.*;

public abstract class Assignment4 {

    public static Assignment4 getSolution() {
        return new A4Solution();
    }

    public abstract Value evaluate(Expression c, Environment e);

    // Fields have name and an initializer
    public static class Field {
        public final Name name;
        public final Expression initializer;

        public Field(Name name, Expression initializer) {
            this.name = name;
            this.initializer = initializer;
        }
    }

    // Methods have a name, argument names, and a body
    public static class Method {
        public final Name name;
        public final Name[] arguments;
        public final Expression body;

        public Method(Name name, Name[] arguments, Expression body) {
            this.name = name;
            this.arguments = arguments;
            this.body = body;
        }
    }

    // New expressions to implement

    public static class InstanceOfExpression extends Expression {
        public final Name className;
        public final Expression target;

        public InstanceOfExpression(Name className, Expression target) {
            this.className = className;
            this.target = target;
        }
    }

    public static class ClassDefExpression extends Expression {
        public final Name className;
        public final Optional<Name> superName;
        public final Field[] fields;
        public final Method[] methods;
        public final Expression body;

        public ClassDefExpression(Name className, Optional<Name> superName, Field[] fields, Method[] methods, Expression body) {
            this.className = className;
            this.superName = superName;
            this.fields = fields;
            this.methods = methods;
            this.body = body;
        }
    }

    public static class NewExpression extends Expression {
        public final Name className;

        public NewExpression(Name className) {
            this.className = className;
        }
    }

    public static class ReadFieldExpression extends Expression {
        public final Expression receiver;
        public final Name fieldName;

        public ReadFieldExpression(Expression receiver, Name fieldName) {
            this.receiver = receiver;
            this.fieldName = fieldName;
        }
    }

    public static class WriteFieldExpression extends Expression {
        public final Expression receiver;
        public final Name fieldName;
        public final Expression newValue;

        public WriteFieldExpression(Expression receiver, Name fieldName, Expression newValue) {
            this.receiver = receiver;
            this.fieldName = fieldName;
            this.newValue = newValue;
        }
    }

    public static class CallMethodExpression extends Expression {
        public final Expression receiver;
        public final Name methodName;
        public final Expression[] arguments;

        public CallMethodExpression(Expression receiver, Name methodName, Expression ... arguments) {
            this.receiver = receiver;
            this.methodName = methodName;
            this.arguments = arguments;
        }
    }
}
