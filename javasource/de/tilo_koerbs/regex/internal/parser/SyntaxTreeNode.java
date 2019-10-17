package de.tilo_koerbs.regex.internal.parser;

import de.tilo_koerbs.regex.internal.token.Token;
import de.tilo_koerbs.regex.internal.token.TokenType;

/**
 * This is the base class for nodes of the regex syntax tree.
 */
public class SyntaxTreeNode {
    protected int                   numberOfSubNodes = 0;
    protected SyntaxTreeNodeSubType syntaxTreeNodeSubType;
    protected TokenType             tokenType;
    protected Token                 token;
	protected int                   capturingGroup = -1;  // Number of capturing group. Capturing groups are numbered by counting their opening parentheses from left to right. Group zero always stands for the entire expression. -1 is used for "no capturing group".
    protected boolean               combinedOperand = false;
    protected SyntaxTreeNode        operand1 = null;
    protected SyntaxTreeNode        operand2 = null;
    
    public SyntaxTreeNode(SyntaxTreeNodeSubType syntaxTreeNodeSubType, TokenType tokenType, Token token) {
        this.syntaxTreeNodeSubType = syntaxTreeNodeSubType;
        this.tokenType = tokenType;
        this.token     = token;
    }
    
    public int getNumberOfSubNodes()
    {
        return numberOfSubNodes;
    }
    
    protected void setNumberOfSubNodes(int numberOfSubNodes)
    {
        this.numberOfSubNodes = numberOfSubNodes;
    }

    public SyntaxTreeNodeSubType getSyntaxTreeNodeSubType() {
        return syntaxTreeNodeSubType;
    }

    protected void setSyntaxTreeNodeSubType(SyntaxTreeNodeSubType syntaxTreeNodeSubType) {
        this.syntaxTreeNodeSubType = syntaxTreeNodeSubType;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public Token getToken() {
        return token;
    }

	public int getCapturingGroup() {
		return capturingGroup;
	}

	public void setCapturingGroup(int capturingGroup) {
		this.capturingGroup = capturingGroup;
	}

    public boolean isCombinedOperand() {
        return combinedOperand;
    }

    public void setIsCombinedOperand(boolean isCombinedOperand) {
        this.combinedOperand = isCombinedOperand;
    }

    public SyntaxTreeNode getOperand1() {
        return operand1;
    }

    public void setOperand1(SyntaxTreeNode operand1) {
        this.operand1 = operand1;
    }

    public SyntaxTreeNode getOperand2() {
        return operand2;
    }

    public void setOperand2(SyntaxTreeNode operand2) {
        this.operand2 = operand2;
    }
}
