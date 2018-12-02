<#import "head.ftl" as headTemplate>
<#import "header.ftl" as headerTemplate>
<#macro defaultHead></#macro>
<#macro defaultContainer></#macro>
<#macro layout head=defaultHead allHead=headTemplate.defaultHead header=headerTemplate.defaultHeaser container=defaultContainer>
<html>
    <head>
        <@allHead/>
        <@head/>
    </head>

    <body>
        <div class="wrapper front-end-wrapper">
            <@header/>
            <@container/>
        </div>
    </body>
</html>
</#macro>