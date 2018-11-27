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
<!-- jQuery Easing -->
<script src="/static/album/js/jquery.easing.1.3.js"></script>
<!-- Bootstrap -->
<script src="/static/album/js/bootstrap.min.js"></script>
<!-- Waypoints -->
<script src="/static/album/js/jquery.waypoints.min.js"></script>
<!-- Magnific Popup -->
<script src="/static/album/js/jquery.magnific-popup.min.js"></script>

<!-- Slimscroll -->
<script src='/static/js/jquery.slimscroll.min.js'></script>

<!-- Popup Overlay -->
<script src='/static/js/jquery.popupoverlay.min.js'></script>

<!-- Modernizr -->
<script src='/static/js/modernizr.min.js'></script>

<!-- Simplify -->
<script src="/static/js/simplify/simplify.js"></script>

<!-- Animate.css -->
<link rel="stylesheet" href="/static/album/css/animate.css">
<!-- Magnific Popup -->
<link rel="stylesheet" href="/static/album/css/magnific-popup.css">
<!-- Salvattore -->
<link rel="stylesheet" href="/static/album/css/salvattore.css">
<!-- Theme Style -->
<link rel="stylesheet" href="/static/album/css/album.css">

<style type="text/css">
    .closeBtn{
        position: fixed;
        right: 5px;
        font-size: 25px;
        z-index: 999;
    }
    .source{
        position: fixed;
        top: 2px;
        right: 15px;
        font-size: 25px;
        z-index: 999;
    }
</style>

</#macro>
<#macro overrideContainer>
<div class="main-container" style="margin-left: 15px;margin-right: 15px;">
    <div id="fh5co-main" style="padding-top: 5px;">
        <div class="container">
            <div class="row">
                <div id="fh5co-board" data-columns>
                </div>
            </div>
        </div>
    </div>
    <a href="#" class="scroll-to-top hidden-print"><i class="fa fa-chevron-up fa-lg" style="margin-top: 35%;"></i></a>
</div><!-- /main-container -->

<!-- Salvattore -->
<script src="/static/album/js/salvattore.min.js"></script>
<!-- Main JS -->
<script src="/static/album/js/album.js"></script>

<script src="/static/js/synchronized.js"></script>

<script type="text/javascript">

    var ruleId = ${RequestParameters.ruleId!0};

    var pageNo=0;

    var isFinish=false

    function image(id, url, source){
        return $('<div id="img' + id + '" class="item">'
                +'<div class="animate-box">'
                +'<button type="button" class="close closeBtn" onclick="del(' + id + ')">&times;</button>'
                +'<a class="close source" href="' + source + '" target="_blank">&oplus;</a>'
                +'<a href="' + url + '" class="image-popup fh5co-board-img">'
                +'<img src="' + url + '" style="min-height: 50px;min-width: 50px;">'
                +'</a>'
                +'</div>'
                +'</div>')
    }

    function del(id){
        var isDel = confirm("confirm delete Data[id = " + id + "]");
        if(!isDel){
            return;
        }
        $.ajax({
            url: "/api/data/delete/" + id,
            type: "PUT",
            contentType: "application/json",
            success: function (data) {
                if(data){
                    var column = $("#img"+id).parent()
                    $("#img"+id).remove()
                    $(column).height(auto(column))
                }
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

    function nextPage(){
        if(jsynchronized("img",1000)){
            pageNo++;
            $.ajax({
                url: "/api/data/album",
                data: {pageNo: pageNo, pageSize:50, ruleId: ruleId},
                type: "GET",
                contentType: "application/json",
                success: function (data) {
                    var datas = data.datas
                    if(datas.length < data.pageSize){
                        isFinish = true
                    }
                    var columns = $(".column")
                    var url;
                    for (var i = 0; i < datas.length; i++) {
//                        if(datas[i].path!=undefined && datas[i].path!=""){
//                            url = datas[i].path;
//                        } else {
                            url = datas[i].content
//                        }
                        $(columns[i%4]).append(image(datas[i].id, url, datas[i].source))
                    }
                    magnifPopup();
                    animateBoxWayPoint();
                },
                error: function(data){
                    alert(data.responseText);
                }
            });
        }
    }

    $(function(){nextPage()})
</script>

</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




