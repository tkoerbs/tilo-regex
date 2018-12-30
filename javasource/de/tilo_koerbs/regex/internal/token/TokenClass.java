package de.tilo_koerbs.regex.internal.token;

public enum TokenClass {
    CHARACTER(),
    CHARACTER_CLASS(),
    DOT(),
    DIGIT(),
    NON_DIGIT(),
    HORIZONTAL_WHITESPACE(),
    NON_HORIZONTAL_WHITESPACE(),
    WHITESPACE(),
    NON_WHITESPACE(),
    VERTICAL_WHITESPACE(),
    NON_VERTICAL_WHITESPACE(),
    WORD_CHARACTER(),
    NON_WORD_CHARACTER(),
    QUESTIONMARK_GREEDY(),  // ?
    STAR_GREEDY(),  // *
    PLUS_GREEDY(),  // +
    N_TIMES(),  // {n}
    N_TIMES_MINIMUM(),  // {n,}
    N_TO_M_TIMES(),  // {n,m}
    OR(),  // |
    OPENING_PARENTHESIS(),  // (
    CLOSING_PARENTHESIS();  // )
    
    TokenClass()
    {
        
    }
}
