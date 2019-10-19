package de.tilo_koerbs.regex.internal.token;

public class Token {
    protected TokenClass tokenClass;
    protected String originalRegexSection;
    protected int originalRegexSectionIndex;
    protected int capturingGroup;
    
    public Token(TokenClass tokenClass, String originalRegexSection, int originalRegexSectionIndex, int capturingGroup)
    {
        this.tokenClass                = tokenClass;
        this.originalRegexSection      = originalRegexSection;
        this.originalRegexSectionIndex = originalRegexSectionIndex;
		this.capturingGroup            = capturingGroup;
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

	public int getCapturingGroup() {
		return capturingGroup;
	}
}
