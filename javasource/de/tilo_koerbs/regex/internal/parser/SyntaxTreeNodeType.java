package de.tilo_koerbs.regex.internal.parser;

public enum SyntaxTreeNodeType {
    UNARY_OPERATOR(),  // postfix operator with one operand, e.g. *, ?, +
    BINARY_OPERATOR(),  // infix operator with two operands, e.g. |
    START_CAPTURING_GROUP(),  // start of a capturing group, (
    END_CAPTURING_GROUP(),  // end of a capturing group, )
    OPERAND();  // an operand, e.g. a character
    
    SyntaxTreeNodeType()
    {
        
    }
}
