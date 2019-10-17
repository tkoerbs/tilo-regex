package de.tilo_koerbs.regex.internal.parser;

public enum SyntaxTreeNodeSubType {
    CHARACTER(SyntaxTreeNodeType.OPERAND),
    CHARACTER_CLASS(SyntaxTreeNodeType.OPERAND),
    SEQUENCE(SyntaxTreeNodeType.BINARY_OPERATOR),  // X followed by Y
    QUESTIONMARK_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // ?
    STAR_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // *
    PLUS_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // +
    N_TIMES_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // {n}
    N_TIMES_MINIMUM_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // {n,}
    N_TO_M_TIMES_GREEDY(SyntaxTreeNodeType.UNARY_OPERATOR),  // {n,m}
    OR(SyntaxTreeNodeType.BINARY_OPERATOR),  // |
    START_CAPTURING_GROUP(SyntaxTreeNodeType.START_CAPTURING_GROUP),  // (
    END_CAPTURING_GROUP(SyntaxTreeNodeType.END_CAPTURING_GROUP);  // )
    
    protected SyntaxTreeNodeType baseType;
    
    SyntaxTreeNodeSubType(SyntaxTreeNodeType baseType)
    {
        this.baseType = baseType;
    }

    public SyntaxTreeNodeType getBaseType() {
        return baseType;
    }

    protected void setBaseType(SyntaxTreeNodeType baseType) {
        this.baseType = baseType;
    }
}
