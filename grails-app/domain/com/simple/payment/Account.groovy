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
        for (Transaction transaction : transactions) {
            balance += transaction.amount
        }
    }

    static constraints = {
    }
}
