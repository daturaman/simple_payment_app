package simple_payment_app

import com.simple.payment.Account
import com.simple.payment.Transaction

import javax.xml.bind.ValidationException

class PayController {

	static defaultAction = "pay"
	def accounts = Account.list()
	def msg = ""
	def mailService

	def pay() {
		accounts = Account.list()
		respond([allAccounts: accounts, msg: msg])
	}

	def payAccount(){
		System.out.println(params.fromAccount)
		//Create payment transaction
		Account fromAcc = Account.findByName(params.fromAccount)
		def amount = Integer.parseInt(params.amount)
		int deduction = Math.negateExact(amount)
		fromAcc.addTransaction(new Transaction(date: new Date(), description: "Payment to ${params.toAccount}", amount: deduction, balance: fromAcc.balance + deduction))

		//Create received tx
		Account toAcc = Account.findByName(params.toAccount)
		toAcc.addTransaction(new Transaction(date: new Date(), description: "Payment from ${params.fromAccount}", amount: amount, balance: toAcc.balance + amount))

		try {
			fromAcc.save(flush: true, failOnError: true)
			toAcc.save(flush: true, failOnError: true)
		} catch (ValidationException v) {
			v.printStackTrace()
		}

		Map mail = [message:'hello world', from:fromAcc.email, to:toAcc.email, subject:'subject']

		mailService.sendMail {
			to toAcc.email
			from "simplepayapp@gmail.com"
			subject "Payment made to your account"
			body "A payment was made to your account for the sum of ${params.amount} by ${params.fromAccount}"
		}
		mailService.sendMail {
			to fromAcc.email
			from "simplepayapp@gmail.com"
			subject "Payment sent from your account"
			body "A payment was made from your account for the sum of ${params.amount} to ${params.toAccount}"
		}

		msg = "Your payment of £${params.amount} to ${params.toAccount} was successful"
		redirect action: 'pay'
	}
}
