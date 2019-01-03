<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>已刻印章详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <link rel="stylesheet" href="${ctxHtml}/css/engraveseal.css">
    <script src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
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
        .modal-content2{
            background: rgba(0,0,0,0);
            border: none;
            box-shadow: none;
            text-align: center;
        }
        .modal-bg-white{
            background: white;
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
        .addhtml2 {
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
        table {
            table-layout:fixed;
            word-wrap:break-word;
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
    </style>
    <script>
        var htmltempl = null ;
        var header=null ;
        var footer = null;
        var content = null;
        var resultHTML = false;

        $(function () {
            if ( '${stamp.stampShape}' == 2) {
                $("#backToWait").hide();
                $("#writechip").hide();
            }
        });

        $(function(){
            $("#my-btn10").click(function () {
                $("#my-btn10").attr("disabled","disabled");
                $.ajax({
                    dataType: 'json',
                    type: "post",
                    url: "${ctx}/stampMakeAction/previewPic?stampId=" + '${stamp.id}',
                    success: function (result) {

                        $("#iSign").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment10 });
                        header = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                            "<html lang=\"zh-cn\">\n" +
                            "<head>\n" +
                            "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></meta>\n" +
                            "</head>\n" +
                            "<body>";
                        footer = " </body>\n" +
                            " </html>";
                        content = "<div style=\"text-align: center;\">" +
                            "<h2>印章备案登记表</h2>" +
                            "</div>" +
                            "<br></br>" +
                            "<div style = \"text-align : center;font-size:14px;\">" +
                            "登记时间：<span style=\"margin-right: 50px\">"+$("#cur-time").text()+"</span>" +
                            "业务流水号：<span>"+$("#ht1").text()+"</span>" +
                            "</div>\n" +
                            "<br>&nbsp;</br>\n" +
                            "<div>\n" +
                            "        <table border=\"1px\" cellpadding=\"0px\" cellspacing=\"0px\" style=\"text-align: center; margin:auto;width:90%;line-height:40px;border-collapse:collapse; font-size：18px;\">\n" +
                            "            <tbody>\n" +
                            "<tr>\n" +
                            "                <th style=\"width:140px;font-family:SimSun;\">企业名称</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#companyname").val()+"\n" +
                            "                </td>             \n" +
                            "                <th style=\"font-family:SimSun;\">\n" +
                            "                    企业类型</th>\n" +
                            "                <td style=\"font-family:SimSun;\">\n" +
                            "                    "+$("#cct").text()+"\n" +
                            "                </td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">企业统一社会信用代码\n" +
                            "                </th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#unifiedcode").val()+"</td>\n" +
                            "                <th style=\"font-family:SimSun;\">企业联系电话\n" +
                            "                </th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#companyphone").val()+"</td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">企业住所</th>\n" +
                            "                <td colspan=\"3\">"+$("#companyaddress").val()+"</td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">法人姓名</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#legalName").val()+"</td>\n" +
                            "                <th style=\"font-family:SimSun;\">法人联系电话</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#legalphone").val()+"</td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">法人证件类型</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#lct").text()+"</td>\n" +
                            "                <th style=\"font-family:SimSun;\">法人证件号码</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#legalid").val()+"</td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">经办人姓名</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#personname").val()+"</td>\n" +
                            "                <th style=\"font-family:SimSun;\">经办人联系电话</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#personphone").val()+"</td>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <th style=\"font-family:SimSun;\">经办人证件类型</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#act").text()+"</td>\n" +
                            "                <th style=\"font-family:SimSun;\">经办人证件号码</th>\n" +
                            "                <td style=\"font-family:SimSun;\">"+$("#personid").val()+"</td>\n" +
                            "            </tr>\n" +
                            "        </tbody>\n" +
                            "</table>\n" +
                            "<br>&nbsp;</br>\n" +
                            "<table border=\"1px solid black\" border-spacing=\"0\" cellpadding=\"0px\" cellspacing=\"0px\" style=\" margin:auto;line-height:40px;width:90%;border-collapse:collapse;\">\n" +
                            "        <thead>\n" +
                            "        <tr style=\"width:100%;\">\n" +
                            "            <th style=\"font-family:SimSun;\">序号</th>\n" +
                            "            <th style=\"font-family:SimSun;\">印章名称</th>\n" +
                            "            <th style=\"font-family:SimSun;\">章型</th>\n" +
                            "            <th style=\"font-family:SimSun;\">章材</th>\n" +
                            "            <th style=\"font-family:SimSun;\">印模</th>\n" +
                            "        </tr>\n" +
                            "        </thead>\n" +
                            "        <tbody id=\"tbody\">\n" +
                            "        <tr height=\"190px\">\n" +
                            "            <th style=\"font-family:SimSun;\">"+$("#xuhao").text()+"</th>\n" +
                            "            <td align=\"center\" style=\"font-family:SimSun;\">\n" +
                            "               "+$("#stampname").text()+"\n" +
                            "            </td>\n" +
                            "            <td align=\"center\" style=\"font-family:SimSun;\">\n" +
                            "                "+$("#stamptype").text()+"\n" +
                            "            </td>\n" +
                            "            <td align=\"center\" style=\"font-family:SimSun;\">"+$("#stamptexture").text()+"</td>\n" +
                            "            <td align=\"center\" style=\"font-family:SimSun;\">\n" +"<img src=\"123"+$("#stampimg img").attr("src")+ "\"\/>" +
                            "             </td>\n" +
                            "        </tr>\n" +
                            "</tbody>\n" +
                            "</table>\n" +
                            "        </div>\n" +
                            "<br>&nbsp;</br>\n" +
                            "<table border=\"1px solid black\" border-spacing=\"0\" cellpadding=\"0px\" cellspacing=\"0px\" style=\"text-align = center; margin:auto;line-height:40px;width:90%;border-collapse:collapse;\">" +
                            "            <tr>" +
                            "                <th width=\"30%\" style=\"font-family:SimSun;\">印章盖章</th>\n" +
                            "                <th width=\"30%\" style=\"font-family:SimSun;\">制章点盖章</th>\n" +
                            "                <th width=\"30%\" style=\"font-family:SimSun;\">交付二维码</th>\n" +
                            "            </tr>\n" +
                            "            <tr>\n" +
                            "                <td></td>\n" +
                            "                <td></td>" +
                            "                <td align=\"center\"><img src=\"https://stamp.ieseals.cn/qrcode?code=https://stamp.ieseals.cn/scan?stampid=${stamp.id}\" width=\"90%\" height=\"190px\"></td>" +
                            "            </tr>" +
                            "            <tr>\n" +
                            "                <th style=\"height: 50px;vertical-align: middle; font-family:SimSun;\">经办人签名</th>\n" +
                            "                <td><img src=\""+$("#iSign").attr("src")+"\" width=\"140px\" height=\"70px\"/></td>\n" +
                            "            </tr>" +
                            "        </table>";
                        htmltempl = header + content + footer;
                        htmltempl = encodeURIComponent(htmltempl,"utf-8");
                        $("#htmltempl").val(htmltempl);

                        $("#submitForm").submit();

                    },
                    error: function () {
                        alert("出错了！请联系管理员！");
                        $("#previewPic").removeAttr("disabled");
                    }
                });

            });

            $("#submitForm").ajaxForm({
            dataType: 'json',
            type: "post",
            url: "${ctx}/stampMakeAction/deliveryStamp",
            success: function (result) {
                alert(result.message);
                if(result.code == 200){
                    <%--主题框架左边列表改变--%>
                    $(".nav-list",window.parent.document).children("li").removeClass("active");
                    $(".nav-list",window.parent.document).children("li:contains('已交付印章')").addClass("active");
                    window.location="${ctx}/stampMakePage/toDeliveryList";
                }else{
                    $("#my-btn10").removeAttr("disabled");
                }
            },
            error: function () {
                alert("出错了！请联系管理员！");
                $("#my-btn10").removeAttr("disabled");
            }
            });

        });


        $(function () {
            <%--修改文件--%>
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
//                        alert("修改失败，请重试。");
//                        $("#subFile").removeAttr("disabled");
                        alert(result.message);
                        location.reload();
                    }
                },
                error:function (e) {
                    alert(e);
                    $("#subFile").removeAttr("disabled");
                }
            });

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

            <%--修改印模图片，已取消此功能--%>
            $("#stampChangeForm").ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/stampMakeEdit/editEleModel",
                success:function (result) {
//                    alert(result.code);
                    if(result.code == 200){
                        alert("修改成功");
                        location.reload();
                        <%--window.location="${ctx}/stampMakePage/oneStampMake?id="+${stamp.lastRecord.id} + "&stampShape="+${stamp.stampShape};--%>
                    }else if(result.code == 404){
                        alert(result.message);
                        $("#subStamp").removeAttr("disabled");
                    }else{
                        alert("出错了");
                        $("#subStamp").removeAttr("disabled");
                    }
                },
                error:function (e) {
                    alert(e);
                    $("#subStamp").removeAttr("disabled");
                }
            });
            $("#subStamp").click(function () {
                $("#stampChangeForm").submit();
                $("#subStamp").attr("disabled","disabled");
            });
            var a = $(".text");
            var judge = /^(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga|JPG|BMP|GIF|ICO|PCX|JPEG|TIF|PNG|RAW|TGA)$/;
            for(var i = 0;i < a.length;i++){
                var b = $(a[i]).val().split("/");
                var c = $(a[i]).val().split(".");
                $(a[i]).val(b[b.length - 1]);
                if(!judge.test(c[c.length-1])){
                    $(a[i]).next().remove();
                    $(a[i]).after("&nbsp <a class='btn btn-primary' href='${ctx}/stampMakeInfo/download/file?id="+ $(a[i]).prop("id") + "'>文件下载</a>");
                }
            }
        });
    </script>
