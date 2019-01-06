package de.tilo_koerbs.regex.internal.token;

public class Token {
    protected TokenClass tokenClass;
    protected String originalRegexSection;
    protected int originalRegexSectionIndex;
    
    public Token(TokenClass tokenClass, String originalRegexSection, int originalRegexSectionIndex)
    {
        this.tokenClass                = tokenClass;
        this.originalRegexSection      = originalRegexSection;
        this.originalRegexSectionIndex = originalRegexSectionIndex;
    }

    public TokenClass getTokenClass() {
        return tokenClass;
    }

    public String getOriginalRegexSection() {
        return originalRegexSection;
    }

    public int getOriginalRegexSectionIndex() {
        return originalRegexSectionIndex;
    }
}
