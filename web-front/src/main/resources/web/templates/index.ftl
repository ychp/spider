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
        <div class="text-center">
            <h3 class="text-upper no-m-top">START Manage YOUR <span class="text-success">BLOG APPLICATION</span></h3>
            <p>This Manage System give you much power to manage your blog content.</p>
        </div>

        <div class="row" style="margin-top:70px;">
            <div class="col-md-4 text-center">
                <div class="how-it-work-list fadeInRight animation-element">
                    <div class="how-it-work-icon">
                        <i class="fa fa-edit fa-3x" style="margin-top: 30px;"></i>
                    </div>

                    <h4 class="m-top-md text-upper">MANAGE SYSTEM</h4>

                    <p style="margin-top:30px;">Easy to manage your blog system.</p>
                </div>
            </div>

            <div class="col-md-4 text-center">
                <div class="how-it-work-list fadeInRight animation-delay2 animation-element">
                    <div class="how-it-work-icon">
                        <i class="fa fa-mobile fa-3x" style="margin-top: 30px;"></i>
                    </div>

                    <h4 class="m-top-md text-upper">MOBILE CLIENT</h4>

                    <p style="margin-top:30px;">Easy to manage blogs on mobile/pad any time.</p>
                </div>
            </div>

            <div class="col-md-4 text-center">
                <div class="how-it-work-list fadeInRight animation-delay4 animation-element">
                    <div class="how-it-work-icon">
                        <i class="fa fa-desktop fa-3x" style="margin-top: 30px;"></i>
                    </div>

                    <h4 class="m-top-md text-upper">PC CLIENT</h4>

                    <p style="margin-top:30px;">Simple Pages.</p>
                </div>
            </div>
        </div>
    </div>
</div>
</#macro>

<@layout head=overrideHead container=overrideContainer >
</@layout>




