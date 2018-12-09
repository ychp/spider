<#macro defaultHeaser>
<header class="navbar front-end-navbar affix-top">
    <div class="container">
        <div class="navbar-header">
            <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a href="/" class="navbar-brand"><strong><span class="text-success">Spider</span> Manager</strong></a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <#if isAdmin??>
                    <#if isAdmin>
                        <li><a href="/users">用户</a></li>
                        <li><a href="/parser-types">爬虫类型</a></li>
                    </#if>
                </#if>
                <li><a href="/parsers">爬虫</a></li>
                <li><a href="/tasks">任务</a></li>
                <#if isAdmin??>
                    <#if isAdmin>
                        <li><a href="/albums">Album</a></li>
                    </#if>
                </#if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if online??>
                    <#if online.nickName??>
                        <li class="btn-link"><a href="/" class="btn btn-sm">${online.nickName}</a></li>
                    <#elseif online.name??>
                        <li class="btn-link"><a href="/" class="btn btn-sm">${online.name}</a></li>
                    </#if>
                    <li class="btn-link"><a id="logout" href="javascript:void();" class="btn btn-sm" >注销</a></li>
                <#else>

                </#if>
            </ul>
        </nav>
    </div>
</header>
<script type="text/javascript">
    $(function(){
        $("#logout").click(function(){
            $.ajax({
                url: "/api/user/logout",
                type: "POST",
                contentType: "application/json",
                success: function (data) {
                    if(data === true){
                        window.location.href="/";
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
