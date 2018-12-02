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
            <a href="/" class="navbar-brand"><strong><span class="text-success">FM</span>Manager</strong></a>
        </div>
        <nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
            <ul class="nav navbar-nav">
                <#if online??>
                    <#if online.nickName??>
                        <#if online.nickName == 'admin'>
                            <li><a href="/users">User</a></li>
                        </#if>
                    <#elseif online.name??>
                        <#if online.name == 'admin'>
                            <li><a href="/users">User</a></li>
                        </#if>
                    </#if>
                <#else>

                </#if>
                <li><a href="/parsers">Parsers</a></li>
                <li><a href="/rules">Rules</a></li>
                <li><a href="/datas">Datas</a></li>
                <li><a href="/albums">Album</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <#if online??>
                    <#if online.nickName??>
                        <li class="btn-link"><a href="/" class="btn btn-sm">${online.nickName}</a></li>
                    <#elseif online.name??>
                        <li class="btn-link"><a href="/" class="btn btn-sm">${online.name}</a></li>
                    </#if>
                    <li class="btn-link"><a id="logout" href="javascript:void();" class="btn btn-sm" >Logout</a></li>
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
