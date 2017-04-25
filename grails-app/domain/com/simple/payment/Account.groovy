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

	void addTransaction(Transaction transaction) {
		transactions << transaction
		balance += transaction.amount
	}

	def mapTxAmount = { tx -> tx.amount }
}
