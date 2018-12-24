<#macro detail >
    <#--<#include "../../taglib/video.ftl">-->

<!-- Large modal -->
<div class="modal fade" id="detail">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="clearDetail()">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Data(<label  class="control-labe" id="detailId"> </label>)</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <#--<div class="form-group text-center">-->
                        <#--<img id="detailImage">-->
                    <#--</div>-->
                    <div class="form-group text-center">
                        <iframe id="videoSource" style="height:540px;width: 540px;"></iframe>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Path:</label>
                        <textarea id="detailPath" style="width: 70%; max-width: 400px; word-break: break-all; resize: none;" disabled>
                        </textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Type:</label>
                        <label class="control-label"id="detailType">Type</label>
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

<script type="text/javascript">
    function showDetail(id){
        $.ajax({
            url: "/api/data/" + id,
            type: "GET",
            contentType: "application/json",
            success: function (data) {
                $("#detailId").html(data.id);
                $("#video").attr("poster", data.url);
                $("#videoSource").attr("src", data.content);
                $("#detailPath").html(data.path);
                $("#detailType").html(data.typeStr);
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
                $('video').video();
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }
    function clearDetail(){
        $("#detailId").html("");
        $("#videoSource").attr("src", "");
    }
</script>
</#macro>