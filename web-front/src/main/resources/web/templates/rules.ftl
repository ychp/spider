<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-Rules</title>
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
            Rules Manage (${rules.total})
            <span class="smart-widget-option" style="width: auto; min-width: 615px; max-width: 615px;">
                类型:
                <select name="type" class="form-control" style="display: inline;max-width: 100px">
                    <option value="" <#if type??><#else>selected</#if>>全部</option>
                    <#list ruleTypes as ruleType>
                        <option value="${ruleType}" <#if type?? && ruleType == type>selected</#if>>${ruleType}</option>
                    </#list>
                </select>
                演员:
                <select name="actor" class="form-control" style="display: inline;max-width: 100px">
                    <option value="" <#if actor??><#else>selected</#if>>全部</option>
                    <#list ruleActors as ruleActor>
                        <option value="${ruleActor}" <#if actor?? && ruleActor == actor>selected</#if>>${ruleActor}</option>
                    </#list>
                </select>
                状态:
                <select name="status" class="form-control" style="display: inline;max-width: 100px">
                    <option value="" <#if status??><#else>selected</#if>>全部</option>
                    <option value="-1" <#if status?? && status==-1>selected</#if>>抓取错误</option>
                    <option value="0" <#if status?? && status==0>selected</#if>>初始化</option>
                    <option value="1" <#if status?? && status==1>selected</#if>>抓取中</option>
                    <option value="2" <#if status?? && status==2>selected</#if>>抓取完成</option>
                    <option value="3" <#if status?? && status==3>selected</#if>>下载图片完毕</option>
                    <option value="4" <#if status?? && status==4>selected</#if>>下载数据完毕</option>
                </select>
                <a href="#" class="btn btn-success" data-toggle="modal" style="color: #fff;" onclick="clearCache();">Clear</a>
                <a href="#formInModal" class="btn btn-success" data-toggle="modal" style="color: #fff;" onclick="clearForm();">ADD</a>
                <a href="#spiders" class="btn btn-success" data-toggle="modal" style="color: #fff;">Spider</a>
            </span>
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th style="width: 5%">ID</th>
                        <th style="width: 30%; max-height: 30px; max-width:1500px;" >名称</th>
                        <th style="width: 20%">类型</th>
                        <th style="width: 10%">状态</th>
                        <th style="width: 8%">Images</th>
                        <th style="width: 7%">Videos</th>
                        <th style="width: 20%">Operator</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list rules.datas as rule>
                            <tr>
                                <td>${rule.id?c}</td>
                                <td style="max-width:1500px; overflow: hidden; word-break: normal;" title="${rule.name}">
                                    <a href="${rule.url?replace("/page/{pageNo}","")}" target="_blank">
                                        <#if rule.mainImage?exists>
                                            <img style="width: 294px;height: 220px;" src="${rule.mainImage}">
                                            <br/>
                                        </#if>
                                        <#if rule.extra?exists>
                                            <#if rule.extra != ''>
                                                <img style="width: 294px;height: 220px;" src="${rule.extra}">
                                                <br/>
                                            </#if>
                                        </#if>
                                        <#if rule.name?length gt 30>
                                        ${rule.name[0..30]}...
                                        <#else>
                                        ${rule.name}
                                        </#if>
                                    </a>
                                </td>
                                <td>
                                    ${rule.types?if_exists}
                                    <br/>
                                    <br/>
                                    ${rule.actors?if_exists}
                                </td>
                                <td>${rule.statusStr?if_exists}</td>
                                <td>${rule.imageCount?default(0)}</td>
                                <td>${rule.videoCount?default(0)}</td>
                                <#--<td style="width: 15%;">${rule.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>-->
                                <td>
                                    <a class="btn btn-sm btn-success" href="#formInModal" onclick="editShow(${rule.id?c})" data-toggle="modal">Edit</a>
                                    <a class="btn btn-sm btn-success" href="#detail" onclick="showDetail(${rule.id?c})" data-toggle="modal">Detail</a>
                                    <a class="btn btn-sm btn-danger" href="#del" onclick="del(${rule.id?c})" data-toggle="modal">Del</a>
                                    <a class="btn btn-sm btn-success" href="#spiderOne" onclick="spiderOne(${rule.id?c})">Start</a>
                                    <a class="btn btn-sm btn-success" href="/data-urls?ruleId=${rule.id?c}" target="_blank">URLS</a>
                                </td>
                            </tr>
                        </#list>
                    </tbody>
                </table>
                <@pagination pageNo=rules.pageNo size=rules.pageSize total=rules.total url='/rules' params="&status=${status?if_exists}&type=${type?if_exists}&actor=${actor?if_exists}"></@pagination>
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
                <h4 class="modal-title" id="myModalLabel">Rule</h4>
            </div>
            <div class="modal-body">
                <form id="create" role="form">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="hidden" name="id">
                        <input type="text" name="name" class="form-control" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label>Code</label>
                        <input type="text" name="code" class="form-control" placeholder="code">
                    </div>
                    <div class="form-group">
                        <label>URL</label>
                        <input type="text" name="url" class="form-control" placeholder="url">
                    </div>
                    <div class="form-group">
                        <label>主图</label>
                        <input type="text" name="mainImage" class="form-control" placeholder="mainImage">
                    </div>
                </form>
                <div class="form-group text-center">
                    <a id="submit" class="btn btn-info m-top-md">Submit</a>
                    <a class="btn btn-default m-top-md" data-dismiss="modal" >Cancel</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Large modal -->
