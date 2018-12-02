<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-用户管理</title>
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
            用户管理
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>昵称</th>
                        <th>用户名</th>
                        <th>联系方式</th>
                        <th>注册时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list users.datas as user>
                            <tr>
                                <td style="width: 3%">${user.id}</td>
                                <td style="width: 3%">${user.nickName}</td>
                                <td style="width: 5%">${user.name}</td>
                                <td style="width: 20%">
                                    Email : ${user.email!}
                                    <#if user.mobile??>
                                        <br>
                                        Mobile : ${user.mobile}
                                    </#if>
                                </td>
                                <td style="width: 10%;">${user.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=users.pageNo size=users.pageSize total=users.total url='/users' ></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<script type="text/javascript">
    Date.prototype.Format = function(fmt) { //author: meizz
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
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        return fmt;
    };

</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




