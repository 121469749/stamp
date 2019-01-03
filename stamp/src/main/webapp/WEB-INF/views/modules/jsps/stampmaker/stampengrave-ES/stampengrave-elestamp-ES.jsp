<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>刻制印章</title>
    <link href="${ctxHtml}/css/engraveseal.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/engraveseal.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/makeSeal.js"></script>
    <script src="${ctxHtml}/js/rangeslider.min.js"></script>
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

    </style>
</head>
<body onunload="loginout()">
<form:form class="border-box " modelAttribute="electronStampDTO" enctype="multipart/form-data"
           action="${ctx}/stampMakeAction/stampElectron" method="post">
    <input type="hidden" name="stamp.id" value="${stamp.id}">
    <input type="hidden" name="stamp.stampShape" value="${stamp.stampShape}">
    <h3 style="margin-bottom: 30px">电子印章制作</h3>
    <div>
        <c:if test="${stamp.stampType != '01'}">
            <div class="form-group each-input">
                <%--<label style="width: 10em;text-align: left">印章重复数：</label>--%>
                <input readonly unselectable="on" value="正在制作第(${makeCount})个此印章" style="height:30px;width:420px;" disabled/>
            </div>
        </c:if>
    </div>
    <div class="engrave">
        <!--左边选项部分-->
        <div class="col-md-7 col-xs-7 col-sm-7 engrave-left-part2">
            <!-- Nav tabs -->
            <ul class="nav tab-title" role="tablist">
                    <p><li>印章基本信息</li></p>
            </ul>
            <!-- Tab panes -->
            <div class="tab-content clearfix">

                <div role="tabpanel" class="tab-pane active clearfix" id="exterior">
                    <div class="form-group engrave-left-tab clearfix" style="margin-top: 25px;">
                        <label class="col-md-5 engrave-text">印模选择：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <c:choose>
                                    <c:when test="${fn:length(phyStamps)>0}">
                                        <select name="phyStampId" onchange="changeimg(this.value)" id="simg">
                                            <option value=""></option>
                                            <c:forEach items="${phyStamps}" var="phyStamp">
                                                <option value="${phyStamp.id}">${phyStamp.stampName}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<button type="button" class="btn" onclick="downloadimg()">下载印模</button>--%>
                                    </c:when>
                                    <c:when test="${fn:length(phyStamps)== 0}">
                                        <p style="color: red">请先将对应的物理印章进行备案！</p>
                                    </c:when>
                                </c:choose>
                                <input type="hidden" id="eleModel" name="stamp.eleModel" value="">
                            </div>
                                <%--<input type="text" name="phyStampId" id="phyStampId" value="${stamp.id}" hidden>--%>
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
                    <c:choose>
                        <c:when test="${fn:length(phyStamps)>0}">
                            <button id="sendES" type="button" class="btn btn-info bottom-btn col-md-4"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="点击开始制作电子印章">
                                <span class="glyphicon glyphicon-play sendES"></span>
                                <img class="sendESLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                                开 始 制 作
                            </button>
                            <button id="changePIN" type="button" class="btn btn-success bottom-btn col-md-4"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="点击修改用户PIN码">
                                <span class="glyphicon glyphicon-edit changePIN"></span>
                                <img class="changePINLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                                修改用户PIN码
                            </button>
                            <button id="revokeES" type="button" class="btn btn-warning bottom-btn col-md-4"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="点击注销印章">
                                <span class="glyphicon glyphicon-repeat revokeES"></span>
                                <img class="revokeESLoad" src="${ctxStatic}/sign/pdfjs/web/images/loading-icon.gif" style="display: none;">
                                注销(谨慎操作)
                            </button>
                        </c:when>
                        <c:when test="${fn:length(phyStamps)== 0}">
                            <button id="" type="button" class="btn btn-primary col-md-4 bottom-btn"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="请先将对应的物理印章进行备案！" disabled>
                                <span class="glyphicon glyphicon-play"></span>   开始制作
                            </button>
                            <button id="" type="button" class="btn btn-success col-md-4 bottom-btn"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="请先将对应的物理印章进行备案！" disabled>
                                <span class="glyphicon glyphicon-edit"></span>   修改用户PIN码
                            </button>
                            <button id="revokeES" type="button" class="btn btn-warning col-md-4 bottom-btn"
                                    style="text-shadow: black 4px 30px 30px;"
                                    title="请先将对应的物理印章进行备案！" disabled>
                                <span class="glyphicon glyphicon-repeat"></span>   注销(谨慎操作)
                            </button>
                        </c:when>
                    </c:choose>
                </div>

            </div>
        </div>
        <div class="col-md-5 col-xs-5 col-sm-5 engrave-right-part">
            <ul class="nav tab-title" role="tablist">
                <p><li>印章外观预览</li></p>
            </ul>
            <%--调用控件标签--%>
            <OBJECT CLASSID="CLSID:D2A20FFC-5207-434A-961E-79FCEA493433"
                    ID="SealDraw" STYLE="LEFT:0;TOP:0" width="0" height="0">
            </OBJECT>
            <OBJECT CLASSID="CLSID:12D7ED82-1C9F-4134-A00C-1BE842488F03"
                    ID="SealService" STYLE="LEFT:0;TOP:0" width="0" height="0">
            </OBJECT>
            <OBJECT CLASSID="CLSID:73E9563B-8074-4E63-A003-0A7BA6A10CFB"
                    ID="SealServiceEx" STYLE="LEFT:0;TOP:0" width="0" height="0">
            </OBJECT>
            <OBJECT CLASSID="CLSID:BAFF8A96-6269-4410-BDBF-EC1E56035928"
                    ID="Crpyto" STYLE="LEFT:0;TOP:0" width="0" height="0">
            </OBJECT>
            <c:choose>
                <c:when test="${fn:length(phyStamps)>0}">
                    <table class="table table-striped table-bordered">
                        <tr>
                            <td><img id="elestamp" src="" style="max-width: 170px"/></td>
                        </tr>
                    </table>
                </c:when>
                <c:when test="${fn:length(phyStamps)== 0}">
                    <img id="elestamp" src="${ctxHtml}/images/no-photo.jpg" width="250px" height="250px"/>
                </c:when>
            </c:choose>
        </div>
    </div>
    <div class="foot">
    </div>
    <input type="hidden" name="electron.type" value="${stamp.stampType}">
    <input type="hidden" name="electron.vendorId" value="RUNCHEN">
    <input type="hidden" name="electron.version" value="1">
    <c:choose>
        <c:when test="${fn:length(phyStamps)>0}">
            <button id="my-btn" type="button" class="btn btn-primary col-md-4 bottom-btn btn-lg"
                    style="margin-left: 33%;margin-top: 50px;text-shadow: black 5px 3px 3px;">
                <span class="glyphicon glyphicon-upload"></span>      提交备案
            </button>
        </c:when>
        <c:when test="${fn:length(phyStamps)== 0}">
            <button type="button" class="btn btn-primary col-md-4 bottom-btn btn-lg"
                    style="margin-left: 33%;margin-top: 50px;text-shadow: black 5px 3px 3px;" disabled>
                <span class="glyphicon glyphicon-upload"></span>      提交备案
            </button>
        </c:when>
    </c:choose>
