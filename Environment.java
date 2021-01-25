package edu.uic.cs474.a4;

import java.util.Objects;

public abstract class Environment {
    Interpreter.List<Binding> bindings;

    public abstract Environment bind(Name name, Value value);

    public Value lookup(Name name) {
        return lookup(name, bindings);
    }

    private Value lookup(Name name, Interpreter.List<Binding> search) {
        if (search == Interpreter.List.EMPTY)
            throw new Error("Name " + name + " not found in environment");

        if (search.item.name.equals(name))
            return search.item.value;

        return lookup(name, search.rest);
    }

    @Override
    public String toString() {
        return "Environment{" + bindings + '}';
    }


    public static class DynamicScopedEnvironment extends Environment {
        public Environment bind(Name name, Value value) {
            Binding b = new Binding(name, value);
            bindings = new Interpreter.List<>(b, bindings);

            return this;
        }
    }

    public static class LexicalScopedEnvironment extends Environment {
        public LexicalScopedEnvironment() {
            bindings = Interpreter.List.EMPTY;
        }

        private LexicalScopedEnvironment(Binding binding, LexicalScopedEnvironment nextInScope) {
            bindings = new Interpreter.List<>(binding, nextInScope.bindings);
        }

        public Environment bind(Name name, Value value) {
            Binding b = new Binding(name, value);
            return new LexicalScopedEnvironment(b, this);
        }
    }


    public static class Name {
        final String theName;

        public Name(String theName) {
            this.theName = theName;
        }

        @Override
        public String toString() {
            return theName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Name name = (Name) o;
            return Objects.equals(theName, name.theName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(theName);
        }
    }

    public static class Binding {
        public final Name name;
        public Value value; // ugh, horrible hack

        public Binding(Name name, Value value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + "name=" + name + ", value=" + value + '}';
        }
    }

}
