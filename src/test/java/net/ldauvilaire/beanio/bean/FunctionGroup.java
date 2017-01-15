package net.ldauvilaire.beanio.bean;

import java.util.List;

public class FunctionGroup {

	private FunctionGroupHeader functionGroupHeader;
	private List<TransactionSet> transactionSet;
	private FunctionGroupTrailer functionGroupTrailer;

	public FunctionGroupHeader getFunctionGroupHeader() {
		return functionGroupHeader;
	}
	public void setFunctionGroupHeader(FunctionGroupHeader functionGroupHeader) {
		this.functionGroupHeader = functionGroupHeader;
	}

	public List<TransactionSet> getTransactionSet() {
		return transactionSet;
	}
	public void setTransactionSet(List<TransactionSet> transactionSet) {
		this.transactionSet = transactionSet;
	}

	public FunctionGroupTrailer getFunctionGroupTrailer() {
		return functionGroupTrailer;
	}
	public void setFunctionGroupTrailer(FunctionGroupTrailer functionGroupTrailer) {
		this.functionGroupTrailer = functionGroupTrailer;
	}
}
