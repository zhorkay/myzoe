<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Spring Social Showcase</title>
</head>
<body>
<div id="content">
    <h3>Connect to Facebook</h3>

    <form:form action="${pageContext.request.contextPath}/connect/facebook" method="POST">
        <input type="hidden" name="scope" value="public_profile,email" />
        <div class="formInfo">
            <p>You aren't connected to Facebook yet. Click the button to connect Spring Social Showcase with your Facebook account.</p>
        </div>
        <button type="submit" class="btn btn-default">Submit Form</button>
    </form:form>
</div>
</body>
</html>
