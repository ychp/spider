<#macro table >
<table class="table table-striped" id="dataTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Url</th>
        <th>Content</th>
        <th>Type</th>
        <th>Status</th>
        <th>CreateTime</th>
        <th>Operator</th>
    </tr>
    </thead>
    <tbody>
        <#list datas.datas as data>
        <tr>
            <td style="width: 5%">${data.id}</td>
            <td style="width: 20%; max-width: 200px; overflow: hidden;" title="${data.url!}">
                <#if data.url??>
                    <#if data.url?length gt 20>
                        <a style="text-decoration:underline;" href="${data.url}" target="_blank">${data.url[0..20]}...</a>
                    <#else>
                        <a style="text-decoration:underline;" href="${data.url}" target="_blank">${data.url}</a>
                    </#if>
                </#if>
            </td>
            <td style="width: 20%;">
                <#if data.content?length gt 20>
                    <xmp>${data.content[0..20]}...</xmp>
                <#else>
                    <xmp>${data.content}</xmp>
                </#if>
            </td>
            <td style="width: 7%">${data.typeStr!}</td>
            <td style="width: 8%">${data.statusStr!}</td>
            <td style="width: 15%;">${data.createdAt?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td style="width: 10%">
                <a class="btn btn-sm btn-success" href="#detail" onclick="showDetail(${data.id?c})" data-toggle="modal">Detail</a>
                <a class="btn btn-sm btn-danger" href="#del" onclick="del(${data.id?c})" data-toggle="modal">Del</a>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
</#macro>