</head>
<body>
<div>
    <div class="panel-head">
        <div style="text-align: center;display: none" id="ph3"><h3>印章备案登记表</h3></div>
        <h4 id="noph3">当前路径：首页 \ 已制作印章 \ 已制作印章详情</h4>
        <span style="float: right">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate value="${stamp.createDate}"  pattern="yyyy-MM-dd"/></span>
            业务流水号：<span id="ht1">${stamp.lastRecord.serialNum}</span>
        </span>
    </div>
    <div style="margin-top: 10px;margin-bottom: 20px" id="d4">
        <button class="btn btn-success" onclick="history.go(-1)">返回上一页</button>
        <div style="float: right">
        <a id="backToWait" class="btn-lg btn-danger" href="#" onclick="returnState()">返还待刻状态</a>
        <a class="btn-lg btn-danger" href="#" onclick="cancellation()">作废</a>
        </div>
    </div>
    <button class="btn btn-success" id="change" onclick="change()">修改信息</button>
    <button class="btn btn-info" id="my-btn" style="margin: 0;display: none">提交</button>
    <form id="stampRecord" action="${ctx}/stampMakeEdit/editInfo">
        <input type="text" style="display: none" name="workType" value="${stamp.lastRecord.workType}" />
        <input type="text" style="display: none" name="id" value="${stamp.lastRecord.id}" />
        <input type="text" style="display: none" name="stamp.stampState" value="${stamp.stampState}" />
        <table class="table table-striped table-bordered" style="table-layout:fixed;word-wrap:break-word;">
            <tr>
                <th>企业名称</th>
                <td colspan="3">
                    <textarea  id="companyname"  value="${stamp.lastRecord.companyName}" readonly style="background:transparent;overflow:hidden;resize: none;border: 0px;" cols="80" rows="1">${stamp.lastRecord.companyName}</textarea>

                </td>
                <%--<td><input class="input" id="companyname" style="border: none;overflow: auto;word-break:break-all;" value="${stamp.lastRecord.companyName}" type="text" readonly/></td>--%>

            </tr>
            <tr>
                <th>企业统一社会信用代码
                </th>
                <td>
                    <%--<input class="input" id="unifiedcode" style="border: none" value="${stamp.lastRecord.soleCode}" type="text" readonly />--%>
                        ${stamp.lastRecord.soleCode}
                </td>
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
                <th>企业住所</th>
                <td colspan="3"><input class="input" id="companyaddress" name="compAddress" value="${stamp.lastRecord.compAddress}" type="text" readonly /></td>
            </tr>
            <tr>
                <th>法人姓名</th>
                <td><input class="input" id="legalName" name="legalName" value="${stamp.lastRecord.legalName}" type="text" readonly/></td>
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
                <td><input class="input" id="legalid" name="legalCertCode" value="${stamp.lastRecord.legalCertCode}" type="text" disabled="disabled" /></td>
            </tr>
            <tr>
                <th>经办人姓名</th>
                <td><input class="input" id="personname" name="agentName" value="${stamp.lastRecord.agentName}" type="text" readonly /></td>
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
                <td><input class="input" id="personid" name="agentCertCode" value="${stamp.lastRecord.agentCertCode}" type="text" disabled="disabled" /></td>
            </tr>
        </table>
    </form>
    <table class="table table-striped table-bordered subbody">
        <thead>
        <tr>
            <%--<th>序号</th>--%>
            <th>印章名称</th>
            <th>章型</th>
            <th>章材</th>
            <th>印模</th>
        </tr>
        </thead>
        <tbody id="tbody" height="190px">
        <tr>
            <%--<th id="xuhao" style="vertical-align:middle;">1</th>--%>
            <td align="center" id="stampname">
                <%--<textarea  value="${stamp.lastRecord.companyName}${fns:getDictLabel(stamp.stampType, "stampType",null )}" readonly style="background:transparent;overflow:hidden;resize: none;border: 0px;" cols="50" rows="1">${stamp.lastRecord.companyName}${fns:getDictLabel(stamp.stampType, "stampType",null )}</textarea>--%>
                    ${stamp.lastRecord.companyName}${fns:getDictLabel(stamp.stampType, "stampType",null )}
            </td>
            <td align="center" id="stamptype">
                ${fns:getDictLabel(stamp.stampShape,"stampShape" ,null )}
            </td>
            <td align="center" id="stamptexture">${fns:getDictLabel(stamp.stampTexture,"stampTexture",null)}
                <c:if test="${stamp.stampShape == 2}">
                    Ukey
                </c:if>
            </td>
            <td align="center" id="stampimg">
                <img src="/img${stamp.eleModel}" alt="">
                <%--<c:if test="${fns:getDictLabel(stamp.stampShape,'stampShape' ,null ) == '电子印章'}">--%>
                    <%--<button class="btn" id="changeStamp" onclick="changeStamp()">修改印模</button>--%>
                    <%--<button class="btn btn-info" id="subStamp" style="margin: 0;display: none">提交</button>--%>
                    <%--<form:form id="stampChangeForm" action="${ctx}/stampMakeEdit/editEleModel" method="post" enctype="multipart/form-data">--%>
                        <%--<input type="text" name="id" value="${stamp.id}" style="display: none">--%>
                        <%--<input type="text" name="type" value="${stamp.stampShape}" style="display: none">--%>
                        <%--<div class="clearfix" style="position:relative;display: none" id="uploadimg">--%>
                            <%--<input type="button" class="btn" value="选择文件">--%>
                            <%--<input type="text"  readonly style="max-width: 150px">--%>
                            <%--<input class='upload' id="file" name="file" type='file'>--%>
                            <%--<input style="display: none" class="btn btn-primary showimg" data-toggle="modal" data-target=".modal2" onclick="showimg(this)" type="button" value="查看">--%>
                        <%--</div>--%>
                    <%--</form:form>--%>
                <%--</c:if>--%>
                <%--<c:if test="${fns:getDictLabel(stamp.stampShape,'stampShape' ,null ) == '物理印章'}">--%>
                    <%--<button class="btn" id="changeStamp" onclick="changeStamp()">修改印模</button>--%>
                    <%--<button class="btn btn-info" id="subStamp" style="margin: 0;display: none">提交</button>--%>
                    <%--<form:form id="stampChangeForm" action="${ctx}/stampMakeEdit/editEleModel" method="post" enctype="multipart/form-data">--%>
                        <%--<input type="text" name="id" value="${stamp.id}" style="display: none">--%>
                        <%--<input type="text" name="type" value="${stamp.stampShape}" style="display: none">--%>
                        <%--<div class="clearfix" style="position:relative;display: none" id="uploadimg">--%>
                            <%--<input type="button" class="btn" value="选择文件">--%>
                            <%--<input type="text"  readonly style="max-width: 150px">--%>
                            <%--<input class='upload' id="file" name="file" type='file'>--%>
                            <%--<input style="display: none" class="btn btn-primary showimg" data-toggle="modal" data-target=".modal2" onclick="showimg(this)" type="button" value="查看">--%>
                        <%--</div>--%>
                    <%--</form:form>--%>
                <%--</c:if>--%>
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
        <table class="table table-striped table-bordered" id="files">
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
                                            <input class="btn btn-primary showimg" name="/img${item.attachPath}" data-toggle="modal" data-target=".modal2" onclick="showimg2(this)" type="button" value="查看">
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
                            <input class="addhtml2 btn" type="button" style="margin-top: 2px;display: none" id="${dictMap.dict.value}" value="+">
                        </div>
                            <%--<button type="button" onclick="seeimg('${dict.value}','${dict.label}')" class="btn btn-primary seeimg" data-toggle="modal" data-target=".bs-example-modal-lg">查看</button>--%>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form:form>

    <table class="table table-striped table-bordered" style="margin-top:20px;margin-bottom:20px;" id="d3">
        <tr>
            <td rowspan="3" style="width: 10%;line-height:45px;text-align: center"><strong>交付材料上传</strong><br><font id="font2" style="font-size:15px;color: red;"></font></td>
        </tr>
        <%--<tr>
            <td><strong>经办人照片上传：</strong></td>
            <td width="380px">
                <div class="box">
                    <div class="clearfix" style="position:relative;">
                        <input type="button" class="btn" value="选择文件">
                        <input type="text"  readonly style="max-width: 150px">
                        <input class='upload' id="file1" name="photo" type='file' accept="image/jpeg,image/png,image/bmp">
                        <input style="display: none" class="btn btn-primary showimg" data-toggle="modal" data-target=".modal2" onclick="showimg(this)" type="button" value="查看">
                    </div>
                    <input class="addhtml btn" type="button" style="margin-top: 2px" value="+">
                </div>
            </td>
        </tr>
        <tr>
            <td><strong>备案登记表上传：</strong></td>
            <td width="380px">
                <div class="clearfix" style="position:relative;">
                    <input type="button" class="btn" value="选择文件">
                    <input type="text"  readonly style="max-width: 150px">
                    <input class='upload' id="file2" name="record" type='file' accept="image/jpeg,image/png,image/bmp">
                    <input style="display: none" class="btn btn-primary showimg" data-toggle="modal" data-target=".modal2" onclick="showimg(this)" type="button" value="查看">
                </div>
            </td>
        </tr>--%>
        <tr>
            <td><strong>操作步骤：</strong>
                <br>1.扫描右边的二维码，打开小程序；
                <br>2.经办人拍照；
                <br>3.经办人签名；
                <br>4.打印备案登记表，用扫描仪扫描上传【要求端正清晰】；
                <br>5.印章交付。
            </td>
            <td width="380px">
                <div style="position:relative;">
                    <img src="https://stamp.ieseals.cn/qrcode?code=https://stamp.ieseals.cn/scan?stampid=${stamp.id}" id="QRCode" width="40%" height="40%">
                    <a class="btn-lg btn-success" id="previewPic" style="cursor: pointer;"
                       onclick="getpreviewPic()" data-toggle="modal" data-target=".bs-example-modal-lg">预览交付材料</a>
                </div>
            </td>
        </tr>
        <tr>
            <form id="uploadRecordForm" action="" method="post" enctype="multipart/form-data">
                <td><strong>备案登记表上传：</strong></td>
                <td width="380px">
                    <div style="position:relative;">
                        <input id="selectBtn" type="button" class="btn btn-info" value="选择文件">
                        <input type="text" readonly style="max-width: 150px;line-height: 1.4285;display: inline-block;">
                        <input id="fileUpload" style="display: none" class='upload' name="file" type='file' accept="image/*">
                        <input style="display: none" class="btn btn-primary showimg" data-toggle="modal" data-target=".modal2" onclick="showimg(this)" type="button" value="查看">
                        <input class="btn btn-danger showimg" onclick="uploadRecord(this)" type="button" value="上传">
                        <input style="display: none" type="text" name="stampid" value="${stamp.id}">
                        <input style="display: none" type="text" name="xflag" value="4">
                    </div>
                </td>
            </form>
        </tr>
    </table>
        <table class="table table-striped table-bordered sigtable" style="display: none" id="print">
            <tr>
                <th width="30%">印章盖章</th>
                <th width="30%">制章点盖章</th>
                <th width="30%">交付二维码</th>
            </tr>
            <tr>
                <td align="center"><%--<img src="/img${stamp.eleModel}" id="stampimg2" alt="">--%></td>
                <td align="center"><%--<img src="/img${eleModel}" id="stampimg3" alt="">--%></td>
                <td align="center"><img src="https://stamp.ieseals.cn/qrcode?code=https://stamp.ieseals.cn/scan?stampid=${stamp.id}" width="90%" height="190px"></td>
            </tr>
            <tr>
                <th style="height: 50px;vertical-align: middle;">经办人签名</th>
                <td align="center" colspan="2" height="80px">
                <img src="" id="iSign" width="140px" height="70px">
                </td>
            </tr>
        </table>
    <form id="submitForm" action="" method="post" enctype="multipart/form-data">
        <input name="htmltempl" id="htmltempl" value="" hidden>
        <input name="id" value="${stamp.id}" hidden>
        <input name="stampShape" value="${stamp.stampShape}" hidden>
        <div style="text-align: center;margin-bottom: 20px" id="d2">
            <a class="btn-lg btn-primary" href="#" onclick="printfPre()">打印</a>
            <a id="writechip" class="btn-lg btn-primary" href="#" data-toggle="modal"
               data-target="#WriteChipModal" onclick="return false;">写入芯片</a>
            <button class="btn-lg btn-danger" type="button" id="my-btn10" >印章交付</button>
        </div>
    </form>
        <input id="recordId" value="${stamp.lastRecord.id}" hidden>
