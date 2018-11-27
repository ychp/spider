<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>
<meta charset="utf-8">
<title>${title}-Parsers</title>
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
            Parsers Manage (${parsers.total})
            <span class="smart-widget-option">
                <a href="#formInModal" class="btn btn-success" data-toggle="modal" style="color: #fff;" onclick="clearForm();">ADD</a>
            </span>
        </div>

        <div class="smart-widget-inner">
            <div class="smart-widget-body">
                <table class="table table-striped" id="dataTable">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Key</th>
                        <th>tag</th>
                        <th>CreateTime</th>
                        <th>Operator</th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list parsers.datas as parser>
                            <tr>
                                <td style="width: 5%">${parser.id}</td>
                                <td style="width: 30%; max-height: 30px; max-width:400px; overflow: hidden; word-break: normal;" title="${parser.name}">${parser.name}</td>
                                <td style="width: 10%">${parser.key?if_exists}</td>
                                <td style="width: 15%">${parser.tags?if_exists}</td>
                                <td style="width: 20%;">${parser.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
                                <td style="width: 15%">
                                    <a class="btn btn-sm btn-success" href="#formInModal" onclick="editShow(${parser.id})" data-toggle="modal">Edit</a>
                                    <a class="btn btn-sm btn-success" href="#detail" onclick="showDetail(${parser.id})" data-toggle="modal">Detail</a>
                                    <a class="btn btn-sm btn-danger" href="#del" onclick="del(${parser.id})" data-toggle="modal">Del</a>
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


<!-- Large modal -->
<div class="modal fade" id="formInModal">
    <div class="modal-dialog modal-mid">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Parser</h4>
            </div>
            <div class="modal-body">
                <form id="create" role="form">
                    <div class="form-group">
                        <label>Name</label>
                        <input type="hidden" name="id">
                        <input type="text" name="name" class="form-control" placeholder="name">
                    </div>
                    <div class="form-group">
                        <label>Key</label>
                        <input type="text" name="key" class="form-control" placeholder="key">
                    </div>
                    <div class="form-group">
                        <label>KeyWords</label>
                        <input type="text" name="keyWords" class="form-control" placeholder="keyWords">
                    </div>
                    <div class="form-group">
                        <label>VideoTag</label>
                        <input type="text" name="videoTag" class="form-control" placeholder="videoTag">
                    </div>
                    <div class="form-group">
                        <label>ImageTag</label>
                        <input type="text" name="imageTag" class="form-control" placeholder="imageTag">
                    </div>
                    <div class="form-group">
                        <label>TextTag</label>
                        <input type="text" name="textTag" class="form-control" placeholder="textTag">
                    </div>
                    <div class="form-group">
                        <label>SubTag</label>
                        <input type="text" name="subTag" class="form-control" placeholder="subTag">
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
                <h4 class="modal-title" id="myModalLabel">Parser(<label id="detailId"> </label>)</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label class="control-label col-lg-3">Name:</label>
                        <label class="control-label"id="detailName">Name</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Key:</label>
                        <label class="control-label"id="detailKey">Key</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">KeyWords:</label>
                        <label class="control-label"id="detailKeyWords">KeyWords</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">VideoTag:</label>
                        <label class="control-label"id="detailVideoTag">VideoTag</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">ImageTag:</label>
                        <label class="control-label"id="detailImageTag">ImageTag</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">TextTag:</label>
                        <label class="control-label"id="detailTextTag">TextTag</label>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">SubTag:</label>
                        <label class="control-label"id="detailSubTag">SubTag</label>
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
        $("input[name=key]").val("");
        $("input[name=keyWords]").val("");
        $("input[name=videoTag]").val("");
        $("input[name=imageTag]").val("");
        $("input[name=textTag]").val("");
        $("input[name=subTag]").val("");
    }

    function hideDetail(){
        $("#detail").hide();
    }

    function editShow(id){
        $.ajax({
            url: "/api/parser/" + id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("input[name=id]").val(data.id);
                $("input[name=name]").val(data.name);
                $("input[name=key]").val(data.key);
                $("input[name=keyWords]").val(data.keyWords);
                $("input[name=videoTag]").val(data.videoTag);
                $("input[name=imageTag]").val(data.imageTag);
                $("input[name=textTag]").val(data.textTag);
                $("input[name=subTag]").val(data.subTag);
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function showDetail(id){
        $.ajax({
            url: "/api/parser/" + id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("#detailId").html(data.id);
                $("#detailName").attr("title",data.name);
                $("#detailName").html(data.name);
                $("#detailKey").html(data.key);
                if(data.keyWords == ""){
                    $("#detailKeyWords").html("无");
                }else{
                    $("#detailKeyWords").html(data.keyWords);
                }
                if(data.videoTag == ""){
                    $("#detailVideoTag").html("无");
                }else{
                    $("#detailVideoTag").html(data.videoTag);
                }
                if(data.imageTag == ""){
                    $("#detailImageTag").html("无");
                }else{
                    $("#detailImageTag").html(data.imageTag);
                }
                if(data.textTag == ""){
                    $("#detailTextTag").html("无");
                }else{
                    $("#detailTextTag").html(data.textTag);
                }
                if(data.subTag == ""){
                    $("#detailSubTag").html("无");
                }else{
                    $("#detailSubTag").html(data.subTag);
                }
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
        var isDel = confirm("confirm delete Parser[id = " + id + "]");
        if(!isDel){
            return;
        }
        $.ajax({
            url: "/api/parser/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    successShow("delete Parser(" + id + ") success");
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
        $("#submit").click(function(){
            var id = $("input[name=id]").val();
            var message = "create Parser success";
            var url = "/api/parser/create";
            var data={};
            if(id != undefined && id != ""){
                message = "update Parser[id = " + id + "] success";
                url = "/api/parser/update";
                data['id']=id;
            }
            data['name']=$("input[name=name]").val();
            data['key']=$("input[name=key]").val();
            data['keyWords']=$("input[name=keyWords]").val();
            data['videoTag']=$("input[name=videoTag]").val();
            data['imageTag']=$("input[name=imageTag]").val();
            data['textTag']=$("input[name=textTag]").val();
            data['subTag']=$("input[name=subTag]").val();

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
    });
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




