<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
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

<script src="/static/ckeditor/ckeditor.js"></script>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header" style="margin-top:10px;height: 440px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            名称: ${rule.name}(${rule.code})<br>
            番号：${rule.code} <br>
            地址：<a href="${rule.url}">${rule.url}</a> <br>
            主图：${rule.mainImage} <br>
            <img src="${rule.mainImage}" width="400" style="max-height: 285px"/><br>
            <#list videos as data>
            <a href="${data.content}" style="text-decoration: solid">在线${data.level}</a> &nbsp;&nbsp;
            </#list>
        </div>
        视频:<br>
        <div class="smart-widget-inner">
            <#list videos as data>
                ${data.content}<br>
            </#list>
        </div>
        <div class="smart-widget-inner">
            <#list images as data>
            ${data.content}<br>
            </#list>
        </div>
        图片:<br>
        <div class="smart-widget-inner">
            <#list images as data>
            <img src="${data.content}" width="300"/>
            </#list>
        </div>
    </div>
</div><!-- /main-container -->

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




