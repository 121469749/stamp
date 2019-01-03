<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>待刻印章详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/myScript.js?id=${fns:getConfig('js.version')}"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script src="${ctxHtml}/js/stampengravechecked.js?id=${fns:getConfig('js.version')}"></script>
    <style type="text/css">
        #tbody tr td input{
            text-align: center;
        }
        .modal-content{
            background: rgba(0,0,0,0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
        #my-modal-content input{
            border:none;
            outline: none;
            width: 100%;
            height: 100%;
            line-height: normal;
        }
        #my-modal-content td{
            text-align: left;
        }
        .input{
            width: 100%;
            height: 100%;
            border: none;
            background: rgba(0, 0, 0, 0);
        }
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

        .box {
            margin: 0;
            width: 100%;
        }
        a{-webkit-tap-highlight-color:rgba(0,0,0,0); }
        a:link,a:visited,a:hover,a:active{
            color:white;
            text-decoration:none;
        }
        .del {
            padding: 1px 5px;
            font-size: 18px;
            color: white;
            margin-left: 5px;
            background-color: #c9302c!important;
            border-color: #ac2925!important;
        }
        .del:hover{
            background-color:#333!important;
            color: white;
        }
        .fileBoxUl li {
            float: left;
            margin-right: 2px;
        }

        .upload {
            width: 24%;
            margin: 0;
        }
        .addhtml {
            width: 70%;
            margin: 0;
        }
        #my-btn:disabled{
            opacity: 0.5;
        }
        input[type="text"]{
            height: 30px;
            line-height: 30px;
        }

        input[type="file"]{
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            opacity: 0;
            height: 30px;
            filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0);
        }
        .change{
            border: 1px solid #000;
            height: 30px;
        }
    </style>
    <script>
        $(function () {
            <%--修改附件事件--%>
            $("#fileForm").ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/stampMakeEdit/editAttachments",
                success:function (result) {
//                    alert(result.code);
                    if(result.code == 200){
                        alert("修改成功");
                        location.reload();
                        <%--window.location="${ctx}/stampMakePage/oneStampMake?id="+${stamp.lastRecord.id} + "&stampShape="+${stamp.stampShape};--%>
                    }else {
                        alert(result.message);
//                        $("#subFile").removeAttr("disabled");
//                        $("#changeFile").show();
//                        $("#subFile").hide();
                        location.reload();
                    }
                },
                error:function(e){
                    alert(e);
                    $("#subFile").removeAttr("disabled");
                }
            });
        });
        $(function () {
            <%--修改备案信息--%>
            $("#stampRecord").ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/stampMakeEdit/editInfo",
                success:function (result) {
//                    alert(result.code);
                    if(result.code == 200){
                        alert("修改成功");
                        location.reload();
                        <%--window.location="${ctx}/stampMakePage/oneStampMake?id="+${stamp.lastRecord.id} + "&stampShape="+${stamp.stampShape};--%>
                    }else {
                        alert("修改失败，请重试。");
                        $("#my-btn").removeAttr("disabled");
                    }
                },
                error:function (e) {
                    alert(e);
                    $("#my-btn").removeAttr("disabled");
                }
            });
            var a = $(".text");
            var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;

            for(var i = 0;i < a.length;i++){
                var b = $(a[i]).val().split("/");
                var c = $(a[i]).val().split(".");
                $(a[i]).val(b[b.length - 1]);
                if(!judge.test(c[c.length-1])){
                    $(a[i]).next().remove();
                    $(a[i]).after("<a class='btn btn-success' href='${ctx}/stampMakeInfo/download/file?id="+ $(a[i]).prop("id") + "'>文件下载</button>");
                }
            }
        });
        function validateCount(id,stampShape){
            $.ajax({
                url:"${ctx}/stampMakePage/validateStamp?id="+id+"&stampShape="+stampShape,
                type:"POST",
                success:function(msg){
                    console.log(msg);
                    if (msg=="1"){
                        alert("物理印章数量不足");
                    }else if(msg=="2"){
                        alert("电子印章数量不足");
                    }else{
                        window.location="${ctx}/stampMakePage/startMake?id="+id+"&stampShape="+stampShape;
                    }

                }
            });

        }
    </script>
