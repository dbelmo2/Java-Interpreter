# Java-Interpreter
Interpreter implementation written in Java

Description
Interpreter with object-oriented concepts. Interpreter can evaluate the following expressions:


• ClassDefExpression: Introduces a new class, that can be used in the body of the expression.
This expression is very similar to the LetExpression seen in class.
  o A class is defined by its name, an array of fields, and an array of methods. A class may have
    a superclass.
    
  o A field is defined by its name and initializer expression, that evaluates to the initial value of
    the field.
    
  o A method is defined by its name, the name of its arguments, and its body.
  
  o Expressions in the body can refer to the class introduced by the ClassDefExpression.

• NewExpression: Creates a new instance of a known class, by using the name of the class.
  o New instances hold fields with the initial value obtained by executing each field initializer
    expression, as defined above.
    
  o Takes:
    ▪ The name of the class to create a new object of.
    
  o Evaluates to a new value that is an instance of the specified class.

• InstanceOfExpression: Checks if an object is an instance of a given class.
  o Takes:
    ▪ An expression that evaluates to the object to check.
    ▪ The name of the class to check.
    
  o Evaluates to true if the object is an instance of the given class, false otherwise.

• ReadFieldExpression: Reads the value of a field on the given object.
  o Takes:
    ▪ An expression that evaluates to the object from which to read the field.
    ▪ The name of the field to read.
    
  o Evaluates to the value held by the field on the provided object.
    ▪ This value is either
    • The initial value, as provided in the class declaration, if the field has not
      been written yet on the provided object.
    • The value of the last write made to that field on that object.

• WriteFieldExpression: Writes the value of a field on the given object
  o Takes:
    ▪ An expression that evaluates to the object from which to read the field.
    ▪ The name of the field to read.
    ▪ An expression that evaluates to the new value to write.
    
  o Evaluates to the new value written to the field.
    ▪ Besides returning the new value written, this expression also updates the value of
      the field in the provided object. Later ReadFieldExpression return this value.
    • CallMethodExpression: Invokes a method on a given object.
      
  o Takes:
    ▪ An expression that evaluates to the object on which to invoke the method (i.e., the
      receiver).
    ▪ The name of the method to invoke.
    ▪ An array of expressions that evaluate to the value of the arguments to pass to the
    method.
    
o Evaluates to the value resulting of calling the method on the receiver object.
