<%@page pageEncoding="UTF-8" language="java" contentType="text/html;UTF-8" %>
<html>
<head>

</head>

<body>
    <form action="/findUser03" method="get">
        <h5>简单类型的POJO传值</h5>
        id:<input type="text" name="id"/> <br/>
        name:<input type="text" name="name"/> <br/>
        age:<input type="text" name="age"/>
        <hr>
        <h5>传值给POJO中的POJO类型的属性</h5>
        address.province:<input type="text" name="address.province"><br>
        address.city:<input type="text" name="address.city"/>
        address.rode:<input type="text" name="address.rode">
        <hr/>
        <h5>传值给POJO中的List< 基本数据类型 > hobby的属性</h5>
        hobby:<input type="checkbox" name="hobby" value="singer"> 唱
              <input type="checkbox" name="hobby" value="dance">跳
              <input type="checkbox" name="hobby" value="rap">rap
              <input type="checkbox" name="hobby" value="java">java
        <hr/>
        <h5>传值给POJO中的List< POJO类型 > addresses的属性</h5>
        addresses:
        <hr>
        <button type="submit">提交</button>
    </form>
</body>
</html>
