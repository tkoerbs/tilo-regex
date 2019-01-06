package de.tilo_koerbs.regex.internal.token;

import de.tilo_koerbs.regex.internal.parser.SyntaxTreeNodeSubType;

public enum TokenClass {
    CHARACTER                (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER),
    CHARACTER_CLASS          (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    DOT                      (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    DIGIT                    (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    NON_DIGIT                (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    HORIZONTAL_WHITESPACE    (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    NON_HORIZONTAL_WHITESPACE(TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    WHITESPACE               (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    NON_WHITESPACE           (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    VERTICAL_WHITESPACE      (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    NON_VERTICAL_WHITESPACE  (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    WORD_CHARACTER           (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    NON_WORD_CHARACTER       (TokenType.OPERAND,               10, SyntaxTreeNodeSubType.CHARACTER_CLASS),
    QUESTIONMARK_GREEDY      (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.QUESTIONMARK_GREEDY),  // ?
    STAR_GREEDY              (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.STAR_GREEDY),  // *
    PLUS_GREEDY              (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.PLUS_GREEDY),  // +
    N_TIMES_GREEDY           (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.N_TIMES_GREEDY),  // {n}
    N_TIMES_MINIMUM_GREEDY   (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.N_TIMES_MINIMUM_GREEDY),  // {n,}
    N_TO_M_TIMES_GREEDY      (TokenType.UNARY_OPERATOR,        60, SyntaxTreeNodeSubType.N_TO_M_TIMES_GREEDY),  // {n,m}
    OR                       (TokenType.BINARY_OPERATOR,       50, SyntaxTreeNodeSubType.OR),  // |
    OPENING_PARENTHESIS      (TokenType.START_CAPTURING_GROUP,  0, SyntaxTreeNodeSubType.START_CAPTURING_GROUP),  // (
    CLOSING_PARENTHESIS      (TokenType.END_CAPTURING_GROUP,    0, SyntaxTreeNodeSubType.END_CAPTURING_GROUP);  // )
    
    protected TokenType tokenType;
    protected int precedence;
    protected SyntaxTreeNodeSubType syntaxTreeNodeSubType;
    
    TokenClass(TokenType tokenType, int precedence, SyntaxTreeNodeSubType syntaxTreeNodeSubType)
    {
        this.tokenType = tokenType;
        this.precedence = precedence;
        this.syntaxTreeNodeSubType = syntaxTreeNodeSubType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    protected void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public SyntaxTreeNodeSubType getSyntaxTreeNodeSubType() {
        return syntaxTreeNodeSubType;
    }

    protected void setSyntaxTreeNodeSubType(SyntaxTreeNodeSubType syntaxTreeNodeSubType) {
        this.syntaxTreeNodeSubType = syntaxTreeNodeSubType;
    }
}
