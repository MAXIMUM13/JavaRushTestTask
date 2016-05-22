<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <div class="navbar-brand">${appName}</div>
        </div>
        <ul class="nav navbar-nav">
            <li><a class="navbar-brand nav-link" href="<spring:url value="/"/>">Home</a></li>
            <li><a class="navbar-brand nav-link" href="<spring:url value="/todo/"/>">${taskName}</a></li>
        </ul>
    </div>
</nav>