</head>
<body>
<div>
    <div class="panel-head">
        <h4>当前路径：首页 \ 待刻印章 \ 待刻印章详情</h4>
        <span style="float: right">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate value="${stamp.createDate}"  pattern="yyyy-MM-dd"/></span>
            业务流水号：<span>${stamp.lastRecord.serialNum}</span>
        </span>
    </div>
    <div style="margin-top: 10px;margin-bottom: 20px" id="d4">
        <button class="btn btn-success" onclick="history.go(-1)">返回上一页</button>
        <div style="float: right">
        <a class="btn-lg btn-danger" href="#" onclick="cancellation()">作废</a>
        </div>
    </div>
    <button class="btn btn-success" id="change" onclick="change()">修改信息</button>
    <button class="btn btn-info" id="my-btn" style="margin: 0;display: none">提交</button>
    <form id="stampRecord" action="${ctx}/stampMakeEdit/editInfo">
        <input type="text" style="display: none" name="workType" value="${stamp.lastRecord.workType}" />
        <input type="text" style="display: none" name="id" value="${stamp.lastRecord.id}" />
        <input type="text" style="display: none" name="stamp.stampState" value="${stamp.stampState}" />
        <table class="table table-striped table-bordered">
        <tr>
            <th>企业名称</th>
            <td colspan="3">
                <textarea  id="companyname"  value="${stamp.lastRecord.companyName}" readonly style="background:transparent;overflow:hidden;resize: none;border: 0px;" cols="80" rows="1">${stamp.lastRecord.companyName}</textarea>
            </td>

        </tr>
        <tr>
            <th>企业统一社会信用代码
                </th>
            <%--<td><input class="input" id="unifiedcode" name="soleCode" value="${stamp.lastRecord.soleCode}" type="text" readonly/></td>--%>
            <td>${stamp.lastRecord.soleCode}</td>
            <th>企业类型</th>
            <td>
                <select style="display: none" class="form-control showtext" id="comCertType" name="type1">
                    <c:forEach items="${fns:getDictList('unitType')}" var="t">
                        <option value="${t.value}">${t.label}</option>
                    </c:forEach>
                </select>
                <input type="text" id="temp" value="${stamp.lastRecord.type1}" style="display: none"/>
                <p id="cct">${fns:getDictLabel(stamp.lastRecord.type1,"unitType" ,null )}</p>
            </td>
            <%--<th>企业联系电话--%>
                <%--</th>--%>
            <%--<td><input class="input" id="companyphone" name="compPhone" value="${stamp.lastRecord.compPhone}" type="text" readonly/></td>--%>
        </tr>
        <tr>
            <th >企业住所</th>
            <td colspan="3"><input class="input" id="companyaddress" name="compAddress" value="${stamp.lastRecord.compAddress}" type="text" readonly /></td>
        </tr>
        <tr>
            <th>法人姓名</th>
            <td><input class="input" id="legalName" name="legalName" value="${stamp.useComp.legalName}" type="text" readonly/></td>
            <th>法人联系电话</th>
            <td><input class="input" id="legalphone" name="legalPhone" value="${stamp.lastRecord.legalPhone}" type="text" readonly /></td>
        </tr>
        <tr>
            <th>法人证件类型</th>
            <td>
                <select style="display: none" class="form-control showtext" id="legalCertType" name="legalCertType">
                    <c:forEach items="${fns:getDictList('certificateType')}" var="l">
                        <option value="${l.value}">${l.label}</option>
                    </c:forEach>
                </select>
                <input type="text" id="tempLegalCertType" value="${stamp.lastRecord.legalCertType}" style="display: none"/>
                <p id="lct">${fns:getDictLabel(stamp.lastRecord.legalCertType,"certificateType" ,null )}</p>
            </td>
            <th>法人证件号码</th>
            <td><input class="input" id="legalid" name="legalCertCode" value="${stamp.lastRecord.legalCertCode}" type="text" disabled="disabled"/></td>
        </tr>
        <tr>
            <th>经办人姓名</th>
            <td><input class="input" id="personname" name="agentName" value="${stamp.lastRecord.agentName}" type="text" readonly/></td>
            <th>经办人联系电话</th>
            <td><input class="input" id="personphone" name="agentPhone" value="${stamp.lastRecord.agentPhone}" type="text" readonly /></td>
        </tr>
        <tr>
            <th>经办人证件类型</th>
            <td>
                <select style="display: none" class="form-control showtext" name="agentCertType" id="persionType">
                    <c:forEach items="${fns:getDictList('certificateType')}" var="l">
                        <option value="${l.value}">${l.label}</option>
                    </c:forEach>
                </select>
                <input type="text" id="tempAgentCertType" value="${stamp.lastRecord.agentCertType}" style="display: none"/>
                <p id="act">${fns:getDictLabel(stamp.lastRecord.agentCertType,"certificateType" ,null )}</p>
            </td>
            <th>经办人证件号码</th>
            <td ><input class="input" id="personid" name="agentCertCode" value="${stamp.lastRecord.agentCertCode}" type="text" disabled="disabled"/></td>
        </tr>
    </table>
    </form>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th>序号</th>
            <th width="30%">刻章种类</th>
            <th>章型</th>
            <th>章材</th>
        </tr>
        </thead>
        <tbody id="tbody">
        <tr>
            <th>1</th>
            <td align="center">
                ${fns:getDictLabel(stamp.stampType, "stampType",null )}
            </td>
            <td align="center"><c:forEach items="${stamp.stampShape}" var="item" >
                ${fns:getDictLabel(item,"stampShape" ,null )}
            </c:forEach></td>
            <td align="center">
                ${fns:getDictLabel(stamp.stampTexture,"stampTexture",null)}
            </td>
        </tr>
        </tbody>
    </table>
    <%--图片查看--%>
    <button class="btn btn-success" id="changeFile" onclick="changeFile()">修改附件</button>
    <button class="btn btn-info" id="subFile" style="margin: 0;display: none" type="button">提交</button>
    <form:form action="${ctx}/stampMakeEdit/editAttachments" id="fileForm" method="post" enctype="multipart/form-data">
        <input type="text" style="display: none" name="stampWorkType" value="${stamp.lastRecord.workType}" />
        <input type="text" style="display: none" name="recordId" value="${stamp.lastRecord.id}" />
        <input type="text" style="display: none" name="stamp.stampState" value="${stamp.stampState}" />
    <table class="table table-striped table-bordered">
        <tr>
            <td rowspan="20" style="width: 10%;line-height:45px;text-align: center"><strong>附<br>件<br>材<br>料</strong></td>
        </tr>
        <c:forEach items="${dir}" var="dictMap">
            <tr>
                <td>
                    <c:if test="${dictMap.key!='0'}">
                    <font color="red">&nbsp;*&nbsp;</font>
                    </c:if>
                        ${dictMap.dict.label}
                </td>
                <td style="width: 370px;">
                    <div class="box" id="${dictMap.dict.value}">
                <c:choose>
                    <c:when test="${stamp.lastRecord.sblsh eq null}">
                    <c:set var="flag" value="0" />
                    <c:forEach items="${attachments}" var="item" varStatus="status">
                        <c:if test="${item.attachType == dictMap.dict.value}">
                            <c:set var="flag" value="1" />
                            <div class="clearfix" style="position:relative;">
                                <input type="file" style="display: none">
                                    <%--<input type="text" value="${dict.value}" name="fileType" style="display: none">--%>
                                    <%--<input type="text" class="text" id="${item.id}" readonly style="max-width: 150px" value="/img${item.attachPath}">--%>
                                <input class="btn btn-primary showimg" name="/img${item.attachPath}" data-toggle="modal" data-target=".bs-example-modal-lg" onclick="showimg(this)" type="button" value="查看">
                                <input style="display: none" type='button' value='×' class='btn delFile' name="${item.id}">
                            </div>
                        </c:if>
                    </c:forEach>
                    <c:if test="${flag != '1'}">
                        <div>（未上传）</div>
                    </c:if>
                    </c:when>
                    <c:otherwise>
                    <div>（多证合一数据）</div>
                    </c:otherwise>
                </c:choose>
                        <input class="addhtml btn" type="button" style="margin-top: 2px; display: none" id="${dictMap.dict.value}" value="+">
                    </div>
                </td>
            </tr>
        </c:forEach>
    </table>
    </form:form>
    <center>
        <%--<a href="${ctx}/stampMakePage/downloadCertInMakeStamp?id=${stamp.id}&stampShape=${stamp.stampShape}" class="btn btn-lg btn-success" style="filter: none">下载用章企业证书</a>--%>
        <a class="btn btn-lg bg-primary" id="my-btn10" onclick="validateCount('${stamp.id}','${stamp.stampShape}')">开始刻制</a>
    </center>
    <input value="${stamp.lastRecord.id}" id="recordId" hidden>

