<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>ApplicationContext | Frank W. Rahn</title>
	</head>
	<body>
		<h1>ApplicationContext</h1>
		<c:forEach var="appCtx" items="${appCtxs}">
		<fieldset>
			<legend>${appCtx.displayName}</legend>
			<table border="0" cellpadding="0" cellspacing="0" width="100%">
				<tr>
					<th>Id</th>
					<td>${appCtx.id}</td>
				</tr>
				<tr>
					<th>Klasse</th>
					<td>${appCtx.appCtxClass}</td>
				</tr>
			</table>
			<fieldset>
				<legend>Beans</legend>
				<table border="1" cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<th>Klasse</th>
						<th>Id</th>
						<th>Alias</th>
					</tr>
					<c:forEach var="bean" items="${appCtx.beans}">
					<tr>
						<c:forEach var="value" items="${bean}">
						<td>${value}</td>
						</c:forEach>
					</tr>
					</c:forEach>
				</table>
			</fieldset>
		</fieldset>
		<p>&nbsp;</p>
		</c:forEach>
	</body>
</html>