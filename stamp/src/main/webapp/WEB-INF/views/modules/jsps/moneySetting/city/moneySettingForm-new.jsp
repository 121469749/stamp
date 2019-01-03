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
    <title>收费设定</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css">
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script src="${ctxHtml}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
    <style>
        th, td {
            text-align: center;
        }
    </style>
    <script>
        <%--填写金额后的失焦事件--%>
        function b(t) {
            var v = false;
            <%--正则验证，精确到小数点后两位--%>
            var rel = /^\d+(\.\d{0,2})?$/;
            var inpval;
            if (t.val() == "" || t.val() == null) {
                t.next().remove();
                t.after("<label style='color: red'>请填写缴费金额</label>");
            } else if (!rel.test(t.val())) {
                t.next().remove();
                t.after("<label style='color: red'>请精确到小数点后两位</label>");
            } else {
                t.next().remove();
            }
            for (var j = 3; j < $("#tbody tr").length; j++) {
                inpval = $("#tbody tr").eq(j).find("input").eq(2);
                if (!rel.test(inpval.val())) {
                    v = false;
                    break;
                } else {
                    v = true;
                }
            }
            for (j = 1; j < $("#tbody2 tr").length; j++) {
                if (!v)
                    break;
                inpval = $("#tbody2 tr").eq(j).find("input").eq(1);
                if (!rel.test(inpval.val())) {
                    v = false;
                    break;
                } else {
                    v = true;
                }
            }

            if (v) {
                $("#save").attr("disabled", false);
            } else {
                $("#save").attr("disabled", true);
            }
        }
    </script>
    <script>
        $(document).ready(function(){
            <%--点击保存按钮事件--%>
            $("#save").click(function () {
                $(this).attr("disabled","disabled");
                $("#submitForm").submit();
            });
            <%--表单提交事件--%>
            $('#submitForm').ajaxForm({
                dataType: 'json',
                type:"post",
                url: "${ctx}/moneySetting/runcheng/setting/save",
                success: function (result) {
                    alert(result.message);
                    if(result.code==200){
                        <%--成功后跳转到列表页面--%>
                        window.location ="${ctx}/dealer/moneySetting/city/setting/list";
                    }else{
                        $("#save").removeAttr("disabled");
                    }


                },
                error: function () {
                    $("#save").removeAttr("disabled");
                    alert("出错了！请联系管理员！");
                }
            });
        });

    </script>
</head>
<body style="width: 80%;margin: 25px auto">
<form id="submitForm" method="post" action="${ctx}/moneySetting/runcheng/setting/save">
    <%--电子印章--%>
    <table class="table-bordered table" style="width: 55%;margin: 0 auto">
        <tbody id="tbody">
        <tr class="table-tr-title">
            <th colspan="10"><b
                    style="color:blue;font-size: 18px">${currentCompany.companyName}(${currentCompany.area.name})</b>
                正在对
                <b style="color: blue;font-size: 18px">刻章点区域(${makeCompanyArea.name})</b>
                进行收费设置修改
            </th>
            <!--收费公司-->
            <input type="hidden" name="company.id" value="${currentCompany.id}">
            <!--缴费区域-->
            <input type="hidden" name="area.id" value="${makeCompanyArea.id}">
        </tr>
        <tr class="table-tr-title">
            <th colspan="10">物理印章</th>
        </tr>
        <tr>
            <th>章材</th>
            <th>缴费金额(元/个)</th>
        </tr>
        <c:forEach items="${dicts}" var="dict">
        <tr>
            <td>${dict.label}</td>
            <td>
                <input id="stampTexture" name="stampTexture" value="${dict.value}" type="hidden">
                <input type="hidden" name="paymentType" value="PHYSTAMP">
                <input name="money" onblur="b($(this))" value="" type="number" step="0.01">
            </td>
        </tr>
        </c:forEach>
    </table>
    <table class="table-bordered table" style="width: 55%;margin: 0 auto">
        <tbody id="tbody2">
        <tr class="table-tr-title">
            <th>缴费类型</th>
            <th>缴费金额(元/个)</th>
        </tr>
        <tr>
            <td>
                电子印章
                <input type="hidden" name="paymentType" value="ELESTAMP">
            </td>
            <td>
                <input id="eleStamp" name="money" onblur="b($(this))" type="number" value="" step="0.01">
            </td>
        </tr>
        <tr>
            <td>
                U盾
                <input type="hidden" name="paymentType" value="UKEY">
            </td>
            <td>
                <input id="UKEY" name="money" onblur="b($(this))" type="number" value="" step="0.01">
            </td>
        </tr>
        </tbody>
    </table>
    <div style="text-align: center;margin-top: 10px">
        <button id="save" type="button" class="btn btn-primary" disabled>保存</button>
        <button type="button" onclick="window.history.go(-1)" class="btn btn-primary">返 回</button>
    </div>

</form>
</body>
</html>
