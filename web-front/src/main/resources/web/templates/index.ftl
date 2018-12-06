<#include "layout/layout.ftl">
<#macro overrideHead>
<title>${title}-Manage</title>
<!-- Jquery -->
<script src="/static/js/jquery-1.11.1.min.js"></script>

<!-- Bootstrap -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>

<!-- Slimscroll -->
<script src='/static/js/jquery.slimscroll.min.js'></script>

<!-- Modernizr -->
<script src='/static/js/modernizr.min.js'></script>

<!-- Simplify -->
<script src="/static/js/simplify/simplify.js"></script>

</#macro>
<#macro overrideContainer>
<div class="section bg-white section-padding body-container" id="howItWorkSection">
    <div class="container">
    </div>
</div>
</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




