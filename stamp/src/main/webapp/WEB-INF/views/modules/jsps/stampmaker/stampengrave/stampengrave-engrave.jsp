<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>刻制印章</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctxHtml}/css/engraveseal.css?id=1" rel="stylesheet">
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

        input[type="text"] {
            height: 30px;

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
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {
        }
        else {
            alert("请在IE下进行操作！");
        }
        $(function () {
            $("#btn-finish").click(function () {
                $("#btn-finish").attr("disabled", "disabled");
                $("#submitForm").submit();
            });
            $("#submitForm").ajaxForm({
                dataType: 'json',
                type: "post",
                url: "${ctx}/stampMakeAction/stampMoulage",
                success: function (result) {
                    alert(result.message);
                    if (result.code == 200) {
                        $(".nav-list", window.parent.document).children("li").removeClass("active");
                        $(".nav-list", window.parent.document).children("li:contains('已制作印章')").addClass("active");
                        window.location = "${ctx}/stampMakePage/toFinishList";
                    } else {
                        //alert(result.message);
                        $("#btn-finish").removeAttr("disabled");
                    }
                },
                error: function () {
                    alert("出错了！请联系管理员！");
                    $("#btn-finish").removeAttr("disabled");
                }
            });
            //异步获取印模信息
            $("#moulagelist").change(function () {
                if ($(this).val() == 0) {
                    return false;
                }
                $.ajax({
                    dataType: 'json',
                    type: "post",
                    url: "${ctx}/stampMakeInfo/getMoulageInfo?id=" + $(this).val(),
                    success: function (result) {
//                        console.log(result);
//                        印模设置
                        var obj = document.getElementById("SealDraw");
                        //设置数值进印模
                        obj.Adj_SealTxtOffset = result.ringUp;
                        //设置数值进前台
                        $("#value1").val(result.ringUp);
                        $("#value12").val(result.ringUp);
                        obj.Adj_SealTxtSize = result.ringSize;
                        $("#value2").val(result.ringSize);
                        obj.Adj_SealTxtScale = result.ringWidth;
                        $("#value3").val(result.ringWidth);
                        obj.Adj_SealTxtAngle = result.ringAngle;
                        $("#value4").val(result.ringAngle);
                        obj.Adj_SubTxtOffset = result.acrossOffset;
                        $("#value5").val(result.acrossOffset);
                        obj.Adj_SubTxtSize = result.acrossSize;
                        $("#value6").val(result.acrossSize);
                        obj.Adj_SubTxtScale = result.acrossWidth;
                        $("#value7").val(result.acrossWidth);
                        obj.Adj_CodeTxtSize = result.codeSize;
                        $("#value8").val(result.codeSize);
                        obj.Adj_CodeTxtScale = result.codeWidth;
                        $("#value9").val(result.codeWidth);
                        obj.Adj_CodeTxtOffset = result.codeUp;
                        $("#value10").val(result.codeUp);
                        obj.Adj_CodeTxtAngle = result.codeAngle;
                        $("#value11").val(result.codeAngle);

                    },
                    error: function () {
                        alert("出错了！请联系管理员！");
                    }
                })
            });
        });

        <%--设置印模类型,并且显示对应的微调列表--%>
        $(document).ready(function () {
            var type = $("#stampType").val();
            var obj = document.getElementById("SealDraw");
//            alert("取出印章类型" + type);
            switch (type) {
                case "单位专用印章":
                    obj.SealType = 1;
                    $("ul li:gt(0)").show();
                    $("#nofading").show();
                    $("#fading").hide();
                    $("#angle").show();
                    break;
                case "财务专用章":
                    obj.SealType = 2;
                    $("ul li:gt(0)").show();
                    $("#nofading").show();
                    $("#fading").hide();
                    $("#angle").show();
                    break;
                case "合同专用章":
                    obj.SealType = 4;
                    $("ul li:gt(0)").show();
                    $("#nofading").show();
                    $("#fading").hide();
                    $("#angle").show();
                    break;
                case "法定代表人名章":
                    obj.SealType = 6;
                    $("ul li:gt(0)").hide();
                    $("#nofading").hide();
                    $("#fading").show();
                    break;
                case "发票专用章":
                    obj.SealType = 3;
                    $("ul li:gt(0)").show();
                    $("#nofading").show();
                    $("#fading").hide();
                    $("#angle").hide();
//                    obj.Adj_SealTxtScale = obj.Adj_SealTxtScale + 0.3;
//                    obj.Adj_SealTxtOffset = obj.Adj_SealTxtOffset - 0.8;
//                    obj.Adj_CodeTxtScale = obj.Adj_CodeTxtScale + 0.4;
//                    obj.Adj_CodeTxtOffset = obj.Adj_CodeTxtOffset - 0.3;
                    break;
                case "业务专用章":
                    obj.SealType = 5;
                    $("ul li:gt(0)").show();
                    $("#nofading").show();
                    $("#fading").hide();
                    $("#angle").show();
                    break;
            }
        })
    </script>

<body>
<form:form class="border-box" id="submitForm" modelAttribute="stamp" action="${ctx}/stampMakeAction/stampMoulage"
           method="post" enctype="multipart/form-data">
    <form:hidden path="id"/>
    <form:hidden path="stampShape"/>
    <!--表单上方的印章单位基本信息-->
    <div>
        <h3>物理印章刻制</h3>
        <div class="form-group each-input" disabled="true">
            <label style="width: 10em;text-align: left">印章单位：</label>
            <form:input path="useComp.companyName" id="comname" readonly="true" style="height:30px;width:420px;"
                        unselectable="on"></form:input>
            <button type="button" class="btn btn-primary "
                    onclick="copyComname();">点击复制
            </button>
        </div>
            <%--<div class="form-group each-input">--%>
            <%--<label>印章名称：</label>--%>
        <form:input type="hidden" path="stampName" id="sname" readonly="true"
                    style="height:30px;width:420px;"></form:input>
            <%--</div>--%>
        <div class="form-group each-input" disabled="true">
            <label style="width: 10em;text-align: left">印章防伪码：</label>
            <input type="text" id="scode" readonly="true" unselectable="on" value="${stamp.stampCode}"
                   style="height:30px;width:420px;"/>
            <button type="button" class="btn btn-primary "
                    onclick="copyScode();">点击复制
            </button>
        </div>
        <div class="form-group each-input" disabled="true">
            <label style="width: 10em;text-align: left">统一社会信用代码：</label>
            <input type="text" id="soleCode" readonly="true" unselectable="on" value="${stamp.useComp.soleCode}"
                   style="height:30px;width:420px;"/>
            <button type="button" class="btn btn-primary "
                    onclick="copySoleCode();">点击复制
            </button>
        </div>
        <c:if test="${stamp.stampType != '01'}">
            <div class="form-group each-input" disabled="true">
                <label style="width: 10em;text-align: left">印章重复数：</label>
                <input type="text" readonly="true" unselectable="on" value="正在制作第(${makeCount})个此印章"
                       style="height:30px;width:420px;"/>
            </div>
        </c:if>

    </div>
    <p class="essentialinformation clo-md-12">
    <div class="btn-group" data-toggle="buttons">
        <label class="btn btn-success active" id="option1">
            <input type="radio" name="options" > 本系统制作(推荐使用)
        </label>
        <label class="btn btn-success" id="option2">
            <input type="radio" name="options" > 第三方排版软件制作
        </label>
    </div>
    </p>
    <!--印章刻制部分-->
    <div class="engrave" id="engrave">
        <!--左边选项部分-->
        <div class="col-md-6 col-xs-6 col-sm-6 engrave-left">
            <!-- 四个设置列表按钮 -->
            <ul class="nav nav-tabs" role="tablist" id="list">
                <li role="presentation" class="active"><a href="#exterior" aria-controls="exterior" role="tab"
                                                          data-toggle="tab">印章外观</a></li>
                <li role="presentation"><a href="#outtext" aria-controls="outtext" role="tab"
                                           data-toggle="tab">环形文字设置</a></li>
                <li role="presentation"><a href="#intext" aria-controls="intext" role="tab" data-toggle="tab">横排文字设置</a>
                </li>
                <li role="presentation"><a href="#Security" aria-controls="Security" role="tab"
                                           data-toggle="tab">防伪码设置</a>
                </li>
            </ul>
            <!-- 列表对应的div块 -->
            <div class="tab-content clearfix">
                    <%-- 印章外观div块 --%>
                <div role="tabpanel" class="tab-pane active clearfix" id="exterior">
                    <div class="form-group engrave-left-tab">
                        <label class="col-md-4">印模选择：</label>
                            <%--<select id="moulagelist">--%>
                            <%--<option value="0">-选择印模-</option>--%>
                            <%--<c:forEach items="${list}" var="lists">--%>
                            <%--<option value="${lists.id}">${lists.moulageName}</option>--%>
                            <%--</c:forEach>--%>
                            <%--</select>--%>
                        <c:choose>
                            <c:when test="${stamp.stampType == 1}">
                                <select onchange="sealTypeChange(this)" id="stampSize">
                                    <option value="1">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-38</option>
                                    <option value="11">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-40</option>
                                    <option value="12">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-42</option>
                                </select>
                            </c:when>
                            <%--<c:when test="${stamp.stampType == 2}">--%>
                            <%--<select onchange="sealTypeChange(this)" id="stampSize">--%>
                            <%--<option value="2">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-圆形</option>--%>
                            <%--<option value="21">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-椭圆</option>--%>
                            <%--</select>--%>
                            <%--</c:when>--%>
                            <c:when test="${stamp.stampType == 6}">
                                <select onchange="sealTypeChange(this)" id="stampSize">
                                    <option value="6">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-16</option>
                                    <option value="61">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-18</option>
                                    <option value="62">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}-20</option>
                                </select>
                            </c:when>
                            <c:otherwise>
                                <select onchange="sealTypeChange(this)" id="stampSize">
                                    <option value="${stamp.stampType}">${fns:getDictLabel(stamp.stampType,"stampType" ,null )}</option>
                                </select>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div class="form-group engrave-left-tab">
                        <label class="col-md-4">印章类型：</label>
                        <input id="stampType" onfocus="this.blur()" type="text"
                               value="${fns:getDictLabel(stamp.stampType,"stampType" ,null )}"
                               style="cursor: not-allowed;">
                    </div>
                    <div id="nofading">
                        <div class="form-group engrave-left-tab">
                            <label class="col-md-4">印章文本：</label>
                            <form:input id="SealText" type="text" oninput="myFunction()" path="moulage.stampText"
                                        cssStyle="width: 50%;"/>
                        </div>
                        <div class="form-group engrave-left-tab">
                            <label class="col-md-4">横排文字：</label>
                            <form:input id="SubText" path="moulage.acrossText" type="text" oninput="myFunction()"
                                        placeholder="横排文字"></form:input>
                        </div>
                        <div class="form-group engrave-left-tab">
                            <label class="col-md-4">显示下标文字：</label>
                            <label><input name="radio" type="radio" onclick="myFunction()">&nbsp;是</label>&nbsp;
                            <label><input name="radio" type="radio" id="labeltext" onclick="myFunction()"
                                          checked>&nbsp;否</label>
                            <form:input id="LabelText" type="text" cssStyle="display: none" path="moulage.subText"
                                        oninput="myFunction()" placeholder="下标文字"/>
                        </div>
                        <div class="form-group engrave-left-tab">
                            <label class="col-md-4">防伪码：</label>
                            <form:input id="AntiCode" type="text" path="stampCode" oninput="myFunction()"
                                        onfocus="this.blur()"
                                        placeholder="防伪码" readonly="true" cssStyle="cursor: not-allowed;"></form:input>
                        </div>
                    </div>
                        <%--法人章对应的div块 --%>
                    <div id="fading" style="display: none">
                        <div class="form-group engrave-left-tab">
                            <label class="col-md-5">法定代表人姓名：</label>
                            <input id="SealText1" type="text" name="${useComp.legalName}" oninput="myFunction()"
                                   placeholder="请在此输入姓名">
                        </div>
                        <div class="form-group engrave-left-tab clearfix">
                            <label class="col-md-5" style="float: left">文本大小：</label>
                            <div class="wrapBox">
                                <div class="spinner">
                                    <button type="button" class="btn btn-primary" onclick="SealTxtSizePlus()">-</button>
                                    <input id="value12" type="text" class="form-control" value="0"
                                           style="width:70px;display: inline"/>
                                    <button type="button" class="btn btn-primary" onclick="SealTxtSizeMinus()">+
                                    </button>
                                    <div class="cb"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="clearfix">
                        <button type="button" class="btn btn-primary col-md-3  bottom-btn" style="margin-left:10%;"
                                onclick="funcRefreshAnti()">刷新防伪线
                        </button>
                        <button type="button" class="btn btn-primary col-md-3  bottom-btn" style="margin-left: 30%"
                                onclick="restar()">重置
                        </button>
                    </div>
                    <div class="clearfix">
                        <p style="margin-left: 30%">如果插件未刷新，请点击重置</p>
                    </div>
                </div>
                    <%-- 环形文字设置div块 --%>
                <div role="tabpanel" class="tab-pane clearfix" id="outtext">
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">环形文本上下偏移：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SealTxtOffsetUp()">-</button>
                                <form:input id="value1" type="text" path="moulage.ringUp" class="form-control" value="0"
                                            style="width:70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SealTxtOffsetDown()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">印章字号大小：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SealTxtSizePlus()">-</button>
                                <form:input type="text" class="form-control" path="moulage.ringSize" id="value2"
                                            value="0"
                                            style="width:70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SealTxtSizeMinus()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">印章文本宽度：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SealTxtScalePlus()">-</button>
                                <form:input type="text" class="form-control" path="moulage.ringWidth" id="value3"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SealTxtScaleMinus()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">印章文本角度：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SealTxtAnglePlus()">-</button>
                                <form:input type="text" class="form-control" path="moulage.ringAngle" id="value4"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SealTxtAngleMinus()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                </div>
                    <%--横排文本设置 --%>
                <div role="tabpanel" class="tab-pane" id="intext">
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">横排文本偏移：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SubTxtOffsetDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.acrossOffset" id="value5"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SubTxtOffsetUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">横排文本大小：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SubTxtSizeDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.acrossSize" id="value6"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SubTxtSizeUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">横排文本宽度：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="SubTxtScaleDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.acrossWidth" id="value7"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="SubTxtScaleUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                </div>
                    <%--防伪码div块 --%>
                <div role="tabpanel" class="tab-pane" id="Security">
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">字体大小：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="CodeTxtSizeDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.codeSize" id="value8"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="CodeTxtSizeUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">字体宽度：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="CodeTxtScaleDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.codeWidth" id="value9"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="CodeTxtScaleUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix">
                        <label class="col-md-5" style="float: left">上下偏移：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="CodeTxtOffsetDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.codeUp" id="value10"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="CodeTxtOffsetUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group engrave-left-tab clearfix" id="angle">
                        <label class="col-md-5" style="float: left">角度：</label>
                        <div class="wrapBox">
                            <div class="spinner">
                                <button type="button" class="btn btn-primary" onclick="CodeTxtAngleDown()">-</button>
                                <form:input type="text" class="form-control" path="moulage.codeAngle" id="value11"
                                            value="0"
                                            style="width: 70px;display: inline"/>
                                <button type="button" class="btn btn-primary" onclick="CodeTxtAngleUp()">+</button>
                                <div class="cb"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
            <%--右边显示印章外观div块 --%>
        <div class="col-md-6 col-xs-6 col-sm-6 engrave-right">
            <p>印章外观预览</p>
                <%--控件调用标签 --%>
            <%--<OBJECT CLASSID="CLSID:D2A20FFC-5207-434A-961E-79FCEA493433"--%>
                    <%----%>
                    <%--ID="SealDraw" style="width: 100%;height: 89%;">--%>
            <OBJECT CLASSID="CLSID:600F0A9A-DB86-4071-9093-F8E6DC07477B"
                    ID="SealDraw" style="width: 100%;height: 89%;">
            </OBJECT>
        </div>
    </div>
    <div class="foot">
            <%--导出印模--%>
        <div class="row" id="exportPath">
            <label style="margin-left:5%;margin-top: 15px">选择导出的路径：</label>
            <input style="margin-top: 15px" id="path" type="text" size="25" value="c:\" placeholder="非IE下请手动输入">
            <input style="margin-top: 15px" type=button value="选择" onclick="browseFolder('path')">
            <label style="margin-left: 2%;">图片格式：</label>
            <select id="imageFormat">
                <option value="png" selected>png</option>
                <option value="jpeg">jpeg</option>
                <option value="gif">gif</option>
                <option value="bmp">bmp</option>
                    <%--<option value="wmf">wmf</option>--%>
                <option value="tiff">tiff</option>
                    <%--<option value="x-emf">x-emf</option>--%>
                    <%--<option value="x-icon">x-icon</option>--%>
            </select>
            <button type="button" class="btn btn-default" style="margin-left: 2%;margin-top: 0"
                    onclick="funcExportblack()">
                导出印模
            </button>
        </div>

            <%--印模上传--%>
        <table class="table table-striped table-bordered" style="margin-top:20px;margin-bottom:20px; ">
            <tr>
                <td rowspan="3" style="width: 10%;line-height:45px;text-align: center"><strong>印模上传</strong><br><font
                        id="font" style="font-size:15px;color: red;"></font></td>
            </tr>
            <tr>
                <td style="vertical-align:middle"><strong>物理印模</strong><font id="font1" style="font-size:15px;color: red;">(请将黑白印模上传)</font>
                </td>
                <td width="350px">
                    <div class="box">
                        <div class="clearfix" style="position:relative;">
                            <input type="text" value="${dict.value}" name="fileType" style="display: none">
                            <input type="button" class="btn" value="选择文件">
                            <input type="text" readonly>
                            <input class='upload' name="moulageFile" type='file'
                                   accept="image/jpeg,image/png,image/bmp,.doc,.docx,.tiff,.gif">
                            <input style="display: none" class="btn btn-primary showimg" data-toggle="modal"
                                   onclick="showimg(this)" data-target=".bs-example-modal-lg" type="button" value="查看">
                        </div>
                    </div>
                </td>
            </tr>
            <%--<tr>
                <td><strong>电子印模</strong><font id="font2" style="font-size:15px;color: red;"></font></td>
                <td>
                    <div class="box">
                        <div class="clearfix" style="position:relative;">
                            <input type="text" value="${dict.value}" name="fileType" style="display: none">
                            <input type="button" class="btn" value="选择文件">
                            <input type="text" readonly>
                            <input class='upload' name="moulageFile" type='file'
                                   accept="image/jpeg,image/png,image/bmp,.doc,.docx,.tiff,.gif">
                            <input style="display: none" class="btn btn-primary showimg" data-toggle="modal"
                                   onclick="showimg(this)" data-target=".bs-example-modal-lg" type="button" value="查看">
                        </div>
                    </div>
                </td>
            </tr>--%>
        </table>
        <button id="btn-finish" type="button" class="btn btn-primary col-md-4 bottom-btn"
                style="margin-left: 33%;text-shadow: black 5px 3px 3px;">
            <span class="glyphicon glyphicon-upload"></span>  提交备案
        </button>
    </div>