</div>
<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 100%" id="modalimg">
        </div>
    </div>
</div>
<div class="my-mask"></div>
<div class="my-mask"></div>
<script>
    <%--作废功能--%>
    function cancellation(){
        var msg = "确定作废吗？(请谨慎操作)";
        if(confirm(msg)){
            //  确定作废
            $.ajax({
                type:"POST",
                url:"${ctx}/stampMakeAction/cancelStampReady",
                dateType:"JSON",
                data:{"stampId":"${stamp.id}","stampShape": "${stamp.stampShape}"},
                success:function (result) {
                    result = JSON.parse(result);
                    alert(result.message);
                    if(result.code == 200){
                        location.href = "${ctx}/stampMakePage/toMakeList";
                    }
                },
                error:function(){
                    alert("出错了，请重试!");
                }
            })
        }
    }

    function changeFile(){
        if (${empty stamp.lastRecord.sblsh} || ${stamp.lastRecord.sblsh eq null}) {
            $(".addhtml").show();
            $(".delFile").show();
            $("#subFile").show();
            $("#changeFile").hide();
        }else {
            alert("【此印章为多证合一数据，无附件材料】");
        }
    }
    function showimg(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
/*        if(IEVersion()<10){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{*/
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
//        }
    }
    $(function () {
        <%--点击提交修改文件时的事件--%>
        $('#subFile').click(function(){
            var f = new Array();
            var i = 0;
            var k = 0;
            var temp = new Array();
            var requestIndexList = new Array();
            <c:forEach items="${dir}" var="dictMap">
                f[i] = ${dictMap.dict.value};
                temp[i] = ${dictMap.key}
                    if(temp[i]!='0'){
                        requestIndexList[k] = i;
                        k++;
                    }
                i++;
            </c:forEach>

            for(var j = 0;j<requestIndexList.length;j++){
                if($("#"+ f[requestIndexList[j]] +"").children(".clearfix").length == 0){
                    alert("请上传必填项！");
                    return false;
                }else if($("#"+ f[j] +"").children(".clearfix").children(".upload").val() == ""){
                    alert("请选择一个附件！");
                    return false;
                }
            }

            $("#fileForm").submit();
            $("#subFile").attr("disabled","disabled");
        });
    //文件选择后
    $(document).on("change","input[type='file']",function(e){
        var files = $(e.target);
        var text = $(e.target).prev();
        var showimg = $(e.target).next();
        var determine = new Array();
        var a = false;
        var arr = ["jpg","JPG","png","PNG","jpeg","JPEG","bmp","BMP","gif","GIF"];
        var textname = new Array();
        textname = files.val().split("\\");
        determine = files.val().split(".");
        text.val(files.val());
        for(var i = 0;i < arr.length;i++){
            if(determine[1] == arr[i]){
                a = true;
            }
        }
        if(files.val() == ""){
            showimg.hide();
        }else if(!a){
            text.val(textname[textname.length-1]);
//                    text.show();
            showimg.hide();
            alert("只能上传图片格式！");
            files.val("");
            text.val("");
        }else{
            text.val(textname[textname.length-1]);
//                    text.hide();
            showimg.show();
        }

        var temp = $(files.parents("td").find("input[type='file']"));
        var index = $(files.parents("div")).index();

        var fil = this.files;
        var file = $(this);
        /*var pic = $("#modalimg");*/
        /*var pic = file.next("img");*/
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/)!= null;
        if(IEVersion()<10) {
            file.select();
            window.parent.document.body.focus();
            var reallocalpath = document.selection.createRange().text;
            showimg.attr('name',reallocalpath);
        }else {
            var reader = new FileReader();
            reader.readAsDataURL(fil[0]);
            reader.onload = function () {
                showimg.attr('name', reader.result);
            }
        }
    });
    $(document).on("click",".modalBox",function(){
        $(this).css("display", "none");
    });
    //图片上传添加按钮，最多只能上传五个文件
    $(".addhtml").click(function(){
        var hasfile = $(this).prev("div").children("input[type='file']").val();
        var textval = $(this).prev("div").children("input[type='text']").val();
        var checked = $(this).parents("td");
        var a = checked.find("input[type = 'text']");
        var b = checked.find("input[type = 'file']");
        if(b.length == 5){
            alert("最多只能上传五个文件。");
            return false;
        }
        var x = true;
        for(var i = 0 ; i < a.length;i++){
            if(a.eq(i).val() == "" || a.eq(i).val() == null){
                x = false;
            }
        }
        if(x){
            $(this).before("<div class='clearfix' style='position:relative;'>" +
                "<input type='text' name='fileType' value='"+ $(this).prop("id") +"' style='display:none'>" +
                "<input type='button' class='btn' value='选择文件'>" +
                "<input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly>" +
                "<input class='upload' name='news' type='file'>" +
                "<input style='display: none' class='btn btn-primary showimg'data-toggle='modal' data-target='.bs-example-modal-lg' type='button' onclick='showimg(this)' value='查看'>" +
                "<input type='button' value='×' class='btn del'></div>")
        }else{
            alert("上一项未填！")
        }
    });
        $(document).on("click",".del",function(){
            var a = $(this).parent();
            a.remove();
        });
        $(document).on("click",".delFile",function(){
            var a = $(this).parent();
            a.after("<input value='"+ $(this).prop("name") +"' name='deleteIds' style='display: none'>");
            a.remove();
        });
    });
    function change(){

        if (${empty stamp.lastRecord.sblsh} || ${stamp.lastRecord.sblsh eq null}) {
            $("#my-btn").show();
            $("#change").hide();
            $("#cct").hide();
            $("#act").hide();
            $("#lct").hide();
            $("input").removeAttr("readonly");
            $("#legalid").removeAttr("disabled");
            $("#personid").removeAttr("disabled");
            $("input").addClass("change");
            $("select").show();

            var tempType1 = $("#temp").val();
            var tempLegalCertType = $("#tempLegalCertType").val();
            var tempAgentCertType = $("#tempAgentCertType").val();

            $("#comCertType").find("option[value = '" + tempType1 + "']").attr("selected", true);
            $("#legalCertType").find("option[value = '" + tempLegalCertType + "']").attr("selected", true);
            $("#persionType").find("option[value = '" + tempAgentCertType + "']").attr("selected", true);
        }else {
            alert("【此印章为多证合一数据，无法修改】");
        }

    }


    //判断是否是IE浏览器，包括Edge浏览器
    function IEVersion() {

        var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
        var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器
        var isEdge = userAgent.indexOf("Windows NT 6.1; Trident/7.0;") > -1 && !isIE; //判断是否IE的Edge浏览器
        if (isIE) {
            var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
            reIE.test(userAgent);
            var fIEVersion = parseFloat(RegExp["$1"]);
            return fIEVersion;
        }
//        else if (isEdge) {
//            return "Edge";
//        }
        else {
            return 100;//非IE
        }
    }

</script>
</body>
</html>
