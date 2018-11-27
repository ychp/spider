<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-Users</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Bootstrap core CSS -->
<link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="/static/css/font-awesome.min.css" rel="stylesheet">

<!-- ionicons -->
<link href="/static/css/ionicons.min.css" rel="stylesheet">

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

<script src="/static/ckeditor/ckeditor.js"></script>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header" style="margin-top:10px;height: 50px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            Users Manage
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>NickName</th>
                        <th>LoginName</th>
                        <th>Communication</th>
                        <th>HomePage</th>
                        <th>CreateData</th>
                        <th>Operator</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list users.datas as user>
                            <tr>
                                <td style="width: 3%">${user.id}</td>
                                <td style="width: 3%">${user.nickName}</td>
                                <td style="width: 5%">${user.loginName}</td>
                                <td style="width: 20%">
                                    Email : ${user.email?if_exists}
                                    <#if user.mobile??>
                                        <br>
                                        Mobile : ${user.mobile}
                                    </#if>
                                </td>
                                <td style="width: 25%">${user.homePage?if_exists}</td>
                                <td style="width: 20%;">${user.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                                <td style="width: 15%">
                                    <a class="btn btn-sm btn-success" href="#formInModal" onclick="showUser(${user.id})" data-toggle="modal">Detail</a>
                                    <a class="btn btn-sm btn-success" onclick="reset(${user.id}, '${user.nickName}')">ResetPWD</a>
                                </td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=users.pageNo size=users.pageSize total=users.total url='/users' ></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<!-- Large modal -->
<div class="modal fade" id="formInModal">
    <div class="modal-dialog modal-mid">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">User(<span id="userId"></span>)</h4>
            </div>
            <div class="modal-body">
                <form id="create" role="form">
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">NickName:</label>
                        <label class="control-label" id="nickName">NickName</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">LoginName:</label>
                        <label class="control-label" id="loginName">LoginName</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">Email:</label>
                        <label class="control-label" id="email">Email</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">Mobile:</label>
                        <label class="control-label" id="mobile">Mobile</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">HomePage:</label>
                        <label class="control-label" id="homePage">HomePage</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">Role:</label>
                        <label class="control-label" id="role">Role</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">CreateDate:</label>
                        <label class="control-label" id="createdAt">CreateDate</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3 text-right">UpdateDate:</label>
                        <label class="control-label" id="updatedAt">UpdateDate</label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div id="message" class="alert alert-success alert-dismissible" role="alert" style="position: absolute;top:25%;left: 40%;width: 20%;min-width: 200px;z-index: 999;display: none;">
    <button type="button" class="close" onclick="hideMsg()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <label id="message-content">You successfully read this important alert message.</label>
</div>

<script type="text/javascript">
    Date.prototype.Format = function(fmt)
    { //author: meizz
        var o = {
            "M+" : this.getMonth()+1,                 //月份
            "d+" : this.getDate(),                    //日
            "h+" : this.getHours(),                   //小时
            "H+" : this.getHours(),                   //小时
            "m+" : this.getMinutes(),                 //分
            "s+" : this.getSeconds(),                 //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S"  : this.getMilliseconds()             //毫秒
        };
        if(/(y+)/.test(fmt))
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        for(var k in o)
            if(new RegExp("("+ k +")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    }

    function reset(id, nickName){
        if(!confirm("Do you want to reset " + nickName + "'s password?")) {
            return;
        }
        $.ajax({
            url: '/api/user/resetPwd/'+id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                successShow(data.result)
            },
            error: function(data){
                failShow(data.responseText);
            }
        });
    }

    function successShow(message){
        $("#message").removeClass("alert-success");
        $("#message").removeClass("alert-danger");
        $("#message").addClass("alert-success");
        $("#message-content").html(message);
        $("#message").show();
    }

    function hideMsg(){
        $("#message").hide();
    }

    function failShow(message){
        $("#message").removeClass("alert-success");
        $("#message").removeClass("alert-danger");
        $("#message").addClass("alert-danger");
        $("#message-content").html(message);
        $("#message").show();
        window.setTimeout("hideMsg()",1000);
    }

    function showUser(id){
        $.ajax({
            url: '/api/user/'+id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("#userId").html(data.id);
                $("#nickName").html(data.nickName);
                $("#loginName").html(data.loginName);
                $("#email").html(data.email);
                if(data.mobile == undefined || data.mobile == ''){
                    $("#mobile").html("无");
                }else{
                    $("#mobile").html(data.mobile);
                }
                if(data.homePage == undefined || data.homePage == ''){
                    $("#homePage").html("无");
                }else{
                    $("#homePage").html(data.homePage);
                }
                $("#role").html(data.roleId);
                $("#createdAt").html(new Date(data.createdAt).Format("yyyy-MM-dd HH:mm:ss"));
                $("#updatedAt").html(new Date(data.updatedAt).Format("yyyy-MM-dd HH:mm:ss"));
            },
            error: function(data){
                failShow(data.responseText);
            }
        });
    }

</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




