<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章刻制备案</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/myCss.css?id=${fns:getConfig('css.version')}">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css?id=${fns:getConfig('css.version')}">
    <link rel="stylesheet" href="${ctxHtml}/css/jquery-ui-1.9.2.custom.min.css">
    <script type="text/javascript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <%--添加备案印模js插件--%>
    <script type="text/javascript" src="${ctxHtml}/js/myScript.js?id=${fns:getConfig('js.version')}"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-ui-1.9.2.custom.min.js"></script>
    <script src="${ctxHtml}/js/stampengravechecked.js?id=${fns:getConfig('js.version')}"></script>
    <script src="${ctxHtml}/js/ajaxfileupload.js?id=${fns:getConfig('js.version')}"></script>

    <style type="text/css">
        body {
            padding: 0 2%;
            font-family: "微软雅黑";
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

        .modal-content {
            background: rgba(0, 0, 0, 0);
            border: none;
            box-shadow: none;
            text-align: center;
        }

        .box {
            margin: 0;
            width: 100%;
        }

        .del {
            padding: 1px 5px;
            font-size: 18px;
            color: white;
            margin-left: 5px;
            background-color: #c9302c !important;
            border-color: #ac2925 !important;
        }

        .del:hover {
            background-color: #333 !important;
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
            cursor: pointer;
        }

        .showtext {
            width: 100% !important;
        }

        #my-btn:disabled {
            opacity: 0.5;
        }

        input[type="text"], input[type="number"] {
            height: 30px;
            line-height: 30px;
            width: 65%;
        }

        input[type="file"] {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            opacity: 0;
            height: 30px;
            filter: progid:DXImageTransform.Microsoft.Alpha(opacity=0);
        }
    </style>

    <script>


        $(document).ready(function () {

            //TODO
            $('#stampRecord').ajaxForm({
                beforesend:$(this).attr("disabled","disabled"),
                dataType: 'json',
                type: "post",
                contentType: "application/json;charset=utf-8",
                url: "${ctx}/stampMakeAction/makeStampRecord",
                success: function (result) {
                    alert(result.message);
                    if (result.code == 200) {
                        $(".nav-list",window.parent.document).children("li").removeClass("active");
                        $(".nav-list",window.parent.document).children("li:contains('待刻印章')").addClass("active");
                        window.location = "${ctx}/stampMakeAction/makeStamp/Receipt?serialNum=" + result.entity.serialNum;
                    } else {
                        if((result.message).indexOf("附件")>=0){
                            alert("必填附件不能为空!")
                        }
                        if((result.message).indexOf("印章形式")>=0){
                            alert("请选择对应的印章形式!")
                        }

                        $("#my-btn").removeAttr("disabled");
                    }
                },
                error: function () {
                    alert("出错了！请联系管理员！");
                    $("#my-btn").removeAttr("disabled");
                }
            });
        });

        var checkEleStampCount = 0;
        var checkEngraveCount = 0;
        var engrave_02;
        var engrave_03;
        var engrave_04;
        var engrave_05;
        var engrave_06;
        var engrave_07;
        var stampType01Count = 0;

        function checkEngrave(stamp, engrave, cbElestamp, cbEngrave, checkbox) {
            //alert(engrave);
            $(cbEngrave).removeAttr("disabled");     //物理印章解锁
            if (engrave > 0) {
                $(cbElestamp).removeAttr("disabled");     //电子印章解锁
            } else {
                alert("需要先存在对应的物理印章，才可以选择电子印章。");
                checked(cbEngrave, true);
                $(cbEngrave).click(
                    function () {
                        this.checked = !this.checked;
                    }
                );
                $(cbEngrave).parent().parent().next().children(":first").css("display", "");     //显示章材
                $(cbElestamp).removeAttr("disabled");     //电子印章解锁
            }
        }

        function checked(cb, isNot) {
            if (isNot) {      //true让选择框选中
                if ($(cb).attr("disabled") == "disabled") {     //如果一开始被控制了
                    //alert("是不是不可选"+$(cb).attr("disabled"));
                    $(cb).removeAttr("disabled");       //取消控制
                    $(cb).prop("checked", "checked");
                } else {      //没被控制则正常执行
                    if ($(cb).attr("checked")) {      //如果选中了
                        //啥也不做
                    } else {                                       //没选中
                        $(cb).prop("checked", "checked");        //让checkbox选中
                    }
                }
            } else {      //false让选择框取消选中
                if ($(cb).attr("disabled") == "disabled") {     //如果一开始被控制了
                    $(cb).removeAttr("disabled");       //取消控制
                    $(cb).prop("checked", false);          //取消选中
                } else {      //没被控制则正常执行
                    if ($(cb).attr("checked")) {      //如果选中了
                        $(cb).prop("checked", false);        //让checkbox取消选中
                    } else {                                       //没选中
                        //啥也不做
                    }
                }
            }
        }


        /*选择公章后检查该公司是否已经制作过公章*/
        function checkMakeCount(t) {
            var count = 0;
            $(".stmapTypeSelect").each(function () {
                //alert($(this).val());
                if ($(this).val() == 1) {
                    count += 1;
                    if (count > 1) {
                        stampType01Count = 1;
                    }
                }
            });

            //刻章类型选框的<td>的下一个<td>就是物理/电子印章的选择
            var checkboxs = $(t).parents("td").next();
            //<td>内有两个<span>，物理/电子印章

            //第一个是物理印章的<span>，<span>内的第一个子元素为<input type="checkbox">
            var cbEngrave = checkboxs.children(":first").children(":first");
            //第一个的下一个是电子印章的<span>，<span>内的第一个子元素为<input type="checkbox">
            var cbElestamp = checkboxs.children(":first").next().children(":first");

            //alert("物理选择："+$(cbEngrave).val()+"；电子选择："+$(cbElestamp).val());
            $(cbEngrave).attr("disabled", "disabled");
            $(cbElestamp).attr("disabled", "disabled");
            if (t.value == 1) {
                if (stampType01Count == 1) {
                    alert("该公司已存在公章，无法重复刻制！");
                    $(cbEngrave).prop("checked", false);
                    $(cbEngrave).attr("disabled", "disabled");
                    $(cbEngrave).parent().parent().next().children(":first").css("display", "block");     //显示章材

                    $(cbElestamp).prop("checked", false);
                    $(cbElestamp).attr("disabled", "disabled");

                    t.value = "";
                } else {
                    //alert(checkEleStampCount + "...." + checkEngraveCount);
                    if (checkEleStampCount == 0 & checkEngraveCount == 0) {
                        alert("需要先存在对应的物理印章，才可以选择电子印章。");
                        checked(cbEngrave, true);
                        $(cbEngrave).click(
                            function () {
                                this.checked = !this.checked;
                            }
                        );
                        $(cbEngrave).parent().parent().next().children(":first").css("display", "");     //显示章材
                        $(cbElestamp).removeAttr("disabled");     //电子印章解锁
                    } else if (checkEleStampCount > 0 & checkEngraveCount > 0) {
                        checked(cbElestamp, false);
                        $(cbElestamp).attr("disabled", "disabled");
                        checked(cbEngrave, false);
                        $(cbEngrave).attr("disabled", "disabled");
                        alert("该公司已存在公章，无法重复刻制！");
                    } else {
                        if (checkEleStampCount > 0) {
                            alert("该公司已存在公章，无法重复刻制！");
                            checked(cbElestamp, false);
                            $(cbElestamp).attr("disabled", "disabled");
                            $(cbEngrave).removeAttr("disabled");
                        }
                        if (checkEngraveCount > 0) {
                            alert("该公司已存在公章，无法重复刻制！");
                            checked(cbEngrave, false);
                            $(cbEngrave).attr("disabled", "disabled");
                            $(cbEngrave).parent().parent().next().children(":first").css("display", "none");     //不显示章材
                            $(cbElestamp).removeAttr("disabled");
                        }
                    }
                }
            }

            else if (t.value == 2) {
                checkEngrave("2", engrave_02, cbElestamp, cbEngrave, t);
            } else if (t.value == 3) {
                checkEngrave("3", engrave_03, cbElestamp, cbEngrave, t);
            } else if (t.value == 4) {
                checkEngrave("4", engrave_04, cbElestamp, cbEngrave, t);
            } else if (t.value == 5) {
                checkEngrave("5", engrave_05, cbElestamp, cbEngrave, t);
            } else if (t.value == 6) {
                checkEngrave("6", engrave_06, cbElestamp, cbEngrave, t);
            } else if (t.value == 7) {
                checkEngrave("7", engrave_07, cbElestamp, cbEngrave, t);
            } else {      //其他类型没限制
                $(cbElestamp).removeAttr("disabled");
                $(cbEngrave).removeAttr("disabled");
            }
        }


        <%--输入统一信用代号后识别是否已经备案过的公司的代码  onblur="automatic(this)"--%>
        function automatic(t) {
            var codeCheck = $('#stampRecord input[name="stampRecord.isSoleCode"]:checked').val();

            $(".bg-model").fadeTo(300, 1);
            //隐藏窗体的滚动条
            $("body").css({"overflow": "hidden"});
            setTimeout(function () {
                if ($(t).next().length == 0) {
                    $.ajax({
                        type: "post",
                        url: "${ctx}/stampMakeInfo/findUseCompany?soleCode=" + $("#unifiedcode").val(),
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
//                    法人
                                $("#legalName").val(result.entity.legalName).attr("readonly", "readonly");
                                $("#legalphone").val(result.entity.legalPhone).attr("readonly", "readonly");
                                $("#legalCertType").val(result.entity.legalCertType).attr("disabled", "disabled");
                                $("#legalid").val(result.entity.legalCertCode).attr("readonly", "readonly");
                                $("#legalName").next().remove();
                                $("#legalphone").next().remove();
                                $("#legalCertType").next().remove();
                                $("#legalid").next().remove();
//                    公司
                                $("#companyname").val(result.entity.companyName).attr("readonly", "readonly");
                                $("#companyphone").val(result.entity.compPhone).attr("readonly", "readonly");
                                $("#companyaddress").val(result.entity.compAddress).attr("readonly", "readonly");
                                $("#comCertType").find("option[value = '"+result.entity.type1+"']").attr("selected",true);
                                $("#companyname").next().remove();
                                $("#companyphone").next().remove();
                                $("#companyaddress").next().remove();
                                $("#unifiedcode").attr("readonly", "readonly");
                                $("#comCertType").attr("disabled", "disabled");

                                //从后台范围的结果赋值给电子印章（公章）计数参数
                                checkEleStampCount = result.entity.checkEleStampCount;
                                //从后台范围的结果赋值给物理印章（公章）计数参数
                                checkEngraveCount = result.entity.checkEngraveCount;

                                //从后台范围的结果赋值给各种物理印章做判断的计数参数
                                engrave_02 = result.entity.engrave_02;
                                engrave_03 = result.entity.engrave_03;
                                engrave_04 = result.entity.engrave_04;
                                engrave_05 = result.entity.engrave_05;
                                engrave_06 = result.entity.engrave_06;
                                engrave_07 = result.entity.engrave_07;

                                $("#stampShape2").attr("disabled", "disabled");
                                $("#stampShape1").attr("disabled", "disabled");

                                $(".bg-model").hide();
                                //显示窗体的滚动条
                                $("body").css({"overflow": "visible"});
                            } else {
                                $(".bg-model").hide();
                                //显示窗体的滚动条
                                $("body").css({"overflow": "visible"});
                            }
                        },
                        error: function (e) {
                            alert("类型代码不能含有中文！");
                        }
                    });
                }
                $(".bg-model").hide();
                //显示窗体的滚动条
                $("body").css({"overflow": "visible"});
            }, 1000);
        }


        /*根据企业名称查询是否已存在*/
        function findUseComp() {

            $(".bg-model").fadeTo(300, 1);
            //隐藏窗体的滚动条
            $("body").css({"overflow": "hidden"});
            setTimeout(function () {
                if ($(this).next().length == 0) {
                    $.ajax({
                        type: "post",
                        url: "${ctx}/stampMakeInfo/findUseCompany?companyName=" +encodeURIComponent( $("#companyname").val()),
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 200) {
                                //法人
                                $("#legalName").val(result.entity.legalName).attr("readonly", "readonly");
                                $("#legalphone").val(result.entity.legalPhone).attr("readonly", "readonly");
                                $("#legalCertType").find("option[value = '"+result.entity.legalCertType+"']").attr("selected",true);
                                $("#legalid").val(result.entity.legalCertCode).attr("readonly", "readonly");
                                $("#legalName").next().remove();
                                $("#legalphone").next().remove();
                                $("#legalCertType").next().remove();
                                $("#legalid").next().remove();
                                $("#legalCertType").attr("disabled", "disabled");
                                //公司
                                $("#companyname").val(result.entity.companyName);
                                $("#companyphone").val(result.entity.compPhone).attr("readonly", "readonly");
                                $("#companyaddress").val(result.entity.compAddress).attr("readonly", "readonly");
                                $("#comCertType").find("option[value = '"+result.entity.type1+"']").attr("selected",true);
                                $("#companyname").next().remove();
                                $("#companyphone").next().remove();
                                $("#companyaddress").next().remove();
                                $("#unifiedcode").val(result.entity.soleCode).attr("readonly", "readonly");
                                $("#comCertType").attr("disabled", "disabled");

                                //从后台范围的结果赋值给电子印章（公章）计数参数
                                checkEleStampCount = result.entity.checkEleStampCount;
                                //从后台范围的结果赋值给物理印章（公章）计数参数
                                checkEngraveCount = result.entity.checkEngraveCount;

                                //从后台范围的结果赋值给各种物理印章做判断的计数参数
                                engrave_02 = result.entity.engrave_02;
                                engrave_03 = result.entity.engrave_03;
                                engrave_04 = result.entity.engrave_04;
                                engrave_05 = result.entity.engrave_05;
                                engrave_06 = result.entity.engrave_06;
                                engrave_07 = result.entity.engrave_07;

                                $("#stampShape2").attr("disabled", "disabled");
                                $("#stampShape1").attr("disabled", "disabled");

                                $(".bg-model").hide();
                                //显示窗体的滚动条
                                $("body").css({"overflow": "visible"});
                            } else {
                                $(".bg-model").hide();
                                //显示窗体的滚动条
                                $("body").css({"overflow": "visible"});
                                //清除值
                                $("#legalName").val('').removeAttr("readonly");
                                $("#legalphone").val('').removeAttr("readonly");
                                $("#legalCertType").val('1').removeAttr("disabled");
                                $("#legalid").val('').removeAttr("readonly");
                                $("#companyaddress").val('').removeAttr("readonly");
                                $("#comCertType").val('1').removeAttr("disabled");
                                $("#unifiedcode").val('').removeAttr("readonly");

                            }
                        },
                        error: function (e) {
                            alert("请求失败！");
                        }
                    });
                }
                $(".bg-model").hide();
                //显示窗体的滚动条
                $("body").css({"overflow": "visible"});
            }, 1000);
        }

        function checkStampType(){
            var options = $("#stampType option:selected").val();
//                alert(options);
            if(options!="0"){

            }else {
                alert("请选择刻章的种类");
                $("#stampShape1").prop("checked",false);
                $("#stampShape2").prop("checked",false);
            }
        }

    </script>
