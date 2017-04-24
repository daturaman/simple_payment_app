<html>
<head>
	<title>Pay Some Person</title>
	<meta name="layout" content="main"/>
</head>
<body>
<span><h3 id="msg">${msg}</h3></span>
<div>
	<h2>Pay</h2>
	<br/>

	<g:form url="[controller:'pay', action:'payAccount']">

		<b>From:</b>
		<select id="fromAccount" name="fromAccount">
			<g:each in="${allAccounts}" var="account">
				<option>
					${account.name}
				</option>
			</g:each>
		</select>
		<br/>
		<b>To: </b>
		<select id="toAccount" name="toAccount">
			<g:each in="${allAccounts}" var="account">
				<option>
					${account.name}
				</option>
			</g:each>
		</select>
		<br/>
		<b>Amount:</b>
		<g:textField id="amount" name="amount"/>
		<br/>
		<b>
			<g:submitButton id="pay_submit" name="Send Payment"/>
		</b>

	</g:form>
</div>
</body>
</html>