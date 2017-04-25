package simple_payment_app

class TransactionsController {

    static defaultAction = "transactions"
    def selected = null
    AccountService accountService

    def transactions(){
        if (selected == null){
            respond([allAccounts: accountService.listAccounts()])
        }else{
            respond([allAccounts: accountService.listAccounts(), selected: selected])
        }
    }

    def showForAccount(){
        selected = accountService.findByName(params.accounts)
        redirect action: 'transactions'
    }
}
