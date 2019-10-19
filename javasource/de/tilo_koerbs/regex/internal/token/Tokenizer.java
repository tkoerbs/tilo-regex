package de.tilo_koerbs.regex.internal.token;

import de.tilo_koerbs.regex.internal.LogLevel;
import java.util.ArrayList;
import java.util.Iterator;

public class Tokenizer {
    protected LogLevel logLevel;
	
	/**
	 * Count number of opening parentheses in regex.
	 * Capturing groups are numbered by counting their opening parentheses from left to right. Group zero always stands for the entire expression. The first opening parentheses defines capturing group 1.
	 */
	protected int capturingGroup = 0;
    
    public Tokenizer(LogLevel logLevel)
    {
        this.logLevel = logLevel;
    }
    
    public ArrayList<Token> tokenize(String regex)
    {
        ArrayList<Token> tokenList = new ArrayList<>();
        int currentPosition = 0;
        Token token;
        do
        {
            token = findNextToken(regex, currentPosition);
            if (token != null)
            {
                tokenList.add(token);
                currentPosition += token.getOriginalRegexSection().length();
            }
        }
        while (token != null);
        
        if (logLevel.getLevelCode() >= 3)
        {
            System.out.println(this.getClass().getName() + ": Regex \"" + regex + "\" was split into " + tokenList.size() + " tokens:");
            Iterator<Token> tokenIterator = tokenList.iterator();
            while (tokenIterator.hasNext())
            {
                token = tokenIterator.next();
                System.out.println(token.getOriginalRegexSection() + "\t" + token.getTokenClass().toString());
            }
        }
        
        return tokenList;
    }
    
    protected Token findNextToken(String regex, int currentPosition)
    {
        Token token = null;
        
        int regexLength = regex.length();
        
        if (currentPosition < regexLength)
        {
            char char1 = regex.charAt(currentPosition);
            char char2 = (currentPosition + 1 < regexLength) ? regex.charAt(currentPosition + 1) : 0;  // using 0 as special char
            
            switch (char1)
            {
                case '*':
                    if (char2 == '?')
                    {
                        // *? reluctant quantifier "zero or more times"
                        // TODO
                    }
                    else if (char2 == '+')
                    {
                        // *+ possessive quantifier "zero or more times"
                        // TODO
                    }
                    else
                    {
                        // * greedy quantifier "zero or more times"
                        token = new Token(TokenClass.STAR_GREEDY, new Character(char1).toString(), currentPosition, capturingGroup);
                    }
                    break;
                case '?':
                    if (char2 == '?')
                    {
                        // ?? reluctant quantifier "once or not at all"
                        // TODO
                    }
                    else if (char2 == '+')
                    {
                        // ?+ possessive quantifier "once or not at all"
                        // TODO
                    }
                    else
                    {
                        // ? greedy quantifier "once or not at all"
                        token = new Token(TokenClass.QUESTIONMARK_GREEDY, new Character(char1).toString(), currentPosition, capturingGroup);
                    }
                    break;
                case '+':
                    if (char2 == '?')
                    {
                        // +? reluctant quantifier "one or more times"
                        // TODO
                    }
                    else if (char2 == '+')
                    {
                        // ++ possessive quantifier "one or more times"
                        // TODO
                    }
                    else
                    {
                        // + greedy quantifier "one or more times"
                        token = new Token(TokenClass.PLUS_GREEDY, new Character(char1).toString(), currentPosition, capturingGroup);
                    }
                    break;
                case '|':
                    // | logical operator "either or"
                    token = new Token(TokenClass.OR, new Character(char1).toString(), currentPosition, capturingGroup);
                    break;
                case '(':
                    // ( start of a capturing group
                    token = new Token(TokenClass.OPENING_PARENTHESIS, new Character(char1).toString(), currentPosition, ++capturingGroup);  // Each opening parentheses defines a new capturing group.
                    break;
                case ')':
                    // ( end of a capturing group
                    token = new Token(TokenClass.CLOSING_PARENTHESIS, new Character(char1).toString(), currentPosition, capturingGroup);
                    break;
                default:
                    token = new Token(TokenClass.CHARACTER, new Character(char1).toString(), currentPosition, capturingGroup);
                    break;
            }
            
            if (token == null)
            {
                throw new IllegalStateException("Internal error tokenizing Regex \"" + regex + "\" at or after position " + currentPosition + " at or after character '" + char1 + "'");
            }
        }
        
        return token;
    }
}
