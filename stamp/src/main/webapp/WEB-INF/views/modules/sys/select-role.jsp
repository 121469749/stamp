<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/23
  Time: 16:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>角色选择</title>
</head>
<body>
<div style="border: 1px solid #202020;width: 50%;margin: 0 auto;text-align: center ">
    <h3>角色选择</h3>
        <form:form modelAttribute="role" action="${ctx}/sys/role/form" method="post">
            <div>
                <label for="">选择角色:</label>
                <form:select path="deptType" cssStyle="padding: 3px;border-radius: 3px;">
                    <c:forEach items="${fns:getDictList('sys_role_type')}" var="dict">
                        <c:if test='${dict.value == "11"}'>
                            <c:if test="${flag == 1}">
                                <form:option value="${dict.value}" label="${dict.label}"></form:option>
                            </c:if>
                        </c:if>
                        <c:if test='${dict.value != "11"}'>
                            <form:option value="${dict.value}" label="${dict.label}"></form:option>
                        </c:if>
                    </c:forEach>
                </form:select>
            </div>
            <button type="submit" style="margin-top: 10px;padding: 4px 8px;border: 1px solid #868181;border-radius: 3px;">下一步</button>
        </form:form>
</div>
</body>
</html>