</div>

<!-- 芯片写入弹出窗 -->
<div class="modal fade" id="WriteChipModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content modal-bg-white" id="my-modal-content">
            <div class="modal-header" style="height:40px;margin-bottom: 5px;">
                <h4 class="modal-title" style="margin-bottom: 5px;">芯片写入信息</h4>
            </div>
            <div class="modal-body" >
                <form >
                    <table style="width:100%;">
                        <tr style="height:25px;">
                            <td style="text-align: right"><strong>印章编码:&nbsp;&nbsp;</strong></td>
                            <td><input type="text" id="seal_code"  readonly  value="${stamp.stampCode}"/></td>
                        </tr>

                        <tr style="height:25px;">
                            <td style="text-align: right"><strong>印章名称:&nbsp;&nbsp;</strong></td>
                            <td><input type="text" id="seal_name" readonly value="${stamp.lastRecord.companyName}${fns:getDictLabel(stamp.stampType, "stampType",null )}" /></td>
                        </tr>

                        <tr style="height:25px;">
                            <td style="text-align: right"><strong>使用单位名称:&nbsp;&nbsp;</strong></td>
                            <td><input type="text" id="use_company_name" readonly value="${stamp.lastRecord.companyName}" /></td>
                        </tr>

                        <tr style="height:25px;">
                            <td style="text-align: right"><strong>制作单位名称:&nbsp;&nbsp;</strong></td>
                            <td><input type="text" id="make_company_name" readonly value="${stamp.lastRecord.makeComp.companyName}" /></td>
                        </tr>

                    </table>
                    <br/>
                    <br/>
                    <center>
                        <button type="button"  id="sure-btn" class="btn btn-primary" style="margin-right:20px;" onclick="writechip()" data-dismiss="modal" >确认</button>

                        <button id="cancle-btn" type="button" class="btn btn-primary" data-dismiss="modal" id="Es-btn" >取消</button>
                    </center>
                </form>
            </div>
        </div>
    </div>
