<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-爬虫<#if parser??>(${parser.id})编辑<#else>创建</#if></title>
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
    <div class="col-md-8" style="float: inherit; margin: auto; background-color: white">
        <div class="smart-widget" style="border: 0px;background-color: transparent;box-shadow: none;">
            <div class="smart-widget-inner">
                <div class="smart-widget-body">
                    <div class="tab-content">
                        <div class="tab-pane fade active in" id="profileTab2">
                            <h4 class="header-text m-top-md"><#if parser??>(${parser.id})编辑<#else>创建</#if></h4>
                            <form class="form-horizontal m-top-md">
                            <#if parser??>
                                <input type="hidden" class="form-control" name="id" value="${parser.id}">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">名称</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="name" value="${parser.name}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">类型</label>
                                    <div class="col-sm-9">
                                        <select name="parserType" disabled>
                                            <option value="">请选择</option>
                                            <#list types as type>
                                                <#if parser.id == type.id>
                                                    <option value="${type.id}" selected>${type.name}</option>
                                                <#else>
                                                    <option value="${type.id}">${type.name}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">地址</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="url" value="${parser.url!}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">规则</label>
                                    <div class="col-sm-9">
                                        <textarea class="form-control" name="spiderRule">${parser.spiderRule}</textarea>
                                    </div>
                                </div>
                            <#else>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">名称</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="name" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">类型</label>
                                    <div class="col-sm-9">
                                        <select name="parserType">
                                            <option value="">请选择</option>
                                            <#list types as type>
                                                <option value="${type.id}">${type.name}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">地址</label>
                                    <div class="col-sm-9">
                                        <input type="text" class="form-control" name="url" value="http://">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">规则</label>
                                    <div class="col-sm-9">
                                        <textarea class="form-control" name="spiderRule"></textarea>
                                    </div>
                                </div>
                            </#if>

                            </form>
                            <div class="form-group text-center">
                                <a id="submit" class="btn btn-info m-top-md">提交</a>
                            </div>
                        </div><!-- ./tab-pane -->
                    </div><!-- ./tab-content -->
                </div>
            </div>
        </div>
    </div>
</div><!-- /main-container -->

<script type="text/javascript">
    $(function () {
        $("#submit").click(function () {
            var id = $("input[name=id]").val();
            var message = "create Parser success";
            var url = "/api/parser";
            var method = "POST";
            var data = {};
            if (id !== undefined && id !== "") {
                message = "update Parser[id = " + id + "] success";
                method = "PUT";
                data['id'] = id;
            }

            data['name'] = $("input[name=name]").val();
            data['url'] = $("input[name=url]").val();
            data['parserTypeId'] = $("select[name=parserType]").val();
            data['spiderRule'] = $("textarea[name=spiderRule]").val();

            $.ajax({
                url: url,
                type: method,
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function (data) {
                    if (data) {
                        alert(message);
                        window.location.href="/parsers";
                    }
                },
                error: function (data) {
                    if(data.status === 401) {
                        window.location.href="/login";
                    }
                    alert(data.responseText);
                }
            });
        });
    });
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




