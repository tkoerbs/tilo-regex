package de.tilo_koerbs.regex.internal.token;

public class Token {
    protected TokenClass tokenClass;
    protected String originalRegexSection;
    protected int originalRegexSectionIndex;
    protected int capturingGroup;  // Valid and required only for tokenClass=TokenClass.OPENING_PARENTHESIS
    
    public Token(TokenClass tokenClass, String originalRegexSection, int originalRegexSectionIndex)
    {
        this.tokenClass                = tokenClass;
        this.originalRegexSection      = originalRegexSection;
        this.originalRegexSectionIndex = originalRegexSectionIndex;
    }
    
    public Token(String originalRegexSection, int originalRegexSectionIndex, int capturingGroup)
    {
        this(TokenClass.OPENING_PARENTHESIS, originalRegexSection, originalRegexSectionIndex);
		this.capturingGroup = capturingGroup;
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

	protected void setCapturingGroup(int capturingGroup) {
		this.capturingGroup = capturingGroup;
	}
}
