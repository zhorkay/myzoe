<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Registration</title>
</head>
<body>
    <div class="page-header">
        <h1>Registration Form</h1>
    </div>
    <div class="panel panel-default">
        <div class="panel-body">
            <form:form action="${pageContext.request.contextPath}/user/register" commandName="userForm"
                       method="POST" role="form">
                <div class="row">
                    <div id="form-group-email" class="form-group col-lg-4">
                        <label class="control-label" for="user-name">Username:</label>
                        <form:input id="user-name" path="username" cssClass="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div id="form-group-password" class="form-group col-lg-4">
                        <label class="control-label" for="user-password">Password:</label>
                        <form:password id="user-password" path="password" cssClass="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div id="form-group-passwordVerification" class="form-group col-lg-4">
                        <label class="control-label" for="user-passwordVerification">Password Verification:</label>
                        <form:password id="user-passwordVerification" path="passwordVerification" cssClass="form-control"/>
                    </div>
                </div>
                <div class="row">
                    <div id="form-group-loginProvider" class="form-group col-lg-4">
                        <label class="control-label" for="user-loginProvider">Login Provider:</label>
                        <form:input id="user-loginProvider" path="loginProvider" cssClass="form-control" readonly="true"/>
                    </div>
                </div>
                <button type="submit" class="btn btn-default">Submit Form</button>
            </form:form>
        </div>
    </div>
</body>
</html>
