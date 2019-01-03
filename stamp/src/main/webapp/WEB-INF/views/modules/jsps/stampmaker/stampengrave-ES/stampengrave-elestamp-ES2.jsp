<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>存量电子印章制作（表格扫描件）</title>
    <link href="${ctxHtml}/css/engraveseal.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/engraveseal.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/makeSeal.js"></script>
    <script src="${ctxHtml}/js/rangeslider.min.js"></script>
    <script src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        body {
            padding: 0 2%;
            font-family: "微软雅黑";
        }

        /*隐藏滚动条*/
        body::-webkit-scrollbar {
            display: none
        }

        th {
            text-align: center;
            border: 1px solid #d1d1d1;
        }

        th img {
            width: 22px;
            height: 22px;
            display: none;
        }

        .fileBoxUl li {
            float: left;
            margin-right: 2px;
        }

        .form-group {
            margin-bottom: 10px;
        }

        .engrave-left-tab {
            margin-top: 0;
        }

        input[type="text"] {
            height: 30px;
            line-height: 30px;
        }
        .black_overlay{
            display: none;
            position: absolute;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: #666666;
            z-index:1001;
            -moz-opacity: 0.8;
            opacity:.80;
            filter: alpha(opacity=50);
        }
        .load_img{
            display: none;
            z-index: 1002;
            position: fixed;
            left:45%;
            top: 30%;
        }
        #selectBtn{
            float: left;
        }
    </style>
