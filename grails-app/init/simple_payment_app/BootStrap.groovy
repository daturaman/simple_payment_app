package simple_payment_app

import com.simple.payment.Account
import com.simple.payment.Transaction

class BootStrap {

	def init = { servletContext ->

		def bob_tx = Arrays.asList(
				new Transaction(date: getDate("2017/01/31"), amount: -10, description: "Payment to Ted", balance: 190),
				new Transaction(date: getDate("2017/02/20"), amount: -20, description: "Payment to Sam", balance: 170),
				new Transaction(date: getDate("2017/03/19"), amount: -30, description: "Payment to Ted", balance: 140),
		)

		new Account("Bob", "bob@gmail.com", bob_tx).save()
		new Account("Ted", "ted@gmail.com", Collections.emptyList()).save()
		new Account("Sam", "sam@gmail.com", Collections.emptyList()).save()
	}

	def getDate(String date) {
		return new Date().parse('yyyy/MM/dd', date)
	}


	def destroy = {
	}
}
