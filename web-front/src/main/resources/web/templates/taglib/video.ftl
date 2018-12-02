<#macro video>
<script src="/static/js/jquery-1.4.2.min.js"></script>
<script src="/static/video/jquery-ui.js"></script>
<script src="/static/video/jquery.video.js"></script>
<link rel="stylesheet" href="/static/video/jquery-ui.css" />
<link rel="stylesheet" href="/static/video/jquery.video.css" />
<script>
    function startVideo(){
        $('video').video();
    }
</script>
<video id="video" width="854px" height="480px" poster="">
    <source id="videoSource" src="" type="video/mp4"/>
</video>
</#macro>