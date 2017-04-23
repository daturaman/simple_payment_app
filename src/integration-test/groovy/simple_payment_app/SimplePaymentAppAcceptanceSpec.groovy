package simple_payment_app

import com.simple.payment.Account
import grails.test.mixin.integration.Integration
import grails.transaction.*
import geb.spock.*

/**
 * See http://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class SimplePaymentAppAcceptanceSpec extends GebSpec {

    def accounts

    def setup() {
        accounts = Account.list()
    }

    def cleanup() {
    }

    void "I visit the Transaction screen"() {
        when:"I view the page"
            go '/transactions'

        then:"I see a list of all the accounts in the system and their balances"
        	title == "See transactions"
            assert $("#accounts option").size() == accounts.size()
    }

    void "There is a list of accounts on the transaction screen"() {
        when:"I select an account and click submit"
            $("form").accounts = "Bob"
            $("#acc_submit").click()

        then:"I see all transactions for the account that I selected"
            title == "See transactions"
            assert $("#balance").text() == "Balance: 140"
            assert $("#transactions tr").size() == 4//accounts[0].transactions.size()//TODO Tx.findByName
    }
//
//    void "I visit the Pay screen"() {
//        when:"I view the page"
//        go '/pay'
//
//        then:"There’s a form to transfer a sum from one account to another. The form contains the following fields: " +
//                "From account, To account, Amount"
//            title == "Welcome to Grails"
//    }
//
//    void "There is a transfer form on the pay screen"() {
//        when:"I select a From account, a different To account and specify an amount less than or equal " +
//                "to the balance of the From account and submit the form"
//        go '/pay'
//
//        then:"The balance of the From account decreases by the amount specified.\n" +
//                "\n" +
//                "The balance of the To account increases by the amount specified.\n" +
//                "\n" +
//                "An email is sent to both account holders confirming the transfer.\n" +
//                "\n" +
//                "I am informed that the transfer was successful."
//        title == "Welcome to Grails"
//    }
//
//    void "There is a transfer form on the pay screen2"() {
//        when:"I select a From account, a different To account and specify an amount greater than " +
//                "the balance of the From account and submit the form"
//        go '/pay'
//
//        then:"I am informed that the transaction was not successful.\n" +
//                "\n" +
//                "The balance of the From account remains unchanged.\n" +
//                "\n" +
//                "The balance of the To account remains unchanged..\n" +
//                "\n" +
//                "No email is sent to either account."
//        title == "Welcome to Grails"
//    }
}
