<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload" %>
<%@ page import="com.thinkgem.jeesite.modules.stamp.common.Condition" %>
<%@ page import="com.thinkgem.jeesite.common.mapper.JsonMapper" %><%--
  Created by IntelliJ IDEA.
  User: zhuyst
  Date: 2017/10/30
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if(request != null && ServletFileUpload.isMultipartContent(request)){
        Condition condition = new Condition(Condition.ERROR_CODE);
        condition.setMessage("上传文件总大小不能大于20M！");
        String jsonStr = JsonMapper.toJsonString(condition);

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonStr);
    }
%>
