<#include "layout/layout.ftl">
<#include "taglib/pagination.ftl">
<#macro overrideHead>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>${title}-Album </title>
<meta name="viewport" content="width=device-width, initial-scale=1">

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

<!-- jQuery Easing -->
<script src="/static/album/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="/static/album/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="/static/album/js/jquery.waypoints.min.js"></script>
<!-- Magnific Popup -->
<script src="/static/album/js/jquery.magnific-popup.min.js"></script>


<!-- Animate.css -->
<link rel="stylesheet" href="/static/album/css/animate.css">
<!-- Icomoon Icon Fonts-->
<#--<link rel="stylesheet" href="/static/album/css/icomoon.css">-->
<!-- Magnific Popup -->
<link rel="stylesheet" href="/static/album/css/magnific-popup.css">
<!-- Salvattore -->
<link rel="stylesheet" href="/static/album/css/salvattore.css">
<!-- Theme Style -->
<link rel="stylesheet" href="/static/album/css/album.css">

<style type="text/css">
    .closeBtn{
        position: fixed;
        right: 3px;
        font-size: 25px;
        z-index: 999;
    }
    .download{
        float: none;
        font-size: 25px;
        z-index: 999;
    }
</style>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;min-height: 490px;">
    <div id="fh5co-main" style="padding-top: 10px;">
        <div class="container">
            <div class="row">
                <div id="fh5co-board" data-columns>
                    <#list albums as album>
                        <div class="item" style="height: 360px;">
                            <div class="animate-box bounceIn animated" style="height: 285px;vertical-align: middle;">
                                <button type="button" class="close closeBtn" onclick="del(${album.ruleId?c})">&times;</button>
                                <a href="/album-detail?ruleId=${album.ruleId?c}" class="image-popup fh5co-board-img">
                                    <img src="${album.image}" style="height: 285px;width: 225px;"></a>
                            </div>
                            <div class="fh5co-desc" style="padding:10;width: 100%; text-align: center;" title="${album.ruleId?c}.${album.name}">
                                <#if album.name?length gt 20>
                                ${album.name[0..20]}...
                                <#else>
                                ${album.name}
                                </#if>(${album.size!})/
                                    <#if album.status==3 || album.status==4>
                                        <span style="color: #00aced">${album.statusStr}</span>
                                    <#else>
                                        <span style="color: red;">${album.statusStr}</span>
                                    </#if>
                                    <br>
                                <button type="button" class="close download" onclick="download(${album.ruleId?c})"><i class="fa fa-download"></i></button>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <a href="#" class="scroll-to-top hidden-print"><i class="fa fa-chevron-up fa-lg" style="margin-top: 35%;"></i></a>
</div><!-- /main-container -->

<!-- Salvattore -->
<script src="/static/album/js/salvattore.min.js"></script>
<!-- Main JS -->
<#--<script src="/static/album/js/album.js"></script>-->

<script type="text/javascript">

    function del(id){
        var isDel = confirm("confirm delete Album[id = " + id + "]");
        if(!isDel){
            return;
        }
        $.ajax({
            url: "/api/album/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    window.location.reload()
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function download(id){
        var isDel = confirm("confirm download Album[id = " + id + "]");
        if(!isDel){
            return;
        }
        $.ajax({
            url: "/api/album/download/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    window.location.reload()
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




