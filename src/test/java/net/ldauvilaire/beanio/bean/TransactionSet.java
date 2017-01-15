package net.ldauvilaire.beanio.bean;

import java.util.List;

public class TransactionSet {

	private TransactionSetHeader header;
	private List<Transaction> transaction;
	private TransactionSetTrailer trailer;

	public TransactionSetHeader getHeader() {
		return header;
	}
	public void setHeader(TransactionSetHeader header) {
		this.header = header;
	}

	public List<Transaction> getTransaction() {
		return transaction;
	}
	public void setTransaction(List<Transaction> transaction) {
		this.transaction = transaction;
	}

	public TransactionSetTrailer getTrailer() {
		return trailer;
	}
	public void setTrailer(TransactionSetTrailer trailer) {
		this.trailer = trailer;
	}
}
