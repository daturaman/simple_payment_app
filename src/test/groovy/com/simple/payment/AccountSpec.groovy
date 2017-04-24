package com.simple.payment

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "balance should reconcile with sum of transactions"() {
        given:
            Account account = new Account("MyAccount", "my@email.com", [])
        when:
            account.addTransaction(new Transaction(date: new Date(), amount: -10, description: "Test tx1", balance: 190))
            account.addTransaction(new Transaction(date: new Date(), amount: 150, description: "Test tx2", balance: 340))
            account.addTransaction(new Transaction(date: new Date(), amount: -70, description: "Test tx3", balance: 270))
        then:
            account.balance == 270
    }

    void "should throw exception if account goes overdrawn"(){
		given:
		Account account = new Account("MyAccount", "my@email.com", [])
		when:
			account.addTransaction(new Transaction(date: new Date(), amount: -210, description: "Test tx1", balance: -10))
		then:
			thrown(IllegalStateException)
    }
}
