<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html>
<html>
<head lang="zh">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
    <title>用章单位-印章详情</title>
    <link rel="stylesheet" href="${ctxHtml}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${ctxHtml}/css/useUnit_style.css"/>
    <script language="JavaScript" src="${ctxHtml}/js/jquery-1.9.1.min.js"></script>
    <style>
        input[type="text"]{
            border: none;
        }
    </style>
</head>
<body style="padding-left: 3%;padding-top: 3%">
<h3>用章单位-印章详情</h3>
<table class="table-bordered table" style="width: 80%">
    <tr>
        <td>用章单位</td>
        <td colspan="3">${stamp.useComp.companyName}</td>
        <td colspan="2" rowspan="3" align="center">
            <%--<div style="float: left">
                印<br>章<br>图<br>像
            </div>--%>
            <c:choose>
                <c:when test="${!empty stamp.phyModel || !empty stamp.eleModel}">
                    <img src="/img${stamp.eleModel}" alt="" style="">
                </c:when>
                <c:otherwise>
                    <img src="${pageContext.request.contextPath}/static/images/making.png" width="200" height="100" alt="" style="float: left;margin-left: 10px">
                </c:otherwise>
            </c:choose>
        </td>
    </tr>
    <tr>
        <td>刻章点</td>
        <td colspan="3">${stamp.makeComp.companyName}</td>
    </tr>
    <tr>
        <td>现在所属刻章公司</td>
        <td colspan="3">${stamp.nowMakeComp.companyName}</td>
    </tr>
    <tr>
        <td>印章制作状态</td>
        <td>${stamp.stampState.value}</td>
        <td>印章名称</td>
        <td>${stamp.stampName}</td>

        <c:if test="${stamp.stampShape == '2'}">
            <td>印章允许使用次数</td>
            <td>${stamp.allowUseNum}</td>
        </c:if>
    </tr>
    <tr>
        <td>印章形式</td>
        <c:set var="stampShape" value="${stamp.stampShape}"/>
        <td>${fns:getDictLabel(stampShape, "stampShape", null)}</td>

        <td>印章使用状态</td>
        <td>${stamp.useState.value}</td>

        <td>印章系统管控状态</td>
        <td>${stamp.sysState.value}</td>
    </tr>

    <tr>
        <td>印章材质</td>
        <c:set var="stampTexture" value="${stamp.stampTexture}"/>
        <td colspan="2">${fns:getDictLabel(stampTexture, "stampTexture", null)}</td>

        <td>印章类型</td>
        <c:set var="stampType" value="${stamp.stampType}"/>
        <td colspan="2">${fns:getDictLabel(stampType, "stampType", null)}</td>
    </tr>
    <tr>
        <td>备案日期</td>
        <td><fmt:formatDate value="${stamp.recordDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

        <td>制作日期</td>
        <td><fmt:formatDate value="${stamp.makeDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>

        <td>交付时间</td>
        <td><fmt:formatDate value="${stamp.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
        <td >办事类型</td>
        <td>${stamp.lastRecord.workType.value}</td>
        <c:if test="${stamp.lastRecord.workType.key != '1'}">
            <td>办事理由</td>
            <td colspan="3">${stamp.lastRecord.workRemakrs}</td>
        </c:if>
    </tr>
    <tr>
        <td>经办人姓名</td>
        <td>${stamp.lastRecord.agentName}</td>
        <td>经办人电话号码</td>
        <td>${stamp.lastRecord.agentPhone}</td>
        <td>经办人证件类型</td>
        <c:set var="agentCertType" value="${stamp.lastRecord.agentCertType}"/>
        <td >${fns:getDictLabel(agentCertType, "certificateType", null)}</td>

    </tr>
    <tr>
        <td>经办人证件号码</td>
        <td colspan="5">${stamp.lastRecord.agentCertCode}</td>
    </tr>
</table>
<button class="btn btn-default" style="width: 10%;margin: 20px 35%" onclick="history.go(-1)">返回</button>
</body>
</html>