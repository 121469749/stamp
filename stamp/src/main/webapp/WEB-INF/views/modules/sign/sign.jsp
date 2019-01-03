<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static/sign"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>盖章</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            /*设置主体高度*/
            var h = $(window).height();
            $(".menu-btn").click(function () {
                $(".menu").slideToggle("slow");
            });
            $("#main").height(h - 144);

            /*验章*/
            $("#btn-checkSeal").click(function () {
                checkSeal("${ctx}");
            });
        });
        <%--window.onload = function(){--%>
            <%--// 获取控件--%>
            <%--var objSeal = document.getElementById("SignerCtrl");--%>
            <%--try {--%>
                <%--objSeal.SOF_Login("${dto.userName}","${dto.password}"); //登录--%>
            <%--}catch(e){--%>
                <%--// 输出出错信息--%>
                <%--alert(e + "\n出错了，请刷新页面重试");--%>
            <%--}--%>
        <%--}--%>
    </script>
</head>
<body>

<div class="header">
    <h2 style="color: white">文件签署</h2>
    <span class="menu-btn"></span>
    <div class="menu">
        <ul>
            <li><a href="${ctx}/a/document/info/uploadPage">上传文件</a></li>
            <li><a href="${ctx}/a/audit/index">审计</a></li>
            <li><a href="${ctx}/a/logout">退出</a></li>
        </ul>
    </div>
</div>
<div id="main" style="position:relative;width: 100%">
    <div style="display: none">
        <OBJECT CLASSID="CLSID:1F4A7405-5BAD-49CF-90B8-9B5A276154B5"
                ID="SignerCtrl" STYLE="LEFT:0;TOP:0" width="0" height="0">
        </OBJECT>
    </div>
    <div style="width:170px;height:170px;border:2px solid #ccc; position:absolute; left: 500px;top:100px;
            background: url('/img/${dto.stamp.eleModel}');background-size: cover;cursor: pointer"
         id="div">
    </div>
    <iframe id="myFrame" name="myFrame" src="${ctx}/a/singal/singatruePage?id=${dto.document.id}" width="100%" height="100%"
            frameborder="0"></iframe>
    <div style="text-align: center;position: absolute;bottom: 0;left: calc((100% - 100px)/2)">
        <button id="signature" class="btn-upload" style="width: 100px;background-color: red;">签章</button>
    </div>
</div>
<div class="footer">
    <ul class="clearfix">
        <li onclick="location.href = '${ctx}/a/document/info/index'">
            <div><span style="background: url('${ctxStatic}/img/list.png') 32px 32px"></span>文件列表</div>
        </li>
        <li class="active">
            <div><span style="background: url('${ctxStatic}/img/file.png') 32px 32px"></span>文档签章</div>
        </li>
        <li>
            <div><span style="background: url('${ctxStatic}/img/stamp.png') 32px 32px"></span>验  章</div>
        </li>
    </ul>
</div>
<form id="form-sign" style="display: none">
    <input name="point.x" id="x">
    <input name="point.y" id="y">
    <input name="point.width" id="width">
    <input name="point.high" id="high">
    <input name="point.pageSize" id="pageSize">
    <input name="point.pxWidth" id="pageWidth">
    <input name="point.pxHigh" id="pageHeight">
    <input name="document" id="document">
    <input name="seal" id="seal">
    <input type="text" id="hexCode" name="hexCode" value="0">
