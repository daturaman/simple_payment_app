package simple_payment_app

import com.simple.payment.Account

class BootStrap {

    def init = { servletContext ->
        def acc1 = new Account(name: "Bob", email: "bob@gmail.com").save()
        def acc2 = new Account(name: "Ted", email: "ted@gmail.com").save()
        def acc3 = new Account(name: "Sam", email: "sam@gmail.com").save()
    }
    def destroy = {
    }
}
