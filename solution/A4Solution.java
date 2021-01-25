package edu.uic.cs474.a4.solution;

import edu.uic.cs474.a4.*;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

import static edu.uic.cs474.a4.Environment.*;
import static edu.uic.cs474.a4.Assignment4.*;
import static edu.uic.cs474.a4.Expression.*;
import static edu.uic.cs474.a4.Value.*;

public class A4Solution extends Assignment4 {

    public Optional<Method> getMethod(ClassValue cv, Name name, Environment e) {
        Method[] methods = cv.methods;
        int i;
        for(i=0; i<methods.length; i++) {
            if(name.equals(methods[i].name)) {
                return Optional.of(methods[i]);
            }
        }
        if(cv.superName.isPresent()) {
            Value parent_v = e.lookup(cv.superName.get());
            if(parent_v instanceof ClassValue) {
                ClassValue parent_class = (ClassValue) parent_v;
                for(i=0; i<parent_class.methods.length; i++) {
                    if(name.equals(parent_class.methods[i].name)){
                        return Optional.of(parent_class.methods[i]);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Expression> findField(Field fields[], Name field_name) {
        Expression ex;
        for (int i=0; i< fields.length; i++) {
            if(field_name.equals(fields[i].name)) {
                return Optional.of(fields[i].initializer);
            }
        }
        return Optional.empty();
    }

    public Optional<Expression> checkParentForField(ClassValue cv, Environment e, Name name ) {


        if(cv.superName.isPresent()) {
            Value v = e.lookup(cv.superName.get());
            if(v instanceof ClassValue) {
                ClassValue pv = (ClassValue) v;

                Optional<Expression> ex_op = findField(pv.fields, name);
                return ex_op;
            }
            ;
        }
    return Optional.empty();
    }



    @Override
    public Value evaluate(Expression c, Environment e) {
        switch(c.getClass().getSimpleName()) {
            case "InstanceOfExpression" : {
                InstanceOfExpression ioe = (InstanceOfExpression) c;

                Value target = evaluate(ioe.target, e);
                if(target instanceof ObjectValue) {

                    ObjectValue obj = (ObjectValue) target;

                    Value v = e.lookup(obj.name);
                    if(v instanceof ClassValue) {
                        ClassValue cv = (ClassValue) v;
                        if(cv.superName.isPresent()) {
                            Name parent_name = cv.superName.get();
                            if(parent_name.equals(ioe.className)) {
                                return new BooleanValue(true);
                            }
                        }
                    }

                    if(ioe.className.equals(obj.name)) {
                        return new BooleanValue(true);
                    }
                    else {
                        return new BooleanValue(false);
                    }

                }
                if(target instanceof IntValue) {
                    return new BooleanValue(false);
                }
                if(target instanceof BooleanValue) {
                    return new BooleanValue(false);
                }

                throw new Error("Not implemented");

            }
            case "ClassDefExpression" : {
                ClassDefExpression def = (ClassDefExpression) c;


                ClassValue ret = new ClassValue(def.className, def.superName, def.methods, def.body, def.fields);

                return evaluate(def.body, e.bind(def.className, ret));
            }
            case "NewExpression" : {
                NewExpression ne = (NewExpression) c;
                

                return new ObjectValue(ne.className);
            }
            case "ReadFieldExpression" : {
                ReadFieldExpression re = (ReadFieldExpression) c;
                Value v1 = evaluate(re.receiver, e);
                if(v1 instanceof ObjectValue) {
                    ObjectValue v2 = (ObjectValue) v1;
                    Value v3 = e.lookup(v2.name);

                    if(v3 instanceof ClassValue) {
                        ClassValue cv = (ClassValue) v3;

                        // if parent class exists, check them for the field
                        Optional<Expression> parent_ex_op = checkParentForField(cv, e, re.fieldName);
                        if(parent_ex_op.isPresent()) {
                            return evaluate(parent_ex_op.get(), e);
                        }

                        // check base class for field
                        Optional<Expression> ex_op = findField(cv.fields, re.fieldName);
                        if(ex_op.isPresent()) {
                            return evaluate(ex_op.get(), e);
                        }

                    }
                }

                throw new Error("Not implemented");
            }
            case "WriteFieldExpression" : {
                WriteFieldExpression wfe = (WriteFieldExpression) c;


                return evaluate(wfe.newValue, e);





            }
            case "CallMethodExpression" : {
                CallMethodExpression cme = (CallMethodExpression) c;
                Value receiver_value = evaluate(cme.receiver, e);
                ObjectValue receiver_obj;

                Value[] args_value = new Value[cme.arguments.length];
                for(int i=0; i<cme.arguments.length; i++) {
                    args_value[i] = evaluate(cme.arguments[i], e);

                }

                if(receiver_value instanceof ObjectValue) {
                    receiver_obj = (ObjectValue) receiver_value;
                    Value re_v = e.lookup(receiver_obj.name);
                    if(re_v instanceof ClassValue) {
                        ClassValue receiver_class_value = (ClassValue) re_v;
                        Optional<Method> method_opt = getMethod(receiver_class_value, cme.methodName, e );
                        if(method_opt.isPresent()) {

                            for(int j=0; j< method_opt.get().arguments.length; j++) {


                                e = e.bind(method_opt.get().arguments[j], args_value[j]);

                            }

                            e = e.bind(new Name("this"), receiver_value );
                            Value ret = evaluate(method_opt.get().body, e);
                            return ret;
                        }
                    }
                }




            }
            default: {
                return Interpreter.evaluate(c, e, this);
            }
        }

    }

    public static class ClassValue extends Value {

        public final Name className;
        public final Optional<Name> superName;
        public  Field[] fields;
        public final Method[] methods;
        public final Expression body;

        public ClassValue(Name classname, Optional<Name> superName,
                          Method[] methods, Expression body, Field[] fields) {
            this.className = classname;
            this.superName = superName;
            this.methods = methods;
            this.body = body;
            this.fields = fields;
        }

    }
    public static class ObjectValue extends Value {
        public final Name name;

        public ObjectValue(Name name) {
            this.name = name;
        }

    }
}