</form:form>
<!--显示上传印模模态框-->
<div class="modal fade bs-example-modal-lg" onclick="showstamp()" id="modal" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header" style="border: 0;padding-right: 10%">
                <button type="button" onclick="showstamp()" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true"><img src="${ctxHtml}/images/close.png"></span></button>
            </div>
            <img style="width: 100%" id="modalimg">
        </div>
    </div>
</div>

<script type="text/javascript">

    <%--监听制章方式tab--%>
    $("#option1").click(function () {
        $("#engrave").css('display','block')
        $("#exportPath").css('display','block')
        $("#font1").html("(请将黑白印模上传)")
    })
    $("#option2").click(function () {
        $("#engrave").css('display','none')
        $("#exportPath").css('display','none')
        $("#font1").html("<br>" + "(印模中必须存在防伪线，章外围不可留有黑边，各种尺寸的印章图象数据分辨率为400dpi。)"
            /*"<br>" +
            "各种尺寸的章分辨率要求：<br>" +
            "<br>" +
            "其他尺寸的章的分辨率按上述比例调整"*/
        )
    })

    <%--选择导出路径的方法，ie下--%>
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

    <%--显示图片的方法--%>
    function showimg(a) {
        var isIE = navigator.userAgent.match(/MSIE/) != null,
            isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
//        ie条件下显示
        if (isIE) {
            $("#SealDraw").css("display", "none");
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + a.name + "\")";
            document.getElementById("modalimg").style.width = "90%";
            document.getElementById("modalimg").src = a.name;
        }
//        非IE条件下显示
        else {
            $("#SealDraw").css("display", "none");
            $(".modalBox").css("display", "block");
            document.getElementById("modalimg").src = a.name;
        }
    }

    <%-- 显示印模调用标签--%>

    function showstamp() {
        document.getElementById("SealDraw").style.display = "block";
    }

    $(function () {
        <%--选择文件方法，用于显示路径以及设置点查看按钮后，图片的路径 --%>
        $(document).on("change", "input[type='file']", function (e) {
            var files = $(e.target);
            var text = $(e.target).prev();
            var showimg = $(e.target).next();
            text.val(files.val());
            <%--如果没有选择文件，则不显示查看按钮 --%>
            if (files.val() == "") {
                showimg.hide();
            } else {
                showimg.show();
            }
            var temp = $(files.parents("td").find("input[type='file']"));
            for (var i = 0; i < temp.length - 1; i++) {
                if (files.val() == temp.eq(i).val()) {
                    alert("上传图片重复！");
                    files.val("");
                    showimg.css("display", "none");
                }
            }
            var fil = this.files;
            var file = $(this);
            /*var pic = $("#modalimg");*/
            /*var pic = file.next("img");*/
            var isIE = navigator.userAgent.match(/MSIE/) != null,
                isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
            <%--如果是ie条件下，设置文件的路径 --%>
            if (isIE) {
                file.select();
                var reallocalpath = document.selection.createRange().text;
                showimg.attr('name', reallocalpath);
            } else {
                <%--非ie条件下设置为文件流 --%>
                var reader = new FileReader();
                reader.readAsDataURL(fil[0]);
                reader.onload = function () {
                    showimg.attr('name', reader.result);
                }
            }
        });
        <%--隐藏模态框 --%>
        $(document).on("click", ".modalBox", function () {
            $(this).css("display", "none");
        });

        //图片上传添加按钮
        $(".addhtml").click(function () {
            var hasfile = $(this).prev("div").children("input[type='file']").val();
            var textval = $(this).prev("div").children("input[type='text']").val();
            if (hasfile != "") {
                $(this).before("<div class='clearfix' style='position:relative;'><input type='text' name='fileType' value='" + textval + "' style='display: none'><input type='button' class='btn' value='选择文件'><input type='text' style='margin-left: 4px;display: none' readonly> <input class='upload' name='file' type='file' accept='image/jpeg,image/png,image/bmp,.doc,.docx'> <input style='display: none' class='btn btn-primary showimg' data-toggle='modal' data-target='.bs-example-modal-lg' type='button' value='查看'><input type='button' value='×' class='btn del'></div>")
            } else {
                return;
            }
        });
        $(document).on("click", ".del", function () {
            var a = $(this).parent();
            a.remove();
        });
    });
