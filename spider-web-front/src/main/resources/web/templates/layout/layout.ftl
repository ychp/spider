<#import "head.ftl" as headTemplate>
<#import "foot.ftl" as footTemplate>
<#import "header.ftl" as headerTemplate>
<#macro defaultHead></#macro>
<#macro defaultContainer></#macro>
<#macro layout head=defaultHead allHead=headTemplate.defaultHead header=headerTemplate.defaultHeaser container=defaultContainer foot=footTemplate.defaultFoot>
<html>
    <head>
        <@allHead/>
        <@head/>
    </head>

    <body>
        <div class="wrapper front-end-wrapper">
            <@header/>
            <@container/>
            <@foot/>
        </div>
    </body>
</html>
</#macro>