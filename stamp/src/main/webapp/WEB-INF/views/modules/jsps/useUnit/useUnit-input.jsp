<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>印章管理-印章使用录入</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${ctxHtml}/css/licence.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script src="${ctxHtml}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        .table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
            border: none;
        }
        td{
            text-align: right;
        }
    </style>
</head>
<body>
<div style="width: 50%;">
<h3 style="text-align: center">物理印章使用录入</h3>
<table class="table">
    <tr>
        <td>物理印章名称：</td>
        <td>
            <select name="stamp" class="form-control" id="stamp">
                <c:forEach items="${userAndStamp.stampList}" var="stamp">
                    <option value="${stamp.id}">${stamp.stampName}</option>
                </c:forEach>
            </select>
        </td>
    </tr>
    <tr>
        <td>操作者：</td>
        <td>
            <%--<select name="user" class="form-control" id="p">--%>
                <%--<c:forEach items="${userAndStamp.userList}" var="user">--%>
                    <%--<option value="${user.id}">${user.name}</option>--%>
                <%--</c:forEach>--%>
            <%--</select>--%>
            <c:forEach items="${userAndStamp.userList}" var="user">
                <input type="text" class="form-control" id="username" value="${user.name}" disabled/>
                <input type="hidden" class="form-control" id="companyId" value="${user.company.id}" />
                <input type="hidden" class="form-control" id="userId" value="${user.id}" />
            </c:forEach>
        </td>
    </tr>
    <tr>
        <td>使用者：</td>
        <td>
            <c:forEach items="${userAndStamp.userList}" var="user">
                <input type="text" class="form-control" id="applyUsername" value="${user.name}" />
            </c:forEach>
        </td>
    </tr>
    <tr>
        <td>使用时间：</td>
        <td>
            <input id="useTime" type="text" class="form-control" onfocus="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})"/>
        </td>
    </tr>
    <tr>
        <td>用途：</td>
        <td><input type="text" class="form-control" id="remarks"></td>
    </tr>
</table>
    <button id="submit" class="btn btn-default" style="width: 10%;margin-left: 45%" onclick="submit()">新增</button>
</div>
</body>
</html>
<script>
//表单提交
    function submit() {

        $('#submit').attr("disabled","disabled");

        var stampId = '';
        stampId = $('#stamp').val();

        var userId = '';
        userId = $('#userId').val();

        if ($('#useTime').val() == null || $('#useTime').val() == '') {
            alert("使用日期不能为空!!!")

        } else {
            $.ajax({
                type: "POST",
                url: "${ctx}/useCompanyLogAction/insertOperation",
                data: {"stampId": stampId, "useDate": $('#useTime').val(), "remarks": $('#remarks').val(), "username": $('#username').val(),
                        "applyUsername": $('#applyUsername').val(), "companyId": $('#companyId').val(), "userId": userId},
                dataType: "JSON",
                timeout: 5000,
                success: function (data) {
                    alert(data.message);
                    window.location.href = "${ctx}/useCompanyLogPage/showStampLog?stamp.id="+ $('#stamp').val() + "&stamp.stampShape=1";

                },
                error: function (XMLHttpRequest, textStatus) {
                    if (textStatus == "timeout") {
                        alert("出错了！请联系管理员！");
                    } else {
                        alert("出错了！请联系管理员！");
                    }
                    $("#submit").attr("disabled",false);
                }
            });
        }


    }
</script>