package de.tilo_koerbs.regex.internal;

public class Token {
    protected TokenClass tokenClass;
    protected String originalRegexSection;
    
    public Token(TokenClass tokenClass, String originalRegexSection)
    {
        this.tokenClass           = tokenClass;
        this.originalRegexSection = originalRegexSection;
    }

    public TokenClass getTokenClass() {
        return tokenClass;
    }

    public String getOriginalRegexSection() {
        return originalRegexSection;
    }
}
