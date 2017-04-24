package com.simple.payment

class Account {

	String name
	String email
	Integer balance = 200
	Collection<Transaction> transactions

	Account(String name, String email, Collection<Transaction> transactions) {
		this.name = name
		this.email = email
		this.transactions = transactions
		balance += transactions.stream().mapToInt(mapTxAmount).sum()
	}

//	def makePayment(amount, recipient) {
//		balance -= amount
//		Transaction payment =
//				new Transaction(date: new Date(), amount: amount, description: "Payment to ${recipient}", balance: balance)
//		return payment
//	}


	void addTransaction(Transaction transaction) {
		if (balance + transaction.amount >= 0) {
			transactions << transaction
			balance += transaction.amount
			System.out.println("New balance = " + balance)
		} else {
			throw new IllegalStateException("Overdrafts are not permitted on this account.")
		}
	}

	def mapTxAmount = { tx -> tx.amount }

	static constraints = {
		balance min: 0
	}
}
