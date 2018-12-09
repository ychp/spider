<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-爬虫(${parser.id})详情</title>
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
                            <h4 class="header-text m-top-md">爬虫(${parser.id})详情</h4>
                            <form class="form-horizontal m-top-md">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">名称</label>
                                    <div class="col-sm-9">
                                        <input disabled="disabled" type="text" class="form-control" value="${parser.name}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">类型</label>
                                    <div class="col-sm-9">
                                        <input disabled="disabled" type="text" class="form-control" value="${parser.parserType}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">地址</label>
                                    <div class="col-sm-9">
                                        <input disabled="disabled" type="text" class="form-control" value="${parser.url}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">规则</label>
                                    <div class="col-sm-9">
                                        <textarea disabled="disabled" class="form-control"><xmp>${parser.spiderRule}</xmp></textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">创建时间</label>
                                    <div class="col-sm-9">
                                        <input disabled="disabled" type="text" class="form-control"
                                               value="${parser.createdAt?string("yyyy-MM-dd HH:mm:ss")}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-3 control-label">最后一次更新时间</label>
                                    <div class="col-sm-9">
                                        <input disabled="disabled" type="text" class="form-control"
                                               value="${parser.updatedAt?string("yyyy-MM-dd HH:mm:ss")}">
                                    </div>
                                </div>
                            </form>
                        </div><!-- ./tab-pane -->
                    </div><!-- ./tab-content -->
                </div>
            </div>
        </div>
    </div>
</div><!-- /main-container -->
</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




