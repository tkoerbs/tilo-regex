package de.tilo_koerbs.regex.internal.parser;

import de.tilo_koerbs.regex.internal.LogLevel;
import de.tilo_koerbs.regex.internal.token.Token;
import de.tilo_koerbs.regex.internal.token.TokenClass;
import de.tilo_koerbs.regex.internal.token.TokenType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.PatternSyntaxException;

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
		SyntaxTreeNode topNode = null;
		
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
					topNode = addOperandToNodeStack(nodeStack, topNode, syntaxTreeNode, syntaxTreeNodeType, token);
					break;
				case UNARY_OPERATOR:
					topNode = addUnaryOperatorToNodeStack(nodeStack, topNode, syntaxTreeNode, syntaxTreeNodeType, token);
					break;
				case BINARY_OPERATOR:
					topNode = addBinaryOperatorToNodeStack(nodeStack, topNode, syntaxTreeNode, syntaxTreeNodeType, token);
					break;
				case START_CAPTURING_GROUP:
					topNode = addStartCapturingGroupToNodeStack(nodeStack, topNode, syntaxTreeNode, syntaxTreeNodeType, token);
					break;
				case END_CAPTURING_GROUP:
					topNode = addEndCapturingGroupToNodeStack(nodeStack, topNode, syntaxTreeNode, syntaxTreeNodeType, token);
					break;
				default:
					{
						throw new IllegalStateException("Internal error parsing input token " + token + ": unknown syntax tree node type \"" + syntaxTreeNodeType + "\"");
					}
			}
		}
		
		return topNode;
	}
	
	protected SyntaxTreeNode addOperandToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode topNode, SyntaxTreeNode operandNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
	{
		if (topNode == null)
		{
			nodeStack.push(operandNode);
			topNode = operandNode;
		}
		else
		{
			SyntaxTreeNodeSubType topNodeSubType = topNode.getSyntaxTreeNodeSubType();
			SyntaxTreeNodeType topNodeType = topNodeSubType.getBaseType();
			
			if (topNode.isCombinedOperand())
			{
				nodeStack.push(operandNode);
				topNode = operandNode;
			}
			else
			{
				switch (topNodeType)
				{
					case OPERAND:
					case UNARY_OPERATOR:
					case BINARY_OPERATOR:
					case START_CAPTURING_GROUP:
					case END_CAPTURING_GROUP:
						{
							nodeStack.push(operandNode);
							topNode = operandNode;
						}
						break;
					default:
						{
							throw new IllegalStateException("Internal error parsing input, addOperandToNodeStack: unknown syntax tree node type \"" + topNodeType + "\" of syntax tree root node " + topNode + "");
						}
				}
			}
		}
		
		return topNode;
	}
	
	protected SyntaxTreeNode addUnaryOperatorToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode topNode, SyntaxTreeNode unaryOperatorNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
	{
		if (topNode == null)
		{
			String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
			throw new PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of the regex", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
		}
		else
		{
			SyntaxTreeNodeSubType topNodeSubType = topNode.getSyntaxTreeNodeSubType();
			SyntaxTreeNodeType topNodeType = topNodeSubType.getBaseType();
			
			if (topNode.isCombinedOperand())
			{
				transformOperatorNodeToCombinedOperand(unaryOperatorNode, topNode, null);
				nodeStack.pop();
				nodeStack.push(unaryOperatorNode);
				topNode = unaryOperatorNode;
			}
			else
			{
				switch (topNodeType)
				{
					case OPERAND:
						{
							transformOperatorNodeToCombinedOperand(unaryOperatorNode, topNode, null);
							nodeStack.pop();
							nodeStack.push(unaryOperatorNode);
							topNode = unaryOperatorNode;
						}
						break;
					case UNARY_OPERATOR:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case BINARY_OPERATOR:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case START_CAPTURING_GROUP:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addUnaryOperatorToNodeStack: unary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case END_CAPTURING_GROUP:
						{
							nodeStack.push(unaryOperatorNode);
							topNode = unaryOperatorNode;
						}
						break;
					default:
						{
							throw new IllegalStateException("Internal error parsing input, addUnaryOperatorToNodeStack: unknown syntax tree node type \"" + topNodeType + "\" of syntax tree root node " + topNode + "");
						}
				}
			}
		}
		
		return topNode;
	}
	
	protected SyntaxTreeNode addBinaryOperatorToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode topNode, SyntaxTreeNode binaryOperatorNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
	{
		if (topNode == null)
		{
			String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
			throw new PatternSyntaxException("Internal error parsing input, addBinaryOperatorToNodeStack: binary operator " + originalRegexSection + " at the beginning of the regex", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
		}
		else
		{
			SyntaxTreeNodeSubType topNodeSubType = topNode.getSyntaxTreeNodeSubType();
			SyntaxTreeNodeType topNodeType = topNodeSubType.getBaseType();
			
			if (topNode.isCombinedOperand())
			{
				nodeStack.push(binaryOperatorNode);
				topNode = binaryOperatorNode;
			}
			else
			{
				switch (topNodeType)
				{
					case UNARY_OPERATOR:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addBinaryOperatorToNodeStack: binary operator " + originalRegexSection + " after unprocessed unary operator", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case BINARY_OPERATOR:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addBinaryOperatorToNodeStack: binary operator " + originalRegexSection + " adjacent to another binary operator", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case START_CAPTURING_GROUP:
						{
							String originalRegexSection = token != null ? token.getOriginalRegexSection() : null;
							throw new PatternSyntaxException("Internal error parsing input, addBinaryOperatorToNodeStack: binary operator " + originalRegexSection + " at the beginning of a capturing group", originalRegex, (token != null ? token.getOriginalRegexSectionIndex() : -1));
						}
					case OPERAND:
					case END_CAPTURING_GROUP:
						{
							nodeStack.push(binaryOperatorNode);
							topNode = binaryOperatorNode;
						}
						break;
					default:
						{
							throw new IllegalStateException("Internal error parsing input, addBinaryOperatorToNodeStack: unknown syntax tree node type \"" + topNodeType + "\" of syntax tree root node " + topNode + "");
						}
				}
			}
		}
		
		return topNode;
	}
	
	protected SyntaxTreeNode addStartCapturingGroupToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode topNode, SyntaxTreeNode startCapturingNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
	{
		if (topNode == null)
		{
			nodeStack.push(startCapturingNode);
			topNode = startCapturingNode;
		}
		else
		{
			SyntaxTreeNodeSubType topNodeSubType = topNode.getSyntaxTreeNodeSubType();
			SyntaxTreeNodeType topNodeType = topNodeSubType.getBaseType();
			
			if (topNode.isCombinedOperand())
			{
				nodeStack.push(startCapturingNode);
				topNode = startCapturingNode;
			}
			else
			{
				switch (topNodeType)
				{
					case OPERAND:
					case UNARY_OPERATOR:
					case BINARY_OPERATOR:
					case START_CAPTURING_GROUP:
					case END_CAPTURING_GROUP:
						{
							nodeStack.push(startCapturingNode);
							topNode = startCapturingNode;
						}
						break;
					default:
						{
							throw new IllegalStateException("Internal error parsing input, addStartCapturingGroupToNodeStack: unknown syntax tree node type \"" + topNodeType + "\" of syntax tree root node " + topNode + "");
						}
				}
			}
		}
		
		return topNode;
	}
	
	protected SyntaxTreeNode addEndCapturingGroupToNodeStack(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode topNode, SyntaxTreeNode endCapturingNode, SyntaxTreeNodeType syntaxTreeNodeType, Token token)
	{
		if (topNode == null)
		{
			nodeStack.push(endCapturingNode);
			topNode = endCapturingNode;
		}
		else
		{
			SyntaxTreeNodeSubType topNodeSubType = topNode.getSyntaxTreeNodeSubType();
			SyntaxTreeNodeType topNodeType = topNodeSubType.getBaseType();
			
			if (topNode.isCombinedOperand())
			{
				nodeStack.push(endCapturingNode);
				topNode = endCapturingNode;
			}
			else
			{
				switch (topNodeType)
				{
					case OPERAND:
					case UNARY_OPERATOR:
					case BINARY_OPERATOR:
					case START_CAPTURING_GROUP:
					case END_CAPTURING_GROUP:
						{
							transformStackForEndCapturingNode(nodeStack, endCapturingNode);
							nodeStack.pop();
							nodeStack.push(endCapturingNode);
							topNode = endCapturingNode;
						}
						break;
					default:
						{
							throw new IllegalStateException("Internal error parsing input, addEndCapturingGroupToNodeStack: unknown syntax tree node type \"" + topNodeType + "\" of syntax tree root node " + topNode + "");
						}
				}
			}
		}
		
		return topNode;
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
	
	/**
	 * An endCapturingNode was encountered during parsing and
	 * the nodeStack needs to be unwinded until the matching SyntaxTreeNode of NodeType START_CAPTURING_GROUP is found and
	 * everything in between needs to be transformed into a combined operand.
	 * 
	 * @param nodeStack
	 * @param endCapturingNode 
	 */
	protected void transformStackForEndCapturingNode(Stack<SyntaxTreeNode> nodeStack, SyntaxTreeNode endCapturingNode)
	{
		SyntaxTreeNode combinedOperand = transformStackIntoCombinedOperand(nodeStack, true);
		if (combinedOperand != null)
		{
			nodeStack.pop();  // Remove combined operand from stack.
			if (!nodeStack.isEmpty())
			{
				SyntaxTreeNode startCapturingNode = nodeStack.pop();  // In case stack is not empty this must be our start capturing node.
				int capturingGroup = startCapturingNode.getToken().getCapturingGroup();
				combinedOperand.setCapturingGroup(capturingGroup);
				nodeStack.push(combinedOperand);
			}
			else
			{
				throw new IllegalStateException("Internal error parsing input, transformStackForEndCapturingNode: no start capturing node found for end capturing node " + endCapturingNode + "");
			}
		}
		else
		{
			Token endCapturingToken = endCapturingNode.getToken();
			//String originalRegexSection = endCapturingToken != null ? endCapturingToken.getOriginalRegexSection() : null;
			throw new PatternSyntaxException("Internal error parsing input, transformStackForEndCapturingNode: empty capturing group for end capturing node " + endCapturingNode + "", originalRegex, (endCapturingToken != null ? endCapturingToken.getOriginalRegexSectionIndex() : -1));
		}
	}
	
	/**
	 * The nodeStack is unwinded and everything is transformed into a combined operand.
	 * 
	 * @param nodeStack 
	 * @param stopAtStartCapturingNode If <code>true</code> then unwinding stops at SyntaxTreeNode of NodeType START_CAPTURING_GROUP.
	 * @return 
	 */
	protected SyntaxTreeNode transformStackIntoCombinedOperand(Stack<SyntaxTreeNode> nodeStack, boolean stopAtStartCapturingNode)
	{
		SyntaxTreeNode combinedOperand = null;
		
		while (!nodeStack.isEmpty() || stopAtStartCapturingNode && !nodeStack.peek().getSyntaxTreeNodeSubType().getBaseType().equals(SyntaxTreeNodeType.START_CAPTURING_GROUP))
		{
			if (combinedOperand != null)
			{
				SyntaxTreeNode leftHandOperand = nodeStack.pop();
				
				SyntaxTreeNodeSubType syntaxTreeNodeSubType = SyntaxTreeNodeSubType.SEQUENCE;
				//SyntaxTreeNodeType syntaxTreeNodeType = syntaxTreeNodeSubType.getBaseType();
				SyntaxTreeNode binaryOperatorNode = new SyntaxTreeNode(syntaxTreeNodeSubType, null, null);
				binaryOperatorNode.setOperand1(leftHandOperand);
				binaryOperatorNode.setOperand2(combinedOperand);
				
				combinedOperand = binaryOperatorNode;
			}
			else
			{
				combinedOperand = nodeStack.pop();
			}
		}
		
		if (combinedOperand != null)
		{
			nodeStack.push(combinedOperand);
		}
		
		return combinedOperand;
	}
}