</head>
<body>
<form:form class="border-box2" modelAttribute="electronStampDTO" enctype="multipart/form-data"
           action="${ctx}/stampMakeAction/stampElectron" method="post">
    <input type="hidden" name="stamp.id" value="${stamp.id}">
    <input type="hidden" name="stamp.stampShape" value="${stamp.stampShape}">
    <input type="hidden" name="stamp.stampSubType" value="${stamp.stampSubType}">
    <h3 style="margin-bottom: 20px">电子印章制作</h3>
    <a id="downloadScan" type="button" class="btn btn-info">保存图片</a>
    <div id="countTip" hidden>
        <c:if test="${stamp.stampType != '01'}">
            <div class="form-group each-input">
                <%--<label style="width: 10em;text-align: left">印章重复数：</label>--%>
                <input readonly unselectable="on" value="正在制作第(${makeCount})个此印章" style="height:30px;width:420px;" disabled/>
            </div>
        </c:if>
    </div>

    <%--制作页--%>
    <div id="page2" class="engrave">
        <!--左边选项部分1-->
        <div class="col-md-6 col-xs-6 col-sm-6 engrave-left-part1" id="leftPart1">
            <!-- Nav tabs -->
            <ul class="nav tab-title" role="tablist">
                <p><li>印章原图文件(请将此图处理完善后在右边上传→)</li></p>
            </ul>
            <!-- Tab panes -->
            <table class="table table-striped table-bordered" style="height: 85%;text-align: center;">
                <tr>
                    <td><img src="/img${stamp.scanModel}" style="width: 450px;height: 650px;"/></td>
                </tr>
            </table>
        </div>

        <!--左边选项部分2-->
        <div class="col-md-5 col-xs-5 col-sm-5 engrave-left-part2" id="leftPart2" hidden>
            <!-- Nav tabs -->
            <ul class="nav tab-title" role="tablist">
                    <p><li>印章基本信息</li></p>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content clearfix">

                <div role="tabpanel" class="tab-pane active clearfix" id="exterior">
                    <div class="form-group engrave-left-tab clearfix" style="margin-top: 25px;">
                        <label class="col-md-5 engrave-text">印章来源：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <p>表格扫描件</p>
                                <input type="hidden" id="eleModel" name="stamp.eleModel" value="">
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">印章编码：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="stampCode">
                                <p>${stamp.stampCode}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">印章名称：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="stampName">
                                <p>${stamp.useComp.companyName}${fns:getDictLabel(stamp.stampType,"stampType" ,null )}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">企业名称：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="useCompName">
                                <p>${stamp.useComp.companyName}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">企业法人：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="legalName">
                                <p>${stamp.useComp.legalName}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">统一社会信用代码：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="soleCode">
                                <p>${stamp.useComp.soleCode}</p>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5 engrave-text">制作日期：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="makeDate">
                                <p>
                                    <script>
                                        setInterval("makeDate.innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
                                    </script>
                                </p>
                            </div>
                        </div>
                    </div>


                    <div class="form-group engrave-left-tab clearfix" style="margin-top: 5px;" hidden>
                        <label class="col-md-5 engrave-text">水印选择：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <select onchange="changeWaterImg(this.value)" id="wimg">
                                    <option value=""></option>
                                    <c:forEach items="${waters}" var="water">
                                        <option value="${water.filePath}">${water.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix" hidden>
                        <label class="col-md-5 engrave-text">用章人证书：</label>
                        <div class="wrapBox">
                            <div class="spinner" id="stampholder">
                                <p>${useUser.loginName}</p>
                        </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab" hidden>
                        <label class="col-md-5 engrave-text">印章有效日期开始：</label>
                        <input id="day1" name="electron.validDateStart" style="width: 200px" class="Wdate" type="text" readonly
                               <%--onFocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',maxDate:'#F{$dp.$D(\'day2\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'%y-%M-{%d}',alwaysUseStartDate:true})"--%>
                        />
                    </div>
                    <div class="form-group engrave-left-tab clearfix" hidden>
                        <label class="col-md-5 engrave-text">印章有效日期结束：</label>
                        <input id="day2" style="width: 200px" name="electron.validDateEnd" class="Wdate" type="text" readonly
                               <%--onFocus="WdatePicker({startDate:'%y-%M-%D 00:00:00',minDate:'#F{$dp.$D(\'day1\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"--%>
                        />
                    </div>
                    <div class="form-group engrave-left-tab clearfix" hidden>
                        <label class="col-md-5 engrave-text">印章可用次数：</label>
                        <input id="usetime" style="width: 200px" name=electron.allowUse type="number" value="-1" readonly/>
                        <P style="color:#999;margin-bottom: 0">-1为无次数限制。</P>
                    </div>
                    <div class="form-group engrave-left-tab" style="height: 35px;line-height: 25px;" hidden>
                        <label class="col-md-5 engrave-text">导出电子印章文件：</label>
                        <input id="path" type="text" size="25" value="c:\" placeholder="非IE下请手动输入">
                        <input type=button value="选择" onclick="browseFolder('path')" style="height: 30px;line-height: 25px;">
                    </div>
                    <div class="form-group engrave-left-tab clearfix" style="margin-bottom: 0" hidden>
                        <label class="col-md-5 engrave-text">电子印章文件：</label>
                        <input id="fileinput" type="file" name="eleFile" style="padding:0" class="col-md-7"
                               accept=".seal">
                    </div>

                    <button id="sendES" type="button" class="btn btn-info bottom-btn col-md-4"
                            style="text-shadow: black 4px 30px 30px;"
                            title="点击开始制作电子印章">
                        <span class="glyphicon glyphicon-play sendES"></span>
                        <img class="sendESLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                        开 始 制 作
                    </button>
                    <button id="changePIN" type="button" class="btn btn-warning bottom-btn col-md-4"
                            style="text-shadow: black 4px 30px 30px;"
                            title="点击修改用户PIN码">
                        <span class="glyphicon glyphicon-edit changePIN"></span>
                        <img class="changePINLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                        修改用户PIN码
                    </button>
                    <button id="revokeES" type="button" class="btn btn-danger bottom-btn col-md-4"
                            style="text-shadow: black 4px 30px 30px;"
                            title="点击注销印章">
                        <span class="glyphicon glyphicon-repeat revokeES"></span>
                        <img class="revokeESLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                        注销(谨慎操作)
                    </button>

                </div>

            </div>
        </div>

        <!--右边选项部分1-->
        <div class="col-md-4 col-xs-4 col-sm-4 engrave-right-part" id="rightPart1">
            <div style="margin: 7px 0;">
                <%--<form id="uploadESModelForm" action="" method="post" enctype="multipart/form-data">--%>
                    <td width="380px">
                        <div style="position:relative;">
                            <input id="selectBtn" type="button" class="btn btn-info" value="选择印章图像(400dpi/BMP)">
                            <input id="fileUpload" style="display: none" class='upload' name="file" type='file' accept="image/BMP">
                            <input class="btn btn-danger" id="uploadESModelBtn" type="button" value="印章图像上传">
                            <input style="display: none" type="text" id="file_stampId" name="stampId" value="${stamp.id}">
                            <input style="display: none" type="text" id="file_stampShape" name="stampShape" value="2">
                        </div>
                    </td>
                <%--</form>--%>
            </div>
            <c:choose>
                <c:when test="${stamp.eleModel eq null || empty stamp.eleModel}">
                    <table class="table table-striped table-bordered" style="height: 85%;text-align: center;">
                        <tr>
                            <td><img id="preImg" src="" style="width: 330px;height: auto;" hidden/></td>
                        </tr>
                    </table>
                </c:when>
                <c:otherwise>
                    <table class="table table-striped table-bordered">
                        <tr>
                            <td><img src="/img${stamp.eleModel}" style="max-width: 170px"/></td>
                        </tr>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
        <!--右边选项部分2-->
        <div class="col-md-4 col-xs-4 col-sm-4 engrave-right-part" id="rightPart2" hidden>
            <ul class="nav tab-title" role="tablist">
                <p><li>印章外观预览</li></p>
            </ul>
            <table class="table table-striped table-bordered">
                <tr>
                    <td><img id="elestamp" src="/img${stamp.eleModel}" style="max-width: 170px"/></td>
                </tr>
            </table>
        </div>

        <button id="toNextPage" type="button" class="btn btn-success col-md-2 bottom-btn btn-lg"
                style="margin-left: 9%;margin-top: 130px;">
            <span class="glyphicon glyphicon-arrow-right"></span>      下一步
        </button>


        <button id="toBack" type="button" class="btn btn-success col-md-2 bottom-btn btn-lg"
                style="margin-left: 23%;margin-top: 50px;display: none;">
            <span class="glyphicon glyphicon-arrow-left"></span>      返回上一步操作
        </button>
        <button id="my-btn" type="button" class="btn btn-primary col-md-2 bottom-btn btn-lg"
                style="margin-left: 5%;margin-top: 50px;display: none;">
            <span class="glyphicon glyphicon-arrow-up"></span>      提交备案
        </button>
    </div>

    <div id="fade" class="black_overlay"></div>
    <div id="MyDiv" class="load_img">
        <img src="${ctxStatic}/sign/img/loading.gif">
    </div>

    <div class="foot">
    </div>

    <input type="hidden" name="electron.type" value="${stamp.stampType}">
    <input type="hidden" name="electron.vendorId" value="RUNCHEN">
    <input type="hidden" name="electron.version" value="1">

</form:form>
<script>
    $(function(){
        // 触发文件上传按钮
        $("#selectBtn").click(function () {
            $("#fileUpload").click();
        });
        // 动态预览上传图片
        $("#fileUpload").change(function(){
            var objUrl = getObjectURL(this.files[0]);
            if(objUrl){
                $('#preImg').show().attr("src",objUrl);
            }
        });
        // 下一步按钮事件
        $("#toNextPage").click(function () {
            if ($("#elestamp").attr("src") == '/img'){
                alert("请将处理完的印章图像上传!")
            }else {
                if(window.confirm('是否已确认最终印模图像？')){
                    $("#leftPart1,#rightPart1,#toNextPage,#downloadScan").hide();
                    $("#leftPart2,#rightPart2,#my-btn,#toBack,#countTip").show();
                }
            }
        });
        // 返回上一步按钮事件
        $("#toBack").click(function () {
            $("#leftPart1,#rightPart1,#toNextPage,#downloadScan").show();
            $("#leftPart2,#rightPart2,#my-btn,#toBack,#countTip").hide();
        });
        //上传处理完的印章图像
        $("#uploadESModelBtn").click(function () {
            if ($('#fileUpload').val() == '' || $('#fileUpload').val() == null){
                alert("请选择文件！");
            }else {
                ShowDiv('MyDiv','fade');//加载中...

                var formData = new FormData();
                formData.append('file', $('#fileUpload')[0].files[0]);
                formData.append('stampId', $("#file_stampId").val());
                formData.append('stampShape', $("#file_stampShape").val());

                $.ajax({
                    dataType: 'json',
                    type: "post",
                    data : formData,
                    url: "${ctx}/stampMakeAction/uploadESModel",//url
                    processData : false, // must
                    contentType : false, // must
                    success:function (result) {
                        if(result.code = 200){
                            alert("印章图像上传成功");
                            $("#elestamp").attr("src","/img"+result.message);
                        }else {
                            alert(result.message);
                        }
                        CloseDiv('MyDiv','fade');
                    },
                    error:function (e) {
                        CloseDiv('MyDiv','fade');
                        alert(e);
                    }
                });
            }
        });

    });

    //获取路径
    function getObjectURL(file){
        var url = null;
        if(window.createObjectURL != undefined){
            url = window.createObjectURL(file);//basic
        }else if(window.URL != undefined){
            url = window.URL.createObjectURL(file);
        }else if(window.webkitURL != undefined){
            url = window.webkitURL.createObjectURL(file);
        }
        return url;
    }
    //弹出隐藏层
    function ShowDiv(show_div,bg_div){
        document.getElementById(show_div).style.display='block';
        document.getElementById(bg_div).style.display='block' ;
    };
    //关闭弹出层
    function CloseDiv(show_div,bg_div)
    {
        document.getElementById(show_div).style.display='none';
        document.getElementById(bg_div).style.display='none';
    };

    <%--选择保存路径--%>
    function browseFolder(path) {
        try {
            var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939"; //选择框提示信息
            var Shell = new ActiveXObject("Shell.Application");
            var Folder = Shell.BrowseForFolder(0, Message, 64, 17); //起始目录为：我的电脑
            //var Folder = Shell.BrowseForFolder(0, Message, 0); //起始目录为：桌面
            if (Folder != null) {
                Folder = Folder.items(); // 返回 FolderItems 对象
                Folder = Folder.item(); // 返回 Folderitem 对象
                Folder = Folder.Path; // 返回路径
                if (Folder.charAt(Folder.length - 1) != "\\") {
                    Folder = Folder + "\\";
                }
                document.getElementById(path).value = Folder;
                return Folder;
            }
        }
        catch (e) {
            alert("非IE下不支持此功能");
        }
    }

    $(document).ready(function () {
        //上传处理完的印章图像
        $("#uploadESModelForm").ajaxForm({
            dataType: 'json',
            type: "post",
            url: "${ctx}/stampMakeAction/uploadESModel",//url
            success:function (result) {
                if(result.code = 200){
                    alert(result.message);
                }else {
                    alert(result.message);
                }
                CloseDiv('MyDiv','fade');
            },
            error:function (e) {
                CloseDiv('MyDiv','fade');
                alert(e);
            }
        });

        $('#electronStampDTO').ajaxForm({
            dataType: 'json',
            type: "post",
            url: "${ctx}/stampMakeAction/stampElectron",
            success: function (result) {
                //alert(result.message);
                if (result.code == 200) {
                    <%--主题框架左边列表改变--%>
                    $(".nav-list",window.parent.document).children("li").removeClass("active");
                    $(".nav-list",window.parent.document).children("li:contains('已制作印章')").addClass("active");
                    window.location = "${ctx}/stampMakePage/toFinishList";
                } else {
                    alert(result.message);
                    $("#my-btn").removeAttr("disabled");
                }
            },
            error: function () {
                alert("出错了！请联系管理员！");
                $("#my-btn").removeAttr("disabled");
            }
        });
        var date = new Date();
        document.getElementById("day1").value = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
        document.getElementById("day2").value = (date.getFullYear() + 1) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();

    });
</script>

<script>
    // 提交备案
    $("#my-btn").click(function () {
        $("#my-btn").attr("disabled", "disabled");
        $("#electronStampDTO").submit();
    })

    // 制作电子印章
    $("#sendES").click(function () {
        var optionVal = $("#elestamp").attr("src");
        if (optionVal == '/img'){
            alert("请返回上一步上传印章图像！");
        }
        else{
            $(".sendES").hide();
            $(".sendESLoad").show();

            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/stampMakeAction/procPersonalize",//url
                data:{"stampId":"${stamp.id}","stampShape": "2"},
                success: function (result) {
                    if (result.code = 200) {
                        var CS_returnvalue= window.external.PersonAlize(JSON.stringify(result.entity));//[{\"szSealSerial\":\"440300000001\"},{\"szSealName\":}]");
                        if (CS_returnvalue.indexOf("success") != -1) {
                            CS_returnvalue = "制作成功！请提交备案";
                        }
                        alert(CS_returnvalue);

                        $(".sendESLoad").hide();
                        $(".sendES").show();
                    }
                },
                error : function() {
                    alert("网络繁忙,稍后再试");
                    $(".sendESLoad").hide();
                    $(".sendES").show();
                }
            });
        }

    });

    // 修改PIN码
    $("#changePIN").click(function () {
        $(".changePIN").hide();
        $(".changePINLoad").show();

        var returnStr = window.external.ChangePin();
        alert(returnStr);

        $(".changePINLoad").hide();
        $(".changePIN").show();
    });

    // 注销
    $("#revokeES").click(function () {
        var msg = "此操作会将Ukey(包括PIN码)整个初始化，是否确认注销？";
        if(confirm(msg)==true){
            $(".revokeES").hide();
            $(".revokeESLoad").show();

            var returnStr = window.external.ProcRevoke();
            if (returnStr.indexOf("success") != -1) {
                returnStr = "注销成功";
            }
            alert(returnStr);

            $(".revokeESLoad").hide();
            $(".revokeES").show();

        }else{
            $(".revokeESLoad").hide();
            $(".revokeES").show();
            return false;
        }
    });

