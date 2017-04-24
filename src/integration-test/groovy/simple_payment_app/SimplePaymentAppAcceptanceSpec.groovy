package simple_payment_app

import com.simple.payment.Account
import grails.test.mixin.integration.Integration
import com.icegreen.greenmail.util.*
import grails.transaction.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class SimplePaymentAppAcceptanceSpec extends GebSpec {

	def accountSize
	def greenMail
//	def bobInitialBalance
//	def samInitialBalance

	def setup() {
		accountSize = Account.count()

	}

	def cleanup() {
	}

	void "I visit the Transaction screen"() {
		when: "I view the page"
		go '/transactions'

		then: "I see a list of all the accountSize in the system and their balances"
		title == "See transactions"
		assert $("#accounts").displayed
		assert $("#accounts option").size() == accountSize
	}

	void "There is a list of accounts on the transaction screen"() {
		when: "I select an account and click submit"
		$("form").accounts = "Bob"
		$("#acc_submit").click()

		then: "I see all transactions for the account that I selected"
		title == "See transactions"
		assert $("#balance").text() == "Balance: 440"
		assert $("#transactions tr").size() == Account.findByName("Bob").transactions.size() + 1
	}

	void "I visit the Pay screen"() {
		when: "I view the page"
		go '/pay'

		then:
		"There’s a form to transfer a sum from one account to another. The form contains the following fields: " +
				"From account, To account, Amount"
		title == "Pay Some Person"
		assert $("#amount").displayed
		assert $("#fromAccount").displayed
		assert $("#toAccount").displayed
		assert $("#fromAccount option").size() == accountSize
		assert $("#toAccount option").size() == accountSize
	}

	@Transactional
    void "There is a transfer form on the pay screen"() {
		given:
		int bobInitialBalance = Account.findByName("Bob").balance
		int samInitialBalance = Account.findByName("Sam").balance

        when:"I select a From account, a different To account and specify an amount less than or equal " +
                "to the balance of the From account and submit the form"
		$("form").fromAccount = "Bob"
		$("form").toAccount = "Sam"
		$("form").amount = "50"
		$("#pay_submit").click()

//      then:"The balance of the From account decreases by the amount specified."
//		def bob = Account.get(1)
//		int newBobBalance = bob.balance
//		assert newBobBalance == 390
//                "\n" +
//                "The balance of the To account increases by the amount specified.\n" +
//                "\n" +
		then: "An email is sent to both account holders confirming the transfer."
		def samMessage = greenMail.getReceivedMessages()[0]
		Map samMail = [from:'simplepayapp@gmail.com', to:'sam@gmail.com', subject:'Payment made to your accountt']
		assert samMail.from == GreenMailUtil.getAddressList(samMessage.from)
		assert samMail.to == samMessage.to
		assert samMail.subject == samMail.subject

		def bobMessage = greenMail.getReceivedMessages()[1]
		Map bobMail = [from:'simplepayapp@gmail.com', to:'bob@gmail.com', subject:'Payment sent from your account']
		assert bobMail.from == GreenMailUtil.getAddressList(bobMessage.from)
		assert bobMail.to == bobMessage.to
		assert bobMail.subject == bobMail.subject
		then: "I am informed that the transfer was successful."
        title == "Pay Some Person"
		assert $("#msg").text() == "Your payment of £50 to Sam was successful"
    }

    void "There is a transfer form on the pay screen2"() {
        when:"I select a From account, a different To account and specify an amount greater than " +
                "the balance of the From account and submit the form"
		$("form").fromAccount = "Bob"
		$("form").toAccount = "Sam"
		$("form").amount = "500"
		$("#pay_submit").click()

        then:"I am informed that the transaction was not successful.\n" +
                "\n" +
                "The balance of the From account remains unchanged.\n" +
                "\n" +
                "The balance of the To account remains unchanged..\n" +
                "\n" +
                "No email is sent to either account."
		title == "Pay Some Person"
		assert greenMail.getReceivedMessages().size() == 0
		assert $("#msg").text() == "Transaction failed: insufficient funds."
    }
}
