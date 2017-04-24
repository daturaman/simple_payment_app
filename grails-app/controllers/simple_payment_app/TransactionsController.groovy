package simple_payment_app

import com.simple.payment.Account
import com.simple.payment.Transaction

class TransactionsController {

    static defaultAction = "transactions"
    def accounts = Account.list()
    def selected = null
    def transactions

    def transactions(){
        if (selected == null){
            respond([allAccounts: accounts])
        }else{
            respond([allAccounts: accounts, selected: selected, transactions: transactions])
        }
    }

    def showForAccount(){
        for (Object account : accounts) {
          if(account.name == params.accounts){
              selected = Account.get(account.id)
              break
          }
        }

        //TODO Replace this with a finder, same as for Account
        transactions = []
        for (Transaction transaction : selected.transactions ) {
            transactions << transaction
        }
        redirect action: 'transactions'
    }
}
