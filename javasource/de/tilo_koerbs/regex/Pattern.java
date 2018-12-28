package de.tilo_koerbs.regex;

import de.tilo_koerbs.regex.internal.LogLevel;
import de.tilo_koerbs.regex.internal.Tokenizer;

public class Pattern
{
    public static Pattern compile(String regex)
    {
        Tokenizer tokenizer = new Tokenizer(LogLevel.DEBUG);
        tokenizer.tokenize(regex);
        
        return new Pattern();
    }
    
    public Matcher matcher(CharSequence input)
    {
        return new Matcher();
    }
}
