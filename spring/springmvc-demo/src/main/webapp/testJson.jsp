<%--
  Created by IntelliJ IDEA.
  User: one
  Date: 2020-12-8
  Time: 12:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
</head>
<body>
    <a href="javascript:void(0)" id="testAjax">测试Ajax实现异步请求中的json格式数据传递</a>

    <script type="text/javascript">
        $(function () {
            // #testAjax id选择器
            $('#testAjax').click(function() {
                $.ajax({
                    //请求方式: post
                    type:'POST',
                    //请求url地址
                    url:"/userController/testJson01",
                    //请求参数
                    data:'{"name":"张三","age":23}',

                    //响应正文类型 json或者text
                    dataType:'json',
                    //请求的正文类型  json或者text
                    contentType:"application/json",
                    //将后端传过来的json数据以弹窗的形式显示在浏览器页面
                    success:function (data) {
                        alert(data.name)
                        alert(data.age)
                    }
                });
                
            });
        });
    </script>

</body>
</html>
