<html>
<head>
<title>Login Page</title>
</head>

<body onload="document.f.username.focus();">
	<h3>Login with Username and Password (Custom Page)</h3>

	<form name="f" action="/login" method="post">
		<fieldset>
			<legend>Please Login</legend>
			<% if (request.getParameter("error") != null) { %>
				<div>
					Failed to login.
					<% if (session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION") != null) { %>
                  Reason: <%= ((Throwable)session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION")).getMessage() %>
					<% } %>
				</div>
			<% } %>

            <% if (request.getParameter("logout") != null) { %>
				<div>You have been logged out.</div>
			<% } %>
			<p>
				<label for="username">Username</label> <input type="text" id="username" name="username" />
			</p>
			<p>
				<label for="password">Password</label> <input type="password" id="password" name="password" />
			</p>
            <!-- 
			<p>
				<label for="remember-me">Remember Me?</label> <input type="checkbox" id="remember-me" name="remember-me" />
			</p>
			 -->
			<div>
				<button type="submit" class="btn">Log in</button>
			</div>
		</fieldset>
	</form>

</body>
</html>