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
	<b>Person:</b>
	<g:form url="[controller:'transactions', action:'showForAccount']">
		<select id="accounts" name="accounts">
			<g:each in="${allAccounts}" var="account">
				<option>
					${account.name}
				</option>
			</g:each>
		</select>
		<br/>
		<b>
			<g:submitButton id="acc_submit" name="Show Transactions"/>
		</b>
	</g:form>
	------------------------------------------------------
	<g:if test="${selected != null}">
		<div id="balance">Balance: ${selected.balance}</div>
		<div>
			<table id="transactions">
				<tr>
					<th>Date</th>
					<th>Description</th>
					<th>Amount</th>
					<th>Balance</th>
				</tr>
				<g:each in="${selected.transactions}" var="tx">
					<tr>
						<td>${tx.date.getDateString()}</td>
						<td>${tx.description}</td>
						<td>${tx.amount}</td>
						<td>${tx.balance}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</g:if>
</div>
</body>
</html>