</form>
<script>
    window.onbeforeunload = function(){
        var objSeal = document.getElementById("SignerCtrl");
        try{
            objSeal.SOF_Logout(); //整个制章动作完成之后，需要用这个接口来退出登录，释放资源
        }catch(e){
            alert("退出登录失败，原因："+ e);
        }
    };
    $("#form-sign").ajaxForm({
        dataType: 'json',
        type:"post",
        url: "${ctx}/a/singal/signature",
        success: function (result) {
            if(result.code == 200){
                /*确认签章*/
                var objSeal = document.getElementById("SignerCtrl");
                try{
                    var docmentId =result.entity.id;
                    var name = "${dto.userName}";
                    var pass = "${dto.password}";
                    objSeal.SOF_Login(name,pass); //登录
                    var seals = objSeal.GetSealList();
                    var ary  = seals.split(";");
                    var ary2 = ary[0].split(",");
                    alert(ary2);
                    var sealid = "${dto.stamp.bindStampId}";
                    //var hexCodeOrginal = "${dto.realPath}";
                    //alert(result.entity.fileSinaturePath);
                    alert("Doc的id"+docmentId);
                    //var hexCodeOrginal ="${doc.fileOrgPath}";
                    var hexCodeOrginal =result.entity.fileSinaturePath;
                    hexCodeOrginal = hexCodeOrginal.replace(/\,/g,'/');
                    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
                    var curWwwPath = window.document.location.href;
                    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                    var pathName = window.document.location.pathname;
                    var pos = curWwwPath.indexOf(pathName);
                    //获取主机地址，如： http://localhost:8083
                    var localhostPaht = curWwwPath.substring(0, pos);
                    var hexCodeOrginal=localhostPaht+"/img/sign"+hexCodeOrginal;console.log("hexCodeOrginal:"+hexCodeOrginal)
                    //alert(hexCodeOrginal);
                    //开始签章，传入印章索引，以及所需签章内容，得到签章数据
                    signedData =  objSeal.SignSealUrl(sealid,hexCodeOrginal);
                    //var realPath="D:\\test-data"+hexCodeOrginal;
                    //signedData =  objSeal.SignSealFile(sealid,realPath);
                    alert("签章返回值"+signedData);
                    $.ajax({
                        type:"post",
                        url:"${ctx}/a/singal/updateSignData",
                        data:{SignData:signedData,DocmentId:docmentId},
                        success:function (data) {
                            if (data.code == 200){

                            }else {
                                alert(e + "\n签章失败，请重试。前台错误"+data.code);
                            }
                        }
                    });
                    //$("#hexCode").attr("value",signedData);
                }catch(e){
                    // 输出出错信息
                    alert(e + "\n签章失败，请重试。后台错误");
                    return false;
                }
                alert("签章成功");
                window.location = "${ctx}/a/document/info/index";
            }else{
                alert(result.message);
            }
        },
        error:function () {
            alert("签章传递错误的数据，error")
        }
    });
    $("#signature").click(function () {
        var msg = "确定签章到此文件吗？";
        if(confirm(msg)==true){
            $("#x").val(px);
            $("#y").val(pageHeight-py);
            $("#width").val(pw);
            $("#high").val(ph);
            $("#pageSize").val(pageCount+1);
            $("#pageWidth").val(pageWidth);
            $("#pageHeight").val(pageHeight);
            $("#document").val("${dto.document.id}");
            $("#seal").val("${dto.stamp.bindStampId}");
            var formdata = new FormData($("#form-sign")[0]);

            $("#form-sign").submit();
            <%--var objSeal = document.getElementById("SignerCtrl");--%>
            <%--try{--%>
                <%--var name = "${dto.userName}";--%>
                <%--var pass = "${dto.password}";--%>
                <%--objSeal.SOF_Login(name,pass); //登录--%>
                <%--signedData =  objSeal.SignSeal("${seal.id}", "${dto.document.hexCodeOrginal}");--%>
            <%--}catch(e){--%>
                <%--alert(e);--%>
            <%--}--%>
            <%--$.ajax({--%>
                <%--url: "${ctx}/a/singal/signature",--%>
                <%--type: "POST",--%>
                <%--data: formdata,--%>
                <%--dataType: "JSON",--%>
                <%--cache: false,--%>
                <%--processData: false,--%>
                <%--contentType: false,--%>
                <%--success: function (result) {--%>
                    <%--if(result.code == 200){--%>
                        <%--alert("签章成功");--%>
                        <%--location.href = "${ctx}/a/document/info/list";--%>
                    <%--}else{--%>
                        <%--alert(result.message);--%>
                    <%--}--%>
                <%--},--%>
                <%--error: function () {--%>
                    <%--alert("error")--%>
                <%--}--%>
            <%--});--%>
        }else{
            return false;
        }
    });
    //拖拽功能
    var div = document.getElementById("div"); //签章div
    var px,py;
    var pw = parseInt(div.style.width); //签章宽度
    var ph = parseInt(div.style.height); //签章高度

    var yScroll; //当前相对pdf首页顶部高度
    var iframe = document.getElementById('myFrame'); //pdfFrame
    var page, pageWidth, pageHeight, pageLeft, pageCount;

    div.onmousedown = function (e) {
        px = e.clientX - parseInt(div.style.left);
        py = e.clientY - parseInt(div.style.top);
        div.onmousemove = mousemove;
        div.onmouseleave = div.onmouseup;
    };
    div.onmouseup = function () {
        div.onmousemove = null;
        page = iframe.contentWindow.document.getElementsByClassName('page')[0]; //iframe内pdf对象
        px = parseInt(div.style.left) - pageLeft - 8;

        pageHeight = parseInt(page.style.height);
        pageLeft = parseInt(page.offsetLeft);
        yScroll = iframe.contentWindow.getScrollTop();
        // py = parseInt(div.style.top) - 86 + yScroll;
        pageCount = parseInt((parseInt(div.style.top) - 53 + yScroll) / pageHeight);
        if(pageCount == 0){
            pageCount = parseInt((parseInt(div.style.top) - 53 + yScroll) / pageHeight);
            py = (parseInt(div.style.top) - 53 - (pageCount-1)*11 + yScroll) % pageHeight;
        }else{
            pageCount = parseInt((parseInt(div.style.top) - 53 - (pageCount-1)*10 + yScroll) / pageHeight);
            py = (parseInt(div.style.top) - 53 - (pageCount-1)*11 + yScroll) % pageHeight;
        }
        console.log("签章的坐标x=" + px + ", y=" + py + ", 签章宽度w=" + pw + ", 高度h=" + ph + ", 页码数count=" + pageCount);
        //pageTop = iframe.contentWindow.document.body.scrollTop ;
    };
    function mousemove(em) {
        /*if(!em) em = window.event;//if不是firefox等浏览器，那么e为IE浏览器

        if((em.clientX-px) < 0){
            div.style.left = 0 + 'px';
        }else if((em.clientX-px) >= (w - 20)){
            div.style.left = (w - 20) + 'px';
            console.log(w);
        }else{
            div.style.left = (em.clientX-px)+'px';
        }
        if((em.clientY-py) < 86){
            div.style.top = 86+'px';
        }else{
            div.style.top = (em.clientY-py)+'px';
        }*/
        if (!em) em = window.event;//if不是firefox等浏览器，那么e为IE浏览器
        page = iframe.contentWindow.document.getElementsByClassName('page')[0]; //iframe内pdf对象
        pageWidth = parseInt(page.style.width);
        pageLeft = parseInt(page.offsetLeft);

        if ((em.clientX - px) < (pageLeft + 8)) {
            div.style.left = (pageLeft + 8) + 'px';
        } else if ((em.clientX - px) > (pageLeft + pageWidth - pw + 8)) {
            div.style.left = (pageLeft + pageWidth - pw + 8) + 'px';
        } else {
            div.style.left = (em.clientX - px) + 'px';
        }
        if ((em.clientY - py) < 42) {
            div.style.top = 42 + 'px';
        } else if ((em.clientY - py) > (document.body.clientHeight - ph - 100)) {
            div.style.top = (document.body.clientHeight - ph - 100) + 'px';
        } else {
            div.style.top = (em.clientY - py) + 'px';
        }
    }
</script>
</body>
</html>