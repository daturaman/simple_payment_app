environments {
  production {
    //grails.serverURL = "http://localhost:8080"
  }
  development {
    grails.mail.port = 3025
    //grails.serverURL = "http://localhost:8080/${appName}"
  }
  test {
    grails.mail.port = 3025
    //grails.serverURL = "http://localhost:8080/${appName}"
  }
}