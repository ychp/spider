<#macro table>
<table class="table table-striped" id="dataTable">
    <thead>
    <tr>
        <th>ID</th>
        <th>Image</th>
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
            <td style="max-width: 55%">
                <img src="${data.url}" title="${data.url}">
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