</head>
<body>
<div>
    <div class="panel-head" style="color: rgb(49, 126, 172);font-weight: bold;font-family: Telex, sans-serif;">
        <h4><strong>当前路径：首页 \ 物电一体化印章备案登记</strong></h4>
        <span style="float: right">
            登记时间：<span id="cur-time" style="margin-right: 50px"><fmt:formatDate value="${currentTime}" pattern="yyyy-MM-dd"/></span>
            业务流水号：<span>${serialNum}</span>
        </span>
    </div>
    <form:form modelAttribute="stampMakeDTO" id="stampRecord" enctype="multipart/form-data">
        <form:hidden path="stampRecord.serialNum" value="${serialNum}"></form:hidden>
        <table class="table table-striped table-bordered">
            <tr>
                <th>企业名称</th>
                <td colspan="3"><form:input type="text" id="companyname" class="form-control showtext"
                                            path="stampRecord.useComp.companyName"/></td>
            </tr>
            <tr>
                <th><form:radiobuttons path="stampRecord.isSoleCode"
                                       items="${fns:getDictList('isSoleCode')}" itemLabel="label"
                                       itemValue="value"
                                       htmlEscape="false"
                                       cssStyle="padding-left: 20px"/></th>
                <td><form:input type="text" id="unifiedcode" class="form-control showtext"
                                path="stampRecord.useComp.soleCode"/>
                </td>
                <th>企业类型</th>
                <td>
                    <form:select class="form-control showtext" id="comCertType" path="stampRecord.useComp.type1" >
                        <form:options items="${fns:getDictList('unitType')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </td>
                    <%--<th>企业联系电话--%>
                    <%--</th>--%>
                    <%--<td>--%>
                    <%--<form:input type="text" id="companyphone" class="form-control showtext"--%>
                    <%--path="stampRecord.useComp.compPhone"/>--%>
                    <%--</td>--%>
            </tr>
            <tr>
                <th>企业住所</th>
                <td colspan="3">
                    <form:input id="companyaddress" type="text" class="form-control showtext"
                                path="stampRecord.useComp.compAddress"/>
                </td>
            </tr>
            <tr>
                <th>法人姓名</th>
                <td>
                    <form:input type="text" id="legalName" class="form-control showtext"
                                path="stampRecord.useComp.legalName"/>
                </td>
                <th>法人手机号码</th>
                <td>
                    <form:input type="text" id="legalphone" class="form-control showtext"
                                path="stampRecord.useComp.legalPhone"/>
                </td>
            </tr>
            <tr>
                <th>法人证件类型</th>
                <td>
                    <form:select class="form-control showtext" id="legalCertType"
                                 path="stampRecord.useComp.legalCertType">
                        <form:options items="${fns:getDictList('certificateType')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </td>
                <th>法人证件号码</th>
                <td><form:input type="text" id="legalid" class="form-control showtext"
                                path="stampRecord.useComp.legalCertCode"/></td>
            </tr>
            <tr>
                <th>经办人姓名</th>
                <td><form:input type="text" id="personname" class="form-control showtext"
                                path="stampRecord.agentName"/></td>
                <th>经办人手机号码</th>
                <td><form:input type="text" id="personphone" class="form-control showtext"
                                path="stampRecord.agentPhone"/></td>
            </tr>
            <tr>
                <th>经办人证件类型</th>
                <td>
                    <form:select class="form-control showtext" path="stampRecord.agentCertType" id="persionType">
                        <form:options items="${fns:getDictList('certificateType')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </td>
                <th>经办人证件号码</th>
                <td>
                    <form:input type="text" id="personid" class="form-control showtext"
                                path="stampRecord.agentCertCode" />
                </td>
            </tr>
        </table>
        <table id="needcheck" class="table table-striped table-bordered ">
            <thead>
            <tr>
                <th class="add" title="添加">
                    <span class="fa fa-plus"></span></th>
                <th>序号</th>
                <th width="30%">刻章种类</th>
                <th>印章形式</th>
                <th>章材</th>
            </tr>
            </thead>
            <tbody id="tbody">
            <tr>
                <th></th>
                <th>1</th>
                <td>
                    <form:select path="stamps[0].stampType" class="form-control stmapTypeSelect required"
                                 onblur="checkMakeCount(this)" id="stampType">
                        <form:option value="0" label="--请选择刻章的种类--" htmlEscape="false" readonly="true"/>
                        <form:options items="${fns:getDictList('stampType')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </td>
                <td>
                    <form:checkboxes path="stamps[0].stampShape" items="${fns:getDictList('stampShape')}"
                                     itemLabel="label" itemValue="value" id="stampShape" onclick="checkStampType();"/>
                </td>
                <td class="zhangcai" id="zhangcai1">
                    <form:select class="form-control" path="stamps[0].stampTexture" style="display: none;">
                        <form:options items="${fns:getDictList('stampTexture')}" itemLabel="label" itemValue="value"
                                      htmlEscape="false"/>
                    </form:select>
                </td>
            </tr>
            </tbody>
        </table>
        <p class="prompt" style="color: red">
            注：全部文件大小总和不能超过20M
        </p>
        <table class="table table-striped table-bordered">
            <tr>
                <td rowspan="20" style="width: 10%;line-height:45px;text-align: center">
                    <strong>附<br>件<br>材<br>料<br>上<br>传</strong></td>
            </tr>
            <c:forEach items="${dir}" var="dictMap">
                <tr>
                    <td>
                        <c:if test="${dictMap.key!='0'}">
                            <font color="red">&nbsp;*&nbsp;</font>
                        </c:if>
                            ${dictMap.dict.label}
                        <input type="text" value="${dictMap.key}" id="requiredList" name="requiredList" style="display: none">

                    </td>
                    <td style="width: 470px;">
                        <div class="box">
                            <div class="clearfix" style="position:relative;">
                                <input type="text" value="" id="fileList${dictMap.dict.value}" name="fileList" style="display: none">
                                <a style="color:#fff;border-color: #46b8da;background:#5bc0de;position:relative;display:block;width:100px;height:30px;border:1px;text-align:center;line-height: 30px;"  href="javascript:void(0);"
                                      >上传文件
                                    <input style="position:absolute;left:0;top:0;width:100%;height:100%;z-index:999;opacity:0;" id="${dictMap.dict.value}"
                                                    type="file" name="file"  multiple="multiple"/>
                                </a>
                                <img class="load0" name="img" id="img${dictMap.dict.value}" src="" style="width: 20px;height:20px;display: none"  />

                                <input type="text" id="text${dictMap.dict.value}" readonly style="max-width: 250px">

                                <input type='text' value="-1" id='fileType${dictMap.dict.value}' name='fileType' style="display: none">

                                <input id="showImage${dictMap.dict.value}" class="btn btn-primary showimg" data-toggle="modal"
                                        data-target=".bs-example-modal-lg" onclick="showimg(this)" type="button"
                                        value="查看">
                                <input type="hidden" name="hidden" id="hidden${dictMap.dict.value}" value="" />
                                <%--<img class="load0" name="img" id="img${dictMap.dict.value}" src="" style="width: 200px;height:200px" />--%>

                            </div>
                            <input class="addhtml btn" type="button" style="margin-top: 2px" value="+">
                        </div>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <center>
            <button type="button" class="btn btn-primary" id="my-btn">提交</button>
        </center>
    </form:form>
