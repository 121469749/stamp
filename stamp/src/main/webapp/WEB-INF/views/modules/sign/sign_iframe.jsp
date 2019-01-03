<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>盖章</title>
    <link href="${ctxStatic}/sign/css/style.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/sign/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/sign/js/common.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/sign/js/jquery-form.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            /*设置主体高度*/
            $("#main").height($(window).height()-10);

            /*验章*/
            $("#btn-checkSeal").click(function () {
                checkSeal("${ctx}");
            });

            if (${dto.stamp.stampType == '3'}){
                $("#div").width(190);
                $("#div").height(140);
            }else {
                $("#div").width(165);
                $("#div").height(165);
            }

        });
    </script>
</head>
<body>
<div id="main" style="position:relative;width: 100%;height: 100%;">
    <div style="display: none">
        <OBJECT CLASSID="CLSID:1F4A7405-5BAD-49CF-90B8-9B5A276154B5"
                ID="SignerCtrl" STYLE="LEFT:0;TOP:0" width="0" height="0">
        </OBJECT>
    </div>
    <%--width:170px;height:170px;--%>
    <div style="border:2px solid #ccc; position:absolute; left: 500px;top:100px;
            background: url('/img/${dto.stamp.eleModel}');background-size: cover;cursor: pointer"
         id="div">
    </div>
    <iframe id="myFrame" name="myFrame" src="${ctx}/singal/singatruePage?id=${dto.document.id}" width="100%" height="100%"
            frameborder="0">
    </iframe>
    <div style="text-align: center;position: absolute;bottom: 15px;left: calc((100% - 100px)/2)">
        <button id="signature" class="btn-upload" style="width: 100px;background-color: red;">签章</button>
    </div>
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
            objSeal.SOF_Logout();
        }catch(e){
//            alert("退出登录失败，原因："+ e);
        }
    };

    $("#form-sign").ajaxForm({
        dataType: 'json',
        type:"post",
        url: "${ctx}/singal/signature",
        success: function (result) {
             if(result.code == 200){
                debugger
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
                    //alert(ary2);
                    var sealid = "${dto.stamp.bindStampId}";
                    //var hexCodeOrginal = "${dto.realPath}";
                    //alert(result.entity.fileSinaturePath);
                    //alert("Doc的id"+docmentId);
                    //var hexCodeOrginal ="${doc.fileOrgPath}";
                    var hexCodeOrginal =result.entity.fileSinaturePath;//alert("hexCodeOrginal1:"+hexCodeOrginal)
                    hexCodeOrginal = hexCodeOrginal.replace(/\,/g,'/');//alert("hexCodeOrginal2:"+hexCodeOrginal)
                    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
                    var curWwwPath = window.document.location.href;
                    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
                    var pathName = window.document.location.pathname;
                    var pos = curWwwPath.indexOf(pathName);
                    //获取主机地址，如： http://localhost:8083
                    var localhostPaht = curWwwPath.substring(0, pos);
                    var hexCodeOrginal=localhostPaht+"/img/sign"+hexCodeOrginal;console.log("hexCodeOrginal:"+hexCodeOrginal)
                    //alert("hexCodeOrginal3:"+hexCodeOrginal)
                    //开始签章，传入印章索引，以及所需签章内容，得到签章数据
//                    var signedData =  objSeal.SignSeal(sealid,hexCodeOrginal);//本地路径
                    var signedData =  objSeal.SignSealUrl(sealid,hexCodeOrginal);
                    //alert("签章返回值"+signedData);
                    $.ajax({
                        dataType: 'json',
                        type:"post",
                        url:"${ctx}/singal/updateSignData",
                        data:{SignData:signedData,DocmentId:docmentId,AuditId:result.message},
                        success:function (data) {
                            if (data.code == 200){
                                alert("签章成功");
                                //关闭预签章modal
                                window.parent.closeModal();
                                parent.location.href = "${ctx}/document/info/index";
                            }else {
                                alert("\n签章失败，请重试。"+data.code);
                            }
                        }
                    });
                    //$("#hexCode").attr("value",signedData);
                }catch(e){
                    // 输出出错信息
                    alert(e + "\n签章失败，请重试。");
                    return false;
                }
            }else{
                alert(result.message);
            }
        },
        error:function () {
            alert("error,签章数据有误");
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

        }else{
            return false;
        }
    });
    //拖拽功能
    var div = document.getElementById("div"); //签章div
    var px,py;
    var pw; //签章宽度
    var ph; //签章高度

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

        pw = parseInt(div.style.width); //签章宽度
        ph = parseInt(div.style.height); //签章高度
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