</div>

<!--图片查看模态框-->
<div class="modal fade modal2" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content2">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 90%" id="modalimg">
        </div>
    </div>
</div>
<!--模态框-->
<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <div >
            <img id="tabAttachmentImg1" style="width: 40%;height: auto" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
            <img id="tabAttachmentImg2" style="width: 40%;height: auto" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
            <img id="tabAttachmentImg3" style="width: 40%;height: auto" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
            <img id="tabAttachmentImg4" style="width: 40%;height: auto" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
            <img id="tabAttachmentImg5" style="width: 40%;height: auto;background-color: white;" onerror="this.src='${ctxHtml}/images/no-photo.jpg'">
            </div>
        </div>
    </div>
</div>

<div id="fade" class="black_overlay"></div>
<div id="MyDiv" class="load_img">
    <img src="${ctxStatic}/sign/img/loading.gif">
</div>

<div class="my-mask"></div>

<script>
    //预览交付材料
    function getpreviewPic() {

        $.ajax({
            dataType: 'json',
            type: "post",
            url: "${ctx}/stampMakeAction/previewPic?stampId=" + '${stamp.id}',
            success: function (result) {
                $("#tabAttachmentImg1").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment1 });
                $("#tabAttachmentImg2").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment2 });
                $("#tabAttachmentImg3").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment3 });
                $("#tabAttachmentImg4").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment4 });
                $("#tabAttachmentImg5").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment10 });
                $("#iSign").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment10 });
                    },
            error: function () {
                alert("出错了！请联系管理员！");
                $("#previewPic").removeAttr("disabled");
            }
        });

    }

    //写入芯片
    function writechip(){

        var seal_code = document.getElementById("seal_code").value;
//        var seal_name = document.getElementById("seal_name").value;
//        var use_company_name = document.getElementById("use_company_name").value;
//        var make_company_name = document.getElementById("make_company_name").value;
//        if(seal_code==null || seal_code=="") seal_code="空";
//        if(seal_name==null || seal_name=="") seal_name="空";
//        if(use_company_name==null || use_company_name=="") use_company_name="空";
//        if(make_company_name==null || make_company_name=="") make_company_name="空";

        //数据加密
        var datas = [];
        datas[0] = seal_code;
//        datas[1] = seal_name;
//        datas[2] = use_company_name;
//        datas[3] = make_company_name;
        $.ajax({
            async : false,
            cache : false,
            traditional: true,
            type : 'POST',
            data : {
                datas:datas
            },
            dataType: 'json',
            url : "${ctx}/stampMakeAction/encryptChipData",  // 请求url
            success : function(data) {
                seal_code = data.encryptData0;
            },
            error : function() {// 请求失败处理函数
                alert("出错了！请联系管理员！");
            }
        });

        try{
            var strPath="cmd /c @echo off & c: && cd \\seal && java -jar c:/seal/writechip_stamp.jar "
                + seal_code +" & echo/ & echo 请按任意键退出哦... & pause>nul";
            var objShell=new ActiveXObject("WScript.Shell");
            objShell.Run(strPath,5,true);
            objShell=null;
            window.status="操作结束。";
        }catch(e){
            alert("操作失败!原因可能是浏览器安全选项设置不对或找不到客户端处理程序.");
        }
    }

    /*上传备案登记表*/
    function uploadRecord(){
        if ($('#fileUpload').val() == '' || $('#fileUpload').val() == null){
            alert("请选择文件！");
        }else {
            ShowDiv('MyDiv','fade');//加载中...
            $("#uploadRecordForm").submit();
        }

    }
    //弹出隐藏层
    function ShowDiv(show_div,bg_div){
        document.getElementById(show_div).style.display='block';
        document.getElementById(bg_div).style.display='block' ;
        var bgdiv = document.getElementById(bg_div);
        bgdiv.style.width = document.body.scrollWidth;
        $("#"+bg_div).height($(document).height());
    };
    //关闭弹出层
    function CloseDiv(show_div,bg_div)
    {
        document.getElementById(show_div).style.display='none';
        document.getElementById(bg_div).style.display='none';
    };

    <%--作废功能--%>
    function cancellation(){
        var msg = "确定作废吗？";
        if(confirm(msg)){
            //  确定作废
            $.ajax({
                type:"POST",
                url:"${ctx}/stampMakeAction/cancelStamp",
                dateType:"JSON",
                data:{"stampId":"${stamp.id}","stampShape": "${stamp.stampShape}"},
                success:function (result) {
                    result = JSON.parse(result);
                    alert(result.message);
                    if(result.code == 200){
                        location.href = "${ctx}/stampMakePage/toFinishList";
                    }
                },
                error:function(){
                    alert("出错了，请重试!");
                }
            })
        }
    }
    <%--返还待刻状态--%>
    function returnState(){
        var msg = "确定返还为待刻状态吗？";
        if(confirm(msg)){
//            确定返还
            $.ajax({
                type:"POST",
                url:"${ctx}/stampMakeEdit/returnRecept",
                dateType:"JSON",
                data:{"stampId":"${stamp.id}","stampShape": "${stamp.stampShape}"},
                success:function (result) {
                    result = JSON.parse(result);
                    alert(result.message);
                    if(result.code == 200){
                        $(".nav-list",window.parent.document).children("li").removeClass("active");
                        $(".nav-list",window.parent.document).children("li:contains('待刻印章')").addClass("active");
                        location.href = "${ctx}/stampMakePage/toMakeList";
                    }
                },
                error:function(){
                    alert("出错了，请重试!");
                }
            })
        }
    }
    <%--以下为点击修改备案、文件、印模时的方法--%>
    function changeStamp(){
        $("#subStamp").show();
        $("#stampimg").hide();
        $("#uploadimg").show();
        $("#changeStamp").hide();
    }
    function changeFile(){
        if (${empty stamp.lastRecord.sblsh} || ${stamp.lastRecord.sblsh eq null}) {
            $(".addhtml2").show();
            $(".delFile").show();
            $("#subFile").show();
            $("#changeFile").hide();
        } else {
            alert("【此印章为多证合一数据，无附件材料】");
        }
    }
    function change(){
        if (${empty stamp.lastRecord.sblsh} || ${stamp.lastRecord.sblsh eq null}) {
            $("#my-btn").show();
            $("#change").hide();
            $("#cct").hide();
            $("#act").hide();
            $("#lct").hide();
            $("#legalid").removeAttr("disabled");
            $("#personid").removeAttr("disabled");
            $("input").removeAttr("readonly");
            $("input").addClass("change");
            $("select").show();

            var tempType1 = $("#temp").val();
            var tempLegalCertType = $("#tempLegalCertType").val();
            var tempAgentCertType = $("#tempAgentCertType").val();

            $("#comCertType").find("option[value = '" + tempType1 + "']").attr("selected", true);
            $("#legalCertType").find("option[value = '" + tempLegalCertType + "']").attr("selected", true);
            $("#agentCertType").find("option[value = '" + tempAgentCertType + "']").attr("selected", true);
        } else {
            alert("【此印章为多证合一数据，无法修改】");
        }
    }

    function showimg(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
        if(IEVersion()<10){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }
    $("#fileUpload").change(function(){
        var objUrl = getObjectURL(this.files[0]);
        if(objUrl){
            $('#modalimg').attr("src",objUrl);
        }
    })
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

    function showimg2(a){
        var isIE = navigator.userAgent.match(/MSIE/)!= null,
            isIE8 = navigator.userAgent.match(/MSIE 8.0/)!= null;
        if(IEVersion()<10){
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
        }
        else{
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }

    <%--打印函数--%>
    function printf(){

                $("#d1").hide();
                $("#d2").hide();
                $("#d3").hide();
                $("#d4").hide();
                $("#change").hide();
                $("#changeFile").hide();
                $("#changeStamp").hide();
                $("#files").hide();
                $("#noph3").hide();
                $("#my-btn10").hide();
                $("#ph3").show();
                $("#print").show();
                $("body").attr("style");

                window.print();
                $("#d1").show();
                $("#d2").show();
                $("#d3").show();
                $("#d4").show();
                $("#change").show();
                $("#changeFile").show();
                $("#changeStamp").show();
                $("#files").show();
                $("#noph3").show();
                $("#my-btn10").show();
                $("#ph3").hide();
                $("#print").hide();
                $("body").removeAttr("style")

    }
   //打印前获取经办人签名
    function printfPre(){
        $.ajax({
            dataType: 'json',
            type: "post",
            url: "${ctx}/stampMakeAction/previewPic?stampId=" + '${stamp.id}',
            success: function (result) {
                if (result.tabAttachment10 == '' || result.tabAttachment10 == null || result.tabAttachment10 == undefined){
                    $("#iSign").attr("style","display:none");
                }

                $("#iSign").attr({"src": "https://stamp.ieseals.cn/img/zf/img/" + result.tabAttachment10 });
                setTimeout(printf(),8000);
            },
            error: function () {
                alert("出错了！请联系管理员！");
                $("#previewPic").removeAttr("disabled");
            }
        });
    }


    $(function(){
        //提交备案登记表表单
        $("#uploadRecordForm").ajaxForm({
            dataType: 'json',
            type: "post",
            url: "https://stamp.ieseals.cn:443/stamp/upload/zf",
            success:function (result) {
                if(result.result == 'success'){
                    alert("上传成功！");
                }else {
                    alert("上传失败，请重试！");
                }
                CloseDiv('MyDiv','fade');
            },
            error:function (e) {
                CloseDiv('MyDiv','fade');
                alert(e);
            }
        });
        
        //触发文件上传按钮
        $("#selectBtn").click(function () {
            $("#fileUpload").click();
        })

        <%--点击提交文件时，判断是否有选择文件--%>
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
        <%--设置上传文件个数限制，最多五个--%>
        $(".addhtml2").click(function(){
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
                    "<input type='text' name='fileType' value='"+ $(this).prop("id") +"' style='display:none;'>" +
                    "<input type='button' class='btn' value='选择文件'>" +
                    "<input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly>" +
                    "<input class='upload' name='news' type='file' style='opacity: 0;z-index: 999;margin-left: -90px;'>" +
                    "<input style='display: none' class='btn btn-primary showimg'data-toggle='modal' data-target='.modal2' type='button' onclick='showimg(this)' value='查看'>" +
                    "<input type='button' value='×' class='btn del'></div>")
            }else{
                alert("上一项未填！")
            }
        });
        //图片上传添加按钮
        $(".addhtml").click(function(){
            var hasfile = $(this).prev("div").children("input[type='file']").val();
            var textval = $(this).prev("div").children("input[type='text']").val();
            var checked = $(this).parents("td");
            var a = checked.find("input[type = 'text']");
            if((a.length/2) == 5){
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
                    "<input type='text' name='fileType' value='"+ textval +"' style='display:none'>" +
                    "<input type='button' class='btn' value='选择文件'>" +
                    "<input type='text' style='margin-left: 4px;max-width: 150px;height: 30px;line-height: 30px' readonly> " +
                    "<input class='upload' name='photo' type='file'>" +
                    " <input style='display: none' class='btn btn-primary showimg' data-toggle='modal' data-target='.modal2' type='button' onclick='showimg(this)' value='查看'>" +
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
