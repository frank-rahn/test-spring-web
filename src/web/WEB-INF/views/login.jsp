<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.springframework.security.web.WebAttributes" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Anmeldung | Frank W. Rahn</title>
	</head>
	<body onload="document.login.j_username.focus();">
		<h1>Anmeldung</h1>
		<c:if test="${not empty param.login_error}">
			<fieldset>
				<legend>Fehler bei Login</legend>
				<div style="color: red;">${SPRING_SECURITY_LAST_EXCEPTION.message}</div>
			</fieldset>
			<br/>
		</c:if>
		<c:if test="${not empty param.timeout}">
			<fieldset>
				<legend>Timeout</legend>
				<div style="color: red;">Ihre Benutzersitzung ist abgelaufen oder wurde beendet.</div>
			</fieldset>
			<br/>
		</c:if>
		<c:url var="url" value="j_spring_security_check" />
		<form action="${url}" method="post">
			<fieldset>
				<p><label for="j_username">Benutzerkennung: </label><input type="text" id="j_username" name="j_username" /></p>
				<p><label for="j_password">Kennwort: </label><input type="password" id="j_password" name="j_password" /></p>
				<input name="submit" type="submit" value="Anmelden" />
				<input name="reset" type="reset" value="Zurücksetzen" />
			</fieldset>
		</form>
	</body>
</html>