</div>
<div class="bg-model"
     style="position:absolute;top:0;left:0;display:none;background:rgba(0,0,0,0.3);width:100%;height:100%;position:fixed;z-index:9999">
    <div class='content'
         style="position: absolute;left: 35%;top: 25%;border-radius: 8px;width: 30%;height: 40%;text-align: center">
        <h3 style="color: white;font-weight: bold">正在识别中</h3>
    </div>
</div>

<%--信息确认框--%>
<div id="dialog-message1" title="请核查您输入的企业名称" class="ui-state-error ui-corner-all">
    <%--<span class="ui-icon ui-icon-info"></span>--%>
    <p></p>
</div>

<%--信息确认框--%>
<div id="dialog-message2" title="请核查您输入的统一信用代码或其他类型代码" class="ui-state-error ui-corner-all">
    <%--<span class="ui-icon ui-icon-info"></span>--%>
    <p></p>
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


<script type="text/javascript">

    //全局变量 保存当前选中的文件file的id
    /*var gFileId =0 ;

    function getFileId(fileId) {

        gFileId = fileId;

    }*/

    $(function () {
        $(document).on("change", "input[type='file']", function (e) {
            //文件id
            var gFileId = e.target.id;
            //imgid指<img />标签id,fileid表示<input type='file' />文件上传标签的id,hiddenid指隐藏域标签id
            $("#img"+gFileId).attr("src","${ctxStatic}/images/loading.gif").removeClass("load0").show();//加载loading图片

            var files = $(e.target);
            console.log("文件路径："+ files.val());

            var file = files.val();
            var fileName = getFileName(file);
            console.log("文件名："+fileName);
            console.log("文件id："+ gFileId);

            //异步删除临时文件夹里的文件
           var fileListgFileId= $("#fileList"+gFileId).val();
           if(fileListgFileId !=""){
               $.ajax({
                   type:'POST',
                   contentType :'application/json',
                   url :'${ctx}/attachment/deleteTempFile?fileName='+fileListgFileId,
                   dataType: 'json',
                   success:function (data) {
                       if(data.code !=200){
                           var error_msg="";
                           switch(data.code)
                           {
                               case 404: error_msg="请联系管理员！错误编码：Failed to delete files1";
                                   break;
                               default :error_msg="服务器不稳定";
                           }
                           alert(error_msg);
                       }
                   },
                   error:function () {
                       console.log("错误："+e);
                       alert("请联系管理员！错误编码：Failed to delete files2");
                   }
               });

           }



            $.ajaxFileUpload
            (
                {
                    type:"POST",
                    url: '${ctx}/attachment/startUploadImage',
                    secureuri: false,
                    fileElementId: gFileId,
                    dataType: 'json',
                    success: function (data, status)
                    {   //data的内容都是在后台java代码中自定义的,json格式返回后在这里以对象的方式的访问
                        if(data.code != 200){//上传文件出错
                            console.log("data.code:"+data.code);
                            var error_msg="";
                            switch(data.code)
                            {
                                case 101: error_msg="文件类型错误";
                                    break;
                                case 102: error_msg="文件过大";
                                    break;
                                case 400: error_msg="非法上传";
                                    break;
                                case 404: error_msg="文件为空";
                                    break;
                                default :error_msg="服务器不稳定";
                            }

                            console.log(error_msg);
                            alert(error_msg);

                            //出现错误清空数据
                            $("#showImage"+gFileId).attr('name',"");

                            $("#fileList"+gFileId).attr('value',"");

                            $("#text"+gFileId).attr('value',"");

                            $("#img"+gFileId).hide();//隐藏加载loading图片

                        }else{


                            $("#img"+gFileId).hide();//隐藏加载loading图片

//                            $("#img"+gFileId).attr("src",data.message).addClass("load1");//加载返回的图片路径并附加上样式
                            $("#hidden"+gFileId).val(data.message);//给对应的隐藏域赋值，以便提交时给后台
                            //给模态框赋值
                            $("#showImage"+gFileId).attr('name',data.message);
                            //此赋值为了后台判断是否上传了必填项文件
                            if($("#fileType"+gFileId).val()== -1){
                                $("#fileType"+gFileId).attr('value',gFileId);
                            }

                            //给text框赋值
                            $("#text"+gFileId).attr('value',fileName);

                            //赋值前清空数据
                            $("#fileList"+gFileId).attr('value',"");
                            //后台返回的路径
                            var locHref = data.message;
                            var locArray = locHref.split("/");
                            var copyFileName = locArray[locArray.length-1];
                            //赋值：为后台拷贝文件时做准备
                            $("#fileList"+gFileId).attr('value',copyFileName);

                        }
                    },
                    error: function (data, status, e)
                    {
                        console.log("错误："+e);
                        alert("文件上传失败，请联系管理员！");
                    }
                }
            )



        });
    });

    //获取文件名
    function getFileName(o){
        var pos=o.lastIndexOf("\\");
        return o.substring(pos+1);
    }



    //图片上传添加按钮，最多只能上传五个
    $(".addhtml").click(function () {
        var randNum =Math.floor( Math.random()*1000+1000);
        var hasfile = $(this).prev("div").children("input[type='file']").val();

        var fileTypeValue = $(this).prev("div").children("input[name='fileType']").val();
        console.log("fileTypeValue:"+fileTypeValue)
        var checked = $(this).parents("td");
        var a = checked.find("input[type = 'text']");
        console.log("文件个数："+a.length);
        if ((a.length)/3== 5) {
            alert("最多只能上传五个文件。");
            return false;
        }
        var x = true;
        for (var i = 0; i < a.length; i++) {
            if (a.eq(i).val() == "" || a.eq(i).val() == null) {
                x = false;
            }
        }
        if (x) {
            $(this).before(" <div class='clearfix' style='position:relative;'>"+
                "<input type='text' value='' id='fileList"+randNum+"' name='fileList' style='display: none'>"+
                "<a style='color:#fff;border-color: #46b8da;background:#5bc0de;position:relative;display:block;width:100px;height:30px;border:1px;text-align:center;line-height: 30px;' " +
                "href='javascript:void(0);'onclick='getFileId("+randNum+");'  >上传文件"+
                "<input style='position:absolute;left:0;top:0;width:100%;height:100%;z-index:999;opacity:0;' id='"+randNum+"'"+
                "type='file' name='file'  multiple='multiple'/>"+
                "</a>"+
                "<input type='text' id='text"+randNum+"' readonly style='max-width: 250px' >"+
                "<input type='text' value='" + fileTypeValue + "' id='fileType"+randNum+"' name='fileType' style='display: none'>"+
                "<input id='showImage"+randNum+"' class='btn btn-primary showimg' data-toggle='modal'"+
                "data-target='.bs-example-modal-lg' onclick='showimg(this)' type='button' value='查看'>"+
                "<input type='hidden' name='hidden' id='hidden"+randNum+"' value='' />"+
//                "<img class='load0' name='img' id='img"+randNum+"' src='' style='width: 200px;height:200px' />"+
                "<input type='button' value='×' class='btn del'>"+

                "</div>")
        } else {
            alert("上一项未填！")
        }
    });

    $(document).on("click", ".del", function () {
        var a = $(this).parent();
        a.remove();
    });

    function showimg(a) {
        $(".modalBox").css("display", "block");
        document.getElementById("modalimg").src = a.name;
    }


</script>




<script>
    $(function () {
        $(document).on("change", ".chosestamp", function () {
            if (this.checked) {
                $(this).parents("td").next().children().show();
            } else {
                $(this).parents("td").next().children().hide();
            }
        });
        $(document).on("change", "#tbody tr td:eq(1) span:first input", function () {
            if (this.checked) {
                /*$(this).parents("td").next().children()*/
                $("#tbody tr td:eq(2) select").css("display", "block");
            }
            else {
                $("#tbody tr td:eq(2) select").css("display", "none");
            }
        });
    })
</script>

</body>
</html>
