package edu.uic.cs474.a4;

public abstract class Programs {
    // 474
    static Expression p1 = new Expression.IntConstant(474);

    // 400 + 74
    static Expression p2 = new Expression.BinaryOperationExpression(
            Expression.BinaryOperationExpression.Operator.PLUS,
            new Expression.IntConstant(400),
            new Expression.IntConstant(74)
    );

    // 400 + (70 + 4)
    static Expression p3 = new Expression.BinaryOperationExpression(
            Expression.BinaryOperationExpression.Operator.PLUS,
            new Expression.IntConstant(400),
            new Expression.BinaryOperationExpression(
                    Expression.BinaryOperationExpression.Operator.PLUS,
                    new Expression.IntConstant(70),
                    new Expression.IntConstant(4)
            )
    );

    // 400 + true
    static Expression p4 = new Expression.BinaryOperationExpression(
            Expression.BinaryOperationExpression.Operator.PLUS,
            new Expression.IntConstant(400),
            new Expression.BooleanConstant(true)
    );

    // if ( (474 - (400 + 74)) == 0) 0 else (1 / (474 - (400 + 74)))
    static Expression p5 = new Expression.IfExpression(
            new Expression.ComparisonExpression(
                    Expression.ComparisonExpression.Type.EQ,
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.MINUS,
                            new Expression.IntConstant(474),
                            new Expression.BinaryOperationExpression(
                                    Expression.BinaryOperationExpression.Operator.PLUS,
                                    new Expression.IntConstant(400),
                                    new Expression.IntConstant(74)
                            )
                    ),
                    new Expression.IntConstant(0)
            )
            ,
            new Expression.IntConstant(0),
            new Expression.BinaryOperationExpression(
                    Expression.BinaryOperationExpression.Operator.DIV,
                    new Expression.IntConstant(1),
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.MINUS,
                            new Expression.IntConstant(474),
                            new Expression.BinaryOperationExpression(
                                    Expression.BinaryOperationExpression.Operator.PLUS,
                                    new Expression.IntConstant(400),
                                    new Expression.IntConstant(74)
                            )
                    )
            )
    );

    // { let operand = (474 - (400 + 74));
    //     if (operand == 0) 0 else 474 / operand;
    // }
    static Expression p6 = new Expression.LetExpression(
            new Environment.Name("operand"),
            new Expression.BinaryOperationExpression(
                    Expression.BinaryOperationExpression.Operator.MINUS,
                    new Expression.IntConstant(474),
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.PLUS,
                            new Expression.IntConstant(400),
                            new Expression.IntConstant(74)
                    )
            ),
            new Expression.IfExpression(
                    new Expression.ComparisonExpression(
                            Expression.ComparisonExpression.Type.EQ,
                            new Expression.VariableExpression(new Environment.Name("operand")),
                            new Expression.IntConstant(0)
                    ),
                    new Expression.IntConstant(0),
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.DIV,
                            new Expression.IntConstant(474),
                            new Expression.VariableExpression(new Environment.Name("operand"))
                    )
            )
    );

    // {
    //   let x = 10
    //   { let x = 20
    //     x -> Always 20
    //   }
    //   + x -> 10 on lexical scoping, 20 on dynamic scoping
    // }
    // Lexical scoping = 30
    // Dynamic scoping = 40
    static Expression p7 = new Expression.LetExpression(
            new Environment.Name("x"),
            new Expression.IntConstant(10),
            new Expression.BinaryOperationExpression(
                    Expression.BinaryOperationExpression.Operator.PLUS,
                    new Expression.LetExpression(
                            new Environment.Name("x"),
                            new Expression.IntConstant(20),
                            new Expression.VariableExpression(new Environment.Name("x"))
                    ),
                    new Expression.VariableExpression(new Environment.Name("x"))
            )
    );

    // safeDivision(474, (474 - (400 + 74)))
    static Expression p8 = new Expression.FunctionCallExpression(
            new Expression.VariableExpression(new Environment.Name("safeDivision")),
            new Expression.IntConstant(474),
            new Expression.BinaryOperationExpression(
                    Expression.BinaryOperationExpression.Operator.MINUS,
                    new Expression.IntConstant(474),
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.PLUS,
                            new Expression.IntConstant(400),
                            new Expression.IntConstant(74)
                    )
            )
    );


    // fact(5)
    static Expression p9 = new Expression.FunctionCallExpression(
            new Expression.VariableExpression(new Environment.Name("fact")),
            new Expression.IntConstant(5)
    );

    // { let safeDivision = function(top, bot) { if (bot == 0) then 0 else top/bot }
    //      safeDivision(474, (474 - (400 + 74)))
    // }
    static Expression p10 = new Expression.LetExpression(
            new Environment.Name("safeDivision"),
            new Expression.LambdaDeclarationExpression(
                    new Expression.IfExpression(
                            new Expression.ComparisonExpression(
                                    Expression.ComparisonExpression.Type.EQ,
                                    new Expression.VariableExpression(new Environment.Name("bot")),
                                    new Expression.IntConstant(0)
                            ),
                            new Expression.IntConstant(0),
                            new Expression.BinaryOperationExpression(
                                    Expression.BinaryOperationExpression.Operator.DIV,
                                    new Expression.VariableExpression(new Environment.Name("top")),
                                    new Expression.VariableExpression(new Environment.Name("bot"))
                            )
                    ),
                    new Environment.Name("top"),
                    new Environment.Name("bot")
            ),
            new Expression.FunctionCallExpression(
                    new Expression.VariableExpression(new Environment.Name("safeDivision")),
                    new Expression.IntConstant(474),
                    new Expression.BinaryOperationExpression(
                            Expression.BinaryOperationExpression.Operator.MINUS,
                            new Expression.IntConstant(474),
                            new Expression.BinaryOperationExpression(
                                    Expression.BinaryOperationExpression.Operator.PLUS,
                                    new Expression.IntConstant(400),
                                    new Expression.IntConstant(74)
                            )
                    )
            )
    );


    // { let safeDivision = function(top, bot) { if (bot == 0) then 0 else top/bot }
    //   {  let fact = function(x) { if (x == 1) then 1 else x * fact(x-1) }
    //      fact(10)
    //   }
    // }
    static Expression p11 = new Expression.LetExpression(
            new Environment.Name("safeDivision"),
            new Expression.FunctionDeclarationExpression(
                    new Environment.Name("safeDivision"),
                    new Expression.IfExpression(
                            new Expression.ComparisonExpression(
                                    Expression.ComparisonExpression.Type.EQ,
                                    new Expression.VariableExpression(new Environment.Name("bot")),
                                    new Expression.IntConstant(0)
                            ),
                            new Expression.IntConstant(0),
                            new Expression.BinaryOperationExpression(
                                    Expression.BinaryOperationExpression.Operator.DIV,
                                    new Expression.VariableExpression(new Environment.Name("top")),
                                    new Expression.VariableExpression(new Environment.Name("bot"))
                            )
                    ),
                    new Environment.Name("top"),
                    new Environment.Name("bot")
            ),
            new Expression.LetExpression(
                    new Environment.Name("fact"),
                    new Expression.FunctionDeclarationExpression(
                            new Environment.Name("fact"),
                            new Expression.IfExpression(
                                    new Expression.ComparisonExpression(
                                            Expression.ComparisonExpression.Type.EQ,
                                            new Expression.VariableExpression(new Environment.Name("x")),
                                            new Expression.IntConstant(1)
                                    ),
                                    new Expression.IntConstant(1),
                                    new Expression.BinaryOperationExpression(
                                            Expression.BinaryOperationExpression.Operator.TIMES,
                                            new Expression.VariableExpression(new Environment.Name("x")),
                                            new Expression.FunctionCallExpression(
                                                    new Expression.VariableExpression(new Environment.Name("fact")),
                                                    new Expression.BinaryOperationExpression(
                                                            Expression.BinaryOperationExpression.Operator.MINUS,
                                                            new Expression.VariableExpression(new Environment.Name("x")),
                                                            new Expression.IntConstant(1)
                                                    )
                                            )
                                    )
                            ),
                            new Environment.Name("x")
                    ),
                    new Expression.FunctionCallExpression(
                            new Expression.VariableExpression(new Environment.Name("fact")),
                            new Expression.IntConstant(10)
                    )
            )
    );


    Integer sum(Integer head) {
        
    }

    public static void main(String[] args) {
        Environment e = new Environment.LexicalScopedEnvironment();
        System.out.println(new Interpreter().evaluate(p11, e, null));
    }
}
