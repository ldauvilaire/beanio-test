package net.ldauvilaire.beanio.bean;

public class Transaction {

	private String type;
	private Long amount;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
}
