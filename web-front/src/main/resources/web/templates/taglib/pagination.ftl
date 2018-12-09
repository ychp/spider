<#macro pagination pageNo=1 size=20 total=100 url='' params=''>
    <#if total gt size>
        <#assign realPageNo = total/size>
        <#if total%size gt 0>
            <#assign realPageNo = (total-total%size)/size + 1>
        </#if>
    <div class="text-right">
        <ul class="pagination">
            <#if pageNo gt 1>
                <li><a href="${url}?pageNo=${pageNo-1}&pageSize=${size}${params}">&laquo;</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0)">&laquo;</a></li>
            </#if>
            <#if realPageNo lte 10>
                <#list 1..realPageNo as i>
                    <#if pageNo == i>
                        <li class="active"><a href="javascript:void(0)">${i}</a></li>
                    <#else>
                        <li><a href="${url}?pageNo=${i}&pageSize=${size}${params}">${i}</a></li>
                    </#if>
                </#list>
            <#elseif pageNo gt 5>
                <#if (pageNo+10) gt realPageNo >
                    <#list (realPageNo-10)..realPageNo as i>
                        <#if pageNo == i>
                            <li class="active"><a href="javascript:void(0)">${i}</a></li>
                        <#else>
                            <li><a href="${url}?pageNo=${i}&pageSize=${size}${params}">${i}</a></li>
                        </#if>
                    </#list>
                <#else>
                    <#list (pageNo-4)..(pageNo+5) as i>
                        <#if pageNo == i>
                            <li class="active"><a href="javascript:void(0)">${i}</a></li>
                        <#else>
                            <li><a href="${url}?pageNo=${i}&pageSize=${size}${params}">${i}</a></li>
                        </#if>
                    </#list>
                </#if>
            <#else>
                <#list 1..10 as i>
                    <#if pageNo == i>
                        <li class="active"><a href="javascript:void(0)">${i}</a></li>
                    <#else>
                        <li><a href="${url}?pageNo=${i}&pageSize=${size}${params}">${i}</a></li>
                    </#if>
                </#list>
            </#if>

            <#if pageNo lt realPageNo>
                <li><a href="${url}?pageNo=${pageNo+1}&pageSize=${size}${params}">&raquo;</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0)">&raquo;</a></li>
            </#if>
        </ul>
    </div>
    </#if>
</#macro>