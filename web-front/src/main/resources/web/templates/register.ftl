<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>${title}-Signup</title>
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

<body class="overflow-hidden light-background">
<div class="wrapper no-navigation preload">
    <div class="sign-in-wrapper">
        <div class="sign-in-inner">
            <div class="login-brand text-center">
                Blog <strong class="text-skin">User</strong>
            </div>

            <form id="signup-form">
                <div class="form-group m-bottom-md" id="loginName" >
                    <input type="text" class="form-control" name="loginName" placeholder="Login Name">
                </div>
                <div class="form-group" id="plainPassword" >
                    <input type="password" class="form-control" name="plainPassword" placeholder="Password">
                </div>
                <div class="form-group" id="confirmPassword" >
                    <input type="password" class="form-control" name="confirmPassword" placeholder="Confirm Password">
                </div>
                <#--<div class="form-group">-->
                    <#--<div class="custom-checkbox">-->
                        <#--<input type="checkbox" id="chkAccept">-->
                        <#--<label for="chkAccept"></label>-->
                    <#--</div>-->
                    <#--I accept the agreement-->
                <#--</div>-->

                <div class="m-top-md p-top-sm">
                    <a href="javascript:void(0)" id="signup" class="btn btn-success block">Create an accounts</a>
                </div>

                <div class="m-top-md p-top-sm">
                    <div class="font-12 text-center m-bottom-xs">Already have an account?</div>
                    <a href="javascript:window.history.back();" class="btn btn-default block">Sign In</a>
                </div>
            </form>
        </div><!-- ./sign-in-inner -->
    </div><!-- ./sign-in-wrapper -->
</div><!-- /wrapper -->

<script type="text/javascript">
    function back(){
        window.history.back();
    }

    $(function() {

        $('input[name="loginName"]').blur(function(){
            var loginName = $('input[name="loginName"]').val();
            var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(!myreg.test(loginName)){
                alert('提示\n\n请输入有效的E_mail！');
                myreg.focus();
                return false;
            }
            $.ajax({
                url: "/api/user/verifyLoginName?loginName=" + loginName,
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if($('#loginName').hasClass('has-error')){
                        $('#loginName').removeClass('has-error')
                    }
                    if($('#loginName').hasClass('has-success')){
                        $('#loginName').removeClass('has-success')
                    }
                    if(data){
                        $('#loginName').addClass('has-success');
                        $('#signup').removeAttr("disabled");
                    } else {
                        $('#loginName').addClass('has-error');
                        $('#signup').attr('disabled',"true");
                    }
                },
                error: function(data){

                }
            });
        });


        $('#signup').click(function(){
            var plainPassword = $('input[name="plainPassword"]').val();
            var confirmPassword = $('input[name="confirmPassword"]').val();
            if(plainPassword != confirmPassword){
                return;
            }
            $('#signup-form').serializeArray();
            $.ajax({
                url: "/api/user/register?" + $('#signup-form').serialize(),
                type: "POST",
                contentType: "application/json",
                success: function (data) {
                    if(data){
                        alert("注册成功");
                        back();
                    }
                },
                error: function(data){
                    alert(data.responseText);
                }
            });
        });

        $('#signup').attr('disabled',"true");

    });
</script>

</body>
</html>