</script>
<script>
    function blockDisplay() {
        if(document.getElementById("engrave").style.display == "none"){
            document.getElementById("engrave").style.display = "";
            document.getElementById("row").style.display = "";
        }
        document.getElementById("engrave").style.display = "none";
        document.getElementById("row").style.display = "none";

    }
    <%--重置印模信息 --%>

    function restar() {
        var a = new Array();
        a = document.getElementById("sname").value.split("-");
        var obj = document.getElementById("SealDraw");
        obj.SealText = document.getElementById("comname").value;
        obj.SubText = a[0];
        obj.LabelText = a[1];
        obj.AntiFakeCode = document.getElementById("scode").value;
        //重置微调属性
        obj.Adj_SealTxtOffset = 0;
        $("#value1").val("0");
        $("#value12").val("0");
        obj.Adj_SealTxtSize = 0;
        $("#value2").val("0");
        obj.Adj_SealTxtScale = 0;
        $("#value3").val("0");
        obj.Adj_SealTxtAngle = 0;
        $("#value4").val("0");
        obj.Adj_SubTxtOffset = 0;
        $("#value5").val("0");
        obj.Adj_SubTxtSize = 0;
        $("#value6").val("0");
        obj.Adj_SubTxtScale = 0;
        $("#value7").val("0");
        obj.Adj_CodeTxtSize = 0;
        $("#value8").val("0");
        obj.Adj_CodeTxtScale = 0;
        $("#value9").val("0");
        obj.Adj_CodeTxtOffset = 0;
        $("#value10").val("0");
        obj.Adj_CodeTxtAngle = 0;
        $("#value11").val("0");
        document.getElementById("SubText").value = a[0];
        document.getElementById("LabelText").value = a[1];
        document.getElementById("SealText").value = obj.SealText;
        document.getElementById("AntiCode").value = obj.AntiFakeCode;
        if (document.getElementById("stampType").value == "法定代表人名章") {
            obj.SealText = document.getElementById("SealText1").value;
        }
    }

    <%--填入文本时的方法，改变横排文字，下标文字等等 --%>

    function myFunction() {
        var obj = document.getElementById("SealDraw");

        var SealText = document.getElementById("SealText").value;
        var SubText = document.getElementById("SubText").value;
        if ($("#labeltext").is(':checked')) {
            var LabelText = "";
        } else {
            var LabelText = a[1];
        }
        var AntiFakeCode = document.getElementById("AntiCode").value;

        obj.SealText = SealText;
        obj.SubText = SubText;
        obj.LabelText = LabelText;
        obj.AntiFakeCode = AntiFakeCode;
        if (document.getElementById("stampType").value == "法定代表人名章") {
            obj.SealText = document.getElementById("SealText1").value;
        }
    }



    <%--刷新防伪线 --%>

    function funcRefreshAnti() {
        var obj = document.getElementById("SealDraw");
        obj.RefreshAntiFakeLine();
    }

    function funcAntiText() {
        var obj = document.getElementById("SealDraw");
        var chk = document.getElementById("IsAntiText");
        obj.IsAntiFakeText = chk.checked;
    }

    function funcExport() {
        var obj = document.getElementById("SealDraw");
        var path = document.getElementById("SealExport1").value;
        obj.ExportSealEx(path, false);
    }

    <%--导出印模 --%>

    function funcExportblack() {
        var obj = document.getElementById("SealDraw");
        <%--导出电子路径 --%>
//        var path = document.getElementById("path").value + document.getElementById("comname").value + "-" + document.getElementById("stampType").value + "2." + document.getElementById("imageFormat").value;
        <%--导出电子印模 --%>
//        obj.ExportSealEx(path, "image/" + document.getElementById("imageFormat").value, false, false);
        <%--导出物理路径 --%>
        var path = document.getElementById("path").value + document.getElementById("comname").value + "-" + document.getElementById("stampType").value + "1." + document.getElementById("imageFormat").value;
        <%--导出物理印模 --%>
        obj.ExportSealEx(path, "image/" + document.getElementById("imageFormat").value, true, true);
    }

    //环形文本上下偏移
    function SealTxtOffsetUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtOffset = obj.Adj_SealTxtOffset - 0.1;
        document.getElementById("value1").value = obj.Adj_SealTxtOffset.toFixed(2);
    }

    function SealTxtOffsetDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtOffset = obj.Adj_SealTxtOffset + 0.1;
        document.getElementById("value1").value = obj.Adj_SealTxtOffset.toFixed(2);
    }

    //印章字号大小
    function SealTxtSizePlus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtSize = obj.Adj_SealTxtSize - 1;
        document.getElementById("value2").value = obj.Adj_SealTxtSize.toFixed(2);
        if (document.getElementById("stampType").value == "法定代表人名章") {
            document.getElementById("value12").value = obj.Adj_SealTxtSize.toFixed(2);
        }
    }

    function SealTxtSizeMinus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtSize = obj.Adj_SealTxtSize + 1;
        document.getElementById("value2").value = obj.Adj_SealTxtSize.toFixed(2);
        if (document.getElementById("stampType").value == "法定代表人名章") {
            document.getElementById("value12").value = obj.Adj_SealTxtSize.toFixed(2);
        }
    }

    //    印章文本宽度
    function SealTxtScalePlus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtScale = obj.Adj_SealTxtScale - 0.1;
        document.getElementById("value3").value = obj.Adj_SealTxtScale.toFixed(2);
    }

    function SealTxtScaleMinus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtScale = obj.Adj_SealTxtScale + 0.1;
        document.getElementById("value3").value = obj.Adj_SealTxtScale.toFixed(2);
    }

    //    印章文本角度
    function SealTxtAnglePlus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtAngle = obj.Adj_SealTxtAngle - 3;
        document.getElementById("value4").value = obj.Adj_SealTxtAngle.toFixed(2);
    }

    function SealTxtAngleMinus() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SealTxtAngle = obj.Adj_SealTxtAngle + 3;
        document.getElementById("value4").value = obj.Adj_SealTxtAngle.toFixed(2);
    }

    //设置横排文字的上下偏移
    function SubTxtOffsetUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtOffset = obj.Adj_SubTxtOffset + 0.1;
        document.getElementById("value5").value = obj.Adj_SubTxtOffset.toFixed(2);
    }

    function SubTxtOffsetDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtOffset = obj.Adj_SubTxtOffset - 0.1;
        document.getElementById("value5").value = obj.Adj_SubTxtOffset.toFixed(2);
    }

    //设置横排文字的字号大小
    function SubTxtSizeUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtSize = obj.Adj_SealTxtSize + 0.5;
        document.getElementById("value6").value = obj.Adj_SubTxtSize.toFixed(2);
    }

    function SubTxtSizeDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtSize = obj.Adj_SubTxtSize - 0.5;
        document.getElementById("value6").value = obj.Adj_SubTxtSize.toFixed(2);
    }

    //设置横排文字的字体宽度
    function SubTxtScaleUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtScale = obj.Adj_SubTxtScale + 0.1;
        document.getElementById("value7").value = obj.Adj_SubTxtScale.toFixed(2);
    }

    function SubTxtScaleDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_SubTxtScale = obj.Adj_SubTxtScale - 0.1;
        document.getElementById("value7").value = obj.Adj_SubTxtScale.toFixed(2);
    }

    //设置防伪编码的字号大小
    function CodeTxtSizeUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtSize = obj.Adj_CodeTxtSize + 0.2;
        document.getElementById("value8").value = obj.Adj_CodeTxtSize.toFixed(2);
    }

    function CodeTxtSizeDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtSize = obj.Adj_CodeTxtSize - 0.1;
        document.getElementById("value8").value = obj.Adj_CodeTxtSize.toFixed(2);
    }

    //设置防伪编码的字体宽度
    function CodeTxtScaleUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtScale = obj.Adj_CodeTxtScale + 0.2;
        document.getElementById("value9").value = obj.Adj_CodeTxtScale.toFixed(2);
    }

    function CodeTxtScaleDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtScale = obj.Adj_CodeTxtScale - 0.2;
        document.getElementById("value9").value = obj.Adj_CodeTxtScale.toFixed(2);
    }

    //设置防伪编码的上下偏移
    function CodeTxtOffsetUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtOffset = obj.Adj_CodeTxtOffset + 0.1;
        document.getElementById("value10").value = obj.Adj_CodeTxtOffset.toFixed(2);
    }

    function CodeTxtOffsetDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtOffset = obj.Adj_CodeTxtOffset - 0.1;
        document.getElementById("value10").value = obj.Adj_CodeTxtOffset.toFixed(2);
    }

    //设置防为编码文字的角度
    function CodeTxtAngleUp() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtAngle = obj.Adj_CodeTxtAngle + 3;
        document.getElementById("value11").value = obj.Adj_CodeTxtAngle.toFixed(2);
    }

    function CodeTxtAngleDown() {
        var obj = document.getElementById("SealDraw");
        obj.Adj_CodeTxtAngle = obj.Adj_CodeTxtAngle - 3;
        document.getElementById("value11").value = obj.Adj_CodeTxtAngle.toFixed(2);
    }

    function sealType() {
        var select = document.getElementById("SealType");
        var index = select.selectedIndex;
        var n = select.options[index].value;
        var obj = document.getElementById("SealDraw");
        obj.SealType = n;
    }

    function sealTypeChange(el) {
        var n = $(el).val();
        var obj = document.getElementById("SealDraw");
        obj.SealType = n;
    }

    <%--初始化设置 --%>
    var a = new Array();
    a = document.getElementById("sname").value.split("-");
    document.getElementById("SubText").value = a[0];
    document.getElementById("LabelText").value = a[1];
    document.getElementById("SealText").value = document.getElementById("comname").value;
    document.getElementById("AntiCode").value = document.getElementById("scode").value;

    document.getElementById("SealExport").value = "c:\\seal.jpeg";

