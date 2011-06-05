<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Bearbeite bzw. Anlegen eines Fahrer | Frank W. Rahn</title>
	</head>
	<body>
		<h1>Bearbeite bzw. Anlegen eines Fahrer</h1>
		<c:url var="url" value="../drivers" />
		<form:form action="${url}" commandName="driver">
			<form:hidden path="id" />
			<fieldset>
				<p><label for="firstname">Vorname: </label><form:input path="firstname" /></p>
				<p><label for="name">Name: </label><form:input path="name" /></p>
				<input name="submit" type="submit" value="Speichern" />
			</fieldset>
		</form:form>
	</body>
</html>