</form:form>
<script>
    <%--存储电子印模信息--%>
    var arr = new Array();
    var n = 0;
    <c:forEach items="${phyStamps}" var="phyStamp">
    arr[n] = new Array();
    arr[n][0] = "${phyStamp.id}";
    arr[n][1] = "${phyStamp.eleModel}";
    arr[n][2] = "${phyStamp.stampCode}";
    n++;
    </c:forEach>
    function changeimg(val) {
        var v = $("#simg option:selected").val();
        if (v == null || v == ""){
            $("#stampCode").text("");
        }
        var isIE8 = navigator.userAgent.match(/MSIE 8.0/) != null;
        var s = "";
        for (var i = 0; i < arr.length; i++) {
            if (arr[i][0] == val) {
                s = "/img" + arr[i][1];
                $("#stampCode").text(arr[i][2]);
                break;
            }
        }
        if (isIE8) {
            document.getElementById("elestamp").src = s;
            document.getElementById("elestamp").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + s + "\")";
            document.getElementById("elestamp").style.width = "90%";
            document.getElementById("eleModel").value = s;
        }
        else {
            document.getElementById("elestamp").src = s;
            document.getElementById("eleModel").value = s;
        }
    }
    function changeWaterImg(val){
        var isIE8 = navigator.userAgent.match(/MSIE 8.0/) != null;
        var s = "/img" + val;
        if (isIE8) {
            document.getElementById("waterImg").src = s;
            document.getElementById("waterImg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + s + "\")";
            document.getElementById("waterImg").style.width = "90%";
        }
        else {
            document.getElementById("waterImg").src = s;
        }
    }
//    下载印模图片，已取消的功能
    function downloadimg() {
        $.ajax({
            type: "post",
            url: "${ctx}/stampMakeInfo/checkEleModelFileExist?id=" + document.getElementById("simg").value,
            dataType: "json",
            success: function (result) {
                //图片下载
                var form = $("<form>");//定义一个form表单
                form.attr("style", "display:none");
                form.attr("target", "");
                form.attr("method", "post");
                form.attr("action", "${ctx}/stampMakeInfo/download/EleModelFile");
                var input1 = $("<input>");
                input1.attr("type", "hidden");
                input1.attr("name", "id");
                input1.attr("value", $("#simg").val());
                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.submit();//表单提交
                <%--document.getElementById("download").href = "${ctx}/stampMakeInfo/download/EleModelFile?id="+document.getElementById("simg").value;--%>
//                alert(document.getElementById("download").href);
//                document.getElementById("download").click();
            },
            error: function () {
                alert("图片不存在");
            }
        })
    }
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

    <%--导出电子印模文件方法--%>
    function funcExport() {
        <c:choose>
        <c:when test="${fn:length(phyStamps)== 0}">
        alert("请先刻制完对应的物理印章!");
        return false;
        </c:when>
        </c:choose>
        if ($("#simg").val() == "") {
            alert("请选择印模！");
            return false;
        }
        var objSeal = document.getElementById("SealServiceEx");
        var date = new Date();
        <%--开始日期结束日期--%>
        var starday = document.getElementById("day1");
        var endday = document.getElementById("day2");
        var s = document.getElementById("simg");
        var stampName = document.getElementById("stampName").value;
        var a = new Array();
        <%--导出路径--%>
        var url = document.getElementById("elestamp").src;
        //alert(url);
        a = url.split(".");
        var waterUrl = document.getElementById("waterImg").src;
        //alert(waterUrl);
        //console.log("${stamp.id}");
        console.log("${stamp.id}"+stampName+date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()
        +starday.value+endday.value+waterUrl+url+document.getElementById("path").value + document.getElementById("companyName").value + ".seal"+
            "${useUser.id}"+ document.getElementById("usetime").value);
        try {
//            objSeal.SOF_Login(name, pass); // 用证书被颁者名称及其证书私钥密码登录，，下面所有接口，在使用前，都必须先Login
            // 开始调用接口填入相关属性
            objSeal.OES_BeginMakeSeal("${stamp.id}");    // 在每刻制一个新章前必须调用，uuid由平台生成传给组件，作为印章唯一标识
            objSeal.OES_SetSealName(stampName);	// 印章名称，根据平台规则来定
            objSeal.OES_SetCreateDate(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());	//创建日期，取当前值
            objSeal.OES_SetValidDateStart(starday.value);	//印章有效开始日期
            objSeal.OES_SetValidDateEnd(endday.value);	//印章有效期结束
            objSeal.OES_SetSealType(1);					//印章类型，目前暂时填1即可
            objSeal.OES_SetSealVersion(1);              //印章格式版本，目前暂填1
            objSeal.OES_SetVendorId("RUNCHEN");				//厂商名称，填润成
            //设置水印
            objSeal.OES_SetWatermark(waterUrl);
            //设置印章
            objSeal.OES_SetNetPicture(a[1], url, 400, 400);
            <%--盖章人写入--%>
            objSeal.OES_AddSealSigner("${useUser.id}");
            //alert(document.getElementById("path").value +
                //document.getElementById("companyname").innerHTML + ".seal");
            //alert("传入签章人证书的名称------：${useUser.id}");
            //alert(document.getElementById("companyName").value);
            //alert( document.getElementById("usetime").value);
            <%--objSeal.OES_Export(document.getElementById("path").value + document.getElementById("companyname").innerHTML + ".seal","${user.loginName}",document.getElementById("usetime").value);--%>
            objSeal.OES_Export(document.getElementById("path").value + document.getElementById("companyName").value + ".seal",
                "${useUser.id}", document.getElementById("usetime").value);//导出所生成的印章文件
            alert("电子印章文件导出成功！路径为:" + document.getElementById("path").value + document.getElementById("companyName").value + ".seal");
        } catch (e) {
            // 输出出错信息
            alert("生成失败！(确认操作是否正确，是否选择了印模，key中证书是否正确等)");
        }
        finally {
            objSeal.OES_EndMakeSeal();// 完成一次刻制时必须调用，显式结束，否则会出bug
        }
    }
    <%--重新登录方法--%>
    function login() {
        var objSeal = document.getElementById("SealServiceEx");
//        name = prompt("输入管理员证书名称");
        pass = prompt("输入UKey密码");
        try {
            objSeal.SOF_Login(name, pass); //登录
            alert("登陆成功");
            $("#relogin").attr("disabled", "disabled");
        } catch (e) {
            // 输出出错信息
            alert("登录失败");
        }
    }
    function loginout() {
        var objSeal = document.getElementById("SealServiceEx");
        objSeal.SOF_Logout(name); //整个制章动作完成之后，需要用这个接口来退出登录，释放资源
    }
</script>

<script>
    $("#my-btn").click(function () {
        $("#my-btn").attr("disabled", "disabled");
        $("#electronStampDTO").submit();
    })

    // 制作电子印章
    $("#sendES").click(function () {
        var optionVal = $("#simg option:selected").val();
        if (optionVal == null || optionVal == ''){
            alert("请先选择印模")
        }
        else{
            $(".sendES").hide();
            $(".sendESLoad").show();

            $.ajax({
                type: "POST",
                dataType: "json",
                url: "${ctx}/stampMakeAction/procPersonalize",//url
                data:{"stampId":optionVal,"stampShape": "1"},
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

    function copyCompanyName(){
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {//复制功能：兼容浏览器判断

            window.clipboardData.clearData();
            window.clipboardData.setData("text", $("#companyName").val());
            alert("复制成功！");

        }else{//非IE浏览器
            //复制印章单位名称

            var copyText = document.getElementById("companyName");
            copyText.select();//选中文本
            document.execCommand("copy");//执行浏览器复制命令
            alert("复制成功！");
        }
    }

    function copySoleCode(){
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {//复制功能：兼容浏览器判断

            window.clipboardData.clearData();
            window.clipboardData.setData("text", $("#soleCode").val());
            alert("复制成功！");

        }else{//非IE浏览器
            //复制社会统一信用代码

            var copyText = document.getElementById("soleCode");
            copyText.select();//选中文本
            document.execCommand("copy");//执行浏览器复制命令
            alert("复制成功！");
        }
    }

</script>
</body>
</html>
