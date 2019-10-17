package de.tilo_koerbs.regex.internal.parser;

import de.tilo_koerbs.regex.internal.LogLevel;
import de.tilo_koerbs.regex.internal.token.Token;
import de.tilo_koerbs.regex.internal.token.TokenClass;
import de.tilo_koerbs.regex.internal.token.TokenType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class Parser {
    protected LogLevel logLevel;
    protected String originalRegex;
    
    public Parser(String originalRegex, LogLevel logLevel)
    {
        this.originalRegex = originalRegex;
        this.logLevel = logLevel;
    }
    
    public SyntaxTreeNode parse(ArrayList<Token> tokenList)
    {
        SyntaxTreeNode rootNode = null;
        
        Stack<SyntaxTreeNode> nodeStack = new Stack<>();

        Iterator<Token> tokenIterator = tokenList.iterator();
        while (tokenIterator.hasNext())
        {
            Token token = tokenIterator.next();
            
            TokenClass tokenClass = token.getTokenClass();
            TokenType tokenType = tokenClass.getTokenType();
            SyntaxTreeNodeSubType syntaxTreeNodeSubType = tokenClass.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType syntaxTreeNodeType = syntaxTreeNodeSubType.getBaseType();
            SyntaxTreeNode syntaxTreeNode = new SyntaxTreeNode(syntaxTreeNodeSubType, tokenType, token);
            
            switch (syntaxTreeNodeType)
            {
                case OPERAND:
                    rootNode = addOperandToNodeStack(nodeStack, rootNode, syntaxTreeNode, syntaxTreeNodeType, token);
                    break;
                case UNARY_OPERATOR:
                    rootNode = addUnaryOperatorToNodeStack(nodeStack, rootNode, syntaxTreeNode, syntaxTreeNodeType, token);
                    break;
                case BINARY_OPERATOR:
                    rootNode = addBinaryOperatorToNodeStack(nodeStack, rootNode, syntaxTreeNode, syntaxTreeNodeType, token);
                    break;
                case START_CAPTURING_GROUP:
                    rootNode = addStartCapturingGroupToNodeStack(nodeStack, rootNode, syntaxTreeNode, syntaxTreeNodeType, token);
                    break;
                case END_CAPTURING_GROUP:
                    rootNode = addEndCapturingGroupToNodeStack(nodeStack, rootNode, syntaxTreeNode, syntaxTreeNodeType, token);
                    break;
                default:
                    {
                        throw new IllegalStateException("Internal error parsing input token " + token + ": unknown syntax tree node type \"" + syntaxTreeNodeType + "\"");
                    }
            }
        }
        
        return rootNode;
    }
    
    protected SyntaxTreeNode addOperandToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode rootNode, SyntaxTreeNode operandNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
    {
        if (rootNode == null)
        {
            nodeStack.push(operandNode);
            rootNode = operandNode;
        }
        else
        {
            SyntaxTreeNodeSubType rootNodeSubType = rootNode.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType rootNodeType = rootNodeSubType.getBaseType();
            
            if (rootNode.isCombinedOperand())
            {
                nodeStack.push(operandNode);
                rootNode = operandNode;
            }
            else
            {
                switch (rootNodeType)
                {
                    case OPERAND:
                    case UNARY_OPERATOR:
                    case BINARY_OPERATOR:
                    case START_CAPTURING_GROUP:
                    case END_CAPTURING_GROUP:
                        {
                            nodeStack.push(operandNode);
                            rootNode = operandNode;
                        }
                        break;
                    default:
                        {
                            throw new IllegalStateException("Internal error parsing input, addOperandToNodeStack: unknown syntax tree node type \"" + rootNodeType + "\" of syntax tree root node " + rootNode + "");
                        }
                }
            }
        }
        
        return rootNode;
    }
    
    protected SyntaxTreeNode addUnaryOperatorToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode rootNode, SyntaxTreeNode unaryOperatorNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
    {
        if (rootNode == null)
        {
            String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
            throw new java.util.regex.PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of the regex", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
        }
        else
        {
            SyntaxTreeNodeSubType rootNodeSubType = rootNode.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType rootNodeType = rootNodeSubType.getBaseType();
            
            if (rootNode.isCombinedOperand())
            {
                transformOperatorNodeToCombinedOperand(unaryOperatorNode, rootNode, null);
                nodeStack.pop();
                nodeStack.push(unaryOperatorNode);
                rootNode = unaryOperatorNode;
            }
            else
            {
                switch (rootNodeType)
                {
                    case OPERAND:
                        {
                            transformOperatorNodeToCombinedOperand(unaryOperatorNode, rootNode, null);
                            nodeStack.pop();
                            nodeStack.push(unaryOperatorNode);
                            rootNode = unaryOperatorNode;
                        }
                        break;
                    case UNARY_OPERATOR:
                        {
                            String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
                            throw new java.util.regex.PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
                        }
                    case BINARY_OPERATOR:
                        {
                            String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
                            throw new java.util.regex.PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
                        }
                    case START_CAPTURING_GROUP:
                        {
                            String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
                            throw new java.util.regex.PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
                        }
                    case END_CAPTURING_GROUP:
                        {
                            nodeStack.push(unaryOperatorNode);
                            rootNode = unaryOperatorNode;
                        }
                        break;
                    default:
                        {
                            throw new IllegalStateException("Internal error parsing input, addUnaryOperatorToNodeStack: unknown syntax tree node type \"" + rootNodeType + "\" of syntax tree root node " + rootNode + "");
                        }
                }
            }
        }
        
        return rootNode;
    }
    
    protected SyntaxTreeNode addBinaryOperatorToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode rootNode, SyntaxTreeNode operandNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
    {
        if (rootNode == null)
        {
            String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
            throw new java.util.regex.PatternSyntaxException("Internal error parsing input, addBinaryOperatorToNodeStack: binary operator " + originalRegexSection + " at the beginning of the regex", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
        }
        else
        {
            SyntaxTreeNodeSubType rootNodeSubType = rootNode.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType rootNodeType = rootNodeSubType.getBaseType();
            
            if (rootNode.isCombinedOperand())
            {
                nodeStack.push(operandNode);
                rootNode = operandNode;
            }
            else
            {
                switch (rootNodeType)
                {
                    case OPERAND:
                    case UNARY_OPERATOR:
                    case BINARY_OPERATOR:
                    case START_CAPTURING_GROUP:
                    case END_CAPTURING_GROUP:
                        {
                            nodeStack.push(operandNode);
                            rootNode = operandNode;
                        }
                        break;
                    default:
                        {
                            throw new IllegalStateException("Internal error parsing input, addBinaryOperatorToNodeStack: unknown syntax tree node type \"" + rootNodeType + "\" of syntax tree root node " + rootNode + "");
                        }
                }
            }
        }
        
        return rootNode;
    }
    
    protected SyntaxTreeNode addStartCapturingGroupToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode rootNode, SyntaxTreeNode operandNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
    {
        if (rootNode == null)
        {
            nodeStack.push(operandNode);
            rootNode = operandNode;
        }
        else
        {
            SyntaxTreeNodeSubType rootNodeSubType = rootNode.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType rootNodeType = rootNodeSubType.getBaseType();
            
            if (rootNode.isCombinedOperand())
            {
                nodeStack.push(operandNode);
                rootNode = operandNode;
            }
            else
            {
                switch (rootNodeType)
                {
                    case OPERAND:
                    case UNARY_OPERATOR:
                    case BINARY_OPERATOR:
                    case START_CAPTURING_GROUP:
                    case END_CAPTURING_GROUP:
                        {
                            nodeStack.push(operandNode);
                            rootNode = operandNode;
                        }
                        break;
                    default:
                        {
                            throw new IllegalStateException("Internal error parsing input, addOperandToNodeStack: unknown syntax tree node type \"" + rootNodeType + "\" of syntax tree root node " + rootNode + "");
                        }
                }
            }
        }
        
        return rootNode;
    }
    
    protected SyntaxTreeNode addEndCapturingGroupToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode rootNode, SyntaxTreeNode operandNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
    {
        if (rootNode == null)
        {
            nodeStack.push(operandNode);
            rootNode = operandNode;
        }
        else
        {
            SyntaxTreeNodeSubType rootNodeSubType = rootNode.getSyntaxTreeNodeSubType();
            SyntaxTreeNodeType rootNodeType = rootNodeSubType.getBaseType();
            
            if (rootNode.isCombinedOperand())
            {
                nodeStack.push(operandNode);
                rootNode = operandNode;
            }
            else
            {
                switch (rootNodeType)
                {
                    case OPERAND:
                    case UNARY_OPERATOR:
                    case BINARY_OPERATOR:
                    case START_CAPTURING_GROUP:
                    case END_CAPTURING_GROUP:
                        {
                            nodeStack.push(operandNode);
                            rootNode = operandNode;
                        }
                        break;
                    default:
                        {
                            throw new IllegalStateException("Internal error parsing input, addOperandToNodeStack: unknown syntax tree node type \"" + rootNodeType + "\" of syntax tree root node " + rootNode + "");
                        }
                }
            }
        }
        
        return rootNode;
    }
    
    protected void transformOperatorNodeToCombinedOperand(SyntaxTreeNode operatorNode, SyntaxTreeNode operand1Node, SyntaxTreeNode operand2Node)
    {
        operatorNode.setIsCombinedOperand(true);
        if (operand1Node != null)
        {
            operatorNode.setOperand1(operand1Node);
        }
        if (operand2Node != null)
        {
            operatorNode.setOperand2(operand2Node);
        }
    }
}
