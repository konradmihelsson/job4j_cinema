<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

    <title>Hello, world!</title>
</head>
<body>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<div class="container">
    <div class="row pt-3">
        <h3>
            Вы выбрали билет на сеанс ${sessionScope.ticket.session.id},
            ряд ${sessionScope.ticket.line}, место ${sessionScope.ticket.seat},
            к оплате: 500 рублей.
        </h3>
    </div>
    <div class="row">
        <form action="${pageContext.request.contextPath}/payment" method="post">
            <div class="form-group">
                <label for="username">ФИО</label>
                <input type="text" class="form-control" id="username" placeholder="ФИО">
            </div>
            <div class="form-group">
                <label for="phone">Номер телефона</label>
                <input type="text" class="form-control" id="phone" placeholder="Номер телефона">
            </div>
            <button type="submit" class="btn btn-success">Оплатить</button>
        </form>
    </div>
</div>
</body>
</html>
