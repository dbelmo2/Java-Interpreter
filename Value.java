package edu.uic.cs474.a4;

import java.util.Objects;

public abstract class Value {

    public static class IntValue extends Value {
        final int v;

        public IntValue(int v) {
            this.v = v;
        }

        @Override
        public String toString() {
            return "IntValue{" + "v=" + v + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            IntValue intValue = (IntValue) o;
            return v == intValue.v;
        }

        @Override
        public int hashCode() {
            return Objects.hash(v);
        }
    }

    public static class BooleanValue extends Value {
        final boolean b;

        public BooleanValue(boolean b) {
            this.b = b;
        }

        @Override
        public String toString() {
            return "BooleanValue{" + "b=" + b + '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BooleanValue that = (BooleanValue) o;
            return b == that.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(b);
        }
    }

    public static class FunctionValue extends Value {
        final java.util.function.Function<Value[], Value> theFunction;

        public FunctionValue(java.util.function.Function<Value[], Value> theFunction) {
            this.theFunction = theFunction;
        }
    }

}
