<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <script src="https://cdn.staticfile.org/jquery/1.10.2/jquery.min.js">
    </script>
    <script type="text/javascript">
        $(document).ready(function(){
            $("#login").click(function(){
                var account = $("[name=name]").val();
                var password = $("[name=password]").val();

                /* if(account == "" || password == ""){
                    alert("用户名或者密码不能为空");
                    return;
                } */

                $.ajax({
                    async:true,
                    headers: {
                        Accept: "application/json; charset=utf-8",
                        //platform:"web",
                    },
                    /* beforeSend:functioin(request){
                        request.setRequestHeader("abc","zzz");
                    }, */
                    url:"/wjdc/user/getCode.do",
                    type:"post",
                    timeout:5000,
                    dataType:"json",
                    data:{
                        "account":account,
                        "type":0
                    },
                    success:function(data){
                        console.log(data);
                        console.log(data.data);
                    },
                    error:function(xml,msg){

                    },
                    complete:function(xhr,status){

                    }
                })
            })


        });


    </script>
</head>
<body >
<div  style="margin:100px auto;width: 600px;height:400px;background-color: lavender;text-align: center">
    <br>
    <form method="post" action="login.do">
        <h3>登录</h3>
        <br>
        <br>
        <label>用户名:</label><input type="text" name="name">
        <br> <br>
        <label>密   码:</label><input type="password" name="password">
        <br><br> <br>
        <div style="text-align: center">
            <button id="login" type="submit">登录</button>  
            <button id="reset" type="reset">重置</button>
            <br>
            <p><a href="register.jsp">还没有帐号？点击这里注册！</a></p>
        </div>
    </form>
</div>
</body>
</html>
