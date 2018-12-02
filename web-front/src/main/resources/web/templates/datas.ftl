<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#import "template/text.ftl" as text>
<#import "template/tags.ftl" as tags>
<#import "template/image.ftl" as image>
<#import "template/video.ftl" as video>
<#import "template/detail/text-detail.ftl" as textDetail>
<#import "template/detail/tags-detail.ftl" as tagsDetail>
<#import "template/detail/image-detail.ftl" as imageDetail>
<#import "template/detail/video-detail.ftl" as videoDetail>
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-Datas</title>
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

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header" style="margin-top:10px;height: 50px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            Datas Manage ( ${datas.total} )
            <span class="smart-widget-option text-right" style="width: auto; min-width: 645px; max-width: 645px;">
                规则类型:
                <select name="ruleType" class="form-control" style="display: inline;max-width: 100px">
                    <option value="" <#if ruleType??><#else>selected</#if>>全部</option>
                    <#list ruleTypes as item>
                        <option value="${item}" <#if ruleType?? && item == ruleType>selected</#if>>${item}</option>
                    </#list>
                </select>
                演员:
                <select name="actor" class="form-control" style="display: inline;max-width: 100px">
                    <option value="" <#if actor??><#else>selected</#if>>全部</option>
                    <#list ruleActors as ruleActor>
                        <option value="${ruleActor}" <#if actor?? && ruleActor == actor>selected</#if>>${ruleActor}</option>
                    </#list>
                </select>
                类型:
                <select name="type" class="form-control" style="display: inline;max-width: 200px">
                    <option value="" <#if type??><#else>selected</#if>>全部</option>
                    <option value="1" <#if type?? && type = 1>selected</#if>>视频</option>
                    <option value="2" <#if type?? && type = 2>selected</#if>>图片</option>
                    <option value="3" <#if type?? && type = 3>selected</#if>>文本</option>
                    <option value="4" <#if type?? && type = 4>selected</#if>>标签</option>
                </select>
            </span>
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <#if type??>
                    <#switch type>
                        <#case 1>
                            <@video.table></@video.table>
                            <#break>
                        <#case 2>
                            <@image.table></@image.table>
                            <#break>
                        <#case 3>
                            <@text.table></@text.table>
                            <#break>
                        <#case 4>
                            <@tags.table></@tags.table>
                            <#break>
                        <#default>
                            <@text.table></@text.table>
                    </#switch>
                <#else>
                    <@text.table ></@text.table>
                </#if>
                <@pagination pageNo=datas.pageNo size=datas.pageSize total=datas.total url='/datas'  params="&ruleType=${ruleType?if_exists}&type=${type?if_exists}&actor=${actor?if_exists}"></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<#if type??>
    <#switch type>
        <#case 1>
            <@videoDetail.detail></@videoDetail.detail>
            <#break>
        <#case 2>
            <@imageDetail.detail></@imageDetail.detail>
            <#break>
        <#case 3>
            <@textDetail.detail></@textDetail.detail>
            <#break>
        <#case 4>
            <@tagsDetail.detail></@tagsDetail.detail>
            <#break>
        <#default>
            <@textDetail.detail></@textDetail.detail>
    </#switch>
<#else>
    <@textDetail.detail></@textDetail.detail>
</#if>

<div id="message" class="alert alert-success alert-dismissible" role="alert" style="position: absolute;top:25%;left: 20%;width: 60%;z-index: 999;display: none;">
    <button type="button" class="close" onclick="hideMsg()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
    <i id="message-icon" class="fa fa-check-circle m-right-xs"></i><strong id="message-title">Well done!</strong><label id="message-content">You successfully read this important alert message.</label>
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

    function clearForm(){
        $("input[name=id]").val("");
        $("input[name=url]").val("");
        $("input[name=keyWords]").val("");
        $("input[name=videoTag]").val("");
        $("input[name=imageTag]").val("");
        $("input[name=textTag]").val("");
        $("input[name=subTag]").val("");
    }

    function hideDetail(){
        $("#detail").hide();
    }

    function del(id){
        var isDel = confirm("confirm delete Data[id = " + id + "]");
        if(!isDel){
            return;
        }
        $.ajax({
            url: "/api/data/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    successShow("delete Data(" + id + ") success");
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function success(){
        $("#message").hide();
        window.location.reload();
    }

    function successShow(message){
        $("#message").addClass("alert-success");
        $("#message-icon").addClass("fa-check-circle");
        $("#message-title").html("Well done!");
        $("#message-content").html(message);
        $("#message").show();
        window.setTimeout("success()",3000);
    }

    function hideMsg(){
        $("#message").hide();
    }

    function failShow(message){
        $("#message").addClass("alert-danger");
        $("#message-icon").addClass("fa-times-circle");
        $("#message-title").html("Oh snap!");
        $("#message-content").html(message);
        $("#message").show();
        window.setTimeout("hideMsg()",3000);
    }


    $(function(){
        hideDetail();

        $("select[name=type]").change(function(){
            var type = $("select[name=type]").val();
            var ruleType = $("select[name=ruleType]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/datas?type=" + type + "&ruleType=" + ruleType + "&actor=" + actor
        });

        $("select[name=ruleType]").change(function(){
            var type = $("select[name=type]").val();
            var ruleType = $("select[name=ruleType]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/datas?type=" + type + "&ruleType=" + ruleType + "&actor=" + actor
        });

        $("select[name=actor]").change(function(){
            var type = $("select[name=type]").val();
            var ruleType = $("select[name=ruleType]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/datas?type=" + type + "&ruleType=" + ruleType + "&actor=" + actor
        });

    });
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