</script>

<script>
    //①判断浏览器类型
    function myBrowser() {
        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
        var isOpera = userAgent.indexOf("Opera") > -1;
        if(isOpera) {
            return "Opera"
        }; //判断是否Opera浏览器
        if(userAgent.indexOf("Firefox") > -1) {
            return "FF";
        } //判断是否Firefox浏览器
        if(userAgent.indexOf("Chrome") > -1) {
            return "Chrome";
        }
        if(userAgent.indexOf("Safari") > -1) {
            return "Safari";
        } //判断是否Safari浏览器
        if(userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
            return "IE";
        }; //判断是否IE浏览器
        if(userAgent.indexOf("Trident") > -1) {
            return "Edge";
        } //判断是否Edge浏览器
    }

    //②IE浏览器图片保存（IE其实用的就是window.open)
    function SaveAs5(imgURL) {
        var oPop = window.open(imgURL, "", "width=1, height=1, top=5000, left=5000");
        for(; oPop.document.readyState != "complete";) {
            if(oPop.document.readyState == "complete") break;
        }
        oPop.document.execCommand("SaveAs");
        oPop.close();
    }

    //③下载函数（区分IE和非IE部分）
    function oDownLoad(url) {
        if(myBrowser() === "IE" || myBrowser() === "Edge") {
            //IE （浏览器）
            SaveAs5(url);
            console.log(1)
        } else {
            //!IE (非IE)
            odownLoad.href = url;
            odownLoad.download = "";
        }

    }

    //④点击事件下载（只需更改图片路径即可）
    var odownLoad = document.getElementById("downloadScan");
    odownLoad.onclick = function() {
        oDownLoad("/img${stamp.scanModel}")
    }

</script>

</body>
</html>