</script>
<script>
    $(function () {
        var $document = $(document);
        var selector = '[data-rangeslider]';
        var $inputRange = $(selector);

        // Example functionality to demonstrate a value feedback
        // and change the output's value.
        function valueOutput(element) {
            var value = element.value;
            var output = element.parentNode.getElementsByTagName('output')[0];
            output.innerHTML = value;
        }

        // Initial value output
        for (var i = $inputRange.length - 1; i >= 0; i--) {
            valueOutput($inputRange[i]);
        }

        // Update value output
        $document.on('input', selector, function (e) {
            valueOutput(e.target);

        });

        // Initialize the elements
        $inputRange.rangeslider({
            polyfill: false
        });

        // Example functionality to demonstrate programmatic value changes
        $document.on('click', '#js-example-change-value button', function (e) {
            var $inputRange = $('input[type="range"]', e.target.parentNode);
            var value = $('input[type="number"]', e.target.parentNode)[0].value;

            $inputRange
                .val(value)
                .change();
        });

        // Example functionality to demonstrate programmatic attribute changes
        $document.on('click', '#js-example-change-attributes button', function (e) {
            var $inputRange = $('input[type="range"]', e.target.parentNode);
            var attributes = {
                min: $('input[name="min"]', e.target.parentNode)[0].value,
                max: $('input[name="max"]', e.target.parentNode)[0].value,
                step: $('input[name="step"]', e.target.parentNode)[0].value
            };

            $inputRange
                .attr(attributes)
                .rangeslider('update', true);
        });

        // Example functionality to demonstrate destroy functionality
        $document
            .on('click', '#js-example-destroy button[data-behaviour="destroy"]', function (e) {
                $('input[type="range"]', e.target.parentNode).rangeslider('destroy');
            })
            .on('click', '#js-example-destroy button[data-behaviour="initialize"]', function (e) {
                $('input[type="range"]', e.target.parentNode).rangeslider({polyfill: false});
            });
    });
    function copyComname(){
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {//复制功能：兼容浏览器判断

            window.clipboardData.clearData();
            window.clipboardData.setData("text", $("#comname").val());
            alert("复制成功！");

        }else{//非IE浏览器
                //复制印章单位名称

                var copyText = document.getElementById("comname");
                copyText.select();//选中文本
                document.execCommand("copy");//执行浏览器复制命令
                alert("复制成功！");
            }
    }


    function copyScode(){
        if ((navigator.userAgent.indexOf('MSIE') >= 0)
            && (navigator.userAgent.indexOf('Opera') < 0)) {//复制功能：兼容浏览器判断

            window.clipboardData.clearData();
            window.clipboardData.setData("text", $("#scode").val());
            alert("复制成功！");

        }else{//非IE浏览器
            //复制印章防伪码

            var copyText = document.getElementById("scode");
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