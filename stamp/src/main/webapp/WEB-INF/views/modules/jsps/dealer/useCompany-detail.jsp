<%--
  Created by IntelliJ IDEA.
  User: 77426
  Date: 2017/5/20
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <meta name="renderer" content="webkit">
    <title>用章单位-详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxHtml}/js/jquery-form.js"></script>
</head>
<body>
<div style="width: 80%;margin: 20px auto">
    <h3>用章单位-详情</h3>

    <table class="table table-bordered ">
        <tbody>
        <tr>
            <td>单位名称</td>
            <td colspan="2">${company.companyName}</td>
            <td>单位类别</td>

            <td colspan="2">${fns:getDictLabel(company.type1,"unitType" ,null )}</td>
        </tr>
        <tr>
            <td>单位统一社会信用代号</td>
            <td colspan="2">${company.soleCode}</td>
            <td>单位联系电话</td>
            <td colspan="2">${company.compPhone}</td>
        </tr>
        <tr>
            <td>单位地址</td>
            <td colspan="3"><input type="text" value="${company.compAddress}" style="width: 100%;border: none" readonly></td>
            <td>法人姓名</td>
            <td>${company.legalName}</td>

        </tr>
        <tr>
            <td>法人联系电话</td>
            <td>${company.legalPhone}</td>
            <td>法人证件号码</td>
            <td>${company.legalCertCode}</td>
            <td>法人证件类型</td>
            <td>${fns:getDictLabel(company.legalCertType, "certificateType", null)}${company.legalCertCode}</td>
        </tr>

        <tr>
            <td>经济性质</td>
            <td colspan="2">${fns:getDictLabel(company.busType, "busType", null)}</td>
            <td>企业营业执照</td>
            <td colspan="2"></td>
        </tr>
        <tr>
            <td>企业状态</td>
            <td>${company.compState.value}</td>
            <td>成立日期</td>
            <td>${company.compCreatDate}</td>
        </tr>
        <tr>
            <td>治安负责人</td>
            <td colspan="2">
                ${company.policeName}
            </td>
            <td>治安负责人身份证号码</td>
            <td colspan="2">
                ${company.policeIdCode}
            </td>
        </tr>
        <tr>
            <td>治安负责人联系电话</td>
            <td>
                ${company.policePhone}
            </td>
            <td>单位总人数</td>
            <td>
                ${company.headCount}
            </td>
            <td>单位特业从业人员</td>
            <td>
                ${company.specialCount}
            </td>
        </tr>
        <tr>
            <td>邮编</td>
            <td>
                ${company.postcode}
            </td>
            <td>经营方式</td>
            <td colspan="3">${company.busModel}</td>
        </tr>
        <tr>
            <td>单位电话</td>
            <td>${company.compPhone}</td>
            <td>登记机关</td>
            <td>${company.busScope}</td>
            <td>经营范围</td>
            <td>${company.regCap}</td>
        </tr>
        <tr>
            <td>注册资本</td>
            <td>${company.regCap}</td>

            <td>营业期限起始时间</td>
            <td><fmt:formatDate value="${company.busStartDate}" pattern="yyyy-MM-dd"/></td>

            <td>营业期限截止时间</td>
            <td><fmt:formatDate value="${company.busEndDate}" pattern="yyyy-MM-dd"/></td>

        </tr>

        </tbody>
    </table>
</div>
</body>
</html>
