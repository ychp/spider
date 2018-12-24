<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-任务</title>
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
            爬虫任务管理 (${tasks.total})
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>名称</th>
                        <th>状态</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list tasks.datas as task>
                        <tr>
                            <td style="width: 5%">${task.id}</td>
                            <td style="width: 30%; max-height: 30px; max-width:400px; overflow: hidden; word-break: normal;"
                                title="${task.name}">${task.name}</td>
                            <td style="width: 10%;">${task.statusStr}</td>
                            <td style="width: 15%;">${task.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                            <td style="width: 15%">
                                <#if task.status == 0>
                                    <a class="btn btn-sm btn-success" href="#task" onclick="task(${task.id})">执行</a>
                                </#if>
                                <#if task.status == 2 || task.status == 1>
                                    <a class="btn btn-sm btn-success" href="/datas?taskId=${task.id}" target="_blank">结果</a>
                                </#if>
                                <#if task.status != 1>
                                    <a class="btn btn-sm btn-danger" href="#del" onclick="del(${task.id})">删除</a>
                                </#if>
                            </td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=criteria.pageNo size=criteria.pageSize total=tasks.total url='/tasks' ></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<script type="text/javascript">

    function task(id) {
        var message = "process Task success";

        $.ajax({
            url: "/api/task/" + id,
            type: "PUT",
            success: function (data) {
                if (data) {
                    alert(message);
                    window.location.href = "/tasks";
                }
            },
            error: function (data) {
                if (data.status === 401) {
                    window.location.href = "/login";
                }
                alert(data.responseText);
            }
        });
    }

    function del(id) {
        var isDel = confirm("confirm delete Task[id = " + id + "]");
        if (!isDel) {
            return;
        }
        $.ajax({
            url: "/api/task/" + id,
            type: "DELETE",
            success: function (data) {
                if (data) {
                    alert("delete Task(" + id + ") success");
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




