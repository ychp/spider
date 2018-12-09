<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-爬虫</title>
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

<!-- Simplify -->
<script src="/static/js/simplify/simplify.js"></script>
<script src="/static/js/date-utils.js"></script>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header"
             style="margin-top:10px;height: 50px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            爬虫管理 (${parsers.total})
            <span class="smart-widget-option">
                <a href="/parser" class="btn btn-success" style="color: #fff;" target="_blank">添加</a>
            </span>
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>名称</th>
                        <th>类型</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list parsers.datas as parser>
                        <tr>
                            <td style="width: 5%">${parser.id}</td>
                            <td style="width: 30%; max-height: 30px; max-width:400px; overflow: hidden; word-break: normal;"
                                title="${parser.name}">${parser.name}</td>
                            <td style="width: 30%">${parser.parserType!}</td>
                            <td style="width: 15%;">${parser.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td style="width: 15%">
                                <a class="btn btn-sm btn-success" href="/parser?id=${parser.id}"
                                   target="_self">编辑</a>
                                <a class="btn btn-sm btn-success" href="/${parser.id}/parser-detail"
                                   target="_self">详情</a>
                                <a class="btn btn-sm btn-danger" href="#del" onclick="del(${parser.id})"
                                   data-toggle="modal">删除</a>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=parsers.pageNo size=parsers.pageSize total=parsers.total url='/parsers' ></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<script type="text/javascript">

    function del(id) {
        var isDel = confirm("confirm delete Parser[id = " + id + "]");
        if (!isDel) {
            return;
        }
        $.ajax({
            url: "/api/parser/" + id,
            type: "DELETE",
            contentType: "application/json",
            success: function (data) {
                if (data) {
                    alert("delete Parser(" + id + ") success");
                    window.location.reload();
                }
            },
            error: function (data) {
                alert(data.responseText);
            }
        });
    }
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




