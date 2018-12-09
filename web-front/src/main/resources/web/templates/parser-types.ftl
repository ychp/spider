<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-爬虫类型</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Bootstrap core CSS -->
<link href="/static/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<!-- Font Awesome -->
<link href="/static/css/font-awesome.min.css" rel="stylesheet">

<!-- Simplify -->
<link href="/static/css/simplify.min.css" rel="stylesheet">
<!-- Jquery -->
<script src="/static/js/jquery-1.11.1.min.js"></script>

<!-- Bootstrap -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>

<!-- Simplify -->
<script src="/static/js/date-utils.js"></script>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
        <div class="smart-widget-header"
             style="margin-top:10px;height: 50px;line-height:30px;padding-top: 7px;padding-bottom: 7px;">
            爬虫类型管理 (${parserTypes.total})
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Key</th>
                        <th>名称</th>
                        <th>创建时间</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list parserTypes.datas as parserType>
                        <tr>
                            <td style="width: 5%">${parserType.id}</td>
                            <td style="width: 10%">${parserType.key!}</td>
                            <td style="width: 30%; max-height: 30px; max-width:400px; overflow: hidden; word-break: normal;"
                                title="${parserType.name}">${parserType.name}</td>
                            <td style="width: 10%;">${parserType.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                        </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=criteria.pageNo size=criteria.pageSize total=parserTypes.total url='/parser-types' ></@pagination>
            </div>
        </div>
    </div>
</div><!-- /main-container -->
</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




