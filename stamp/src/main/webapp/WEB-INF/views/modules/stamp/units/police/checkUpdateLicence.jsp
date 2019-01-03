<%--
  Created by IntelliJ IDEA.
  User: hjw-pc
  Date: 2017/5/16
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<p>测试公安查看并审批license</p>
<p>${licence.makeComp}</p>
<p>${licence.workType}</p>
<form:form id="form" modelAttribute="licence" action="${ctx}/stamp/units/police/police/updateCheckState" method="post" class="breadcrumb form-search hide">
    <input id="id" name="id" type="hidden" value="${licence.id}"/>
    <form:select path="checkState" items="${checkStates}" itemLabel="value" >
</form:select>
    &nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>
</form:form>
</body>
</html>
