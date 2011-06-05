<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Fahrerverwaltung | Frank w. Rahn</title>
	</head>
	<body>
		<h1>Fahrerverwaltung</h1>
		<c:if test="${not empty statusMessage}">
			<fieldset>
				<legend>Hinweis/Fehler</legend>
				<div style="color: red;">${statusMessage}</div>
			</fieldset>
			<br/>
		</c:if>
		<table border="1" cellpadding="0" cellspacing="0">
			<tr>
				<th>Vorname</th>
				<th>Name</th>
				<th>&nbsp;</th>
			</tr>
			<c:forEach var="driver" items="${drivers}">
				<c:url var="editUrl" value="/drivers/edit">
	            	<c:param name="id" value="${driver.id}" />
	        	</c:url>
			<tr>
				<td>${driver.firstname}</td>
				<td>${driver.name}</td>
				<td>
					<a href="${editUrl}">Bearbeiten</a>
				</td>
			</tr>
			</c:forEach>
		</table>
		<c:url var="createUrl" value="/drivers/edit" />
		<p><a href="${createUrl}">Erzeuge einen Fahrer</a></p>
	</body>
</html>