<div class="modal fade" id="detail">
    <div class="modal-dialog modal-mid">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Rule(<label id="detailId"> </label>)</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label class="control-label col-lg-3">Name:</label>
                        <label class="control-label"id="detailName" style="max-width: 300px; word-break: break-all">Name</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Code:</label>
                        <label class="control-label"id="detailCode" style="max-width: 300px; word-break: break-all">Code</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">URL:</label>
                        <label class="control-label"id="detailURL" style="max-width: 300px; word-break: break-all">URL</label>
                    </div>
                     <div class="form-group">
                        <label class="control-label col-lg-3">主图:</label>
                        <img id="detailImage" src="">
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Status:</label>
                        <label class="control-label"id="detailStatus">Status</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">CreateDate:</label>
                        <label class="control-label"id="detailCreate">CreateDate</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">UpdateDate:</label>
                        <label class="control-label"id="detailUpdate">UpdateDate</label>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Large modal -->
<div class="modal fade" id="spiders">
    <div class="modal-dialog modal-sm">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Rule</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label class="control-label col-lg-3">Parser:</label>
                        <select name="parserId" class="form-control">
                            <#list parsers as parser>
                                <option value="${parser.id}">${parser.name}</option>
                            </#list>
                        </select>
                    </div>
                </form>
                <div class="form-group text-center">
                    <a id="spidersBtn" class="btn btn-info m-top-md">Submit</a>
                    <a class="btn btn-default m-top-md" data-dismiss="modal" >Cancel</a>
                </div>
            </div>
        </div>
    </div>
</div>

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
        $("input[name=name]").val("");
        $("input[name=code]").val("");
        $("input[name=url]").val("");
    }

    function hideDetail(){
        $("#detail").hide();
    }

    function clearCache(){
        $.ajax({
            url: "/api/rule/clearCache",
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    successShow("clear cache success");
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function editShow(id){
        $.ajax({
            url: "/api/rule/" + id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("input[name=id]").val(data.id);
                $("input[name=name]").val(data.name);
                $("input[name=code]").val(data.code);
                $("input[name=url]").val(data.url);
                $("input[name=mainImage]").val(data.mainImage);
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function showDetail(id){
        $.ajax({
            url: "/api/rule/" + id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("#detailId").html(data.id);
                $("#detailName").html(data.name);
                $("#detailURL").attr("title",data.url);
                $("#detailURL").html(data.url);
                $("#detailImage").attr("src", data.mainImage);
                $("#detailCode").html(data.code);
                $("#detailStatus").html(data.statusStr);
                $("#detailCreate").html(new Date(data.createdAt).Format("yyyy-MM-dd HH:mm:ss"));
                $("#detailUpdate").html(new Date(data.updatedAt).Format("yyyy-MM-dd HH:mm:ss"));
                var forms = $("#detail .form-group");
                if (forms != null) {
                    for (var i = 0; i < forms.length; i++) {
                        var obj=forms[i].childNodes[3];
                        if (forms[i].style.height > parseInt(obj.style.height)) {
                            forms[i].style.height = obj.style.height;
                        }
                    }
                }
                $("#detail form").style.height=auto;
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function del(id){
//        var isDel = confirm("confirm delete Rule[id = " + id + "]");
//        if(!isDel){
//            return;
//        }
        $.ajax({
            url: "/api/rule/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    successShow("delete Rule(" + id + ") success");
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function spiderOne(id){
        $.ajax({
            url: "/api/rule/" + id + "/spider",
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    successShow("start spider Rule(" + id + ") success");
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
        window.setTimeout("success()", 1000);
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
        $("#submit").click(function(){
            var id = $("input[name=id]").val();
            var message = "create Rule success";
            var url = "/api/rule/create";
            var data={};
            if(id != undefined && id != ""){
                url = "/api/rule/update";
                data['id']=id;
            }
            data['name']=$("input[name=name]").val();
            data['url']=$("input[name=url]").val();
            data['code']=$("input[name=code]").val();
            data['mainImage']=$("input[name=mainImage]").val();

            $.ajax({
                url: url,
                type: "POST",
                data: JSON.stringify(data),
                contentType: "application/json",
                success: function (data) {
                    if(data){
                        window.location.reload();
                    }
                },
                error: function(data){
                    failShow(data.responseText);
                }
            });
        });

        $("#spidersBtn").click(function(){
            var type = $("select[name=type]").val();
            var actor = $("select[name=actor]").val();
            $.ajax({
                url: '/api/rule/spiderAll?type=' + type + '&actor=' + actor,
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    if(data){
                        successShow("start spider Rules success");
                    }
                },
                error: function(data){
                    alert(data.responseText);
                }
            });
        });

        $("select[name=status]").change(function(){
            var status = $("select[name=status]").val();
            var type = $("select[name=type]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/rules?status=" + status + '&type=' + type + '&actor=' + actor
        });

        $("select[name=type]").change(function(){
            var status = $("select[name=status]").val();
            var type = $("select[name=type]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/rules?status=" + status + '&type=' + type + '&actor=' + actor
        });

        $("select[name=actor]").change(function(){
            var status = $("select[name=status]").val();
            var type = $("select[name=type]").val();
            var actor = $("select[name=actor]").val();
            window.location.href = "/rules?status=" + status + '&type=' + type + '&actor=' + actor
        });
    });
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




