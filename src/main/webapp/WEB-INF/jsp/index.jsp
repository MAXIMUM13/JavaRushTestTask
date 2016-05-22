<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${appName}</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="<spring:url value="/resources/css/common.css"/>" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="../jsp/fragments/header.jsp"/>
        <div class="container">
            <div class="row">
                <h2 class="form-group">
                    <a href="<spring:url value="/todo/"/>">${taskName}</a>
                </h2>
            </div>
        </div>
    </body>
</html>
