package tests.de.tilo_koerbs.regex;

import de.tilo_koerbs.regex.Matcher;
import de.tilo_koerbs.regex.Pattern;

public class RegexTest {
    public static void main(String args[])
    {
        if (args.length != 2)
        {
            printUsage();
            return;
        }

        Pattern compiledPlaceholderSectionEndPatternRegex = Pattern.compile(args[0]);
        
        Matcher placeholderSectionEndPatternMatcher = compiledPlaceholderSectionEndPatternRegex.matcher(args[1]);
//        if (placeholderSectionEndPatternMatcher.find())
//        {
//            // found
//        }
//        else
//        {
//            // not found
//        }
        
        return;
    }
    
    public static void printUsage()
    {
        System.out.println("usage: RegexTest regex string");
        System.out.println("");
        System.out.println("        regex            A regular expression.");
        System.out.println("");
        System.out.println("        string           A string to find matches of the regex in.");
    }
}
