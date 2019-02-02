<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title></title>
</head>
<body>
    <div id="content">
        <h3>Connected to Facebook</h3>

        <form id="disconnect" method="post">
            <div class="formInfo">
                <p>
                    Spring Social Showcase is connected to your Facebook account.
                    Click the button if you wish to disconnect.
                </p>
            </div>
            <button type="submit">Disconnect</button>
            <input type="hidden" name="_method" value="delete" />
        </form>

        <a href="<c:url value="${pageContext.request.contextPath}/facebook" />">View your Facebook profile</a>
    </div>
</body>
</html>
