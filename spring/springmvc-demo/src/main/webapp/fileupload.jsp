<%--
  Created by IntelliJ IDEA.
  User: one
  Date: 2020-12-9
  Time: 10:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>测试文件上传</title>
</head>
<body>
    <form action="/fileUpload" method="post" enctype="multipart/form-data">
        上传LOGO:<input type="file" name="file"/><br/>
        <input type="submit" value="上传">
    </form>
</body>
</html>
