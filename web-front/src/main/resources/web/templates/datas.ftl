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
<title>${title}-结果</title>
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

<script src="/static/js/date-utils.js"></script>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header"
             style="margin-top:10px;height: 50px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            结果 ( ${datas.total} )
            <span class="smart-widget-option text-right" style="width: auto; min-width: 645px; max-width: 645px;">
                <input type="hidden" name="taskId" value="${criteria.taskId}">
                类型:
                <select name="type" class="form-control" style="display: inline;max-width: 200px">
                    <option value="" <#if criteria.type??><#else>selected</#if>>全部</option>
                    <option value="1" <#if criteria.type?? && criteria.type = 1>selected</#if>>视频</option>
                    <option value="2" <#if criteria.type?? && criteria.type = 2>selected</#if>>图片</option>
                    <option value="3" <#if criteria.type?? && criteria.type = 3>selected</#if>>文本</option>
                    <option value="4" <#if criteria.type?? && criteria.type = 4>selected</#if>>标签</option>
                </select>
            </span>
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <#if criteria.type??>
                    <#switch criteria.type>
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
                <@pagination pageNo=criteria.pageNo size=criteria.pageSize total=datas.total url='/datas'  params="&taskId=${criteria.taskId!}&type=${criteria.type!}"></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

    <#if criteria.type??>
        <#switch criteria.type>
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

<script type="text/javascript">

    function del(id) {
        var isDel = confirm("confirm delete Data[id = " + id + "]");
        if (!isDel) {
            return;
        }
        $.ajax({
            url: "/api/data/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if (data) {
                    alert("delete Data(" + id + ") success");
                }
            },
            error: function (data) {
                alert(data.responseText);
            }
        });
    }


    $(function () {

        $("select[name=type]").change(function () {
            var type = $("select[name=type]").val();
            var taskId = $("input[name=taskId]").val();
            window.location.href = "/datas?type=" + type + "&taskId=" + taskId
        });


    });
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




