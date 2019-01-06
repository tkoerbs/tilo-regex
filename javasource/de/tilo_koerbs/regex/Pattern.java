package de.tilo_koerbs.regex;

import de.tilo_koerbs.regex.internal.LogLevel;
import de.tilo_koerbs.regex.internal.parser.Parser;
import de.tilo_koerbs.regex.internal.parser.SyntaxTreeNode;
import de.tilo_koerbs.regex.internal.token.Token;
import de.tilo_koerbs.regex.internal.token.Tokenizer;
import java.util.ArrayList;

public class Pattern
{
    public static Pattern compile(String regex)
    {
        Tokenizer tokenizer = new Tokenizer(LogLevel.DEBUG);
        ArrayList<Token> tokenList = tokenizer.tokenize(regex);
        
        Parser parser = new Parser(regex, LogLevel.DEBUG);
        SyntaxTreeNode rootNode = parser.parse(tokenList);
        
        return new Pattern();
    }
    
    public Matcher matcher(CharSequence input)
    {
        return new Matcher();
    }
}
