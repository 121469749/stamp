<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/static/copy"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head lang="zh">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta charset="UTF-8">
    <title>未签章文件</title>
    <link href="${ctxStatic}/css/style.css?id=2" rel="stylesheet" />
    <script src="${ctxStatic}/js/jquery.js?id=2" type="text/javascript"></script>
    <script src="${ctxStatic}/js/common.js?id=2" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            /*设置主体高度*/
            var h = $(window).height();
            $(".menu-btn").click(function () {
                $(".menu").slideToggle("slow");
            });
            $("#main").height(h - 144);

            /*上传文件*/
            $("#upload").click(function () {
                var formdata = new FormData($("#form")[0]);
                $.ajax({
                    url: "${ctx}/a/document/action/upload",
                    type: "POST",
                    data: formdata,
                    dataType: "json",
                    cache: false,
                    processData: false,
                    contentType: false,
                    success: function (result) {
                        if(result.code == 200){
                            alert("上传成功");
                            location.reload();
                        }else{
                            alert("上传失败");
                        }
                    },
                    error: function () {
                        alert("error")
                    }
                })
            })

            /*验章*/
            $("#btn-checkSeal").click(function () {
                checkSeal("${ctx}");
            });
        });
    </script>
</head>
<body>

<div class="modal" id="uploadFileModal">
    <div class="upload">
        <h2 class="clearfix">选择文件<img src="${ctxStatic}/img/close.png" class="close" onclick="closeModal()"></h2>
        <hr>
        <form id="form">
            <div class="clearfix" style="padding:10px 10px">
                <div class="label">文件名：</div>
                <input type="text" class="input" name="fileName" id="fileName">
            </div>
            <div class="clearfix" style="padding:5px 10px">
                <div class="label">备注：</div>
                <input type="text" class="input" name="remarks" id="remarks">
            </div>
            <div style="padding:8px 10px;text-align: left">
                <button id="btn-choose" class="btn-choose" type="button">选择文件</button>
                <span class="file-name"></span>
                <input type="file" id="uploadFile" name="file" style="display: none" onchange="filepath(this)">
            </div>
            <div style="padding-top: 20px;padding-bottom: 20px;">
                <button id="upload" class="btn-upload" type="button">确认上传</button>
            </div>
        </form>
    </div>
</div>
<div class="modal" id="checkSealModal">
    <div class="upload">
        <h2 class="clearfix">验章<img src="${ctxStatic}/img/close.png" class="close" onclick="closeCheckSealModal()"></h2>
        <hr>
        <form id="checkSealForm">
            <div style="padding:8px 10px;text-align: left">
                <button id="btn-check" class="btn-choose" type="button">选择文件</button>
                <span class="file-name"></span>
                <input type="file" id="checkSealFile" name="file" style="display: none" onchange="filepath(this)">
            </div>
            <div style="padding-top: 20px;padding-bottom: 20px;">
                <button id="btn-checkSeal" class="btn-upload" type="button">确认上传</button>
            </div>
        </form>
    </div>
</div>

<div class="header">
    <h2 style="color: white">未签署文件</h2>
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
    <iframe id="myFrame" name="myFrame" src="${ctx}/a/document/info/orginal/show?id=${document}" width="100%" height="100%"
            frameborder="0"></iframe>
</div>
<div class="footer">
    <ul class="clearfix">
        <li onclick="location.href = '${ctx}/a/document/info/list'">
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
</body>
</html>