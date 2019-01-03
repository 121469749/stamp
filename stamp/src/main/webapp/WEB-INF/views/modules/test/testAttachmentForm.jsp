<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/10
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="${ctxStatic}/jquery/jquery-1.8.3.js"></script>
    <script type="text/javascript">

        function saveAttachment() {
            var formdata = new FormData($("#Attachment")[0]);
            $.ajax({
                type: "post",
                data: formdata,
                dataType: "json",
                url: "${ctx}/attachment/testSave",
                contentType: false,
                cache: false,
                processData: false,
                success: function (result) {
                    alert(result.message);
                },
                error: function () {
                    alert("fail!");
                }
            })

        }


    </script>
</head>
<body>
<form  method="post"
      enctype="multipart/form-data">

        选择文件:<input  id="Attachment" type="file" name="files">

        <input type="button" value="提交" onClick="saveAttachment()">
</form>
<form:form modelAttribute="testEntity" action="${ctx}/attachment/submitTest" method="post">
        <form:input path="name"></form:input>
        <form:select path="userType">
            <form:options items="${userTypes}" itemLabel="value"></form:options>
        </form:select>
        <input type="submit" value="提交">
    </form:form>
</body>
</html>
