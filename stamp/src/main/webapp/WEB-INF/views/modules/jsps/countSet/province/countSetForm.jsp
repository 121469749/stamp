<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/17
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>润城印章生产数量控制</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        th, td {
            text-align: center;
        }
        label{
            color: red;
            font-size: 14px;
        }
    </style>
    <script>
        $(document).ready(function () {
            <%--保存按钮点击事件--%>
            $("#save").click(function () {
                <%--按钮变为不可点击--%>
                $(this).attr("disabled", "disabled");
                $('#submitForm').ajaxSubmit({
                    dataType: 'json',
                    type: "post",
                    url: "${ctx}/dealer/countSet/province/save",
                    success: function (result) {
                        console.log(result);
                        <%--提示信息，成功后跳转，失败后按钮重新变为可点击--%>
                        alert(result.message);
                        if (result.code == 200) {
                            window.location = "${ctx}/dealer/countSet/province/list";
                        } else {
                            $("#save").removeAttr("disabled");
                        }

                    },
                    error: function () {
                        $("#save").removeAttr("disabled");
                        alert("出错了！请联系管理员！");
                    }
                });
            });

            <%--表单验证--%>
            $("#phy").blur(function () {
                var s = true;
                if($(this).val()==""){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p1'>印章数不能为空</label>");
                }else if($(this).val() < 0){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p2'>不能填负数</label>");
                }else if(parseInt($(this).val()) > 1000000){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class = 'p3'>数值不能大于一百万</label>");
                }else{
                    $(this).next().remove();
                }
                if($("#ele").val() < 0){
                    s = false;
                }else if(parseInt($('#ele').val()) > 1000000){
                    s = false;
                }
                if(s){
                    $("#save").removeAttr("disabled");
                }else{
                    $("#save").attr("disabled", "disabled");
                }
            });
            $("#ele").blur(function () {
                var s = true;
                if($(this).val()==""){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class='e1'>印章数不能为空</label>");
                }else if($(this).val() < 0){
                    s = false;
                    $("label").remove();
                    $(this).after("<label class='e2'>不能填负数</label>");
                }else if(parseInt($(this).val()) > 1000000){
                    $("label").remove();
                    s = false;
                    $(this).after("<label class='e3'>数值不能大于一百万</label>");
                }else{
                    $(this).next().remove();
                }
                if($("#phy").val() < 0){
                    s = false;
                }else if(parseInt($('#phy').val()) > 1000000){
                    s = false;
                }
                if(s){
                    $("#save").removeAttr("disabled");
                }else{
                    $("#save").attr("disabled", "disabled");
                }
            });
        });

    </script>
</head>
<body style="width: 80%;margin: 25px auto">
<form:form id="submitForm" modelAttribute="agencyCompany">
    <table class="table-bordered table" style="width: 55%;margin: 0 auto">
        <tbody id="tbody">
        <tr class="table-tr-title">
            <th colspan="10">
                <b style="color:blue;font-size: 18px">${currentCompany.companyName}(${currentCompany.area.name})</b>
                正在对
                <b style="color: blue;font-size: 18px">${agencyCompany.companyName}(${agencyCompany.area.name})</b>
                进行印章生产控制设定
            </th>
            <!--收费公司-->
            <input type="hidden" name="id" value="${agencyCompany.id}">
            <!--缴费区域-->
            <input type="hidden" name="area.id" value="${agencyCompany.area.id}">
        </tr>
        <tr class="table-tr-title">
            <th colspan="10">${currentCompany.area.name}
                还剩有<c:if test="${currentCompany.phyCountSet != null}">
                    ${currentCompany.phyCountSet.count}
                </c:if>
                <c:if test="${currentCompany.phyCountSet == null}">
                    0
                </c:if>
                个物理印章，
                <c:if test="${currentCompany.eleCountSet != null}">
                    ${currentCompany.eleCountSet.count}
                </c:if>
                <c:if test="${currentCompany.eleCountSet == null}">
                    0
                </c:if>
                个电子印章</h4>
            </th>
        </tr>
        <tr>
            <th>印章类型</th>
            <th>分配数量(个)</th>
        </tr>
        <tr>
            <td>
                物理印章
                <form:hidden value="PHYSTAMP" path="phyCountSet.stampShape"/>
                <form:hidden path="phyCountSet.id" />
            </td>
            <td>
                <form:input path="phyCountSet.count" id="phy" type="number" value="0" onfocus="this.value=''"></form:input>
            </td>
        </tr>
        <tr>
            <td>
                电子印章
                <form:hidden path="eleCountSet.stampShape" value="ELESTAMP"/>
                <form:hidden path="eleCountSet.id" />
            </td>
            <td>
                <form:input path="eleCountSet.count" id="ele" type="number" value="0" onfocus="this.value=''"></form:input>
            </td>
        </tr>


    </table>
    <div style="text-align: center;margin-top: 10px">
        <button id="save" type="button" class="btn btn-primary">保存</button>
        <button type="button" onclick="window.history.go(-1)" class="btn btn-primary">返 回</button>
    </div>
</form:form>
</body>
</html>
