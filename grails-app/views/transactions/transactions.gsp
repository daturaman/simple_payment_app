<%@ page import="com.simple.payment.Account" %>
<html>
<head>
	<title>See transactions</title>
	<meta name="layout" content="main"/>
</head>
<body>
<div>
	<h2>Pay</h2>
	<br/>
	[ errors go here ]
	<b>Person:</b>
	<form>
		<select id="accounts">
			<g:each in="${Account.list()}" var="account">
				<option>
					${account.name}
				</option>
			</g:each>
		</select>
		<br/>
		<b>[ submit ]</b>
	</form>
	------------------------------------------------------
	<div>Balance: [amount]</div>
	<div>some awesome table with transaction amounts</div>
</div>
</body>
</html>