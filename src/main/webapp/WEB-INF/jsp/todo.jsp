<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>${taskName}</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <link rel="stylesheet" href="<spring:url value="/resources/css/bootstrap-select.min.css"/>" type="text/css"/>
        <link rel="stylesheet" href="<spring:url value="/resources/css/common.css"/>" type="text/css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <script src="<spring:url value="/resources/js/bootstrap-select.min.js"/>"></script>
        <script src="<spring:url value="/resources/js/jquery.bootpag.min.js"/>"></script>
        <script src="<spring:url value="/resources/js/todo.js"/>"></script>
    </head>
    <body>
        <jsp:include page="../jsp/fragments/header.jsp"/>
        <div class="container">
            <div class="page-select"></div>

            <form id="todo">
                <div>
                    <div class="category">
                        <label for="categories">Category:</label><br>
                        <select id="categories" class="selectpicker">
                            <option value="All">All</option>
                            <option value="Done">Done</option>
                            <option value="Not done">Not done</option>
                        </select>
                    </div>

                    <div class="sortby">
                        <label for="sort-by">Sort by:</label><br>
                        <select id="sort-by" class="selectpicker">
                            <option value="ID">ID</option>
                            <option value="Description">Description</option>
                        </select>
                    </div>

                    <div class="sortorder">
                        <label for="sort-order">Sort order:</label><br>
                        <select id="sort-order" class="selectpicker">
                            <option value="ASC">ASC</option>
                            <option value="DESC">DESC</option>
                        </select>
                    </div>

                    <div class="count">
                        <label for="row-count">Count:</label><br>
                        <select id="row-count" class="selectpicker">
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="25">25</option>
                            <option value="50">50</option>
                            <option value="100">100</option>
                        </select>
                    </div>

                    <div class="buttons">
                        <input id="add-todo" type="button" value="Add Todo"/>
                        <input id="delete-todos" type="button" value="Delete"/>
                    </div>
                </div>

                <table id="todos" class="table table-hover">
                    <tbody>
                        <tr id="header">
                            <th>ID</th><th>Description</th><th>Done</th>
                        </tr>
                    </tbody>
                </table>

                <p class="note">
                    Double click on description - to change note =)
                    <br>
                    Ctrl + click - to select note :)
                </p>
            </form>

            <div class="page-select"></div>
        </div>
    </body>
</html>
