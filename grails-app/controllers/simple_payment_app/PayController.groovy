package simple_payment_app

import com.simple.payment.Account
import com.simple.payment.Transaction

class PayController {

	static defaultAction = "pay"
	def accounts
	def msg = ""
	def mailService
	def accountService

	def pay() {
		accounts = accountService.listAccounts()
		respond([allAccounts: accounts, msg: msg])
	}

	def payAccount(){
		Account fromAcc = accountService.findByName(params.fromAccount)
		def amount = Integer.parseInt(params.amount)
		if(amount > fromAcc.balance){
			msg = "Transaction failed: insufficient funds."
			redirect action: 'pay'
			return
		}

		int deduction = Math.negateExact(amount)
		fromAcc.addTransaction(new Transaction(date: new Date(), description: "Payment to ${params.toAccount}", amount: deduction, balance: fromAcc.balance + deduction))

		Account toAcc = accountService.findByName(params.toAccount)
		toAcc.addTransaction(new Transaction(date: new Date(), description: "Payment from ${params.fromAccount}", amount: amount, balance: toAcc.balance + amount))

		accountService.update(fromAcc, toAcc)

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
