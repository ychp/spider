<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title}-登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="shortcut icon" href="/static/favicon.ico">
    <link rel="Bookmark" href="/static/favicon.ico">

    <!-- Bootstrap core CSS -->
    <link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Simplify -->
    <link href="/static/css/simplify.min.css" rel="stylesheet">

    <!-- Jquery -->
    <script src="/static/js/jquery-1.11.1.min.js"></script>

    <!-- Bootstrap -->
    <script src="/static/bootstrap/js/bootstrap.min.js"></script>

    <!-- Slimscroll -->
    <script src='/static/js/jquery.slimscroll.min.js'></script>

    <!-- Popup Overlay -->
    <script src='/static/js/jquery.popupoverlay.min.js'></script>

    <!-- Modernizr -->
    <script src='/static/js/modernizr.min.js'></script>

    <!-- Simplify -->
    <script src="/static/js/simplify/simplify.js"></script>

</head>

<body class="overflow-hidden light-background" onkeydown='if(event.keyCode === 13){$("#signBtn").click()}'>
<div class="wrapper no-navigation preload">
    <div class="sign-in-wrapper">
        <div class="sign-in-inner">
            <div class="login-brand text-center">
                Spider <strong class="text-skin">Admin</strong>
            </div>

            <form>
                <div class="form-group m-bottom-md">
                    <input id="loginName" type="text" class="form-control" placeholder="Login Name">
                </div>
                <div class="form-group">
                    <input id="password" type="password" class="form-control" placeholder="Password">
                </div>
                <div id="message" class="alert alert-danger alert-dismissible" role="alert" style="background-color:transparent;width: 100%;">
                    <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <i id="message-icon" class="fa fa-times-circle m-right-xs"></i><strong id="message-title">Oh snap!</strong><label id="message-content"></label>
                </div>

                <div class="m-top-md p-top-sm">
                    <a id="signBtn" href="javascript:void(0)" class="btn btn-success block">Sign in</a>
                </div>
            </form>
        </div><!-- ./sign-in-inner -->
    </div><!-- ./sign-in-wrapper -->
</div><!-- /wrapper -->

<script type="text/javascript">
    var target = "/user";
    var url = window.location.href;
    if(url.indexOf("?") !== -1 && url.indexOf("target") !== -1){
        url = url.split("?")[1];
        var size = url.split("&").length;
        for(var i = 0;i<size;i++){
            url = url.split("&")[i];
            if(url.indexOf("target") === 0){
                target = url.split("=")[1];
            }
        }
    }

    function fail(){
        $("#message").hide();
    }

    function failShow(message){
        $("#message-content").html(message);
        $("#message").show();
        window.setTimeout("fail()",3000);
    }
    $(function() {
        $("#message").hide();
        $('#signBtn').click(function(){
            var target = "/";
            var loginName = $('#loginName').val();
            if(loginName === undefined || loginName.trim() ===''){
                alert('请输入用户名');
                return;
            }

            var password = $('#password').val();
            if(password === undefined || password === ''){
                alert('请输入密码');
                return;
            }

            $.ajax({
                url: "/api/user/login?name=" + loginName + "&password=" + password,
                type: "POST",
                contentType: "application/json",
                success: function () {
                    window.location.href = target;
                },
                error: function(data){
                    failShow(data.responseText);
                }
            });
        })
    })
</script>

</body>
</html>
