<#macro detail >
<!-- Large modal -->
<div class="modal fade" id="detail">
    <div class="modal-dialog modal-mid">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">Data(<label  class="control-labe" id="detailId"> </label>)</h4>
            </div>
            <div class="modal-body">
                <form role="form">
                    <div class="form-group">
                        <label class="control-label col-lg-3">URL:</label>
                        <textarea id="detailURL"
                                  style="width: 70%; max-width: 400px; word-break: break-all; resize: none;"
                                  disabled>
                        </textarea>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-lg-3">Content:</label>
                        <textarea id="detailContent"
                                  style="width: 70%; max-width: 400px; word-break: break-all; resize: none;"
                                  rows="5" disabled>
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
                $("#detailId").html(data.ruleId + "-" + data.id);
                if(data.url === "" || data.url === undefined){
                    $("#detailURL").html("无");
                }else {
                    $("#detailURL").attr("title",data.url);
                    $("#detailURL").html(data.url);
                }
                $("#detailContent").html(data.content);
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
            },
            error: function(data){
                alert(data.responseText);
            }
        });
    }

</script>
</#macro>