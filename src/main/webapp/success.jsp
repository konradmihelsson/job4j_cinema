<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Success</title>
</head>
<body>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>

<div class="container">
    <div class="row pt-3">
        <h3>
            Вы успешно приобрели билет на сеанс ${sessionScope.ticket.session.id},
            ряд ${sessionScope.ticket.line}, место ${sessionScope.ticket.seat}.
        </h3>
    </div>
    <div class="row float-right">
        <a href="${pageContext.request.contextPath}/index.html">
            <button type="button" class="btn btn-dark">На главную</button>
        </a>
    </div>
</div>
</body>
</html>
